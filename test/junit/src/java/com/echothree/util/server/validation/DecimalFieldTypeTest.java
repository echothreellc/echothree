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

package com.echothree.util.server.validation;

import com.echothree.control.user.party.common.form.PartyFormFactory;
import com.echothree.control.user.party.server.command.CreateVendorCommand;
import com.echothree.control.user.term.common.edit.TermEditFactory;
import com.echothree.model.control.accounting.server.control.AccountingControl;
import com.echothree.model.data.accounting.common.pk.CurrencyPK;
import com.echothree.model.data.accounting.common.pk.SymbolPositionPK;
import com.echothree.model.data.accounting.server.entity.Currency;
import com.echothree.model.data.accounting.server.value.CurrencyValue;
import com.echothree.util.common.form.BaseForm;
import com.echothree.util.common.message.Messages;
import com.echothree.util.common.string.DecimalUtils;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.string.AmountUtils;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.ValueSource;
import static org.junit.jupiter.api.Assertions.*;

public class DecimalFieldTypeTest {

    private static final String FIELD = "CreditLimit";

    private Currency currency(String separator, int priceLineDigits) {
        return new Currency(new CurrencyValue(new CurrencyPK(1L), "USD", "$", new SymbolPositionPK(1L),
                false, false, false, false, ",", 3, separator, 2, 4, priceLineDigits, 6, 3, "-", false, 0),
                EntityPermission.READ_ONLY);
    }

    private Validator validator() {
        var validator = new Validator(null);
        validator.setCurrency(currency(".", 2));
        return validator;
    }

    private Messages validate(Validator validator, BaseForm form, FieldType fieldType, boolean required, Long minimum, Long maximum) {
        return validator.validateField(form, new FieldDefinition(FIELD, fieldType, required, minimum, maximum));
    }

    private void assertError(FieldType fieldType, String input, String expectedKey, Object... parameters) {
        var form = TermEditFactory.getCustomerTypeCreditLimitEdit();
        form.set(FIELD, input);
        var messages = validate(validator(), form, fieldType, false, null, null);
        assertNotNull(messages, input);
        assertEquals(1, messages.size());
        var error = messages.get(FIELD).next();
        assertEquals(expectedKey, error.getKey());
        if(parameters.length > 0) {
            assertArrayEquals(parameters, error.getValues());
        }
        assertEquals(input, form.get(FIELD));
    }

    @ParameterizedTest
    @EnumSource(value = FieldType.class, names = {"PRICE_LINE", "UNSIGNED_PRICE_LINE", "PRICE_UNIT", "UNSIGNED_PRICE_UNIT",
            "COST_LINE", "UNSIGNED_COST_LINE", "COST_UNIT", "UNSIGNED_COST_UNIT"})
    public void allAmountFieldsUseTheirPrecisionAndTheFullLongRange(FieldType fieldType) {
        var precision = switch(fieldType) {
            case PRICE_LINE, UNSIGNED_PRICE_LINE -> 2;
            case PRICE_UNIT, UNSIGNED_PRICE_UNIT -> 4;
            case COST_LINE, UNSIGNED_COST_LINE -> 3;
            case COST_UNIT, UNSIGNED_COST_UNIT -> 6;
            default -> throw new IllegalArgumentException();
        };
        var unsigned = fieldType.name().startsWith("UNSIGNED");
        var form = TermEditFactory.getCustomerTypeCreditLimitEdit();
        for(var amount : new long[]{0L, 2147483648L, Long.MAX_VALUE}) {
            form.set(FIELD, DecimalUtils.getInstance().format("-", ".", precision, amount));
            assertNull(validate(validator(), form, fieldType, false, null, null));
            assertEquals(Long.toString(amount), form.get(FIELD));
        }
        var maximum = DecimalUtils.getInstance().format("-", ".", precision, Long.MAX_VALUE);
        var overflow = new java.math.BigDecimal(java.math.BigInteger.valueOf(Long.MAX_VALUE).add(java.math.BigInteger.ONE), precision).toPlainString();
        assertError(fieldType, overflow, Validator.ERROR_MAXIMUM_VALUE, maximum);
        assertError(fieldType, "1." + "0".repeat(precision + 1), Validator.ERROR_EXCESS_FRACTIONAL_DIGITS, precision);
        if(unsigned) {
            assertError(fieldType, "-1", Validator.ERROR_MINIMUM_VALUE,
                    DecimalUtils.getInstance().format("-", ".", precision, 0L));
            assertError(fieldType, "-0", Validator.ERROR_MINIMUM_VALUE,
                    DecimalUtils.getInstance().format("-", ".", precision, 0L));
            assertError(fieldType, "MIN_VALUE", Validator.ERROR_INVALID_FORMAT);
        } else {
            var minimum = DecimalUtils.getInstance().format("-", ".", precision, Long.MIN_VALUE);
            form.set(FIELD, minimum);
            assertNull(validate(validator(), form, fieldType, false, null, null));
            assertEquals(Long.toString(Long.MIN_VALUE), form.get(FIELD));
            var underflow = new java.math.BigDecimal(java.math.BigInteger.valueOf(Long.MIN_VALUE).subtract(java.math.BigInteger.ONE), precision).toPlainString();
            assertError(fieldType, underflow, Validator.ERROR_MINIMUM_VALUE, minimum);
        }
    }

    @ParameterizedTest
    @ValueSource(strings = {"abc", ".", "1.2.3", "1a2", "--1", "1-", "$1", "1,000.00", "1 000.00", "1e2", "+-1"})
    public void rejectsMalformedAmountsAndPreservesInput(String input) {
        assertError(FieldType.UNSIGNED_PRICE_LINE, input, Validator.ERROR_INVALID_FORMAT);
    }

    @Test
    public void optionalBlanksRemainNullAndRequiredBlanksReportAnError() {
        var form = TermEditFactory.getCustomerTypeCreditLimitEdit();
        for(var input : new String[]{null, "", "  "}) {
            form.set(FIELD, input);
            assertNull(validate(validator(), form, FieldType.UNSIGNED_PRICE_LINE, false, null, null));
            assertNull(form.get(FIELD));
            form.set(FIELD, input);
            var errors = validate(validator(), form, FieldType.UNSIGNED_PRICE_LINE, true, null, null);
            assertTrue(errors.containsKey(FIELD, Validator.ERROR_REQUIRED_FIELD));
        }
        form.set(FIELD, "0");
        assertNull(validate(validator(), form, FieldType.UNSIGNED_PRICE_LINE, false, null, null));
        assertEquals("0", form.get(FIELD));
    }

    @Test
    public void boundsAndMarkersUseTheEffectiveScaledRange() {
        var form = TermEditFactory.getCustomerTypeCreditLimitEdit();
        for(var marker : new String[]{"MAX_VALUE", "max_value", "MIN_VALUE"}) {
            form.set(FIELD, marker);
            assertNull(validate(validator(), form, FieldType.PRICE_LINE, false, -100L, 500L));
            assertEquals(marker.equals("MIN_VALUE") ? "-100" : "500", form.get(FIELD));
        }
        form.set(FIELD, "5.01");
        assertEquals("5.00", validate(validator(), form, FieldType.PRICE_LINE, false, -100L, 500L).get(FIELD).next().getValues()[0]);
        form.set(FIELD, "-1.01");
        assertEquals("-1.00", validate(validator(), form, FieldType.PRICE_LINE, false, -100L, 500L).get(FIELD).next().getValues()[0]);
        form.set(FIELD, "MAX_VALUE");
        assertNull(validate(validator(), form, FieldType.UNSIGNED_PRICE_LINE, false, null, null));
        assertEquals(Long.toString(Long.MAX_VALUE), form.get(FIELD));
    }

    @Test
    public void currencySeparatorAndZeroPrecisionAreEnforced() {
        var validator = validator();
        validator.setCurrency(currency(",", 0));
        var form = TermEditFactory.getCustomerTypeCreditLimitEdit();
        form.set(FIELD, "1,0");
        assertTrue(validate(validator, form, FieldType.PRICE_LINE, false, null, null).containsKey(FIELD, Validator.ERROR_EXCESS_FRACTIONAL_DIGITS));
        validator.setCurrency(currency(",", 2));
        form.set(FIELD, " 1,25 ");
        assertNull(validate(validator, form, FieldType.PRICE_LINE, false, null, null));
        assertEquals("125", form.get(FIELD));
        form.set(FIELD, "1.25");
        assertTrue(validate(validator, form, FieldType.PRICE_LINE, false, null, null).containsKey(FIELD, Validator.ERROR_INVALID_FORMAT));
        validator.setCurrency(currency(null, 2));
        form.set(FIELD, "1");
        assertTrue(validate(validator, form, FieldType.PRICE_LINE, false, null, null).containsKey(FIELD, Validator.ERROR_INVALID_DECIMAL_CONFIGURATION));
    }

    @Test
    public void explicitMissingAndUnknownCurrenciesCannotFallBackToDefault() {
        var validator = new Validator(null) {
            @Override
            public AccountingControl getAccountingControl() {
                return new AccountingControl() {
                    @Override
                    public Currency getCurrencyByIsoName(String currencyIsoName) { return null; }
                    @Override
                    public Currency getDefaultCurrency() { fail("Unexpected default currency fallback"); return null; }
                };
            }
        };
        var form = TermEditFactory.getCustomerTypeCreditLimitEdit();
        form.set(FIELD, "1");
        var definition = new FieldDefinition(FIELD + ":CurrencyIsoName,CurrencyIsoName", FieldType.PRICE_LINE, false, null, null);
        assertTrue(validator.validateField(form, definition).containsKey(FIELD, Validator.ERROR_MISSING_CURRENCY));
        form.set("CurrencyIsoName", "BAD");
        var errors = validator.validateField(form, definition);
        assertTrue(errors.containsKey(FIELD, Validator.ERROR_UNKNOWN_CURRENCY_ISO_NAME));
        assertArrayEquals(new Object[]{"BAD"}, errors.get(FIELD).next().getValues());
    }

    @Test
    public void vendorPurchaseOrderLimitsUseTheDefaultCurrencyWhenPreferenceIsOmitted() {
        var command = new CreateVendorCommand() {
            @Override
            public List<FieldDefinition> getFormFieldDefinitions() {
                return super.getFormFieldDefinitions();
            }
        };
        var validator = new Validator(null) {
            @Override
            public AccountingControl getAccountingControl() {
                return new AccountingControl() {
                    @Override
                    public Currency getDefaultCurrency() { return currency(".", 2); }
                    @Override
                    public Currency getCurrencyByIsoName(String currencyIsoName) {
                        return currencyIsoName.equals("EUR") ? currency(",", 2) : null;
                    }
                };
            }
        };
        for(var fieldName : List.of("MinimumPurchaseOrderAmount", "MaximumPurchaseOrderAmount")) {
            var definition = command.getFormFieldDefinitions().stream()
                    .filter(fieldDefinition -> fieldDefinition.getFieldName().startsWith(fieldName + ":"))
                    .findFirst().orElseThrow();
            var form = PartyFormFactory.getCreateVendorForm();
            form.set(fieldName, "100.00");
            assertNull(validator.validateField(form, definition));
            assertEquals("100000", form.get(fieldName));
            assertNull(form.getPreferredCurrencyIsoName());

            form.setPreferredCurrencyIsoName("EUR");
            form.set(fieldName, "100,25");
            assertNull(validator.validateField(form, definition));
            assertEquals("100250", form.get(fieldName));

            form.setPreferredCurrencyIsoName("BAD");
            form.set(fieldName, "100.00");
            assertTrue(validator.validateField(form, definition).containsKey(fieldName, Validator.ERROR_UNKNOWN_CURRENCY_ISO_NAME));
        }
    }

    @Test
    public void missingDefaultCurrencyProducesAnExplicitError() {
        var validator = new Validator(null) {
            @Override
            public AccountingControl getAccountingControl() {
                return new AccountingControl() {
                    @Override
                    public Currency getDefaultCurrency() { return null; }
                };
            }
        };
        var form = TermEditFactory.getCustomerTypeCreditLimitEdit();
        form.set(FIELD, "1");
        assertTrue(validate(validator, form, FieldType.PRICE_LINE, false, null, null).containsKey(FIELD, Validator.ERROR_MISSING_CURRENCY));
    }

    @Test
    public void percentAndCoordinateCallersRejectRatherThanSanitize() {
        assertError(FieldType.FRACTIONAL_PERCENT, "1.2345%", Validator.ERROR_EXCESS_FRACTIONAL_DIGITS, 3);
        assertError(FieldType.FRACTIONAL_PERCENT, "101%", Validator.ERROR_MAXIMUM_VALUE, "100.000");
        assertError(FieldType.FRACTIONAL_PERCENT, "-1%", Validator.ERROR_MINIMUM_VALUE, "0.000");
        assertError(FieldType.LATITUDE, "1a", Validator.ERROR_INVALID_FORMAT);
        assertError(FieldType.LONGITUDE, "1.0000001", Validator.ERROR_EXCESS_FRACTIONAL_DIGITS, 6);
        assertError(FieldType.LATITUDE, "90.000001", Validator.ERROR_MAXIMUM_VALUE, "90.000000");
        for(var fieldType : List.of(FieldType.LATITUDE, FieldType.LONGITUDE, FieldType.FRACTIONAL_PERCENT)) {
            var form = TermEditFactory.getCustomerTypeCreditLimitEdit();
            form.set(FIELD, fieldType == FieldType.FRACTIONAL_PERCENT ? "12.345%" : "-12.345678");
            assertNull(validate(validator(), form, fieldType, false, null, null));
            assertEquals(fieldType == FieldType.FRACTIONAL_PERCENT ? "12345" : "-12345678", form.get(FIELD));
        }
    }

    @Test
    public void amountFormattingRoundTripsAllPrecisionsAndStorageLimits() {
        var currency = currency(",", 2);
        var amountUtils = AmountUtils.getInstance();
        for(var amount : new long[]{Long.MIN_VALUE, -1L, 0L, Long.MAX_VALUE}) {
            assertEquals(Long.toString(amount), DecimalUtils.getInstance().parse("-", ",", 2, amountUtils.formatAmount(currency, amount)));
            assertEquals(Long.toString(amount), DecimalUtils.getInstance().parse("-", ",", 2, amountUtils.formatPriceLine(currency, amount)));
            assertEquals(Long.toString(amount), DecimalUtils.getInstance().parse("-", ",", 4, amountUtils.formatPriceUnit(currency, amount)));
            assertEquals(Long.toString(amount), DecimalUtils.getInstance().parse("-", ",", 3, amountUtils.formatCostLine(currency, amount)));
            assertEquals(Long.toString(amount), DecimalUtils.getInstance().parse("-", ",", 6, amountUtils.formatCostUnit(currency, amount)));
        }
    }

    @Test
    public void explicitNullCurrencyDoesNotResolveAnUnrelatedDefault() {
        var validator = new Validator(null) {
            @Override
            public AccountingControl getAccountingControl() { throw new AssertionError("Unexpected currency lookup"); }
        };
        validator.setCurrency(null);
        var form = TermEditFactory.getCustomerTypeCreditLimitEdit();
        form.set(FIELD, "1");
        assertTrue(validate(validator, form, FieldType.PRICE_LINE, false, null, null).containsKey(FIELD, Validator.ERROR_MISSING_CURRENCY));
    }

    @Test
    public void currencyFormattingHandlesLongMinimumAndNegativeSubUnitAmounts() {
        var currency = currency(",", 2);
        var amountUtils = AmountUtils.getInstance();
        assertEquals("-0,01", amountUtils.formatAmount(currency, -1L));
        assertEquals("-0,01", amountUtils.formatPriceLine(currency, -1L));
        assertEquals("-0,0001", amountUtils.formatPriceUnit(currency, -1L));
        assertEquals("-0,001", amountUtils.formatCostLine(currency, -1L));
        assertEquals("-0,000001", amountUtils.formatCostUnit(currency, -1L));
        assertEquals("-92233720368547758,08", amountUtils.formatPriceLine(currency, Long.MIN_VALUE));
        assertNull(amountUtils.formatAmount(null, null));
        assertNull(amountUtils.formatPriceLine(null, null));
    }
}
