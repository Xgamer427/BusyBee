package com.example.myapplication

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.myapplication.data.Bus
import com.example.myapplication.data.BusTrackerUiState
import kotlinx.android.synthetic.main.fragment_selection_page.btnSelectStopSelection
import kotlinx.android.synthetic.main.fragment_selection_page.tvStopSelection
import kotlinx.coroutines.launch


class MainActivity : FragmentActivity() {

    private val viewModel: BusTrackerViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        object : Thread() {
            @RequiresApi(Build.VERSION_CODES.O)
            override fun run() {
                while(true){
                    Log.d("ViewModel", viewModel.uiState.value.toString())
                    sleep(1000)
                }

            }
        }.start()

    }

}