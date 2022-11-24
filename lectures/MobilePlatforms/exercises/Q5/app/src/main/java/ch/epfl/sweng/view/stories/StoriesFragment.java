package ch.epfl.sweng.view.stories;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import ch.epfl.sweng.R;
import ch.epfl.sweng.presentation.StoriesViewModel;

/** A fragment that displays a list of stories. */
public final class StoriesFragment extends Fragment {

  /** Creates a new {@link StoriesFragment} instance. */
  public static StoriesFragment getInstance() {
    return new StoriesFragment();
  }

  /** {@inheritDoc} */
  @Nullable
  @Override
  public View onCreateView(
      @NonNull LayoutInflater inflater,
      @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    return inflater.inflate(R.layout.fragment_stories, container, false);
  }

  /** {@inheritDoc} */
  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    var toolbar = view.<Toolbar>findViewById(R.id.toolbar);
    var recyclerView = view.<RecyclerView>findViewById(R.id.news_list);
    var viewModel = new ViewModelProvider(this).get(StoriesViewModel.class);
    var adapter = new StoriesAdapter();

    recyclerView.setAdapter(adapter);

    // Observe the changes from the ViewModel and update the adapter on changes.
    viewModel.getTopStories().observe(getViewLifecycleOwner(), adapter::submitList);
    viewModel.refresh(getViewLifecycleOwner());

    // Set up the toolbar listeners.
    toolbar.setOnMenuItemClickListener(
        item -> {
          int itemId = item.getItemId();
          if (itemId == R.id.menu_refresh) {
            viewModel.refresh(getViewLifecycleOwner());
            return true;
          } else if (itemId == R.id.menu_clear) {
            viewModel.clearAll();
            return true;
          }
          return false;
        });
  }
}
