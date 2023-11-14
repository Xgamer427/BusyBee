package com.example.myapplication.data

import TimeMachine
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import java.time.LocalDateTime


data class BusTrackerUiState(
    val notificationArray: Array<BusTrackerNotification> = arrayOf<BusTrackerNotification>(),
    val currentSetupStop: Stop? = null,
    val currentSetupBusline: Busline? = null,
    val currentSetupDepartureTime: DepartureTime? = null,
    val currentSetupBuffertime: Int = 0,
    val currentSetupAdditionalTime: Int = 0,
    val currentSetupDirection: Boolean? = null
) {

    private val TAG = "Notification"

    fun getNofiticationNeeded(): List<BusTrackerNotification>{
        val listToReturn : MutableList<BusTrackerNotification> = mutableListOf()
        val currentTimeStamp = TimeMachine.now()
        val currentTimeInDepartureTime = (DepartureTime(currentTimeStamp.hour, currentTimeStamp.minute))

        var notificationIndex = 0

         notificationArray.forEach {


             if(!it.notificationDone && it.getTimeToGetReady() != null && it.getTimeToGetReady()!! <= currentTimeInDepartureTime){
                 listToReturn.add(it)
             }
             notificationIndex++
         }
        return listToReturn
    }

    override fun toString(): String {
        var returnString: String = ""

        returnString += "Stop: " + currentSetupStop.toString() + "\n"
        returnString += "Busline: " + currentSetupBusline.toString() + "\n"
        returnString += "Buffertime: $currentSetupBuffertime\n"
        returnString += "Direction: " + currentSetupDirection.toString() + "\n"
        returnString += "Addtime: $currentSetupAdditionalTime\n"
        returnString += "DepTime: " + currentSetupDepartureTime.toString() + "\n"
        returnString += "NotArray: " + notificationArray.joinToString() + "\n"

        return returnString
    }

}