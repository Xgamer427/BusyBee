package com.example.myapplication.Simulation

import com.example.myapplication.data.Bus
import com.example.myapplication.data.Busline
import com.example.myapplication.data.DepartureTime
import com.example.myapplication.data.Stop

class FakeBusDataSimulation : BusDataSimulation {


    private val stops = arrayOf<Stop>(
        Stop(0,0,"Stop1"),
        Stop(0,0,"Stop2"),
        Stop(0,0,"Stop3"),

    )

    private val buslines:List<Busline> = mutableListOf(
        Busline( "Test0", stops.slice(0..2)),

    )

    private val buses:List<Bus> = mutableListOf(
        Bus(0,0, "TestBus", buslines[0],
            mutableListOf(
                DepartureTime(12,2),
                DepartureTime(12,4),
                DepartureTime(12,6)
            ),
            true
        ),
    )

    init {
        buses.forEach {
            var index = 0
            it.plannedDepTimes.forEach{plannedDepartureTime ->
                it.realDepTimes[index++] = plannedDepartureTime.plusMinutes(1)
            }
            println("New real dep times ${it.name} "+ it.realDepTimes)
        }

    }

    override fun getBusses() = buses

    override fun getStops() = stops

    override fun getBuslines() = buslines
}
