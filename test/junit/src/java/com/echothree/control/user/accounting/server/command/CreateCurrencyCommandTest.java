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

package com.echothree.control.user.accounting.server.command;

import com.echothree.control.user.accounting.common.form.AccountingFormFactory;
import com.echothree.model.control.accounting.server.control.AccountingControl;
import com.echothree.model.control.accounting.server.logic.SymbolPositionLogic;
import com.echothree.model.data.accounting.common.pk.SymbolPositionPK;
import com.echothree.model.data.accounting.server.entity.Currency;
import com.echothree.model.data.accounting.server.entity.SymbolPosition;
import com.echothree.model.data.accounting.server.value.SymbolPositionValue;
import com.echothree.model.data.party.common.pk.PartyPK;
import com.echothree.util.common.form.ValidationResult;
import com.echothree.util.common.message.Messages;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.EntityPermission;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import static org.junit.jupiter.api.Assertions.*;

@SuppressWarnings("UnnecessaryAssignment") // Supply CDI dependencies without a container.
public class CreateCurrencyCommandTest {

    private static final List<String> PRECISION_FIELDS = List.of("DefaultFractionDigits", "PriceUnitFractionDigits",
            "PriceLineFractionDigits", "CostUnitFractionDigits", "CostLineFractionDigits");
    private static final PartyPK CREATED_BY = new PartyPK(99L);

    private static class TestCommand extends CreateCurrencyCommand {
        private final SymbolPosition symbolPosition = new SymbolPosition(
                new SymbolPositionValue(new SymbolPositionPK(1L), null, null), EntityPermission.READ_ONLY);
        private int executions;
        private int creates;
        private int currencyLookups;
        private List<Integer> savedPrecisions;
        private String savedFractionSeparator;

        TestCommand() {
            accountingControl = new AccountingControl() {
                @Override
                public Currency getCurrencyByIsoName(String currencyIsoName) {
                    assertEquals("TST", currencyIsoName);
                    currencyLookups++;
                    return null;
                }

                @Override
                public Currency createCurrency(String currencyIsoName, String symbol, SymbolPosition selectedSymbolPosition,
                        Boolean symbolOnListStart, Boolean symbolOnListMember, Boolean symbolOnSubtotal, Boolean symbolOnTotal,
                        String groupingSeparator, Integer groupingSize, String fractionSeparator, Integer defaultFractionDigits,
                        Integer priceUnitFractionDigits, Integer priceLineFractionDigits, Integer costUnitFractionDigits,
                        Integer costLineFractionDigits, String minusSign, Boolean isDefault, Integer sortOrder, BasePK createdBy) {
                    assertEquals("TST", currencyIsoName);
                    assertSame(symbolPosition, selectedSymbolPosition);
                    assertEquals(CREATED_BY, createdBy);
                    creates++;
                    savedFractionSeparator = fractionSeparator;
                    savedPrecisions = Arrays.asList(defaultFractionDigits, priceUnitFractionDigits, priceLineFractionDigits,
                            costUnitFractionDigits, costLineFractionDigits);
                    return null;
                }
            };
            symbolPositionLogic = new SymbolPositionLogic() {
                @Override
                public SymbolPosition getSymbolPositionByName(ExecutionErrorAccumulator eea, String symbolPositionName) {
                    assertEquals("BEFORE_AMOUNT", symbolPositionName);
                    return symbolPosition;
                }
            };
            form = AccountingFormFactory.getCreateCurrencyForm();
            form.setCurrencyIsoName("TST");
            form.setSymbolPositionName("BEFORE_AMOUNT");
            form.setGroupingSeparator(",");
            form.setGroupingSize("3");
            form.setFractionSeparator(".");
            form.setMinusSign("-");
            form.setIsDefault("false");
            form.setSortOrder("0");
            PRECISION_FIELDS.forEach(fieldName -> form.set(fieldName, "2"));
        }

        void setPrecision(String fieldName, String value) { form.set(fieldName, value); }
        void setFractionSeparator(String value) { form.setFractionSeparator(value); }

        ValidationResult submit() {
            var validationResult = validate();
            if(!validationResult.getHasErrors()) {
                executions++;
                execute();
            }
            return validationResult;
        }

        @Override
        public PartyPK getPartyPK() { return CREATED_BY; }
    }

    private static Stream<Arguments> invalidPrecisions() {
        return PRECISION_FIELDS.stream().flatMap(fieldName -> Stream.of(
                Arguments.of(fieldName, "-1", "MinimumValue", "0"),
                Arguments.of(fieldName, "-2147483648", "MinimumValue", "0"),
                Arguments.of(fieldName, "20", "MaximumValue", "19"),
                Arguments.of(fieldName, "2147483647", "MaximumValue", "19"),
                Arguments.of(fieldName, "MAX_VALUE", "MaximumValue", "19"),
                Arguments.of(fieldName, "MIN_VALUE", "MinimumValue", "0")));
    }

    @ParameterizedTest
    @MethodSource("invalidPrecisions")
    public void rejectsOutOfRangePrecisionOnEachFieldBeforeExecution(String fieldName, String input, String errorKey,
            String bound) {
        var command = new TestCommand();
        command.setPrecision(fieldName, input);
        var result = command.submit();
        assertTrue(result.getHasErrors());
        assertEquals(1, result.getValidationMessages().size());
        var error = result.getValidationMessages().get(fieldName).next();
        assertEquals(errorKey, error.getKey());
        assertArrayEquals(new Object[]{bound}, error.getValues());
        assertEquals(0, command.executions);
        assertEquals(0, command.currencyLookups);
        assertEquals(0, command.creates);
    }

    private static Stream<Arguments> supportedPrecisions() {
        return PRECISION_FIELDS.stream().flatMap(fieldName -> Stream.of(0, 2, 6, 19)
                .map(precision -> Arguments.of(fieldName, precision)));
    }

    @ParameterizedTest
    @MethodSource("supportedPrecisions")
    public void acceptsSupportedPrecisionOnEachFieldAndPassesItUnchangedToCreate(String fieldName, int precision) {
        var command = new TestCommand();
        command.setPrecision(fieldName, Integer.toString(precision));
        assertFalse(command.submit().getHasErrors());
        assertFalse(command.hasExecutionErrors());
        assertEquals(1, command.executions);
        assertEquals(1, command.creates);
        assertEquals(".", command.savedFractionSeparator);
        for(var index = 0; index < PRECISION_FIELDS.size(); index++) {
            assertEquals(PRECISION_FIELDS.get(index).equals(fieldName) ? precision : 2, command.savedPrecisions.get(index));
        }
    }

    @Test
    public void preservesDistinctPrecisionsForTheFiveAmountFormats() {
        var command = new TestCommand();
        var precisions = List.of(0, 2, 6, 18, 19);
        for(var index = 0; index < PRECISION_FIELDS.size(); index++) {
            command.setPrecision(PRECISION_FIELDS.get(index), precisions.get(index).toString());
        }
        assertFalse(command.submit().getHasErrors());
        assertEquals(1, command.creates);
        assertEquals(precisions, command.savedPrecisions);
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"  "})
    public void integerOnlyCurrenciesCanStillOmitTheSeparatorAndPrecisions(String input) {
        var command = new TestCommand();
        command.setFractionSeparator(input);
        PRECISION_FIELDS.forEach(fieldName -> command.setPrecision(fieldName, input));
        assertFalse(command.submit().getHasErrors());
        assertFalse(command.hasExecutionErrors());
        assertEquals(1, command.creates);
        assertNull(command.savedFractionSeparator);
        assertEquals(Arrays.asList(null, null, null, null, null), command.savedPrecisions);
    }

    private static Stream<Arguments> missingPrecisions() {
        return PRECISION_FIELDS.stream().flatMap(fieldName -> Stream.of(null, "", "  ")
                .map(input -> Arguments.of(fieldName, input)));
    }

    @ParameterizedTest
    @MethodSource("missingPrecisions")
    public void missingPrecisionWithASeparatorReportsItsExistingErrorWithoutThrowing(String fieldName, String input) {
        var command = new TestCommand();
        command.setPrecision(fieldName, input);
        assertFalse(assertDoesNotThrow(command::submit).getHasErrors());
        assertTrue(command.hasExecutionErrors());
        assertEquals(1, command.getExecutionErrors().size());
        var error = command.getExecutionErrors().get(Messages.EXECUTION_ERROR).next();
        assertEquals("Missing" + fieldName, error.getKey());
        assertEquals(1, command.executions);
        assertEquals(0, command.creates);
        assertNull(command.savedPrecisions);
    }

    private static IntStream missingPrecisionCombinations() {
        return IntStream.range(1, 1 << PRECISION_FIELDS.size());
    }

    @ParameterizedTest
    @MethodSource("missingPrecisionCombinations")
    public void reportsEveryMissingPrecisionTogetherAndNeverCreatesAPartialCurrency(int missingFields) {
        var command = new TestCommand();
        for(var index = 0; index < PRECISION_FIELDS.size(); index++) {
            if((missingFields & (1 << index)) != 0) {
                command.setPrecision(PRECISION_FIELDS.get(index), null);
            }
        }
        assertFalse(assertDoesNotThrow(command::submit).getHasErrors());
        var errors = command.getExecutionErrors();
        assertNotNull(errors);
        assertEquals(Integer.bitCount(missingFields), errors.size());
        for(var index = 0; index < PRECISION_FIELDS.size(); index++) {
            assertEquals((missingFields & (1 << index)) != 0,
                    errors.containsKey(Messages.EXECUTION_ERROR, "Missing" + PRECISION_FIELDS.get(index)));
        }
        assertEquals(0, command.creates);
        assertNull(command.savedPrecisions);
    }
}
