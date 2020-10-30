package model;

public class Course {
  private String code;
  private Lecturer lecturer;

  public Course(String code, Lecturer lecturer) {
    this.code = code;
    this.lecturer = lecturer;
  }

  public String getCode() {
    return code;
  }

  public Lecturer getLecturer() {
    return lecturer;
  }
}