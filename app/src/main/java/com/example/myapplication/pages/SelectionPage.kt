package com.example.myapplication.pages

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
import com.example.myapplication.R
import com.example.myapplication.data.BusTrackerViewModel
import kotlinx.android.synthetic.main.fragment_selection_page.btnCreateNotification
import kotlinx.android.synthetic.main.fragment_selection_page.btnSelectBuslineSelection
import kotlinx.android.synthetic.main.fragment_selection_page.btnSelectDeparturetime
import kotlinx.android.synthetic.main.fragment_selection_page.btnSelectStopSelection
import kotlinx.android.synthetic.main.fragment_selection_page.etAdditionalTime
import kotlinx.android.synthetic.main.fragment_selection_page.etBuffertime
import kotlinx.android.synthetic.main.fragment_selection_page.etNotificationtitle
import kotlinx.coroutines.launch

/**
 * Fragment for user input and selection of bus tracking parameters.
 */
class SelectionPage : Fragment() {

    companion object {
        // Static property for building BusTrackerNotification, not used in the code
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

                // Initialize default text to show
                var textToShowStop: String = "Select ..."
                var textToShowBusLine: String = "Select ..."
                var textToShowDeparturetime: String = "Select ..."
                var toShowBufferTime: Int = model.currentSetupBuffertime
                var toShowAditionalTime: Int = model.currentSetupAdditionalTime
                var toShowTitle:String = model.currentSetupTitle

                // Check if the currentSetupStop has a name, and if so, update textToShow
                if (model.currentSetupStop.value?.name != null) {
                    textToShowStop = model.currentSetupStop.value!!.name
                }

                if (model.currentSetupBusline.value?.name != null) {
                    textToShowBusLine = model.currentSetupBusline.value!!.name
                }
                if (model.currentSetupDepartureTime.value != null) {
                    textToShowDeparturetime = model.currentSetupDepartureTime.value.toString()
                }
                // Update the text of UI elements (buttons) based on the collected data
                btnSelectStopSelection.text = textToShowStop
                btnSelectBuslineSelection.text = textToShowBusLine
                btnSelectDeparturetime.text = textToShowDeparturetime
                etBuffertime.setText(toShowBufferTime.toString(), TextView.BufferType.EDITABLE)
                etAdditionalTime.setText(toShowAditionalTime.toString(), TextView.BufferType.EDITABLE)
                etNotificationtitle.setText(toShowTitle, TextView.BufferType.EDITABLE)
                if(model.currentSetupStop.value == null ){
                    btnSelectBuslineSelection.isEnabled = false
                }
            }
        }
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_selection_page, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        // Button click listener to navigate to the StopSelectionPage fragment
        btnSelectStopSelection.setOnClickListener {
            activity?.supportFragmentManager?.beginTransaction()?.apply {
                replace(R.id.fragment_container, StopSelectionPage())
                addToBackStack(null)
                commit()
            }
        }

        // ViewModel observer for currentSetupStop to update the button text
        val model = ViewModelProvider(requireActivity())[BusTrackerViewModel::class.java]
        model.currentSetupStop.observe(viewLifecycleOwner) {
            if(it != null){
                btnSelectStopSelection.text = it.name
            }else{
                btnSelectStopSelection.text = "Select"
            }
        }

        // ViewModel observer for currentSetupBusline to update the button text
        model.currentSetupBusline.observe(viewLifecycleOwner){
            if(it != null){
                btnSelectBuslineSelection.text = it.name
            }else{
                btnSelectBuslineSelection.text = "Select"
            }
        }

        // ViewModel observer for currentSetupDepartureTime to update the button text
        model.currentSetupDepartureTime.observe(viewLifecycleOwner){
            Log.d("SelectionPage", it.toString())
            if(it != null){
                btnSelectDeparturetime.text = it.toString()
            }else{
                btnSelectDeparturetime.text = "Select"
            }
        }

        // AddTextChangedListener for etAdditionalTime to update ViewModel when text changes
        etAdditionalTime.addTextChangedListener {
            if(etAdditionalTime.text.toString() != ""){
                val model = ViewModelProvider(requireActivity())[BusTrackerViewModel::class.java]
                model.currentSetupAdditionalTime = etAdditionalTime.text.toString().toInt()
            }else{
                model.currentSetupAdditionalTime = 0
            }
        }

        // AddTextChangedListener for etBuffertime to update ViewModel when text changes
        etBuffertime.addTextChangedListener {
            val model = ViewModelProvider(requireActivity())[BusTrackerViewModel::class.java]
            if (etBuffertime.text.toString() != "") {
                model.currentSetupBuffertime = etBuffertime.text.toString().toInt()
                Log.d("SelectionPage", "ExecutedListener")
            } else {
                model.currentSetupBuffertime = 0
            }
        }

        etNotificationtitle.addTextChangedListener {

            if(etNotificationtitle.text.toString() != "") {
                val model = ViewModelProvider(requireActivity())[BusTrackerViewModel::class.java]
                model.currentSetupTitle = etNotificationtitle.text.toString()
            } else {
                model.currentSetupTitle = "No Title"
            }
        }

        btnSelectBuslineSelection.setOnClickListener {
            activity?.supportFragmentManager?.beginTransaction()?.apply {
                replace(R.id.fragment_container, BuslineSelectionPage())
                addToBackStack("buslineSelection")
                commit()
            }
        }

        // Button click listener to navigate to the DeparturetimeSelectionPage fragment
        btnSelectDeparturetime.setOnClickListener {
            activity?.supportFragmentManager?.beginTransaction()?.apply {
                Log.d("Tim", "Clicked")
                replace(R.id.fragment_container, DeparturetimeSelectionPage())
                addToBackStack("DeparturetimeSelection")
                commit()
            }
        }

        // Button click listener to create a new notification and update ViewModel
        btnCreateNotification.setOnClickListener {
            val model = ViewModelProvider(requireActivity())[BusTrackerViewModel::class.java]

            // Check if the user entered everything correctly
            // The if statement must be true if everything is not null

            if((model.currentSetupTitle != null) &&
                (model.currentSetupStop.value != null) &&
                (model.currentSetupBusline.value != null) &&
                (model.currentSetupDepartureTime.value != null) &&
                (model.currentSetupDirection != null) &&
                (model.currentSetupBuffertime != null)
            // Buffertime is 0 by default, no null check required
            ) {
                // Put the current states into a new Notification
                Toast.makeText(context, "Notification Saved", Toast.LENGTH_SHORT).show()
                val newNotification = BusTrackerNotification(
                    model.currentSetupTitle!!,
                    model.currentSetupStop.value!!,
                    model.currentSetupBusline.value!!,
                    model.currentSetupDirection,
                    model.currentSetupDepartureTime.value!!,
                    model.currentSetupBuffertime!!,
                    model.currentSetupAdditionalTime
                )
                model.addToNotificationArray(newNotification)
                model.resetCurrentSetup()
                etBuffertime.setText("0", TextView.BufferType.EDITABLE)
                etAdditionalTime.setText("0", TextView.BufferType.EDITABLE)

                Log.d("Notification", model.toStringCustome())
            } else {
                Toast.makeText(context, "Put in every Value", Toast.LENGTH_SHORT).show()
            }

        }
        super.onViewCreated(view, savedInstanceState)
    }
}