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

import org.dcp.entities.primitives.Boolean;
import org.dcp.io.BitOutputStream;
import org.dcp.io.impl.MockBitInputStream;
import org.dcp.io.impl.MockBitOutputStream;
import org.junit.Test;

public class BooleanTest {

  @Test
  public void testBasicFunctionality() {
    final Boolean boolValueDefault = new Boolean();
    assertEquals(boolValueDefault.value(), false);

    final Boolean boolValueFalse = new Boolean(false);
    assertEquals(boolValueFalse.value(), false);

    final Boolean boolValueTrue = new Boolean(true);
    assertEquals(boolValueTrue.value(), true);
  }

  @Test
  public void testReadFunctionality() {
    assertEquals(new Boolean().readFrom(new MockBitInputStream("0")).value(), false);
    assertEquals(new Boolean().readFrom(new MockBitInputStream("1")).value(), true);
  }

  @Test
  public void testWriteFunctionality() {
    final BitOutputStream bitOutputStreamFalse = new MockBitOutputStream();
    new Boolean(false).writeTo(bitOutputStreamFalse);
    assertEquals(bitOutputStreamFalse.toString(), "0");

    final BitOutputStream bitOutputStreamTrue = new MockBitOutputStream();
    new Boolean(true).writeTo(bitOutputStreamTrue);
    assertEquals(bitOutputStreamTrue.toString(), "1");
  }
}
