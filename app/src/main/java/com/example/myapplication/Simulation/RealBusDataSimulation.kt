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
        Stop(0,0,"Stop5"),
        Stop(0,0,"Stop6"),
        Stop(0,0,"Stop7"),
        Stop(0,0,"Stop8"),
        Stop(0,0,"Stop9"),
        Stop(0,0,"Stop10"),
        Stop(0,0,"Stop11"),
        Stop(0,0,"Stop12")
    )

    private val buslines:List<Busline> = mutableListOf(
        Busline( "Busline0", stops.slice(0..2)),
        Busline( "Busline1", stops.slice(1..3)),
        Busline( "Busline2", stops.slice(2..4)),
        Busline( "Busline4", stops.slice(3..5)),
        Busline( "Busline5", stops.slice(4..6)),
        Busline( "Busline6", stops.slice(5..7)),
        Busline( "Busline7", stops.slice(6..8)),
    )

    private val buses:List<Bus> = mutableListOf(
        Bus(0,0, "Bus0", buslines[0],
            mutableListOf(
                DepartureTime(16,32),
                DepartureTime(17,46),
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