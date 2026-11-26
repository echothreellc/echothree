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

package com.echothree.util.common.string.test;

import com.echothree.util.common.string.DecimalUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import static org.junit.jupiter.api.Assertions.*;

public class DecimalUtilsTest {

    private final DecimalUtils decimalUtils = DecimalUtils.getInstance();

    @ParameterizedTest
    @CsvSource({"0,0", "0.00,0", "001.20,120", "-0.01,-1", "+1.2,120", ".5,50", "1.,100",
            "92233720368547758.07,9223372036854775807", "-92233720368547758.08,-9223372036854775808",
            "92233720368547758.08,9223372036854775808"})
    public void scalesExactlyWithoutImposingAStorageRange(String input, String expected) {
        assertEquals(expected, decimalUtils.parse("-", ".", 2, input));
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" ", "abc", ".", "-", "+", "1a2", "1.2.3", "1-2", "--1", "-+1", "+-1",
            "1+", "1,000.00", "$1.00", "1.00 USD", "1 2", "1e2", "NaN", "MAX_VALUE", "MIN_VALUE", "１２"})
    public void rejectsMalformedOriginalInput(String input) {
        var exception = assertThrows(DecimalUtils.ParseException.class, () -> decimalUtils.parse("-", ".", 2, input));
        assertEquals(DecimalUtils.ParseError.INVALID_FORMAT, exception.getError());
    }

    @ParameterizedTest
    @ValueSource(strings = {"1.001", "0.000", "-1.234", "100.0000000"})
    public void rejectsExcessDigitsEvenWhenTheyAreZero(String input) {
        var exception = assertThrows(DecimalUtils.ParseException.class, () -> decimalUtils.parse("-", ".", 2, input));
        assertEquals(DecimalUtils.ParseError.EXCESS_FRACTIONAL_DIGITS, exception.getError());
    }

    @Test
    public void handlesCurrencySettingsAndPrecisionWithoutEntities() {
        assertEquals("-123450", decimalUtils.parse("~", ",", 3, "  ~123,45  "));
        assertEquals("123", decimalUtils.parse(null, null, null, "123"));
        assertEquals("12345678901", decimalUtils.parse("-", ".", 10, "1.2345678901"));
        assertEquals("1", decimalUtils.parse("-", ".", 0, "1"));
        assertEquals(DecimalUtils.ParseError.EXCESS_FRACTIONAL_DIGITS,
                assertThrows(DecimalUtils.ParseException.class, () -> decimalUtils.parse("-", ".", 0, "1.0")).getError());
        assertThrows(DecimalUtils.ParseException.class, () -> decimalUtils.parse(null, ".", 2, "-1"));
        assertThrows(DecimalUtils.ParseException.class, () -> decimalUtils.parse("~", ",", 2, "-1,00"));
    }

    @Test
    public void rejectsInvalidConfigurationExplicitly() {
        for(var separator : new String[]{null, "", "1", "+", "-", "a", " ", "\t"}) {
            assertEquals(DecimalUtils.ParseError.INVALID_CONFIGURATION,
                    assertThrows(DecimalUtils.ParseException.class, () -> decimalUtils.parse("-", separator, 2, "1")).getError());
        }
        assertEquals(DecimalUtils.ParseError.INVALID_CONFIGURATION,
                assertThrows(DecimalUtils.ParseException.class, () -> decimalUtils.parse("-", ".", -1, "1")).getError());
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 2, 6, 10, 19})
    public void nullMinusSignRejectsNegativeValuesInBothDirections(int precision) {
        for(var amount : new long[]{Long.MIN_VALUE, -100, -1}) {
            var formatException = assertThrows(DecimalUtils.ParseException.class,
                    () -> decimalUtils.format(null, ".", precision, amount));
            assertEquals(DecimalUtils.ParseError.INVALID_FORMAT, formatException.getError());
            var signedValue = decimalUtils.format("-", ".", precision, amount);
            var parseException = assertThrows(DecimalUtils.ParseException.class,
                    () -> decimalUtils.parse(null, ".", precision, signedValue));
            assertEquals(DecimalUtils.ParseError.INVALID_FORMAT, parseException.getError());
        }
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 2, 6, 10, 19})
    public void nullMinusSignPreservesNonnegativeRoundTripsAndAbsentValues(int precision) {
        for(var amount : new long[]{0, 1, 100, Long.MAX_VALUE}) {
            var formatted = decimalUtils.format(null, ",", precision, amount);
            assertEquals(Long.toString(amount), decimalUtils.parse(null, ",", precision, formatted));
        }
        assertNull(decimalUtils.format(null, ",", precision, null));
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 2, 6, 10, 19})
    public void roundTripsFullLongRangeAndSubUnitNegatives(int precision) {
        for(var amount : new long[]{Long.MIN_VALUE, Long.MIN_VALUE + 1, -100, -1, 0, 1, 100, Long.MAX_VALUE}) {
            var formatted = decimalUtils.format("~", ",", precision, amount);
            assertEquals(Long.toString(amount), decimalUtils.parse("~", ",", precision, formatted));
        }
        assertNull(decimalUtils.format("-", ".", precision, null));
    }
}
