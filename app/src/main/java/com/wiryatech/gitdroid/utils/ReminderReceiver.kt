package com.wiryatech.gitdroid.utils

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import android.widget.Toast
import androidx.core.app.NotificationCompat
import com.wiryatech.gitdroid.R
import com.wiryatech.gitdroid.ui.activities.MainActivity
import es.dmoral.toasty.Toasty
import java.util.*

class ReminderReceiver : BroadcastReceiver() {

    companion object {
        private const val NOTIFICATION_ID = 1
        private const val CHANNEL_ID = "notification_channel"
        private const val CHANNEL_NAME = "GitDroid Daily Reminder"
    }

    override fun onReceive(context: Context, intent: Intent) {
        // This method is called when the BroadcastReceiver is receiving an Intent broadcast.
        showNotification(context)
    }

    fun setReminder(context: Context, boolean: Boolean) {
        if (boolean) {
            activateReminder(context)
        } else {
            deactivateReminder(context)
        }
    }

    private fun showToast(context: Context, message: Int) {
        Toasty.custom(context, message, R.drawable.ic_round_info_24, R.color.colorAccentSecondary, Toast.LENGTH_SHORT, true, true).show()
    }

    private fun activateReminder(context: Context) {
        val reminderManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, ReminderReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(context, NOTIFICATION_ID, intent, 0)

        val calendar = Calendar.getInstance()
        calendar.apply {
            set(Calendar.HOUR_OF_DAY, 9)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
        }

        reminderManager.setInexactRepeating(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            AlarmManager.INTERVAL_DAY,
            pendingIntent
        )

        showToast(context, R.string.reminder_summary_on)
    }

    private fun deactivateReminder(context: Context) {
        val reminderManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, ReminderReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(context, NOTIFICATION_ID, intent, 0)

        pendingIntent.cancel()
        reminderManager.cancel(pendingIntent)
        showToast(context, R.string.reminder_summary_off)
    }

    private fun showNotification(context: Context) {
        val intent = Intent(context, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(context, 0, intent, 0)
        val largeIcon = BitmapFactory.decodeResource(context.resources, R.drawable.icon)

        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val notificationBuilder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setContentIntent(pendingIntent)
            .setSmallIcon(R.drawable.ic_round_notifications_24)
            .setLargeIcon(largeIcon)
            .setContentTitle(CHANNEL_NAME)
            .setContentText(context.resources.getString(R.string.reminder_notif))

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT)
            channel.description = CHANNEL_NAME
            notificationBuilder.setChannelId(CHANNEL_ID)
            notificationManager.createNotificationChannel(channel)
        }

        val notification = notificationBuilder.build()
        notificationManager.notify(NOTIFICATION_ID, notification)
    }
}
