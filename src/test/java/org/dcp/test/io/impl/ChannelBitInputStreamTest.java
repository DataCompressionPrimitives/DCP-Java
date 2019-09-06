/**
 * Copyright 2019 DataCompressionPrimitives.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package org.dcp.test.io.impl;

import org.dcp.entities.bit.Bit;
import org.dcp.entities.bit.BitList;
import org.dcp.io.BitInputStream;
import org.dcp.io.impl.ChannelBitInputStream;
import org.dcp.io.impl.MockBitInputStream;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;

import static org.junit.Assert.assertEquals;

public class ChannelBitInputStreamTest {

    @Test
    public void testBasicFunctionality(){
        final byte bytesToRead[] = { (byte)0b11001100, (byte)0b11000000 };
        final ByteArrayInputStream inputStream = new ByteArrayInputStream(bytesToRead);
        final ReadableByteChannel channel = Channels.newChannel(inputStream);
        final BitInputStream bitInputStream = new ChannelBitInputStream(channel);
        assertEquals(bitInputStream.readBit(), Bit.TRUE);
        assertEquals(bitInputStream.readBit(), Bit.TRUE);
        assertEquals(bitInputStream.readBit(), Bit.FALSE);
        assertEquals(bitInputStream.readBit(), Bit.FALSE);
        assertEquals(bitInputStream.readBit(), Bit.TRUE);
        assertEquals(bitInputStream.readBit(), Bit.TRUE);
        assertEquals(bitInputStream.readBit(), Bit.FALSE);
        assertEquals(bitInputStream.readBit(), Bit.FALSE);
        assertEquals(bitInputStream.readBit(), Bit.TRUE);
        assertEquals(bitInputStream.readBit(), Bit.TRUE);
        assertEquals(bitInputStream.readBit(), Bit.FALSE);
    }

    @Test
    public void testReadBitsFunctionality(){
        final byte bytesToRead[] = { (byte)0b11001100, (byte)0b11000000 };
        final ByteArrayInputStream inputStream = new ByteArrayInputStream(bytesToRead);
        final ReadableByteChannel channel = Channels.newChannel(inputStream);
        final BitInputStream bitInputStream = new ChannelBitInputStream(channel);
        final int sizeInBits = 5;
        final Iterable<Bit> readBits = bitInputStream.readBits(sizeInBits);
        final BitList bitList = new BitList(readBits, sizeInBits);
        assertEquals(bitList.toString(), "11001");
    }

    @Test
    public void testSkipBitsFunctionality(){
        final byte bytesToRead[] = { (byte)0b11001100, (byte)0b11000000 };
        final ByteArrayInputStream inputStream = new ByteArrayInputStream(bytesToRead);
        final ReadableByteChannel channel = Channels.newChannel(inputStream);
        final BitInputStream bitInputStream = new ChannelBitInputStream(channel);
        final int sizeInBits = 5;
        bitInputStream.skipBits(2);
        final Iterable<Bit> readBits = bitInputStream.readBits(sizeInBits);
        final BitList bitList = new BitList(readBits, sizeInBits);
        assertEquals(bitList.toString(), "00110");
    }

}
