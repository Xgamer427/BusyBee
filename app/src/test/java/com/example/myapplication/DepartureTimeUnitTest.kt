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
        val aa = a.plusMinutes(10)
        assertEquals("Base object not change", DepartureTime(12,10), a)
        assertEquals("10+10 min is 20 min", DepartureTime(12,20), aa)


        val b = DepartureTime(12,10)
        val bb = b.plusMinutes(50)
        assertEquals("Base object not change", DepartureTime(12,10), b)
        assertEquals("12:10 plus 50 min is 13:00 min", DepartureTime(13,0), bb)

        val c = DepartureTime(23,59)
        val cc = c.plusMinutes(70)
        assertEquals("Base object not change", DepartureTime(23,59), c)
        assertEquals("23:59 plus 70 min is 1:09 min", DepartureTime(1,9), cc)

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

    @Test
    fun testmultiplePlusMinutes(){
        val b = DepartureTime(12,10)
        val c = b.plusMinutes(20).plusMinutes(2)
        assertEquals("Base time object not changes", 10, b.min)
        assertEquals("10+20+2 minutes is 32 minutes even in stack", 32, c.min)
    }

    @Test
    fun testmultipleMinusMinutes(){
        val b = DepartureTime(12,10)
        val c = b.minusMinutes(3)
        assertEquals("Base time object not changes", 10, b.min)
        assertEquals("Base time object not changes", 12, b.hour)

        assertEquals("10-3 minutes is 7 minutes", 7, c.min)
        assertEquals("10-3 minutes should not change hour", 12, c.hour)

        val d = DepartureTime(12,10)
        val e = d.minusMinutes(6).minusMinutes(6)
        assertEquals("Base time object not changes", 10, d.min)
        assertEquals("Base time object not changes", 12, d.hour)
        assertEquals("12:10 - 12 minutes should be 11:58 even in stack", 11, e.hour)
        assertEquals("12:10 - 12 minutes should be 11:58 even in stack", 58, e.min)

        val f = DepartureTime(0,10)
        val g = f.minusMinutes(12)
        assertEquals("Base time object not changes", DepartureTime(0,10), f)
        assertEquals("12:10 minutes is 12 minutes should be 23:58 even in stack", DepartureTime(23,58), g)
    }

}