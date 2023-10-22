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
import kotlinx.android.synthetic.main.fragment_selection_page.btnSelectStopSelection
import kotlinx.android.synthetic.main.fragment_selection_page.tvStopSelection
import kotlinx.coroutines.launch


class SelectionPage : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                val model = ViewModelProvider(requireActivity())[BusTrackerViewModel::class.java]
                model.uiState.collect {
                    val currentUiState: BusTrackerUiState = model.uiState.value
                    var textToShow:String = "Select ..."
                    if(currentUiState.currentSetupStop?.name != null){
                        textToShow = currentUiState.currentSetupStop.name
                    }
                    btnSelectStopSelection.text = textToShow

                }
            }
        }
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_selection_page, container, false)
    }

    companion object{
        val bustrackerNotificationToBuild: BusTrackerNotification? = null
    }



}
