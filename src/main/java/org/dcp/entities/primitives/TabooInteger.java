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
import org.dcp.util.EntropyUtil;

public class TabooInteger implements BitStreamSerializable<TabooInteger> {
  private final long integralValue;
  private final UnsignedInteger tabooPattern;

  public TabooInteger(final long integralValue, final UnsignedInteger tabooPattern) {
    if (integralValue < 0) {
      throw new IllegalArgumentException(
          String.format("Unsigned numbers cannot be negative. Value: %d", integralValue));
    }
    if (tabooPattern == null) {
      throw new IllegalArgumentException("Taboo pattern must not be null.");
    }
    if (tabooPattern.value() == integralValue) {
      throw new IllegalArgumentException("Taboo pattern must not be the same as the value.");
    }
    this.integralValue = integralValue;
    this.tabooPattern = tabooPattern;
  }

  public TabooInteger(final long integralValue, final long base) {
    this(integralValue, new UnsignedInteger(base, EntropyUtil.findSizeOfBitsToHold(base)));
  }

  public TabooInteger(final long integralValue) {
    this(integralValue, 2);
  }

  public long value() {
    return integralValue;
  }

  public UnsignedInteger getTabooPattern() {
    return tabooPattern;
  }

  public long getSizeInBits() {
    final int n = tabooPattern.getSizeInBits();
    final long base = EntropyUtil.findMaximumValueRepresentible(n); // 2^n - 1
    if (integralValue == 0) {
      return n; // Only the taboo pattern
    }
    int digits = EntropyUtil.findSizeOfDigitsToHold(integralValue, base);
    return (digits) * n + n;
  }

  @Override
  public TabooInteger readFrom(final BitInputStream bitInputStream) {
    final int n = tabooPattern.getSizeInBits();
    final long taboo = tabooPattern.value();
    long value = 0;
    int digits = 0;
    while (true) {
      long chunk = 0;
      for (int i = 0; i < n; i++) {
        chunk = (chunk << 1) | (bitInputStream.readBit().value() ? 1 : 0);
      }
      if (chunk == taboo) {
        if (digits == 0) {
          return new TabooInteger(0, tabooPattern);
        }
        break;
      } else {
        value = value * (taboo) + chunk;
        digits++;
      }
    }
    return new TabooInteger(value, tabooPattern);
  }

  @Override
  public void writeTo(final BitOutputStream bitOutputStream) {
    final int n = tabooPattern.getSizeInBits();
    final long taboo = tabooPattern.value();
    if (integralValue == 0) {
      tabooPattern.writeTo(bitOutputStream);
      return;
    }
    // encode value in base-(2^n-1)
    long v = integralValue;
    java.util.ArrayList<java.lang.Integer> digits = new java.util.ArrayList<>();
    while (v > 0) {
      digits.add(0, java.lang.Integer.valueOf((int) (v % taboo)));
      v /= taboo;
    }
    // output each digit as n bits
    for (int idx = 0; idx < digits.size(); idx++) {
      int d = ((java.lang.Integer) digits.get(idx)).intValue();
      for (int i = n - 1; i >= 0; i--) {
        bitOutputStream.writeBit(((d >> i) & 1) == 1 ? Bit.TRUE : Bit.FALSE);
      }
    }
    tabooPattern.writeTo(bitOutputStream);
  }

  @Override
  public boolean equals(final Object other) {
    if (this == other) return true;
    if (other == null) return false;

    if (other instanceof TabooInteger) {
      return ((TabooInteger) other).value() == this.value();
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
