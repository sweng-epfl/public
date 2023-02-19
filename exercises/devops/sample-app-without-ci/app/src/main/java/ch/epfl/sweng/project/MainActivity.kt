package ch.epfl.sweng.project

import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

open class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Get a reference to the UI components
        val nameText = findViewById<EditText>(R.id.mainName)
        val goButton = findViewById<Button>(R.id.mainGoButton)
        val weatherButton = findViewById<Button>(R.id.weatherButton)

        // Set the behaviour of the button
        goButton.setOnClickListener {
            val name = nameText.text.toString()
            sayHello(name)
        }
        weatherButton.setOnClickListener {
            startActivity(
                Intent(
                    this,
                    WeatherActivity::class.java
                )
            )
        }

        // Bonus: trigger the button when the user presses "enter" in the text field
        nameText.setOnEditorActionListener { _: TextView?, actionId: Int, _: KeyEvent? ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                goButton.callOnClick()
                return@setOnEditorActionListener true
            }
            false
        }
    }

    /**
     * Opens a new [GreetingActivity] saying hello to the specified `userName`
     *
     * @param userName the user to greet
     */
    private fun sayHello(userName: String?) {
        val intent = Intent(this, GreetingActivity::class.java)
        intent.putExtra(GreetingActivity.EXTRA_USER_NAME, userName)
        startActivity(intent)
    }
}