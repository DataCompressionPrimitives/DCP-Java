/**
 * Copyright 2019 DataCompressionPrimitives.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package org.dcp.io.impl;

import org.dcp.entities.bit.Bit;
import org.dcp.io.BitOutputStream;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.WritableByteChannel;

public class ChannelBitOutputStream implements BitOutputStream {

    private static final int FIRST_BIT = 128;
    private static final int LAST_BIT = 1;

    private final WritableByteChannel byteChannel;
    private final ByteBuffer buffer;
    private int mask;
    private byte current;

    public ChannelBitOutputStream(WritableByteChannel byteChannel) {
        this.byteChannel = byteChannel;
        this.buffer = ByteBuffer.allocateDirect(1);
        this.mask = FIRST_BIT;
    }

    protected void writeByte(byte byteWrite) {
        buffer.put(0, byteWrite);
        try {
            byteChannel.write(buffer);
        } catch (IOException ioException) {
            throw new IllegalStateException(ioException);
        }
        buffer.rewind();
    }

    @Override
    public void writeBit(Bit bit) {
        if (bit.value()) {
            current |= mask;
        }
        if (mask != LAST_BIT) {
            mask >>>= 1;
        } else {
            writeByte(current);
            mask = FIRST_BIT;
            current = 0;
        }
    }

    @Override
    public void flushBits() {
        if(mask != LAST_BIT) {
            writeByte(current);
            mask = FIRST_BIT;
            current = 0;
        }
    }
}
