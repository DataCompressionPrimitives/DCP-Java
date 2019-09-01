/**
 * Copyright 2019 DataCompressionPrimitives.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package org.dcp.io;

import org.dcp.entities.bit.Bit;

import java.util.ArrayList;
import java.util.List;

public interface BitInputStream {
    public Bit readBit();

    public default Iterable<Bit> readBits(int toRead) {
        final List<Bit> readBits = new ArrayList<Bit>(toRead);
        for(int readIter = 0; readIter < toRead; ++readIter) {
            readBits.add(readBit());
        }
        return readBits;
    }

    public default void skipBits(int toRead) {
        for(int readIter = 0; readIter < toRead; ++readIter) {
            readBit();
        }
    }

}
