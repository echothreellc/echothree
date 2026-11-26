// --------------------------------------------------------------------------------
// Copyright 2002-2026 Echo Three, LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
// --------------------------------------------------------------------------------

package com.echothree.util.common.string;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.regex.Pattern;

/** Exact decimal conversion with no server, persistence, or currency-entity dependencies. */
public class DecimalUtils {

    public enum ParseError {
        INVALID_FORMAT,
        EXCESS_FRACTIONAL_DIGITS,
        INVALID_CONFIGURATION
    }

    public static class ParseException extends IllegalArgumentException {
        private final ParseError error;

        public ParseException(ParseError error) {
            super(error.name());
            this.error = error;
        }

        public ParseError getError() {
            return error;
        }
    }

    private DecimalUtils() {
        super();
    }

    private static class DecimalUtilsHolder {
        static final DecimalUtils instance = new DecimalUtils();
    }

    public static DecimalUtils getInstance() {
        return DecimalUtilsHolder.instance;
    }

    private boolean invalidSymbol(String symbol) {
        return symbol != null && (symbol.isEmpty() || symbol.codePoints().anyMatch(character ->
                Character.isLetterOrDigit(character) || Character.isWhitespace(character) || Character.isSpaceChar(character)
                        || character == '+'));
    }

    private int validateConfiguration(String minusSign, String fractionSeparator, Integer fractionDigits) {
        var precision = fractionDigits == null ? 0 : fractionDigits;

        if(precision < 0 || (precision > 0 && fractionSeparator == null) || invalidSymbol(minusSign)
                || invalidSymbol(fractionSeparator) || (fractionSeparator != null && minusSign != null
                    && (fractionSeparator.contains(minusSign) || minusSign.contains(fractionSeparator)))) {
            throw new ParseException(ParseError.INVALID_CONFIGURATION);
        }

        return precision;
    }

    /**
     * Parses a plain decimal to a canonical scaled integer string, without rounding or a storage-size limit.
     * ASCII digits, a leading configured minus sign or ASCII plus, and an optional configured decimal separator
     * are accepted. Surrounding whitespace is ignored; grouping, symbols, internal whitespace, exponents, and
     * range markers are rejected. A null minus sign disables negative input; null precision means zero digits.
     * Callers handle optional/required blanks and explicitly resolve any supported range markers before parsing.
     */
    public String parse(String minusSign, String fractionSeparator, Integer fractionDigits, String value) {
        var precision = validateConfiguration(minusSign, fractionSeparator, fractionDigits);
        var decimal = value == null ? "" : value.strip();
        var negative = minusSign != null && decimal.startsWith(minusSign);

        if(negative) {
            decimal = decimal.substring(minusSign.length());
        } else if(decimal.startsWith("+")) {
            decimal = decimal.substring(1);
        }

        var separatorPattern = fractionSeparator == null ? null : Pattern.quote(fractionSeparator);
        var decimalPattern = separatorPattern == null ? "[0-9]+"
                : "(?:[0-9]+(?:" + separatorPattern + "[0-9]*)?|" + separatorPattern + "[0-9]+)";

        if(!decimal.matches(decimalPattern)) {
            throw new ParseException(ParseError.INVALID_FORMAT);
        }

        var separatorIndex = fractionSeparator == null ? -1 : decimal.indexOf(fractionSeparator);
        var whole = separatorIndex < 0 ? decimal : decimal.substring(0, separatorIndex);
        var fraction = separatorIndex < 0 ? "" : decimal.substring(separatorIndex + fractionSeparator.length());

        if(fraction.length() > precision) {
            throw new ParseException(ParseError.EXCESS_FRACTIONAL_DIGITS);
        }

        var scaled = new BigInteger((negative ? "-" : "") + (whole.isEmpty() ? "0" : whole) + fraction
                + "0".repeat(precision - fraction.length()));

        return scaled.toString();
    }

    /** Formats scaled storage values exactly, including Long.MIN_VALUE and negative values smaller than one. */
    public String format(String minusSign, String fractionSeparator, Integer fractionDigits, Long value) {
        String result = null;

        if(value != null) {
            var precision = validateConfiguration(minusSign, fractionSeparator, fractionDigits);
            result = BigDecimal.valueOf(value, precision).toPlainString();
            if(precision > 0) {
                result = result.replace(".", fractionSeparator);
            }
            if(value < 0 && minusSign != null) {
                result = minusSign + result.substring(1);
            }
        }

        return result;
    }

}
