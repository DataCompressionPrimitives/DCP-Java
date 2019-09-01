/**
 * Copyright 2019 DataCompressionPrimitives.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package org.dcp.io.impl;

import org.dcp.entities.bit.Bit;
import org.dcp.io.BitOutputStream;
import static org.dcp.entities.Constants.*;

public class MockBitOutputStream implements BitOutputStream {

    final StringBuffer bitString;

    public MockBitOutputStream(final int size) {
        bitString = new StringBuffer(size);
    }

    public MockBitOutputStream() {
        this(BITS_IN_A_BUFFER);
    }

    @Override
    public void writeBit(Bit bit) {
        if(bit.value())
            bitString.append('1');
        else
            bitString.append('0');
    }

    @Override
    public void flushBits() {
        int remaining = (bitString.length() % BITS_IN_A_BYTE);
        if(remaining == 0)
            return;
        remaining = BITS_IN_A_BYTE - remaining;
        while(remaining > 0) {
            writeBit(Bit.FALSE);
            remaining--;
        }
    }

    @Override
    public String toString() {
        return bitString.toString();
    }

}
