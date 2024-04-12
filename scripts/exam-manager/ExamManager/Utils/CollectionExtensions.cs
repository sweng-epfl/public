namespace ExamManager.Utils;

using System;
using System.Collections.Concurrent;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

using ShellProgressBar;

public static class CollectionExtensions
{
    public static IEnumerable<T> WithProgress<T>(this IReadOnlyCollection<T> list, string text, Func<T, string>? printer = null)
    {
        printer ??= (t => t?.ToString() ?? "<null>");
        using var bar = new ProgressBar(list.Count, text + "...");
        foreach (var item in list)
        {
            bar.Tick($"{text}: {printer(item)}");
            yield return item;
        }
    }

    public static async Task<IReadOnlyCollection<(T Item, TResult Result)>> ParallelSelectWithProgressAsync<T, TResult>(this IReadOnlyCollection<T> list, string text, Func<T, Task<TResult>> func)
        where T : notnull
    {
        // Note that ProgressBar handles concurrent calls already, nothing to do on that front
        using var bar = new ProgressBar(list.Count, text + "...");
        var results = new ConcurrentDictionary<T, TResult>();
        await Parallel.ForEachAsync(list, async (item, _) =>
        {
            results[item] = await func(item);
            // Since the processing happens in parallel, it makes more sense to show the % finished
            // (unlike the sequential one, there's little point in seeing "what the current one is")
            bar.Tick($"Finished {text}: {item}");
        });
        // Keep the order!
        return list.Select(it => (it, results[it])).ToArray();
    }
}
