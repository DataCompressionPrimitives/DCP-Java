/**
 * Copyright 2019-Present DataCompressionPrimitives.
 *
 * <p>Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License. You may obtain a copy of the License at
 *
 * <p>http://www.apache.org/licenses/LICENSE-2.0
 */
package org.dcp.io.impl;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.ReadableByteChannel;
import org.dcp.entities.bit.Bit;
import org.dcp.io.BitInputStream;

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
      } catch (IOException ioException) {
        throw new IllegalStateException(ioException);
      }
      if (count < 1) {
        throw new IllegalStateException("Unable to read from ReadableByteChannel.");
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
    if (mask != LAST_BIT) {
      mask >>>= 1;
    } else {
      mask = FIRST_BIT;
    }
    return readBit;
  }
}
