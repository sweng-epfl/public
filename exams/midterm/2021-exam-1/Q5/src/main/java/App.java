/**
 * Entry point of the application.
 * !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
 * You MUST use this file for the real dependencies of the announcements fetcher.
 * You CAN add new constructors, methods, and nested classes to this class.
 * You MUST NOT edit the name, parameters, checked exceptions or return type of the main method.
 * You MUST NOT delete the main method.
 * You MUST NOT rename this file.
 * You MUST NOT delete this file.
 * !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
 */
public final class App {
    public static void main(String[] args) {
        System.out.println("Hello, World! Once the server people do their job this app will stop crashing...");
        System.out.println("But for now it just needs to be correct so that it'll work when the server is set up.");
        new AnnouncementsFetcher().fetch();
    }
}
