/**
 * Copyright 2019-Present DataCompressionPrimitives.
 *
 * <p>Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License. You may obtain a copy of the License at
 *
 * <p>http://www.apache.org/licenses/LICENSE-2.0
 */
package org.dcp.entities.primitives;

import org.dcp.entities.bit.Bit;
import org.dcp.entities.bit.BitStreamSerializable;
import org.dcp.io.BitInputStream;
import org.dcp.io.BitOutputStream;

public class UnaryInteger implements BitStreamSerializable<UnaryInteger> {
  private long integralValue;

  public UnaryInteger(final long integralValue) {
    if (integralValue < 0) {
      throw new IllegalArgumentException(
          String.format("Unsigned numbers cannot be negative. Value: %d", integralValue));
    }
    this.integralValue = integralValue;
  }

  public UnaryInteger() {
    this(0);
  }

  public long value() {
    return integralValue;
  }

  public long getSizeInBits() {
    return integralValue + 1;
  }

  @Override
  public UnaryInteger readFrom(final BitInputStream bitInputStream) {
    int count = 0;
    while (bitInputStream.readBit().value()) {
      count++;
    }
    return new UnaryInteger(count);
  }

  @Override
  public void writeTo(final BitOutputStream bitOutputStream) {
    for (int i = 0; i < integralValue; i++) {
      bitOutputStream.writeBit(Bit.TRUE);
    }
    bitOutputStream.writeBit(Bit.FALSE);
  }

  @Override
  public boolean equals(final Object other) {
    if (this == other) return true;
    if (other == null) return false;

    if (other instanceof UnaryInteger) {
      return ((Integer) other).value() == this.value();
    } else if (other instanceof java.lang.Integer) {
      return this.value() == ((java.lang.Integer) other).intValue();
    } else if (other instanceof java.lang.Long) {
      return this.value() == ((java.lang.Long) other).longValue();
    } else if (other instanceof Number) {
      return this.value() == ((Number) other).longValue();
    }
    return false;
  }

  @Override
  public String toString() {
    return String.valueOf(integralValue);
  }
}
