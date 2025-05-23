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
import static org.junit.Assert.fail;

import org.dcp.entities.primitives.UnsignedInteger;
import org.dcp.entities.primitives.VarUnsignedInteger;
import org.dcp.io.BitInputStream;
import org.dcp.io.BitOutputStream;
import org.dcp.io.impl.MockBitInputStream;
import org.dcp.io.impl.MockBitOutputStream;
import org.junit.Test;

public class VarUnsignedIntegerTest {

  @Test
  public void testBasicFunctionality() {
    try {
      new VarUnsignedInteger(new UnsignedInteger(231, 12), 1);
      fail(
          "Should throw IllegalArgumentException for invalid VarUnsignedInteger constructor arguments");
    } catch (IllegalArgumentException ie) {
      // Good!
    }

    try {
      new VarUnsignedInteger(new UnsignedInteger(231, 12), 2);
      fail(
          "Should throw IllegalArgumentException for invalid VarUnsignedInteger constructor arguments");
    } catch (IllegalArgumentException ie) {
      // Good!
    }

    try {
      new VarUnsignedInteger(new UnsignedInteger(231, 12), 7);
      fail(
          "Should throw IllegalArgumentException for invalid VarUnsignedInteger constructor arguments");
    } catch (IllegalArgumentException ie) {
      // Good!
    }

    try {
      new VarUnsignedInteger(new UnsignedInteger(231, 12), 7);
      fail(
          "Should throw IllegalArgumentException for invalid VarUnsignedInteger constructor arguments");
    } catch (IllegalArgumentException ie) {
      // Good!
    }

    final VarUnsignedInteger varUnsignedIntegerDefault =
        new VarUnsignedInteger(new UnsignedInteger(231, 12), 8);
    assertEquals(varUnsignedIntegerDefault.value(), 231);

    try {
      new VarUnsignedInteger(new UnsignedInteger(231, 12), 12);
      fail(
          "Should throw IllegalArgumentException for invalid VarUnsignedInteger constructor arguments");
    } catch (IllegalArgumentException ie) {
      // Good!
    }

    try {
      new VarUnsignedInteger(new UnsignedInteger(231, 12), 13);
      fail(
          "Should throw IllegalArgumentException for invalid VarUnsignedInteger constructor arguments");
    } catch (IllegalArgumentException ie) {
      // Good!
    }
  }

  @Test
  public void testReadFunctionality() {
    final BitInputStream bitInputStreamFirst = new MockBitInputStream("1000000000000111");
    final VarUnsignedInteger varUnsignedIntegerFirst =
        new VarUnsignedInteger(new UnsignedInteger(0, 12), 8).readFrom(bitInputStreamFirst);
    assertEquals(varUnsignedIntegerFirst.value(), 7);

    final BitInputStream bitInputStreamSecond = new MockBitInputStream("00000111");
    final VarUnsignedInteger varUnsignedIntegerSecond =
        new VarUnsignedInteger(new UnsignedInteger(0, 12), 8).readFrom(bitInputStreamSecond);
    assertEquals(varUnsignedIntegerSecond.value(), 7);

    final BitInputStream bitInputStreamThird = new MockBitInputStream("00000111");
    final VarUnsignedInteger varUnsignedIntegerThird =
        new VarUnsignedInteger(new UnsignedInteger(0, 18), 8).readFrom(bitInputStreamThird);
    assertEquals(varUnsignedIntegerThird.value(), 7);

    final BitInputStream bitInputStreamFourth = new MockBitInputStream("1000000000000111");
    final VarUnsignedInteger varUnsignedIntegerFourth =
        new VarUnsignedInteger(new UnsignedInteger(0, 14), 8).readFrom(bitInputStreamFourth);
    assertEquals(varUnsignedIntegerFourth.value(), 7);
  }

  @Test
  public void testWriteFunctionality() {
    final BitOutputStream bitOutputStreamFirst = new MockBitOutputStream();
    new VarUnsignedInteger(new UnsignedInteger(7, 12), 8).writeTo(bitOutputStreamFirst);
    assertEquals(bitOutputStreamFirst.toString(), "1000000000000111");

    final BitOutputStream bitOutputStreamSecond = new MockBitOutputStream();
    new VarUnsignedInteger(new UnsignedInteger(91, 13), 8).writeTo(bitOutputStreamSecond);
    assertEquals(bitOutputStreamSecond.toString(), "1000000001011011");
  }
}
