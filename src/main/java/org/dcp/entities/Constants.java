/**
 * Copyright 2019-Present DataCompressionPrimitives.
 *
 * <p>Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License. You may obtain a copy of the License at
 *
 * <p>http://www.apache.org/licenses/LICENSE-2.0
 */
package org.dcp.entities;

public interface Constants {
  public static final int BITS_IN_A_BYTE = 8; // Use Long.
  public static final int BYTES_IN_A_SEGMENT = 8;
  public static final int BITS_IN_A_SEGMENT = BITS_IN_A_BYTE * BYTES_IN_A_SEGMENT;

  public static final int BYTES_IN_A_INTEGER = 8; // Use Long.
  public static final int BITS_IN_A_INTEGER = BYTES_IN_A_INTEGER * BITS_IN_A_BYTE;

  public static final int BYTES_IN_A_BUFFER = 4096;
  public static final int BITS_IN_A_BUFFER = BYTES_IN_A_INTEGER * BYTES_IN_A_BUFFER;
}
