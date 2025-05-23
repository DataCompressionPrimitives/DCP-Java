/**
 * Copyright 2019-Present DataCompressionPrimitives.
 *
 * <p>Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License. You may obtain a copy of the License at
 *
 * <p>http://www.apache.org/licenses/LICENSE-2.0
 */
package org.dcp.io;

import java.util.Iterator;
import org.dcp.entities.bit.Bit;

public interface BitInputStream {
  public Bit readBit();

  public default Iterable<Bit> readBits(final int toRead) {
    return () -> {
      return new Iterator<Bit>() {
        int readIter = 0;

        @Override
        public boolean hasNext() {
          return (readIter < toRead);
        }

        @Override
        public Bit next() {
          ++readIter;
          return readBit();
        }
      };
    };
  }

  public default void skipBits(final int toRead) {
    readBits(toRead).forEach(bit -> {});
  }
}
