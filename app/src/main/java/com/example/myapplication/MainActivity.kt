package com.example.myapplication

import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.fragment.app.FragmentActivity


class MainActivity : FragmentActivity() {

    private val viewModel: BusTrackerViewModel = BusTrackerViewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        object : Thread() {
            @RequiresApi(Build.VERSION_CODES.O)
            override fun run() {
                viewModel.getNofiticationNeeded()
                var builder = NotificationCompat.Builder(applicationContext)
                    .setSmallIcon(R.drawable.ic_launcher_foreground)
                    .setContentTitle("Get Ready to get your bus")
                    .setContentText("")
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)

                sleep(10000)
            }
        }.start()
    }




}