package com.example.myapplication

import com.example.myapplication.data.DepartureTime
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class DepartureTimeUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun testHoursPlus(){
        val a = DepartureTime(12,10)
        a.plusHours(10)
        assertEquals("12+10 hours is 22 hours", 22, a.hour)

        val b = DepartureTime(12,10)
        b.plusHours(20)
        assertEquals("12+20 hours is 8 hours", 8, b.hour)
    }

    @Test
    fun testMinPlus(){
        val a = DepartureTime(12,10)
        a.plusMinutes(10)
        assertEquals("10+10 min is 20 min", 20, a.min)
        assertEquals("10+10 min should not change the hours", 12, a.hour)

        val b = DepartureTime(12,10)
        b.plusMinutes(50)
        assertEquals("12:10 plus 50 min should be 13:00", 13, b.hour)
        assertEquals("12:10 plus 50 min should be 13:00", 0, b.min)

        val c = DepartureTime(23,59)
        c.plusMinutes(70)
        assertEquals("12:10 plus 50 min should be 13:00", 1, c.hour)
        assertEquals("12:10 plus 50 min should be 13:00", 9, c.min)
    }

    @Test
    fun CloneTest(){
        val c = DepartureTime(23,59)

        val clone = c.clone()
        assertTrue("Clone should be same as time cloned from",c == clone)
        assertEquals("Clone should have same minutes", 23, c.hour)
        assertEquals("Clone should have same hours",59, c.min)

        clone.plusMinutes(10)
        assertTrue("After change clone not should be the same",c != clone)
    }

    @Test
    fun compareTest(){
        val b = DepartureTime(12,10)
        val c = DepartureTime(23,59)

        assertTrue(b<c)
        assertFalse(b>c)
        assertFalse(b==c)

        val d = DepartureTime(23,59)
        assertFalse(d<c)
        assertFalse(d>c)
    }
}