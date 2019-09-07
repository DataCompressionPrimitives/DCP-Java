/**
 * Copyright 2019 DataCompressionPrimitives.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package org.dcp.entities.primitives;

import org.dcp.entities.bit.BitStreamSerializable;
import org.dcp.io.BitInputStream;
import org.dcp.io.BitOutputStream;
import org.dcp.util.EntropyUtil;

public class Decimal implements BitStreamSerializable {

    public static enum DecimalFormat {

        //Half
        IEEE_754_HALF_16(1, 5, 10),
        //BFloat16
        BFLOAT_16(1, 8, 7),
        //Float
        IEEE_754_FLOAT_32(1, 8, 23),
        //Double
        IEEE_754_DOUBLE_64(1, 11, 52),
        //Quadruple
        IEEE_754_QUADRUPLE_128(1, 15, 112),
        //Octuple
        IEEE_754_OCTUPLE_256(1, 19, 236);

        public final int signBits;
        public final int exponentBits;
        public final int mantissaBits;
        public final long exponentBias;

        private DecimalFormat(final int signBits, final int exponentBits, final int mantissaBits) {
            this.signBits = signBits;
            this.exponentBits = exponentBits;
            this.mantissaBits = mantissaBits;
            this.exponentBias = (1L << (this.exponentBits - 1)) - 1;
        }

        private DecimalFormat(final int exponentBits, final int mantissaBits) {
            this(1, exponentBits, mantissaBits);
        }

        @Override
        public String toString() {
            return String.format("%s SignBits: %d ExponentBits: %d, MantissaBits: %d", name(), signBits, exponentBits, mantissaBits);
        }
    }

    public static class DecimalIntegral {
        public final long mantissaValue;
        public final long exponentValue;
        public final boolean isNegative;
        public final DecimalFormat format;

        public DecimalIntegral(final boolean isNegative, final long exponentValue, final long mantissaValue, final DecimalFormat format) {
            this.isNegative = isNegative;
            this.exponentValue = exponentValue;
            this.mantissaValue = mantissaValue;
            this.format = format;
        }

        public DecimalIntegral(final long longValue, final DecimalFormat format) {
            this.isNegative = (((longValue >>> (format.exponentBits + format.mantissaBits)) & 1) == 1);
            this.exponentValue = (longValue >>> format.mantissaBits) & EntropyUtil.findMaximumValueRepresentible(format.exponentBits);
            this.mantissaValue = (longValue & EntropyUtil.findMaximumValueRepresentible(format.mantissaBits));
            this.format = format;
        }

        public DecimalIntegral(final double doubleValue) {
            this(Double.doubleToRawLongBits(doubleValue), DecimalFormat.IEEE_754_DOUBLE_64);
        }

        public DecimalIntegral(final float floatValue) {
            this(Float.floatToRawIntBits(floatValue), DecimalFormat.IEEE_754_FLOAT_32);
        }

        public DecimalIntegral() {
            this(0.0);
        }

        @Override
        public boolean equals(final Object other) {
            if(other instanceof DecimalIntegral) {
                final DecimalIntegral otherDecimalIntegral = (DecimalIntegral)other;
                return otherDecimalIntegral.isNegative == isNegative &&
                        otherDecimalIntegral.exponentValue == exponentValue &&
                        otherDecimalIntegral.mantissaValue == mantissaValue &&
                        otherDecimalIntegral.format == format;
            }
            return false;
        }

        @Override
        public String toString() {
            return String.format("Sign: %s Exponent: %d Mantissa: %d Format: %s", isNegative ? "-" : "+", exponentValue, mantissaValue, format);
        }
    }

    public static class IntegralDecimal {
        private final DecimalIntegral decimalIntegral;
        private final Number decimalValue;

        public IntegralDecimal(final DecimalIntegral decimalIntegral) {
            this.decimalIntegral = decimalIntegral;
            final DecimalFormat format = decimalIntegral.format;
            if(format == DecimalFormat.IEEE_754_DOUBLE_64)
                decimalValue = getDoubleValue(decimalIntegral);
            else if(format == DecimalFormat.IEEE_754_FLOAT_32)
                decimalValue = getFloatValue(decimalIntegral);
            else
                decimalValue = null;
        }

        protected static Float getFloatValue(final DecimalIntegral decimalIntegral) {
            final DecimalFormat format = decimalIntegral.format;
            int intValueRaw = 0;
            if (decimalIntegral.isNegative) intValueRaw |= 1L << (format.mantissaBits + format.exponentBits);
            intValueRaw |= (decimalIntegral.exponentValue << format.mantissaBits);
            intValueRaw |= (decimalIntegral.mantissaValue);
            return Float.intBitsToFloat(intValueRaw);
        }

        protected static Double getDoubleValue(final DecimalIntegral decimalIntegral) {
            final DecimalFormat format = decimalIntegral.format;
            long longValueRaw = 0;
            if (decimalIntegral.isNegative) longValueRaw |= 1L << (format.mantissaBits + format.exponentBits);
            longValueRaw |= (decimalIntegral.exponentValue << format.mantissaBits);
            longValueRaw |= (decimalIntegral.mantissaValue);
            return Double.longBitsToDouble(longValueRaw);
        }

        public IntegralDecimal() {
            this(new DecimalIntegral(0.0));
        }

        @Override
        public boolean equals(final Object other) {
            if(decimalValue != null)
                return decimalValue.equals(other);
            return decimalIntegral.equals(other);
        }

        @Override
        public String toString() {
            if(decimalValue != null)
                return String.valueOf(decimalValue);
            return decimalIntegral.toString();
        }
    }

    private final DecimalIntegral decimalIntegral;

    public Decimal(final DecimalIntegral decimalIntegral) {
        this.decimalIntegral = decimalIntegral;
    }

    public Decimal(final Number decimalValue) {
        if(decimalValue instanceof Double)
            this.decimalIntegral = new DecimalIntegral((Double)decimalValue);
        else if(decimalValue instanceof Float)
            this.decimalIntegral = new DecimalIntegral((Float) decimalValue);
        else
            throw new IllegalArgumentException("Unsupported Numeric Object Type.");
    }

    public Decimal() {
        this(0.0);
    }

    @Override
    public Decimal readFrom(final BitInputStream bitInputStream) {
        final boolean isNegative = new Boolean().readFrom(bitInputStream).value();
        final long exponentValue = new UnsignedInteger(decimalIntegral.format.exponentBits).readFrom(bitInputStream).value();
        final long mantissaValue = new UnsignedInteger(decimalIntegral.format.mantissaBits).readFrom(bitInputStream).value();
        final DecimalIntegral decimalIntegralRead = new DecimalIntegral(isNegative, exponentValue, mantissaValue, decimalIntegral.format);
        return new Decimal(decimalIntegralRead);
    }

    @Override
    public void writeTo(final BitOutputStream bitOutputStream) {
        new Boolean(decimalIntegral.isNegative).writeTo(bitOutputStream);
        new UnsignedInteger(decimalIntegral.exponentValue, decimalIntegral.format.exponentBits).writeTo(bitOutputStream);
        new UnsignedInteger(decimalIntegral.mantissaValue, decimalIntegral.format.mantissaBits).writeTo(bitOutputStream);
    }

    @Override
    public boolean equals(final Object other) {
        return new IntegralDecimal(decimalIntegral).equals(other);
    }

    @Override
    public String toString() {
        return new IntegralDecimal(decimalIntegral).toString();
    }
}
