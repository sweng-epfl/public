package ex6;

public class App {
    public static void main(String[] args) {
        File file = new RealFile();

        file.read("some_file.txt");
        file.write("some_other_file.txt", "crazy data");
        file.read("highly_sensitive_file.NSA");

        File proxy = new ProxyFile();
        proxy.read("some_file_that_is_cool.doc");
        proxy.write("can_I_read_this_file.xls", "evil_data");

        // Implement your ProxyFile this way so the following calls will print some kind of error message
        proxy.read("this_file_is_sensitive"); // should print an error message
        proxy.write("sensitive.php", "new_password"); // should print an error message
    }
}
