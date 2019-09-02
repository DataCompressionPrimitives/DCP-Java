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

    final UnsignedInteger unsignedInteger;
    final boolean isNegative;

    public Integer(final long integralValue, final int sizeInBits) {
        isNegative = (integralValue < 0);
        final long unsignedIntValue = isNegative ? -integralValue: integralValue;
        final int sizeInBitsUnsigned = sizeInBits - 1;
        final long neededSizeInBits = EntropyUtil.findSizeOfBitsToHold(unsignedIntValue);
        if(neededSizeInBits > sizeInBitsUnsigned)
            throw new IllegalArgumentException(String.format("Needed Bits cannot be > than SizeUnsigned. NeededSize: %d SizeUnsigned: %d", neededSizeInBits, sizeInBitsUnsigned));
        this.unsignedInteger = new UnsignedInteger(unsignedIntValue, sizeInBitsUnsigned);
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
        return new Integer(integralValue, unsignedInteger.sizeInBits + 1);
    }

    @Override
    public void writeTo(BitOutputStream bitOutputStream) {
        new Boolean(isNegative).writeTo(bitOutputStream);
        unsignedInteger.writeTo(bitOutputStream);
    }
}
