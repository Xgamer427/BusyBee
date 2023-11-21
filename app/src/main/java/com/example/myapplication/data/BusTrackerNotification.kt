package com.example.myapplication.data

import DepartureTime
import android.app.Notification
import com.example.myapplication.Simulation.BusDataSimulation
import com.example.myapplication.data.Bus
import java.lang.Math.abs


// Data class representing a notification for a bus tracker.
data class BusTrackerNotification(
    val title:String,
    val stop: Stop,
    val busline: Busline,
    val directionArrayAscendant: Boolean? = null,
    val timePicked: DepartureTime,
    val buffertime: Int = 0,
    val additionalTime: Int = 0,
    var notificationDone: Boolean = false
) {

    // Generates a string representation of the BusTrackerNotification.
    override fun toString(): String {

        return "\n${stop.name}" +
                "\n${busline.name}" +
                "\n after $timePicked (+ $buffertime + $additionalTime)"
    }

    fun toDebugString(): String{
        return "\n${stop.name}" +
                "\n${busline.name}" +
                "\n after $timePicked (+ $buffertime + $additionalTime)" +
                "\n notificationDone: $notificationDone"
    }

    // Retrieves the real departure time of the bus based on user input and simulated data.
    fun getRealDepartureTime(): DepartureTime? {
        var indexIfStopInBusLine: Int = 0
        for (stopIter: Stop in busline.stops) {
            if (stop == stopIter) {
                break
            }
            indexIfStopInBusLine++
        }
        var smallestDiffBetweenPickedAndRealDep: Int = -1
        var busUsed: Bus? = null
        BusDataSimulation.getInstance().getBusses().forEach {
            val realDeptimeOfStop = it.realDepTimes[indexIfStopInBusLine]

            if (it.buslineServed == busline && it.directionArrayAscendant == directionArrayAscendant && timePicked < realDeptimeOfStop) {
                val diffTime = abs((timePicked.hour * 60 + timePicked.min) - (realDeptimeOfStop.hour * 60 + realDeptimeOfStop.min))
                if (smallestDiffBetweenPickedAndRealDep < 0 || diffTime < smallestDiffBetweenPickedAndRealDep) {
                    smallestDiffBetweenPickedAndRealDep = diffTime
                    busUsed = it
                }
            }
        }
        return busUsed?.realDepTimes?.get(indexIfStopInBusLine)
    }

    // Calculates the time needed for the user to get ready before the bus departure.
    fun getTimeToGetReady(): DepartureTime? {
        val realDepTime: DepartureTime? = getRealDepartureTime()
        val toReturn: DepartureTime? = realDepTime?.minusMinutes(buffertime)?.minusMinutes(additionalTime)
        return if (toReturn != null && realDepTime != null && toReturn <= realDepTime) toReturn else null
    }
}

// Data class representing a bus stop.
data class Stop(
    val name: String = ""
) {
    // Generates a string representation of the Stop.
    override fun toString(): String {
        return name
    }
}

// Data class representing a bus line.
data class Busline(
    val name: String,
    val stops: List<Stop>
) {
    override fun toString(): String {
        return name
    }
}
