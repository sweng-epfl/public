package ch.epfl.sweng.view;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import ch.epfl.sweng.R;
import ch.epfl.sweng.domain.Story;

/** The main entry point of the application. */
public final class MainActivity extends AppCompatActivity {

  /** {@inheritDoc} */
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    RecyclerView recyclerView = findViewById(R.id.news_list);
    var adapter = new StoriesAdapter();

    recyclerView.setAdapter(adapter);

    // Display a bunch of stories.
    adapter.submitList(generateStories(1000));
  }

  /** Generates a list of stories of size {@code count}. */
  private static List<Story> generateStories(final int count) {
    var result = new ArrayList<Story>();
    for (int i = 0; i < count; i++) {
      result.add(new Story.Builder().id(i).title("title " + i).url("url " + i).build());
    }
    return result;
  }
}
