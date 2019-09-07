/**
 * Copyright 2019 DataCompressionPrimitives.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package org.dcp.entities.symbol;

import org.dcp.entities.bit.BitStreamSerializable;

public abstract class Symbol implements BitStreamSerializable {

    final protected int indexValue;
    protected int occurences = 0;

    public Symbol(final int indexValue) {
        this.indexValue = indexValue;
    }

    public final int indexValue() {
        return indexValue;
    }

    public final int incrementAndGet() {
        ++occurences;
        return occurences;
    }

    public final int numberOfOccurences() {
        return occurences;
    }

}
