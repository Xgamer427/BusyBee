package com.example.myapplication

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import android.graphics.Path.Direction
import androidx.lifecycle.ViewModel
import com.example.myapplication.data.Bus
import com.example.myapplication.data.BusTrackerNotification
import com.example.myapplication.data.BusTrackerUiState
import com.example.myapplication.data.Busline
import com.example.myapplication.data.DepartureTime
import com.example.myapplication.data.Stop
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.time.LocalDate
import java.time.LocalDateTime

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

}