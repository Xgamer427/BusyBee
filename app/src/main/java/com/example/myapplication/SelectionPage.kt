package com.example.myapplication

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
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
        viewLifecycleOwner.lifecycleScope.launch {
            // This block will execute when the Fragment is in the STARTED state
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                // Create an instance of a ViewModel called BusTrackerViewModel, which appears to be shared with the activity
                val model = ViewModelProvider(requireActivity())[BusTrackerViewModel::class.java]


                //TODO: Cleaning up the following Code

                // Initialize a default text to show
                var textToShowStop: String = "Select ..."
                var textToShowBusLine: String = "Select ..."
                var textToShowDeparturetime: String = "Select ..."

                // Check if the currentSetupStop has a name, and if so, update textToShow
                if (model.currentSetupStop?.name != null) {
                    textToShowStop = model.currentSetupStop!!.name
                }

                if (model.currentSetupBusline?.name != null) {
                    textToShowBusLine = model.currentSetupBusline!!.name
                }
                if (model.currentSetupDepartureTime?.toString() != null) {
                    textToShowDeparturetime = model.currentSetupDepartureTime.toString()
                }
                // Update the text of UI elements (buttons) based on the collected data
                btnSelectStopSelection.text = textToShowStop
                btnSelectBuslineSelection.text = textToShowBusLine
                btnSelectDeparturetime.text = textToShowDeparturetime

            }
        }
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_selection_page, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {



        btnSelectStopSelection.setOnClickListener {
            activity?.supportFragmentManager?.beginTransaction()?.apply {
                replace(R.id.fragment_container, StopSelectionPage())
                addToBackStack(null)
                commit()
            }
        }

        etBuffertime.addTextChangedListener {
            if(etBuffertime.text.toString() != ""){
                val model = ViewModelProvider(requireActivity())[BusTrackerViewModel::class.java]
                model.updateCurrentSetupBuffertime(etBuffertime.text.toString().toInt())
                Log.d("SelectionPage", "ExecutedListener")
            }
        }

        val model = ViewModelProvider(requireActivity())[BusTrackerViewModel::class.java]
        model.currentSetupBuffertime.observe(viewLifecycleOwner
        ) {
            Log.d("SelectionPage", "ExecutedObserve")
            etBuffertime.setText(it.toString(), TextView.BufferType.EDITABLE)
        }

        etAdditionalTime.addTextChangedListener {
            if(etAdditionalTime.text.toString() != ""){
                val model = ViewModelProvider(requireActivity())[BusTrackerViewModel::class.java]
                model.updateCurrentSetupAdditionaltime(etAdditionalTime.text.toString().toInt())

            }
        }

        btnSelectBuslineSelection.setOnClickListener {
            activity?.supportFragmentManager?.beginTransaction()?.apply {
                replace(R.id.fragment_container, BuslineSelectionPage())
                addToBackStack("buslineSelection")
                commit()
            }
        }

        btnSelectDeparturetime.setOnClickListener {
            activity?.supportFragmentManager?.beginTransaction()?.apply {
                Log.d("Tim", "Clicked")
                replace(R.id.fragment_container, DeparturetimeSelectionPage())
                addToBackStack("DeparturetimeSelection")
                commit()
            }
        }

        btnCreateNotification.setOnClickListener {
            val model = ViewModelProvider(requireActivity())[BusTrackerViewModel::class.java]
            //Check if the user entered everyting correctly
            //Das if Statement muss true sein, wenn alles != null ist


            if((model.currentSetupStop != null) &&
                (model.currentSetupBusline != null) &&
                (model.currentSetupDepartureTime != null) &&
                (model.currentSetupDirection != null) &&
                (model.currentSetupBuffertime.value != null)
                //Buffertime is 0 on default, no null check required
            )
            {
                //put the currentstates into a new Notification
                Toast.makeText(context, "All values entered", Toast.LENGTH_SHORT).show()
                val newNotification = BusTrackerNotification(
                    model.currentSetupStop!!,
                    model.currentSetupBusline!!,
                    model.currentSetupDirection,
                    model.currentSetupDepartureTime!!,
                    model.currentSetupBuffertime.value!!,
                    model.currentSetupAdditionalTime
                )
                model.addToNotificationArray(newNotification)
                model.resetCurrentSetup()
                Log.d("Notification", model.toStringCustome())
            } else {
                Toast.makeText(context, "Put in every Value", Toast.LENGTH_SHORT).show()
            }
        }
        super.onViewCreated(view, savedInstanceState)
    }
}