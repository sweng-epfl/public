package ch.epfl.sweng;

// !!!!!!!!!!!!!!!!!!!!!!!
// DO NOT MODIFY THIS FILE
// !!!!!!!!!!!!!!!!!!!!!!!

/**
 * An observable, as per the Observer pattern.
 */
public interface Observable {
  /**
   * Registers the specified observer.
   */
  void addObserver(Observer observer);

  /**
   * Notifies the registered observers with the specified argument.
   */
  void notifyObservers(Object arg);
}