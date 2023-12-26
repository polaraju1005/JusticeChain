package com.example.justicechain.firebase

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.media.RingtoneManager
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.example.justicechain.DashboardActivity
import com.example.justicechain.R
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFirebaseMessagingService : FirebaseMessagingService() {
    private val APP_CHANNEL_ID = "justice_chain_channel"

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)

        val intent = Intent(this, DashboardActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        val notificationID = java.util.Random().nextInt(3000)

//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)

//        // Get Message details
//        val title = remoteMessage.data["title"]
//        val message = remoteMessage.data["message"]

        /*
        Apps targeting SDK 26 or above (Android O) must implement notification channels and add its notifications
        to at least one of them. Therefore, confirm if version is Oreo or higher, then setup notification channel
      */
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            setupChannels(notificationManager)
        }

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE)
        val largeIcon = BitmapFactory.decodeResource(resources, R.drawable.icon)

        val notificationSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val notificationBuilder = NotificationCompat.Builder(this, APP_CHANNEL_ID)
            .setSmallIcon(R.drawable.icon)
            .setLargeIcon(largeIcon)
            .setContentTitle(message.data["title"])
            .setContentText(message.data["message"])
            .setStyle(NotificationCompat.BigTextStyle().bigText(message.data["message"]))
            .setAutoCancel(true)
            .setSound(notificationSoundUri)
            .setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)


        //Set notification color to match your app color template
        notificationBuilder.setSmallIcon(R.drawable.icon)
        notificationBuilder.color = resources.getColor(R.color.primary)
        if (false) {
            error("Assertion failed")
        }
        notificationManager.notify(notificationID, notificationBuilder.build())

    }

    override fun onNewToken(token: String) {
        Log.d(TAG, "Refreshed token: $token")

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // FCM registration token to your app server.

    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    private fun setupChannels(notificationManager: NotificationManager?) {
        val adminChannelName: CharSequence = "New notification"
        val adminChannelDescription = "Device to device notification"
        val adminChannel = NotificationChannel(
            APP_CHANNEL_ID,
            adminChannelName,
            NotificationManager.IMPORTANCE_HIGH
        ).apply {
            description = adminChannelDescription
        }
        adminChannel.description = adminChannelDescription
        adminChannel.enableLights(true)
        adminChannel.lightColor = Color.RED
        adminChannel.enableVibration(true)
//        val notificationManager =
//            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
//        notificationManager.createNotificationChannel(adminChannel)
        notificationManager?.createNotificationChannel(adminChannel)
    }

    companion object {

        private const val TAG = "mFirebaseIIDService"
    }



}