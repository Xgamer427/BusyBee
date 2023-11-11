package com.example.myapplication


import TimeMachine
import com.example.myapplication.Simulation.BusDataSimulation
import com.example.myapplication.data.BusTrackerNotification
import com.example.myapplication.data.DepartureTime
import org.junit.Assert
import org.junit.BeforeClass
import org.junit.Test
import java.time.LocalDateTime
import java.time.LocalTime

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

            viewModel.updateNotificationArray(arrayOf<BusTrackerNotification>(
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

        var timeDate = LocalDateTime.parse("2018-12-30T12:00:00.00")
        TimeMachine.useFixedClockAt(timeDate)

        var listOfNotitications : List<BusTrackerNotification> = viewModel.uiState.value.getNofiticationNeeded()
        Assert.assertEquals(0, listOfNotitications.size)


        timeDate = LocalDateTime.parse("2018-12-30T12:01:00.00")
        TimeMachine.useFixedClockAt(timeDate)

        listOfNotitications = viewModel.uiState.value.getNofiticationNeeded()
        Assert.assertEquals(1, listOfNotitications.size)
    }

    @Test
    fun getRealDepartureTimeTest(){
        val departureTime = viewModel.uiState.value.notificationArray[0].getRealDepartureTime()
        Assert.assertEquals(DepartureTime(12,3), departureTime)
    }


    @Test
    fun getTimeToGetReadyTest() {
        val departureTime = viewModel.uiState.value.notificationArray[0].getTimeToGetReady()
        Assert.assertEquals(DepartureTime(12,1), departureTime)
    }


}
