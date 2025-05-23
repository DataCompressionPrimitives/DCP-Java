/**
 * Copyright 2019-Present DataCompressionPrimitives.
 *
 * <p>Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License. You may obtain a copy of the License at
 *
 * <p>http://www.apache.org/licenses/LICENSE-2.0
 */
package org.dcp.entities.bit;

import static org.dcp.entities.Constants.*;

import java.util.Iterator;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class BitBuffer implements Iterable<Bit> {

  private final long[] bitSegments;
  private final int sizeInBits;
  private final int sizeInSegments;

  public BitBuffer(final int sizeInBits) {
    if (sizeInBits <= 0)
      throw new IllegalArgumentException(
          String.format("Size should be >0. Size: %d\n", sizeInBits));
    this.sizeInBits = sizeInBits;
    this.sizeInSegments = getSizeInSegments(this.sizeInBits);
    this.bitSegments = new long[sizeInSegments];
  }

  public BitBuffer(final Iterator<Bit> bits, final int sizeInBits) {
    this(sizeInBits);
    int bitIter = 0;
    while (bits.hasNext()) {
      if (bitIter >= sizeInBits) break;
      setBit(bitIter++, bits.next());
    }
  }

  public BitBuffer(final Iterable<Bit> bits, final int sizeInBits) {
    this(bits.iterator(), sizeInBits);
  }

  public BitBuffer(final String bitString) {
    this(
        bitString.chars().mapToObj(charValue -> Bit.valueOf((char) charValue)).iterator(),
        bitString.length());
  }

  private static int getSizeInSegments(final int sizeInBits) {
    return (sizeInBits + (BITS_IN_A_SEGMENT - 1)) / BITS_IN_A_SEGMENT;
  }

  private static final int getSegment(final int index) {
    return index / BITS_IN_A_SEGMENT;
  }

  private static int getBitIndexInSegment(final int index) {
    // In Big Endian Format. For Little Endian, return (sizeInBits % (BITS_IN_A_SEGMENT));
    return BITS_IN_A_SEGMENT - 1 - (index % (BITS_IN_A_SEGMENT));
  }

  public Bit getBit(final int index) {
    if (index >= sizeInBits) {
      throw new IndexOutOfBoundsException(
          String.format("Index out of bounds. Index: %d, Size: %d\n", index, sizeInBits));
    }
    final int segmentIndex = getSegment(index);
    final int segmentBitIndex = getBitIndexInSegment(index);
    if (segmentIndex >= sizeInSegments) {
      throw new IndexOutOfBoundsException(
          String.format(
              "Index out of bounds. Segment Index: %d, Segment Size: %d\n",
              segmentIndex, sizeInSegments));
    }
    final long value = (bitSegments[segmentIndex] >>> segmentBitIndex) & 1;
    final int intValue = (int) value;
    return Bit.valueOf(intValue);
  }

  public void setBit(final int index, final Bit value) {
    if (index >= sizeInBits) {
      throw new IndexOutOfBoundsException(
          String.format("Index out of bounds. Index: %d, Size: %d\n", index, sizeInBits));
    }
    final int segmentIndex = getSegment(index);
    final int segmentBitIndex = getBitIndexInSegment(index);
    if (segmentIndex >= sizeInSegments) {
      throw new IndexOutOfBoundsException(
          String.format(
              "Index out of bounds. Segment Index: %d, Segment Size: %d\n",
              segmentIndex, sizeInSegments));
    }
    final long mask = ((long) value.intValue()) << segmentBitIndex;
    bitSegments[segmentIndex] |= mask;
  }

  public int getSizeInBits() {
    return sizeInBits;
  }

  public BitBuffer subBuffer(final int startIndex, final int endIndexExclusive) {
    if (startIndex < 0)
      throw new IndexOutOfBoundsException(
          String.format("Start Index out of bounds. StartIndex: %d\n", startIndex));
    if (endIndexExclusive > sizeInBits)
      throw new IndexOutOfBoundsException(
          String.format("End Index out of bounds. EndIndex: %d\n", endIndexExclusive));
    final int bitSize = endIndexExclusive - startIndex;
    return new BitBuffer(iterator(startIndex, endIndexExclusive), bitSize);
  }

  public BitBuffer subBuffer(final int startIndex) {
    return subBuffer(startIndex, sizeInBits);
  }

  public Iterator<Bit> iterator(final int startIndex, final int endIndexExclusive) {
    if (startIndex < 0)
      throw new IndexOutOfBoundsException(
          String.format("Start Index out of bounds. StartIndex: %d\n", startIndex));
    if (endIndexExclusive > sizeInBits)
      throw new IndexOutOfBoundsException(
          String.format("End Index out of bounds. EndIndex: %d\n", endIndexExclusive));
    return new Iterator<Bit>() {
      int indexScanning = startIndex;

      @Override
      public boolean hasNext() {
        return indexScanning < endIndexExclusive;
      }

      @Override
      public Bit next() {
        return getBit(indexScanning++);
      }
    };
  }

  public Iterator<Bit> iterator(final int startIndex) {
    return iterator(startIndex, sizeInBits);
  }

  public Iterator<Bit> iterator() {
    return iterator(0);
  }

  public BitBuffer append(final Iterable<Bit> appendBits, final int appendSizeInBits) {
    final int newSizeInBits = sizeInBits + appendSizeInBits;
    final BitBuffer bitBufferNew = new BitBuffer(this, newSizeInBits);
    int bitIter = sizeInBits;
    for (final Bit bit : appendBits) {
      if (bitIter >= newSizeInBits) break;
      bitBufferNew.setBit(bitIter++, bit);
    }
    return bitBufferNew;
  }

  public BitBuffer append(final BitBuffer bitBuffer) {
    return append(bitBuffer, bitBuffer.sizeInBits);
  }

  public String toString() {
    return StreamSupport.stream(this.spliterator(), false)
        .map(bit -> bit.toString())
        .collect(Collectors.joining(""));
  }
}
