import com.example.myapplication.Simulation.BusDataSimulation
import com.example.myapplication.data.BusTrackerViewModel
import org.junit.Test
import java.time.LocalDateTime
import org.junit.Assert.assertEquals
import org.junit.BeforeClass

/**
 * Unit tests for the ViewModel class
 *
 */
class ViewModelTest {

    //Setup code before all tests executed once
    companion object{

        lateinit var viewModel: BusTrackerViewModel
        @BeforeClass
        @JvmStatic // needed otherwise not used
        fun setupUp(){
            //DataSimulationFake does make the real departuretimes of all busses 3 minutes later then planned departure time
            BusDataSimulation.setDataSimulationToFake()
            viewModel = BusTrackerViewModel()

            //Setup Viewmodel notificationarray for test
            viewModel.addToNotificationArray(
                mutableListOf<BusTrackerNotification>(
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

    /**
     * test for getting all set up  which needs a notification right now
     */
    @Test
    fun testGetNotification() {
        // Test when no notifications are needed
        var timeDate = LocalDateTime.parse("2018-12-30T12:02:00.00")
        TimeMachine.useFixedClockAt(timeDate)
        var listOfNotifications: List<BusTrackerNotification> = viewModel.getNofiticationNeeded()
        assertEquals(0, listOfNotifications.size)

        // Test when a notification is needed
        timeDate = LocalDateTime.parse("2018-12-30T12:03:00.00")
        TimeMachine.useFixedClockAt(timeDate)
        listOfNotifications = viewModel.getNofiticationNeeded()
        assertEquals(1, listOfNotifications.size)

    }

    /**
     * test for getting the real departure time of a notifications bus
     */
    @Test
    fun testGetRealDepartureTime() {
        // Test the getRealDepartureTime function
        val departureTime = viewModel.notificationArray[0].getRealDepartureTime()
        assertEquals(DepartureTime(12, 5), departureTime)

        // Add more assertions as needed
    }

    /**
     * Test for getting the time to get ready for the bus for the notification
     */
    @Test
    fun testGetTimeToGetReady() {
        // Test the getTimeToGetReady function
        val timeToGetReady = viewModel.notificationArray[0].getTimeToGetReady()
        assertEquals(DepartureTime(12, 3), timeToGetReady)

        // Add more assertions as needed
    }
}
