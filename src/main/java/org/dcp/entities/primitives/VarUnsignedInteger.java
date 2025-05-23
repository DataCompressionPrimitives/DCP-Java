/**
 * Copyright 2019-Present DataCompressionPrimitives.
 *
 * <p>Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License. You may obtain a copy of the License at
 *
 * <p>http://www.apache.org/licenses/LICENSE-2.0
 */
package org.dcp.entities.primitives;

import static org.dcp.entities.Constants.*;

import java.util.stream.IntStream;
import org.dcp.entities.bit.Bit;
import org.dcp.entities.bit.BitBuffer;
import org.dcp.entities.bit.BitStreamSerializable;
import org.dcp.io.BitInputStream;
import org.dcp.io.BitOutputStream;

public class VarUnsignedInteger implements BitStreamSerializable<VarUnsignedInteger> {

  final UnsignedInteger value;
  final int sizeInBits;
  final int chunkSizeInBits;
  final int chunkValueBits;

  public VarUnsignedInteger(final UnsignedInteger value, final int chunkSizeInBits) {
    final int absoluteMinSizeInBits = 2;
    if (chunkSizeInBits < absoluteMinSizeInBits) {
      throw new IllegalArgumentException(
          String.format(
              "Given ChunkSize is Lesser than Absolute Minimum: %d. ChunkSize: %d",
              absoluteMinSizeInBits, chunkSizeInBits));
    }
    /*
     * This minSizeInBits check is: Are you Sane? and not for correctness.
     *
     * Why? For very short integers, consider using a fixed size UnsignedInteger.
     * Otherwise, if you require Byte-Aligned access, you are wasting space.
     */
    final int optimumSizeInBits = BITS_IN_A_BYTE;
    if (chunkSizeInBits < optimumSizeInBits) {
      throw new IllegalArgumentException(
          String.format(
              "Given ChunkSize is Lesser than Optimum: %d. Consider Fixed Length. ChunkSize: %d",
              optimumSizeInBits, chunkSizeInBits));
    }
    this.sizeInBits = value.getSizeInBits();
    if (chunkSizeInBits == sizeInBits) {
      throw new IllegalArgumentException(
          String.format(
              "Given ChunkSize is Equal to ValueSize: %d. Consider Fixed Length.", sizeInBits));
    }
    if (chunkSizeInBits > sizeInBits) {
      throw new IllegalArgumentException(
          String.format(
              "Given ChunkSize is Greater Size: %d. ChunkSize: %d", sizeInBits, chunkSizeInBits));
    }
    this.chunkSizeInBits = chunkSizeInBits;
    this.chunkValueBits = chunkSizeInBits - 1;
    this.value = value;
  }

  public long value() {
    return value.value();
  }

  @Override
  public VarUnsignedInteger readFrom(final BitInputStream bitInputStream) {
    final int numberOfChunks = (sizeInBits / chunkValueBits);
    final int leftOverBits = (sizeInBits % chunkValueBits);
    final int paddedBits = (leftOverBits == 0) ? 0 : (chunkValueBits - leftOverBits);
    final int totalNumberOfChunks = numberOfChunks + ((paddedBits > 0) ? 1 : 0);
    final int totalNumberOfBits = totalNumberOfChunks * chunkValueBits;
    final BitBuffer totalBits = new BitBuffer(totalNumberOfBits);

    int readBits = 0;
    int chunkIter = totalNumberOfChunks;
    while (chunkIter-- > 0) {
      final Bit sentinelBit = bitInputStream.readBit();
      if (chunkIter == 0 && sentinelBit.value())
        throw new IllegalArgumentException(
            String.format(
                "Did not get End Sentinel in TotalChunks: %d. Size: %d",
                totalNumberOfChunks, sizeInBits));
      for (final Bit readBit : bitInputStream.readBits(chunkValueBits)) {
        totalBits.setBit(readBits++, readBit);
      }
      if (!sentinelBit.value()) break;
    }

    final BitBuffer readIntegerBits;
    if (sizeInBits == readBits) {
      readIntegerBits = totalBits.subBuffer(0, readBits);
    } else if (readBits < sizeInBits) {
      final int bitsToPadLeft = sizeInBits - readBits;
      readIntegerBits = new BitBuffer(bitsToPadLeft).append(totalBits, readBits);
    } else {
      final int bitsToUnpad = readBits - sizeInBits;
      readIntegerBits = totalBits.subBuffer(bitsToUnpad, readBits);
    }

    final UnsignedInteger unsignedInteger = new UnsignedInteger(readIntegerBits, sizeInBits);
    return new VarUnsignedInteger(unsignedInteger, chunkSizeInBits);
  }

  @Override
  public void writeTo(final BitOutputStream bitOutputStream) {
    final int numberOfChunks = (sizeInBits / chunkValueBits);
    final int leftOverBits = (sizeInBits % chunkValueBits);
    final int paddedBits = (leftOverBits == 0) ? 0 : (chunkValueBits - leftOverBits);
    final int totalNumberOfChunks = numberOfChunks + ((paddedBits > 0) ? 1 : 0);
    final BitBuffer totalBits =
        (paddedBits > 0)
            ? new BitBuffer(paddedBits).append(value, sizeInBits)
            : new BitBuffer(value, sizeInBits);

    IntStream.range(0, totalNumberOfChunks)
        .forEach(
            currentChunk -> {
              final int nextChunk = currentChunk + 1;
              final int startIndex = currentChunk * chunkValueBits;
              final int endIndex = nextChunk * chunkValueBits;
              bitOutputStream.writeBit(Bit.valueOf(nextChunk < totalNumberOfChunks));
              bitOutputStream.writeBits(() -> totalBits.iterator(startIndex, endIndex));
            });
  }

  @Override
  public boolean equals(final Object other) {
    if (this == other) return true;
    if (other == null) return false;

    if (other instanceof VarUnsignedInteger) {
      return ((VarUnsignedInteger) other).value() == this.value();
    } else if (other instanceof java.lang.Integer) {
      return this.value() == ((java.lang.Integer) other).intValue();
    } else if (other instanceof java.lang.Long) {
      return this.value() == ((java.lang.Long) other).longValue();
    } else if (other instanceof Number) {
      return this.value() == ((Number) other).longValue();
    }
    return false;
  }

  @Override
  public String toString() {
    return String.valueOf(value());
  }
}
