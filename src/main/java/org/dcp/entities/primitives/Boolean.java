/**
 * Copyright 2019 DataCompressionPrimitives.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package org.dcp.entities.primitives;

import org.dcp.entities.bit.Bit;
import org.dcp.entities.bit.BitStreamSerializable;
import org.dcp.io.BitInputStream;
import org.dcp.io.BitOutputStream;

public class Boolean implements BitStreamSerializable {

    final boolean value;

    public Boolean(final boolean value) {
        this.value = value;
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
}
