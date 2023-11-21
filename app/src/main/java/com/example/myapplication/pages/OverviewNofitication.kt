package com.example.myapplication

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_overview_notifications.rv_recyclerView

class OverviewNofitication : AppCompatActivity() {

    //Datastorages for the Cards
    private var titlesList = mutableListOf<String>()
    private var descriptionList = mutableListOf<String>()
    private var imagesList = mutableListOf<Int>()

    //For the deleting an entry
    lateinit var overviewRecyclerView: RecyclerView
    lateinit var overviewAdapter: RecyclerAdapter


    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        setContentView(R.layout.fragment_overview_notifications)

        rv_recyclerView.layoutManager = LinearLayoutManager(this)
        rv_recyclerView.adapter = RecyclerAdapter(titlesList, descriptionList)

         postToList()
    }

    //Add one Item to the List
    private fun addToList(title: String, description: String, image: Int) {
        titlesList.add(title)
        descriptionList.add(description)
        imagesList.add(image)
    }

    //Go through the array and add everything which can be found to the list
    private fun postToList() {
        for (i in 1..25) {
            addToList("Title $i", "Description $i", R.mipmap.ic_launcher_round)
        }
    }
}