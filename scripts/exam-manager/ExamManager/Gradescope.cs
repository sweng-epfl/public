namespace ExamManager;

using System;
using System.Collections.Generic;
using System.Globalization;
using System.IO;
using System.Linq;
using System.Net.Http;
using System.Net.Http.Headers;
using System.Net.Http.Json;
using System.Threading.Tasks;

using CsvHelper;
using CsvHelper.Configuration;

// This works as of Fall 2022
public static class Gradescope
{
    // Gradescope expects user emails, not just IDs
    private const string UserIdSuffix = "@example.org";


    private static readonly HttpClient _client;

    static Gradescope()
    {
        _client = new HttpClient
        {
            BaseAddress = new Uri("https://www.gradescope.com")
        };
        _client.DefaultRequestHeaders.Add("Accept", "application/json");
    }


    public static async Task LoadTokenAsync(string email, string password)
    {
        var data = new FormUrlEncodedContent(new Dictionary<string, string>
            {
                { "email", email },
                { "password", password }
            });
        var response = await _client.PostAsync("/api/v1/user_session", data);
        response.EnsureSuccessStatusCode();
        var json = await response.Content.ReadFromJsonAsync<TokenResponse>();
        ArgumentNullException.ThrowIfNull(json, "Gradescope token response is null?!");
        _client.DefaultRequestHeaders.Add("access-token", json.Token);
    }

    public static async Task DumpAsRosterAsync(IReadOnlyCollection<User> users, FileInfo file)
    {
        using var writer = new StreamWriter(file.OpenWrite());
        using var csv = new CsvWriter(writer, new CsvConfiguration(CultureInfo.InvariantCulture));

        csv.WriteField("Full name");
        csv.WriteField("Email");
        await csv.NextRecordAsync();
        foreach (var user in users)
        {
            csv.WriteField(user.MainId);
            csv.WriteField(user.MainId + UserIdSuffix);
            await csv.NextRecordAsync();
        }
        await csv.FlushAsync();
    }

    public static async Task UploadFilesAsync(string courseId, string assignmentId, string userId, IReadOnlyCollection<FileInfo> files)
    {
        using var data = new MultipartFormDataContent();
        foreach (var file in files)
        {
            var fileContent = new StreamContent(file.OpenRead());
            fileContent.Headers.ContentDisposition = new ContentDispositionHeaderValue("form-data")
            {
                // The extra quotes look weird but are required
                Name = "\"files[]\"",
                FileName = "\"" + file.Name + "\""
            };
            fileContent.Headers.ContentType = new MediaTypeHeaderValue("text/plain");
            data.Add(fileContent);
        }
        data.Add(new StringContent(userId + UserIdSuffix)
        {
            Headers = { { "Content-Disposition", "form-data; name=\"owner_email\"" } }
        });
        var response = await _client.PostAsync(
            $"/api/v1/courses/{courseId}/assignments/{assignmentId}/submissions",
            data
        );
        response.EnsureSuccessStatusCode();
    }


    public static async Task<(decimal MaxPoints, Dictionary<User, Report> Reports)> ParseReportsAsync(FileInfo file, IReadOnlyCollection<User> users, string title)
    {
        var template = ParseReportTemplate(file);

        using var reader = new StreamReader(file.OpenRead());
        using var csv = new CsvReader(reader, CultureInfo.InvariantCulture);
        csv.Read();
        csv.ReadHeader(); // We ignore headers, `template` has all we need, but we do need to explicitly skip over their row

        var maxPoints = template.Where(g => g.Name != null).Sum(g => g.Entries.Max(e => e.Value.PointsAvailable));
        var usersById = users.ToDictionary(u => u.MainId, u => u);

        var results = new Dictionary<User, Report>();
        while (await csv.ReadAsync())
        {
            var userName = csv.GetField<string>("Name");
            if (string.IsNullOrWhiteSpace(userName))
            {
                // reached the end, ignore the remaining stuff
                break;
            }

            var user = usersById[userName];
            var userEntries = new List<ReportEntry>();
            foreach (var group in template)
            {
                if (group.Name == null)
                {
                    // At the top level, everything is extra
                    foreach (var entry in group.Entries)
                    {
                        if (csv.GetField<bool>(entry.Key))
                        {
                            userEntries.Add(entry.Value);
                        }
                    }
                }
                else
                {
                    // Otherwise, all groups are pick 0 or 1
                    var picked = group.Entries.Where(p => csv.GetField<bool>(p.Key)).Select(p => p.Value).ToArray();
                    if (picked.Length == 1)
                    {
                        userEntries.Add(picked[0]);
                    }
                    else if (picked.Length == 0)
                    {
                        var groupPoints = group.Entries.Max(e => e.Value.PointsAvailable);
                        userEntries.Add(new ReportEntry(Negate(group.Name), 0, groupPoints));
                    }
                    else
                    {
                        throw new Exception("More than one picked for " + user + " in " + group + ": " + string.Join(", ", picked.Select(x => x.ToString())));
                    }
                }
            }

            var userReport = new Report(
                title,
                userEntries.ToArray(),
                Array.Empty<Report>()
            );

            string adjustment = csv.GetField("Adjustment");
            if (adjustment != string.Empty)
            {
                string reason = csv.GetField("Comments");
                var extra = new ReportEntry(reason, decimal.Parse(adjustment), 0);
                userReport = userReport.AppendEntry(extra);
            }
            results.Add(user, userReport);
        }

        return (maxPoints, results);
    }

    private static ReportGroupTemplate[] ParseReportTemplate(FileInfo file)
    {
        using var reader = new StreamReader(file.OpenRead());
        using var csv = new CsvReader(reader, CultureInfo.InvariantCulture);
        csv.Read();

        // Headers (and rows) also include a bunch of extra info we must skip, both before and after
        csv.ReadHeader();
        if (csv.HeaderRecord == null)
        {
            throw new Exception("No headers!?");
        }
        var dataStartInclusive = Array.IndexOf(csv.HeaderRecord, "Submission Time") + 1;
        var dataEndExclusive = Array.IndexOf(csv.HeaderRecord, "Adjustment");

        // The point values are near the end, after a blank line following the entries
        // HOWEVER, the indexes don't match the headers :-) so we find the first index instead
        int pointsIndexStart = 0;
        while (true)
        {
            if (!csv.Read())
            {
                throw new Exception("Reached end of CSV before reaching point values!?");
            }
            if (csv.GetField(0) == "Point Values")
            {
                while (!decimal.TryParse(csv.GetField(pointsIndexStart), out _))
                {
                    pointsIndexStart++;
                }
                pointsIndexStart++; // it starts with a sum, which we must ignore as well
                break;
            }
        }

        // Two phases: first create groups without "available" points, then re-create them once we know what the max points of each group are
        var groups = new List<ReportGroupTemplate>();
        int index = -1;
        foreach (var header in csv.HeaderRecord[dataStartInclusive..dataEndExclusive])
        {
            index++;

            var parts = header.Split(':', 2, StringSplitOptions.TrimEntries);
            string? groupName;
            string text;
            if (parts.Length == 1)
            {
                groupName = null;
                text = header;
            }
            else
            {
                groupName = parts[0];
                text = parts[1];
            }

            if (text.StartsWith("SKIP|"))
            {
                continue;
            }

            // Dubious complexity, but makes things easier since Dictionary<string, ...> can't have null keys, and the data is small
            var group = groups.FirstOrDefault(g => g.Name == groupName);
            if (group == null)
            {
                group = new ReportGroupTemplate(groupName, new());
                groups.Add(group);
            }

            // At this point `csv` is on the points line
            var points = csv.GetField<decimal>(index + pointsIndexStart);
            group.Entries.Add(index + dataStartInclusive, new ReportEntry(text, points, 0));
        }

        return groups.Select(g =>
        {
            var availablePoints = g.Name == null ? 0 : g.Entries.Max(p => p.Value.PointsObtained);
            return new ReportGroupTemplate(g.Name, g.Entries.ToDictionary(p => p.Key, p => p.Value with { PointsAvailable = availablePoints }));
        }).ToArray();
    }

    private static string Negate(string text)
    {
        return "No " + char.ToLowerInvariant(text[0]) + text[1..];
    }

    // Null name -> top-level group
    private sealed record ReportGroupTemplate(string? Name, Dictionary<int, ReportEntry> Entries);

    // Exists to easily parse Gradescope's token API response
    private sealed class TokenResponse
    {
        public string? Token { get; set; }
        // no need for the rest
    }
}
