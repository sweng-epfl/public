package ch.epfl.sweng.project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    // References to GUI elements
    private EditText mNameText;
    private Button mGoButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // We get a reference to the view objects, using their previously set ID
        mNameText = findViewById(R.id.mainName);
        mGoButton = findViewById(R.id.mainGoButton);

        // We then set the behaviour of the button
        // It's quite short, so we can leave it here, but as soon as it starts
        // doing more complex stuff, it should be moved to a separate method
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