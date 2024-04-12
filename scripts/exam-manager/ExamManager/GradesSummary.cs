namespace ExamManager;

using System.Collections.Generic;
using System.Globalization;
using System.IO;
using System.Linq;

using CsvHelper;
using CsvHelper.Configuration;

public static class GradesSummary
{
    private const string IdHeader = "ID";
    private const string PointsHeader = "Points";
    private const string GradeHeader = "Grade";


    public static void WritePoints(FileInfo file, IReadOnlyDictionary<User, decimal> grades)
    {
        using var writer = new StreamWriter(file.OpenWrite());
        using var csv = new CsvWriter(writer, new CsvConfiguration(CultureInfo.InvariantCulture));

        // Fake header; we do not know how many IDs there are but need to write them all
        // These must match the definition below
        csv.WriteField(IdHeader);
        for (int n = 1; n < (grades.Keys.FirstOrDefault()?.Ids?.Count ?? 0); n++)
        {
            csv.WriteField(string.Empty);
        }
        csv.WriteField(PointsHeader);
        csv.WriteField(GradeHeader);
        csv.NextRecord();

        foreach (var pair in grades)
        {
            foreach (var id in pair.Key.Ids)
            {
                csv.WriteField(id);
            }
            csv.WriteField(pair.Value);
            csv.WriteField(string.Empty); // no grade yet
            csv.NextRecord();
        }
        csv.Flush();
    }

    public static IReadOnlyDictionary<User, string> ReadGrades(FileInfo file, IReadOnlyCollection<User> users)
    {
        var config = new CsvConfiguration(CultureInfo.InvariantCulture)
        {
            HasHeaderRecord = true
        };
        using var reader = new StreamReader(file.OpenRead());
        using var csv = new CsvReader(reader, config);

        var perId = new Dictionary<string, string>();
        csv.Read();
        csv.ReadHeader();
        while (csv.Read())
        {
            perId.Add(csv.GetField<string>(IdHeader), csv.GetField<string>(GradeHeader));
        }
        return users.ToDictionary(u => u, u => perId[u.MainId]);
    }
}
