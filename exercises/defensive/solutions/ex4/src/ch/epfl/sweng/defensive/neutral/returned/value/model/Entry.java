package ch.epfl.sweng.defensive.neutral.returned.value.model;

public class Entry {

  private Integer number;
  private String content;

  public Entry(Integer number, String content) {
    this.number = number;
    this.content = content;
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append(String.format("Entry #%d", number));
    builder.append(System.lineSeparator());
    builder.append(content);
    builder.append(System.lineSeparator());
    return builder.toString();
  }
}