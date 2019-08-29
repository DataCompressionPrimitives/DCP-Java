/**
 * Copyright 2019 DataCompressionPrimitives.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package org.dcp.io;

import org.dcp.entities.Bit;

public interface BitOutputStream {
    public void writeBit(final Bit bit);
    public void writeBits(final Iterable<Bit> bits);
    public void flushBits();
}
