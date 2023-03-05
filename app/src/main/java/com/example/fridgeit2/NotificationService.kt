package com.example.fridgeit2

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationCompat
import com.example.fridgeit2.data.Item
import com.example.fridgeit2.ui.HomeActivity

class NotificationService(
    private val context: Context
) {

    fun showNotification(){
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val notification = NotificationCompat.Builder(context, EXPIRY_CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_fridgeitlogo)
            .setContentTitle("FridgeIt")
            .setContentText("test is about to expire")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .setContentIntent(getContentIntent())
        notificationManager.notify(1, notification.build())
        Log.d("Notification","Notification Shown")
    }

    private fun getContentIntent(): PendingIntent {
        val intent = Intent(context, HomeActivity::class.java)
        return PendingIntent.getActivity(context,0,intent,PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT)
        Log.d("Notification","Notification Shown 2")
    }

    companion object {
        const val EXPIRY_CHANNEL_ID = "expiry_channel"
    }
}