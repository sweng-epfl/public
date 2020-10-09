package callback;

public interface Callback<T> {
  void onSuccess(T value);
  void onError(Exception e);
}
