package ch.epfl.sweng;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public final class NewsActivity extends AppCompatActivity {

    /** {@inheritDoc} */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        RecyclerView recyclerView = findViewById(R.id.news_list);
        var adapter = new StoriesAdapter();

        recyclerView.setAdapter(adapter);

        // Display a bunch of stories.
        adapter.submitList(generateStories());
    }

    /** Generates a list of stories. */
    private static List<Story> generateStories() {
        return List.of(
                new Story.Builder().id(0).title("title 0").url("https://epfl.ch").build(),
                new Story.Builder().id(1).title("title 1").url("https://epfl.ch").build(),
                new Story.Builder().id(2).title("title 2").url("https://epfl.ch").build(),
                new Story.Builder().id(3).title("title 3").url("https://epfl.ch").build());
    }
}
