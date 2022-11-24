package ch.epfl.sweng.domain.api;

import java.util.Optional;
import java.util.stream.Stream;

/**
 * A class representing an API response.
 *
 * @param <T> the type of the response body
 */
public final class ApiResponse<T> {

  /**
   * Creates a new successful API response.
   *
   * @param data the response body
   * @param <T> the type of the response body
   * @return a new successful API response
   */
  public static <T> ApiResponse<T> success(T data) {
    return new ApiResponse<>(data, null);
  }

  /**
   * Creates a new failed API response.
   *
   * @param error the error
   * @param <T> the type of the response body
   * @return a new failed API response
   */
  public static <T> ApiResponse<T> error(Throwable error) {
    return new ApiResponse<>(null, error);
  }

  private final T data;
  private final Throwable error;

  private ApiResponse(T data, Throwable error) {
    this.data = data;
    this.error = error;
  }

  /** Returns the response body, as an {@link Optional}. */
  public Optional<T> get() {
    return Optional.ofNullable(data);
  }

  /** Returns a {@link Stream} of the inner value. */
  public Stream<T> stream() {
    if (data == null) {
      return Stream.empty();
    }
    return Stream.of(data);
  }

  /**
   * Returns the error, as an {@link Optional}.
   *
   * @param defaultValue the default value to return if there is no error
   */
  public T getOrElse(T defaultValue) {
    return data != null ? data : defaultValue;
  }
}
