/**
 * Copyright 2019 DataCompressionPrimitives.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package org.dcp.test.entities.primitives;

import org.dcp.entities.primitives.UnsignedInteger;
import org.dcp.entities.primitives.VarUnsignedInteger;
import org.dcp.io.BitInputStream;
import org.dcp.io.BitOutputStream;
import org.dcp.io.impl.MockBitInputStream;
import org.dcp.io.impl.MockBitOutputStream;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class VarUnsignedIntegerTest {

    @Test
    public void testBasicFunctionality() {
        try {
            final VarUnsignedInteger varUnsignedIntegerTooSmall = new VarUnsignedInteger( new UnsignedInteger(231, 12), 1);
            assertTrue(false);
        } catch(IllegalArgumentException ie) {
            //Good!
        }

        try {
            final VarUnsignedInteger varUnsignedIntegerTooUnoptimal = new VarUnsignedInteger( new UnsignedInteger(231, 12), 2);
            assertTrue(false);
        } catch(IllegalArgumentException ie) {
            //Good!
        }

        try {
            final VarUnsignedInteger varUnsignedIntegerBorderLine = new VarUnsignedInteger( new UnsignedInteger(231, 12), 7);
            assertTrue(false);
        } catch(IllegalArgumentException ie) {
            //Good!
        }

        try {
            final VarUnsignedInteger varUnsignedIntegerBorderLine = new VarUnsignedInteger( new UnsignedInteger(231, 12), 7);
            assertTrue(false);
        } catch(IllegalArgumentException ie) {
            //Good!
        }

        final VarUnsignedInteger varUnsignedIntegerDefault = new VarUnsignedInteger( new UnsignedInteger(231, 12), 8);
        assertEquals(varUnsignedIntegerDefault.value(), 231);

        try {
            final VarUnsignedInteger varUnsignedIntegerTooBig = new VarUnsignedInteger( new UnsignedInteger(231, 12), 12);
            assertTrue(false);
        } catch(IllegalArgumentException ie) {
            //Good!
        }

        try {
            final VarUnsignedInteger varUnsignedIntegerTooBigAgain = new VarUnsignedInteger( new UnsignedInteger(231, 12), 13);
            assertTrue(false);
        } catch(IllegalArgumentException ie) {
            //Good!
        }

    }

    @Test
    public void testReadFunctionality() {
        final BitInputStream bitInputStreamFirst = new MockBitInputStream("1000000000000111");
        final VarUnsignedInteger varUnsignedIntegerFirst = new VarUnsignedInteger(new UnsignedInteger(0, 12), 8).readFrom(bitInputStreamFirst);
        assertEquals(varUnsignedIntegerFirst.value(), 7);

        final BitInputStream bitInputStreamSecond = new MockBitInputStream("00000111");
        final VarUnsignedInteger varUnsignedIntegerSecond = new VarUnsignedInteger(new UnsignedInteger(0, 12), 8).readFrom(bitInputStreamSecond);
        assertEquals(varUnsignedIntegerSecond.value(), 7);

        final BitInputStream bitInputStreamThird = new MockBitInputStream("00000111");
        final VarUnsignedInteger varUnsignedIntegerThird = new VarUnsignedInteger(new UnsignedInteger(0, 18), 8).readFrom(bitInputStreamThird);
        assertEquals(varUnsignedIntegerThird.value(), 7);

        final BitInputStream bitInputStreamFourth = new MockBitInputStream("1000000000000111");
        final VarUnsignedInteger varUnsignedIntegerFourth = new VarUnsignedInteger(new UnsignedInteger(0, 14), 8).readFrom(bitInputStreamFourth);
        assertEquals(varUnsignedIntegerFourth.value(), 7);
    }

    @Test
    public void testWriteFunctionality() {
        final BitOutputStream bitOutputStreamFirst = new MockBitOutputStream();
        new VarUnsignedInteger(new UnsignedInteger(7, 12), 8).writeTo(bitOutputStreamFirst);
        assertEquals(bitOutputStreamFirst.toString(), "1000000000000111");

        final BitOutputStream bitOutputStreamSecond = new MockBitOutputStream();
        new VarUnsignedInteger(new UnsignedInteger(91, 13), 8).writeTo(bitOutputStreamSecond);
        assertEquals(bitOutputStreamSecond.toString(), "1000000001011011");
    }

}
