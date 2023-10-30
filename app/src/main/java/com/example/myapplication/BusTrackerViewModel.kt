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

    fun updateCurrentSetupBuffertime(bufferTime:Int){
        _uiState.value = BusTrackerUiState(currentSetupBuffertime = bufferTime)
    }

    fun updateCurrentSetupBus(bus: Bus){
        _uiState.value = BusTrackerUiState(currentSetupBus = bus)
    }

    fun updateCurrentDeparturetime(departureTime: DepartureTime){
        _uiState.value = BusTrackerUiState(currentSetupDepartureTime = departureTime)
    }

}