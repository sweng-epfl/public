package ch.epfl.sweng.defensive.error.processing.routine.callback;

public interface Callback<T> {
  void onSuccess(T value);
  void onError(Exception e);
}