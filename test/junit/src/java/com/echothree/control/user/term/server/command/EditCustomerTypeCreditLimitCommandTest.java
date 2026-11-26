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

package com.echothree.control.user.term.server.command;

import com.echothree.control.user.term.common.edit.TermEditFactory;
import com.echothree.control.user.term.common.spec.TermSpecFactory;
import com.echothree.model.control.accounting.server.logic.CurrencyLogic;
import com.echothree.model.control.core.common.transfer.EntityLockTransfer;
import com.echothree.model.control.customer.server.logic.CustomerTypeLogic;
import com.echothree.model.control.term.common.transfer.CustomerTypeCreditLimitTransfer;
import com.echothree.model.control.term.server.control.TermControl;
import com.echothree.model.data.accounting.common.pk.CurrencyPK;
import com.echothree.model.data.accounting.common.pk.SymbolPositionPK;
import com.echothree.model.data.accounting.server.entity.Currency;
import com.echothree.model.data.accounting.server.value.CurrencyValue;
import com.echothree.model.data.customer.common.pk.CustomerTypePK;
import com.echothree.model.data.customer.server.entity.CustomerType;
import com.echothree.model.data.customer.server.value.CustomerTypeValue;
import com.echothree.model.data.party.common.pk.PartyPK;
import com.echothree.model.data.term.common.pk.CustomerTypeCreditLimitPK;
import com.echothree.model.data.term.server.entity.CustomerTypeCreditLimit;
import com.echothree.model.data.term.server.value.CustomerTypeCreditLimitValue;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.common.command.EditMode;
import com.echothree.util.common.form.ValidationResult;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.message.Messages;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.BaseEntity;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.validation.Validator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.jupiter.params.provider.CsvSource;
import static org.junit.jupiter.api.Assertions.*;

@SuppressWarnings("UnnecessaryAssignment") // Supply CDI dependencies without a container.
public class EditCustomerTypeCreditLimitCommandTest {

    private static class TestCommand extends EditCustomerTypeCreditLimitCommand {
        private final Currency selectedCurrency = new Currency(new CurrencyValue(new CurrencyPK(1L), "USD", "$",
                new SymbolPositionPK(1L), false, false, false, false, ",", 3, ".", 2, 2, 2, 2, 2, "-", false, 0), EntityPermission.READ_ONLY);
        private final CustomerType customerType = new CustomerType(new CustomerTypeValue(new CustomerTypePK(2L), null, null), EntityPermission.READ_ONLY);
        private final CustomerTypeCreditLimit creditLimit = new CustomerTypeCreditLimit(new CustomerTypeCreditLimitValue(
                new CustomerTypeCreditLimitPK(3L), customerType.getPrimaryKey(), selectedCurrency.getPrimaryKey(),
                1234L, 5678L, 0L, Long.MAX_VALUE), EntityPermission.READ_WRITE) {
            @Override
            public CustomerType getCustomerType() { return customerType; }
            @Override
            public Currency getCurrency() { return selectedCurrency; }
        };
        private int currencyLookups;
        private int limitLookups;
        private int updates;
        private boolean transferred;
        private CustomerTypeCreditLimitValue savedValue;

        TestCommand(String missingEntity, EditMode requestedEditMode) {
            editMode = requestedEditMode;
            spec = TermSpecFactory.getCustomerTypeCreditLimitSpec();
            spec.setCustomerTypeName("TYPE");
            spec.setCurrencyIsoName("USD");
            edit = TermEditFactory.getCustomerTypeCreditLimitEdit();
            setLimits("1234", "5678");
            customerTypeLogic = new CustomerTypeLogic() {
                @Override
                public CustomerType getCustomerTypeByName(ExecutionErrorAccumulator eea, String customerTypeName) {
                    assertEquals("TYPE", customerTypeName);
                    if("type".equals(missingEntity)) {
                        eea.addExecutionError(ExecutionErrors.UnknownCustomerTypeName.name(), customerTypeName);
                    }
                    return "type".equals(missingEntity) ? null : customerType;
                }
            };
            currencyLogic = new CurrencyLogic() {
                @Override
                public Currency getCurrencyByName(ExecutionErrorAccumulator eea, String currencyIsoName) {
                    currencyLookups++;
                    assertEquals("USD", currencyIsoName);
                    if("currency".equals(missingEntity)) {
                        eea.addExecutionError(ExecutionErrors.UnknownCurrencyIsoName.name(), currencyIsoName);
                    }
                    return "currency".equals(missingEntity) ? null : selectedCurrency;
                }
            };
            termControl = new TermControl() {
                @Override
                public CustomerTypeCreditLimit getCustomerTypeCreditLimit(CustomerType selectedType, Currency currency, EntityPermission entityPermission) {
                    assertSame(customerType, selectedType);
                    assertSame(selectedCurrency, currency);
                    assertEquals(requestedEditMode == EditMode.UPDATE ? EntityPermission.READ_WRITE : EntityPermission.READ_ONLY, entityPermission);
                    limitLookups++;
                    return "limit".equals(missingEntity) ? null : creditLimit;
                }

                @Override
                public void updateCustomerTypeCreditLimitFromValue(CustomerTypeCreditLimitValue value, BasePK updatedBy) {
                    assertEquals(customerType.getPrimaryKey(), value.getCustomerTypePK());
                    assertEquals(selectedCurrency.getPrimaryKey(), value.getCurrencyPK());
                    updates++;
                    savedValue = value;
                }

                @Override
                public CustomerTypeCreditLimitTransfer getCustomerTypeCreditLimitTransfer(UserVisit userVisit, CustomerTypeCreditLimit selectedLimit) {
                    assertSame(creditLimit, selectedLimit);
                    transferred = true;
                    return null;
                }
            };
        }

        void setField(String fieldName, String value) { edit.set(fieldName, value); }
        String getField(String fieldName) { return (String)edit.get(fieldName); }

        ValidationResult submitUpdate() {
            var validationResult = validate();
            if(!validationResult.getHasErrors()) {
                execute();
            }
            return validationResult;
        }

        @Override
        public boolean lockEntityForUpdate(com.echothree.util.server.persistence.BaseEntity lockEntity) { return true; }
        @Override
        public boolean unlockEntity(com.echothree.util.server.persistence.BaseEntity lockEntity) { return true; }

        void setLimits(String limit, String potentialLimit) {
            edit.setCreditLimit(limit);
            edit.setPotentialCreditLimit(potentialLimit);
        }

        boolean hasValidationErrors() { return validate().getHasErrors(); }

        @Override
        public PartyPK getPartyPK() { return new PartyPK(99L); }
        @Override
        public UserVisit getUserVisit() { return null; }
        @Override
        public EntityLockTransfer getEntityLockTransfer(BaseEntity lockEntity) {
            assertSame(customerType, lockEntity);
            return null;
        }

        void assertValidationErrorResult(boolean missingLimit) {
            setLimits("92233720368547758.08", "56.78");
            assertTrue(hasValidationErrors());
            assertNotNull(getBaseResultAfterErrors());
            assertEquals(missingLimit, hasExecutionErrors());
            assertEquals(!missingLimit, transferred);
        }
    }

    @ParameterizedTest
    @ValueSource(strings = { "type", "currency", "limit" })
    public void missingEntitiesStopDependentLookupAndReportCompleteKey(String missingEntity) {
        var command = new TestCommand(missingEntity, EditMode.UPDATE);
        assertNull(command.getEntity(command.getResult()));
        var error = command.getExecutionErrors().get(Messages.EXECUTION_ERROR).next();
        var expected = switch(missingEntity) {
            case "type" -> ExecutionErrors.UnknownCustomerTypeName;
            case "currency" -> ExecutionErrors.UnknownCurrencyIsoName;
            case "limit" -> ExecutionErrors.UnknownCustomerTypeCreditLimit;
            default -> throw new IllegalArgumentException();
        };
        var key = switch(missingEntity) {
            case "type" -> new Object[] { "TYPE" };
            case "currency" -> new Object[] { "USD" };
            case "limit" -> new Object[] { "TYPE", "USD" };
            default -> throw new IllegalArgumentException();
        };
        assertEquals(expected.name(), error.getKey());
        assertArrayEquals(key, error.getValues());
        assertEquals("limit".equals(missingEntity) ? 1 : 0, command.limitLookups);
    }

    @ParameterizedTest
    @EnumSource(EditMode.class)
    public void usesRequestedPermissionAndLocksCustomerType(EditMode mode) {
        var command = new TestCommand(null, mode);
        assertSame(command.creditLimit, command.getEntity(command.getResult()));
        assertSame(command.customerType, command.getLockEntity(command.creditLimit));
    }

    @ParameterizedTest
    @ValueSource(booleans = { false, true })
    public void validationAndLookupReuseCurrencyEvenWhenUnknown(boolean unknownCurrency) {
        var command = new TestCommand(unknownCurrency ? "currency" : null, EditMode.UPDATE);
        var validator = new Validator(command);
        command.setupValidatorForEdit(validator, null);
        assertSame(unknownCurrency ? null : command.selectedCurrency, validator.getCurrency());
        command.getEntity(command.getResult());
        assertEquals(1, command.currencyLookups);
        assertEquals(unknownCurrency, command.hasExecutionErrors());
    }

    @Test
    public void lockFormatsCurrencyAmounts() {
        var command = new TestCommand(null, EditMode.LOCK);
        var edit = command.getEdit();
        command.doLock(edit, command.creditLimit);
        assertEquals("12.34", edit.getCreditLimit());
        assertEquals("56.78", edit.getPotentialCreditLimit());
    }

    @Test
    public void validationScalesDecimalAmountsBeforeUpdate() {
        var command = new TestCommand(null, EditMode.UPDATE);
        command.setLimits("99.99", "123.45");
        assertFalse(command.hasValidationErrors());
        command.doUpdate(command.creditLimit);
        assertEquals(9999L, command.savedValue.getCreditLimit());
        assertEquals(12345L, command.savedValue.getPotentialCreditLimit());
        assertEquals(1, command.currencyLookups);
    }

    @Test
    public void unchangedLimitsDoNotWrite() {
        var command = new TestCommand(null, EditMode.UPDATE);
        command.doUpdate(command.creditLimit);
        assertEquals(0, command.updates);
    }

    @ParameterizedTest
    @ValueSource(ints = { 0, 1, 2 })
    public void nullableAmountsCanBeClearedIndependently(int mask) {
        var command = new TestCommand(null, EditMode.UPDATE);
        command.setLimits((mask & 1) == 0 ? null : "1234", (mask & 2) == 0 ? null : "5678");
        command.doUpdate(command.creditLimit);
        assertEquals((mask & 1) == 0 ? null : 1234L, command.savedValue.getCreditLimit());
        assertEquals((mask & 2) == 0 ? null : 5678L, command.savedValue.getPotentialCreditLimit());
        assertEquals(1, command.updates);
    }

    @ParameterizedTest
    @ValueSource(booleans = { false, true })
    public void invalidEditRetainsResultWithoutDereferencingMissingLimit(boolean missingLimit) {
        new TestCommand(missingLimit ? "limit" : null, EditMode.UPDATE).assertValidationErrorResult(missingLimit);
    }

    @ParameterizedTest
    @ValueSource(strings = { "92233720368547758.08", "999999999999999999999.00" })
    public void rejectsOutOfRangeAmountsBeforeWriting(String amount) {
        var command = new TestCommand(null, EditMode.UPDATE);
        command.setLimits(amount, null);
        assertTrue(command.hasValidationErrors());
        assertEquals(0, command.updates);
    }


    @ParameterizedTest
    @CsvSource({"abc,InvalidFormat", "-1.00,MinimumValue", "1.234,ExcessFractionalDigits",
            "92233720368547758.08,MaximumValue", "1a2,InvalidFormat", "1.2.3,InvalidFormat"})
    public void invalidLimitsFailValidationBeforeAnyWriteAndRetainInput(String input, String errorKey) {
        for(var fieldName : new String[]{"CreditLimit", "PotentialCreditLimit"}) {
            var command = new TestCommand(null, EditMode.UPDATE);
            command.setLimits("12.34", "56.78");
            command.setField(fieldName, input);
            var result = command.submitUpdate();
            assertTrue(result.getHasErrors());
            assertTrue(result.getValidationMessages().containsKey(fieldName, errorKey));
            assertEquals(input, command.getField(fieldName));
            assertEquals(0, command.updates);
            assertNull(command.savedValue);
        }
    }

    @ParameterizedTest
    @ValueSource(strings = {"21474836.48", "92233720368547758.07", "0"})
    public void validatedLongAmountsReachTheUpdateWithoutLoss(String input) {
        var command = new TestCommand(null, EditMode.UPDATE);
        command.setLimits(input, "56.78");
        assertFalse(command.submitUpdate().getHasErrors());
        assertEquals(new java.math.BigDecimal(input).movePointRight(2).longValueExact(), command.savedValue.getCreditLimit());
        assertEquals(1, command.updates);
    }

    @Test
    public void validatedBlankAndZeroLimitsRemainDistinct() {
        var command = new TestCommand(null, EditMode.UPDATE);
        command.setLimits("  ", "0");
        assertFalse(command.submitUpdate().getHasErrors());
        assertNull(command.savedValue.getCreditLimit());
        assertEquals(0L, command.savedValue.getPotentialCreditLimit());
    }

    @Test
    public void unresolvedCommandCurrencyFailsWithoutDefaultCurrencyFallback() {
        var command = new TestCommand("currency", EditMode.UPDATE);
        command.setLimits("1", "2");
        assertTrue(command.submitUpdate().getHasErrors());
        assertTrue(command.hasExecutionErrors());
        assertEquals(0, command.updates);
        assertEquals("1", command.getField("CreditLimit"));
    }
}
