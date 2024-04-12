namespace ExamManager;

using System.Collections.Generic;
using System.Linq;
using System.Text;

public sealed record Report(string Title,
                            IReadOnlyList<ReportEntry> Entries,
                            IReadOnlyList<Report> SubReports)
{
    /// File type that corresponds to the ToString format
    public static readonly string FileType = ".md";

    public (decimal Obtained, decimal Available) GetPoints()
    {
        decimal obtained = Entries.Sum(e => e.PointsObtained);
        decimal available = Entries.Sum(e => e.PointsAvailable);
        foreach (var subReport in SubReports)
        {
            var (subObtained, subAvailable) = subReport.GetPoints();
            obtained += subObtained;
            available += subAvailable;
        }
        if (obtained >= available)
        {
            obtained = available;
        }
        if (obtained < 0)
        {
            obtained = 0;
        }
        return (obtained, available);
    }

    public Report AppendEntry(ReportEntry entry)
    {
        return this with { Entries = Entries.Append(entry).ToArray() };
    }

    public Report AppendSubReport(Report subReport)
    {
        return this with { SubReports = SubReports.Append(subReport).ToArray() };
    }

    public override string ToString()
    {
        var builder = new StringBuilder();
        Print(builder, 0);
        return builder.ToString();
    }

    private void Print(StringBuilder builder, int level)
    {
        // NOTES:
        // - We use "out of" instead of a division sign to avoid ambiguities (e.g. "1/2 points" can be read as 0.5 or as 1 out of 2)
        // - We explicitly use '\n' as a separator to make sure the reports are the exact same regardless of the OS
        const char NewLine = '\n';

        var (obtained, available) = GetPoints();

        // Title as Markdown heading (level is 0-based so we use <=)
        for (int n = 0; n <= level; n++)
        {
            builder.Append('#');
        }
        if (available > 0)
        {
            builder.Append($" {Title} [{obtained:0.##} out of {available:0.##} points]" + NewLine);
        }
        else
        {
            builder.Append($" {Title} [{obtained:0.##} points]" + NewLine);
        }
        builder.Append(NewLine);

        foreach (var entry in Entries)
        {
            builder.Append(entry.ToString());
            builder.Append(NewLine);
        }

        builder.Append(NewLine);

        foreach (var subReport in SubReports)
        {
            subReport.Print(builder, level + 1);
        }
    }
}
