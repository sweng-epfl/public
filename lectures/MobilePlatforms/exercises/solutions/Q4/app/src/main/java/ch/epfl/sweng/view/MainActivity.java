package ch.epfl.sweng.view;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import ch.epfl.sweng.R;
import ch.epfl.sweng.presentation.StoriesViewModel;

/** The main entry point of the application. */
public final class MainActivity extends AppCompatActivity {

  /** {@inheritDoc} */
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    RecyclerView recyclerView = findViewById(R.id.news_list);
    var viewModel = new ViewModelProvider(this).get(StoriesViewModel.class);
    var adapter = new StoriesAdapter();

    recyclerView.setAdapter(adapter);

    // Observe the changes from the ViewModel and update the adapter on changes.
    viewModel.getTopStories().observe(this, adapter::submitList);
  }
}
