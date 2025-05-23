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

import org.dcp.entities.bit.Bit;
import org.dcp.entities.bit.BitBuffer;
import org.dcp.io.BitOutputStream;
import org.dcp.io.impl.MockBitOutputStream;
import org.junit.Test;

public class MockBitOutputStreamTest {

  @Test
  public void testBasicFunctionality() {
    final BitOutputStream bitOutputStream = new MockBitOutputStream();
    bitOutputStream.writeBit(Bit.TRUE);
    bitOutputStream.writeBit(Bit.FALSE);
    assertEquals(bitOutputStream.toString(), "10");
  }

  @Test
  public void testWriteBitsFunctionality() {
    final BitOutputStream bitOutputStream = new MockBitOutputStream();
    final BitBuffer bitBuffer = new BitBuffer("001001");
    bitOutputStream.writeBits(bitBuffer);
    assertEquals(bitOutputStream.toString(), bitBuffer.toString());
  }

  @Test
  public void testFlushFunctionality() {
    final BitOutputStream bitOutputStream = new MockBitOutputStream();
    final BitBuffer bitBuffer = new BitBuffer("001001");
    bitOutputStream.writeBits(bitBuffer);
    bitOutputStream.flushBits();
    assertEquals(bitOutputStream.toString(), bitBuffer.toString() + "00");

    final BitOutputStream bitOutputStreamSecond = new MockBitOutputStream();
    final BitBuffer bitBufferSecond = new BitBuffer("00100100");
    bitOutputStreamSecond.writeBits(bitBufferSecond);
    bitOutputStreamSecond.flushBits();
    assertEquals(bitOutputStreamSecond.toString(), bitBufferSecond.toString());
  }
}
