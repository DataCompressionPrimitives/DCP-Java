/**
 * Copyright 2019 DataCompressionPrimitives.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package org.dcp.test.entities;

import org.dcp.entities.bit.Bit;
import org.junit.Test;
import static org.junit.Assert.*;


public class BitTest {

    @Test
    public void testBasicFunctionality() {
        final Bit bitFirst = Bit.valueOf(false);
        assertEquals(bitFirst.value(), false);
        assertEquals(bitFirst.intValue(), 0);
        assertEquals(bitFirst.toString(), "0");

        final Bit bitSecond = Bit.valueOf(true);
        assertEquals(bitSecond.value(), true);
        assertEquals(bitSecond.intValue(), 1);
        assertEquals(bitSecond.toString(), "1");

        final Bit bitThird = Bit.valueOf(0);
        assertEquals(bitThird.value(), false);
        assertEquals(bitThird.intValue(), 0);
        assertEquals(bitThird.toString(), "0");

        final Bit bitFourth = Bit.valueOf(1);
        assertEquals(bitFourth.value(), true);
        assertEquals(bitFourth.intValue(), 1);
        assertEquals(bitFourth.toString(), "1");

        final Bit bitFifth = Bit.valueOf("0");
        assertEquals(bitFifth.value(), false);
        assertEquals(bitFifth.intValue(), 0);
        assertEquals(bitFifth.toString(), "0");

        final Bit bitSixth = Bit.valueOf("1");
        assertEquals(bitSixth.value(), true);
        assertEquals(bitSixth.intValue(), 1);
        assertEquals(bitSixth.toString(), "1");
    }
}