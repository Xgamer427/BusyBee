package com.example.myapplication.data

import TimeMachine
import androidx.annotation.RequiresApi
import androidx.lifecycle.MutableLiveData
import java.time.LocalDateTime


data class BusTrackerUiState(

    private val TAG: String = "Notification"
) {
    /*

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

    fun toStringCustome(): String {
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
*/
}