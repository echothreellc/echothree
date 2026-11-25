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

package com.echothree.control.user.letter.server.command;

import com.echothree.control.user.letter.common.edit.LetterEditFactory;
import com.echothree.control.user.letter.common.spec.LetterSourceUniversalSpec;
import com.echothree.control.user.letter.common.spec.LetterSpecFactory;
import com.echothree.model.control.contact.common.ContactMechanismTypes;
import com.echothree.model.control.core.common.transfer.EntityLockTransfer;
import com.echothree.model.control.letter.common.transfer.LetterSourceTransfer;
import com.echothree.model.control.letter.server.control.LetterControl;
import com.echothree.model.control.letter.server.logic.LetterSourceLogic;
import com.echothree.model.data.contact.common.pk.ContactMechanismDetailPK;
import com.echothree.model.data.contact.common.pk.ContactMechanismPK;
import com.echothree.model.data.contact.common.pk.ContactMechanismTypePK;
import com.echothree.model.data.contact.common.pk.PartyContactMechanismDetailPK;
import com.echothree.model.data.contact.common.pk.PartyContactMechanismPK;
import com.echothree.model.data.contact.server.entity.ContactMechanism;
import com.echothree.model.data.contact.server.entity.ContactMechanismDetail;
import com.echothree.model.data.contact.server.entity.PartyContactMechanism;
import com.echothree.model.data.contact.server.entity.PartyContactMechanismDetail;
import com.echothree.model.data.contact.server.value.ContactMechanismDetailValue;
import com.echothree.model.data.contact.server.value.ContactMechanismValue;
import com.echothree.model.data.contact.server.value.PartyContactMechanismDetailValue;
import com.echothree.model.data.contact.server.value.PartyContactMechanismValue;
import com.echothree.model.data.letter.common.pk.LetterSourceDescriptionPK;
import com.echothree.model.data.letter.common.pk.LetterSourceDetailPK;
import com.echothree.model.data.letter.common.pk.LetterSourcePK;
import com.echothree.model.data.letter.server.entity.LetterSource;
import com.echothree.model.data.letter.server.entity.LetterSourceDescription;
import com.echothree.model.data.letter.server.entity.LetterSourceDetail;
import com.echothree.model.data.letter.server.value.LetterSourceDescriptionValue;
import com.echothree.model.data.letter.server.value.LetterSourceDetailValue;
import com.echothree.model.data.letter.server.value.LetterSourceValue;
import com.echothree.model.data.party.common.pk.LanguagePK;
import com.echothree.model.data.party.common.pk.PartyPK;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.model.data.party.server.value.LanguageValue;
import com.echothree.model.data.party.server.value.PartyValue;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.common.command.EditMode;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.BaseEntity;
import com.echothree.util.server.persistence.EntityPermission;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.ValueSource;
import static org.junit.jupiter.api.Assertions.*;

@SuppressWarnings("UnnecessaryAssignment") // Supply CDI dependencies without a container.
public class EditLetterSourceCommandTest {

    private static class TestCommand extends EditLetterSourceCommand {
        private final Party companyParty = new Party(new PartyValue(new PartyPK(1L), null, null), EntityPermission.READ_ONLY);
        private final Language language = new Language(new LanguageValue(new LanguagePK(2L), "en", false, 0), EntityPermission.READ_ONLY);
        private final PartyContactMechanism email = contact(3L, "EMAIL");
        private final PartyContactMechanism postal = contact(4L, "POSTAL");
        private final PartyContactMechanism web = contact(5L, "WEB");
        private final LetterSource letterSource = new LetterSource(new LetterSourceValue(new LetterSourcePK(6L), null, null), EntityPermission.READ_WRITE) {
            @Override
            public LetterSourceDetail getLastDetail() {
                return new LetterSourceDetail(detail(), EntityPermission.READ_ONLY) {
                    @Override
                    public Party getCompanyParty() { return companyParty; }
                    @Override
                    public PartyContactMechanism getEmailAddressPartyContactMechanism() { return email; }
                    @Override
                    public PartyContactMechanism getPostalAddressPartyContactMechanism() { return postal; }
                    @Override
                    public PartyContactMechanism getLetterSourcePartyContactMechanism() { return web; }
                };
            }
        };
        private boolean missingSource;
        private int duplicateMode;
        private int contactLookups;
        private boolean updated;
        private boolean transferred;
        private boolean existingDescription;
        private String descriptionOperation;

        private LetterSourceDetailValue detail() {
            return new LetterSourceDetailValue(new LetterSourceDetailPK(7L), letterSource.getPrimaryKey(), "SOURCE", companyParty.getPrimaryKey(),
                    email.getPrimaryKey(), postal.getPrimaryKey(), web.getPrimaryKey(), false, 0, 0L, Long.MAX_VALUE);
        }

        private PartyContactMechanism contact(long id, String name) {
            var contactMechanism = new ContactMechanism(new ContactMechanismValue(new ContactMechanismPK(id), null, null), EntityPermission.READ_ONLY) {
                @Override
                public ContactMechanismDetail getLastDetail() {
                    return new ContactMechanismDetail(new ContactMechanismDetailValue(new ContactMechanismDetailPK(id), getPrimaryKey(),
                            name, new ContactMechanismTypePK(1L), false, 0L, Long.MAX_VALUE), EntityPermission.READ_ONLY);
                }
            };
            return new PartyContactMechanism(new PartyContactMechanismValue(new PartyContactMechanismPK(id), null, null), EntityPermission.READ_ONLY) {
                @Override
                public PartyContactMechanismDetail getLastDetail() {
                    return new PartyContactMechanismDetail(new PartyContactMechanismDetailValue(new PartyContactMechanismDetailPK(id),
                            getPrimaryKey(), companyParty.getPrimaryKey(), contactMechanism.getPrimaryKey(), null, false, 0, 0L, Long.MAX_VALUE),
                            EntityPermission.READ_ONLY) {
                        @Override
                        public ContactMechanism getContactMechanism() { return contactMechanism; }
                    };
                }
            };
        }

        private LetterSourceDescription description() {
            return existingDescription ? new LetterSourceDescription(new LetterSourceDescriptionValue(new LetterSourceDescriptionPK(8L),
                    letterSource.getPrimaryKey(), language.getPrimaryKey(), "old", 0L, Long.MAX_VALUE), EntityPermission.READ_WRITE) : null;
        }

        TestCommand(EditMode requestedEditMode) {
            editMode = requestedEditMode;
            spec = LetterSpecFactory.getLetterSourceUniversalSpec();
            spec.setLetterSourceName("SOURCE");
            edit = LetterEditFactory.getLetterSourceEdit();
            edit.setLetterSourceName("NEW");
            edit.setEmailAddressContactMechanismName("EMAIL");
            edit.setPostalAddressContactMechanismName("POSTAL");
            edit.setLetterSourceContactMechanismName("WEB");
            edit.setIsDefault("true");
            edit.setSortOrder("10");
            letterSourceLogic = new LetterSourceLogic() {
                @Override
                public LetterSource getLetterSourceByUniversalSpec(ExecutionErrorAccumulator eea, LetterSourceUniversalSpec universalSpec,
                        boolean allowDefault, EntityPermission entityPermission) {
                    assertSame(spec, universalSpec);
                    assertFalse(allowDefault);
                    assertEquals(requestedEditMode == EditMode.UPDATE ? EntityPermission.READ_WRITE : EntityPermission.READ_ONLY, entityPermission);
                    if(missingSource) {
                        eea.addExecutionError(ExecutionErrors.UnknownLetterSourceName.name(), "SOURCE");
                    }
                    return missingSource ? null : letterSource;
                }

                @Override
                public PartyContactMechanism getLetterSourcePartyContactMechanism(ExecutionErrorAccumulator eea, Party party,
                        String contactMechanismName, String aliasType, String alias, ContactMechanismTypes expectedType) {
                    contactLookups++;
                    assertSame(companyParty, party);
                    assertNull(aliasType);
                    assertNull(alias);
                    return switch(expectedType) {
                        case EMAIL_ADDRESS -> { assertEquals("EMAIL", contactMechanismName); yield email; }
                        case POSTAL_ADDRESS -> { assertEquals("POSTAL", contactMechanismName); yield postal; }
                        case WEB_ADDRESS -> { assertEquals("WEB", contactMechanismName); yield web; }
                        default -> throw new IllegalArgumentException();
                    };
                }
            };
            letterControl = new LetterControl() {
                @Override
                public LetterSource getLetterSourceByName(String letterSourceName) {
                    assertEquals("NEW", letterSourceName);
                    return switch(duplicateMode) {
                        case 0 -> null;
                        case 1 -> letterSource;
                        case 2 -> new LetterSource(new LetterSourceValue(new LetterSourcePK(99L), null, null), EntityPermission.READ_ONLY);
                        default -> throw new IllegalArgumentException();
                    };
                }

                @Override
                public LetterSourceDetailValue getLetterSourceDetailValueForUpdate(LetterSource selectedSource) {
                    assertSame(letterSource, selectedSource);
                    return detail();
                }

                @Override
                public void updateLetterSourceFromValue(LetterSourceDetailValue value, BasePK updatedBy) {
                    assertEquals("NEW", value.getLetterSourceName());
                    assertEquals(companyParty.getPrimaryKey(), value.getCompanyPartyPK());
                    assertEquals(email.getPrimaryKey(), value.getEmailAddressPartyContactMechanismPK());
                    assertEquals(postal.getPrimaryKey(), value.getPostalAddressPartyContactMechanismPK());
                    assertEquals(web.getPrimaryKey(), value.getLetterSourcePartyContactMechanismPK());
                    assertTrue(value.getIsDefault());
                    assertEquals(10, value.getSortOrder());
                    updated = true;
                }

                @Override
                public LetterSourceDescription getLetterSourceDescription(LetterSource source, Language selectedLanguage) {
                    return description();
                }

                @Override
                public LetterSourceDescription getLetterSourceDescriptionForUpdate(LetterSource source, Language selectedLanguage) {
                    return description();
                }

                @Override
                public LetterSourceDescription createLetterSourceDescription(LetterSource source, Language selectedLanguage, String description, BasePK createdBy) {
                    assertEquals("new", description);
                    descriptionOperation = "create";
                    return null;
                }

                @Override
                public void deleteLetterSourceDescription(LetterSourceDescription description, BasePK deletedBy) {
                    descriptionOperation = "delete";
                }

                @Override
                public void updateLetterSourceDescriptionFromValue(LetterSourceDescriptionValue value, BasePK updatedBy) {
                    assertEquals("new", value.getDescription());
                    descriptionOperation = "update";
                }

                @Override
                public LetterSourceTransfer getLetterSourceTransfer(UserVisit userVisit, LetterSource source) {
                    assertSame(letterSource, source);
                    transferred = true;
                    return null;
                }
            };
        }

        @Override
        public PartyPK getPartyPK() { return null; }
        @Override
        public Language getPreferredLanguage() { return language; }
        @Override
        public UserVisit getUserVisit() { return null; }
        @Override
        public EntityLockTransfer getEntityLockTransfer(BaseEntity entity) {
            assertSame(letterSource, entity);
            return null;
        }

        void setDescription(String description) { edit.setDescription(description); }

        void assertInvalidEditResult() {
            edit.setSortOrder("invalid");
            assertTrue(validate().getHasErrors());
            assertNotNull(getBaseResultAfterErrors());
            assertEquals(missingSource, hasExecutionErrors());
            assertEquals(!missingSource, transferred);
        }
    }

    @ParameterizedTest
    @EnumSource(EditMode.class)
    public void resolvesWithRequestedPermissionWithoutDefaultAndLocksSource(EditMode mode) {
        var command = new TestCommand(mode);
        assertSame(command.letterSource, command.getEntity(command.getResult()));
        assertSame(command.letterSource, command.getLockEntity(command.letterSource));
    }

    @ParameterizedTest
    @ValueSource(ints = { 0, 1, 2 })
    public void duplicateRenameIsRejectedAndSameEntityAllowed(int duplicateMode) {
        var command = new TestCommand(EditMode.UPDATE);
        command.duplicateMode = duplicateMode;
        command.canUpdate(command.letterSource);
        assertEquals(duplicateMode == 2, command.hasExecutionErrors());
        assertEquals(duplicateMode == 2 ? 0 : 3, command.contactLookups);
        assertFalse(command.updated);
    }

    @ParameterizedTest
    @ValueSource(ints = { 0, 1, 2, 3 })
    public void updateCopiesFieldsAndCreatesUpdatesOrDeletesDescription(int descriptionState) {
        var command = new TestCommand(EditMode.UPDATE);
        command.existingDescription = (descriptionState & 1) != 0;
        command.setDescription((descriptionState & 2) != 0 ? "new" : null);
        command.canUpdate(command.letterSource);
        assertFalse(command.hasExecutionErrors());
        command.doUpdate(command.letterSource);
        assertTrue(command.updated);
        var expected = switch(descriptionState) {
            case 0 -> null;
            case 1 -> "delete";
            case 2 -> "create";
            case 3 -> "update";
            default -> throw new IllegalArgumentException();
        };
        assertEquals(expected, command.descriptionOperation);
    }

    @Test
    public void lockCopiesSourceFieldsContactsAndDescription() {
        var command = new TestCommand(EditMode.LOCK);
        command.existingDescription = true;
        var edit = command.getEdit();
        command.doLock(edit, command.letterSource);
        assertEquals("SOURCE", edit.getLetterSourceName());
        assertEquals("EMAIL", edit.getEmailAddressContactMechanismName());
        assertEquals("POSTAL", edit.getPostalAddressContactMechanismName());
        assertEquals("WEB", edit.getLetterSourceContactMechanismName());
        assertEquals("false", edit.getIsDefault());
        assertEquals("0", edit.getSortOrder());
        assertEquals("old", edit.getDescription());
    }

    @ParameterizedTest
    @ValueSource(booleans = { false, true })
    public void invalidEditRetainsResultWithoutDereferencingMissingSource(boolean missingSource) {
        var command = new TestCommand(EditMode.UPDATE);
        command.missingSource = missingSource;
        command.assertInvalidEditResult();
    }

}
