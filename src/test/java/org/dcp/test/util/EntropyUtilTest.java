/**
 * Copyright 2019-Present DataCompressionPrimitives.
 *
 * <p>Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License. You may obtain a copy of the License at
 *
 * <p>http://www.apache.org/licenses/LICENSE-2.0
 */
package org.dcp.test.util;

import static org.junit.Assert.assertEquals;

import java.math.BigInteger;
import org.dcp.util.EntropyUtil;
import org.junit.Test;

public class EntropyUtilTest {

  @Test
  public void testFindSizeOfBitsToHold() {
    assertEquals(EntropyUtil.findSizeOfBitsToHold(0), 0);
    assertEquals(EntropyUtil.findSizeOfBitsToHold(1), 1);
    assertEquals(EntropyUtil.findSizeOfBitsToHold(-1), 2);
    assertEquals(EntropyUtil.findSizeOfBitsToHold(2), 2);

    assertEquals(EntropyUtil.findSizeOfBitsToHold(Short.MAX_VALUE), 15);
    assertEquals(EntropyUtil.findSizeOfBitsToHold(Short.MIN_VALUE), 17);
    assertEquals(EntropyUtil.findSizeOfBitsToHold(Long.MAX_VALUE), 63);
    assertEquals(EntropyUtil.findSizeOfBitsToHold(Long.MIN_VALUE), 65);

    assertEquals(EntropyUtil.findSizeOfBitsToHoldBig(new BigInteger("0")), 0);
    assertEquals(EntropyUtil.findSizeOfBitsToHoldBig(new BigInteger("1")), 1);
    assertEquals(EntropyUtil.findSizeOfBitsToHoldBig(new BigInteger("-1")), 2);
    assertEquals(EntropyUtil.findSizeOfBitsToHoldBig(new BigInteger("2")), 2);
    assertEquals(EntropyUtil.findSizeOfBitsToHoldBig(new BigInteger("255")), 8);
    assertEquals(EntropyUtil.findSizeOfBitsToHoldBig(new BigInteger("-128")), 9);
    assertEquals(EntropyUtil.findSizeOfBitsToHoldBig(new BigInteger("9223372036854775807")), 63);
    assertEquals(
        EntropyUtil.findSizeOfBitsToHoldBig(
            new BigInteger(
                "-115792089237316195423570985008687907853269984665640564039457584007913129639935")),
        257);
    assertEquals(
        EntropyUtil.findSizeOfBitsToHoldBig(
            new BigInteger(
                "179769313486231590772930519078902473361797697894230657273430081157732675805500963132708477322407536021120113879871393357658789768814416622492847430639474124377767893424865485276302219601246094119453082952085005768838150682342462881473913110540827237163350510684586298239947245938479716304835356329624224137215")),
        1024);
  }

  @Test
  public void findMaximumValueRepresentible() {
    assertEquals(EntropyUtil.findMaximumValueRepresentible(0), 0);
    assertEquals(EntropyUtil.findMaximumValueRepresentible(1), 1);
    assertEquals(EntropyUtil.findMaximumValueRepresentible(2), 3);
    assertEquals(EntropyUtil.findMaximumValueRepresentible(8), 255);
    assertEquals(EntropyUtil.findMaximumValueRepresentible(32), 4294967295L);
    assertEquals(EntropyUtil.findMaximumValueRepresentible(63), 9223372036854775807L);

    assertEquals(
        EntropyUtil.findMaximumValueRepresentibleBig(63), new BigInteger("9223372036854775807"));
    assertEquals(
        EntropyUtil.findMaximumValueRepresentibleBig(64), new BigInteger("18446744073709551615"));
    assertEquals(
        EntropyUtil.findMaximumValueRepresentibleBig(128),
        new BigInteger("340282366920938463463374607431768211455"));
    assertEquals(
        EntropyUtil.findMaximumValueRepresentibleBig(256),
        new BigInteger(
            "115792089237316195423570985008687907853269984665640564039457584007913129639935"));
    assertEquals(
        EntropyUtil.findMaximumValueRepresentibleBig(1024),
        new BigInteger(
            "179769313486231590772930519078902473361797697894230657273430081157732675805500963132708477322407536021120113879871393357658789768814416622492847430639474124377767893424865485276302219601246094119453082952085005768838150682342462881473913110540827237163350510684586298239947245938479716304835356329624224137215"));
  }
}
