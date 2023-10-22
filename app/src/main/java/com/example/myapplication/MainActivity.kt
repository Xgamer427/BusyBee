package com.example.myapplication

import android.content.Context
import android.os.Bundle
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

    private val viewModel: BusTrackerViewModel = BusTrackerViewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        BusDataSimulation().start()



        window.decorView.post {
            btnSelectStopSelection.setOnClickListener {
                supportFragmentManager.beginTransaction().apply {
                    replace(R.id.fragmentContainer, StopSelectionPage())
                    addToBackStack(null)
                    commit()
                }
            }
        }
    }



}