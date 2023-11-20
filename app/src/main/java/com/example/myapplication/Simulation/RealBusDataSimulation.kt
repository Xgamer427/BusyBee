package com.example.myapplication.Simulation

import com.example.myapplication.data.Bus
import com.example.myapplication.data.Busline
import com.example.myapplication.data.DepartureTime
import com.example.myapplication.data.Stop

open class RealBusDataSimulation: BusDataSimulation {

    private val stops = arrayOf<Stop>(
        Stop(0,0,"Stop1"),
        Stop(0,0,"Stop2"),
        Stop(0,0,"Stop3"),
        Stop(0,0,"Stop4"),
    )

    private val buslines:List<Busline> = mutableListOf(
        Busline( "Busline0", stops.slice(0..2)),
        Busline( "Busline1", stops.slice(1..3)),
    )

    private val buses:List<Bus> = mutableListOf(
        Bus(0,0, "Bus0", buslines[0],
            mutableListOf(
                DepartureTime(0,30),
                DepartureTime(15,5),
                DepartureTime(17,47)
            ),
            true
        ),

        Bus(0,0, "Bus1", buslines[1],
            mutableListOf(
                DepartureTime(12,14),
                DepartureTime(12,12),
                DepartureTime(12,10)),
            false
        ),
    )



    override fun getBusses() = buses

    override fun getStops() = stops

    override fun getBuslines() = buslines

}