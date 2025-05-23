/**
 * Copyright 2019-Present DataCompressionPrimitives.
 *
 * <p>Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License. You may obtain a copy of the License at
 *
 * <p>http://www.apache.org/licenses/LICENSE-2.0
 */
package org.dcp.io;

import java.io.Closeable;
import org.dcp.entities.bit.Bit;

public interface BitOutputStream extends Closeable {
  public void writeBit(final Bit bit);

  public default void writeBits(final Iterable<Bit> bits) {
    bits.forEach(bit -> writeBit(bit));
  }

  @Override
  default void close() {
    flushBits();
  }

  public void flushBits();
}
