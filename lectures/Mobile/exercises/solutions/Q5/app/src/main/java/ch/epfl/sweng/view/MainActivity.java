package ch.epfl.sweng.view;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import ch.epfl.sweng.R;
import ch.epfl.sweng.view.stories.StoriesFragment;

/** The main entry point of the application. */
public final class MainActivity extends AppCompatActivity {

  /** {@inheritDoc} */
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    if (savedInstanceState == null) {
      getSupportFragmentManager()
          .beginTransaction()
          .add(R.id.container, StoriesFragment.getInstance())
          .commit();
    }
  }
}
