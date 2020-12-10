package ch.epfl.sweng.project;

import android.content.Intent;
import android.os.Bundle;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get a reference to the UI components
        EditText nameText = findViewById(R.id.mainName);
        Button goButton = findViewById(R.id.mainGoButton);
        Button weatherButton = findViewById(R.id.weatherButton);

        // Set the behaviour of the button
        goButton.setOnClickListener(clicked -> {
            String name = nameText.getText().toString();
            sayHello(name);
        });

        weatherButton.setOnClickListener(clicked -> {
            startActivity(new Intent(this, WeatherActivity.class));
        });

        // Bonus: trigger the button when the user presses "enter" in the text field
        nameText.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                goButton.callOnClick();
                return true;
            }
            return false;
        });
    }

    /**
     * Opens a new {@link GreetingActivity} saying hello to the specified {@code userName}
     *
     * @param userName the user to greet
     */
    protected void sayHello(String userName) {
        Intent intent = new Intent(this, GreetingActivity.class);
        intent.putExtra(GreetingActivity.EXTRA_USER_NAME, userName);
        startActivity(intent);
    }
}