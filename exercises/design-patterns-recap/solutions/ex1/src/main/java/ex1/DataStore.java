package ex1;

public class DataStore extends Subject {

    private String data;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
        // Data has changed. Update the UI
        notifyObservers(data);
    }


}
