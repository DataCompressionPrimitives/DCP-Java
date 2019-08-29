package org.dcp.test.entities;

import org.dcp.entities.Bit;
import org.junit.Test;
import static org.junit.Assert.*;


public class BitTest {

    @Test
    public void testBasicFunctionality() {
        final Bit bitFirst = new Bit(false);
        assertEquals(bitFirst.value(), false);
        assertEquals(bitFirst.toInt(), 0);
        assertEquals(bitFirst.toString(), "0");

        final Bit bitSecond = new Bit(true);
        assertEquals(bitSecond.value(), true);
        assertEquals(bitSecond.toInt(), 1);
        assertEquals(bitSecond.toString(), "1");
    }
}