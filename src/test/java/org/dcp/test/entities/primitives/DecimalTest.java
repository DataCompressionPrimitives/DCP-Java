/**
 * Copyright 2019-Present DataCompressionPrimitives.
 *
 * <p>Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License. You may obtain a copy of the License at
 *
 * <p>http://www.apache.org/licenses/LICENSE-2.0
 */
package org.dcp.test.entities.primitives;

import static org.junit.Assert.*;

import org.dcp.entities.primitives.Decimal;
import org.dcp.entities.primitives.Decimal.DecimalIntegral;
import org.dcp.io.BitInputStream;
import org.dcp.io.BitOutputStream;
import org.dcp.io.impl.MockBitInputStream;
import org.dcp.io.impl.MockBitOutputStream;
import org.junit.Test;

public class DecimalTest {

  protected static void checkConversion(final float value) {
    new DecimalIntegral(value);
  }

  protected static void checkConversion(final double value) {
    new DecimalIntegral(value);
  }

  protected static void checkSerialization(final DecimalIntegral value) {
    final BitOutputStream bitOutputStream = new MockBitOutputStream();
    new Decimal(value).writeTo(bitOutputStream);
    final BitInputStream bitInputStream = new MockBitInputStream(bitOutputStream.toString());
    assertTrue(new Decimal(value).readFrom(bitInputStream).equals(value));
  }

  protected static void checkSerialization(final float value) {
    final BitOutputStream bitOutputStream = new MockBitOutputStream();
    new Decimal(value).writeTo(bitOutputStream);
    final BitInputStream bitInputStream = new MockBitInputStream(bitOutputStream.toString());
    assertTrue(new Decimal(value).readFrom(bitInputStream).equals(value));
  }

  protected static void checkSerialization(final double value) {
    final BitOutputStream bitOutputStream = new MockBitOutputStream();
    new Decimal(value).writeTo(bitOutputStream);
    final BitInputStream bitInputStream = new MockBitInputStream(bitOutputStream.toString());
    assertTrue(new Decimal(value).readFrom(bitInputStream).equals(value));
  }

  @Test
  public void testBasicFunctionality() {
    checkConversion(0.0f);
    checkConversion(-0.0f);
    checkConversion(0.1f);
    checkConversion(-0.1f);
    checkConversion(Float.POSITIVE_INFINITY);
    checkConversion(Float.NEGATIVE_INFINITY);
    checkConversion(Float.NaN);
    checkConversion(Float.MAX_VALUE);
    checkConversion(Float.MIN_VALUE);

    checkConversion(0.0);
    checkConversion(-0.0);
    checkConversion(0.1);
    checkConversion(-0.1);
    checkConversion(Math.PI);
    checkConversion(-Math.PI);
    checkConversion(Math.E);
    checkConversion(-Math.E);
    checkConversion(Double.POSITIVE_INFINITY);
    checkConversion(Double.NEGATIVE_INFINITY);
    checkConversion(Double.NaN);
    checkConversion(Double.MAX_VALUE);
    checkConversion(Double.MIN_VALUE);
  }

  @Test
  public void testReadWriteFunctionality() {
    checkSerialization(0.0f);
    checkSerialization(-0.0f);
    checkSerialization(0.1f);
    checkSerialization(-0.1f);
    checkSerialization(Float.POSITIVE_INFINITY);
    checkSerialization(Float.NEGATIVE_INFINITY);
    checkSerialization(Float.NaN);
    checkSerialization(Float.MAX_VALUE);
    checkSerialization(Float.MIN_VALUE);

    checkSerialization(0.0);
    checkSerialization(-0.0);
    checkSerialization(0.1);
    checkSerialization(-0.1);
    checkSerialization(Math.PI);
    checkSerialization(-Math.PI);
    checkSerialization(Math.E);
    checkSerialization(-Math.E);
    checkSerialization(Double.POSITIVE_INFINITY);
    checkSerialization(Double.NEGATIVE_INFINITY);
    checkSerialization(Double.NaN);
    checkSerialization(Double.MAX_VALUE);
    checkSerialization(Double.MIN_VALUE);

    checkSerialization(new DecimalIntegral(true, 255, 1, Decimal.DecimalFormat.BFLOAT_16));
    checkSerialization(new DecimalIntegral(true, 31, 1, Decimal.DecimalFormat.IEEE_754_HALF_16));

    // Currently Unwritable till we get BigInteger/BigUnsignedInteger support.
    // checkSerialization(new DecimalIntegral(true, 1, 1,
    // Decimal.DecimalFormat.IEEE_754_QUADRUPLE_128));
    // checkSerialization(new DecimalIntegral(true, 1, 1,
    // Decimal.DecimalFormat.IEEE_754_OCTUPLE_256));
  }

  @Test
  public void testWriteFunctionality() {
    final BitOutputStream bitOutputStreamFirst = new MockBitOutputStream();
    new Decimal(Math.E).writeTo(bitOutputStreamFirst);
    assertEquals(
        bitOutputStreamFirst.toString(),
        "0100000000000101101111110000101010001011000101000101011101101001");

    final BitOutputStream bitOutputStreamSecond = new MockBitOutputStream();
    new Decimal(-Math.E).writeTo(bitOutputStreamSecond);
    assertEquals(
        bitOutputStreamSecond.toString(),
        "1100000000000101101111110000101010001011000101000101011101101001");
  }

  @Test
  public void testReadFunctionality() {
    assertEquals(
        new Decimal()
            .readFrom(
                new MockBitInputStream(
                    "1100000000000101101111110000101010001011000101000101011101101001")),
        -Math.E);
    assertEquals(
        new Decimal()
            .readFrom(
                new MockBitInputStream(
                    "0100000000000101101111110000101010001011000101000101011101101001")),
        Math.E);
  }
}
