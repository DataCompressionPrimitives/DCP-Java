/**
 * Copyright 2019 DataCompressionPrimitives.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package org.dcp.io;

import org.dcp.entities.bit.Bit;

import java.util.Iterator;

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
