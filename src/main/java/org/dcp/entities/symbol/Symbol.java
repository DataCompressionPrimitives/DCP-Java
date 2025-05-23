/**
 * Copyright 2019-Present DataCompressionPrimitives.
 *
 * <p>Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License. You may obtain a copy of the License at
 *
 * <p>http://www.apache.org/licenses/LICENSE-2.0
 */
package org.dcp.entities.symbol;

import org.dcp.entities.bit.BitStreamSerializable;

public abstract class Symbol implements BitStreamSerializable {

  protected final int indexValue;
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
