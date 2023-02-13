package ch.epfl.sweng.domain.api.retrofit;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import ch.epfl.sweng.domain.api.ApiResponse;
import retrofit2.CallAdapter;
import retrofit2.Retrofit;

/**
 * A Retrofit {@link CallAdapter.Factory} that converts the {@link retrofit2.Call} into a {@link
 * LiveData} of {@link ApiResponse}.
 *
 * @see LiveDataAdapter the associated {@link CallAdapter}
 */
/* package */ final class LiveDataAdapterFactory extends CallAdapter.Factory {

  /** Creates a new {@link LiveDataAdapterFactory}. */
  public LiveDataAdapterFactory() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  public CallAdapter<?, ?> get(
      @NonNull Type returnType, @NonNull Annotation[] annotations, @NonNull Retrofit retrofit) {
    if (getRawType(returnType) != LiveData.class) {
      return null;
    }
    var observableType = getParameterUpperBound(0, (ParameterizedType) returnType);
    var rawObservableType = getRawType(observableType);
    if (rawObservableType != ApiResponse.class) {
      throw new IllegalArgumentException("type must be a resource");
    }
    if (!(observableType instanceof ParameterizedType)) {
      throw new IllegalArgumentException("resource must be parameterized");
    }
    var bodyType = getParameterUpperBound(0, (ParameterizedType) observableType);
    return new LiveDataAdapter<>(bodyType);
  }
}
