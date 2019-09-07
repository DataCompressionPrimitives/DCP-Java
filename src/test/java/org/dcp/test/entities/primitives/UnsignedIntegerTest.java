/**
 * Copyright 2019 DataCompressionPrimitives.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package org.dcp.test.entities.primitives;

import org.dcp.entities.Constants;
import org.dcp.entities.primitives.UnsignedInteger;
import org.dcp.io.BitOutputStream;
import org.dcp.io.impl.MockBitInputStream;
import org.dcp.io.impl.MockBitOutputStream;
import org.junit.Test;

import static org.junit.Assert.*;

public class UnsignedIntegerTest {

    @Test
    public void testBasicFunctionality() {
        final UnsignedInteger unsignedIntegerDefault = new UnsignedInteger(4);
        assertEquals(unsignedIntegerDefault.value(), 0);

        try {
            final UnsignedInteger unsignedIntegerUnParsable = new UnsignedInteger(19, 4);
            assertTrue(false);
        } catch(IllegalArgumentException ie) {
            //Good!
        }

        try {
            final UnsignedInteger unsignedIntegernegative = new UnsignedInteger(-5, 4);
            assertTrue(false);
        } catch(IllegalArgumentException ie) {
            //Good!
        }

        final UnsignedInteger unsignedIntegerParsable = new UnsignedInteger(Long.MAX_VALUE, Constants.BITS_IN_A_INTEGER - 1);
        assertEquals(unsignedIntegerParsable.value(), Long.MAX_VALUE);
    }

    @Test
    public void testReadFunctionality() {
        assertEquals(new UnsignedInteger(4).readFrom(new MockBitInputStream("1011")).value(), 11);
        assertEquals(new UnsignedInteger(3).readFrom(new MockBitInputStream("011")).value(), 3);
        assertEquals(new UnsignedInteger(2).readFrom(new MockBitInputStream("10")).value(), 2);
        assertEquals(new UnsignedInteger(1).readFrom(new MockBitInputStream("0")).value(), 0);
        assertEquals(new UnsignedInteger(16).readFrom(new MockBitInputStream("1111111111111111")).value(), 65535);
    }

    @Test
    public void testWriteFunctionality() {
        final BitOutputStream bitOutputStreamFirst = new MockBitOutputStream();
        new UnsignedInteger(1, 1).writeTo(bitOutputStreamFirst);
        assertEquals(bitOutputStreamFirst.toString(), "1");
        new UnsignedInteger(13, 4).writeTo(bitOutputStreamFirst);
        assertEquals(bitOutputStreamFirst.toString(), "11101");
        new UnsignedInteger(11, 4).writeTo(bitOutputStreamFirst);
        assertEquals(bitOutputStreamFirst.toString(), "111011011");

        final BitOutputStream bitOutputStreamSecond = new MockBitOutputStream();
        new UnsignedInteger(0, 19).writeTo(bitOutputStreamSecond);
        assertEquals(bitOutputStreamSecond.toString(), "0000000000000000000");
        new UnsignedInteger(4, 3).writeTo(bitOutputStreamSecond);
        assertEquals(bitOutputStreamSecond.toString(), "0000000000000000000100");
        new UnsignedInteger(4, 4).writeTo(bitOutputStreamSecond);
        assertEquals(bitOutputStreamSecond.toString(), "00000000000000000001000100");
        new UnsignedInteger(1, 5).writeTo(bitOutputStreamSecond);
        assertEquals(bitOutputStreamSecond.toString(), "0000000000000000000100010000001");
    }

}
