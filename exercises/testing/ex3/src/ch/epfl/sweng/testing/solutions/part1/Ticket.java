package ch.epfl.sweng.testing.solutions.part2;

public class Ticket {
    private Category category;
    private String location;
    private String group;

    public Ticket(Category category, String location, String group) {
        this.category = category;
        this.location = location;
        this.group = group;
    }

    public Category getCategory() {
        return category;
    }

    public String getLocation() {
        return location;
    }

    public String getGroup() {
        return group;
    }
}
