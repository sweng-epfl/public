package ch.epfl.sweng.domain.api;

import androidx.lifecycle.LiveData;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * A subset of the Hacker News API.
 *
 * @see <a href="https://github.com/HackerNews/API">Hacker News API</a>
 */
public interface HackerNewsApi {

  /** The base URL of the Hacker News API. */
  String BASE_URL = "https://hacker-news.firebaseio.com/v0/";

  /**
   * Returns the top stories.
   *
   * @see <a href="https://github.com/HackerNews/API#new-top-and-best-stories">Hacker News API</a>
   */
  @GET("topstories.json")
  LiveData<ApiResponse<List<Integer>>> topStories();

  /**
   * Returns the new stories.
   *
   * @see <a href="https://github.com/HackerNews/API#items">Hacker News API</a>
   */
  @GET("item/{id}.json")
  LiveData<ApiResponse<HackerNewsItem>> item(@Path("id") int id);
}
