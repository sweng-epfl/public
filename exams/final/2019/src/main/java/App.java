// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
// You CAN edit everything in this file
// You CAN delete this file
// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

final class App {
    public static void main(String[] args) throws IOException {
        AppController controller = new AppController(new TestDirectory());
        AppView view = controller.handle("");

        BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));

        while (true) {
            String text = view.toString();
            System.out.print(text);
            do {
                view = controller.handle(stdin.readLine());
            } while (view == null);
        }
    }


    private static final class TestDirectory implements Directory {
        @Override
        public List<Person> search(String name) {
            // You can fill this in if you want to run manual tests using the command line.
            // (The App class is only here to show how the Model, View and Controller go together, it is not graded, so you are not required to do anything in this file)
            throw new UnsupportedOperationException();
        }
    }
}