public class Course {
    public enum TYPE {
        MATH, ART, ENGLISH, HISTORY, GEOGRAPHY
    }

    private TYPE type;

    public Course(TYPE type) {
        this.type = type;
    }

    public TYPE getType() {
        return type;
    }
}