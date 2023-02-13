package ch.epfl.sweng;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public final class NewsActivity extends AppCompatActivity {

    /** {@inheritDoc} */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
    }
}
