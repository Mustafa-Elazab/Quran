package com.mostafa.quran.domain.reminder

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.mostafa.quran.R

class AlarmReceiver : BroadcastReceiver() {

    private var mediaPlayer: MediaPlayer? = null
    override fun onReceive(context: Context, intent: Intent) {

        mediaPlayer?.apply {
            if (isPlaying) {
                stop()
            }
            release()
        }
        mediaPlayer = null

        val prayName = intent.getStringExtra("PrayName")
        var soundUri = R.raw.adzan_makkah
        if(prayName.equals("الفجْر")){
            soundUri = R.raw.adzan_fajr
        }
        createNotification(context, prayName,soundUri)
    }

    private fun createNotification(context: Context, prayName: String?, azanUri: Int) {
        val channelId = "alarm_channel"
        val notificationId = 123

        // Create a notification channel for Android Oreo and higher
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "Alarm Channel",
                NotificationManager.IMPORTANCE_HIGH
            )
            val notificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }

        val soundUri = Uri.parse("android.resource://${context.packageName}/${azanUri}")
        // Build the notification

        // Initialize the MediaPlayer
        mediaPlayer = MediaPlayer.create(context, soundUri)

// Set a completion listener to release the MediaPlayer resources when playback finishes
        mediaPlayer?.setOnCompletionListener {
            mediaPlayer?.release()
            mediaPlayer = null
        }

// Start playback
        mediaPlayer?.start()

        val notificationBuilder = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.drawable.app_icon)
            .setContentTitle("${prayName} صلاة  ")
            .setContentText(" حان الان موعد صلاة $prayName")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .setSound(soundUri)

        // Show the notification
        val notificationManager = NotificationManagerCompat.from(context)
        notificationManager.notify(notificationId, notificationBuilder.build())
    }
}
