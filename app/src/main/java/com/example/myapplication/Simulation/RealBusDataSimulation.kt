// Define a package for the simulation functionality within the application
package com.example.myapplication.Simulation

// Import necessary classes for the simulation
import com.example.myapplication.data.Bus
import Busline
import DepartureTime
import Stop

// Class representing a real implementation of the BusDataSimulation interface for actual data simulation
open class RealBusDataSimulation : BusDataSimulation {

    // Array of stops used in the real simulation
    private val stops = arrayOf<Stop>(
        Stop("Stop1"),
        Stop("Stop2"),
        Stop("Stop3"),
        Stop("Stop4"),
    )

    // List of bus lines with associated stops used in the real simulation
    private val buslines: List<Busline> = mutableListOf(
        Busline("Busline0", stops.slice(0..2)),
        Busline("Busline1", stops.slice(1..3)),
    )

    // List of buses with planned departure times and a boolean indicating if the bus is currently active
    private val buses: List<Bus> = mutableListOf(
        Bus("Bus0", buslines[0],
            mutableListOf(
                DepartureTime(0, 30),
                DepartureTime(15, 5),
                DepartureTime(17, 47)
            ),
            true
        ),

        Bus("Bus1", buslines[1],
            mutableListOf(
                DepartureTime(12, 14),
                DepartureTime(12, 12),
                DepartureTime(12, 10)),
            false
        ),
    )

    // Override function to get the list of buses in the real simulation
    override fun getBusses() = buses

    // Override function to get the array of stops in the real simulation
    override fun getStops() = stops

    // Override function to get the list of bus lines in the real simulation
    override fun getBuslines() = buslines
}
