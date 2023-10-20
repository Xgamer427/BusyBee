package com.example.myapplication

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import kotlinx.android.synthetic.main.fragment_selection_page.btnSelectStopSelection


class MainActivity : FragmentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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