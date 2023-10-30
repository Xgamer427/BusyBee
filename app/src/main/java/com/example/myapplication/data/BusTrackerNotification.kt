package com.example.myapplication.data

import kotlin.random.Random


data class BusTrackerNotification(
    val stop: Stop,
    val bus: Bus,
    val plannedDepTime: DepartureTime,
    val buffertime: Int = 0,
    val additionalTime: Int = 0
) {
    fun getRealDepartureTime(): DepartureTime{
        var indexIfStopInBusLineServed: Int = 0
        for (stopIter: Stop in bus.buslineServed.stops) {
            if (stop== stopIter){
                break;
            }
            indexIfStopInBusLineServed++
        }
        return bus.realDepTimes[indexIfStopInBusLineServed]
    }

    fun getTimeToGetReady(): DepartureTime{
        return getRealDepartureTime().plusMinutes(buffertime).plusMinutes(additionalTime)
    }
}

data class DepartureTime(
    var hour: Int = 0,
    var min: Int = 0
){
    init {
        if(hour<0 || hour>23 || min<0 || min>59){
            throw Exception("Values are not in time boundaries")
        }
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

    fun clone(): DepartureTime{
        return DepartureTime(hour,min)
    }

    fun equalsAndGreaterSmaller(other: DepartureTime){

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
    val stops: List<Stop>
) {

}