package ch.epfl.sweng.ctf

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.res.Configuration
import android.os.Build
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

/**
 * A global config object
 * Don't do this in a real app
 *
 * @author Alexandre Chau
 */
object Config {
    val NOTIFICATION_CHANNEL_ID = BuildConfig.APPLICATION_ID

    // name of the app
    const val name: String = BuildConfig.APPLICATION_ID
    // version of the app
    const val version: String = BuildConfig.VERSION_NAME
    // whether the app is currently in debug mode
    var debug: Boolean = false

    /**
     * Registers a notification channel into Android
     * Enables the app to trigger system notifications later
     */
    fun registerNotificationChannel(ctx: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = ctx.getString(R.string.app_name)
            val descriptionText = ctx.getString(R.string.notif_channel_desc)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(NOTIFICATION_CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                ctx.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }

    }

    /**
     * Sends a notification on the previously registered notification channel
     * MUST be called after [registerNotificationChannel]
     */
    fun createNotification(ctx: Context) {
        val notif = NotificationCompat.Builder(ctx, NOTIFICATION_CHANNEL_ID)
            .setSmallIcon(R.drawable.icon_flag)
            .setContentTitle(ctx.getString(R.string.notif_title))
            .setContentText(ctx.getString(R.string.notif_text))
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        with(NotificationManagerCompat.from(ctx)) {
            notify(Notifications.FROM_CONFIG.ordinal, notif.build())
        }
    }

    /**
     * Checks whether the OS night mode (= dark mode) setting is enabled for this app
     */
    fun isNightModeEnabled(ctx: Context): Boolean? =
        when (ctx.resources?.configuration?.uiMode?.and(Configuration.UI_MODE_NIGHT_MASK)) {
            Configuration.UI_MODE_NIGHT_YES -> true
            Configuration.UI_MODE_NIGHT_NO -> false
            else -> null
        }

    /**
     * Force-toggles the app-local night mode
     */
    fun toggleNightMode(ctx: Context): Boolean? = isNightModeEnabled(ctx)?.let { enabled ->
        val flag = if (enabled) {
            AppCompatDelegate.MODE_NIGHT_NO
        } else {
            AppCompatDelegate.MODE_NIGHT_YES
        }
        AppCompatDelegate.setDefaultNightMode(flag)
        return !enabled
    }

    /**
     * Enum to assign Int IDs to possible notification types sent by this app
     */
    enum class Notifications {
        FROM_CONFIG,
    }
}