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
        EditText mNameText = findViewById(R.id.mainName);
        Button mGoButton = findViewById(R.id.mainGoButton);

        // Set the behaviour of the button
        mGoButton.setOnClickListener(clicked -> {
            String name = mNameText.getText().toString();
            sayHello(name);
        });

        // Bonus: trigger the button when the user presses "enter" in the text field
        mNameText.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                mGoButton.callOnClick();
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