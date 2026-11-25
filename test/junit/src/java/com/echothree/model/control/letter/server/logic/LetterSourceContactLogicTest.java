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

package com.echothree.model.control.letter.server.logic;

import com.echothree.model.control.contact.common.ContactMechanismTypes;
import com.echothree.model.control.contact.server.control.ContactControl;
import com.echothree.model.control.contact.server.logic.ContactMechanismAliasTypeLogic;
import com.echothree.model.control.contact.server.logic.ContactMechanismLogic;
import com.echothree.model.control.contact.server.logic.PartyContactMechanismLogic;
import com.echothree.model.data.contact.common.pk.ContactMechanismAliasPK;
import com.echothree.model.data.contact.common.pk.ContactMechanismAliasTypePK;
import com.echothree.model.data.contact.common.pk.ContactMechanismDetailPK;
import com.echothree.model.data.contact.common.pk.ContactMechanismPK;
import com.echothree.model.data.contact.common.pk.ContactMechanismTypePK;
import com.echothree.model.data.contact.common.pk.PartyContactMechanismPK;
import com.echothree.model.data.contact.server.entity.ContactMechanism;
import com.echothree.model.data.contact.server.entity.ContactMechanismAlias;
import com.echothree.model.data.contact.server.entity.ContactMechanismAliasType;
import com.echothree.model.data.contact.server.entity.ContactMechanismDetail;
import com.echothree.model.data.contact.server.entity.ContactMechanismType;
import com.echothree.model.data.contact.server.entity.PartyContactMechanism;
import com.echothree.model.data.contact.server.value.ContactMechanismAliasTypeValue;
import com.echothree.model.data.contact.server.value.ContactMechanismAliasValue;
import com.echothree.model.data.contact.server.value.ContactMechanismDetailValue;
import com.echothree.model.data.contact.server.value.ContactMechanismTypeValue;
import com.echothree.model.data.contact.server.value.ContactMechanismValue;
import com.echothree.model.data.contact.server.value.PartyContactMechanismValue;
import com.echothree.model.data.party.common.pk.PartyPK;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.model.data.party.server.value.PartyValue;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.message.Messages;
import com.echothree.util.server.message.DummyExecutionErrorAccumulator;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.EntityPermission;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import static org.junit.jupiter.api.Assertions.*;

@SuppressWarnings("UnnecessaryAssignment") // Supply CDI dependencies without a container.
public class LetterSourceContactLogicTest {

    private static class TestLogic extends LetterSourceLogic {
        private final Party companyParty = new Party(new PartyValue(new PartyPK(1L), null, null), EntityPermission.READ_ONLY);
        private final ContactMechanismAliasType aliasType = new ContactMechanismAliasType(
                new ContactMechanismAliasTypeValue(new ContactMechanismAliasTypePK(2L), null, null), EntityPermission.READ_ONLY);
        private final PartyContactMechanism partyContactMechanism = new PartyContactMechanism(
                new PartyContactMechanismValue(new PartyContactMechanismPK(3L), null, null), EntityPermission.READ_ONLY);
        private int partyLookups;

        TestLogic(String typeName, String missing) {
            var contactMechanismType = new ContactMechanismType(new ContactMechanismTypeValue(
                    new ContactMechanismTypePK(4L), typeName, null, false, 0), EntityPermission.READ_ONLY);
            var contactMechanism = new ContactMechanism(new ContactMechanismValue(new ContactMechanismPK(5L), null, null), EntityPermission.READ_ONLY) {
                @Override
                public ContactMechanismDetail getLastDetail() {
                    return new ContactMechanismDetail(new ContactMechanismDetailValue(new ContactMechanismDetailPK(6L), getPrimaryKey(),
                            "CONTACT", contactMechanismType.getPrimaryKey(), false, 0L, Long.MAX_VALUE), EntityPermission.READ_ONLY) {
                        @Override
                        public ContactMechanismType getContactMechanismType() {
                            return contactMechanismType;
                        }
                    };
                }
            };
            contactMechanismLogic = new ContactMechanismLogic() {
                @Override
                public ContactMechanism getContactMechanismByName(ExecutionErrorAccumulator eea, String contactMechanismName) {
                    assertEquals("CONTACT", contactMechanismName);
                    if("name".equals(missing)) {
                        eea.addExecutionError(ExecutionErrors.UnknownContactMechanismName.name(), contactMechanismName);
                    }
                    return "name".equals(missing) ? null : contactMechanism;
                }
            };
            contactMechanismAliasTypeLogic = new ContactMechanismAliasTypeLogic() {
                @Override
                public ContactMechanismAliasType getContactMechanismAliasTypeByName(ExecutionErrorAccumulator eea, String contactMechanismAliasTypeName) {
                    assertEquals("ALIAS_TYPE", contactMechanismAliasTypeName);
                    if("aliasType".equals(missing)) {
                        eea.addExecutionError(ExecutionErrors.UnknownContactMechanismAliasTypeName.name(), contactMechanismAliasTypeName);
                    }
                    return "aliasType".equals(missing) ? null : aliasType;
                }
            };
            contactControl = new ContactControl() {
                @Override
                public ContactMechanismAlias getContactMechanismAliasByAlias(ContactMechanismAliasType selectedAliasType, String alias) {
                    assertSame(aliasType, selectedAliasType);
                    assertEquals("ALIAS", alias);
                    return "alias".equals(missing) ? null : new ContactMechanismAlias(new ContactMechanismAliasValue(
                            new ContactMechanismAliasPK(7L), contactMechanism.getPrimaryKey(), aliasType.getPrimaryKey(), alias, 0L, Long.MAX_VALUE),
                            EntityPermission.READ_ONLY) {
                        @Override
                        public ContactMechanism getContactMechanism() {
                            return contactMechanism;
                        }
                    };
                }
            };
            partyContactMechanismLogic = new PartyContactMechanismLogic() {
                @Override
                public PartyContactMechanism getPartyContactMechanism(ExecutionErrorAccumulator eea, Party party, ContactMechanism selectedContactMechanism) {
                    partyLookups++;
                    assertSame(companyParty, party);
                    assertSame(contactMechanism, selectedContactMechanism);
                    if("party".equals(missing)) {
                        eea.addExecutionError(ExecutionErrors.UnknownPartyContactMechanism.name(), "COMPANY", "CONTACT");
                    }
                    return "party".equals(missing) ? null : partyContactMechanism;
                }
            };
        }
    }

    @ParameterizedTest
    @ValueSource(strings = { "EMAIL_ADDRESS", "POSTAL_ADDRESS", "WEB_ADDRESS" })
    public void acceptsNamesAndAliasesForRequiredTypeAndCompany(String typeName) {
        var logic = new TestLogic(typeName, null);
        for(boolean useAlias : new boolean[] { false, true }) {
            var errors = new DummyExecutionErrorAccumulator();
            var result = logic.getLetterSourcePartyContactMechanism(errors, logic.companyParty,
                    useAlias ? null : "CONTACT", useAlias ? "ALIAS_TYPE" : null, useAlias ? "ALIAS" : null,
                    ContactMechanismTypes.valueOf(typeName));
            assertSame(logic.partyContactMechanism, result);
            assertFalse(errors.hasExecutionErrors());
        }
        assertEquals(2, logic.partyLookups);
    }

    @ParameterizedTest
    @ValueSource(strings = { "name", "aliasType", "alias", "party", "wrongType" })
    public void invalidContactStopsBeforeDependentWorkAndReportsKey(String failure) {
        var logic = new TestLogic("wrongType".equals(failure) ? "TELECOM_ADDRESS" : "EMAIL_ADDRESS", failure);
        var errors = new DummyExecutionErrorAccumulator();
        var useAlias = "aliasType".equals(failure) || "alias".equals(failure);
        assertNull(logic.getLetterSourcePartyContactMechanism(errors, logic.companyParty,
                useAlias ? null : "CONTACT", useAlias ? "ALIAS_TYPE" : null, useAlias ? "ALIAS" : null, ContactMechanismTypes.EMAIL_ADDRESS));
        var error = errors.getExecutionErrors().get(Messages.EXECUTION_ERROR).next();
        var expectedError = switch(failure) {
            case "name" -> ExecutionErrors.UnknownContactMechanismName;
            case "aliasType" -> ExecutionErrors.UnknownContactMechanismAliasTypeName;
            case "alias" -> ExecutionErrors.UnknownContactMechanismAlias;
            case "party" -> ExecutionErrors.UnknownPartyContactMechanism;
            case "wrongType" -> ExecutionErrors.InvalidContactMechanismType;
            default -> throw new IllegalArgumentException(failure);
        };
        assertEquals(expectedError.name(), error.getKey());
        if("alias".equals(failure)) {
            assertArrayEquals(new Object[] { "ALIAS_TYPE", "ALIAS" }, error.getValues());
        }
        assertEquals("party".equals(failure) ? 1 : 0, logic.partyLookups);
    }

    @ParameterizedTest
    @ValueSource(ints = { 0, 2, 3, 4, 5, 7 })
    public void rejectsMissingPartialAndAmbiguousContactIdentifiers(int mask) {
        var logic = new TestLogic("EMAIL_ADDRESS", null);
        var errors = new DummyExecutionErrorAccumulator();
        assertNull(logic.getLetterSourcePartyContactMechanism(errors, logic.companyParty,
                (mask & 1) == 0 ? null : "CONTACT", (mask & 2) == 0 ? null : "ALIAS_TYPE", (mask & 4) == 0 ? null : "ALIAS",
                ContactMechanismTypes.EMAIL_ADDRESS));
        assertEquals(ExecutionErrors.InvalidParameterCount.name(), errors.getExecutionErrors().get(Messages.EXECUTION_ERROR).next().getKey());
        assertEquals(0, logic.partyLookups);
    }

}
