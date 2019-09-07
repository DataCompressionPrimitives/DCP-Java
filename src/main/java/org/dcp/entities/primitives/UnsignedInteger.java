/**
 * Copyright 2019 DataCompressionPrimitives.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package org.dcp.entities.primitives;

import org.dcp.entities.Constants;
import org.dcp.entities.bit.Bit;
import org.dcp.entities.bit.BitStreamSerializable;
import org.dcp.io.BitInputStream;
import org.dcp.io.BitOutputStream;
import org.dcp.util.EntropyUtil;

import java.util.Iterator;

public class UnsignedInteger implements BitStreamSerializable, Iterable<Bit> {

    private final int sizeInBits;
    private final long integralValue;

    public UnsignedInteger(final long integralValue, final int sizeInBits) {
        if(integralValue < 0) {
            throw new IllegalArgumentException(String.format("Unsigned numbers cannot be negative. Value: %d", integralValue));
        }
        final long maximumValue = EntropyUtil.findMaximumValueRepresentible(sizeInBits);
        if(integralValue > maximumValue)
            throw new IllegalArgumentException(String.format("Value cannot be greater than Maximum: %d. Value: %d", maximumValue, integralValue));
        this.integralValue = integralValue;
        this.sizeInBits = sizeInBits;
    }

    public UnsignedInteger(final int sizeInBits) {
        this(0, sizeInBits);
    }

    public UnsignedInteger(final Iterable<Bit> bits, final int sizeInBits) {
        final int maxSizeInBits = Constants.BITS_IN_A_INTEGER - 1;
        if(sizeInBits > maxSizeInBits)
            throw new IllegalArgumentException(String.format("Given Size cannot be greater than Maximum: %d. Size: %d", maxSizeInBits, sizeInBits));
        long integralValue = 0;
        long mask = 1L << sizeInBits;
        for(final Bit bit: bits) {
            mask >>>= 1;
            integralValue |= bit.value()? mask : 0;
        }
        this.integralValue = integralValue;
        this.sizeInBits = sizeInBits;
    }

    public long value() {
        return integralValue;
    }

    public int getSizeInBits() {
        return sizeInBits;
    }

    @Override
    public Iterator<Bit> iterator() {
        return new Iterator<Bit>() {
            long mask = 1L << sizeInBits;

            @Override
            public boolean hasNext() {
                return mask != 1;
            }

            @Override
            public Bit next() {
                mask >>>= 1;
                return Bit.valueOf( (integralValue & mask) != 0 );
            }
        };
    }

    @Override
    public UnsignedInteger readFrom(final BitInputStream bitInputStream)
    {
        return new UnsignedInteger(bitInputStream.readBits(this.sizeInBits), sizeInBits);
    }

    @Override
    public void writeTo(final BitOutputStream bitOutputStream) {
        bitOutputStream.writeBits(this);
    }

    @Override
    public String toString() {
        return String.valueOf(value());
    }

}