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
import com.echothree.model.control.contact.server.logic.ContactMechanismLogic;
import com.echothree.model.control.contact.server.logic.PartyContactMechanismLogic;
import com.echothree.model.data.contact.common.pk.ContactMechanismPK;
import com.echothree.model.data.contact.common.pk.PartyContactMechanismPK;
import com.echothree.model.data.contact.server.entity.ContactMechanism;
import com.echothree.model.data.contact.server.entity.PartyContactMechanism;
import com.echothree.model.data.contact.server.value.ContactMechanismValue;
import com.echothree.model.data.contact.server.value.PartyContactMechanismValue;
import com.echothree.model.data.party.common.pk.PartyPK;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.model.data.party.server.value.PartyValue;
import com.echothree.util.common.exception.BaseException;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.message.Messages;
import com.echothree.util.server.message.DummyExecutionErrorAccumulator;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.EntityPermission;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import static org.junit.jupiter.api.Assertions.*;

@SuppressWarnings("UnnecessaryAssignment")
public class LetterSourceContactLogicTest {

    private static class TestLogic extends LetterSourceLogic {
        private final Party companyParty = new Party(new PartyValue(new PartyPK(1L), null, null), EntityPermission.READ_ONLY);
        private final ContactMechanism contact = new ContactMechanism(new ContactMechanismValue(new ContactMechanismPK(2L), null, null), EntityPermission.READ_ONLY);
        private final PartyContactMechanism relationship = new PartyContactMechanism(
                new PartyContactMechanismValue(new PartyContactMechanismPK(3L), null, null), EntityPermission.READ_ONLY);
        private int partyLookups;

        private void assertErrorPair(Class<? extends BaseException> exception, ExecutionErrors error, String role, String suffix) {
            assertEquals("Unknown" + role + suffix, error.name());
            assertEquals("com.echothree.model.control.letter.common.exception." + error.name() + "Exception", exception.getName());
        }

        TestLogic(String role, String failure) {
            contactMechanismLogic = new ContactMechanismLogic() {
                @Override
                public ContactMechanism getContactMechanismByNameOrAlias(Class<? extends BaseException> nameException, ExecutionErrors nameError,
                        Class<? extends BaseException> aliasTypeException, ExecutionErrors aliasTypeError,
                        Class<? extends BaseException> aliasException, ExecutionErrors aliasError, ExecutionErrorAccumulator eea,
                        String name, String aliasTypeName, String alias, ContactMechanismTypes expectedType) {
                    assertErrorPair(nameException, nameError, role, "ContactMechanismName");
                    assertErrorPair(aliasTypeException, aliasTypeError, role, "ContactMechanismAliasTypeName");
                    assertErrorPair(aliasException, aliasError, role, "ContactMechanismAlias");
                    switch(failure) {
                        case "name" -> handleExecutionError(nameException, eea, nameError.name(), "CONTACT");
                        case "aliasType" -> handleExecutionError(aliasTypeException, eea, aliasTypeError.name(), "ALIAS_TYPE");
                        case "alias" -> handleExecutionError(aliasException, eea, aliasError.name(), "ALIAS_TYPE", "ALIAS");
                        default -> { }
                    }
                    return contact;
                }
            };
            partyContactMechanismLogic = new PartyContactMechanismLogic() {
                @Override
                public PartyContactMechanism getPartyContactMechanism(Class<? extends BaseException> exception, ExecutionErrors error,
                        ExecutionErrorAccumulator eea, Party party, ContactMechanism selectedContact) {
                    partyLookups++;
                    assertErrorPair(exception, error, role, "PartyContactMechanism");
                    assertSame(companyParty, party);
                    assertSame(contact, selectedContact);
                    if("party".equals(failure)) {
                        handleExecutionError(exception, eea, error.name(), "COMPANY", "CONTACT");
                    }
                    return relationship;
                }
            };
        }
    }

    @ParameterizedTest
    @CsvSource({
        "EMAIL_ADDRESS,EmailAddress", "POSTAL_ADDRESS,PostalAddress", "WEB_ADDRESS,LetterSource"
    })
    public void usesRoleSpecificErrorsAtEveryLookupStage(String typeName, String role) {
        for(var failure : new String[] { "name", "aliasType", "alias", "party", "none" }) {
            var logic = new TestLogic(role, failure);
            var errors = new DummyExecutionErrorAccumulator();
            var result = logic.getLetterSourcePartyContactMechanism(errors, logic.companyParty,
                    "name".equals(failure) ? "CONTACT" : null, "name".equals(failure) ? null : "ALIAS_TYPE",
                    "name".equals(failure) ? null : "ALIAS", ContactMechanismTypes.valueOf(typeName));
            if("none".equals(failure)) {
                assertSame(logic.relationship, result);
                assertFalse(errors.hasExecutionErrors());
            } else {
                assertTrue(errors.hasExecutionErrors());
                var error = errors.getExecutionErrors().get(Messages.EXECUTION_ERROR).next();
                var suffix = switch(failure) {
                    case "name" -> "ContactMechanismName";
                    case "aliasType" -> "ContactMechanismAliasTypeName";
                    case "alias" -> "ContactMechanismAlias";
                    case "party" -> "PartyContactMechanism";
                    default -> throw new IllegalArgumentException();
                };
                assertEquals("Unknown" + role + suffix, error.getKey());
                if("alias".equals(failure)) {
                    assertArrayEquals(new Object[] { "ALIAS_TYPE", "ALIAS" }, error.getValues());
                }
                if("party".equals(failure)) {
                    assertArrayEquals(new Object[] { "COMPANY", "CONTACT" }, error.getValues());
                }
            }
            assertEquals("party".equals(failure) || "none".equals(failure) ? 1 : 0, logic.partyLookups);
        }
    }

}
