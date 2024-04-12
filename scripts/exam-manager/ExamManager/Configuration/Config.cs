namespace ExamManager.Configuration;

using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Text.Json;
using System.Text.Json.Serialization;

// TODO-Cleanup: use FileInfo&DirectoryInfo instead of string paths
public sealed record Config(GitHubConfig GitHub, string ExamDirectory, string ExamName,
                            IReadOnlyList<string> ExtraItems,
                            IReadOnlyList<string> ExecutableFilenames,
                            string GradescopeId, string? ReportExtraRemark,
                            IReadOnlyList<Question> Questions)
{
    private const string ConfigFilename = "config.json";
    private const string UsersFilename = "users.csv";
    private const string GithubTokenFilename = "github-token.txt";

    // Default values for these are not intended to be used, only there because making them nullable would be a huge pain
    // (we only use Config from its Load method so it's ok)

    [JsonIgnore] // stored separately, can change often
    public IReadOnlyList<User> Users { get; private set; } = Array.Empty<User>();

    [JsonIgnore] // stored separately, because it's per-user not per-exam
    public string GithubToken { get; private set; } = string.Empty;


    public static Config Load()
    {
        if (!File.Exists(ConfigFilename) || !File.Exists(UsersFilename) || !File.Exists(GithubTokenFilename))
        {
            Console.WriteLine($"Please fill in {ConfigFilename} and {UsersFilename} and {GithubTokenFilename}");
            Environment.Exit(1);
        }

        string json = File.ReadAllText(ConfigFilename);
        string[] users = File.ReadAllLines(UsersFilename);
        string githubToken = File.ReadAllText(GithubTokenFilename);

        // Be nice, allow convenient things in the config
        var jsonOptions = new JsonSerializerOptions
        {
            AllowTrailingCommas = true,
            ReadCommentHandling = JsonCommentHandling.Skip,
            Converters = { new JsonStringEnumConverter() } // enums as strings instead of ints
        };

        var config = JsonSerializer.Deserialize<Config>(json, jsonOptions);
        ArgumentNullException.ThrowIfNull(config, "why would you ever do this");

        config.Users = users.Where(s => !string.IsNullOrWhiteSpace(s)).Select(User.Parse).ToArray();
        config.GithubToken = githubToken.Trim();

        return config;
    }
}
