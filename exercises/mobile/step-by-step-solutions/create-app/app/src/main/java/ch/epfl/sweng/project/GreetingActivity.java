package ch.epfl.sweng.project;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class GreetingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_greeting);
    }
}