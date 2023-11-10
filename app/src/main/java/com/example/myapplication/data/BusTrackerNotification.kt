package com.example.myapplication.data

import android.util.Log
import com.example.myapplication.BusDataSimulation
import java.lang.Math.abs
import kotlin.random.Random


data class BusTrackerNotification(
    val stop: Stop,
    val busline : Busline,
    val directionArrayAscendant : Boolean? = null,
    val timePicked: DepartureTime,
    val buffertime: Int = 0,
    val additionalTime: Int = 0,
) {


    fun getRealDepartureTime(): DepartureTime? {
        var indexIfStopInBusLine: Int = 0
        for (stopIter: Stop in busline.stops) {
            if (stop == stopIter){
                break;
            }
            indexIfStopInBusLine++
        }
        var smallestDiffBetweenPickedAndRealDep : Int = -1
        var busUsed : Bus? = null
        BusDataSimulation.getInstance().getBusses().forEach {

            var realDeptimeOfStop = it.realDepTimes[indexIfStopInBusLine]

            if(it.buslineServed == busline && it.directionArrayAscendant == directionArrayAscendant && timePicked < realDeptimeOfStop){
                var diffTime = abs((timePicked.hour*60 + timePicked.min) - (realDeptimeOfStop.hour*60 + realDeptimeOfStop.min))
                if(smallestDiffBetweenPickedAndRealDep<0 || diffTime<smallestDiffBetweenPickedAndRealDep){
                    smallestDiffBetweenPickedAndRealDep = diffTime
                    busUsed = it
                }
            }
        }
        if(busUsed != null){
            return busUsed!!.realDepTimes[indexIfStopInBusLine]
        }else{
            return null
        }


    }

    //TODO handle/change that getRealDepartureTIme returns null if the best bus drives at next day
    fun getTimeToGetReady(): DepartureTime?{
        return getRealDepartureTime()?.minusMinutes(buffertime)?.minusMinutes(additionalTime) //TODO what if no bus for this time found
    }
}

data class DepartureTime(
    var hour: Int = 0,
    var min: Int = 0
): Comparable<DepartureTime>{
    init {
        if(hour<0 || hour>23 || min<0 || min>59){
            throw Exception("Values are not in time boundaries")
        }

    }
    override fun toString(): String {
        return hour.toString() + "h" + min.toString() + "min"
    }

    fun plusMinutes(plusMin: Int): DepartureTime{
        val newMin: Int = min + plusMin
        plusHours(newMin/60)
        min = newMin%60
        return this
    }

    fun plusHours(plusH: Int) : DepartureTime{
        hour = (hour+plusH)%24
        return this
    }

    fun minusMinutes(minusMin: Int): DepartureTime {
        val newMin: Int = min - minusMin
        minusHours((newMin/60)+1)
        min = 60+((newMin)%60)
        return this
    }

    private fun minusHours(minusH: Int) {
        val newH: Int = hour - minusH
        hour = 24+((newH)%24)
    }

    fun clone(): DepartureTime{
        return DepartureTime(hour,min)
    }

    override fun compareTo(other: DepartureTime): Int {
        if (hour<other.hour){
            return -1
        }else{
            if(hour==other.hour){
                if(min<other.min){
                    return -1
                }else{
                    if(min==other.min) {
                        return 0
                    }else{
                        return 1
                    }
                }
            }else{
                return 1
            }
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as DepartureTime

        if (hour != other.hour) return false
        if (min != other.min) return false

        return true
    }

    override fun hashCode(): Int {
        var result = hour
        result = 31 * result + min
        return result
    }
}

data class Direction (
    val directionA: String = "DirectionA",
    val directionB: String = "DirectionB"
)

data class Stop(
    val longditude: Long = 0,
    val latitude: Long = 0,
    val name : String = ""
){
    override fun toString(): String {
        return name
    }
}

data class Bus(
    val longditude: Long = 0,
    val latitude: Long = 0,
    val name: String,
    val buslineServed: Busline,
    val plannedDepTimes: List<DepartureTime>,//gives the departure times for the stops of the buslineServed in the order of the stops in buslineServed (exaple plannedDepTime[0] is departure for buslineServer.stops[0]. This not depends on directionArrayAscendant
    val directionArrayAscendant : Boolean
){
    val realDepTimes: List<DepartureTime>
    // initializer block
    init {
        if(buslineServed.stops.size != plannedDepTimes.size){
            throw Exception("PlannedDepTimes must be same length as buslineServed.stops")
        }
        realDepTimes = mutableListOf()
        val random = Random
        plannedDepTimes.forEach {
            //TODO create realDepTimes

            var realDepTime: DepartureTime = it.clone()
            realDepTime.plusMinutes(random.nextInt(0, 2 + 1))
            realDepTimes.add(realDepTime)
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Bus

        if (longditude != other.longditude) return false
        if (latitude != other.latitude) return false

        return true
    }

    override fun hashCode(): Int {
        var result = longditude.hashCode()
        result = 31 * result + latitude.hashCode()
        return result
    }
}

data class Busline(
    val name: String,
    val stops: List<Stop>
) {

}