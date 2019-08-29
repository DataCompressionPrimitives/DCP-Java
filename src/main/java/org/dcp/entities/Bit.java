/**
 * Copyright 2019 DataCompressionPrimitives.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package org.dcp.entities;

public class Bit {
    private boolean bitValue = false;

    public Bit(final int intValue) {
        if(intValue == 0)
            bitValue = false;
        else if (intValue == 1)
            bitValue = true;
    }

    public Bit(final boolean bitValue) {
        this.bitValue = bitValue;
    }

    public boolean value() {
        return bitValue;
    }

    public int toInt() {
        if(bitValue)
            return 1;
        return 0;
    }

    public String toString() {
        return String.valueOf(toInt());
    }

}
