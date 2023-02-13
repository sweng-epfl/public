package ch.epfl.sweng.domain.persistence;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/** An entity representing a story. */
@Entity
public class StoryEntity {

  /** The unique identifier of the story. */
  @PrimaryKey public int id;

  /** The title of the story. Might be null. */
  @ColumnInfo(name = "title")
  public String title;

  /** The url of the story. Might be null. */
  @ColumnInfo(name = "url")
  public String url;
}
