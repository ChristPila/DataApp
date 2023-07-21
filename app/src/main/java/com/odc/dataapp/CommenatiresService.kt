package com.odc.dataapp

import android.app.*
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat

class CommenatiresService : Service() {

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    fun startForGroundService() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val CHANNEL_ID = "my_channel_01"
            val channel = NotificationChannel(
                CHANNEL_ID,
                "Channel human readable title",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            (getSystemService(NOTIFICATION_SERVICE) as NotificationManager).createNotificationChannel(
                channel
            )
            val intent = Intent(this, MainActivity::class.java)
            //intent.putExtra("","")
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

            val pendingIntent =
                PendingIntent.getActivity(this, 1, intent, PendingIntent.FLAG_IMMUTABLE)


            val notification: Notification = NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_media_play)
                .setContentTitle("DATA APP")
                .setContentIntent(pendingIntent)
                .setContentText("Recheche de nouveaux messages...").build()
            startForeground(1001, notification)
        }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.i("SERVICE", "DEMARRAGE SERVICE")
        startForGroundService()
        return START_STICKY
    }
}