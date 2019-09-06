/**
 * Copyright 2019 DataCompressionPrimitives.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package org.dcp.test.io.impl;

import org.dcp.entities.bit.Bit;
import org.dcp.entities.bit.BitList;
import org.dcp.io.BitOutputStream;
import org.dcp.io.impl.MockBitOutputStream;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class MockBitOutputStreamTest {

    @Test
    public void testBasicFunctionality(){
        final BitOutputStream bitOutputStream = new MockBitOutputStream();
        bitOutputStream.writeBit(Bit.TRUE);
        bitOutputStream.writeBit(Bit.FALSE);
        assertEquals(bitOutputStream.toString(), "10");
    }

    @Test
    public void testWriteBitsFunctionality(){
        final BitOutputStream bitOutputStream = new MockBitOutputStream();
        final BitList bitList = new BitList("001001");
        bitOutputStream.writeBits(bitList);
        assertEquals(bitOutputStream.toString(), bitList.toString());
    }

    @Test
    public void testFlushFunctionality(){
        final BitOutputStream bitOutputStream = new MockBitOutputStream();
        final BitList bitList = new BitList("001001");
        bitOutputStream.writeBits(bitList);
        bitOutputStream.flushBits();
        assertEquals(bitOutputStream.toString(), bitList.toString() + "00");
    }

}
