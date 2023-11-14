package com.example.myapplication

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.core.app.NotificationCompat
import androidx.fragment.app.FragmentActivity
import com.example.myapplication.Simulation.BusDataSimulation
import com.example.myapplication.Simulation.RealBusDataSimulation
import com.example.myapplication.data.BusTrackerNotification
import com.example.myapplication.data.DepartureTime
import com.google.gson.Gson


class MainActivity : FragmentActivity() {

    private val viewModel: BusTrackerViewModel by viewModels()

    private val TAG = "Notification"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        /*val intent = Intent(this, NotificationService::class.java);

        val viewModelJson:String = Gson().toJson(viewModel.uiState.value)
        intent.putExtra("viewModelUIState",viewModelJson)
        startService(intent);
        */
        object : Thread() {
            override fun run() {
                //Setup test NotificationList
                /*
                viewModel.updateNotificationArray(arrayOf<BusTrackerNotification>(
                    BusTrackerNotification(
                        BusDataSimulation.getInstance().getStops()[0],
                        BusDataSimulation.getInstance().getBuslines()[0],
                        true,
                        DepartureTime(10,10),
                        1,
                        1
                        ),
                    BusTrackerNotification(
                        BusDataSimulation.getInstance().getStops()[0],
                        BusDataSimulation.getInstance().getBuslines()[0],
                        true,
                        DepartureTime(10,10),
                        1,
                        1
                    )
                ))*/
                while(true){
                    val listOfNotitications : List<BusTrackerNotification> = viewModel.uiState.value.getNofiticationNeeded()
                    Log.d(TAG, "NotificationToTrigger: " + listOfNotitications)
                    Log.d(TAG, "Bus0 " + BusDataSimulation.getBusses()[0].myToString())
                    if (!listOfNotitications.isEmpty()){
                        val notifTitle = "Get ready to get your Bus!"
                        var notifText = ""
                        if(listOfNotitications.size==1){
                            notifText = "Get ready and start heading down to ${listOfNotitications[0].stop.name}"
                        }else{
                            notifText += "Your got multiple notifications for "
                            listOfNotitications.forEach {
                                notifText += "${it.stop.name}, "
                            }
                            notifText = notifText.subSequence(0,notifText.length-2).toString()
                        }


                        val followersChannel: NotificationChannel = NotificationChannel("1", "Name",
                            NotificationManager.IMPORTANCE_DEFAULT)
                        followersChannel.lightColor = Color.GREEN

                        val nm: NotificationManager =  getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                        nm.createNotificationChannel(followersChannel)

                        //viewModel.getNofiticationNeeded()
                        var notification = NotificationCompat.Builder(applicationContext, "1")
                            .setSmallIcon(R.drawable.ic_launcher_foreground)
                            .setContentTitle(notifTitle)
                            .setContentText(notifText)
                            .build()

                        nm.notify(1, notification)
                    }
                    viewModel.setNotificationDone(listOfNotitications)
                    sleep(10000)
                }
            }
        }.start()
    }
}