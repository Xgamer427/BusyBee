
import android.os.Build
import androidx.annotation.RequiresApi
import java.time.Clock

import java.time.LocalDateTime

import java.time.ZoneId


object TimeMachine {
    private var clock = Clock.systemDefaultZone()
    private val zoneId = ZoneId.systemDefault()
    fun now(): LocalDateTime {
        return LocalDateTime.now(clock)
    }

    fun useFixedClockAt(date: LocalDateTime) {
        clock = Clock.fixed(date.atZone(zoneId).toInstant(), zoneId)
    }

    fun useSystemDefaultZoneClock() {
        clock = Clock.systemDefaultZone()
    }
}