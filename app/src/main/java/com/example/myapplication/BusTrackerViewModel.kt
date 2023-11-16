package com.example.myapplication

import androidx.lifecycle.ViewModel
import com.example.myapplication.data.BusTrackerNotification
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

    fun updateNotificationArray(newNotificationArray: Array<BusTrackerNotification>){
        _uiState.update { currentState ->
            currentState.copy(
                notificationArray = newNotificationArray
            )
        }
    }

    fun updateCurrentSetupStop(stop: Stop){
        _uiState.update { currentState ->
            currentState.copy(
                currentSetupStop = stop
            )
        }
    }

    fun updateCurrentSetupDirection(valueOfDirection: Boolean){
        _uiState.update { currentState ->
            currentState.copy(
                currentSetupDirection = valueOfDirection
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

    fun updateCurrentDeparturetime(departureTime: DepartureTime){
        _uiState.update { currentState ->
            currentState.copy(
                currentSetupDepartureTime = departureTime
            )
        }
    }

    fun updateCurrentDirection(direction: Boolean){
        _uiState.update { currentState ->
            currentState.copy(
                currentSetupDirection = direction
            )
        }
    }

    fun updateCurrentBusline(busline: Busline){
        _uiState.update { currentState ->
            currentState.copy(
                currentSetupBusline = busline
            )
        }
    }


    fun updateNotificationArray(busTrackerNotification : BusTrackerNotification) {
        _uiState.update { currentState ->
            currentState.copy(
                notificationArray = _uiState.value.notificationArray.plus(busTrackerNotification)
            )
        }
    }

    fun setNotificationDone(listOfNotitications: List<BusTrackerNotification>) {
        listOfNotitications.forEach {
            it.notificationDone = true
        }
    }

}