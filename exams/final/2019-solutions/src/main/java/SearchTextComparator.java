import java.util.Comparator;

final class SearchTextComparator implements Comparator<String> {
    @Override
    public int compare(String left, String right) {
        if (left == null) {
            return right == null ? 0 : 1;
        }
        if (right == null) {
            return -1;
        }

        left = left.trim().toLowerCase();
        right = right.trim().toLowerCase();
        return left.compareTo(right);
    }
}
