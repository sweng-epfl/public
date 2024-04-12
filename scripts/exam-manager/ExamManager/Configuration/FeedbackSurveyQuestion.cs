namespace ExamManager.Configuration;

using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Threading.Tasks;

public sealed record FeedbackSurveyQuestion(string Name, decimal Points)
    : Question(QuestionType.FeedbackSurvey, Name, GradescopeId: null)
{
    public override Task<IReadOnlyCollection<FileInfo>> GetFilesToManuallyGradeAsync(DirectoryInfo userDir, DirectoryInfo skeletonDir)
    {
        return Task.FromResult<IReadOnlyCollection<FileInfo>>(Array.Empty<FileInfo>());
    }

    public override Task<IReadOnlyDictionary<User, Report>> GradeAsync(FileInfo report, IReadOnlyDictionary<User, DirectoryInfo> userDirs)
    {
        return Task.FromResult(Moodle.GetSurveyReports(report, userDirs.Keys.ToArray(), Name, Points));
    }
}
