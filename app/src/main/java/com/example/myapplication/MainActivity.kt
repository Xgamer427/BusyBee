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

        Log.d("uiStateJson","OnStart")
        val pref = PreferenceManager.getDefaultSharedPreferences(applicationContext)
        val jsonStringUIState:String? = pref.getString("uiStateJson", null)
        if(jsonStringUIState != null){
            val loadedUIState: JsonToSaveForPersistance = Gson().fromJson(jsonStringUIState, JsonToSaveForPersistance::class.java)
            viewModel.updateNotificationArray(loadedUIState.listOfNotification)
        }else{
            Log.d("uiStateJson", "loaded json is null")
        }
        viewModel.uiState.value.notificationArray.forEach {
            Log.d("uiStateJson", "Notifications Loaded \n" + it)
        }

    }

    override fun onPause() {
        Log.d("uiStateJson", "OnPause")
        val pref = PreferenceManager.getDefaultSharedPreferences(applicationContext)
        val edit = pref.edit()
        val uiStateJson:String = Gson().toJson(JsonToSaveForPersistance(viewModel.uiState.value.notificationArray))
        edit.putString("uiStateJson", uiStateJson)
        edit.commit()
        Log.d("Notifications Saved", uiStateJson)


        val intent = Intent(this, NotificationService::class.java)
        val viewModelJson:String = Gson().toJson(JsonToSaveForPersistance(viewModel.uiState.value.notificationArray))
        Log.d("ToPutInIntent", viewModelJson)
        intent.putExtra("viewModelUIState",viewModelJson)
        startService(intent)
        super.onPause()
    }
}