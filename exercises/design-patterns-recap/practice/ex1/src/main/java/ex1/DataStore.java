package ex1;

public class DataStore {
    private String data;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
        // Data has changed. Update the UI
    }

    protected void updatePhone(Phone phone) {
        phone.printToPhone(this.data);
    }

    protected void updateScreen(Screen screen) {
        screen.display(this.data);
    }
}