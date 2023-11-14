package com.example.myapplication

import com.example.myapplication.Simulation.BusDataSimulation
import com.example.myapplication.data.Bus
import com.example.myapplication.data.BusTrackerNotification
import com.example.myapplication.data.DepartureTime
import org.junit.Assert
import org.junit.BeforeClass
import org.junit.Test

class BusTrackerNotificationTest {
    //Setup Notification for test
    val notificationToTest = BusTrackerNotification(
        BusDataSimulation.getInstance().getStops()[0],
        BusDataSimulation.getInstance().getBuslines()[0],
        true,
        DepartureTime(10,10),
        1,
        1
    )



    companion object{
        var busToUse: Bus? = null
        //setup a bus to fit for the notification
        @BeforeClass
        @JvmStatic // needed otherwise not used
        fun setupUp(){
            BusDataSimulation.setDataSimulationToFake()
            //override the normal created random realdeparturetimes with planneddeparturetimes plus 1 min
            var realDepTimesToOverride = mutableListOf<DepartureTime>()

            busToUse = BusDataSimulation.getInstance().getBusses()[0]

            busToUse!!.plannedDepTimes.forEach {//set realdeparturetime not random
                realDepTimesToOverride.add(it.plusMinutes(1))
            }

            busToUse!!.realDepTimes = realDepTimesToOverride
        }


    }
    @Test
    fun getRealDepartureTimeTest(){

        val departureTime = notificationToTest.getRealDepartureTime()
        Assert.assertEquals(busToUse?.realDepTimes?.get(0), departureTime)
    }


    @Test
    fun getTimeToGetReadyTest() {
        val departureTime = notificationToTest.getTimeToGetReady()
        Assert.assertEquals(busToUse?.realDepTimes?.get(0)?.minusMinutes(notificationToTest.additionalTime)?.minusMinutes(notificationToTest.buffertime), departureTime)
    }


}

