package com.example.myapplication.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.graphics.Color
import androidx.core.app.NotificationCompat
import com.example.myapplication.MainActivity
import com.example.myapplication.R
import BusTrackerNotification

/**
 * Thread responsible for generating and displaying notifications based on BusTrackerNotification data.
 *
 * @param mainActivity The main activity associated with this thread.
 */
class NotificationThread(
    val mainActivity: MainActivity
) : Thread() {

    private val TAG = "Notification"

    /**
     * The main execution logic of the thread, continuously generates and displays notifications.
     */
    override fun run() {
        while (true) {
            val listOfNotifications: List<BusTrackerNotification> = mainActivity.viewModel.getNofiticationNeeded()

            if (!listOfNotifications.isEmpty()) {
                var notifTitle = ""
                var notifText = ""
                if (listOfNotifications.size == 1) {
                    notifTitle = "Get ready to get your Bus at " + listOfNotifications[0].getRealDepartureTime() + "!"
                    notifText = "Get ready and start heading down to ${listOfNotifications[0].stop.name}"
                } else {
                    notifTitle = "Get ready to get your Bus!"
                    notifText += "You have multiple notifications for "
                    listOfNotifications.forEach {
                        notifText += "${it.stop.name}, "
                    }
                    notifText = notifText.subSequence(0, notifText.length - 2).toString()
                }

                val followersChannel: NotificationChannel = NotificationChannel("1", "TestName",
                    NotificationManager.IMPORTANCE_DEFAULT)
                followersChannel.lightColor = Color.GREEN

                val nm: NotificationManager = mainActivity.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                nm.createNotificationChannel(followersChannel)

                var notification = NotificationCompat.Builder(mainActivity.applicationContext, "1")
                    .setSmallIcon(R.drawable.ic_launcher_foreground)
                    .setContentTitle(notifTitle)
                    .setContentText(notifText)
                    .build()

                nm.notify(1, notification)
            }
            mainActivity.viewModel.setNotificationDone(listOfNotifications)
            Thread.sleep(10000)
        }
    }
}
