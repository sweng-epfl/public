package ch.epfl.sweng.defensive.neutral.returned.value.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Result {

  private Iterator<String> iterator;
  private List<Entry> entries = new ArrayList<>();
  
  public Result(Iterator<String> iterator) {
    this.iterator = iterator;
    int number = 0;
    while (number < 8 && iterator.hasNext()) {
      entries.add(new Entry(number++, iterator.next()));
    }
  }

  public Optional<Result> next() {
    if (iterator.hasNext()) {
      return Optional.of(new Result(iterator));
    }
    return Optional.empty();
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    for (Entry entry : entries) {
      builder.append(entry.toString());
      builder.append(System.lineSeparator());
    }
    return builder.toString();
  }
}