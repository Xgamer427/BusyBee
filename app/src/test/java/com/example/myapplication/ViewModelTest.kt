package com.example.myapplication

import android.util.Log
import com.example.myapplication.data.BusTrackerNotification
import com.example.myapplication.data.DepartureTime
import org.junit.Test

class ViewModelTest {

private val TAG = "ViewModelTest"

    @Test
    fun TestGetNotification (){
        //Setup Viewmodel for test
        val viewModel: BusTrackerViewModel = BusTrackerViewModel()
        viewModel.updateNotificationArray(arrayOf<BusTrackerNotification>(
            BusTrackerNotification(
                BusDataSimulation.getInstance().getStops()[0],
                BusDataSimulation.getInstance().getBuslines()[0],
                true,
                DepartureTime(10,10),
                1,
                1
            )
        ))
        println("uiState Value: " + viewModel.uiState.value)
        val listOfNotitications : List<BusTrackerNotification> = viewModel.uiState.value.getNofiticationNeeded()
        println("NotificationList " + listOfNotitications)
    }
}