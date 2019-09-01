/**
 * Copyright 2019 DataCompressionPrimitives.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package org.dcp.entities.bit;

public class Bit {
    public static final Bit FALSE = new Bit(false);
    public static final Bit TRUE = new Bit(true);

    private final boolean bitValue;

    private Bit(final boolean bitValue) {
        this.bitValue = bitValue;
    }

    public static Bit valueOf(final boolean boolValue) {
        if(boolValue == false)
            return FALSE;
        else
            return TRUE;
    }

    public static Bit valueOf(final String stringValue) {
        if("0".equals(stringValue))
            return FALSE;
        else if("1".equals(stringValue))
            return TRUE;
        else
            throw new IllegalArgumentException(String.format("Value should be 0 or 1. Value: %s", stringValue));
    }

    public static Bit valueOf(final char charValue) {
        if(charValue == '0')
            return FALSE;
        else if(charValue == '1')
            return TRUE;
        else
            throw new IllegalArgumentException(String.format("Value should be 0 or 1. Value: %c", charValue));
    }

    public static Bit valueOf(final long intValue) {
        if(intValue == 0)
            return FALSE;
        else if(intValue == 1)
            return TRUE;
        else
            throw new IllegalArgumentException(String.format("Value should be 0 or 1. Value: %d", intValue));
    }

    public boolean value() {
        return bitValue;
    }

    public long intValue() {
        if(bitValue == false)
            return 0;
        else
            return 1;
    }

    public String toString() {
        if(bitValue == false)
            return "0";
        else
            return "1";
    }

}
