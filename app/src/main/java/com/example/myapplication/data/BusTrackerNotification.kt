package com.example.myapplication.data



data class BusTrackerNotification(
    val stop: Stop,
    val bus: Bus,
    val plannedDepTime: DepartureTime,
    val buffertime: Int = 0,
    val additionalTime: Int = 0
)

data class DepartureTime(
    val hour: Int = 0,
    val min: Int = 0
)

data class Stop(
    val longditude: Long = 0,
    val latitude: Long = 0,
    val name : String = ""
){
    override fun toString(): String {
        return name
    }

    fun beauryToString(): String {
        return super.toString()
    }
}

data class Bus(
    val longditude: Long = 0,
    val latitude: Long = 0,
    val name : String = "",
    var fromStop: Stop,
    var toStop: Stop,
    var timeToNextStop : Int
){
    fun update(){
        timeToNextStop--
        if(timeToNextStop<=0){
            val temp = fromStop
            fromStop = toStop
            toStop = temp
            timeToNextStop = 10
        }
    }
}