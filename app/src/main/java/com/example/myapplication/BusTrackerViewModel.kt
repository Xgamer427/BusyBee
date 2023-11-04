package com.example.myapplication

import android.graphics.Path.Direction
import androidx.lifecycle.ViewModel
import com.example.myapplication.data.Bus
import com.example.myapplication.data.BusTrackerUiState
import com.example.myapplication.data.Busline
import com.example.myapplication.data.DepartureTime
import com.example.myapplication.data.Stop
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class BusTrackerViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(BusTrackerUiState())
    val uiState: StateFlow<BusTrackerUiState> = _uiState.asStateFlow()

    fun updateCurrentSetupStop(stop: Stop){
        _uiState.update { currentState ->
            currentState.copy(
                currentSetupStop = stop
            )
        }
    }

    fun updateCurrentSetupBuffertime(bufferTime:Int){
        _uiState.update { currentState ->
            currentState.copy(
                currentSetupBuffertime = bufferTime
            )
        }
    }

    fun updateCurrentSetupAdditionaltime(additionalTime: Int) {
        _uiState.update { currentState ->
            currentState.copy(
                currentSetupAdditionalTime = additionalTime
            )
        }
    }

    fun updateCurrentSetupBus(bus: Bus){
        _uiState.update { currentState ->
            currentState.copy(
                currentSetupBus = bus
            )
        }
    }

    fun updateCurrentDeparturetime(departureTime: DepartureTime){
        _uiState.update { currentState ->
            currentState.copy(
                currentSetupDepartureTime = departureTime
            )
        }
    }

    fun updateCurrentDirection(direction: com.example.myapplication.data.Direction){
        _uiState.update { currentState ->
            currentState.copy(
                currentSetupDirection = direction
            )
        }
    }

    fun updateCurrentBusline(busline: Busline){
        _uiState.value = BusTrackerUiState(currentSetupBusline = busline)
    }

}