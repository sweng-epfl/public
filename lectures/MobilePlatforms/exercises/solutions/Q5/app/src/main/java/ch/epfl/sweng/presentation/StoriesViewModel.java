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
import androidx.lifecycle.ViewModel;
import androidx.room.Room;

import java.util.Comparator;
import java.util.List;

import ch.epfl.sweng.domain.api.ApiResponse;
import ch.epfl.sweng.domain.api.HackerNewsApi;
import ch.epfl.sweng.domain.api.HackerNewsItem;
import ch.epfl.sweng.domain.api.retrofit.RetrofitHackerNewsApiFactory;
import ch.epfl.sweng.domain.Story;
import ch.epfl.sweng.domain.persistence.StoriesDao;
import ch.epfl.sweng.domain.persistence.StoriesDatabase;
import ch.epfl.sweng.domain.persistence.StoryEntity;

/** A {@link ViewModel} which displays the list of available stories. */
public final class StoriesViewModel extends AndroidViewModel {

  private final StoriesDao dao;
  private final HackerNewsApi api;

  /** Creates a new {@link StoriesViewModel} instance. */
  public StoriesViewModel(Application application) {
    super(application);
    this.api = new RetrofitHackerNewsApiFactory().create();
    this.dao =
        Room.databaseBuilder(application, StoriesDatabase.class, StoriesDatabase.NAME)
            // TODO : You should not do this in a real app.
            .allowMainThreadQueries()
            .build()
            .storiesDao();
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

  private static StoryEntity toEntity(Story story) {
    var entity = new StoryEntity();
    entity.id = story.getId();
    entity.title = story.getTitle();
    entity.url = story.getUrl();
    return entity;
  }

  /** Reloads the stories and populates the database with the new values. */
  public void refresh(LifecycleOwner owner) {
    getTopStoriesLiveData()
        .observe(
            owner,
            s -> {
              var updated = s.stream().map(StoriesViewModel::toEntity).toArray(StoryEntity[]::new);
              dao.insertAll(updated);
            });
  }

  /** Moves all the stories from the database. */
  public void clearAll() {
    dao.clear();
  }

  /** Returns the top stories from HackerNews. */
  @NonNull
  public LiveData<List<Story>> getTopStories() {
    return map(
        dao.getAllStories(),
        stories ->
            stories.stream()
                .map(s -> new Story.Builder().id(s.id).title(s.title).url(s.url).build())
                .collect(toList()));
  }
}
