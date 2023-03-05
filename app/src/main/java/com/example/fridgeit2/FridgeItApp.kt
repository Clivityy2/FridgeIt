package com.example.fridgeit2

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.util.Log
import com.example.fridgeit2.data.Item

class FridgeItApp: Application() {



    fun createNotificationChannel(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)  {
            val channel = NotificationChannel(
                NotificationService.EXPIRY_CHANNEL_ID,
                "Expiry Reminder",
                NotificationManager.IMPORTANCE_HIGH
            )
            channel.description = "An item is about to expire"
            Log.d("Notification","Notification Channel Created")

            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}