import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

public class App {
    private interface Model {
        String getForecast();
    }

    private interface View {
        void show(String text);
        void run(Presenter presenter);
    }

    private static final class RandomModel implements Model {
        public String getForecast() {
            int weather = ThreadLocalRandom.current().nextInt(0, 4);
            if (weather == 0) {
                return "Sunny";
            } else if (weather == 1) {
                return "Rainy";
            } else {
                return "???";
            }
        }
    }

    private static final class ConsoleView implements View {
        private final Scanner scanner = new Scanner(System.in);

        public void show(String text) {
            System.out.println(text);
        }

        public void run(Presenter presenter) {
            while (true) {
                scanner.nextLine();
                presenter.onInput();
            }
        }
    }

    private static final class Presenter {
        private final Model model;
        private final View view;

        Presenter(Model model, View view) {
            this.model = model;
            this.view = view;
        }

        public void onInput() {
            view.show("Weather forecast: " + model.getForecast());
        }

        public void run() {
            // This is still specifically related to a console view; a real app might want to let the View display (part of) this information
            view.show("Hi! Hit Enter to check the weather forecast");
            view.run(this);
        }
    }

    public static void main(String[] args) {
        new Presenter(new RandomModel(), new ConsoleView()).run();
    }
}
