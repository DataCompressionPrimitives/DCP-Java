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

import org.dcp.entities.primitives.UnaryInteger;
import org.dcp.io.BitInputStream;
import org.dcp.io.BitOutputStream;
import org.dcp.io.impl.MockBitInputStream;
import org.dcp.io.impl.MockBitOutputStream;
import org.junit.Test;

public class UnaryIntegerTest {

  @Test
  public void testBasicFunctionality() {
    try {
      new UnaryInteger(-1);
      assertTrue(false);
    } catch (IllegalArgumentException ie) {
      // Good!
    }
    UnaryInteger zero = new UnaryInteger(0);
    assertEquals(0, zero.value());
    UnaryInteger one = new UnaryInteger(1);
    assertEquals(1, one.value());
    UnaryInteger large = new UnaryInteger(12345);
    assertEquals(12345, large.value());
  }

  @Test
  public void testReadFunctionality() {
    // Unary encoding: n times '1', then a '0'. For 3: "1110"
    final BitInputStream in1 = new MockBitInputStream("11110"); // 4 ones, value=4
    UnaryInteger u1 = new UnaryInteger(0).readFrom(in1);
    assertEquals(4, u1.value());

    final BitInputStream in2 = new MockBitInputStream("0"); // value=0
    UnaryInteger u2 = new UnaryInteger(0).readFrom(in2);
    assertEquals(0, u2.value());

    final BitInputStream in3 = new MockBitInputStream("110"); // value=2
    UnaryInteger u3 = new UnaryInteger(0).readFrom(in3);
    assertEquals(2, u3.value());
  }

  @Test
  public void testWriteFunctionality() {
    final BitOutputStream out1 = new MockBitOutputStream();
    new UnaryInteger(4).writeTo(out1);
    assertEquals("11110", out1.toString());

    final BitOutputStream out2 = new MockBitOutputStream();
    new UnaryInteger(0).writeTo(out2);
    assertEquals("0", out2.toString());

    final BitOutputStream out3 = new MockBitOutputStream();
    new UnaryInteger(2).writeTo(out3);
    assertEquals("110", out3.toString());
  }
}
