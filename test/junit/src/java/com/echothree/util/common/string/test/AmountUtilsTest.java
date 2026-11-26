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

import com.echothree.model.control.accounting.common.transfer.CurrencyTransfer;
import com.echothree.util.common.string.AmountFormatType;
import com.echothree.util.common.string.AmountFormatter;
import com.echothree.util.common.string.AmountUtils;
import com.echothree.util.common.string.DecimalUtils;
import de.odysseus.el.ExpressionFactoryImpl;
import de.odysseus.el.util.SimpleContext;
import java.math.BigDecimal;
import java.math.BigInteger;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.EnumSource;
import static org.junit.jupiter.api.Assertions.*;

public class AmountUtilsTest {

    private final AmountUtils amountUtils = AmountUtils.getInstance();

    private CurrencyTransfer currency(Integer precision, String separator, String minusSign) {
        return new CurrencyTransfer("TST", "$", null, false, false, false, false, ".", 3, separator,
                precision, precision, precision, precision, precision, minusSign, false, 0, "Test");
    }

    private String format(CurrencyTransfer currency, AmountFormatType amountFormatType, Number amount) {
        return switch(amountFormatType) {
            case AMOUNT -> amountUtils.formatAmount(currency, amount);
            case PRICE_UNIT -> amountUtils.formatPriceUnit(currency, amount);
            case PRICE_LINE -> amountUtils.formatPriceLine(currency, amount);
            case COST_UNIT -> amountUtils.formatCostUnit(currency, amount);
            case COST_LINE -> amountUtils.formatCostLine(currency, amount);
        };
    }

    private String formatInteger(CurrencyTransfer currency, AmountFormatType amountFormatType, Integer amount) {
        return switch(amountFormatType) {
            case AMOUNT -> amountUtils.formatAmount(currency, amount);
            case PRICE_UNIT -> amountUtils.formatPriceUnit(currency, amount);
            case PRICE_LINE -> amountUtils.formatPriceLine(currency, amount);
            case COST_UNIT -> amountUtils.formatCostUnit(currency, amount);
            case COST_LINE -> amountUtils.formatCostLine(currency, amount);
        };
    }

    @ParameterizedTest
    @EnumSource(AmountFormatType.class)
    public void formatsTheFullLongRangeAndSubUnitNegativesExactly(AmountFormatType amountFormatType) {
        var currency = currency(2, ",", "~");
        var formatter = amountUtils.getAmountFormatter(currency, amountFormatType);
        assertEquals("~92233720368547758,08", format(currency, amountFormatType, Long.MIN_VALUE));
        assertEquals("92233720368547758,07", format(currency, amountFormatType, Long.MAX_VALUE));
        assertEquals("21474836,48", format(currency, amountFormatType, 2147483648L));
        assertEquals("~0,01", format(currency, amountFormatType, -1L));
        assertEquals("0,00", format(currency, amountFormatType, 0L));
        assertEquals("~92233720368547758,08", formatter.format(Long.MIN_VALUE));
        assertEquals("92233720368547758,07", formatter.format(Long.MAX_VALUE));
        assertEquals("~0,01", formatter.format(-1L));
    }

    @ParameterizedTest
    @EnumSource(AmountFormatType.class)
    public void existingIntegerEntryPointsRetainExactFormatting(AmountFormatType amountFormatType) {
        var currency = currency(2, ",", "~");
        var formatter = amountUtils.getAmountFormatter(currency, amountFormatType);
        assertEquals("~21474836,48", formatInteger(currency, amountFormatType, Integer.MIN_VALUE));
        assertEquals("21474836,47", formatInteger(currency, amountFormatType, Integer.MAX_VALUE));
        assertEquals("~0,01", formatInteger(currency, amountFormatType, -1));
        assertEquals("~21474836,48", formatter.format(Integer.MIN_VALUE));
        assertEquals("~0,01", formatter.format(-1));
    }

    @ParameterizedTest
    @EnumSource(AmountFormatType.class)
    public void roundTripsHighPrecisionWithoutPowerOverflow(AmountFormatType amountFormatType) {
        for(var precision : new int[]{0, 2, 6, 10, 19}) {
            var currency = currency(precision, ",", "~");
            var formatter = amountUtils.getAmountFormatter(currency, amountFormatType);
            for(var amount : new long[]{Long.MIN_VALUE, -1, 0, 1, Long.MAX_VALUE}) {
                var formatted = format(currency, amountFormatType, amount);
                assertEquals(Long.toString(amount), DecimalUtils.getInstance().parse("~", ",", precision, formatted));
                assertEquals(formatted, formatter.format(amount));
            }
        }
        assertEquals("0,0000000000000000001", format(currency(19, ",", "~"), amountFormatType, 1L));
    }

    @ParameterizedTest
    @CsvSource({"AMOUNT,~0.1", "PRICE_UNIT,~0.01", "PRICE_LINE,~0.001", "COST_UNIT,~0.0001", "COST_LINE,~0.00001"})
    public void selectsTheCorrectCurrencyPrecision(AmountFormatType amountFormatType, String expected) {
        var currency = new CurrencyTransfer("TST", "$", null, false, false, false, false, ",", 3, ".",
                1, 2, 3, 4, 5, "~", false, 0, "Test");
        assertEquals(expected, format(currency, amountFormatType, -1L));
        assertEquals(expected, formatInteger(currency, amountFormatType, -1));
        assertEquals(expected, amountUtils.getAmountFormatter(currency, amountFormatType).format(-1L));
    }

    @ParameterizedTest
    @EnumSource(AmountFormatType.class)
    public void absentValuesStayNullAndMissingPrecisionMeansIntegerFormatting(AmountFormatType amountFormatType) {
        Long absentLong = null;
        assertNull(format(null, amountFormatType, absentLong));
        assertNull(formatInteger(null, amountFormatType, null));
        assertNull(amountUtils.getAmountFormatter(null, amountFormatType).format(absentLong));
        assertNull(amountUtils.getAmountFormatter(null, amountFormatType).format(null));
        var currency = currency(null, null, "~");
        assertEquals("~9223372036854775808", format(currency, amountFormatType, Long.MIN_VALUE));
        assertEquals("~2147483648", formatInteger(currency, amountFormatType, Integer.MIN_VALUE));
    }

    @Test
    public void untypedNullCallsRemainUnambiguousAndReturnNull() {
        assertNull(amountUtils.formatAmount(null, null));
        assertNull(amountUtils.formatPriceUnit(null, null));
        assertNull(amountUtils.formatPriceLine(null, null));
        assertNull(amountUtils.formatCostUnit(null, null));
        assertNull(amountUtils.formatCostLine(null, null));
        assertNull(new AmountFormatter(null, AmountFormatType.AMOUNT).format(null));
    }

    @ParameterizedTest
    @EnumSource(AmountFormatType.class)
    public void unsupportedNumberTypesCannotBeSilentlyTruncatedOrWrapped(AmountFormatType amountFormatType) {
        var currency = currency(2, ".", "-");
        var formatter = amountUtils.getAmountFormatter(currency, amountFormatType);
        for(var amount : new Number[]{1.5d, 1.5f, new BigDecimal("1.5"), new BigInteger("9223372036854775808")}) {
            assertEquals(DecimalUtils.ParseError.INVALID_FORMAT,
                    assertThrows(DecimalUtils.ParseException.class, () -> format(currency, amountFormatType, amount)).getError());
            assertEquals(DecimalUtils.ParseError.INVALID_FORMAT,
                    assertThrows(DecimalUtils.ParseException.class, () -> formatter.format(amount)).getError());
        }
    }

    @ParameterizedTest
    @EnumSource(AmountFormatType.class)
    public void sharesTheStrictSignAndConfigurationContract(AmountFormatType amountFormatType) {
        assertEquals(DecimalUtils.ParseError.INVALID_FORMAT, assertThrows(DecimalUtils.ParseException.class,
                () -> format(currency(2, ".", null), amountFormatType, -1L)).getError());
        assertEquals(DecimalUtils.ParseError.INVALID_CONFIGURATION, assertThrows(DecimalUtils.ParseException.class,
                () -> format(currency(2, null, "-"), amountFormatType, 1L)).getError());
        assertEquals("0.01", format(currency(2, ".", null), amountFormatType, 1L));
    }

    @ParameterizedTest
    @EnumSource(AmountFormatType.class)
    public void expressionLanguageCallsKeepLongValuesWithoutNarrowing(AmountFormatType amountFormatType) {
        var factory = new ExpressionFactoryImpl();
        var context = new SimpleContext();
        context.setVariable("formatter", factory.createValueExpression(
                amountUtils.getAmountFormatter(currency(2, ".", "-"), amountFormatType), AmountFormatter.class));
        context.setVariable("amount", factory.createValueExpression(Long.MAX_VALUE, Long.class));
        assertEquals("92233720368547758.07",
                factory.createValueExpression(context, "${formatter.format(amount)}", String.class).getValue(context));
    }

    @ParameterizedTest
    @EnumSource(AmountFormatType.class)
    public void acceptsIntegralWrappersWithoutTruncation(AmountFormatType amountFormatType) {
        var currency = currency(2, ".", "-");
        for(var amount : new Number[]{(byte)-1, (short)-1, -1, -1L}) {
            assertEquals("-0.01", format(currency, amountFormatType, amount));
            assertEquals("-0.01", amountUtils.getAmountFormatter(currency, amountFormatType).format(amount));
        }
    }
}
