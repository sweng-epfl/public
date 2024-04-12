namespace ExamManager.Configuration;

public sealed record GitHubConfig(string Organization, string StaffTeam,
                                  string SkeletonRepoName,
                                  string RepoPrefix, string TeamPrefix);
