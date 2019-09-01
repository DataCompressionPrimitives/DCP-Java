/**
 * Copyright 2019 DataCompressionPrimitives.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package org.dcp.entities.primitives;

import org.dcp.entities.bit.BitStreamSerializable;
import org.dcp.io.BitInputStream;
import org.dcp.io.BitOutputStream;
import static org.dcp.entities.Constants.*;

public class Integer implements BitStreamSerializable {

    final UnsignedInteger unsignedInteger;
    final boolean isNegative;

    public Integer(final long integralValue, final int sizeInBits) {
        long unsignedIntValue;
        if(sizeInBits >= BITS_IN_A_INTEGER)
            throw new IllegalArgumentException(String.format("Bits cannot be greater Maximum. Maximum: %d Bits: ", BITS_IN_A_INTEGER - 1, sizeInBits));
        if(integralValue == java.lang.Long.MIN_VALUE)
            throw new IllegalArgumentException(String.format("Value cannot be lesser than: %d", java.lang.Long.MIN_VALUE + 1));
        if(integralValue < 0) {
            isNegative = true;
            unsignedIntValue = -integralValue;
        } else {
            isNegative = false;
            unsignedIntValue = integralValue;
        }
        this.unsignedInteger = new UnsignedInteger(unsignedIntValue, sizeInBits);
    }

    public Integer(final int sizeInBits) {
        this(0, sizeInBits);
    }

    public long value() {
        return isNegative? -unsignedInteger.integralValue : unsignedInteger.integralValue;
    }

    @Override
    public Integer readFrom(BitInputStream bitInputStream) {
        boolean isNegative = bitInputStream.readBit().value();
        final UnsignedInteger readInteger = unsignedInteger.readFrom(bitInputStream);
        final long integralValue = isNegative? (-readInteger.integralValue): readInteger.integralValue;
        return new Integer(integralValue, unsignedInteger.sizeInBits);
    }

    @Override
    public void writeTo(BitOutputStream bitOutputStream) {
        new Boolean(isNegative).writeTo(bitOutputStream);
        unsignedInteger.writeTo(bitOutputStream);
    }
}
