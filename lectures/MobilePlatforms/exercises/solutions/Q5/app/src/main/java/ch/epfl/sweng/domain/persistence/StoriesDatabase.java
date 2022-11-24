package ch.epfl.sweng.domain.persistence;

import androidx.room.Database;
import androidx.room.RoomDatabase;

/** The database containing the stories. */
@Database(
    entities = {StoryEntity.class},
    version = StoriesDatabase.VERSION)
public abstract class StoriesDatabase extends RoomDatabase {

  /**
   * The current version of the database. This value must be incremented whenever the database
   * schema is changed.
   */
  public static final int VERSION = 1;

  /** The name of the database file. */
  public static final String NAME = "stories.sqlite";

  /** Returns a {@link StoriesDao}. */
  public abstract StoriesDao storiesDao();
}
