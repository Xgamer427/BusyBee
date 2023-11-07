package com.example.myapplication

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.myapplication.data.BusTrackerNotification
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.myapplication.data.BusTrackerUiState
import kotlinx.android.synthetic.main.fragment_selection_page.btnSelectBuslineSelection
import kotlinx.android.synthetic.main.fragment_selection_page.btnSelectDeparturetime
import kotlinx.android.synthetic.main.fragment_selection_page.btnSelectStopSelection
import kotlinx.android.synthetic.main.fragment_selection_page.etAdditionalTime
import kotlinx.android.synthetic.main.fragment_selection_page.etBuffertime
import kotlinx.coroutines.launch


class SelectionPage : Fragment() {

    companion object {
        val bustrackerNotificationToBuild: BusTrackerNotification? = null
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Using Kotlin's lifecycleScope to perform asynchronous operations tied to the Fragment's lifecycle
        lifecycleScope.launch {
            // This block will execute when the Fragment is in the STARTED state
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                // Create an instance of a ViewModel called BusTrackerViewModel, which appears to be shared with the activity
                val model = ViewModelProvider(requireActivity())[BusTrackerViewModel::class.java]

                // Collect and observe changes in the ViewModel's UI state
                model.uiState.collect {
                    // Retrieve the current UI state from the ViewModel
                    val currentUiState: BusTrackerUiState = model.uiState.value

                    //TODO: Cleaning up the following Code

                    // Initialize a default text to show
                    var textToShowStop: String = "Select ..."
                    var textToShowBusLine: String = "Select ..."
                    var textToShowDeparturetime: String = "Select ..."

                    // Check if the currentSetupStop has a name, and if so, update textToShow
                    if (currentUiState.currentSetupStop?.name != null) {
                        textToShowStop = currentUiState.currentSetupStop.name
                    }

                    if (currentUiState.currentSetupBusline?.name != null) {
                        textToShowBusLine = currentUiState.currentSetupBusline.name
                    }
                    if (currentUiState.currentSetupDepartureTime?.toString() != null) {
                        textToShowDeparturetime =
                            currentUiState.currentSetupDepartureTime.toString()
                    }
                    // Update the text of UI elements (buttons) based on the collected data
                    btnSelectStopSelection.text = textToShowStop
                    btnSelectBuslineSelection.text = textToShowBusLine
                    btnSelectDeparturetime.text = textToShowDeparturetime
                }
            }
        }
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_selection_page, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {



        btnSelectStopSelection.setOnClickListener {
            activity?.supportFragmentManager?.beginTransaction()?.apply {
                replace(R.id.fragmentContainer, StopSelectionPage())
                addToBackStack(null)
                commit()
            }
        }

        etBuffertime.addTextChangedListener {
            if(etBuffertime.text.toString() != ""){
                val model = ViewModelProvider(requireActivity())[BusTrackerViewModel::class.java]
                model.updateCurrentSetupBuffertime(etBuffertime.text.toString().toInt())
                Log.d("ViewModel", model.uiState.value.toString())
            }
        }

        etAdditionalTime.addTextChangedListener {
            if(etAdditionalTime.text.toString() != ""){
                val model = ViewModelProvider(requireActivity())[BusTrackerViewModel::class.java]
                model.updateCurrentSetupAdditionaltime(etAdditionalTime.text.toString().toInt())
                Log.d("ViewModel", model.uiState.value.toString())
            }
        }

        btnSelectBuslineSelection.setOnClickListener {
            activity?.supportFragmentManager?.beginTransaction()?.apply {
                replace(R.id.fragmentContainer, BuslineSelectionPage())
                addToBackStack(null)
                commit()
            }
        }

        btnSelectDeparturetime.setOnClickListener {
            activity?.supportFragmentManager?.beginTransaction()?.apply {
                Log.d("Tim", "Clicked")
                replace(R.id.fragmentContainer, DeparturetimeSelectionPage())
                addToBackStack(null)
                commit()
            }
        }
        super.onViewCreated(view, savedInstanceState)
    }


}
