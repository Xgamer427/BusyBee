package com.example.myapplication

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import androidx.activity.viewModels
import androidx.core.app.NotificationCompat
import androidx.fragment.app.FragmentActivity
import com.example.myapplication.Simulation.BusDataSimulation
import com.example.myapplication.data.BusTrackerNotification
import com.example.myapplication.data.JsonToSaveForPersistance
import com.google.gson.Gson


class MainActivity : FragmentActivity() {

    val viewModel: BusTrackerViewModel by viewModels()

    private val TAG = "Notification"

    private val notificationThread : Thread = NotificationThread(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        notificationThread.start()

        val pref = PreferenceManager.getDefaultSharedPreferences(applicationContext)
        val jsonStringUIState:String? = pref.getString("uiStateJson", null)
        if(jsonStringUIState != null){
            val loadedUIState: JsonToSaveForPersistance = Gson().fromJson(jsonStringUIState, JsonToSaveForPersistance::class.java)
            viewModel.updateNotificationArray(loadedUIState.listOfNotification)
        }

        var msg = "${viewModel.uiState.value.notificationArray.size} Notifications Loaded"
        viewModel.uiState.value.notificationArray.forEach {
            msg = msg+ "\n $it"
        }
        Log.d("uiStateJson", msg)

    }

    override fun onPause() {
        val pref = PreferenceManager.getDefaultSharedPreferences(applicationContext)
        val edit = pref.edit()
        val uiStateJson:String = Gson().toJson(JsonToSaveForPersistance(viewModel.uiState.value.notificationArray))
        edit.putString("uiStateJson", uiStateJson)
        edit.commit()

        val intent = Intent(this, NotificationService::class.java)
        startService(intent)
        super.onPause()
    }
}