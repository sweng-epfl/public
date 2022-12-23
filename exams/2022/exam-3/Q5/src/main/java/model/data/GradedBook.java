package model.data;

/**
 * Represents a graded book.
 * <p>
 * !!!!!!!!!!!!!!!!!!!!!! <p>
 * DO NOT TOUCH THIS FILE <p>
 * !!!!!!!!!!!!!!!!!!!!!! <p>
 */
public final class GradedBook {
    private final String title;
    private int grade;

    /**
     * Creates a new graded book.
     * @param title the title of the book
     * @param grade the grade of the book
     */
    public GradedBook(String title, int grade) {
        this.title = title;
        this.grade = grade;
    }

    /**
     * Returns the title of the book.
     * @return the title of the book
     */
    public String getTitle() {
        return title;
    }

    /**
     * Returns the grade of the book.
     * @return the grade of the book
     */
    public int getGrade() {
        return grade;
    }

    /**
     * Sets the grade of the book.
     * @param grade the new grade of the book
     */
    public void setGrade(int grade) {
        this.grade = grade;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GradedBook gradedBook = (GradedBook) o;

        return title.equals(gradedBook.title);
    }

    @Override
    public int hashCode() {
        return title.hashCode();
    }

    @Override
    public String toString() {
        return "GradedBook{" +
                "title='" + title + '\'' +
                ", grade=" + grade +
                '}';
    }
}
