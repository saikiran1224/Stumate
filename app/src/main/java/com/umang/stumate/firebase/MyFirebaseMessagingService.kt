package com.umang.stumate.firebase

/*
class MyFirebaseMessagingService {
}*/

import com.umang.stumate.R
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.media.RingtoneManager
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.umang.stumate.BuildConfig
import com.umang.stumate.auth.AuthenticationActivity
import com.umang.stumate.utils.AppPreferences
import java.util.*


class MyFirebaseMessagingService : FirebaseMessagingService() {
    private val ADMIN_CHANNEL_ID = "admin_channel"


/*
    override fun onNewToken(s: String) {
        super.onNewToken(s)

      //AppPreferences.init(this)
      //  Toast.makeText(baseContext, "Token Generated",Toast.LENGTH_SHORT).show()

        FirebaseMessaging.getInstance().subscribeToTopic("GMRIT_IT_3_A");

    }
*/

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        val intent = Intent(this, AuthenticationActivity::class.java)
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        val notificationID = Random().nextInt(3000)

        /*
        Apps targeting SDK 26 or above (Android O) must implement notification channels and add its notifications
        to at least one of them. Therefore, confirm if version is Oreo or higher, then setup notification channel
      */if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            setupChannels(notificationManager)
        }
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pendingIntent = PendingIntent.getActivity(
            this, 0, intent,
            PendingIntent.FLAG_ONE_SHOT
        )
        val largeIcon = BitmapFactory.decodeResource(
            resources, R.drawable.stumate
        )
        val notificationSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val notificationBuilder = NotificationCompat.Builder(this, ADMIN_CHANNEL_ID)
            .setSmallIcon(R.drawable.stumate)
            .setLargeIcon(largeIcon)
            .setContentTitle(remoteMessage.data["title"])
            .setContentText(remoteMessage.data["message"])
            .setStyle(
                NotificationCompat.BigTextStyle()
                    .bigText(remoteMessage.data["message"])
            )
            .setAutoCancel(true)
            .setSound(notificationSoundUri)
            .setContentIntent(pendingIntent)
        //Set notification color to match your app color template
        notificationBuilder.setSmallIcon(R.drawable.stumate)
        notificationBuilder.color = resources.getColor(R.color.colorPrimary)
        if (false) {
            error("Assertion failed")
        }
        notificationManager.notify(notificationID, notificationBuilder.build())
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private fun setupChannels(notificationManager: NotificationManager?) {
        val adminChannelName: CharSequence = "New notification"
        val adminChannelDescription = "Device to devie notification"
        val adminChannel: NotificationChannel
        adminChannel = NotificationChannel(
            ADMIN_CHANNEL_ID,
            adminChannelName,
            NotificationManager.IMPORTANCE_HIGH
        )
        adminChannel.description = adminChannelDescription
        adminChannel.enableLights(true)
        adminChannel.lightColor = Color.RED
        adminChannel.enableVibration(true)
        notificationManager?.createNotificationChannel(adminChannel)
    }

    companion object {

        private const val TAG = "mFirebaseIIDService"
       // private const val SUBSCRIBE_TO = "/topics/donateFood"
    }
}

