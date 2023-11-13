package com.example.myapplication

import android.os.Bundle
import androidx.fragment.app.FragmentActivity


class MainActivity : FragmentActivity() {

    private val viewModel: BusTrackerViewModel = BusTrackerViewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        actionBar?.setDisplayHomeAsUpEnabled(true)
    }

}