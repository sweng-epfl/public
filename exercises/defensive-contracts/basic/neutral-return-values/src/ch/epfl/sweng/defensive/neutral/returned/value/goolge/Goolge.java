package ch.epfl.sweng.defensive.neutral.returned.value.goolge;

import java.util.Iterator;

import ch.epfl.sweng.defensive.neutral.returned.value.internet.Internet;
import ch.epfl.sweng.defensive.neutral.returned.value.model.Result;

public class Goolge {

  public static Result search(String[] keywords) {
    Iterator<String> iterator = Internet.find(keywords);
    if (iterator.hasNext()) {
      return new Result(iterator);
    }
    return null;
  }
}