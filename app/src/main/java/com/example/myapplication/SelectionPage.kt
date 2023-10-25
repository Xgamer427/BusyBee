package com.example.myapplication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.myapplication.data.BusTrackerNotification
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.myapplication.data.BusTrackerUiState
import kotlinx.android.synthetic.main.fragment_selection_page.btnSelectBusSelection
import kotlinx.android.synthetic.main.fragment_selection_page.btnSelectStopSelection
import kotlinx.android.synthetic.main.fragment_selection_page.tvStopSelection
import kotlinx.coroutines.launch


class SelectionPage : Fragment() {


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

                    // Initialize a default text to show
                    var textToShow: String = "Select ..."

                    // Check if the currentSetupStop has a name, and if so, update textToShow
                    if (currentUiState.currentSetupStop?.name != null) {
                        textToShow = currentUiState.currentSetupStop.name
                    }
                    if(currentUiState.currentSetupBus?.name != null) {
                        textToShow = currentUiState.currentSetupBus.name
                    }

                    // Update the text of UI elements (buttons) based on the collected data
                    btnSelectStopSelection.text = textToShow
                    btnSelectBusSelection.text = textToShow
                }
            }
        }

        // Inflate and return the layout associated with this fragment
        return inflater.inflate(R.layout.fragment_selection_page, container, false)
    }


    companion object{
        val bustrackerNotificationToBuild: BusTrackerNotification? = null
    }



}
