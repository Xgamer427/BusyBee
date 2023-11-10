package com.example.myapplication

import com.example.myapplication.data.BusTrackerNotification
import com.example.myapplication.data.DepartureTime
import org.junit.Assert
import org.junit.Before
import org.junit.BeforeClass
import org.junit.Test
import java.time.Clock
import java.time.Instant
import java.time.ZoneOffset

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
        val busToUse = BusDataSimulation.getInstance().getBusses()[0]
        //setup a bus to fit for the notification
        @BeforeClass
        @JvmStatic // needed otherwise not used
        fun setupUp(){

            //override the normal created random realdeparturetimes with planneddeparturetimes plus 1 min
            var realDepTimesToOverride = mutableListOf<DepartureTime>()

            busToUse.plannedDepTimes.forEach {
                realDepTimesToOverride.add(it.plusMinutes(1))
            }

            busToUse.realDepTimes = realDepTimesToOverride
        }


    }
    @Test
    fun getRealDepartureTimeTest(){

        val departureTime = notificationToTest.getRealDepartureTime()
        Assert.assertEquals(departureTime, DepartureTime(12,11))
    }


    @Test
    fun getTimeToGetReadyTest() {
        val departureTime = notificationToTest.getTimeToGetReady()
        Assert.assertEquals(DepartureTime(12,9), departureTime)
    }


}

