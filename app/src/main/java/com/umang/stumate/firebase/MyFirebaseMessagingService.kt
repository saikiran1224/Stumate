package com.umang.stumate.firebase

/*
class MyFirebaseMessagingService {
}*/

import android.app.AlarmManager
import com.umang.stumate.R
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.media.RingtoneManager
import android.os.Build
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.umang.stumate.BuildConfig
import com.umang.stumate.auth.AuthenticationActivity
import com.umang.stumate.firebase.ScheduledWorker.Companion.NOTIFICATION_MESSAGE
import com.umang.stumate.firebase.ScheduledWorker.Companion.NOTIFICATION_TITLE
import com.umang.stumate.general.ClassNotesActivity
import com.umang.stumate.general.HomeActivity
import com.umang.stumate.general.ViewNotificationsActivity
import com.umang.stumate.utils.AppPreferences
import com.umang.stumate.utils.NotificationUtil
import com.umang.stumate.utils.isTimeAutomatic
import java.text.SimpleDateFormat
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

        //startActivity(intent)
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        val notificationID = Random().nextInt(3000)

        // Get Message details
        val title = remoteMessage.data["title"]
        val message = remoteMessage.data["message"]



        var intent: Intent? = null

        if(title!!.contains("Uploaded a File!")) {
           intent = Intent(this, ClassNotesActivity::class.java)
        } else {
           intent = Intent(this, ViewNotificationsActivity::class.java)

        }


        /*
        Apps targeting SDK 26 or above (Android O) must implement notification channels and add its notifications
        to at least one of them. Therefore, confirm if version is Oreo or higher, then setup notification channel
      */if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            setupChannels(notificationManager)
        }


        val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT)
        val largeIcon = BitmapFactory.decodeResource(resources, R.drawable.stumate)


        // Can be used for Scheduled Notification
      /*  // Check that 'Automatic Date and Time' settings are turned ON.
        // If it's not turned on, Return
        if (!isTimeAutomatic(applicationContext)) {
            Log.d(TAG, "`Automatic Date and Time` is not enabled")
            return
        }

        // Check whether notification is scheduled or not
        val isScheduled = remoteMessage.data["isScheduled"]?.toBoolean()
        isScheduled?.run {
            if (this) {
                // This is Scheduled Notification, Schedule it
                val scheduledTime = remoteMessage.data["scheduledTime"]
                scheduleAlarm(scheduledTime, remoteMessage.data["title"], remoteMessage.data["message"])
            } else {
                // This is not scheduled notification, show it now
                showNotification(title!!, message!!)
            }
        }
*/

       val notificationSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val notificationBuilder = NotificationCompat.Builder(this, ADMIN_CHANNEL_ID)
            .setSmallIcon(R.drawable.logo_large)
            .setLargeIcon(largeIcon)
            .setContentTitle(remoteMessage.data["title"])
            .setContentText(remoteMessage.data["message"])
            .setStyle(NotificationCompat.BigTextStyle().bigText(remoteMessage.data["message"]))
            .setAutoCancel(true)
            .setSound(notificationSoundUri)
            .setContentIntent(pendingIntent)

        //Set notification color to match your app color template
        notificationBuilder.setSmallIcon(R.drawable.logo_large)
        notificationBuilder.color = resources.getColor(R.color.colorPrimary)
        if (false) {
            error("Assertion failed")
        }
        notificationManager.notify(notificationID, notificationBuilder.build())
   }

/*
    private fun scheduleAlarm(scheduledTimeString: String?, title: String?, message: String?) {

        val alarmMgr = applicationContext.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val alarmIntent =
            Intent(applicationContext, NotificationBroadcastReceiver::class.java).let { intent ->
                intent.putExtra(NOTIFICATION_TITLE, title)
                intent.putExtra(NOTIFICATION_MESSAGE, message)

                Log.d("NotificationData", title + " " + message)

                PendingIntent.getBroadcast(applicationContext, 0, intent, 0)
            }

        Log.d("ScheduledNotification",title + " " + message + " " + scheduledTimeString )

        // Parse Schedule time
        val scheduledTime = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
            .parse(scheduledTimeString!!)

        scheduledTime?.let {

            // With set(), it'll set non repeating one time alarm.

            alarmMgr.set(
                AlarmManager.RTC_WAKEUP,
                it.time,
                alarmIntent
            )

        }
    }
*/

/*
    private fun showNotification(title: String, message: String) {
        NotificationUtil(applicationContext).showNotification(title, message)
    }
*/

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

