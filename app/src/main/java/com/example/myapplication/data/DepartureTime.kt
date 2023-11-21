// Data class representing a departure time.
data class DepartureTime(
    val hour: Int = 0,
    val min: Int = 0
): Comparable<DepartureTime> {

    // Validates that hour and minute values are within acceptable time boundaries.
    init {
        if(hour < 0 || hour > 23 || min < 0 || min > 59) {
            throw Exception("Values are not in time boundaries $hour, $min")
        }
    }

    // Generates a formatted string representation of the DepartureTime.
    override fun toString(): String {
        var hourStr = "$hour"
        if(hour < 10){
            hourStr = "0$hourStr"
        }
        var minStr = "$min"
        if(min < 10){
            minStr = "0$minStr"
        }
        return "$hourStr:$minStr"
    }

    // Adds a specified number of minutes to the current DepartureTime.
    fun plusMinutes(plusMin: Int): DepartureTime {
        val newMin: Int = min + plusMin
        val minForNewTime: Int = newMin % 60
        val timeForNewHours: DepartureTime = plusHours(newMin / 60)
        return DepartureTime(timeForNewHours.hour, minForNewTime)
    }

    // Adds a specified number of hours to the current DepartureTime.
    fun plusHours(plusH: Int): DepartureTime {
        val newhour = (hour + plusH) % 24
        return DepartureTime(newhour, this.min)
    }

    // Subtracts a specified number of minutes from the current DepartureTime.
    fun minusMinutes(minusMin: Int): DepartureTime {
        var newMin: Int = min - minusMin
        var newHours = this.hour

        if(newMin < 0){
            newHours = minusHours(Math.abs(newMin / 60) + 1).hour
            newMin = 60 + ((newMin) % 60)
        }

        return DepartureTime(newHours, newMin)
    }

    // Subtracts a specified number of hours from the current DepartureTime.
    private fun minusHours(minusH: Int): DepartureTime {
        var newH: Int = hour - minusH
        if(newH < 0){
            newH = 24 + ((newH) % 24)
        }

        return DepartureTime(newH, this.min)
    }

    // Creates a clone of the current DepartureTime.
    fun clone(): DepartureTime {
        return DepartureTime(hour, min)
    }

    // Compares two DepartureTime objects.
    override fun compareTo(other: DepartureTime): Int {
        return when {
            hour < other.hour -> -1
            hour == other.hour -> min.compareTo(other.min)
            else -> 1
        }
    }

    // Checks if two DepartureTime objects are equal.
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as DepartureTime

        return hour == other.hour && min == other.min
    }

    // Generates a hash code for the DepartureTime.
    override fun hashCode(): Int {
        var result = hour
        result = 31 * result + min
        return result
    }
}
