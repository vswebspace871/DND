package com.example.dnd

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat

class DNDService : Service() {
    private var notificationManager: NotificationManager? = null

    companion object {

        const val CHANNEL_ID = "123456"
        const val NOTIFICATION_ID = "123456"
    }

    override fun onCreate() {
        super.onCreate()

        notificationManager = this.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(
                CHANNEL_ID, "locations",
                NotificationManager.IMPORTANCE_MIN
            )
            notificationManager?.createNotificationChannel(notificationChannel)
        }

        startForeground(NOTIFICATION_ID.toInt(), getNotification())


    }


    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    private fun getNotification(): Notification {
        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("DND Service")
            .setContentText("Service Running")
            .setSmallIcon(R.drawable.repairingservice)
            .setPriority(NotificationCompat.PRIORITY_MIN)
            .setOngoing(true)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notification.setChannelId(CHANNEL_ID)
        }
        return notification.build()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        //stopForeground(STOP_FOREGROUND_DETACH)
        //stopSelf()
    }


}