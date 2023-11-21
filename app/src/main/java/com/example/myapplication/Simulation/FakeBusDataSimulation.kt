// Define a package for the simulation functionality within the application
package com.example.myapplication.Simulation

// Import necessary classes for the simulation

import com.example.myapplication.data.Bus
import DepartureTime
import com.example.myapplication.data.Busline
import com.example.myapplication.data.Stop

// Class representing a fake implementation of the BusDataSimulation interface for testing purposes
class FakeBusDataSimulation : BusDataSimulation {

    // Array of stops used in the fake simulation
    private val stops = arrayOf<Stop>(
        Stop("Stop1"),
        Stop("Stop2"),
        Stop("Stop3"),
    )

    // List of bus lines with associated stops used in the fake simulation
    private val buslines: List<Busline> = mutableListOf(
        Busline("Test0", stops.slice(0..2)),
    )

    // List of buses with planned departure times and a boolean indicating if the bus is currently active
    private val buses: List<Bus> = mutableListOf(
        Bus("TestBus", buslines[0],
            mutableListOf(
                DepartureTime(12, 2),
                DepartureTime(12, 4),
                DepartureTime(12, 6)
            ),
            true
        ),
    )

    // Initialization block to set real departure times for each bus based on planned departure times
    init {
        buses.forEach {
            var index = 0
            it.plannedDepTimes.forEach { plannedDepartureTime ->
                it.realDepTimes[index++] = plannedDepartureTime.plusMinutes(3)
            }
            println("New real dep times ${it.name} " + it.realDepTimes)
        }
    }

    // Override function to get the list of buses in the fake simulation
    override fun getBusses() = buses

    // Override function to get the array of stops in the fake simulation
    override fun getStops() = stops

    // Override function to get the list of bus lines in the fake simulation
    override fun getBuslines() = buslines
}
