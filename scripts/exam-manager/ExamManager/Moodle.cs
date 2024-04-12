namespace ExamManager;

using System;
using System.Collections.Generic;
using System.Globalization;
using System.IO;
using System.Linq;

using CsvHelper;
using CsvHelper.Configuration;

public static class Moodle
{
    public static IReadOnlyDictionary<User, Report> GetSurveyReports(FileInfo surveyFile, IReadOnlyCollection<User> users, string title, decimal points)
    {
        var config = new CsvConfiguration(CultureInfo.InvariantCulture)
        {
            HasHeaderRecord = false
        };
        using var reader = new StreamReader(surveyFile.OpenRead());
        using var csv = new CsvReader(reader, config);

        var completed = new HashSet<string>();
        while (csv.Read())
        {
            var value = csv.GetField(1);
            if (value != "-")
            {
                var id = csv.GetField(0);
                completed.Add(id);
            }
        }

        ReportEntry CreateEntry(User u) =>
            u.Ids.Any(completed.Contains) ? new ReportEntry("Survey completed, thank you very much", points, points)
                                          : new ReportEntry("Survey not completed", 0, points);

        return users.ToDictionary(
            u => u,
            u => new Report(title, new[] { CreateEntry(u) }, Array.Empty<Report>())
        );
    }
}
