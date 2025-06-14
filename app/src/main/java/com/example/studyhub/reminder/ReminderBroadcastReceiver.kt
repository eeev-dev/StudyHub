package com.example.studyhub.reminder

import android.Manifest
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import android.content.Intent
import android.content.pm.PackageManager
import android.media.RingtoneManager
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.studyhub.MyApp
import com.example.studyhub.R
import androidx.core.net.toUri

class ReminderBroadcastReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if (context != null) {
            val text = intent?.getStringExtra("text") ?: "Reminder text"
            val id = intent?.getIntExtra("id", 0)
            val url = intent?.getStringExtra("url") ?: ""
            val openUrlIntent = Intent(Intent.ACTION_VIEW, url.toUri())
            val pendingIntent = PendingIntent.getActivity(
                context,
                0,
                openUrlIntent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
            val notificationManager = NotificationManagerCompat.from(context)
            val builder = NotificationCompat.Builder(context, MyApp.channelId)
                .setSmallIcon(R.drawable.calendar)
                .setContentTitle("StudyHub")
                .setContentText(text)
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                return
            }
            notificationManager.notify(id!!, builder.build())
        }
    }
}