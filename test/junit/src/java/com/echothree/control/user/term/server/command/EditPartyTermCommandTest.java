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
import com.echothree.control.user.term.common.result.EditPartyTermResult;
import com.echothree.control.user.term.common.spec.TermSpecFactory;
import com.echothree.model.control.core.common.transfer.EntityLockTransfer;
import com.echothree.model.control.party.server.logic.PartyChainLogic;
import com.echothree.model.control.party.server.logic.PartyLogic;
import com.echothree.model.control.term.common.transfer.PartyTermTransfer;
import com.echothree.model.control.term.server.control.TermControl;
import com.echothree.model.control.term.server.logic.TermLogic;
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
import com.echothree.model.data.term.common.pk.PartyTermPK;
import com.echothree.model.data.term.common.pk.TermDetailPK;
import com.echothree.model.data.term.common.pk.TermPK;
import com.echothree.model.data.term.common.pk.TermTypePK;
import com.echothree.model.data.term.server.entity.PartyTerm;
import com.echothree.model.data.term.server.entity.Term;
import com.echothree.model.data.term.server.entity.TermDetail;
import com.echothree.model.data.term.server.value.PartyTermValue;
import com.echothree.model.data.term.server.value.TermDetailValue;
import com.echothree.model.data.term.server.value.TermValue;
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
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import static org.junit.jupiter.api.Assertions.*;

@SuppressWarnings("UnnecessaryAssignment")
public class EditPartyTermCommandTest {

    private static class TestCommand extends EditPartyTermCommand {
        private final Party party;
        private final Term currentTerm = term(10L, "CURRENT");
        private final Term replacementTerm = term(11L, "NEW");
        private final PartyTerm partyTerm;
        private int partyTermLookups;
        private int termLookups;
        private int updates;
        private int chains;
        private int locks;
        private int unlocks;
        private int transfers;
        private boolean lockSucceeds = true;
        private boolean chainFails;
        private PartyTermValue savedValue;

        private static Term term(long identifier, String name) {
            return new Term(new TermValue(new TermPK(identifier), null, null), EntityPermission.READ_ONLY) {
                @Override public TermDetail getLastDetail() {
                    return new TermDetail(new TermDetailValue(new TermDetailPK(identifier), getPrimaryKey(), name,
                            new TermTypePK(1L), false, 0, 0L, Long.MAX_VALUE), EntityPermission.READ_ONLY);
                }
            };
        }

        TestCommand(EditMode mode, String missingEntity, String partyTypeName) {
            var partyType = new PartyType(new PartyTypeValue(new PartyTypePK(1L), partyTypeName, null, null,
                    false, false, false, 0), EntityPermission.READ_ONLY);
            party = new Party(new PartyValue(new PartyPK(2L), null, null), EntityPermission.READ_ONLY) {
                @Override public PartyDetail getLastDetail() {
                    return new PartyDetail(new PartyDetailValue(new PartyDetailPK(3L), getPrimaryKey(), "PARTY", partyType.getPrimaryKey(),
                            null, null, null, null, 0L, Long.MAX_VALUE), EntityPermission.READ_ONLY) {
                        @Override public PartyType getPartyType() { return partyType; }
                    };
                }
            };
            partyTerm = new PartyTerm(new PartyTermValue(new PartyTermPK(4L), party.getPrimaryKey(), currentTerm.getPrimaryKey(),
                    "VENDOR".equals(partyTypeName) ? null : false, 0L, Long.MAX_VALUE), EntityPermission.READ_WRITE) {
                @Override public Party getParty() { return party; }
                @Override public Term getTerm() { return currentTerm; }
            };
            editMode = mode;
            spec = TermSpecFactory.getPartyTermSpec();
            spec.setPartyName("PARTY");
            edit = TermEditFactory.getPartyTermEdit();
            edit.setTermName("CURRENT");
            edit.setTaxable("VENDOR".equals(partyTypeName) ? null : "false");
            partyLogic = new PartyLogic() {
                @Override public Party getPartyByName(ExecutionErrorAccumulator eea, String name) {
                    assertEquals("PARTY", name);
                    if("party".equals(missingEntity)) {
                        eea.addExecutionError(ExecutionErrors.UnknownPartyName.name(), name);
                    }
                    return "party".equals(missingEntity) ? null : party;
                }
            };
            termLogic = new TermLogic() {
                @Override public PartyTerm getPartyTerm(ExecutionErrorAccumulator eea, Party selectedParty, EntityPermission permission) {
                    assertSame(party, selectedParty);
                    assertEquals(mode == EditMode.UPDATE ? EntityPermission.READ_WRITE : EntityPermission.READ_ONLY, permission);
                    partyTermLookups++;
                    if("partyTerm".equals(missingEntity)) {
                        eea.addExecutionError(ExecutionErrors.UnknownPartyTerm.name(), "PARTY");
                    }
                    return "partyTerm".equals(missingEntity) ? null : partyTerm;
                }
                @Override public Term getTermByName(ExecutionErrorAccumulator eea, String name) {
                    termLookups++;
                    if("MISSING".equals(name)) {
                        eea.addExecutionError(ExecutionErrors.UnknownTermName.name(), name);
                    }
                    return switch(name) {
                        case "CURRENT" -> currentTerm;
                        case "NEW" -> replacementTerm;
                        case "MISSING" -> null;
                        default -> throw new AssertionError(name);
                    };
                }
            };
            termControl = new TermControl() {
                @Override public PartyTermValue getPartyTermValue(PartyTerm selectedPartyTerm) {
                    assertSame(partyTerm, selectedPartyTerm);
                    return selectedPartyTerm.getPartyTermValue().clone();
                }
                @Override public void updatePartyTermFromValue(PartyTermValue value, BasePK updatedBy) {
                    assertEquals(new PartyPK(99L), updatedBy);
                    savedValue = value;
                    updates++;
                }
                @Override public PartyTermTransfer getPartyTermTransfer(UserVisit visit, PartyTerm selectedPartyTerm) {
                    assertSame(partyTerm, selectedPartyTerm);
                    transfers++;
                    return null;
                }
            };
            partyChainLogic = new PartyChainLogic() {
                @Override public ChainInstance createPartyTermChangedChainInstance(ExecutionErrorAccumulator eea, Party selectedParty, BasePK createdBy) {
                    assertNull(eea);
                    assertSame(party, selectedParty);
                    assertEquals(new PartyPK(99L), createdBy);
                    chains++;
                    if(chainFails) {
                        throw new IllegalStateException("Chain failure");
                    }
                    return null;
                }
            };
        }

        EditPartyTermResult executeEdit() { return (EditPartyTermResult)execute(); }
        void changeEdit(String termName, String taxable) {
            edit.setTermName(termName);
            edit.setTaxable(taxable);
        }
        @Override public PartyPK getPartyPK() { return new PartyPK(99L); }
        @Override public UserVisit getUserVisit() { return null; }
        @Override public boolean lockEntity(BaseEntity target) {
            assertSame(party, target);
            locks++;
            return lockSucceeds;
        }
        @Override public boolean lockEntityForUpdate(BaseEntity target) {
            assertSame(party, target);
            locks++;
            return lockSucceeds;
        }
        @Override public boolean unlockEntity(BaseEntity target) {
            assertSame(party, target);
            unlocks++;
            return true;
        }
        @Override public EntityLockTransfer getEntityLockTransfer(BaseEntity target) {
            assertSame(party, target);
            return null;
        }
        ValidationResult validateEditRequest() { return validate(); }
        String currentEditTaxable() { return edit.getTaxable(); }
        boolean hasValidationErrorResult() { return getBaseResultAfterErrors() != null; }
        void verifyValidationErrorResult() {
            edit.setTaxable("invalid");
            assertTrue(validate().getHasErrors());
            assertNotNull(getBaseResultAfterErrors());
        }
    }

    private static void assertError(TestCommand command, ExecutionErrors expected, Object... names) {
        var error = command.getExecutionErrors().get(Messages.EXECUTION_ERROR).next();
        assertEquals(expected.name(), error.getKey());
        assertArrayEquals(names, error.getValues());
    }

    @ParameterizedTest
    @EnumSource(EditMode.class)
    public void resolvesPartyTermWithModePermissionAndLocksParty(EditMode mode) {
        var command = new TestCommand(mode, null, "CUSTOMER");
        assertSame(command.partyTerm, command.getEntity(command.getResult()));
        assertSame(command.party, command.getLockEntity(command.partyTerm));
        assertEquals(1, command.partyTermLookups);
        assertFalse(command.hasExecutionErrors());
    }

    @ParameterizedTest
    @ValueSource(strings = { "party", "partyTerm" })
    public void missingEntityStopsDependentWorkAndReportsPartyName(String missingEntity) {
        for(var mode : EditMode.values()) {
            var command = new TestCommand(mode, missingEntity, "CUSTOMER");
            assertNotNull(command.executeEdit());
            assertError(command, "party".equals(missingEntity) ? ExecutionErrors.UnknownPartyName : ExecutionErrors.UnknownPartyTerm, "PARTY");
            assertEquals("party".equals(missingEntity) ? 0 : 1, command.partyTermLookups);
            assertEquals(0, command.termLookups);
            assertEquals(0, command.locks);
            assertEquals(0, command.unlocks);
            assertEquals(0, command.updates);
        }
    }

    @ParameterizedTest
    @ValueSource(booleans = { true, false })
    public void lockPopulatesCurrentValuesOrReportsFailure(boolean lockSucceeds) {
        var command = new TestCommand(EditMode.LOCK, null, "CUSTOMER");
        command.lockSucceeds = lockSucceeds;
        var result = command.executeEdit();
        assertEquals(1, command.locks);
        assertEquals(1, command.transfers);
        assertEquals(0, command.termLookups);
        if(lockSucceeds) {
            assertEquals("CURRENT", result.getEdit().getTermName());
            assertEquals("false", result.getEdit().getTaxable());
            assertFalse(command.hasExecutionErrors());
        } else {
            assertNull(result.getEdit());
            assertError(command, ExecutionErrors.EntityLockFailed);
        }
    }

    @Test
    public void abandonUnlocksPartyWithoutUpdatingOrResolvingEditedTerm() {
        var command = new TestCommand(EditMode.ABANDON, null, "CUSTOMER");
        assertNotNull(command.executeEdit());
        assertEquals(1, command.unlocks);
        assertEquals(0, command.locks);
        assertEquals(0, command.termLookups);
        assertEquals(0, command.updates);
        assertEquals(0, command.transfers);
    }

    @ParameterizedTest
    @ValueSource(ints = { 0, 1, 2, 3 })
    public void customerChangesPersistAndOnlyChangedValuesStartChain(int changes) {
        var command = new TestCommand(EditMode.UPDATE, null, "CUSTOMER");
        command.changeEdit((changes & 1) == 0 ? "CURRENT" : "NEW", (changes & 2) == 0 ? "false" : "true");
        assertFalse(command.validateEditRequest().getHasErrors());
        command.executeEdit();
        assertFalse(command.hasExecutionErrors());
        assertEquals(changes == 0 ? 0 : 1, command.updates);
        assertEquals(changes == 0 ? 0 : 1, command.chains);
        assertEquals(1, command.termLookups);
        assertEquals(1, command.locks);
        assertEquals(1, command.unlocks);
        if(changes != 0) {
            assertEquals(command.party.getPrimaryKey(), command.savedValue.getPartyPK());
            assertEquals(command.partyTerm.getPrimaryKey(), command.savedValue.getPrimaryKey());
            assertEquals((changes & 1) == 0 ? command.currentTerm.getPrimaryKey() : command.replacementTerm.getPrimaryKey(), command.savedValue.getTermPK());
            assertEquals((changes & 2) != 0, command.savedValue.getTaxable());
        }
    }

    @ParameterizedTest
    @ValueSource(booleans = { false, true })
    public void vendorChangesKeepTaxableNullAndNeverStartCustomerChain(boolean changeTerm) {
        var command = new TestCommand(EditMode.UPDATE, null, "VENDOR");
        command.changeEdit(changeTerm ? "NEW" : "CURRENT", null);
        assertFalse(command.validateEditRequest().getHasErrors());
        command.executeEdit();
        assertFalse(command.hasExecutionErrors());
        assertEquals(changeTerm ? 1 : 0, command.updates);
        assertEquals(0, command.chains);
        assertEquals(1, command.unlocks);
        if(changeTerm) {
            assertNull(command.savedValue.getTaxable());
            assertEquals(command.replacementTerm.getPrimaryKey(), command.savedValue.getTermPK());
            assertEquals(command.party.getPrimaryKey(), command.savedValue.getPartyPK());
        }
    }

    @Test
    public void lockingVendorReturnsNullTaxable() {
        var command = new TestCommand(EditMode.LOCK, null, "VENDOR");
        var result = command.executeEdit();
        assertFalse(command.hasExecutionErrors());
        assertEquals("CURRENT", result.getEdit().getTermName());
        assertNull(result.getEdit().getTaxable());
        assertEquals(1, command.locks);
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = { "", " ", "true", "false", "invalid" })
    public void customerRequiresValidTaxableValue(String taxable) {
        var command = new TestCommand(EditMode.UPDATE, null, "CUSTOMER");
        command.changeEdit("NEW", taxable);
        var result = command.validateEditRequest();
        var valid = "true".equals(taxable) || "false".equals(taxable);
        assertEquals(!valid, result.getHasErrors());
        if(!valid) {
            var error = result.getValidationMessages().get("Taxable").next();
            assertEquals("invalid".equals(taxable) ? Validator.ERROR_INVALID_FORMAT : Validator.ERROR_REQUIRED_FIELD, error.getKey());
            assertTrue(command.hasValidationErrorResult());
        }
        assertEquals(0, command.updates);
        assertEquals(0, command.locks);
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = { "", " ", "true", "false", "invalid" })
    public void vendorRejectsPopulatedTaxableValue(String taxable) {
        var command = new TestCommand(EditMode.UPDATE, null, "VENDOR");
        command.changeEdit("NEW", taxable);
        var result = command.validateEditRequest();
        var populated = taxable != null && !taxable.isBlank();
        assertEquals(populated, result.getHasErrors());
        if(populated) {
            assertEquals(Validator.ERROR_NO_VALUE_ALLOWED, result.getValidationMessages().get("Taxable").next().getKey());
            assertTrue(command.hasValidationErrorResult());
        } else {
            assertNull(command.currentEditTaxable());
        }
        assertEquals(0, command.updates);
        assertEquals(0, command.locks);
    }

    @ParameterizedTest
    @ValueSource(strings = { "EMPLOYEE", "COMPANY", "DIVISION", "DEPARTMENT", "WAREHOUSE" })
    public void rejectsUnsupportedPartyTypesBeforePartyTermLookup(String partyTypeName) {
        for(var mode : EditMode.values()) {
            var command = new TestCommand(mode, null, partyTypeName);
            command.executeEdit();
            assertError(command, ExecutionErrors.InvalidPartyType, partyTypeName);
            assertEquals(0, command.partyTermLookups);
            assertEquals(0, command.locks);
            assertEquals(0, command.unlocks);
            assertEquals(0, command.updates);
        }
    }

    @Test
    public void unknownTermReturnsExistingDataWithoutTakingUpdateLock() {
        var command = new TestCommand(EditMode.UPDATE, null, "CUSTOMER");
        command.changeEdit("MISSING", "true");
        command.executeEdit();
        assertError(command, ExecutionErrors.UnknownTermName, "MISSING");
        assertEquals(1, command.transfers);
        assertEquals(0, command.locks);
        assertEquals(0, command.updates);
        assertEquals(0, command.chains);
    }

    @Test
    public void staleLockReturnsExistingDataWithoutWriting() {
        var command = new TestCommand(EditMode.UPDATE, null, "CUSTOMER");
        command.lockSucceeds = false;
        command.changeEdit("NEW", "true");
        command.executeEdit();
        assertError(command, ExecutionErrors.EntityLockStale);
        assertEquals(1, command.transfers);
        assertEquals(1, command.locks);
        assertEquals(0, command.unlocks);
        assertEquals(0, command.updates);
        assertEquals(0, command.chains);
    }

    @Test
    public void chainFailurePropagatesAndUnlocksParty() {
        var command = new TestCommand(EditMode.UPDATE, null, "CUSTOMER");
        command.chainFails = true;
        command.changeEdit("NEW", "true");
        assertThrows(IllegalStateException.class, command::executeEdit);
        assertEquals(1, command.updates);
        assertEquals(1, command.chains);
        assertEquals(1, command.unlocks);
    }

    @ParameterizedTest
    @ValueSource(strings = { "present", "party", "partyTerm" })
    public void invalidEditKeepsResultWithoutDereferencingMissingEntity(String missingEntity) {
        var command = new TestCommand(EditMode.UPDATE, missingEntity, "CUSTOMER");
        command.verifyValidationErrorResult();
        assertEquals("present".equals(missingEntity) ? 1 : 0, command.transfers);
        assertEquals(0, command.updates);
    }

}
