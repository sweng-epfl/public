package ch.epfl.sweng.domain.api;

/**
 * A Hacker News item.
 *
 * @see <a href="https://github.com/HackerNews/API#items">Hacker News API</a>
 */
public class HackerNewsItem {
  public int id;
  public boolean deleted;
  public String type;
  public String by;
  public long time;
  public String text;
  public boolean dead;
  public int parent;
  public int[] kids;
  public String url;
  public int score;
  public String title;
  public int[] parts;
  public int descendants;
}
