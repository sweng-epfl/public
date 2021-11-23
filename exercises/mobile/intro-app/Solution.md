# Exercise 1: getting started with Android development (Solution)

### Create the main screen

The empty Activity contains a `Constraint Layout`, that defines the placement of components on the screen. In the future, you'll learn how to design proper screens. For now, to prevent the components from disapearing, you can set at least one vertical and one horizontal constraint on each component, in the side panel at the right. Specifically, you can look for the following attributes and the likes, that allow you to position your component relative to others: 

```
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent"
        ...
```

A good practice in Android is to extract all the text constants in another file. To do that easily, open the "Code" view of your screen, and put your cursor on both `android:text=...` fields. Do `Alt + Enter` to show the quick fix toolbox.

### Create a greeting screen

You need to create a new activity (*File* > *New* > *Activity* > *Empty Activity*). The rest is similar to the previous part. Don't forget to give an `id` to your text field!

## Add behavior to your app

In `GreetingActivity.java`:

```java

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

        // Alternatively, if you don't use strings, you'd use this code.
        textView.setText("Hello " + name + "!");
    }
}

```

The corresponding `strings.xml` file:

```xml
<resources>
   <!-- Other strings here -->
   <string name="greeting_message">Hello %s!</string>
</resources>
```

And finally the `MainActivity.java` file:

```java

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
```

Bonus: in `AndroidManifest.xml`:

Replace:

```xml
<activity android:name=".GreetingActivity"></activity>
```

By:

```xml
<!-- Bonus: Set the parent activity so that the user can go back
         to the main activity from the greeting activity -->
<activity
    android:name=".GreetingActivity"
    android:parentActivityName=".MainActivity">
</activity>
```

You can also browse the source code of the project at this step [here](solution-code). 
