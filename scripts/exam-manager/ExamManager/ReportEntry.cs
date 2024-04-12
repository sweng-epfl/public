namespace ExamManager;

using System.Text;

// "X out of 0" with X!=0 means bonus/malus points
// "0 out of 0" means info only
public sealed record ReportEntry(string Description, decimal PointsObtained, decimal PointsAvailable)
{
    public override string ToString()
    {
        var builder = new StringBuilder();
        builder.Append("- ");

        // No points obtained nor available -> just an info message
        if (PointsObtained == 0 && PointsAvailable == 0)
        {
            builder.Append('\u2139'); // 'i' info box
            builder.Append(" " + Description);
            return builder.ToString();
        }

        if (PointsObtained > 0)
        {
            if (PointsAvailable == 0 || PointsAvailable == PointsObtained)
            {
                builder.Append('\u2714'); // tick
            }
            else
            {
                builder.Append("\u26a0\ufe0f"); // warning
            }
        }
        else
        {
            builder.Append('\u274C'); // cross
        }

        builder.Append($" **{PointsObtained:0.##} ");
        if (PointsAvailable == 0)
        {
            builder.Append("point");
            if (PointsObtained != 1)
            {
                builder.Append('s');
            }
        }
        else
        {
            builder.Append($"out of {PointsAvailable:0.##} point");
            if (PointsAvailable != 1)
            {
                builder.Append('s');
            }
        }
        builder.Append($"**: {Description}");
        return builder.ToString();
    }
}
