/*
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

import org.dcp.entities.primitives.TabooInteger;
import org.dcp.entities.primitives.UnsignedInteger;
import org.dcp.io.BitOutputStream;
import org.dcp.io.impl.MockBitInputStream;
import org.dcp.io.impl.MockBitOutputStream;
import org.dcp.util.EntropyUtil;
import org.junit.Test;

public class TabooIntegerTest {

  @Test
  public void testBasicFunctionality() {
    // Test default base 2
    TabooInteger tInt = new TabooInteger(5);
    assertEquals(5, tInt.value());
    assertEquals(2, tInt.getTabooPattern().value());

    // Test custom base
    TabooInteger tIntBase = new TabooInteger(7, 10);
    assertEquals(7, tIntBase.value());
    assertEquals(10, tIntBase.getTabooPattern().value());

    // Test UnsignedInteger constructor
    UnsignedInteger tabooPattern = new UnsignedInteger(15, 4);
    TabooInteger tIntPattern = new TabooInteger(8, tabooPattern);
    assertEquals(8, tIntPattern.value());
    assertEquals(tabooPattern, tIntPattern.getTabooPattern());

    // Test tabooPattern must be greater than value
    try {
      new TabooInteger(10, 10);
      fail("Should throw IllegalArgumentException when value >= tabooPattern");
    } catch (IllegalArgumentException ie) {
      // Good
    }
    try {
      new TabooInteger(-1, 10);
      fail("Should throw IllegalArgumentException for negative value");
    } catch (IllegalArgumentException ie) {
      // Good
    }
    try {
      new TabooInteger(1, (UnsignedInteger) null);
      fail("Should throw IllegalArgumentException for null tabooPattern");
    } catch (IllegalArgumentException ie) {
      // Good
    }
  }

  @Test
  public void testGetSizeInBits() {
    // base = 2, value = 0, should be 1 bit (taboo pattern only)
    TabooInteger t0 = new TabooInteger(0);
    assertEquals(1, t0.getSizeInBits());

    // base = 2, value = 3, should be 3 bits (2 digits + taboo pattern)
    TabooInteger t3 = new TabooInteger(3);
    assertEquals(3, t3.getSizeInBits());

    // base = 5, value = 12 (digits in base 5)
    TabooInteger t12 = new TabooInteger(12, 5);
    int n = EntropyUtil.findSizeOfBitsToHold(5);
    long base = EntropyUtil.findMaximumValueRepresentible(n);
    int digits = EntropyUtil.findSizeOfDigitsToHold(12, base);
    assertEquals((digits) * n + n, t12.getSizeInBits());
  }

  @Test
  public void testReadFunctionality() {
    // base = 2, taboo pattern = 2 (10)
    TabooInteger t = new TabooInteger(0);
    assertEquals(0, t.readFrom(new MockBitInputStream("10")).value());
    assertEquals(1, t.readFrom(new MockBitInputStream("010")).value());
    assertEquals(2, t.readFrom(new MockBitInputStream("110")).value());
    assertEquals(3, t.readFrom(new MockBitInputStream("0110")).value());
    assertEquals(4, t.readFrom(new MockBitInputStream("1110")).value());

    // base = 3, taboo pattern = 3 (11)
    TabooInteger tBase3 = new TabooInteger(0, 3);
    assertEquals(0, tBase3.readFrom(new MockBitInputStream("11")).value());
    assertEquals(1, tBase3.readFrom(new MockBitInputStream("011")).value());
    assertEquals(2, tBase3.readFrom(new MockBitInputStream("111")).value());
    assertEquals(3, tBase3.readFrom(new MockBitInputStream("0011")).value());
  }

  @Test
  public void testWriteFunctionality() {
    // base = 2, taboo pattern = 2 (10)
    BitOutputStream out = new MockBitOutputStream();
    new TabooInteger(0).writeTo(out);
    assertEquals("10", out.toString());
    new TabooInteger(1).writeTo(out);
    assertEquals("10010", out.toString());
    new TabooInteger(2).writeTo(out);
    assertEquals("10010110", out.toString());

    // base = 3, taboo pattern = 3 (11)
    BitOutputStream out3 = new MockBitOutputStream();
    new TabooInteger(0, 3).writeTo(out3);
    assertEquals("11", out3.toString());
    new TabooInteger(1, 3).writeTo(out3);
    assertEquals("11011", out3.toString());
    new TabooInteger(2, 3).writeTo(out3);
    assertEquals("1101111", out3.toString());
    new TabooInteger(3, 3).writeTo(out3);
    assertEquals("110111100", out3.toString());
  }

  @Test
  public void testEqualsAndToString() {
    TabooInteger t = new TabooInteger(5, 10);
    assertEquals(t, new TabooInteger(5, 10));
    assertEquals(t, Long.valueOf(5));
    assertEquals(t, Integer.valueOf(5));
    assertEquals(t, (Number) 5L);
    assertEquals("5", t.toString());
  }
}
