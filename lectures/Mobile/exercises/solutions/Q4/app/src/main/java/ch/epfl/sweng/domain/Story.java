package ch.epfl.sweng.domain;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.Objects;

/** A domain object representing a story. */
public final class Story {

  private final int id;
  private final String title;
  private final String url;

  private Story(@NonNull Builder builder) {
    this.id = builder.id;
    this.title = builder.title;
    this.url = builder.url;
  }

  /** Returns the id of the story. */
  public int getId() {
    return id;
  }

  /** Returns the title of the story. */
  @Nullable
  public String getTitle() {
    return title;
  }

  /** Returns the URL of the story. */
  @Nullable
  public String getUrl() {
    return url;
  }

  /** {@inheritDoc} */
  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Story story = (Story) o;
    return Objects.equals(id, story.id)
        && Objects.equals(title, story.title)
        && Objects.equals(url, story.url);
  }

  /** {@inheritDoc} */
  @Override
  public int hashCode() {
    return Objects.hash(id, title, url);
  }

  /** A builder for {@link Story} objects. */
  public static final class Builder {

    private int id;
    private String title = "";
    private String url = "";

    /** Sets the id of the story. */
    public Builder id(int id) {
      this.id = id;
      return this;
    }

    /** Sets the title of the story. */
    public Builder title(@Nullable String title) {
      this.title = title;
      return this;
    }

    /** Sets the url of this story. */
    public Builder url(@Nullable String url) {
      this.url = url;
      return this;
    }

    /** Builds a new {@link Story} instance. */
    @NonNull
    public Story build() {
      return new Story(this);
    }
  }
}
