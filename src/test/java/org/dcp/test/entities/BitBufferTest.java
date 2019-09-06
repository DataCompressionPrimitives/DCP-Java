/**
 * Copyright 2019 DataCompressionPrimitives.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package org.dcp.test.entities;

import org.dcp.entities.bit.Bit;
import org.dcp.entities.bit.BitBuffer;
import org.junit.Test;

import java.math.BigInteger;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class BitBufferTest {

    @Test
    public void testBasicFunctionality() {
        final int sizeInBits = 20;
        final BitBuffer bitBuffer = new BitBuffer(sizeInBits);
        assertEquals(bitBuffer.toString(), "00000000000000000000");
    }

    @Test
    public void testSet() {
        final int sizeInBits = 20;
        final BitBuffer bitBuffer = new BitBuffer(sizeInBits);
        assertEquals(bitBuffer.toString(), "00000000000000000000");
        for(int bitIter = 0; bitIter < (sizeInBits/2); ++bitIter) {
            bitBuffer.setBit(bitIter, Bit.TRUE);
        }
        assertEquals(bitBuffer.toString(), "11111111110000000000");
    }

    @Test
    public void testExtendFunctionality() {
        final int sizeInBits = 20;
        final BitBuffer bitBuffer = new BitBuffer(sizeInBits);
        assertEquals(bitBuffer.toString(), "00000000000000000000");
        for(int bitIter = 0; bitIter < sizeInBits; ++bitIter) {
            bitBuffer.setBit(bitIter, Bit.valueOf(bitIter & 1));
        }
        assertEquals(bitBuffer.toString(), "01010101010101010101");
        final int newsizeInBits = 24;
        final BitBuffer bitBufferExtended = new BitBuffer(bitBuffer, newsizeInBits);
        assertEquals(bitBufferExtended.toString(), "010101010101010101010000");
    }

    @Test
    public void testTruncateFunctionality() {
        final int sizeInBits = 20;
        final BitBuffer bitBuffer = new BitBuffer(sizeInBits);
        assertEquals(bitBuffer.toString(), "00000000000000000000");
        for(int bitIter = 0; bitIter < sizeInBits; ++bitIter) {
            bitBuffer.setBit(bitIter, Bit.valueOf(bitIter & 1));
        }
        assertEquals(bitBuffer.toString(), "01010101010101010101");
        final int newsizeInBits = 4;
        final BitBuffer bitBufferShrunk = new BitBuffer(bitBuffer, newsizeInBits);
        assertEquals(bitBufferShrunk.toString(), "0101");
    }

    @Test
    public void testSliceFunctionality() {
        final int sizeInBits = 20;
        final BitBuffer bitBuffer = new BitBuffer(sizeInBits);
        assertEquals(bitBuffer.toString(), "00000000000000000000");
        for(int bitIter = 0; bitIter < sizeInBits; ++bitIter) {
            bitBuffer.setBit(bitIter, Bit.valueOf(bitIter & 1));
        }
        assertEquals(bitBuffer.toString(), "01010101010101010101");
        final BitBuffer bitBufferSlice = bitBuffer.subBuffer(0, sizeInBits);
        assertNotEquals(bitBufferSlice, bitBuffer);
        assertEquals(bitBufferSlice.toString(), bitBuffer.toString());
        final BitBuffer bitBufferSliceSmall = bitBuffer.subBuffer(2, 10);
        assertEquals(bitBufferSliceSmall.toString(), "01010101");
    }

    @Test
    public void testReadStringFunctionality() {
        final String integerToString = Long.toBinaryString(32);
        final BitBuffer bitBuffer = new BitBuffer(integerToString);
        assertEquals(bitBuffer.toString(), "100000");
        final String negativeIntegerToString = Long.toBinaryString(-32);
        final BitBuffer negBitBuffer = new BitBuffer(negativeIntegerToString);
        assertEquals(negBitBuffer.toString(), "1111111111111111111111111111111111111111111111111111111111100000");
        final BitBuffer bigBitBuffer = new BitBuffer(new BigInteger("12345678901234567890123456789012345678901234567890123456789012345678901234567890").toString(2));
        assertEquals(bigBitBuffer.toString(), "11010101001111010001110101000100011110010110110001111000010001010001111001100011010110110010010011010001011010000001001011111110001010101101101011010101010000100011101110010110010100111110001010010111010110011001111111100011001011011001110001111110000101011010010");
    }

}