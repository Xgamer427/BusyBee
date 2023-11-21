package com.example.myapplication.pages

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.RecyclerAdapter
import com.example.myapplication.data.BusTrackerViewModel

class OverviewFragment : Fragment() {

    private lateinit var busTrackerViewModel : BusTrackerViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_overview_notifications, container, false)

        busTrackerViewModel = ViewModelProvider(requireActivity()).get(BusTrackerViewModel::class.java)

        val recyclerView: RecyclerView = view.findViewById(R.id.rv_recyclerView)

        val notificationArray = busTrackerViewModel.notificationArray.toList()

        //get the Details out of the notificationArray
        val details: MutableList<String> = notificationArray.map { it.toString() }.toMutableList()

        //get the titles of the notificationArray
        val titles: MutableList<String> = notificationArray.map { it.title }.toMutableList()

        // Create an instance of your RecyclerAdapter
        val adapter = RecyclerAdapter(
            titles = titles,
            details = details
        )

        // Set the adapter on the RecyclerView
        recyclerView.adapter = adapter

        // Set a LinearLayoutManager on the RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(context)

        return view
    }
}
