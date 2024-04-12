namespace ExamManager;

public sealed record TestResult(string FullName, string DisplayName, bool Success, string? Message);