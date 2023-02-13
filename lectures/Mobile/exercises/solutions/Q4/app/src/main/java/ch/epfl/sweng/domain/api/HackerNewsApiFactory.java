package ch.epfl.sweng.domain.api;

/** A factory for creating {@link HackerNewsApi} instances. */
public interface HackerNewsApiFactory {

  /** Creates a new {@link HackerNewsApi} instance. */
  HackerNewsApi create();
}
