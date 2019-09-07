/**
 * Copyright 2019 DataCompressionPrimitives.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package org.dcp.entities.primitives;

import org.dcp.entities.bit.BitStreamSerializable;
import org.dcp.io.BitInputStream;
import org.dcp.io.BitOutputStream;
import org.dcp.util.EntropyUtil;

public class Integer implements BitStreamSerializable {

    private final UnsignedInteger unsignedInteger;
    private final int sizeInBits;
    private final boolean isNegative;

    public Integer(final long integralValue, final int sizeInBits) {
        isNegative = (integralValue < 0);
        final long unsignedIntValue = isNegative ? -integralValue: integralValue;
        final int sizeInBitsUnsigned = sizeInBits - 1;
        final long neededSizeInBits = EntropyUtil.findSizeOfBitsToHold(unsignedIntValue);
        if(neededSizeInBits > sizeInBitsUnsigned)
            throw new IllegalArgumentException(String.format("Size too small to Hold Value. NeededSize: %d SizeUnsigned: %d", neededSizeInBits, sizeInBitsUnsigned));
        this.sizeInBits = sizeInBits;
        this.unsignedInteger = new UnsignedInteger(unsignedIntValue, sizeInBitsUnsigned);
    }

    public Integer(final int sizeInBits) {
        this(0, sizeInBits);
    }

    public long value() {
        final long unsignedValue = unsignedInteger.value();
        return isNegative? -unsignedValue : unsignedValue;
    }

    public int getSizeInBits() {
        return sizeInBits;
    }

    @Override
    public Integer readFrom(final BitInputStream bitInputStream) {
        boolean isNegative = bitInputStream.readBit().value();
        final UnsignedInteger readInteger = unsignedInteger.readFrom(bitInputStream);
        final long unsignedValue = readInteger.value();
        final long integralValue = isNegative? -unsignedValue : unsignedValue;
        return new Integer(integralValue, readInteger.getSizeInBits() + 1);
    }

    @Override
    public void writeTo(final BitOutputStream bitOutputStream) {
        new Boolean(isNegative).writeTo(bitOutputStream);
        unsignedInteger.writeTo(bitOutputStream);
    }

    @Override
    public String toString() {
        return String.valueOf(value());
    }
}
