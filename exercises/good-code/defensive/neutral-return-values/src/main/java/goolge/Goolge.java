package goolge;

import java.util.Iterator;

import internet.Internet;
import model.Result;

public class Goolge {

  public static Result search(String[] keywords) {
    Iterator<String> iterator = Internet.find(keywords);
    if (iterator.hasNext()) {
      return new Result(iterator);
    }
    return null;
  }
}