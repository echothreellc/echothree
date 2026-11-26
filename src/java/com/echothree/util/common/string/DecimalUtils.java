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

/**
 * Converts between plain decimal text and scaled integer values using exact arithmetic.
 * Format settings are supplied as values, without server, persistence, or currency-entity dependencies.
 * Instances retain no per-conversion state and may be shared between threads.
 *
 * <p>Parsing validates the original input before conversion and never rounds or truncates it. Callers are
 * responsible for required/optional field handling, destination storage limits, and any supported range markers.
 * For example, a precision of two maps {@code "12.34"} to the scaled integer string {@code "1234"}.
 */
public class DecimalUtils {

    /** Distinguishes invalid input, excess precision, and invalid format settings. */
    public enum ParseError {
        /** The input does not match the supported decimal syntax, or a negative value has no configured minus sign. */
        INVALID_FORMAT,
        /** The input contains more fractional digits than configured, including excess trailing zeroes. */
        EXCESS_FRACTIONAL_DIGITS,
        /** The precision or sign/separator settings are invalid or ambiguous. */
        INVALID_CONFIGURATION
    }

    /** Reports a decimal conversion failure with a machine-readable reason. */
    public static class ParseException extends IllegalArgumentException {
        /** Reason retained for callers to distinguish conversion failures. */
        private final ParseError error;

        /**
         * Creates a conversion failure whose message is the reason's enum name.
         *
         * @param error non-null reason for the failure
         * @throws NullPointerException if {@code error} is null
         */
        public ParseException(ParseError error) {
            super(error.name());
            this.error = error;
        }

        /**
         * Returns the reason for the conversion failure.
         *
         * @return the reason supplied when this exception was constructed
         */
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

    /**
     * Returns the lazily initialized, shared utility instance.
     *
     * @return the stateless decimal conversion utility
     */
    public static DecimalUtils getInstance() {
        return DecimalUtilsHolder.instance;
    }

    /**
     * Checks whether a supplied sign or separator contains unsupported characters.
     *
     * @param symbol sign or separator token; null represents an absent token
     * @return true for an empty token or one containing a Unicode letter, digit, whitespace, space character,
     *         or ASCII plus sign; false for null or an otherwise valid token
     */
    private boolean invalidSymbol(String symbol) {
        return symbol != null && (symbol.isEmpty() || symbol.codePoints().anyMatch(character ->
                Character.isLetterOrDigit(character) || Character.isWhitespace(character) || Character.isSpaceChar(character)
                        || character == '+'));
    }

    /**
     * Validates the format settings and resolves a null precision to zero.
     * Non-null sign and separator tokens must pass {@link #invalidSymbol(String)} and neither may contain the other.
     * A separator is required when the effective precision is greater than zero.
     *
     * @param minusSign literal negative-sign token, or null to disable negative values
     * @param fractionSeparator literal decimal separator, or null when no separator is supported
     * @param fractionDigits nonnegative fractional-digit count, or null for zero
     * @return the effective fractional-digit count
     * @throws ParseException with {@link ParseError#INVALID_CONFIGURATION} if the settings are invalid
     */
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
     * Parses plain decimal text to a canonical integer string scaled by ten to the configured precision.
     *
     * <p>The input may contain ASCII digits, one optional leading configured minus sign or ASCII plus sign,
     * and at most one configured decimal separator. At least one digit is required. Either side of the separator
     * may be empty when the other contains digits, so {@code ".5"} and {@code "1."} are valid with a dot separator
     * and sufficient precision. Surrounding whitespace is removed using {@link String#strip()}.
     * Grouping separators, currency symbols, internal whitespace, exponents, range markers such as {@code MAX_VALUE},
     * and all other characters outside the configured syntax are rejected.
     *
     * <p>Missing fractional digits are padded with zeroes; excess digits are rejected even when they are zeroes.
     * No floating-point conversion, rounding, or truncation occurs. With precision two, {@code "1.2"} produces
     * {@code "120"}, and {@code "-0.01"} produces {@code "-1"}. Leading zeroes, a leading plus sign, and negative
     * zero are normalized in the result. The result is not checked against an {@code Integer} or {@code Long} range.
     *
     * @param minusSign literal token accepted at the start of negative input; null disables negative input
     * @param fractionSeparator literal decimal separator; null disables separator input and requires zero precision
     * @param fractionDigits maximum fractional-digit count and scaling precision; null means zero
     * @param value original decimal text; null and blank input are invalid rather than optional or zero
     * @return the exact scaled integer as ASCII decimal digits with an optional leading ASCII minus sign;
     *         never null, with no redundant leading zeroes and with zero represented as {@code "0"}
     * @throws ParseException with {@link ParseError#INVALID_CONFIGURATION} if precision is negative, a required
     *         separator is absent, a non-null token is empty or contains a letter, digit, whitespace, space character,
     *         or ASCII plus sign, or either configured token contains the other;
     *         with {@link ParseError#INVALID_FORMAT} if the input does not match the supported syntax;
     *         or with {@link ParseError#EXCESS_FRACTIONAL_DIGITS} if its fractional-digit count exceeds the precision
     * @see #format(String, String, Integer, Long)
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

    /**
     * Formats a scaled {@code Long} as plain decimal text using the supplied sign, separator, and precision.
     *
     * <p>The value is interpreted as an integer count of units of ten to the negative precision. With precision
     * two, {@code 1234L} produces {@code "12.34"} with a dot separator, and {@code -1L} produces {@code "-0.01"}
     * with a hyphen minus sign. Positive precision always produces exactly that many fractional digits; zero
     * precision produces an integer without a separator. No grouping, currency symbol, exponent, or plus sign
     * is added. Conversion is exact for the entire {@code Long} range, including {@link Long#MIN_VALUE}.
     *
     * <p>A null minus sign disables negative values, matching {@link #parse(String, String, Integer, String)}.
     * Nonnegative values remain supported. A null value returns null without validating the format settings.
     *
     * @param minusSign literal token prepended to negative output; null permits only nonnegative values
     * @param fractionSeparator literal decimal separator; required for positive precision and otherwise omitted
     * @param fractionDigits number of fractional digits and scaling precision; null means zero
     * @param value scaled storage value, or null for an absent value
     * @return the exact decimal representation using the configured tokens, or null if {@code value} is null
     * @throws ParseException with {@link ParseError#INVALID_CONFIGURATION} if a non-null value is supplied and
     *         the format settings violate the configuration rules documented by
     *         {@link #parse(String, String, Integer, String)}; or with {@link ParseError#INVALID_FORMAT} if
     *         {@code value} is negative and {@code minusSign} is null
     * @see #parse(String, String, Integer, String)
     */
    public String format(String minusSign, String fractionSeparator, Integer fractionDigits, Long value) {
        String result = null;

        if(value != null) {
            var precision = validateConfiguration(minusSign, fractionSeparator, fractionDigits);

            if(value < 0 && minusSign == null) {
                throw new ParseException(ParseError.INVALID_FORMAT);
            }

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
