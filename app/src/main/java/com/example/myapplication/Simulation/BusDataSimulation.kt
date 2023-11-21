// Define a package for the simulation functionality within the application
package com.example.myapplication.Simulation

// Import necessary classes for the simulation

import com.example.myapplication.data.Bus
import com.example.myapplication.data.Busline
import com.example.myapplication.data.Stop

// Define an interface for simulating bus data
interface BusDataSimulation {

    // Companion object for the BusDataSimulation interface
    companion object : BusDataSimulation {

        // Private variable to hold an instance of the BusDataSimulation interface
        private var instanceOfSimulation: BusDataSimulation? = null

        // Volatile variable to ensure that changes made by one thread are visible to other threads
        @Volatile
        private var instance: BusDataSimulation? = null

        // Initialization block to set the instanceOfSimulation to a RealBusDataSimulation instance
        init {
            instanceOfSimulation = RealBusDataSimulation()
        }

        // Function to set the data simulation to a fake implementation
        fun setDataSimulationToFake(){
            instanceOfSimulation = FakeBusDataSimulation()
        }

        // Function to get the singleton instance of the BusDataSimulation interface
        fun getInstance() =
            instance ?: synchronized(this) {
                instance ?: BusDataSimulation.also { instance = it }
            }

        // Override function to get the list of buses from the current simulation instance
        override fun getBusses()= instanceOfSimulation!!.getBusses()

        // Override function to get the array of stops from the current simulation instance
        override fun getStops() = instanceOfSimulation!!.getStops()

        // Override function to get the list of bus lines from the current simulation instance
        override fun getBuslines() = instanceOfSimulation!!.getBuslines()
    }

    // Function to get the list of buses in the simulation
    fun getBusses(): List<Bus>

    // Function to get the array of stops in the simulation
    fun getStops(): Array<Stop>

    // Function to get the list of bus lines in the simulation
    fun getBuslines(): List<Busline>
}
