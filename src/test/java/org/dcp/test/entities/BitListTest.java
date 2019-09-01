/**
 * Copyright 2019 DataCompressionPrimitives.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package org.dcp.test.entities;

import org.dcp.entities.bit.Bit;
import org.dcp.entities.bit.BitList;
import org.junit.Test;

import java.math.BigInteger;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class BitListTest {

    @Test
    public void testBasicFunctionality() {
        final int sizeInBits = 20;
        final BitList bitList = new BitList(sizeInBits);
        assertEquals(bitList.toString(), "00000000000000000000");
    }

    @Test
    public void testSet() {
        final int sizeInBits = 20;
        final BitList bitList = new BitList(sizeInBits);
        assertEquals(bitList.toString(), "00000000000000000000");
        for(int bitIter = 0; bitIter < (sizeInBits/2); ++bitIter) {
            bitList.setBit(bitIter, Bit.TRUE);
        }
        assertEquals(bitList.toString(), "11111111110000000000");
    }

    @Test
    public void testExtendFunctionality() {
        final int sizeInBits = 20;
        final BitList bitList = new BitList(sizeInBits);
        assertEquals(bitList.toString(), "00000000000000000000");
        for(int bitIter = 0; bitIter < sizeInBits; ++bitIter) {
            bitList.setBit(bitIter, Bit.valueOf(bitIter & 1));
        }
        assertEquals(bitList.toString(), "01010101010101010101");
        final int newsizeInBits = 24;
        final BitList bitListExtended = new BitList(bitList, newsizeInBits);
        assertEquals(bitListExtended.toString(), "010101010101010101010000");
    }

    @Test
    public void testTruncateFunctionality() {
        final int sizeInBits = 20;
        final BitList bitList = new BitList(sizeInBits);
        assertEquals(bitList.toString(), "00000000000000000000");
        for(int bitIter = 0; bitIter < sizeInBits; ++bitIter) {
            bitList.setBit(bitIter, Bit.valueOf(bitIter & 1));
        }
        assertEquals(bitList.toString(), "01010101010101010101");
        final int newsizeInBits = 4;
        final BitList bitListShrunk = new BitList(bitList, newsizeInBits);
        assertEquals(bitListShrunk.toString(), "0101");
    }

    @Test
    public void testSliceFunctionality() {
        final int sizeInBits = 20;
        final BitList bitList = new BitList(sizeInBits);
        assertEquals(bitList.toString(), "00000000000000000000");
        for(int bitIter = 0; bitIter < sizeInBits; ++bitIter) {
            bitList.setBit(bitIter, Bit.valueOf(bitIter & 1));
        }
        assertEquals(bitList.toString(), "01010101010101010101");
        final BitList bitListSlice = bitList.subList(0, sizeInBits);
        assertNotEquals(bitListSlice, bitList);
        assertEquals(bitListSlice.toString(), bitList.toString());
        final BitList bitListSliceSmall = bitList.subList(2, 10);
        assertEquals(bitListSliceSmall.toString(), "01010101");
    }

    @Test
    public void testReadStringFunctionality() {
        final String integerToString = Long.toBinaryString(32);
        final BitList bitList = new BitList(integerToString);
        assertEquals(bitList.toString(), "100000");
        final String negativeIntegerToString = Long.toBinaryString(-32);
        final BitList negBitList = new BitList(negativeIntegerToString);
        assertEquals(negBitList.toString(), "1111111111111111111111111111111111111111111111111111111111100000");
        final BitList bigBitList = new BitList(new BigInteger("12345678901234567890123456789012345678901234567890123456789012345678901234567890").toString(2));
        assertEquals(bigBitList.toString(), "11010101001111010001110101000100011110010110110001111000010001010001111001100011010110110010010011010001011010000001001011111110001010101101101011010101010000100011101110010110010100111110001010010111010110011001111111100011001011011001110001111110000101011010010");
    }

}