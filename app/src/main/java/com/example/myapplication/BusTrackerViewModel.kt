package com.example.myapplication

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.data.BusTrackerNotification
import com.example.myapplication.data.Busline
import com.example.myapplication.data.DepartureTime
import com.example.myapplication.data.Stop

class BusTrackerViewModel : ViewModel() {

    var notificationArray: Array<BusTrackerNotification> = arrayOf<BusTrackerNotification>()
    var currentSetupStop: Stop? = null
    var currentSetupBusline: Busline? = null
    var currentSetupDepartureTime: DepartureTime? = null
    var currentSetupBuffertime: MutableLiveData<Int> = MutableLiveData<Int>()
    var currentSetupAdditionalTime: Int = 0
    var currentSetupDirection: Boolean? = null

    fun addToNotificationArray(newNotificationArray: Array<BusTrackerNotification>){
        notificationArray = newNotificationArray
    }

    fun updateCurrentSetupStop(stop: Stop){
        currentSetupStop = stop
    }

    fun updateCurrentSetupDirection(valueOfDirection: Boolean){
        currentSetupDirection = valueOfDirection
    }

    fun updateCurrentSetupBuffertime(bufferTime:Int){
        currentSetupBuffertime.value = bufferTime
    }

    fun updateCurrentSetupAdditionaltime(additionalTime: Int) {
        currentSetupAdditionalTime = additionalTime
    }

    fun updateCurrentDeparturetime(departureTime: DepartureTime){
        currentSetupDepartureTime = departureTime
    }

    fun updateCurrentBusline(busline: Busline){
        currentSetupBusline = busline
    }


    fun addToNotificationArray(busTrackerNotification : BusTrackerNotification) {
        notificationArray.plus(busTrackerNotification)
    }

    fun setNotificationDone(listOfNotitications: List<BusTrackerNotification>) {
        listOfNotitications.forEach {
            it.notificationDone = true
        }
    }

    fun resetCurrentSetup() {
        currentSetupBuffertime.value = 0
    }

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

}