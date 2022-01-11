package ch.epfl.sweng.todoapp.models;

public class TodoItem {
    public enum Status {
        CURRENT,
        DONE
    }

    public final String name;
    private Status status;

    public TodoItem(String name, Status status) {
        this.name = name;
        this.status = status;
    }

    public Status getStatus() {
        return this.status;
    }

    public TodoItem setStatus(Status status) {
        this.status = status;
        return this;
    }
}
