package ch.epfl.sweng.domain.persistence;

import static androidx.room.OnConflictStrategy.REPLACE;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

/** A Data Access Object (DAO) for the stories. */
@Dao
public interface StoriesDao {

  /** Returns the list of currently stored stories. */
  @Query("SELECT * FROM StoryEntity ORDER BY id DESC")
  LiveData<List<StoryEntity>> getAllStories();

  /**
   * Inserts the given stories into the database.
   *
   * @param stories the stories to insert.
   */
  @Insert(onConflict = REPLACE)
  void insertAll(StoryEntity... stories);

  /** Deletes all stories from the database. */
  @Query("DELETE FROM StoryEntity")
  void clear();
}
