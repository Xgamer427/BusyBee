package com.example.myapplication


import TimeMachine
import com.example.myapplication.Simulation.BusDataSimulation
import BusTrackerNotification
import com.example.myapplication.data.BusTrackerViewModel
import DepartureTime
import org.junit.Assert
import org.junit.BeforeClass
import org.junit.Test
import java.time.LocalDateTime

class ViewModelTest {

private val TAG = "ViewModelTest"

    companion object{

        lateinit var viewModel: BusTrackerViewModel
        @BeforeClass
        @JvmStatic // needed otherwise not used
        fun setupUp(){
            BusDataSimulation.setDataSimulationToFake()
            viewModel = BusTrackerViewModel()

            //Setup Viewmodel for test

            viewModel.addToNotificationArray(arrayOf<BusTrackerNotification>(
                BusTrackerNotification(
                    BusDataSimulation.getInstance().getStops()[0],
                    BusDataSimulation.getInstance().getBuslines()[0],
                    true,
                    DepartureTime(10,10),
                    1,
                    1
                )
            ))

        }
    }
    @Test
    fun TestGetNotification (){

        var timeDate = LocalDateTime.parse("2018-12-30T12:02:00.00")
        TimeMachine.useFixedClockAt(timeDate)

        var listOfNotitications : List<BusTrackerNotification> = viewModel.getNofiticationNeeded()
        Assert.assertEquals(0, listOfNotitications.size)


        timeDate = LocalDateTime.parse("2018-12-30T12:03:00.00")
        TimeMachine.useFixedClockAt(timeDate)

        listOfNotitications = viewModel.getNofiticationNeeded()
        Assert.assertEquals(1, listOfNotitications.size)
    }

    @Test
    fun getRealDepartureTimeTest(){
        val departureTime = viewModel.notificationArray[0].getRealDepartureTime()
        Assert.assertEquals(DepartureTime(12,5), departureTime)
    }


    @Test
    fun getTimeToGetReadyTest() {
        val departureTime = viewModel.notificationArray[0].getTimeToGetReady()
        Assert.assertEquals(DepartureTime(12,3), departureTime)
    }


}
