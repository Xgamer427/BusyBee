package com.example.myapplication

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.data.Bus
import com.example.myapplication.data.Busline
import kotlinx.android.synthetic.main.fragment_busselection_page.actvBusSelection
import kotlinx.android.synthetic.main.fragment_busselection_page.btnSaveBusSelection

class BusSelectionPage : Fragment() {

    private val busses = arrayOf("bus1", "bus2")
    private var selectedBusline: Busline? = null

     override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_busselection_page, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        //setup UI Element. Default value == false
        btnSaveBusSelection.isEnabled = false

        //Set array for busSelection on auto complete
        val con = this.context
        val busses = BusDataSimulation.getInstance().getBusses()
        var busNameArray = arrayOf<String>()
        busses.forEach {
            busNameArray += it.name
        }
        if(con != null){
            actvBusSelection.setAdapter(
                ArrayAdapter(
                    con,
                    android.R.layout.simple_dropdown_item_1line,
                    busses
                )
            )
        }

        actvBusSelection.setOnItemClickListener { parent, view, position, id ->
            selectedBusline = parent.getItemAtPosition(position) as Busline
            btnSaveBusSelection.isEnabled = true
        }

        actvBusSelection.addTextChangedListener {
            btnSaveBusSelection.isEnabled = false
        }

        //OnClick for button to save selected bus
        btnSaveBusSelection.setOnClickListener {
            Log.d("Tim", "Clicked")
            val model = ViewModelProvider(requireActivity())[BusTrackerViewModel::class.java]
            model.updateCurrentBusline(selectedBusline!!)
            activity?.supportFragmentManager?.popBackStack()
        }

        super.onViewCreated(view, savedInstanceState)
        }
    }
