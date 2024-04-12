namespace ExamManager.Configuration;

using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Threading.Tasks;

using ExamManager.Utils;

public sealed record FreeFormQuestion(string Name, string GradescopeId, string File)
    : Question(QuestionType.FreeForm, Name, GradescopeId)
{
    public override Task<IReadOnlyCollection<FileInfo>> GetFilesToManuallyGradeAsync(DirectoryInfo userDir, DirectoryInfo skeletonDir)
    {
        var skeletonFile = skeletonDir.File(File);
        var file = userDir.File(File);

        if (FileSystemUtils.HasSignificantDiff(file, skeletonFile))
        {
            FileSystemUtils.DeleteUnchanged(file, skeletonFile);
            return Task.FromResult<IReadOnlyCollection<FileInfo>>(new[] { file });
        }

        return Task.FromResult<IReadOnlyCollection<FileInfo>>(Array.Empty<FileInfo>());
    }

    public override async Task<IReadOnlyDictionary<User, Report>> GradeAsync(FileInfo report, IReadOnlyDictionary<User, DirectoryInfo> userDirs)
    {
        var (maxPoints, results) = await Gradescope.ParseReportsAsync(report, userDirs.Keys.ToArray(), Name);
        foreach (var user in userDirs.Keys)
        {
            if (!results.ContainsKey(user))
            {
                results[user] = new Report(
                    Name,
                    new[] { new ReportEntry("No answer given", 0, maxPoints) },
                    Array.Empty<Report>()
                );
            }
        }
        return new Dictionary<User, Report>(results);
    }
}
