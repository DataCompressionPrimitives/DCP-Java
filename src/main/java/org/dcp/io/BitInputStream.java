package org.dcp.io;

import org.dcp.entities.Bit;

public interface BitInputStream {
    public Bit readBit();
    public Iterable<Bit> readBits(final int toRead);
    public void skipBits(final int toRead);
}
