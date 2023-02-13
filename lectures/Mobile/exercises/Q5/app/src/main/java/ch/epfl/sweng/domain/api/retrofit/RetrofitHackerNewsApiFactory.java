package ch.epfl.sweng.domain.api.retrofit;

import ch.epfl.sweng.domain.api.HackerNewsApi;
import ch.epfl.sweng.domain.api.HackerNewsApiFactory;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * An implementation of {@link HackerNewsApiFactory} that uses Retrofit to perform the network
 * requests.
 */
public final class RetrofitHackerNewsApiFactory implements HackerNewsApiFactory {

  /** The {@link Retrofit} instance used to create the {@link HackerNewsApi}. */
  private final Retrofit retrofit =
      new Retrofit.Builder()
          .addConverterFactory(GsonConverterFactory.create())
          .addCallAdapterFactory(new LiveDataAdapterFactory())
          .baseUrl(HackerNewsApi.BASE_URL)
          .build();

  /** {@inheritDoc} */
  @Override
  public HackerNewsApi create() {
    return retrofit.create(HackerNewsApi.class);
  }
}
