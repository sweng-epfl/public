package ex6;

public class ProxyFile implements File {

    private File file = new RealFile();

    private static final String ERROR_MESSAGE = "Something sneaky is going on here! Somebody is trying to access a sensitive file";

    private boolean isSensitive(String fileName) {
        return fileName.contains("sensitive");
    }

    @Override
    public String read(String fileName) {
        if (isSensitive(fileName)) {
            System.err.println(ERROR_MESSAGE);
            return "no data";
        }
        return file.read(fileName);
    }

    @Override
    public void write(String fileName, String data) {
        if (isSensitive(fileName)) {
            System.err.println(ERROR_MESSAGE);
            return;
        }
        file.write(fileName, data);
    }
}
