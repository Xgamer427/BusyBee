package com.example.myapplication

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_selection_page.btnSelectStopSelection
import kotlinx.android.synthetic.main.fragment_stopselection_page.actvStopSelection

/**
 * A simple [Fragment] subclass.
 */
class StopSelectionPage : Fragment() {

    private val stops = arrayOf("stop1", "stop2")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


               // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_stopselection_page, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val con = this.context
        if(con !=null){
            actvStopSelection.setAdapter(
                ArrayAdapter<String>(
                    con,
                    android.R.layout.simple_dropdown_item_1line,
                    stops
                )
            )
        }else{
            Log.d("leo","con is null")
        }
        super.onViewCreated(view, savedInstanceState)
    }
}