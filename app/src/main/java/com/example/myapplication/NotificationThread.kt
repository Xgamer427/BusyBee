package com.example.myapplication

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.graphics.Color
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat.getSystemService
import com.example.myapplication.data.BusTrackerNotification

class NotificationThread(
    val mainActivity: MainActivity
) : Thread() {

    private val TAG = "Notification"

    override fun run() {

        while(true){
            val listOfNotitications : List<BusTrackerNotification> = mainActivity.viewModel.getNofiticationNeeded()

            if (!listOfNotitications.isEmpty()){
                var notifTitle = ""
                var notifText = ""
                if(listOfNotitications.size==1){
                    notifTitle = "Get ready to get your Bus at "+ listOfNotitications[0].getRealDepartureTime() +"!"
                    notifText = "Get ready and start heading down to ${listOfNotitications[0].stop.name}"
                }else{
                    notifTitle = "Get ready to get your Bus!"
                    notifText += "Your got multiple notifications for "
                    listOfNotitications.forEach {
                        notifText += "${it.stop.name}, "
                    }
                    notifText = notifText.subSequence(0,notifText.length-2).toString()
                }


                val followersChannel: NotificationChannel = NotificationChannel("1", "TestName",
                    NotificationManager.IMPORTANCE_DEFAULT)
                followersChannel.lightColor = Color.GREEN

                val nm: NotificationManager =  mainActivity.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                nm.createNotificationChannel(followersChannel)

                //viewModel.getNofiticationNeeded()
                var notification = NotificationCompat.Builder(mainActivity.applicationContext, "1")
                    .setSmallIcon(R.drawable.ic_launcher_foreground)
                    .setContentTitle(notifTitle)
                    .setContentText(notifText)
                    .build()

                nm.notify(1, notification)
            }
            mainActivity.viewModel.setNotificationDone(listOfNotitications)
            Thread.sleep(10000)
        }
    }
}