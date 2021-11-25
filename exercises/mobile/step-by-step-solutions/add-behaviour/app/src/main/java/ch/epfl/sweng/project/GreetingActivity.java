package ch.epfl.sweng.project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class GreetingActivity extends AppCompatActivity {
    // Good practice: put constants in static final fields
    public static final String EXTRA_USER_NAME = "username";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_greeting);

        Intent intent = getIntent(); // Retrieve the intent that created this activity
        String name = intent.getStringExtra(EXTRA_USER_NAME); // Get the `extra` containing the name of the user

        TextView textView = findViewById(R.id.greetingMessage);
        textView.setText(getString(R.string.greeting_message, name)); // Set the text of the field
    }
}