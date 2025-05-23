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

public class Boolean implements BitStreamSerializable {

  private final boolean value;

  public Boolean(final boolean value) {
    this.value = value;
  }

  public Boolean(final Bit bitValue) {
    this.value = bitValue.value();
  }

  public Boolean() {
    this(false);
  }

  public boolean value() {
    return value;
  }

  @Override
  public Boolean readFrom(final BitInputStream bitInputStream) {
    return new Boolean(bitInputStream.readBit().value());
  }

  @Override
  public void writeTo(final BitOutputStream bitOutputStream) {
    bitOutputStream.writeBit(Bit.valueOf(value));
  }

  @Override
  public boolean equals(final Object other) {
    if (other == null) return false;

    if (this == other) return true;

    if (other instanceof Boolean) {
      return this.value == ((Boolean) other).value;
    } else if (other instanceof Bit) {
      return this.value == ((Bit) other).value();
    } else if (other instanceof java.lang.Boolean) {
      return this.value == ((java.lang.Boolean) other).booleanValue();
    } else if (other instanceof String) {
      try {
        return Bit.valueOf((String) other).value() == this.value;
      } catch (IllegalArgumentException e) {
        return false;
      }
    } else if (other instanceof Character) {
      try {
        return Bit.valueOf((char) other).value() == this.value;
      } catch (IllegalArgumentException e) {
        return false;
      }
    } else if (other instanceof Number) {
      try {
        return Bit.valueOf(((Number) other).longValue()).value() == this.value;
      } catch (IllegalArgumentException e) {
        return false;
      }
    }
    return false;
  }

  @Override
  public String toString() {
    return String.valueOf(value);
  }
}
