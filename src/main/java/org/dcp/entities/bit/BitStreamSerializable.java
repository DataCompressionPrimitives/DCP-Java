/**
 * Copyright 2019-Present DataCompressionPrimitives.
 *
 * <p>Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License. You may obtain a copy of the License at
 *
 * <p>http://www.apache.org/licenses/LICENSE-2.0
 */
package org.dcp.entities.bit;

import org.dcp.io.BitInputStream;
import org.dcp.io.BitOutputStream;

public interface BitStreamSerializable<T extends BitStreamSerializable> {
  public T readFrom(final BitInputStream bitInputStream);

  public void writeTo(final BitOutputStream bitOutputStream);
}
