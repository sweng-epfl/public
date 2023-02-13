package ch.epfl.sweng.presentation;

import static androidx.lifecycle.Transformations.map;
import static androidx.lifecycle.Transformations.switchMap;
import static java.util.stream.Collectors.toList;
import static ch.epfl.sweng.presentation.utils.LiveDataTransformations.combineLatestNonNull;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.Comparator;
import java.util.List;

import ch.epfl.sweng.domain.api.ApiResponse;
import ch.epfl.sweng.domain.api.HackerNewsApi;
import ch.epfl.sweng.domain.api.HackerNewsItem;
import ch.epfl.sweng.domain.api.retrofit.RetrofitHackerNewsApiFactory;
import ch.epfl.sweng.domain.Story;

/** A {@link ViewModel} which displays the list of available stories. */
public final class StoriesViewModel extends AndroidViewModel {

  private final HackerNewsApi api;

  /** Creates a new {@link StoriesViewModel} instance. */
  public StoriesViewModel(Application application) {
    super(application);
    this.api = new RetrofitHackerNewsApiFactory().create();
  }

  /** Returns the top story ids from HackerNews. */
  private LiveData<List<Integer>> getTopItemIdsLiveData() {
    var top = api.topStories();
    return map(top, r -> r.stream().flatMap(List::stream).collect(toList()));
  }

  /** Returns the top items from HackerNews. */
  private LiveData<List<HackerNewsItem>> getTopItemsLiveData() {
    var responses =
        switchMap(
            getTopItemIdsLiveData(),
            ids -> combineLatestNonNull(ids.stream().map(api::item).collect(toList())));
    return map(responses, r -> r.stream().flatMap(ApiResponse::stream).collect(toList()));
  }

  /** Returns the top stories from the HackerNews API. */
  public LiveData<List<Story>> getTopStoriesLiveData() {
    return map(
        getTopItemsLiveData(),
        list ->
            list.stream()
                .map(it -> new Story.Builder().id(it.id).title(it.title).url(it.url).build())
                .sorted(Comparator.comparing(Story::getId).reversed())
                .collect(toList()));
  }

  /** Returns the top stories from HackerNews. */
  @NonNull
  public LiveData<List<Story>> getTopStories() {
    return getTopStoriesLiveData();
  }
}
