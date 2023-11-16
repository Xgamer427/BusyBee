package com.example.myapplication

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_overview_notifications.rv_recyclerView

class OverviewNofitication : AppCompatActivity() {

    private var titlesList = mutableListOf<String>()
    private var descList = mutableListOf<String>()
    private var imagesList = mutableListOf<Int>()

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        setContentView(R.layout.fragment_overview_notifications)

        rv_recyclerView.layoutManager = LinearLayoutManager(this)
        rv_recyclerView.adapter = RecyclerAdapter(titlesList, descList, imagesList)
         postToList()
    }

    private fun addToList(title: String, description: String, image: Int) {
        titlesList.add(title)
        descList.add(description)
        imagesList.add(image)
    }

    private fun postToList() {
        for (i in 1..25) {
            addToList("Title $i", "Description $i", R.mipmap.ic_launcher_round)
        }
    }
}