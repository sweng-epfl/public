public final class WeatherView {
    private static Runnable callback;
    private static String weather;

    /** Gets the current weather that should be displayed. */
    public static String weather() {
        return weather;
    }

    /** Sets a callback to test the button click. */
    public static void setCallback(Runnable callback) {
        WeatherView.callback = callback;
    }

    // Pretend there's a framework like Android that uses this method and requires it to return `void` and have no parameters
    public static void clickButton() {
        Weather.today()
               .thenAccept(w -> {
                   weather = w;
                   if (callback != null) {
                       callback.run();
                   }
               });
    }
}
