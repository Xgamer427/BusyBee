package com.example.myapplication

import androidx.lifecycle.ViewModel
import com.example.myapplication.data.Bus
import com.example.myapplication.data.BusTrackerNotification
import com.example.myapplication.data.BusTrackerUiState
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

    @RequiresApi(Build.VERSION_CODES.O)
    fun getNofiticationNeeded(): List<BusTrackerNotification>?{
        val currentTimeStamp = LocalDateTime.now()
        Log.d("Leo", currentTimeStamp.toString())
        if(DepartureTime(currentTimeStamp.hour, currentTimeStamp.minute))

        val currentUiState = _uiState.value
         currentUiState.notificationArray.forEach {
             it.getTimeToGetReady()

         }
        return null
    }

}