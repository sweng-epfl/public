package ch.epfl.sweng;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

/** The main entry point of the application. */
public final class MainActivity extends AppCompatActivity {

    /** {@inheritDoc} */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = findViewById(R.id.action_news);

        button.setOnClickListener(
                v -> {
                    var intent = new Intent(MainActivity.this, NewsActivity.class);
                    startActivity(intent);
                });
    }
}
