/**
 * Copyright 2019 DataCompressionPrimitives.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package org.dcp.io;

import org.dcp.entities.bit.Bit;

public interface BitOutputStream {
    public void writeBit(final Bit bit);

    public default void writeBits(final Iterable<Bit> bits) {
        for(final Bit bit: bits) {
            writeBit(bit);
        }
    }

    public void flushBits();
}
