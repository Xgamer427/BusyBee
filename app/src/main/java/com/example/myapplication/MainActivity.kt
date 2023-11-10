package com.example.myapplication

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.fragment.app.FragmentActivity
import com.example.myapplication.data.Bus
import com.example.myapplication.data.BusTrackerNotification
import com.example.myapplication.data.DepartureTime
import com.google.gson.Gson


class MainActivity : FragmentActivity() {

    private val viewModel: BusTrackerViewModel by viewModels()

    private val TAG = "Notification"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.d("Timers", "onPause")
        val intent = Intent(this, NotificationService::class.java);

        val viewModelJson:String = Gson().toJson(viewModel.uiState.value)
        intent.putExtra("viewModelUIState",viewModelJson)
        startService(intent);

        object : Thread() {
            @RequiresApi(Build.VERSION_CODES.O)
            override fun run() {
                //Setup test NotificationList
                //val currentUIState = viewModel.uiState.value
                viewModel.updateNotificationArray(arrayOf<BusTrackerNotification>(
                    BusTrackerNotification(
                        BusDataSimulation.getInstance().getStops()[0],
                        BusDataSimulation.getInstance().getBuslines()[0],
                        true,
                        DepartureTime(10,10),
                        1,
                        1
                        )
                ))
                while(true){
                    //TODO notification to right points in app
                    val listOfNotitications : List<BusTrackerNotification> = viewModel.uiState.value.getNofiticationNeeded()
                    Log.d(TAG, "NotificationToTrigger: " + listOfNotitications)
                    if (!listOfNotitications.isEmpty()){
                        val followersChannel: NotificationChannel = NotificationChannel("1", "Name",
                            NotificationManager.IMPORTANCE_DEFAULT )
                        followersChannel.lightColor = Color.GREEN

                        val nm: NotificationManager =  getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                        nm.createNotificationChannel(followersChannel)

                        //viewModel.getNofiticationNeeded()
                        var notification = NotificationCompat.Builder(applicationContext, "1")
                            .setSmallIcon(R.drawable.ic_launcher_foreground)
                            .setContentTitle("Testtitle")
                            .setContentText("Test contentText")
                            .build()

                        nm.notify(1, notification)
                    }

                    sleep(10000)
                }

            }
        }.start()
    }

}
