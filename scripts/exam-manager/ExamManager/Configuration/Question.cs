namespace ExamManager.Configuration;

using System.Collections.Generic;
using System.IO;
using System.Text.Json.Serialization;
using System.Threading.Tasks;

public abstract partial record Question([JsonDiscriminator] QuestionType Type, string Name, string? GradescopeId)
{
    public abstract Task<IReadOnlyCollection<FileInfo>> GetFilesToManuallyGradeAsync(DirectoryInfo userDir, DirectoryInfo skeletonDir);

    // this one is batched since reports are typically for all users at a time
    public abstract Task<IReadOnlyDictionary<User, Report>> GradeAsync(FileInfo report, IReadOnlyDictionary<User, DirectoryInfo> userDirs);
}
