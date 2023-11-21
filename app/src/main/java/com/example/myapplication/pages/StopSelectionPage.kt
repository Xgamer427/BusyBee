package com.example.myapplication.pages

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.R
import com.example.myapplication.Simulation.BusDataSimulation
import com.example.myapplication.data.BusTrackerViewModel
import Stop
import kotlinx.android.synthetic.main.fragment_stopselection_page.actvStopSelection
import kotlinx.android.synthetic.main.fragment_stopselection_page.btnSaveStopSelection

/**
 * Fragment for selecting a bus stop from an auto-complete dropdown list.
 */
class StopSelectionPage : Fragment() {

    private var selectedStop: Stop? = null

    /**
     * Overrides the onCreateView method to inflate the fragment layout.
     *
     * @param inflater The LayoutInflater object that can be used to inflate any views in the fragment.
     * @param container If non-null, this is the parent view that the fragment's UI should be attached to.
     * @param savedInstanceState If non-null, this fragment is being re-constructed from a previous saved state.
     * @return The View for the fragment's UI.
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_stopselection_page, container, false)
    }

    /**
     * Overrides the onViewCreated method to set up the UI elements and event listeners.
     *
     * @param view The View returned by onCreateView.
     * @param savedInstanceState If non-null, this fragment is being re-constructed from a previous saved state.
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        // Setup UI element
        btnSaveStopSelection.isEnabled = false

        // Set array for stopSelection auto-complete
        val con = this.context
        val stops = BusDataSimulation.getInstance().getStops()
        var stopNameArray = arrayOf<String>()
        stops.forEach {
            stopNameArray += it.name
        }
        if (con != null) {
            actvStopSelection.setAdapter(
                ArrayAdapter(
                    con,
                    android.R.layout.simple_dropdown_item_1line,
                    stops
                )
            )
        }

        // Event listener for auto-complete dropdown item selection
        actvStopSelection.setOnItemClickListener { parent, view, position, id ->
            selectedStop = parent.getItemAtPosition(position) as Stop
            btnSaveStopSelection.isEnabled = true
        }

        // AddTextChangedListener executed before ItemClickListener, so it's fine
        actvStopSelection.addTextChangedListener {
            btnSaveStopSelection.isEnabled = false
        }

        // OnClick for button to save selected stop
        btnSaveStopSelection.setOnClickListener {
            val model = ViewModelProvider(requireActivity())[BusTrackerViewModel::class.java]
            model.updateCurrentSetupStop(selectedStop!!)
            activity?.supportFragmentManager?.popBackStack()
        }

        super.onViewCreated(view, savedInstanceState)
    }
}
