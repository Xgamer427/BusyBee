package com.example.myapplication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.data.Stop
import kotlinx.android.synthetic.main.fragment_stopselection_page.actvStopSelection
import kotlinx.android.synthetic.main.fragment_stopselection_page.btnSaveStopSelection

/**
 * A simple [Fragment] subclass.
 */
class StopSelectionPage : Fragment() {


    private var selectedStop: Stop? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_stopselection_page, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        //setup UI element
        btnSaveStopSelection.isEnabled = false

        //Set array for stopSelection auto complete
        val con = this.context
        val stops = BusDataSimulation.getInstance().getStops()
        var stopNameArray = arrayOf<String>()
        stops.forEach {
            stopNameArray += it.name
        }
        if(con !=null){
            actvStopSelection.setAdapter(
                ArrayAdapter(
                    con,
                    android.R.layout.simple_dropdown_item_1line,
                    stops
                )
            )
        }

        actvStopSelection.setOnItemClickListener { parent, view, position, id ->
            selectedStop = parent.getItemAtPosition(position) as Stop
            btnSaveStopSelection.isEnabled = true
        }

        actvStopSelection.addTextChangedListener {//this is executed before itemslickListener so its fine => if something selected button is clickable, if changed afterwards button not clickable
            btnSaveStopSelection.isEnabled = false
        }


        //OnClick for button to save selected stop
        btnSaveStopSelection.setOnClickListener{
            val model = ViewModelProvider(requireActivity())[BusTrackerViewModel::class.java]
            model.updateCurrentSetupStop(selectedStop!!)
            activity?.supportFragmentManager?.popBackStack()
        }

        super.onViewCreated(view, savedInstanceState)
    }
}