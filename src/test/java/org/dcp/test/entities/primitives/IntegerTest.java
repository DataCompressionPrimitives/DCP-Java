/**
 * Copyright 2019-Present DataCompressionPrimitives.
 *
 * <p>Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License. You may obtain a copy of the License at
 *
 * <p>http://www.apache.org/licenses/LICENSE-2.0
 */
package org.dcp.test.entities.primitives;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.dcp.entities.Constants;
import org.dcp.entities.primitives.Integer;
import org.dcp.io.BitOutputStream;
import org.dcp.io.impl.MockBitInputStream;
import org.dcp.io.impl.MockBitOutputStream;
import org.junit.Test;

public class IntegerTest {

  @Test
  public void testBasicFunctionality() {
    final Integer integerDefault = new Integer(4);
    assertEquals(integerDefault.value(), 0);

    try {
      final Integer integerUnParsable = new Integer(19, 4);
      assertTrue(false);
    } catch (IllegalArgumentException ie) {
      // Good!
    }

    try {
      final Integer integerMaybeParsable =
          new Integer(java.lang.Long.MIN_VALUE, Constants.BITS_IN_A_INTEGER);
      assertTrue(false);
    } catch (IllegalArgumentException ie) {
      // Good!
    }

    final Integer integerParsableMin =
        new Integer(java.lang.Long.MIN_VALUE + 1, Constants.BITS_IN_A_INTEGER);
    assertEquals(integerParsableMin.value(), java.lang.Long.MIN_VALUE + 1);

    final Integer integerParsableMax =
        new Integer(java.lang.Long.MAX_VALUE, Constants.BITS_IN_A_INTEGER);
    assertEquals(integerParsableMax.value(), java.lang.Long.MAX_VALUE);
  }

  @Test
  public void testReadFunctionality() {
    assertEquals(new Integer(5).readFrom(new MockBitInputStream("01011")).value(), 11);
    assertEquals(new Integer(4).readFrom(new MockBitInputStream("0011")).value(), 3);
    assertEquals(new Integer(3).readFrom(new MockBitInputStream("010")).value(), 2);
    assertEquals(new Integer(2).readFrom(new MockBitInputStream("00")).value(), 0);
    assertEquals(
        new Integer(17).readFrom(new MockBitInputStream("01111111111111111")).value(), 65535);

    assertEquals(new Integer(5).readFrom(new MockBitInputStream("11011")).value(), -11);
    assertEquals(new Integer(4).readFrom(new MockBitInputStream("1011")).value(), -3);
    assertEquals(new Integer(3).readFrom(new MockBitInputStream("110")).value(), -2);
    assertEquals(new Integer(2).readFrom(new MockBitInputStream("10")).value(), 0);
    assertEquals(
        new Integer(17).readFrom(new MockBitInputStream("11111111111111111")).value(), -65535);
  }

  @Test
  public void testWriteFunctionality() {
    final BitOutputStream bitOutputStreamFirst = new MockBitOutputStream();
    new Integer(-13, 5).writeTo(bitOutputStreamFirst);
    assertEquals(bitOutputStreamFirst.toString(), "11101");
    new Integer(11, 5).writeTo(bitOutputStreamFirst);
    assertEquals(bitOutputStreamFirst.toString(), "1110101011");

    final BitOutputStream bitOutputStreamSecond = new MockBitOutputStream();
    new Integer(0, 5).writeTo(bitOutputStreamSecond);
    assertEquals(bitOutputStreamSecond.toString(), "00000");
    new Integer(-4, 4).writeTo(bitOutputStreamSecond);
    assertEquals(bitOutputStreamSecond.toString(), "000001100");
    new Integer(-4, 5).writeTo(bitOutputStreamSecond);
    assertEquals(bitOutputStreamSecond.toString(), "00000110010100");
    new Integer(1, 5).writeTo(bitOutputStreamSecond);
    assertEquals(bitOutputStreamSecond.toString(), "0000011001010000001");
  }
}
