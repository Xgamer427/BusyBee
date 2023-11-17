package com.example.myapplication

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.data.Bus
import com.example.myapplication.data.BusTrackerNotification
import com.example.myapplication.data.Busline
import com.example.myapplication.data.DepartureTime
import com.example.myapplication.data.Stop

class BusTrackerViewModel : ViewModel() {

    var notificationArray: Array<BusTrackerNotification> = arrayOf()


    private val _currentSetupStop: MutableLiveData<Stop?> = MutableLiveData<Stop?>()
    val currentSetupStop: LiveData<Stop?>
        get() = _currentSetupStop

    private val _currentSetupBusline: MutableLiveData<Busline?> = MutableLiveData<Busline?>()
    val currentSetupBusline: LiveData<Busline?>
        get() = _currentSetupBusline

    private val _currentSetupDepartureTime: MutableLiveData<DepartureTime?> = MutableLiveData<DepartureTime?>()
    val currentSetupDepartureTime: LiveData<DepartureTime?>
        get() = _currentSetupDepartureTime

    var currentSetupBuffertime: Int = 0

    var currentSetupAdditionalTime: Int = 0

    var currentSetupDirection: Boolean? = null

    fun addToNotificationArray(newNotificationArray: Array<BusTrackerNotification>){
        notificationArray = newNotificationArray
    }

    fun updateCurrentSetupStop(stop: Stop){
        _currentSetupStop.value = stop
    }

    fun updateCurrentSetupDirection(valueOfDirection: Boolean){
        currentSetupDirection = valueOfDirection
    }

    fun updateCurrentDeparturetime(departureTime: DepartureTime){
        _currentSetupDepartureTime.value = departureTime
    }

    fun updateCurrentBusline(busline: Busline){
        _currentSetupBusline.value = busline
    }


    fun addToNotificationArray(busTrackerNotification : BusTrackerNotification) {
        notificationArray = notificationArray.plus(busTrackerNotification)
    }

    fun setNotificationDone(listOfNotitications: List<BusTrackerNotification>) {
        listOfNotitications.forEach {
            it.notificationDone = true
        }
    }

    fun resetCurrentSetup() {
        _currentSetupStop.value = null
        _currentSetupBusline.value = null
        _currentSetupDepartureTime.value = null
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