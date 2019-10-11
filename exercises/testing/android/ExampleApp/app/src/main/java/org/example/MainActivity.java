package org.example;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.button).setOnClickListener(b -> {
            EditText edit = findViewById(R.id.editText);
            TextView view = findViewById(R.id.textView);
            view.setText("Hi, " + edit.getText());
        });
    }
}
