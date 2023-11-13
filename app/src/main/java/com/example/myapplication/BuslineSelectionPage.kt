package com.example.myapplication

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.RadioButton
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.data.BusTrackerUiState
import com.example.myapplication.data.Busline

import com.example.myapplication.data.Stop
import com.example.myapplication.databinding.FragmentBuslineselectionPageBinding
import kotlinx.android.synthetic.main.fragment_buslineselection_page.actvBuslineSelection
import kotlinx.android.synthetic.main.fragment_buslineselection_page.btnSaveBuslineSelection
import kotlinx.android.synthetic.main.fragment_buslineselection_page.rgBuslineSelectionPage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class BuslineSelectionPage : Fragment() {

    private val buslines= arrayOf("busline1", "busline2")
    private var selectedBusline: Busline? = null
    private var checkedText : String? = null

     override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_buslineselection_page, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val model = ViewModelProvider(requireActivity())[BusTrackerViewModel::class.java]

        //setup UI Element. Default value == false
        btnSaveBuslineSelection.isEnabled = false

        //model for Updating and reading the uiState
        val con = this.context
        val buslines = BusDataSimulation.getInstance().getBuslines()
        val currentStop: Stop? = model.uiState.value.currentSetupStop
        var filteredBuslines: List<Busline> = emptyList()
        var filteredBuslinesNameArray = arrayListOf<String>()

        //Filter the Buslines if a stop is selected
        if(buslines != null && currentStop != null){
            filteredBuslines = buslines.filter { busline: Busline ->
                busline.stops.contains(currentStop)
            }
            if(filteredBuslines.isEmpty()) {
                Log.d("Tim", "filteredBuslines.isEmpty()")
            }
        } else {
            Log.d("Tim", "Buslines or currentStop ist null")
        }

        filteredBuslines.forEach {
            filteredBuslinesNameArray += it.name
        }
        if (con != null) {
            actvBuslineSelection.setAdapter(
                ArrayAdapter(
                    con,
                    android.R.layout.simple_dropdown_item_1line,
                    filteredBuslines
                )
            )
        }

        actvBuslineSelection.setOnItemClickListener { parent, view, position, id ->
            selectedBusline = parent.getItemAtPosition(position) as Busline
            btnSaveBuslineSelection.isEnabled = true
        }

        actvBuslineSelection.addTextChangedListener {
            btnSaveBuslineSelection.isEnabled = false
        }

        //Update current checked Button
        rgBuslineSelectionPage.setOnCheckedChangeListener { group, checkedId ->
            var checkedRadioButton = view.findViewById<RadioButton>(checkedId)
            Log.d("Tim", "rgChangeListener")
            //Ensure that a RadioButton is checked
            if (checkedRadioButton != null) {
                checkedText = checkedRadioButton!!.text.toString()
                Log.d("Tim", checkedText!!)
                model.updateCurrentSetupDirection(checkedText.equals("DirectionA"))
                Log.d("ViewModel", model.uiState.value.toString())
            }
        }

        //OnClick for button to save selected bus
        btnSaveBuslineSelection.setOnClickListener {
            Log.d("Tim", "Clicked")
            if(checkedText == null){
                val model = ViewModelProvider(requireActivity())[BusTrackerViewModel::class.java]
                model.updateCurrentSetupDirection(true)
            }
            model.updateCurrentBusline(selectedBusline!!)
            activity?.supportFragmentManager?.popBackStack()
        }
        super.onViewCreated(view, savedInstanceState)
        }
    }
