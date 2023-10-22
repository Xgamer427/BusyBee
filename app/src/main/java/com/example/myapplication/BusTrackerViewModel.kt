package com.example.myapplication

import androidx.lifecycle.ViewModel
import com.example.myapplication.data.BusTrackerUiState
import com.example.myapplication.data.Stop
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class BusTrackerViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(BusTrackerUiState())
    val uiState: StateFlow<BusTrackerUiState> = _uiState.asStateFlow()

    fun updateCurrentSetupStop(stop: Stop){
        _uiState.value = BusTrackerUiState(currentSetupStop = stop)
    }

}