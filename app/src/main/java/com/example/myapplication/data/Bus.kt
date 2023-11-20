package com.example.myapplication.data

import Busline
import kotlin.random.Random

/**
 * Data class representing a Bus with its properties.
 *
 * @property longditude The longitude of the bus.
 * @property latitude The latitude of the bus.
 * @property name The name of the bus.
 * @property buslineServed The bus line served by the bus.
 * @property plannedDepTimes The planned departure times for the stops of the bus line.
 * @property directionArrayAscendant Indicates the direction of the bus line (ascending or descending).
 */
data class Bus(
    val longditude: Long = 0,
    val latitude: Long = 0,
    val name: String,
    val buslineServed: Busline,
    val plannedDepTimes: List<DepartureTime>,
    val directionArrayAscendant : Boolean
) {
    // List to store real departure times for each stop
    public var realDepTimes: MutableList<DepartureTime>

    // Initializer block to validate and initialize real departure times
    init {
        // Check if the number of stops matches the number of planned departure times
        if(buslineServed.stops.size != plannedDepTimes.size){
            throw Exception("PlannedDepTimes must be same length as buslineServed.stops")
        }

        // Initialize the list to store real departure times
        realDepTimes = mutableListOf()

        // Use random to simulate actual departure times based on planned times
        val random = Random
        plannedDepTimes.forEach {
            var realDepTime: DepartureTime = it.clone()

            // Add a random delay between 0 and 2 minutes to simulate real departure times
            realDepTime = realDepTime.plusMinutes(random.nextInt(0, 2 + 1))

            // Add the simulated departure time to the list
            realDepTimes.add(realDepTime)
        }
    }

    /**
     * Override of the equals function to compare Bus objects.
     *
     * @param other The object to compare with.
     * @return True if the objects are equal, false otherwise.
     */
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Bus

        // Check equality based on longitude and latitude
        if (longditude != other.longditude) return false
        if (latitude != other.latitude) return false

        return true
    }

    /**
     * Override of the hashCode function to generate a hash code for Bus objects.
     *
     * @return The hash code.
     */
    override fun hashCode(): Int {
        var result = longditude.hashCode()
        result = 31 * result + latitude.hashCode()
        return result
    }

    /**
     * Custom function to convert Bus object to a string representation with real departure times.
     *
     * @return The string representation of the Bus object.
     */
    fun myToString() : String{
        return toString() + " realDepartureTimes: " + realDepTimes
    }
}
