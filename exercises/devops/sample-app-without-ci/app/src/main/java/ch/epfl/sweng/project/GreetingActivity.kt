package ch.epfl.sweng.project

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class GreetingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_greeting)
        val intent = intent
        val name = intent.getStringExtra(EXTRA_USER_NAME)
        val textView = findViewById<TextView>(R.id.greetingMessage)
        textView.text = getString(R.string.greeting_message, name)
    }

    companion object {
        const val EXTRA_USER_NAME = "username"
    }
}