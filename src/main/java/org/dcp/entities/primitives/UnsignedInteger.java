/**
 * Copyright 2019 DataCompressionPrimitives.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package org.dcp.entities.primitives;

import org.dcp.entities.bit.Bit;
import org.dcp.entities.bit.BitList;
import org.dcp.entities.bit.BitStreamSerializable;
import org.dcp.io.BitInputStream;
import org.dcp.io.BitOutputStream;

import static org.dcp.entities.Constants.BITS_IN_A_INTEGER;

public class UnsignedInteger implements BitStreamSerializable {

    final int sizeInBits;
    final long integralValue;

    public UnsignedInteger(final long integralValue, final int sizeInBits) {
        if(sizeInBits >= BITS_IN_A_INTEGER)
            throw new IllegalArgumentException(String.format("Bits cannot be greater Maximum. Maximum: %d Bits: ", BITS_IN_A_INTEGER - 1, sizeInBits));
        if(integralValue < 0) {
            throw new IllegalArgumentException(String.format("Unsigned numbers cannot be negative. Value: %d", integralValue));
        }
        final long maximumValue = ~(-1L<<sizeInBits);
        if(integralValue > maximumValue)
            throw new IllegalArgumentException(String.format("Given value cannot be represented. Maximum: %d Value: %d", maximumValue, integralValue));
        this.integralValue = integralValue;
        this.sizeInBits = sizeInBits;
    }

    public UnsignedInteger(final int sizeInBits) {
        this(0, sizeInBits);
    }

    public long value() {
        return integralValue;
    }

    @Override
    public UnsignedInteger readFrom(BitInputStream bitInputStream)
    {
        final Iterable<Bit> bitsRead = bitInputStream.readBits(this.sizeInBits);
        final long readInteger = java.lang.Long.parseUnsignedLong(new BitList(bitsRead, this.sizeInBits).toString(), 2);
        return new UnsignedInteger(readInteger, sizeInBits);
    }

    @Override
    public void writeTo(BitOutputStream bitOutputStream) {
        final String binaryString = java.lang.Long.toBinaryString(this.integralValue);
        final StringBuilder stringBuilder = new StringBuilder(sizeInBits);
        int remainingLength = sizeInBits - binaryString.length();
        while(remainingLength > 0) {
            remainingLength--;
            stringBuilder.append('0');
        }
        stringBuilder.append(binaryString);
        bitOutputStream.writeBits(new BitList(stringBuilder.toString()));
    }

}
