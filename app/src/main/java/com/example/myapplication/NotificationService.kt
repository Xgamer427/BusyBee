package com.example.myapplication

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Handler
import android.os.IBinder
import android.preference.PreferenceManager
import android.util.Log
import androidx.core.app.NotificationCompat
import com.example.myapplication.data.BusTrackerNotification
import com.example.myapplication.data.BusTrackerUiState
import com.example.myapplication.data.JsonToSaveForPersistance
import com.google.gson.Gson
import java.util.Timer
import java.util.TimerTask


class NotificationService() : Service() {
    var timer: Timer? = null
    var timerTask: TimerTask? = null
    val TAG = "NotificationService"
    var Your_X_SECS = 5

    var lastBustrackerUIState : BusTrackerUiState? = null


    override fun onBind(arg0: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.e(TAG, "onStartCommand: got from intent")
        var viewModelJsonString : String? = null
        super.onStartCommand(intent, flags, startId)
        startTimer()

        val pref = PreferenceManager.getDefaultSharedPreferences(applicationContext)
        val jsonStringUIState:String? = pref.getString("uiStateJson", null)


        viewModelJsonString = intent?.getStringExtra("viewModelUIState")
        if(viewModelJsonString == null){

        }
        val loadedNotificationArray : JsonToSaveForPersistance = Gson().fromJson(viewModelJsonString, JsonToSaveForPersistance::class.java)
        Log.e(TAG, loadedNotificationArray.toString())
        var notificationArray : Array<BusTrackerNotification> = loadedNotificationArray.listOfNotification
        lastBustrackerUIState = BusTrackerUiState(notificationArray = notificationArray)
        return START_STICKY
    }

    override fun onCreate() {
        Log.e(TAG, "onCreate")

    }

    override fun onDestroy() {
        Log.e(TAG, "onDestroy")
        stoptimertask()
        super.onDestroy()
    }

    //we are going to use a handler to be able to run in our TimerTask
    val handler: Handler = Handler()
    fun startTimer() {
        //set a new Timer
        timer = Timer()

        //initialize the TimerTask's job
        initializeTimerTask()

        //schedule the timer, after the first 5000ms the TimerTask will run every 10000ms
        timer!!.schedule(timerTask, 5000, (Your_X_SECS * 1000).toLong()) //
        //timer.schedule(timerTask, 5000,1000); //
    }

    fun stoptimertask() {
        //stop the timer, if it's not already null
        if (timer != null) {
            timer!!.cancel()
            timer = null
        }
    }

    fun initializeTimerTask() {

        timerTask = object : TimerTask() {
            override fun run() {
                //use a handler to run a toast that shows the current timestamp
                handler.post(Runnable { //TODO CALL NOTIFICATION FUNC
                    val listOfNotitications : List<BusTrackerNotification>? =
                        lastBustrackerUIState?.getNofiticationNeeded() ?: null

                    if (listOfNotitications != null) {
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

                            val nm: NotificationManager =  getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                            nm.createNotificationChannel(followersChannel)

                            var notification = NotificationCompat.Builder(applicationContext, "1")
                                .setSmallIcon(R.drawable.ic_launcher_foreground)
                                .setContentTitle(notifTitle)
                                .setContentText(notifText)
                                .build()

                            nm.notify(1, notification)
                            listOfNotitications.forEach {
                                it.notificationDone = true
                            }
                            if(lastBustrackerUIState != null){
                                val pref = PreferenceManager.getDefaultSharedPreferences(applicationContext)
                                val edit = pref.edit()
                                val uiStateJson:String = Gson().toJson(JsonToSaveForPersistance(
                                    lastBustrackerUIState!!.notificationArray))

                                edit.putString("uiStateJson", uiStateJson)
                                edit.commit()
                            }

                        }
                    }
                })
            }
        }
    }
}