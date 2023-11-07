package com.example.myapplication.data

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import java.time.LocalDateTime


data class BusTrackerUiState(
    val notificationArray: Array<BusTrackerNotification> = arrayOf<BusTrackerNotification>(),
    val currentSetupStop: Stop? = null,
    val currentSetupBusline: Busline? = null,
    val directionArrayAscendant : Boolean? = null,
    val currentSetupDepartureTime: DepartureTime? = null,
    val currentSetupBuffertime: Int = 0,
    val currentSetupAdditionalTime: Int = 0,
) {

    @RequiresApi(Build.VERSION_CODES.O)
    fun getNofiticationNeeded(): List<BusTrackerNotification>{
        val listToReturn : MutableList<BusTrackerNotification> = mutableListOf()
        val currentTimeStamp = LocalDateTime.now()
        Log.d("Leo", currentTimeStamp.toString())
        val currentTimeInDepartureTime = (DepartureTime(currentTimeStamp.hour, currentTimeStamp.minute))

         notificationArray.forEach {
             if(it.getTimeToGetReady() != null && it.getTimeToGetReady()!! < currentTimeInDepartureTime){
                 listToReturn.add(it)
             }
         }
        return listToReturn
    }

}
