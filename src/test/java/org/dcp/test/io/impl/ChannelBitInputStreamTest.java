/**
 * Copyright 2019-Present DataCompressionPrimitives.
 *
 * <p>Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License. You may obtain a copy of the License at
 *
 * <p>http://www.apache.org/licenses/LICENSE-2.0
 */
package org.dcp.test.io.impl;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayInputStream;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import org.dcp.entities.bit.Bit;
import org.dcp.entities.bit.BitBuffer;
import org.dcp.io.BitInputStream;
import org.dcp.io.impl.ChannelBitInputStream;
import org.junit.Test;

public class ChannelBitInputStreamTest {

  @Test
  public void testBasicFunctionality() {
    final byte bytesToRead[] = {(byte) 0b11001100, (byte) 0b11000000};
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
  public void testReadBitsFunctionality() {
    final byte bytesToRead[] = {(byte) 0b11001100, (byte) 0b11000000};
    final ByteArrayInputStream inputStream = new ByteArrayInputStream(bytesToRead);
    final ReadableByteChannel channel = Channels.newChannel(inputStream);
    final BitInputStream bitInputStream = new ChannelBitInputStream(channel);
    final int sizeInBits = 5;
    final Iterable<Bit> readBits = bitInputStream.readBits(sizeInBits);
    final BitBuffer bitBuffer = new BitBuffer(readBits, sizeInBits);
    assertEquals(bitBuffer.toString(), "11001");
  }

  @Test
  public void testSkipBitsFunctionality() {
    final byte bytesToRead[] = {(byte) 0b11001100, (byte) 0b11000000};
    final ByteArrayInputStream inputStream = new ByteArrayInputStream(bytesToRead);
    final ReadableByteChannel channel = Channels.newChannel(inputStream);
    final BitInputStream bitInputStream = new ChannelBitInputStream(channel);
    final int sizeInBits = 5;
    bitInputStream.skipBits(2);
    final Iterable<Bit> readBits = bitInputStream.readBits(sizeInBits);
    final BitBuffer bitBuffer = new BitBuffer(readBits, sizeInBits);
    assertEquals(bitBuffer.toString(), "00110");
  }
}
