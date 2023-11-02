package com.example.myapplication

import android.util.Log
import com.example.myapplication.data.Bus
import com.example.myapplication.data.Busline
import com.example.myapplication.data.DepartureTime
import com.example.myapplication.data.Stop

class BusDataSimulation: Thread() {

    private val stops = arrayOf<Stop>(
        Stop(0,0,"Stop1"),
        Stop(0,0,"Stop2"),
        Stop(0,0,"Stop3"),
        Stop(0,0,"Stop4"),
        Stop(0,0,"Stop5"),
        Stop(0,0,"Stop6"),
    )

//    private val busses = arrayOf<Bus>(
//        Bus(0, 0, "Bus1", stops[0], stops[1], 5),
//        Bus(5, 5, "Bus2", stops[1], stops[0], 5)
//    )

    private val buslines:List<Busline> = mutableListOf(
        Busline( "Test", stops.slice(0..2)),
    )

    private val buses:List<Bus> = mutableListOf(
        Bus(0,0, "TestBus", buslines[0],
            mutableListOf(
                DepartureTime(12,10),
                DepartureTime(12,12),
                DepartureTime(12,14)
            ),
            true
        ),

        Bus(0,0, "TestBus", buslines[0],
            mutableListOf(
                DepartureTime(12,14),
                DepartureTime(12,12),
                DepartureTime(12,10)),
            false
        ),
    )

    companion object {
        @Volatile
        private var instance: BusDataSimulation? = null

        fun getInstance() =
            instance ?: synchronized(this) {
                instance ?: BusDataSimulation().also { instance = it }
            }
    }

    fun getStops() = stops

    fun getBusses() = buses

    fun getBuslines() = buslines

}