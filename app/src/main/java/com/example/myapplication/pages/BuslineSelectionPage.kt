package com.example.myapplication.pages

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.RadioButton
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.data.BusTrackerViewModel
import com.example.myapplication.R
import com.example.myapplication.Simulation.BusDataSimulation
import Busline
import Stop
import kotlinx.android.synthetic.main.fragment_buslineselection_page.actvBuslineSelection
import kotlinx.android.synthetic.main.fragment_buslineselection_page.btnSaveBuslineSelection
import kotlinx.android.synthetic.main.fragment_buslineselection_page.rgBuslineSelectionPage

/**
 * Fragment for selecting a bus line and direction.
 */
class BuslineSelectionPage : Fragment() {

    private var selectedBusline: Busline? = null
    private var checkedText: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_buslineselection_page, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        // ViewModel instance for accessing and updating UI state
        val model = ViewModelProvider(requireActivity())[BusTrackerViewModel::class.java]

        // Setup UI Element. Default value == false
        btnSaveBuslineSelection.isEnabled = false

        val con = this.context
        val buslines = BusDataSimulation.getInstance().getBuslines()
        val currentStop: Stop? = model.currentSetupStop.value
        var filteredBuslines: List<Busline> = emptyList()
        var filteredBuslinesNameArray = arrayListOf<String>()

        // Filter the Buslines if a stop is selected
        if (buslines != null && currentStop != null) {
            filteredBuslines = buslines.filter { busline: Busline ->
                busline.stops.contains(currentStop)
            }
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

        // Listener for AutoCompleteTextView item selection
        actvBuslineSelection.setOnItemClickListener { parent, view, position, id ->
            selectedBusline = parent.getItemAtPosition(position) as Busline
            btnSaveBuslineSelection.isEnabled = true
        }

        // Listener for AutoCompleteTextView text change
        actvBuslineSelection.addTextChangedListener {
            btnSaveBuslineSelection.isEnabled = false
        }

        // Update current checked RadioButton
        rgBuslineSelectionPage.setOnCheckedChangeListener { group, checkedId ->
            var checkedRadioButton = view.findViewById<RadioButton>(checkedId)

            // Ensure that a RadioButton is checked
            if (checkedRadioButton != null) {
                checkedText = checkedRadioButton!!.text.toString()
                model.updateCurrentSetupDirection(checkedText.equals("DirectionA"))
            }
        }

        // OnClick for the button to save the selected bus line
        btnSaveBuslineSelection.setOnClickListener {
            if (checkedText == null) {
                // If no direction is selected, default to DirectionA
                val model = ViewModelProvider(requireActivity())[BusTrackerViewModel::class.java]
                model.updateCurrentSetupDirection(true)
            }
            model.updateCurrentBusline(selectedBusline!!)
            activity?.supportFragmentManager?.popBackStack()
        }
        super.onViewCreated(view, savedInstanceState)
    }
}
