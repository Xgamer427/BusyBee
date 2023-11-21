package com.example.myapplication

import DepartureTime
import org.junit.Test

import org.junit.Assert.*

/**
 * Unit tests for the DepartureTime class, which represents a time for departure.
 *
 * These tests cover various operations like addition and subtraction of hours and minutes,
 * cloning, comparison, and multiple consecutive operations.
 */
class DepartureTimeUnitTest {

    /**
     * Test for the plusHours method of the DepartureTime class.
     */
    @Test
    fun testHoursPlus() {
        // Test case 1
        val a = DepartureTime(12, 10)
        val aa = a.plusHours(10)
        assertEquals("Base object should be same", DepartureTime(12, 10), a)
        assertEquals("12:10 + 10 hours is 22:10", DepartureTime(22, 10), aa)

        // Test case 2
        val b = DepartureTime(12, 10)
        val bb = b.plusHours(20)
        assertEquals("Base object should be same", DepartureTime(12, 10), b)
        assertEquals("12 + 20 hours is 8:10", DepartureTime(8, 10), bb)
    }

    /**
     * Test for the plusMinutes method of the DepartureTime class.
     */
    @Test
    fun testMinPlus() {
        // Test case 1
        val a = DepartureTime(12, 10)
        val aa = a.plusMinutes(10)
        assertEquals("Base object not change", DepartureTime(12, 10), a)
        assertEquals("10 + 10 minutes is 20 minutes", DepartureTime(12, 20), aa)

        // Test case 2
        val b = DepartureTime(12, 10)
        val bb = b.plusMinutes(50)
        assertEquals("Base object not change", DepartureTime(12, 10), b)
        assertEquals("12:10 plus 50 minutes is 13:00", DepartureTime(13, 0), bb)

        // Test case 3
        val c = DepartureTime(23, 59)
        val cc = c.plusMinutes(70)
        assertEquals("Base object not change", DepartureTime(23, 59), c)
        assertEquals("23:59 plus 70 minutes is 1:09", DepartureTime(1, 9), cc)
    }

    /**
     * Test for the clone method of the DepartureTime class.
     */
    @Test
    fun CloneTest() {
        val c = DepartureTime(23, 59)

        // Test case 1
        var clone = c.clone()
        assertTrue("Clone should be same as time cloned from", c == clone)
        assertEquals("Clone should have same minutes", 23, c.hour)
        assertEquals("Clone should have same hours", 59, c.min)

        // Test case 2
        assertTrue("After change clone should not be the same", c == clone)
        clone = clone.plusMinutes(10)
        assertTrue("After change clone should not be the same", c != clone)
    }

    /**
     * Test for the comparison methods of the DepartureTime class.
     */
    @Test
    fun compareTest() {
        // Test case 1
        val b = DepartureTime(12, 10)
        val c = DepartureTime(23, 59)

        assertTrue(b < c)
        assertFalse(b > c)
        assertFalse(b == c)

        // Test case 2
        val d = DepartureTime(23, 59)
        assertFalse(d < c)
        assertFalse(d > c)
    }

    /**
     * Test for multiple consecutive plusMinutes operations.
     */
    @Test
    fun testmultiplePlusMinutes() {
        // Test case 1
        val b = DepartureTime(12, 10)
        val c = b.plusMinutes(20).plusMinutes(2)
        assertEquals("Base time object not changes", 10, b.min)
        assertEquals("10 + 20 + 2 minutes is 32 minutes", 32, c.min)
    }

    /**
     * Test for multiple consecutive minusMinutes operations.
     */
    @Test
    fun testmultipleMinusMinutes() {
        // Test case 1
        val b = DepartureTime(12, 10)
        val c = b.minusMinutes(3)
        assertEquals("Base time object not changes", 10, b.min)
        assertEquals("Base time object not changes", 12, b.hour)
        assertEquals("10 - 3 minutes is 7 minutes", 7, c.min)
        assertEquals("10 - 3 minutes should not change hour", 12, c.hour)

        // Test case 2
        val d = DepartureTime(12, 10)
        val e = d.minusMinutes(6).minusMinutes(6)
        assertEquals("Base time object not changes", 10, d.min)
        assertEquals("Base time object not changes", 12, d.hour)
        assertEquals("12:10 - 12 minutes is 11:58", 11, e.hour)
        assertEquals("12:10 - 12 minutes is 11:58", 58, e.min)

        // Test case 3
        val f = DepartureTime(0, 10)
        val g = f.minusMinutes(12)
        assertEquals("Base time object not changes", DepartureTime(0, 10), f)
        assertEquals("0:10 - 12 minutes is 23:58", DepartureTime(23, 58), g)
    }
}
