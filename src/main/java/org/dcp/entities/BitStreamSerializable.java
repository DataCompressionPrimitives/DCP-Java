package org.dcp.entities;

import org.dcp.io.BitInputStream;
import org.dcp.io.BitOutputStream;

public interface BitStreamSerializable {
    public Object readFrom(final BitInputStream bitInputStream);
    public void writeTo(final BitOutputStream bitOutputStream);
}
