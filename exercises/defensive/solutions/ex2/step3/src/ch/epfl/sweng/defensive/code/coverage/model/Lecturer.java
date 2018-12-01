package ch.epfl.sweng.defensive.code.coverage.model;

import java.util.Optional;

public class Lecturer {
  private String name;

  public Lecturer(String name) {
    this.name = name;
  }

  public Optional<String> getName() {
    return Optional.ofNullable(name);
  }
}