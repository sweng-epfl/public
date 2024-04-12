namespace ExamManager.Utils;

using System.Collections.Generic;

public static class EnumerableExtensions
{
    // Due to the way C# nullable types work we have to write two versions, which is somewhat unfortunate

    public static IEnumerable<T> WhereNonNull<T>(this IEnumerable<T?> enumerable)
        where T : struct
    {
        foreach (var item in enumerable)
        {
            if (item.HasValue)
            {
                yield return item.Value;
            }
        }
    }

    public static IEnumerable<T> WhereNonNull<T>(this IEnumerable<T?> enumerable)
        where T : class
    {
        foreach (var item in enumerable)
        {
            if (item != null)
            {
                yield return item;
            }
        }
    }
}
