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
import java.nio.channels.WritableByteChannel;
import org.dcp.entities.bit.Bit;
import org.dcp.io.BitOutputStream;

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
    if (mask != LAST_BIT) {
      writeByte(current);
      mask = FIRST_BIT;
      current = 0;
    }
  }
}
