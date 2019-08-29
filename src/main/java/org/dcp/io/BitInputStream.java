/**
 * Copyright 2019 DataCompressionPrimitives.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package org.dcp.io;

import org.dcp.entities.Bit;

public interface BitInputStream {
    public Bit readBit();
    public Iterable<Bit> readBits(final int toRead);
    public void skipBits(final int toRead);
}
