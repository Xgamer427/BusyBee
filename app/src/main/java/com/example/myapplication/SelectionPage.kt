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
import kotlinx.android.synthetic.main.fragment_selection_page.btnSelectStopSelection
import kotlinx.android.synthetic.main.fragment_selection_page.etAdditionalTime
import kotlinx.android.synthetic.main.fragment_selection_page.etBuffertime
import kotlinx.coroutines.launch


class SelectionPage : Fragment() {

    companion object{
        val bustrackerNotificationToBuild: BusTrackerNotification? = null
    }

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
        super.onViewCreated(view, savedInstanceState)
    }


}
