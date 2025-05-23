/**
 * Copyright 2019-Present DataCompressionPrimitives.
 *
 * <p>Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License. You may obtain a copy of the License at
 *
 * <p>http://www.apache.org/licenses/LICENSE-2.0
 */
package org.dcp.entities.primitives;

import org.dcp.entities.bit.BitStreamSerializable;
import org.dcp.io.BitInputStream;
import org.dcp.io.BitOutputStream;
import org.dcp.util.EntropyUtil;

public class Integer implements BitStreamSerializable {

  private final UnsignedInteger unsignedInteger;
  private final int sizeInBits;
  private final boolean isNegative;

  public Integer(final long integralValue, final int sizeInBits) {
    isNegative = (integralValue < 0);
    final long unsignedIntValue = isNegative ? -integralValue : integralValue;
    final int sizeInBitsUnsigned = sizeInBits - 1;
    final long neededSizeInBits = EntropyUtil.findSizeOfBitsToHold(unsignedIntValue);
    if (neededSizeInBits > sizeInBitsUnsigned)
      throw new IllegalArgumentException(
          String.format(
              "Size too small to Hold Value. NeededSize: %d SizeUnsigned: %d",
              neededSizeInBits, sizeInBitsUnsigned));
    this.sizeInBits = sizeInBits;
    this.unsignedInteger = new UnsignedInteger(unsignedIntValue, sizeInBitsUnsigned);
  }

  public Integer(final int sizeInBits) {
    this(0, sizeInBits);
  }

  public long value() {
    final long unsignedValue = unsignedInteger.value();
    return isNegative ? -unsignedValue : unsignedValue;
  }

  public int getSizeInBits() {
    return sizeInBits;
  }

  @Override
  public Integer readFrom(final BitInputStream bitInputStream) {
    boolean isNegative = bitInputStream.readBit().value();
    final UnsignedInteger readInteger = unsignedInteger.readFrom(bitInputStream);
    final long unsignedValue = readInteger.value();
    final long integralValue = isNegative ? -unsignedValue : unsignedValue;
    return new Integer(integralValue, readInteger.getSizeInBits() + 1);
  }

  @Override
  public void writeTo(final BitOutputStream bitOutputStream) {
    new Boolean(isNegative).writeTo(bitOutputStream);
    unsignedInteger.writeTo(bitOutputStream);
  }

  @Override
  public boolean equals(final Object other) {
    if (this == other) return true;
    if (other == null) return false;

    if (other instanceof Integer) {
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
    return String.valueOf(value());
  }
}
