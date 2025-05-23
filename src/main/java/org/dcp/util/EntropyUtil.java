/**
 * Copyright 2019-Present DataCompressionPrimitives.
 *
 * <p>Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License. You may obtain a copy of the License at
 *
 * <p>http://www.apache.org/licenses/LICENSE-2.0
 */
package org.dcp.util;

import static org.dcp.entities.Constants.*;

import java.math.BigInteger;

public class EntropyUtil {

  protected static int findSizeOfBitsToHoldWithComplement(final long givenValue) {
    int bitCount = 0;
    long bitShiftValue = givenValue;
    while (bitShiftValue != 0) {
      bitShiftValue >>>= 1;
      ++bitCount;
    }
    return bitCount;
  }

  public static int findSizeOfBitsToHold(final long givenValue) {
    final boolean isNegative = (givenValue < 0);
    final int bitCount =
        findSizeOfBitsToHoldWithComplement(isNegative ? -givenValue : givenValue)
            + (isNegative ? 1 : 0);
    return bitCount;
  }

  public static int findSizeOfBitsToHoldBig(final BigInteger givenValue) {
    final boolean isNegative = (givenValue.signum() < 0);
    final int bitCount = givenValue.abs().bitLength() + (isNegative ? 1 : 0);
    return bitCount;
  }

  public static long findMaximumValueRepresentible(final int sizeInBits) {
    final int minSizeInBits = 0;
    final int maxSizeInBits = BITS_IN_A_INTEGER - 1;
    if (sizeInBits < minSizeInBits)
      throw new IllegalArgumentException(
          String.format(
              "Size lesser than Minimum. Size: %d Minimum: %d", sizeInBits, minSizeInBits));
    if (sizeInBits > maxSizeInBits)
      throw new IllegalArgumentException(
          String.format(
              "Size greater than Maximum. Size: %d Maximum: %d", sizeInBits, maxSizeInBits));
    return ~(-1L << sizeInBits);
  }

  public static BigInteger findMaximumValueRepresentibleBig(final int sizeInBits) {
    final int minSizeInBits = 0;
    if (sizeInBits < minSizeInBits)
      throw new IllegalArgumentException(
          String.format(
              "Size lesser than Minimum. Size: %d Minimum: %d", sizeInBits, minSizeInBits));
    return BigInteger.ONE.shiftLeft(sizeInBits).subtract(BigInteger.ONE);
  }
}
