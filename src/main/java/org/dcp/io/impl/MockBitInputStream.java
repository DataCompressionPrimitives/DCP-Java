/**
 * Copyright 2019-Present DataCompressionPrimitives.
 *
 * <p>Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License. You may obtain a copy of the License at
 *
 * <p>http://www.apache.org/licenses/LICENSE-2.0
 */
package org.dcp.io.impl;

import org.dcp.entities.bit.Bit;
import org.dcp.io.BitInputStream;

public class MockBitInputStream implements BitInputStream {

  final String bitString;
  int bitIter;

  public MockBitInputStream(final String bitString) {
    if (null == bitString) throw new IllegalArgumentException("Given BitString is NULL.");
    this.bitString = bitString;
    this.bitIter = 0;
  }

  @Override
  public Bit readBit() {
    final char readChar = this.bitString.charAt(this.bitIter++);
    return Bit.valueOf(readChar);
  }

  @Override
  public String toString() {
    return bitString;
  }
}
