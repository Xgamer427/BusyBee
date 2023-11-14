package com.example.myapplication.Simulation

import com.example.myapplication.data.Bus
import com.example.myapplication.data.Busline
import com.example.myapplication.data.Stop

interface BusDataSimulation {
    companion object : BusDataSimulation {

        private var instanceOfSimulation: BusDataSimulation? = null
        @Volatile
        private var instance: BusDataSimulation? = null

        init {
            instanceOfSimulation = RealBusDataSimulation()
        }

        fun setDataSimulationToFake(){
            instanceOfSimulation = FakeBusDataSimulation()
        }

        fun getInstance() =
            instance ?: synchronized(this) {
                instance ?: BusDataSimulation.also { instance = it }
            }

        override fun getBusses()= instanceOfSimulation!!.getBusses()

        override fun getStops() = instanceOfSimulation!!.getStops()

        override fun getBuslines() = instanceOfSimulation!!.getBuslines()
    }

    fun getBusses(): List<Bus>

    fun getStops(): Array<Stop>

    fun getBuslines(): List<Busline>
}