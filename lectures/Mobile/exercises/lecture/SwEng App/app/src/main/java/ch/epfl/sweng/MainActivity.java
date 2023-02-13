package ch.epfl.sweng;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

/** The main entry point of the application. */
public final class MainActivity extends AppCompatActivity {

    /** {@inheritDoc} */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
