package com.example.myapplication

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_overview_notifications.rv_recyclerView

/**
 * Activity class for displaying an overview of notifications using a RecyclerView.
 */
class OverviewNofitication : AppCompatActivity() {

    // Lists to store data for RecyclerView
    private var titlesList = mutableListOf<String>()
    private var descList = mutableListOf<String>()
    private var imagesList = mutableListOf<Int>()

    /**
     * Overrides the onCreate method to initialize the activity.
     *
     * @param savedInstanceState The saved state of the activity.
     * @param persistentState The persistent state of the activity.
     */
    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        setContentView(R.layout.fragment_overview_notifications)

        // Set up RecyclerView with a LinearLayoutManager
        rv_recyclerView.layoutManager = LinearLayoutManager(this)

        // Set up RecyclerView adapter with the data lists
        rv_recyclerView.adapter = RecyclerAdapter(titlesList, descList, imagesList)

        // Populate data lists and update RecyclerView
        postToList()
    }

    /**
     * Function to add data to the lists for RecyclerView.
     *
     * @param title The title to add.
     * @param description The description to add.
     * @param image The image resource to add.
     */
    private fun addToList(title: String, description: String, image: Int) {
        titlesList.add(title)
        descList.add(description)
        imagesList.add(image)
    }

    /**
     * Function to populate data in the lists for RecyclerView.
     */
    private fun postToList() {
        // Add sample data to the lists for RecyclerView
        for (i in 1..25) {
            addToList("Title $i", "Description $i", R.mipmap.ic_launcher_round)
        }
    }
}
