/**
 * Copyright 2019 DataCompressionPrimitives.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package org.dcp.io.impl;

import org.dcp.entities.bit.Bit;
import org.dcp.io.BitInputStream;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.ReadableByteChannel;

public class ChannelBitInputStream implements BitInputStream {

    private static final int FIRST_BIT = 128;
    private static final int LAST_BIT = 1;

    private final ReadableByteChannel byteChannel;
    private final ByteBuffer buffer;
    private int mask;
    private byte current;

    public ChannelBitInputStream(ReadableByteChannel byteChannel) {
        this.byteChannel = byteChannel;
        this.buffer = ByteBuffer.allocateDirect(1);
        this.mask = FIRST_BIT;
    }

    protected byte readByte() {
        if (buffer.hasRemaining()) {
            final int count;
            try {
                count = byteChannel.read(buffer);
            } catch (IOException e) {
                throw new IllegalStateException(e);
            }
            if (count < 1) {
                throw new IllegalStateException();
            }
        }
        final byte readByte = buffer.get(0);
        buffer.rewind();
        return readByte;
    }

    @Override
    public Bit readBit() {
        if (mask == FIRST_BIT) {
            current = readByte();
        }
        final Bit readBit = Bit.valueOf((current & mask) != 0);
        if (mask == LAST_BIT) {
            mask = FIRST_BIT;
        } else {
            mask >>>= 1;
        }
        return readBit;
    }

}
