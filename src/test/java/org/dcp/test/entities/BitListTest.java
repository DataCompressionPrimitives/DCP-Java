/**
 * Copyright 2019 DataCompressionPrimitives.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package org.dcp.test.entities;

import org.dcp.entities.Bit;
import org.dcp.entities.BitList;
import org.junit.Test;

import java.math.BigInteger;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class BitListTest {

    @Test
    public void testBasicFunctionality() {
        final int bitSize = 20;
        final BitList bitList = new BitList(bitSize);
        assertEquals(bitList.toString(), "00000000000000000000");
    }

    @Test
    public void testSet() {
        final int bitSize = 20;
        final BitList bitList = new BitList(bitSize);
        assertEquals(bitList.toString(), "00000000000000000000");
        for(int bitIter = 0; bitIter < (bitSize/2); ++bitIter) {
            bitList.setBit(bitIter, Bit.TRUE);
        }
        assertEquals(bitList.toString(), "11111111110000000000");
    }

    @Test
    public void testExtendFunctionality() {
        final int bitSize = 20;
        final BitList bitList = new BitList(bitSize);
        assertEquals(bitList.toString(), "00000000000000000000");
        for(int bitIter = 0; bitIter < bitSize; ++bitIter) {
            bitList.setBit(bitIter, Bit.valueOf(bitIter & 1));
        }
        assertEquals(bitList.toString(), "01010101010101010101");
        final int newBitSize = 24;
        final BitList bitListExtended = new BitList(bitList, newBitSize);
        assertEquals(bitListExtended.toString(), "010101010101010101010000");
    }

    @Test
    public void testTruncateFunctionality() {
        final int bitSize = 20;
        final BitList bitList = new BitList(bitSize);
        assertEquals(bitList.toString(), "00000000000000000000");
        for(int bitIter = 0; bitIter < bitSize; ++bitIter) {
            bitList.setBit(bitIter, Bit.valueOf(bitIter & 1));
        }
        assertEquals(bitList.toString(), "01010101010101010101");
        final int newBitSize = 4;
        final BitList bitListShrunk = new BitList(bitList, newBitSize);
        assertEquals(bitListShrunk.toString(), "0101");
    }

    @Test
    public void testSliceFunctionality() {
        final int bitSize = 20;
        final BitList bitList = new BitList(bitSize);
        assertEquals(bitList.toString(), "00000000000000000000");
        for(int bitIter = 0; bitIter < bitSize; ++bitIter) {
            bitList.setBit(bitIter, Bit.valueOf(bitIter & 1));
        }
        assertEquals(bitList.toString(), "01010101010101010101");
        final BitList bitListSlice = bitList.subList(0, bitSize);
        assertNotEquals(bitListSlice, bitList);
        assertEquals(bitListSlice.toString(), bitList.toString());
        final BitList bitListSliceSmall = bitList.subList(2, 10);
        assertEquals(bitListSliceSmall.toString(), "01010101");
    }

    @Test
    public void testReadStringFunctionality() {
        final String integerToString = Integer.toBinaryString(32);
        final BitList bitList = new BitList(integerToString);
        assertEquals(bitList.toString(), "100000");
        final String negativeIntegerToString = Integer.toBinaryString(-32);
        final BitList negBitList = new BitList(negativeIntegerToString);
        assertEquals(negBitList.toString(), "11111111111111111111111111100000");
        final BitList bigBitList = new BitList(new BigInteger("12345678901234567890").toString(2));
        assertEquals(bigBitList.toString(), "1010101101010100101010011000110011101011000111110000101011010010");
    }

}