package com.example.myapplication

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.myapplication.data.BusTrackerNotification
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.myapplication.data.BusTrackerUiState
import kotlinx.android.synthetic.main.fragment_selection_page.btnCreateNotification
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

        btnCreateNotification.setOnClickListener {
            val model = ViewModelProvider(requireActivity())[BusTrackerViewModel::class.java]
            //Check if the user entered everyting correctly
            //Das if Statement muss true sein, wenn alles != null ist

            Log.d("Tim" ,(model.uiState.value.currentSetupDirection != null).toString() )
            if((model.uiState.value.currentSetupStop != null) &&
                (model.uiState.value.currentSetupBusline != null) &&
                (model.uiState.value.currentSetupDepartureTime != null) &&
                (model.uiState.value.currentSetupDirection != null)
                //Buffertime is 0 on default, no null check required
            )
            {
                //put the currentstates into a new Notification
                Toast.makeText(context, "All values entered", Toast.LENGTH_SHORT).show()
                val newNotification = BusTrackerNotification(
                    model.uiState.value.currentSetupStop!!,
                    model.uiState.value.currentSetupBusline!!,
                    model.uiState.value.currentSetupDirection,
                    model.uiState.value.currentSetupDepartureTime!!,
                    model.uiState.value.currentSetupBuffertime,
                    model.uiState.value.currentSetupAdditionalTime
                )
                model.updateNotificationArray(newNotification)
                Log.d("Tim", model.uiState.value.toString())
            } else {
                Toast.makeText(context, "Put in every Value", Toast.LENGTH_SHORT).show()
            }
        }
        super.onViewCreated(view, savedInstanceState)
    }
}