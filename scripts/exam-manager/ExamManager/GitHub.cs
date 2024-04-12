namespace ExamManager;

using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Net;
using System.Threading.Tasks;

using ExamManager.Configuration;
using ExamManager.Utils;

using Octokit;

public sealed class GitHub
{
    private const string Remote = "git@github.com";
    private const string ProductName = "EPFL-SwEng-Manager";
    private static readonly DirectoryInfo LocalReposDirectory = new("repos");

    private readonly GitHubClient _client;
    private readonly GitHubConfig _config;
    private Dictionary<string, Team> _teams;
    private Dictionary<string, Repository> _repos;
    private bool _hasClonedSkeleton;
    private int _staffTeamId;

    private GitHub(string token, GitHubConfig config)
    {
        _client = new GitHubClient(new ProductHeaderValue(ProductName))
        {
            Credentials = new Credentials(token)
        };
        _config = config;
        // Need a default value so using them is not awkward, but anyway the only way to externally get an instance
        // is the InitializeAsync method, which overwrites them immediately
        _teams = new();
        _repos = new();
    }

    // "async constructor", basically
    public static async Task<GitHub> InitializeAsync(string token, GitHubConfig config)
    {
        // In theory, we do not need the Where with prefixes for teams/repos,
        // but this massively reduces risk in case of programming errors, e.g. "delete all repos" at least won't delete unrelated repos

        var github = new GitHub(token, config);
        github._teams = (await github._client.Organization.Team.GetAll(config.Organization)).ToDictionary(t => t.Slug, t => t);
        github._repos = (await github._client.Repository.GetAllForOrg(config.Organization)).ToDictionary(r => r.Name, r => r);

        if (!github._teams.TryGetValue(config.StaffTeam, out var staffTeam))
        {
            throw new Exception("Could not find staff team.");
        }
        github._staffTeamId = staffTeam.Id;

        return github;
    }

    public string GetRateLimitInfo()
    {
        var info = _client.GetLastApiInfo();
        if (info == null)
        {
            return "???";
        }
        return $"{info.RateLimit?.Remaining} remaining, reset at {info.RateLimit?.Reset.ToLocalTime()}";
    }

    public async Task<DirectoryInfo> CloneSkeletonRepoAsync()
    {
        var dir = LocalReposDirectory.SubDirectory(_config.SkeletonRepoName);
        if (!_hasClonedSkeleton)
        {
            await Git.CloneAsync(Remote, _config.Organization, dir);
            _hasClonedSkeleton = true;
        }
        return dir;
    }

    public async Task<DirectoryInfo> GetUserRepoAsync(string userId, string userEmail)
    {
        await EnsureTeamExistsAsync(userId, userEmail);

        var repoName = _config.RepoPrefix + userId;
        var repoDir = LocalReposDirectory.SubDirectory(repoName);
        if (_repos.ContainsKey(repoName))
        {
            await Git.CloneAsync(Remote, _config.Organization, repoDir);
        }
        else
        {
            // GitHub says there are "secondary rate limits" on calls such as creating repos and teams,
            // let's make sure we don't hit them; since we likely needed to create the team as well,
            // wait both before and after creating the repo
            await Task.Delay(TimeSpan.FromSeconds(1.0));

            var repo = await _client.Repository.Create(
                _config.Organization,
                new NewRepository(repoName) { TeamId = _staffTeamId, Private = true }
            );
            await Git.CreateRepoAsync(Remote, _config.Organization, repoDir);
            _repos.Add(repoName, repo);

            await Task.Delay(TimeSpan.FromSeconds(1.0));
        }
        return repoDir;
    }


    public Task ShowRepoAsync(string userId)
    {
        var team = _teams[_config.TeamPrefix + userId];
        return _client.Organization.Team.AddRepository(
            team.Id,
            _config.Organization,
            _config.RepoPrefix + userId,
            new RepositoryPermissionRequest(Permission.Push)
        );
    }

    public Task FreezeRepoAsync(string userId)
    {
        var team = _teams[_config.TeamPrefix + userId];
        return _client.Organization.Team.AddRepository(
            team.Id,
            _config.Organization,
            _config.RepoPrefix + userId,
            new RepositoryPermissionRequest(Permission.Pull)
        );
    }

    public Task HideRepoAsync(string userId)
    {
        var team = _teams[_config.TeamPrefix + userId];
        return _client.Organization.Team.RemoveRepository(team.Id, _config.Organization, _config.RepoPrefix + userId);
    }

    public async Task<bool> IsRepoShownAsync(string userId)
    {
        var team = _teams[_config.TeamPrefix + userId];
        var repo = _repos[_config.RepoPrefix + userId];
        var repos = await _client.Organization.Team.GetAllRepositories(team.Id);
        return repos.Any(r => r.Name == repo.Name && r.Permissions.Push);
    }

    public async Task<bool> IsBuildFailingAsync(string userId)
    {
        var repo = _repos[_config.RepoPrefix + userId];
        var runs = await _client.Check.Run.GetAllForReference(repo.Id, "main");
        return runs.CheckRuns.Any(r => r.Conclusion.HasValue && r.Conclusion.Value == CheckConclusion.Failure);
    }

    public DateTimeOffset GetLastPush(string userId)
    {
        // will never be null since we have always pushed at least one commit
        return _repos[_config.RepoPrefix + userId].PushedAt!.Value;
    }

    public async Task DeleteRepoAsync(string userId)
    {
        var repoId = _config.RepoPrefix + userId;
        if (!_repos.ContainsKey(repoId))
        {
            return;
        }

        var repo = _repos[repoId];
        var team = _teams[_config.TeamPrefix + userId];

        var loginsToDelete = new List<string>();
        foreach (var user in await _client.Organization.Team.GetAllMembers(team.Id))
        {
            var info = await _client.Organization.Member.GetOrganizationMembership(_config.Organization, user.Login);
            if (info.Role.Value != MembershipRole.Admin)
            {
                loginsToDelete.Add(user.Login);
            }
        }
        if (loginsToDelete.Count > 1)
        {
            throw new Exception("Expected at most 1 non-owner member in team: " + team.Slug);
        }

        await _client.Organization.Team.Delete(team.Id);
        foreach (var login in loginsToDelete)
        {
            await _client.Organization.Member.RemoveOrganizationMembership(_config.Organization, login);
        }
        await _client.Repository.Delete(repo.Id);
    }

    private async Task<Team> EnsureTeamExistsAsync(string teamId, string userEmail)
    {
        var teamName = _config.TeamPrefix + teamId;
        if (!_teams.TryGetValue(teamName, out var team))
        {
            team = await _client.Organization.Team.Create(_config.Organization, new NewTeam(teamName));
            _teams.Add(teamName, team);
        }
        // Inviting should be a no-op if already invited but in practice last time I tried it still re-invited...
        // 2 members: the user who created the team, and the person who was invited
        if (team.MembersCount == 2)
        {
            return team;
        }

        // Octokit doesn't have invite-by-email, and their codebase is complex enough I'm not going to bother with a PR...
        var response = await _client.Connection.Post(
            new Uri($"orgs/{_config.Organization}/invitations", UriKind.Relative),
            new InvitationRequest { Email = userEmail, TeamIds = new[] { team.Id } },
            AcceptHeaders.StableVersion
        );
        if (response != HttpStatusCode.Created)
        {
            throw new Exception("Oh no: " + response);
        }
        return team;
    }

    public sealed class InvitationRequest
    {
        public string? Email { get; set; }
        public int[]? TeamIds { get; set; }
    }
}
