package com.example.myapplication

import com.example.myapplication.data.Bus
import com.example.myapplication.data.Busline
import com.example.myapplication.data.DepartureTime
import com.example.myapplication.data.Stop

class BusDataSimulation {

    private val stops: List<Stop> = mutableListOf(
        Stop(0,0,"Stop1"),
        Stop(0,0,"Stop2"),
        Stop(0,0,"Stop3"),
        Stop(0,0,"Stop4"),
        Stop(0,0,"Stop5"),
        Stop(0,0,"Stop6"),
    )

    private val buslines:List<Busline> = mutableListOf(
        Busline(stops.slice(0..2)),
    )

    private val buses:List<Bus> = mutableListOf(
        Bus(0,0, buslines[0],
            mutableListOf(
                DepartureTime(12,10),
                DepartureTime(12,12),
                DepartureTime(12,14)
            ),
            true
        ),

        Bus(0,0, buslines[0],
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
}