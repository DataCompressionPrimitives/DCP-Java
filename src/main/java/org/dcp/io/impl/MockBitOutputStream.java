/**
 * Copyright 2019-Present DataCompressionPrimitives.
 *
 * <p>Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License. You may obtain a copy of the License at
 *
 * <p>http://www.apache.org/licenses/LICENSE-2.0
 */
package org.dcp.io.impl;

import static org.dcp.entities.Constants.*;

import org.dcp.entities.bit.Bit;
import org.dcp.io.BitOutputStream;

public class MockBitOutputStream implements BitOutputStream {

  final StringBuffer bitString;

  public MockBitOutputStream(final int size) {
    bitString = new StringBuffer(size);
  }

  public MockBitOutputStream() {
    this(BITS_IN_A_BUFFER);
  }

  @Override
  public void writeBit(Bit bit) {
    if (bit.value()) bitString.append('1');
    else bitString.append('0');
  }

  @Override
  public void flushBits() {
    int remaining = (bitString.length() % BITS_IN_A_BYTE);
    if (remaining == 0) return;
    remaining = BITS_IN_A_BYTE - remaining;
    while (remaining > 0) {
      writeBit(Bit.FALSE);
      remaining--;
    }
  }

  @Override
  public String toString() {
    return bitString.toString();
  }
}
