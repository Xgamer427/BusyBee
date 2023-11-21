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
import androidx.lifecycle.ViewModel
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
                var toShowBufferTime: Int = model.currentSetupBuffertime
                var toShowAditionalTime: Int = model.currentSetupAdditionalTime
                var toShowTitle: String = model.currentSetupTitle

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

        val model = ViewModelProvider(requireActivity())[BusTrackerViewModel::class.java]
        model.currentSetupStop.observe(viewLifecycleOwner
        ) {
            if(it != null){
                btnSelectStopSelection.text = it.name
            }else{
                btnSelectStopSelection.text = "Select"
            }
        }
        model.currentSetupBusline.observe(viewLifecycleOwner){
            if(it != null){
                btnSelectBuslineSelection.text = it.name
            }else{
                btnSelectBuslineSelection.text = "Select"
            }
        }
        model.currentSetupDepartureTime.observe(viewLifecycleOwner){
            Log.d("SelectionPage", it.toString())
            if(it != null){
                btnSelectDeparturetime.text = it.toString()
            }else{
                btnSelectDeparturetime.text = "Select"
            }
        }

        etAdditionalTime.addTextChangedListener {
            if(etAdditionalTime.text.toString() != ""){
                val model = ViewModelProvider(requireActivity())[BusTrackerViewModel::class.java]
                model.currentSetupAdditionalTime = etAdditionalTime.text.toString().toInt()
            }else{
                model.currentSetupAdditionalTime = 0
            }
        }

        etBuffertime.addTextChangedListener {
            val model = ViewModelProvider(requireActivity())[BusTrackerViewModel::class.java]
            if(etBuffertime.text.toString() != ""){
                model.currentSetupBuffertime = etBuffertime.text.toString().toInt()
                Log.d("SelectionPage", "ExecutedListener")
            }else{
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


            if((model.currentSetupTitle != null) &&
                (model.currentSetupStop.value != null) &&
                (model.currentSetupBusline.value != null) &&
                (model.currentSetupDepartureTime.value != null) &&
                (model.currentSetupDirection != null) &&
                (model.currentSetupBuffertime != null)
                //Buffertime is 0 on default, no null check required
            )
            {
                //put the currentstates into a new Notification
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
                Log.d("Notification", model.toStringCustome())
            } else {
                Toast.makeText(context, "Put in every Value", Toast.LENGTH_SHORT).show()
            }

        }
        super.onViewCreated(view, savedInstanceState)
    }
}