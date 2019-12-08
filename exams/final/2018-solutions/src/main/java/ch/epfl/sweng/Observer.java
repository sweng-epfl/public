package ch.epfl.sweng;

// !!!!!!!!!!!!!!!!!!!!!!!
// DO NOT MODIFY THIS FILE
// !!!!!!!!!!!!!!!!!!!!!!!

/**
 * An observer, as per the Observer pattern.
 */
public interface Observer {
  /**
   * Callback invoked when the state of an observable changes.
   * @param observable the observable whose state changed
   * @param arg the argument provided by the observable
   */
  void update(Observable observable, Object arg);
}