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
import com.example.myapplication.data.BusTrackerViewModel
import JsonToSaveForPersistance
import com.example.myapplication.Simulation.BusDataSimulation
import com.example.myapplication.data.BusTrackerNotification
import com.google.gson.Gson
import java.util.Timer
import java.util.TimerTask

/**
 * Service responsible for generating notifications based on BusTrackerNotification data.
 */
class NotificationService() : Service() {
    var timer: Timer? = null
    var timerTask: TimerTask? = null
    val TAG = "NotificationService"
    var Your_X_SECS = 5

    var lastBustrackerUIState: BusTrackerViewModel? = null

    override fun onBind(arg0: Intent?): IBinder? {
        return null
    }

    /**
     * Called when the service is started. Initializes the timer and loads the last known UI state.
     */
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d(TAG, "onStartCommand: got from intent")
        var viewModelJsonString: String? = null
        super.onStartCommand(intent, flags, startId)
        startTimer()

        val pref = PreferenceManager.getDefaultSharedPreferences(applicationContext)
        val jsonStringUIState: String? = pref.getString("uiStateJson", null)
        if (jsonStringUIState != null) {
            val jsonToSaveForPersistance: JsonToSaveForPersistance =
                Gson().fromJson(jsonStringUIState, JsonToSaveForPersistance::class.java)
            var notificationArray: MutableList<BusTrackerNotification> = jsonToSaveForPersistance.listOfNotification
            Log.d(TAG, notificationArray.toString())
            lastBustrackerUIState = BusTrackerViewModel()
            lastBustrackerUIState!!.notificationArray = notificationArray
        }else{
            Log.d("uiStateJson", "Json string loaded in service is null")
        }
        if(lastBustrackerUIState != null){
            var msg = "${lastBustrackerUIState!!.notificationArray.size} Notifications after loaded in service"
            lastBustrackerUIState!!.notificationArray.forEach {
                msg = msg + "\n ${it.toDebugString()}"
            }
            Log.d("uiStateJson", "Loaded " + msg)
        }
        BusDataSimulation.getInstance().getBusses().forEach {
            Log.d("BusDataSimulation in Service", it.myToString())
        }
        return START_STICKY
    }

    /**
     * Called when the service is created. Logs a message.
     */
    override fun onCreate() {
        Log.e(TAG, "onCreate")
    }

    /**
     * Called when the service is destroyed. Stops the timer.
     */
    override fun onDestroy() {
        Log.e(TAG, "onDestroy")
        stoptimertask()
        super.onDestroy()
    }

    // We are going to use a handler to be able to run in our TimerTask
    val handler: Handler = Handler()

    /**
     * Starts the timer to schedule periodic tasks.
     */
    fun startTimer() {
        // Set a new Timer
        timer = Timer()

        // Initialize the TimerTask's job
        initializeTimerTask()

        timer!!.schedule(timerTask, 5000, (Your_X_SECS * 1000).toLong()) //
    }

    /**
     * Stops the timer task.
     */
    fun stoptimertask() {
        // Stop the timer if it's not already null
        if (timer != null) {
            timer!!.cancel()
            timer = null
        }
    }

    /**
     * Initializes the TimerTask for scheduling tasks.
     */
    fun initializeTimerTask() {

        timerTask = object : TimerTask() {
            override fun run() {
                // Use a handler to run a task that generates notifications
                handler.post(Runnable {
                    val listOfNotitications: List<BusTrackerNotification>? =
                        lastBustrackerUIState?.getNofiticationNeeded() ?: null

                    if (listOfNotitications != null) {
                        if (!listOfNotitications.isEmpty()) {
                            var notifTitle = ""
                            var notifText = ""
                            if (listOfNotitications.size == 1) {
                                notifTitle =
                                    "Get ready to get your Bus at " + listOfNotitications[0].getRealDepartureTime() + "!"
                                notifText =
                                    "Get ready and start heading down to ${listOfNotitications[0].stop.name}"
                            } else {
                                notifTitle = "Get ready to get your Bus!"
                                notifText += "You have multiple notifications for "
                                listOfNotitications.forEach {
                                    notifText += "${it.stop.name}, "
                                }
                                notifText = notifText.subSequence(0, notifText.length - 2).toString()
                            }

                            val followersChannel: NotificationChannel =
                                NotificationChannel("1", "TestName", NotificationManager.IMPORTANCE_DEFAULT)
                            followersChannel.lightColor = Color.GREEN

                            val nm: NotificationManager =
                                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                            nm.createNotificationChannel(followersChannel)

                            var notification = NotificationCompat.Builder(applicationContext, "1")
                                .setSmallIcon(R.drawable.ic_launcher_foreground)
                                .setContentTitle(notifTitle)
                                .setContentText(notifText)
                                .build()

                            nm.notify(1, notification)
                            listOfNotitications.forEach {
                                it.notificationDone = true
                                if(lastBustrackerUIState?.notificationArray != null){
                                    lastBustrackerUIState!!.notificationArray.remove(it)
                                }
                            }
                            if (lastBustrackerUIState != null) {
                                lastBustrackerUIState!!.notificationArray.removeAll {
                                    it.notificationDone
                                }
                                val pref = PreferenceManager.getDefaultSharedPreferences(applicationContext)
                                val edit = pref.edit()
                                val uiStateJson: String = Gson().toJson(JsonToSaveForPersistance(
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
