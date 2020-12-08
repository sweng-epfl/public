package ch.epfl.sweng.ctf.activities

import android.os.Bundle
import android.view.MenuItem
import android.view.MotionEvent
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SwitchCompat
import androidx.core.content.ContextCompat
import ch.epfl.sweng.ctf.Config
import ch.epfl.sweng.ctf.R
import ch.epfl.sweng.ctf.repositories.ChallengesRepository
import ch.epfl.sweng.ctf.repositories.FlagsRepository
import ch.epfl.sweng.ctf.repositories.FruitsRepository
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import org.koin.android.ext.android.inject


/**
 * Activity for settings
 * Allows user to change config
 *
 * @author Alexandre Chau
 */
class SettingsActivity : AppCompatActivity() {
    private val flagsRepository: FlagsRepository by inject()
    private val challengesRepository: ChallengesRepository by inject()
    private val fruitsRepository: FruitsRepository by inject()
    private val nightModeSwitch: SwitchCompat by lazy { findViewById(R.id.settings_night_mode_switch) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        actionBar?.setDisplayHomeAsUpEnabled(true)

        setupNightModeSwitch()
        setupDebugModeSwitch()
        setupNotificationTrigger()
        setupDeleteDataTrigger()

        findViewById<TextView>(R.id.settings_app_name).text = Config.name
        findViewById<TextView>(R.id.settings_version).text = Config.version
    }

    private fun setupNightModeSwitch() {
        val toggleNightMode = {
            val stateAfter = Config.toggleNightMode(this)
            nightModeSwitch.isChecked = stateAfter == true
        }

        nightModeSwitch.setOnClickListener { toggleNightMode() }
        findViewById<View>(R.id.settings_night_mode).setOnClickListener { toggleNightMode() }
    }

    private fun setupDebugModeSwitch() {
        val debugSwitch = findViewById<SwitchCompat>(R.id.settings_debug_switch)
        debugSwitch.isChecked = Config.debug
        val toggleDebugMode = {
            Config.debug = !Config.debug
            debugSwitch.isChecked = Config.debug
        }
        debugSwitch.setOnClickListener { toggleDebugMode() }
        findViewById<View>(R.id.settings_debug).setOnClickListener { toggleDebugMode() }
    }

    private fun setupNotificationTrigger() {
        val view = findViewById<View>(R.id.settings_notification)
        view.setOnClickListener {
            Config.createNotification(this)
        }
        view.setOnTouchListener(OnTouchChangeBackgroundColor())
    }

    private fun setupDeleteDataTrigger() {
        val view = findViewById<View>(R.id.settings_delete_data)
        view.setOnClickListener {
            // setup confirmation dialog
            MaterialAlertDialogBuilder(this)
                .setTitle(resources.getString(R.string.settings_delete_data))
                .setMessage(resources.getString(R.string.settings_delete_data_alert))
                .setNeutralButton("Cancel") { dialog, _ -> dialog.dismiss() }
                .setPositiveButton("Delete") { dialog, _ ->
                    flagsRepository.deleteAllPersistedData()
                    challengesRepository.notifyDatasetChanged()
                    fruitsRepository.removeAllFruits()
                    dialog.dismiss()
                }
                .show()
        }
        view.setOnTouchListener(OnTouchChangeBackgroundColor())
    }

    override fun onResume() {
        super.onResume()
        // check if night mode is enabled here, as user might leave app, trigger dark mode, and return
        Config.isNightModeEnabled(this)?.let { enabled ->
            nightModeSwitch.isChecked = enabled
        }
    }

    // handle custom homeAsUp back button
    override fun onOptionsItemSelected(item: MenuItem): Boolean = when (item.itemId) {
        android.R.id.home -> {
            // prevent re-render of MainActivity by simply closing SettingsActivity
            this.finish()
            true
        }
        else -> super.onOptionsItemSelected(item)
    }

    /**
     * Helper View.OnTouchListener to change background colors
     */
    inner class OnTouchChangeBackgroundColor() : View.OnTouchListener {
        override fun onTouch(view: View?, event: MotionEvent?): Boolean =
            when (event?.action) {
                MotionEvent.ACTION_DOWN -> {
                    view?.setBackgroundColor(
                        ContextCompat.getColor(this@SettingsActivity, R.color.focused_background)
                    )
                    true
                }
                MotionEvent.ACTION_UP -> {
                    view?.setBackgroundColor(
                        ContextCompat.getColor(this@SettingsActivity, R.color.unfocused_background)
                    )
                    view?.performClick()
                    true
                }
                else -> false
            }
    }
}