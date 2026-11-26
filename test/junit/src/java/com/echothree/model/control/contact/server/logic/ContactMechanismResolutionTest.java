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

package com.echothree.model.control.contact.server.logic;

import com.echothree.model.control.contact.common.ContactMechanismTypes;
import com.echothree.model.control.contact.server.control.ContactControl;
import com.echothree.model.control.letter.common.exception.UnknownEmailAddressContactMechanismAliasException;
import com.echothree.model.control.letter.common.exception.UnknownEmailAddressContactMechanismAliasTypeNameException;
import com.echothree.model.control.letter.common.exception.UnknownEmailAddressContactMechanismNameException;
import com.echothree.model.control.letter.common.exception.UnknownEmailAddressPartyContactMechanismException;
import com.echothree.model.data.contact.common.pk.ContactMechanismAliasPK;
import com.echothree.model.data.contact.common.pk.ContactMechanismAliasTypeDetailPK;
import com.echothree.model.data.contact.common.pk.ContactMechanismAliasTypePK;
import com.echothree.model.data.contact.common.pk.ContactMechanismDetailPK;
import com.echothree.model.data.contact.common.pk.ContactMechanismPK;
import com.echothree.model.data.contact.common.pk.ContactMechanismTypePK;
import com.echothree.model.data.contact.common.pk.PartyContactMechanismPK;
import com.echothree.model.data.contact.server.entity.ContactMechanism;
import com.echothree.model.data.contact.server.entity.ContactMechanismAlias;
import com.echothree.model.data.contact.server.entity.ContactMechanismAliasType;
import com.echothree.model.data.contact.server.entity.ContactMechanismAliasTypeDetail;
import com.echothree.model.data.contact.server.entity.ContactMechanismDetail;
import com.echothree.model.data.contact.server.entity.ContactMechanismType;
import com.echothree.model.data.contact.server.entity.PartyContactMechanism;
import com.echothree.model.data.contact.server.value.ContactMechanismAliasTypeDetailValue;
import com.echothree.model.data.contact.server.value.ContactMechanismAliasTypeValue;
import com.echothree.model.data.contact.server.value.ContactMechanismAliasValue;
import com.echothree.model.data.contact.server.value.ContactMechanismDetailValue;
import com.echothree.model.data.contact.server.value.ContactMechanismTypeValue;
import com.echothree.model.data.contact.server.value.ContactMechanismValue;
import com.echothree.model.data.contact.server.value.PartyContactMechanismValue;
import com.echothree.model.data.party.common.pk.PartyDetailPK;
import com.echothree.model.data.party.common.pk.PartyPK;
import com.echothree.model.data.party.common.pk.PartyTypePK;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.model.data.party.server.entity.PartyDetail;
import com.echothree.model.data.party.server.value.PartyDetailValue;
import com.echothree.model.data.party.server.value.PartyValue;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.message.Messages;
import com.echothree.util.server.message.DummyExecutionErrorAccumulator;
import com.echothree.util.server.persistence.EntityPermission;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import static org.junit.jupiter.api.Assertions.*;

@SuppressWarnings("UnnecessaryAssignment")
public class ContactMechanismResolutionTest {

    private static class Fixture {
        private String missing;
        private EntityPermission expectedPermission = EntityPermission.READ_ONLY;
        private int aliasLookups;
        private final ContactMechanismLogic contactLogic = new ContactMechanismLogic();
        private final ContactMechanismAliasTypeLogic aliasTypeLogic = new ContactMechanismAliasTypeLogic();
        private final ContactMechanismAliasLogic aliasLogic = new ContactMechanismAliasLogic();
        private final PartyContactMechanismLogic partyLogic = new PartyContactMechanismLogic();
        private final ContactMechanismAliasType aliasType = new ContactMechanismAliasType(new ContactMechanismAliasTypeValue(
                new ContactMechanismAliasTypePK(1L), null, null), EntityPermission.READ_ONLY) {
            @Override
            public ContactMechanismAliasTypeDetail getLastDetail() {
                return new ContactMechanismAliasTypeDetail(new ContactMechanismAliasTypeDetailValue(new ContactMechanismAliasTypeDetailPK(2L),
                        getPrimaryKey(), "ALIAS_TYPE", null, false, 0, 0L, Long.MAX_VALUE), EntityPermission.READ_ONLY);
            }
        };
        private final Party party = new Party(new PartyValue(new PartyPK(3L), null, null), EntityPermission.READ_ONLY) {
            @Override
            public PartyDetail getLastDetail() {
                return new PartyDetail(new PartyDetailValue(new PartyDetailPK(4L), getPrimaryKey(), "COMPANY", new PartyTypePK(1L),
                        null, null, null, null, 0L, Long.MAX_VALUE), EntityPermission.READ_ONLY);
            }
        };
        private final PartyContactMechanism relationship = new PartyContactMechanism(new PartyContactMechanismValue(
                new PartyContactMechanismPK(5L), null, null), EntityPermission.READ_ONLY);
        private final ContactMechanism contact;

        Fixture(String typeName) {
            var type = new ContactMechanismType(new ContactMechanismTypeValue(new ContactMechanismTypePK(6L), typeName, null, false, 0),
                    EntityPermission.READ_ONLY);
            contact = new ContactMechanism(new ContactMechanismValue(new ContactMechanismPK(7L), null, null), EntityPermission.READ_ONLY) {
                @Override
                public ContactMechanismDetail getLastDetail() {
                    return new ContactMechanismDetail(new ContactMechanismDetailValue(new ContactMechanismDetailPK(8L),
                            getPrimaryKey(), "CONTACT", type.getPrimaryKey(), false, 0L, Long.MAX_VALUE), EntityPermission.READ_ONLY) {
                        @Override
                        public ContactMechanismType getContactMechanismType() { return type; }
                    };
                }
            };
            var control = new ContactControl() {
                @Override
                public ContactMechanism getContactMechanismByName(String name, EntityPermission permission) {
                    assertEquals("CONTACT", name);
                    assertEquals(expectedPermission, permission);
                    return "name".equals(missing) ? null : contact;
                }

                @Override
                public ContactMechanismAliasType getContactMechanismAliasTypeByName(String name, EntityPermission permission) {
                    assertEquals("ALIAS_TYPE", name);
                    assertEquals(expectedPermission, permission);
                    return "aliasType".equals(missing) ? null : aliasType;
                }

                @Override
                public ContactMechanismAlias getContactMechanismAliasByAlias(ContactMechanismAliasType selectedType, String alias, EntityPermission permission) {
                    aliasLookups++;
                    assertSame(aliasType, selectedType);
                    assertEquals("ALIAS", alias);
                    assertEquals(expectedPermission, permission);
                    return "alias".equals(missing) ? null : new ContactMechanismAlias(new ContactMechanismAliasValue(new ContactMechanismAliasPK(9L),
                            contact.getPrimaryKey(), aliasType.getPrimaryKey(), alias, 0L, Long.MAX_VALUE), EntityPermission.READ_ONLY) {
                        @Override
                        public ContactMechanism getContactMechanism() { return contact; }
                    };
                }

                @Override
                public PartyContactMechanism getPartyContactMechanism(Party selectedParty, ContactMechanism selectedContact, EntityPermission permission) {
                    assertSame(party, selectedParty);
                    assertSame(contact, selectedContact);
                    assertEquals(expectedPermission, permission);
                    return "party".equals(missing) ? null : relationship;
                }
            };
            contactLogic.contactControl = control;
            contactLogic.contactMechanismAliasLogic = aliasLogic;
            aliasTypeLogic.contactControl = control;
            aliasLogic.contactControl = control;
            aliasLogic.contactMechanismAliasTypeLogic = aliasTypeLogic;
            partyLogic.contactControl = control;
        }
    }

    @ParameterizedTest
    @ValueSource(strings = { "name", "aliasType", "alias", "party" })
    public void lowLevelLookupsSupportCustomErrorsAndRetainGenericDefaults(String stage) {
        var fixture = new Fixture("EMAIL_ADDRESS");
        fixture.missing = stage;
        for(boolean custom : new boolean[] { false, true }) {
            var errors = new DummyExecutionErrorAccumulator();
            switch(stage) {
                case "name" -> {
                    assertNull(custom ? fixture.contactLogic.getContactMechanismByName(UnknownEmailAddressContactMechanismNameException.class,
                            ExecutionErrors.UnknownEmailAddressContactMechanismName, errors, "CONTACT")
                            : fixture.contactLogic.getContactMechanismByName(errors, "CONTACT"));
                }
                case "aliasType" -> {
                    assertNull(custom ? fixture.aliasTypeLogic.getContactMechanismAliasTypeByName(UnknownEmailAddressContactMechanismAliasTypeNameException.class,
                            ExecutionErrors.UnknownEmailAddressContactMechanismAliasTypeName, errors, "ALIAS_TYPE")
                            : fixture.aliasTypeLogic.getContactMechanismAliasTypeByName(errors, "ALIAS_TYPE"));
                }
                case "alias" -> {
                    assertNull(custom ? fixture.aliasLogic.getContactMechanismAliasByAlias(UnknownEmailAddressContactMechanismAliasTypeNameException.class,
                            ExecutionErrors.UnknownEmailAddressContactMechanismAliasTypeName, UnknownEmailAddressContactMechanismAliasException.class,
                            ExecutionErrors.UnknownEmailAddressContactMechanismAlias, errors, "ALIAS_TYPE", "ALIAS", EntityPermission.READ_ONLY)
                            : fixture.aliasLogic.getContactMechanismAliasByAlias(errors, "ALIAS_TYPE", "ALIAS"));
                }
                case "party" -> {
                    assertNull(custom ? fixture.partyLogic.getPartyContactMechanism(UnknownEmailAddressPartyContactMechanismException.class,
                            ExecutionErrors.UnknownEmailAddressPartyContactMechanism, errors, fixture.party, fixture.contact)
                            : fixture.partyLogic.getPartyContactMechanism(errors, fixture.party, fixture.contact));
                }
                default -> throw new IllegalArgumentException(stage);
            }
            var suffix = switch(stage) {
                case "name" -> "ContactMechanismName";
                case "aliasType" -> "ContactMechanismAliasTypeName";
                case "alias" -> "ContactMechanismAlias";
                case "party" -> "PartyContactMechanism";
                default -> throw new IllegalArgumentException(stage);
            };
            var key = switch(stage) {
                case "name" -> new Object[] { "CONTACT" };
                case "aliasType" -> new Object[] { "ALIAS_TYPE" };
                case "alias" -> new Object[] { "ALIAS_TYPE", "ALIAS" };
                case "party" -> new Object[] { "COMPANY", "CONTACT" };
                default -> throw new IllegalArgumentException(stage);
            };
            var error = errors.getExecutionErrors().get(Messages.EXECUTION_ERROR).next();
            assertEquals("Unknown" + (custom ? "EmailAddress" : "") + suffix, error.getKey());
            assertArrayEquals(key, error.getValues());
        }
    }

    @ParameterizedTest
    @ValueSource(strings = { "EMAIL_ADDRESS", "POSTAL_ADDRESS", "WEB_ADDRESS" })
    public void resolvesNamesAndAliasesAndChecksType(String typeName) {
        var fixture = new Fixture(typeName);
        for(boolean useAlias : new boolean[] { false, true }) {
            var errors = new DummyExecutionErrorAccumulator();
            var result = fixture.contactLogic.getContactMechanismByNameOrAlias(errors, useAlias ? null : "CONTACT",
                    useAlias ? "ALIAS_TYPE" : null, useAlias ? "ALIAS" : null, ContactMechanismTypes.valueOf(typeName));
            assertSame(fixture.contact, result);
            assertFalse(errors.hasExecutionErrors());
        }
    }

    @ParameterizedTest
    @ValueSource(ints = { 0, 2, 3, 4, 5, 7 })
    public void rejectsInvalidNameAliasCombinations(int mask) {
        var fixture = new Fixture("EMAIL_ADDRESS");
        var errors = new DummyExecutionErrorAccumulator();
        assertNull(fixture.contactLogic.getContactMechanismByNameOrAlias(errors,
                (mask & 1) == 0 ? null : "CONTACT", (mask & 2) == 0 ? null : "ALIAS_TYPE", (mask & 4) == 0 ? null : "ALIAS",
                ContactMechanismTypes.EMAIL_ADDRESS));
        assertEquals(ExecutionErrors.InvalidParameterCount.name(), errors.getExecutionErrors().get(Messages.EXECUTION_ERROR).next().getKey());
        assertEquals(0, fixture.aliasLookups);
    }

    @ParameterizedTest
    @ValueSource(strings = { "name", "aliasType", "alias", "wrongType" })
    public void combinedLookupPropagatesSpecificErrorAtEachStage(String stage) {
        var fixture = new Fixture("wrongType".equals(stage) ? "TELECOM_ADDRESS" : "EMAIL_ADDRESS");
        fixture.missing = stage;
        var errors = new DummyExecutionErrorAccumulator();
        fixture.contactLogic.getContactMechanismByNameOrAlias(UnknownEmailAddressContactMechanismNameException.class,
                ExecutionErrors.UnknownEmailAddressContactMechanismName, UnknownEmailAddressContactMechanismAliasTypeNameException.class,
                ExecutionErrors.UnknownEmailAddressContactMechanismAliasTypeName, UnknownEmailAddressContactMechanismAliasException.class,
                ExecutionErrors.UnknownEmailAddressContactMechanismAlias, errors, "name".equals(stage) || "wrongType".equals(stage) ? "CONTACT" : null,
                "aliasType".equals(stage) || "alias".equals(stage) ? "ALIAS_TYPE" : null,
                "aliasType".equals(stage) || "alias".equals(stage) ? "ALIAS" : null, ContactMechanismTypes.EMAIL_ADDRESS);
        var expectedError = switch(stage) {
            case "name" -> ExecutionErrors.UnknownEmailAddressContactMechanismName;
            case "aliasType" -> ExecutionErrors.UnknownEmailAddressContactMechanismAliasTypeName;
            case "alias" -> ExecutionErrors.UnknownEmailAddressContactMechanismAlias;
            case "wrongType" -> ExecutionErrors.InvalidContactMechanismType;
            default -> throw new IllegalArgumentException(stage);
        };
        assertEquals(expectedError.name(), errors.getExecutionErrors().get(Messages.EXECUTION_ERROR).next().getKey());
        assertEquals("alias".equals(stage) ? 1 : 0, fixture.aliasLookups);
    }

    @ParameterizedTest
    @ValueSource(strings = { "name", "aliasType", "alias", "party" })
    public void permissionOverloadsRetainRequestedPermission(String stage) {
        var fixture = new Fixture("EMAIL_ADDRESS");
        fixture.expectedPermission = EntityPermission.READ_WRITE;
        var errors = new DummyExecutionErrorAccumulator();
        switch(stage) {
            case "name" -> assertSame(fixture.contact, fixture.contactLogic.getContactMechanismByNameForUpdate(
                    UnknownEmailAddressContactMechanismNameException.class, ExecutionErrors.UnknownEmailAddressContactMechanismName, errors, "CONTACT"));
            case "aliasType" -> assertSame(fixture.aliasType, fixture.aliasTypeLogic.getContactMechanismAliasTypeByNameForUpdate(
                    UnknownEmailAddressContactMechanismAliasTypeNameException.class, ExecutionErrors.UnknownEmailAddressContactMechanismAliasTypeName, errors, "ALIAS_TYPE"));
            case "alias" -> {
                assertNotNull(fixture.aliasLogic.getContactMechanismAliasByAlias(errors, fixture.aliasType, "ALIAS", EntityPermission.READ_WRITE));
            }
            case "party" -> assertSame(fixture.relationship, fixture.partyLogic.getPartyContactMechanism(
                    UnknownEmailAddressPartyContactMechanismException.class, ExecutionErrors.UnknownEmailAddressPartyContactMechanism,
                    errors, fixture.party, fixture.contact, EntityPermission.READ_WRITE));
            default -> throw new IllegalArgumentException(stage);
        }
        assertFalse(errors.hasExecutionErrors());
    }

}
