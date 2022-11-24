package ch.epfl.sweng.domain.api.retrofit;

import static ch.epfl.sweng.domain.api.ApiResponse.error;
import static ch.epfl.sweng.domain.api.ApiResponse.success;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import java.lang.reflect.Type;
import java.util.concurrent.atomic.AtomicBoolean;

import ch.epfl.sweng.domain.api.ApiResponse;
import retrofit2.Call;
import retrofit2.CallAdapter;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A Retrofit {@link CallAdapter} that converts the {@link Call} into a {@link LiveData} of {@link
 * ApiResponse}.
 *
 * @param <T> the type of the response
 */
/* package */ final class LiveDataAdapter<T> implements CallAdapter<T, LiveData<ApiResponse<T>>> {

  /** The type of the response. */
  private final Type responseType;

  /**
   * Creates a new {@link LiveDataAdapter}.
   *
   * @param responseType the type of the response
   */
  public LiveDataAdapter(Type responseType) {
    this.responseType = responseType;
  }

  /** {@inheritDoc} */
  @NonNull
  @Override
  public Type responseType() {
    return responseType;
  }

  /** {@inheritDoc} */
  @NonNull
  @Override
  public LiveData<ApiResponse<T>> adapt(@NonNull Call<T> call) {
    return new LiveData<>() {

      // Ensures that the call is only executed once.
      private final AtomicBoolean started = new AtomicBoolean();

      /** {@inheritDoc} */
      @Override
      protected void onActive() {
        super.onActive();
        if (!started.compareAndSet(false, true)) {
          return;
        }
        call.enqueue(
            new Callback<>() {

              /** {@inheritDoc} */
              @Override
              public void onResponse(@NonNull Call<T> call, @NonNull Response<T> response) {
                postValue(success(response.body()));
              }

              /** {@inheritDoc} */
              @Override
              public void onFailure(@NonNull Call<T> call, @NonNull Throwable t) {
                postValue(error(t));
              }
            });
      }
    };
  }
}
