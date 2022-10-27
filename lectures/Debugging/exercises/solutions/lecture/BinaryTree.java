import java.util.*;

public class App {
    static final class BinaryTree {
        public final String value;
        public final BinaryTree left;
        public final BinaryTree right;

        public BinaryTree(String v, BinaryTree l, BinaryTree r) {
            value = v;
            left = l;
            right = r; // BUGFIX: not 'l'... with better names, this kind of error is less likely to happen!
        }

        public static BinaryTree fromList(List<String> list) {
            // BUGFIX: Base case for the recursion
            if (list.size() == 0) {
                return null;
            }
            int mid = list.size() / 2;
            // BUGFIX: Fixed the slicing bounds; note that the upper bound to subList is exclusive, as you can tell from its documentation
            return new BinaryTree(list.get(mid), fromList(list.subList(0, mid)), fromList(list.subList(mid+1, list.size())));
        }

        public List<String> toList() {
            var result = new ArrayList<String>();
            if (left != null) {
                result.addAll(left.toList());
            }
            result.add(value);
            if (right != null) {
                result.addAll(right.toList());
            }
            return result;
        }
    }

    public static void main(String[] args) {
        var list = List.of("ABC", "DEF", "GHI", "JKL", "MNO", "PQR", "STU");
        var tree = BinaryTree.fromList(list);
        var newList = tree.toList();
        System.out.println("List before: " + String.join(", ", list));
        System.out.println("List after: " + String.join(", ", newList));
    }
}
