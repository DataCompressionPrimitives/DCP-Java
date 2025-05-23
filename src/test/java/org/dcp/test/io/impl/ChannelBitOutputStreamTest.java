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

import java.io.ByteArrayOutputStream;
import java.nio.channels.Channels;
import java.nio.channels.WritableByteChannel;
import org.dcp.entities.bit.Bit;
import org.dcp.entities.bit.BitBuffer;
import org.dcp.io.BitOutputStream;
import org.dcp.io.impl.ChannelBitOutputStream;
import org.junit.Test;

public class ChannelBitOutputStreamTest {
  @Test
  public void testBasicFunctionality() {
    final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    final WritableByteChannel channel = Channels.newChannel(outputStream);
    try (final BitOutputStream bitOutputStream = new ChannelBitOutputStream(channel)) {
      bitOutputStream.writeBit(Bit.TRUE);
      bitOutputStream.writeBit(Bit.FALSE);
      bitOutputStream.flushBits();
    }
    assertEquals(outputStream.toByteArray()[0], (byte) 0b10000000);
  }

  @Test
  public void testWriteBitsFunctionality() {
    final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    final WritableByteChannel channel = Channels.newChannel(outputStream);
    try (final BitOutputStream bitOutputStream = new ChannelBitOutputStream(channel)) {
      final BitBuffer bitBuffer = new BitBuffer("00100100");
      bitOutputStream.writeBits(bitBuffer);
    }
    assertEquals(outputStream.toByteArray()[0], (byte) 0b00100100);
  }

  @Test
  public void testCloseFunctionality() {
    final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    final WritableByteChannel channel = Channels.newChannel(outputStream);
    try (final BitOutputStream bitOutputStream = new ChannelBitOutputStream(channel)) {}
  }
}
