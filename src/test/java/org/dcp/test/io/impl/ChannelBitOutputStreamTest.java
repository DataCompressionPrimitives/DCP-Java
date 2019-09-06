/**
 * Copyright 2019 DataCompressionPrimitives.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package org.dcp.test.io.impl;

import org.dcp.entities.bit.Bit;
import org.dcp.entities.bit.BitBuffer;
import org.dcp.io.BitOutputStream;
import org.dcp.io.impl.ChannelBitOutputStream;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.nio.channels.Channels;
import java.nio.channels.WritableByteChannel;

import static org.junit.Assert.assertEquals;

public class ChannelBitOutputStreamTest {
    @Test
    public void testBasicFunctionality() {
        final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        final WritableByteChannel channel = Channels.newChannel(outputStream);
        final BitOutputStream bitOutputStream = new ChannelBitOutputStream(channel);
        bitOutputStream.writeBit(Bit.TRUE);
        bitOutputStream.writeBit(Bit.FALSE);
        bitOutputStream.flushBits();
        assertEquals(outputStream.toByteArray()[0], (byte) 0b10000000);
    }

    @Test
    public void testWriteBitsFunctionality() {
        final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        final WritableByteChannel channel = Channels.newChannel(outputStream);
        final BitOutputStream bitOutputStream = new ChannelBitOutputStream(channel);
        final BitBuffer bitBuffer = new BitBuffer("001001");
        bitOutputStream.writeBits(bitBuffer);
        bitOutputStream.flushBits();
        assertEquals(outputStream.toByteArray()[0], (byte) 0b00100100);
    }

}
