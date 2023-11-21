package com.example.myapplication.data

import DepartureTime
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 * ViewModel class for managing bus tracking setup and notifications.
 */
class BusTrackerViewModel : ViewModel() {

    // Array to store bus tracker notifications
    var notificationArray: MutableList<BusTrackerNotification> = mutableListOf<BusTrackerNotification>()

    // LiveData for the current setup stop
    private val _currentSetupStop: MutableLiveData<Stop?> = MutableLiveData<Stop?>()
    val currentSetupStop: LiveData<Stop?>
        get() = _currentSetupStop

    // LiveData for the current setup bus line
    private val _currentSetupBusline: MutableLiveData<Busline?> = MutableLiveData<Busline?>()
    val currentSetupBusline: LiveData<Busline?>
        get() = _currentSetupBusline

    // LiveData for the current setup departure time
    private val _currentSetupDepartureTime: MutableLiveData<DepartureTime?> = MutableLiveData<DepartureTime?>()
    val currentSetupDepartureTime: LiveData<DepartureTime?>
        get() = _currentSetupDepartureTime

    // Buffertime for the current setup
    var currentSetupBuffertime: Int = 0

    // Additional time for the current setup
    var currentSetupAdditionalTime: Int = 0

    // Direction for the current setup
    var currentSetupDirection: Boolean? = null

    // Title for the current setup
    var currentSetupTitle: String = ""

    /**
     * Function to update the notification array.
     *
     * @param newNotificationArray The new array of bus tracker notifications.
     */
    fun addToNotificationArray(newNotificationArray: MutableList<BusTrackerNotification>) {
        notificationArray = newNotificationArray
    }

    /**
     * Function to update the current setup stop.
     *
     * @param stop The new stop for the current setup.
     */
    fun updateCurrentSetupStop(stop: Stop) {
        _currentSetupStop.value = stop
    }

    /**
     * Function to update the current setup direction.
     *
     * @param valueOfDirection The new direction for the current setup.
     */
    fun updateCurrentSetupDirection(valueOfDirection: Boolean) {
        currentSetupDirection = valueOfDirection
    }

    /**
     * Function to update the current setup departure time.
     *
     * @param departureTime The new departure time for the current setup.
     */
    fun updateCurrentDeparturetime(departureTime: DepartureTime) {
        _currentSetupDepartureTime.value = departureTime
    }

    /**
     * Function to update the current setup bus line.
     *
     * @param busline The new bus line for the current setup.
     */
    fun updateCurrentBusline(busline: Busline) {
        _currentSetupBusline.value = busline
    }

    /**
     * Function to add a single notification to the notification array.
     *
     * @param busTrackerNotification The notification to add.
     */
    fun addToNotificationArray(busTrackerNotification: BusTrackerNotification) {
        notificationArray.add(busTrackerNotification)
        var msg = "${notificationArray.size} Notifications after add"
        notificationArray.forEach {
            msg = msg + "\n ${it.toDebugString()}"
        }
        Log.d("uiStateJson", "Loaded " + msg)
    }

    /**
     * Function to mark a list of notifications as done.
     *
     * @param listOfNotifications The list of notifications to mark as done.
     */
    fun setNotificationDone(listOfNotitications: List<BusTrackerNotification>) {
        listOfNotitications.forEach {
            it.notificationDone = true
        }
    }

    /**
     * Function to reset the current setup.
     */
    fun resetCurrentSetup() {
        _currentSetupStop.value = null
        _currentSetupBusline.value = null
        _currentSetupDepartureTime.value = null
    }

    /**
     * Function to get a list of notifications that are needed based on the current setup.
     *
     * @return The list of notifications needed.
     */
    fun getNofiticationNeeded(): List<BusTrackerNotification> {
        val listToReturn: MutableList<BusTrackerNotification> = mutableListOf()
        val currentTimeStamp = TimeMachine.now()
        val currentTimeInDepartureTime = (DepartureTime(currentTimeStamp.hour, currentTimeStamp.minute))

        var notificationIndex = 0

        notificationArray.forEach {

            if (!it.notificationDone && it.getTimeToGetReady() != null && it.getTimeToGetReady()!! <= currentTimeInDepartureTime) {
                listToReturn.add(it)
            }
            notificationIndex++
        }
        return listToReturn
    }

    /**
     * Custom function to convert BusTrackerViewModel object to a string representation.
     *
     * @return The string representation of the BusTrackerViewModel object.
     */
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
