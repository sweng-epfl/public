package model;

public class Joke {
  private String statement;
  
  public Joke(String statement) {
    this.statement = statement;
  }

  @Override
  public String toString() {
    return statement;
  }
}
