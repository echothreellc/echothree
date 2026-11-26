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
import com.echothree.model.control.party.server.logic.PartyChainLogic;
import com.echothree.model.control.party.server.logic.PartyLogic;
import com.echothree.model.control.term.server.control.TermControl;
import com.echothree.model.data.accounting.common.pk.CurrencyPK;
import com.echothree.model.data.accounting.common.pk.SymbolPositionPK;
import com.echothree.model.data.accounting.server.entity.Currency;
import com.echothree.model.data.accounting.server.value.CurrencyValue;
import com.echothree.model.data.chain.server.entity.ChainInstance;
import com.echothree.model.data.party.common.pk.PartyDetailPK;
import com.echothree.model.data.party.common.pk.PartyPK;
import com.echothree.model.data.party.common.pk.PartyTypePK;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.model.data.party.server.entity.PartyDetail;
import com.echothree.model.data.party.server.entity.PartyType;
import com.echothree.model.data.party.server.value.PartyDetailValue;
import com.echothree.model.data.party.server.value.PartyTypeValue;
import com.echothree.model.data.party.server.value.PartyValue;
import com.echothree.model.data.term.common.pk.PartyCreditLimitPK;
import com.echothree.model.data.term.server.entity.PartyCreditLimit;
import com.echothree.model.data.term.server.value.PartyCreditLimitValue;
import com.echothree.util.common.command.EditMode;
import com.echothree.util.common.form.ValidationResult;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.message.Messages;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.validation.Validator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.jupiter.params.provider.CsvSource;
import static org.junit.jupiter.api.Assertions.*;

@SuppressWarnings("UnnecessaryAssignment") // Supply CDI dependencies without a container.
public class EditPartyCreditLimitCommandTest {

    private static class TestCommand extends EditPartyCreditLimitCommand {
        private final Currency selectedCurrency = new Currency(new CurrencyValue(new CurrencyPK(1L), "USD", "$",
                new SymbolPositionPK(1L), false, false, false, false, ",", 3, ".", 2, 2, 2, 2, 2, "-", false, 0), EntityPermission.READ_ONLY);
        private final Party selectedParty;
        private final PartyCreditLimit selectedCreditLimit;
        private int currencyLookups;
        private int creditLimitLookups;
        private int updates;
        private int chains;
        private PartyCreditLimitValue savedValue;

        TestCommand(String missingEntity, String partyTypeName) {
            var partyType = new PartyType(new PartyTypeValue(new PartyTypePK(2L), partyTypeName, null, null,
                    false, false, false, 0), EntityPermission.READ_ONLY);
            selectedParty = new Party(new PartyValue(new PartyPK(3L), null, null), EntityPermission.READ_ONLY) {
                @Override
                public PartyDetail getLastDetail() {
                    return new PartyDetail(new PartyDetailValue(new PartyDetailPK(4L), getPrimaryKey(), "PARTY", partyType.getPrimaryKey(),
                            null, null, null, null, 0L, Long.MAX_VALUE), EntityPermission.READ_ONLY) {
                        @Override
                        public PartyType getPartyType() {
                            return partyType;
                        }
                    };
                }
            };
            selectedCreditLimit = new PartyCreditLimit(new PartyCreditLimitValue(new PartyCreditLimitPK(5L), selectedParty.getPrimaryKey(),
                    selectedCurrency.getPrimaryKey(), 1234L, 5678L, 0L, Long.MAX_VALUE), EntityPermission.READ_WRITE) {
                @Override
                public Party getParty() {
                    return selectedParty;
                }
                @Override
                public Currency getCurrency() {
                    return selectedCurrency;
                }
            };
            editMode = EditMode.UPDATE;
            spec = TermSpecFactory.getPartyCreditLimitSpec();
            spec.setPartyName("PARTY");
            spec.setCurrencyIsoName("USD");
            edit = TermEditFactory.getPartyCreditLimitEdit();
            edit.setCreditLimit("1234");
            edit.setPotentialCreditLimit("5678");
            partyLogic = new PartyLogic() {
                @Override
                public Party getPartyByName(ExecutionErrorAccumulator eea, String partyName) {
                    if("party".equals(missingEntity)) {
                        eea.addExecutionError(ExecutionErrors.UnknownPartyName.name(), partyName);
                    }
                    return "party".equals(missingEntity) ? null : selectedParty;
                }
            };
            currencyLogic = new CurrencyLogic() {
                @Override
                public Currency getCurrencyByName(ExecutionErrorAccumulator eea, String currencyIsoName) {
                    currencyLookups++;
                    if("currency".equals(missingEntity)) {
                        eea.addExecutionError(ExecutionErrors.UnknownCurrencyIsoName.name(), currencyIsoName);
                    }
                    return "currency".equals(missingEntity) ? null : selectedCurrency;
                }
            };
            termControl = new TermControl() {
                @Override
                public PartyCreditLimit getPartyCreditLimit(Party party, Currency currency, EntityPermission entityPermission) {
                    assertSame(selectedParty, party);
                    assertSame(selectedCurrency, currency);
                    assertEquals(EntityPermission.READ_WRITE, entityPermission);
                    creditLimitLookups++;
                    return "limit".equals(missingEntity) ? null : selectedCreditLimit;
                }
                @Override
                public com.echothree.model.control.term.common.transfer.PartyCreditLimitTransfer getPartyCreditLimitTransfer(
                        com.echothree.model.data.user.server.entity.UserVisit userVisit, PartyCreditLimit partyCreditLimit) { return null; }

                @Override
                public PartyCreditLimitValue getPartyCreditLimitValue(PartyCreditLimit partyCreditLimit) {
                    return selectedCreditLimit.getPartyCreditLimitValue().clone();
                }
                @Override
                public void updatePartyCreditLimitFromValue(PartyCreditLimitValue value, BasePK updatedBy) {
                    updates++;
                    savedValue = value;
                }
            };
            partyChainLogic = new PartyChainLogic() {
                @Override
                public ChainInstance createPartyCreditLimitChangedChainInstance(ExecutionErrorAccumulator eea, Party party, BasePK createdBy) {
                    assertNull(eea);
                    assertSame(selectedParty, party);
                    chains++;
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

        @Override
        public com.echothree.model.data.user.server.entity.UserVisit getUserVisit() { return null; }
        @Override
        public com.echothree.model.control.core.common.transfer.EntityLockTransfer getEntityLockTransfer(
                com.echothree.util.server.persistence.BaseEntity lockEntity) { return null; }

        void setLimits(String creditLimit, String potentialCreditLimit) {
            edit.setCreditLimit(creditLimit);
            edit.setPotentialCreditLimit(potentialCreditLimit);
        }

        @Override
        public PartyPK getPartyPK() {
            return new PartyPK(99L);
        }
    }

    @ParameterizedTest
    @ValueSource(strings = { "party", "currency", "limit" })
    public void missingEntitiesStopLookupAndReportCompleteKeys(String missingEntity) {
        var command = new TestCommand(missingEntity, "CUSTOMER");
        assertNull(command.getEntity(command.getResult()));
        assertTrue(command.hasExecutionErrors());
        assertEquals("limit".equals(missingEntity) ? 1 : 0, command.creditLimitLookups);
        var error = command.getExecutionErrors().get(Messages.EXECUTION_ERROR).next();
        var names = switch(missingEntity) {
            case "party" -> new Object[] { "PARTY" };
            case "currency" -> new Object[] { "USD" };
            case "limit" -> new Object[] { "PARTY", "USD" };
            default -> throw new IllegalArgumentException(missingEntity);
        };
        assertArrayEquals(names, error.getValues());
    }

    @Test
    public void validationAndEntityLookupReuseCurrencyAndLockParty() {
        var command = new TestCommand(null, "CUSTOMER");
        var validator = new Validator(command);
        command.setupValidatorForEdit(validator, null);

        assertSame(command.selectedCurrency, validator.getCurrency());
        assertSame(command.selectedCreditLimit, command.getEntity(command.getResult()));
        assertSame(command.selectedParty, command.getLockEntity(command.selectedCreditLimit));
        assertEquals(1, command.currencyLookups);
    }

    @Test
    public void lockFormatsBothAmountsInCurrencyUnits() {
        var command = new TestCommand(null, "CUSTOMER");
        var edit = command.getEdit();
        command.doLock(edit, command.selectedCreditLimit);

        assertEquals("12.34", edit.getCreditLimit());
        assertEquals("56.78", edit.getPotentialCreditLimit());
    }

    @ParameterizedTest
    @ValueSource(strings = { "CUSTOMER", "VENDOR" })
    public void changedLimitsUpdateAndOnlyCustomersStartChain(String partyTypeName) {
        var command = new TestCommand(null, partyTypeName);
        command.setLimits("9999", "5678");
        command.doUpdate(command.selectedCreditLimit);

        assertEquals(1, command.updates);
        assertEquals(9999L, command.savedValue.getCreditLimit());
        assertEquals("CUSTOMER".equals(partyTypeName) ? 1 : 0, command.chains);
    }

    @Test
    public void unchangedLimitsDoNotUpdateOrStartChain() {
        var command = new TestCommand(null, "CUSTOMER");
        command.doUpdate(command.selectedCreditLimit);

        assertEquals(0, command.updates);
        assertEquals(0, command.chains);
    }

    @Test
    public void clearingLimitsPreservesNullValuesAndStartsCustomerChain() {
        var command = new TestCommand(null, "CUSTOMER");
        command.setLimits(null, null);
        command.doUpdate(command.selectedCreditLimit);

        assertNull(command.savedValue.getCreditLimit());
        assertNull(command.savedValue.getPotentialCreditLimit());
        assertEquals(1, command.chains);
    }


    @ParameterizedTest
    @CsvSource({"abc,InvalidFormat", "-1.00,MinimumValue", "1.234,ExcessFractionalDigits",
            "92233720368547758.08,MaximumValue", "1a2,InvalidFormat", "1.2.3,InvalidFormat"})
    public void invalidLimitsFailValidationBeforeAnyWriteAndRetainInput(String input, String errorKey) {
        for(var fieldName : new String[]{"CreditLimit", "PotentialCreditLimit"}) {
            var command = new TestCommand(null, "CUSTOMER");
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
        var command = new TestCommand(null, "CUSTOMER");
        command.setLimits(input, "56.78");
        assertFalse(command.submitUpdate().getHasErrors());
        assertEquals(new java.math.BigDecimal(input).movePointRight(2).longValueExact(), command.savedValue.getCreditLimit());
        assertEquals(1, command.updates);
    }

    @Test
    public void validatedBlankAndZeroLimitsRemainDistinct() {
        var command = new TestCommand(null, "CUSTOMER");
        command.setLimits("  ", "0");
        assertFalse(command.submitUpdate().getHasErrors());
        assertNull(command.savedValue.getCreditLimit());
        assertEquals(0L, command.savedValue.getPotentialCreditLimit());
    }

    @Test
    public void unresolvedCommandCurrencyFailsWithoutDefaultCurrencyFallback() {
        var command = new TestCommand("currency", "CUSTOMER");
        command.setLimits("1", "2");
        assertTrue(command.submitUpdate().getHasErrors());
        assertTrue(command.hasExecutionErrors());
        assertEquals(0, command.updates);
        assertEquals("1", command.getField("CreditLimit"));
    }
}
