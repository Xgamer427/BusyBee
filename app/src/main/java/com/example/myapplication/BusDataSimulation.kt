package com.example.myapplication

import android.util.Log
import com.example.myapplication.data.Bus
import com.example.myapplication.data.Stop

class BusDataSimulation: Thread() {

    private val stops = arrayOf<Stop>(
        Stop(0,0,"Stop1"),
        Stop(0,0,"Stop2")
    )

    private val busses = arrayOf<Bus>(
        Bus(0, 0, "Bus1", stops[0], stops[1], 5),
        Bus(5, 5, "Bus2", stops[1], stops[0], 5)
    )



    private val bus = Bus(0,0,"TestBus", stops[0], stops[1], 10)

    override fun run() {

        while (true){
            sleep(1000)
            getInstance().update()
        }

    }

    companion object {
        @Volatile
        private var instance: BusDataSimulation? = null

        fun getInstance() =
            instance ?: synchronized(this) {
                instance ?: BusDataSimulation().also { instance = it }
            }
    }

    fun getStops() = stops

    fun getBusses() = busses

    private fun update(){
        bus.update()
        Log.d("BusSimulation", bus.fromStop.name + " -> " + bus.toStop.name + " minutes left " + bus.timeToNextStop)
    }
}