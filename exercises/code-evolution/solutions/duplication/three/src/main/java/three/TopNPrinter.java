package three;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TopNPrinter<T extends Comparable<T>> {
    private int N;
    private String header;

    public TopNPrinter(int N, String header) {
        this.N = N;
        this.header = header;
    }

    private List<T> getTopN(List<T> list) {
        List<T> copy = new ArrayList<>(list);
        Collections.sort(copy);
        return copy.subList(0, N);
    }

    public void prettyPrintTopN(List<T> list) {
        System.out.println(header);
        int i = 1;
        for (T t : getTopN(list))
            System.out.println(i++ + ") " + t);
    }

}
