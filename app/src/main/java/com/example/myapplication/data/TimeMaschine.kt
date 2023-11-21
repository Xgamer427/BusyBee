import java.time.Clock
import java.time.LocalDateTime
import java.time.ZoneId

/**
 * A singleton object representing a TimeMachine for managing time-related operations.
 */
object TimeMachine {
    // The clock instance used for time-related operations.
    private var clock = Clock.systemDefaultZone()

    // The default time zone.
    private val zoneId = ZoneId.systemDefault()

    /**
     * Gets the current date and time using the configured clock.
     *
     * @return The current date and time.
     */
    fun now(): LocalDateTime {
        return LocalDateTime.now(clock)
    }

    /**
     * Configures the TimeMachine to use a fixed clock set to the specified date and time.
     *
     * @param date The date and time to set the fixed clock.
     */
    fun useFixedClockAt(date: LocalDateTime) {
        clock = Clock.fixed(date.atZone(zoneId).toInstant(), zoneId)
    }

    /**
     * Restores the TimeMachine to use the system's default time zone clock.
     */
    fun useSystemDefaultZoneClock() {
        clock = Clock.systemDefaultZone()
    }
}
