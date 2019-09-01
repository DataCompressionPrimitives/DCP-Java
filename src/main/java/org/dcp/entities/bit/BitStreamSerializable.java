/**
 * Copyright 2019 DataCompressionPrimitives.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package org.dcp.entities.bit;

import org.dcp.io.BitInputStream;
import org.dcp.io.BitOutputStream;

public interface BitStreamSerializable<T extends BitStreamSerializable> {
    public T readFrom(final BitInputStream bitInputStream);
    public void writeTo(final BitOutputStream bitOutputStream);
}
