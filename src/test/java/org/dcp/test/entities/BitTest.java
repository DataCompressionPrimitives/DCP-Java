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
    }
}