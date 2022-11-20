import library.BinaryTree;

import java.util.Arrays;
import java.util.List;

/**
 * Entry point of the application.
 * !!!!!!!!!!!!!!!!!!!!!!
 * DO NOT TOUCH THIS FILE
 * !!!!!!!!!!!!!!!!!!!!!!
 */
public class App {

    public static void main(String[] args) {
        List<Integer> fetchedData = Arrays.asList(1, 2, 3, 4, 5, 6, 7);
        BinaryTree binaryTree = new ListAdapter(fetchedData);
    }

}