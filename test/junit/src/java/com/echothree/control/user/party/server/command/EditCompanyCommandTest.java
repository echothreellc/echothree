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

package com.echothree.control.user.party.server.command;

import com.echothree.control.user.party.common.edit.PartyEditFactory;
import com.echothree.control.user.party.common.spec.CompanyUniversalSpec;
import com.echothree.control.user.party.common.spec.PartySpecFactory;
import com.echothree.model.control.accounting.server.logic.CurrencyLogic;
import com.echothree.model.control.core.common.transfer.EntityLockTransfer;
import com.echothree.model.control.party.common.transfer.CompanyTransfer;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.party.server.logic.CompanyLogic;
import com.echothree.model.control.party.server.logic.DateTimeFormatLogic;
import com.echothree.model.control.party.server.logic.LanguageLogic;
import com.echothree.model.control.party.server.logic.TimeZoneLogic;
import com.echothree.model.data.accounting.common.pk.CurrencyPK;
import com.echothree.model.data.accounting.common.pk.SymbolPositionPK;
import com.echothree.model.data.accounting.server.entity.Currency;
import com.echothree.model.data.accounting.server.value.CurrencyValue;
import com.echothree.model.data.party.common.pk.DateTimeFormatDetailPK;
import com.echothree.model.data.party.common.pk.DateTimeFormatPK;
import com.echothree.model.data.party.common.pk.LanguagePK;
import com.echothree.model.data.party.common.pk.PartyCompanyPK;
import com.echothree.model.data.party.common.pk.PartyDetailPK;
import com.echothree.model.data.party.common.pk.PartyGroupPK;
import com.echothree.model.data.party.common.pk.PartyPK;
import com.echothree.model.data.party.common.pk.PartyTypePK;
import com.echothree.model.data.party.common.pk.TimeZoneDetailPK;
import com.echothree.model.data.party.common.pk.TimeZonePK;
import com.echothree.model.data.party.server.entity.DateTimeFormat;
import com.echothree.model.data.party.server.entity.DateTimeFormatDetail;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.model.data.party.server.entity.PartyCompany;
import com.echothree.model.data.party.server.entity.PartyDetail;
import com.echothree.model.data.party.server.entity.PartyGroup;
import com.echothree.model.data.party.server.entity.TimeZone;
import com.echothree.model.data.party.server.entity.TimeZoneDetail;
import com.echothree.model.data.party.server.value.DateTimeFormatDetailValue;
import com.echothree.model.data.party.server.value.DateTimeFormatValue;
import com.echothree.model.data.party.server.value.LanguageValue;
import com.echothree.model.data.party.server.value.PartyCompanyValue;
import com.echothree.model.data.party.server.value.PartyDetailValue;
import com.echothree.model.data.party.server.value.PartyGroupValue;
import com.echothree.model.data.party.server.value.PartyValue;
import com.echothree.model.data.party.server.value.TimeZoneDetailValue;
import com.echothree.model.data.party.server.value.TimeZoneValue;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.common.command.EditMode;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.message.Messages;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.BaseEntity;
import com.echothree.util.server.persistence.EntityPermission;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.ValueSource;
import static org.junit.jupiter.api.Assertions.*;

@SuppressWarnings("UnnecessaryAssignment")
public class EditCompanyCommandTest {

    private static class TestCommand extends EditCompanyCommand {
        private boolean currentPreferences = true;
        private final Language language = new Language(new LanguageValue(new LanguagePK(1L), "en", false, 0), EntityPermission.READ_ONLY);
        private final Currency currency = new Currency(new CurrencyValue(new CurrencyPK(2L), "USD", "$", new SymbolPositionPK(1L),
                false, false, false, false, ",", 3, ".", 2, 2, 2, 2, 2, "-", false, 0), EntityPermission.READ_ONLY);
        private final TimeZone timeZone = new TimeZone(new TimeZoneValue(new TimeZonePK(3L), null, null), EntityPermission.READ_ONLY) {
            @Override
            public TimeZoneDetail getLastDetail() {
                return new TimeZoneDetail(new TimeZoneDetailValue(new TimeZoneDetailPK(4L), getPrimaryKey(), "America/New_York",
                        "America/New_York", false, 0, 0L, Long.MAX_VALUE), EntityPermission.READ_ONLY);
            }
        };
        private final DateTimeFormat dateTimeFormat = new DateTimeFormat(new DateTimeFormatValue(new DateTimeFormatPK(5L), null, null),
                EntityPermission.READ_ONLY) {
            @Override
            public DateTimeFormatDetail getLastDetail() {
                return new DateTimeFormatDetail(new DateTimeFormatDetailValue(new DateTimeFormatDetailPK(6L), getPrimaryKey(), "FORMAT",
                        "x", "x", "x", "x", "x", "x", "x", "x", "x", "x", "x", "x", "x", "x", "x", "x", false, 0, 0L, Long.MAX_VALUE), EntityPermission.READ_ONLY);
            }
        };
        private final Party party = new Party(new PartyValue(new PartyPK(7L), null, null), EntityPermission.READ_ONLY) {
            @Override
            public PartyDetail getLastDetail() {
                return new PartyDetail(detail(), EntityPermission.READ_ONLY) {
                    @Override
                    public Language getPreferredLanguage() { return currentPreferences ? language : null; }
                    @Override
                    public Currency getPreferredCurrency() { return currentPreferences ? currency : null; }
                    @Override
                    public TimeZone getPreferredTimeZone() { return currentPreferences ? timeZone : null; }
                    @Override
                    public DateTimeFormat getPreferredDateTimeFormat() { return currentPreferences ? dateTimeFormat : null; }
                };
            }
        };
        private final PartyCompany company = new PartyCompany(new PartyCompanyValue(new PartyCompanyPK(8L), party.getPrimaryKey(),
                "COMPANY", false, 0, 0L, Long.MAX_VALUE), EntityPermission.READ_WRITE) {
            @Override
            public Party getParty() { return party; }
        };
        private int duplicateMode;
        private int updates;
        private boolean missingCompany;
        private boolean transferred;
        private String missingPreference;
        private PartyDetailValue savedParty;

        private PartyDetailValue detail() {
            return new PartyDetailValue(new PartyDetailPK(9L), party.getPrimaryKey(), "PARTY", new PartyTypePK(1L),
                    language.getPrimaryKey(), currency.getPrimaryKey(), timeZone.getPrimaryKey(), dateTimeFormat.getPrimaryKey(), 0L, Long.MAX_VALUE);
        }

        TestCommand(EditMode mode) {
            editMode = mode;
            spec = PartySpecFactory.getCompanyUniversalSpec();
            spec.setCompanyName("COMPANY");
            edit = PartyEditFactory.getCompanyEdit();
            edit.setCompanyName("NEW");
            edit.setName("New Company");
            edit.setIsDefault("true");
            edit.setSortOrder("10");
            setPreferences(true);
            companyLogic = new CompanyLogic() {
                @Override
                public PartyCompany getPartyCompanyByUniversalSpec(ExecutionErrorAccumulator eea, CompanyUniversalSpec universalSpec, boolean allowDefault, EntityPermission permission) {
                    assertSame(spec, universalSpec);
                    assertFalse(allowDefault);
                    assertEquals(mode == EditMode.UPDATE ? EntityPermission.READ_WRITE : EntityPermission.READ_ONLY, permission);
                    if(missingCompany) {
                        eea.addExecutionError(ExecutionErrors.UnknownCompanyName.name(), "COMPANY");
                    }
                    return missingCompany ? null : company;
                }
            };
            languageLogic = new LanguageLogic() {
                @Override
                public Language getLanguageByName(ExecutionErrorAccumulator eea, String name) {
                    assertEquals("en", name);
                    if("language".equals(missingPreference)) {
                        eea.addExecutionError(ExecutionErrors.UnknownLanguageIsoName.name(), name);
                    }
                    return "language".equals(missingPreference) ? null : language;
                }
            };
            currencyLogic = new CurrencyLogic() {
                @Override
                public Currency getCurrencyByName(ExecutionErrorAccumulator eea, String name) {
                    assertEquals("USD", name);
                    if("currency".equals(missingPreference)) {
                        eea.addExecutionError(ExecutionErrors.UnknownCurrencyIsoName.name(), name);
                    }
                    return "currency".equals(missingPreference) ? null : currency;
                }
            };
            timeZoneLogic = new TimeZoneLogic() {
                @Override
                public TimeZone getTimeZoneByName(ExecutionErrorAccumulator eea, String name) {
                    assertEquals("America/New_York", name);
                    if("timeZone".equals(missingPreference)) {
                        eea.addExecutionError(ExecutionErrors.UnknownJavaTimeZoneName.name(), name);
                    }
                    return "timeZone".equals(missingPreference) ? null : timeZone;
                }
            };
            dateTimeFormatLogic = new DateTimeFormatLogic() {
                @Override
                public DateTimeFormat getDateTimeFormatByName(ExecutionErrorAccumulator eea, String name) {
                    assertEquals("FORMAT", name);
                    if("format".equals(missingPreference)) {
                        eea.addExecutionError(ExecutionErrors.UnknownDateTimeFormatName.name(), name);
                    }
                    return "format".equals(missingPreference) ? null : dateTimeFormat;
                }
            };
            partyControl = new PartyControl() {
                @Override
                public PartyCompany getPartyCompanyByName(String name) {
                    assertEquals("NEW", name);
                    return switch(duplicateMode) {
                        case 0 -> null;
                        case 1 -> company;
                        case 2 -> new PartyCompany(new PartyCompanyValue(new PartyCompanyPK(99L), party.getPrimaryKey(),
                                "NEW", false, 0, 0L, Long.MAX_VALUE), EntityPermission.READ_ONLY);
                        default -> throw new IllegalArgumentException();
                    };
                }

                @Override
                public PartyCompanyValue getPartyCompanyValueForUpdate(PartyCompany selectedCompany) {
                    return selectedCompany.getPartyCompanyValue().clone();
                }

                @Override
                public PartyDetailValue getPartyDetailValueForUpdate(Party selectedParty) {
                    assertSame(party, selectedParty);
                    return detail();
                }

                @Override
                public PartyGroup getPartyGroup(Party selectedParty) {
                    return new PartyGroup(new PartyGroupValue(new PartyGroupPK(10L), party.getPrimaryKey(), "Old Company", 0L, Long.MAX_VALUE),
                            EntityPermission.READ_ONLY);
                }

                @Override
                public PartyGroupValue getPartyGroupValueForUpdate(Party selectedParty) {
                    return getPartyGroup(selectedParty).getPartyGroupValue().clone();
                }

                @Override
                public void updatePartyCompanyFromValue(PartyCompanyValue value, BasePK updatedBy) {
                    assertEquals("NEW", value.getPartyCompanyName());
                    assertTrue(value.getIsDefault());
                    assertEquals(10, value.getSortOrder());
                    updates++;
                }

                @Override
                public void updatePartyFromValue(PartyDetailValue value, BasePK updatedBy) {
                    savedParty = value;
                    assertEquals("PARTY", value.getPartyName());
                    updates++;
                }

                @Override
                public void updatePartyGroupFromValue(PartyGroupValue value, BasePK updatedBy) {
                    assertEquals("New Company", value.getName());
                    updates++;
                }

                @Override
                public CompanyTransfer getCompanyTransfer(UserVisit visit, PartyCompany selectedCompany) {
                    assertSame(company, selectedCompany);
                    transferred = true;
                    return null;
                }
            };
        }

        void setPreferences(boolean include) {
            edit.setPreferredLanguageIsoName(include ? "en" : null);
            edit.setPreferredCurrencyIsoName(include ? "USD" : null);
            edit.setPreferredJavaTimeZoneName(include ? "America/New_York" : null);
            edit.setPreferredDateTimeFormatName(include ? "FORMAT" : null);
        }

        @Override
        public PartyPK getPartyPK() { return new PartyPK(98L); }
        @Override
        public UserVisit getUserVisit() { return null; }
        @Override
        public EntityLockTransfer getEntityLockTransfer(BaseEntity entity) {
            assertSame(party, entity);
            return null;
        }

        void assertValidationErrorResult() {
            edit.setSortOrder("invalid");
            assertTrue(validate().getHasErrors());
            assertNotNull(getBaseResultAfterErrors());
            assertEquals(missingCompany, hasExecutionErrors());
            assertEquals(!missingCompany, transferred);
        }
    }

    @ParameterizedTest
    @EnumSource(EditMode.class)
    public void usesRequestedPermissionAndLocksParty(EditMode mode) {
        var command = new TestCommand(mode);
        assertSame(command.company, command.getEntity(command.getResult()));
        assertSame(command.party, command.getLockEntity(command.company));
    }

    @ParameterizedTest
    @ValueSource(booleans = { false, true })
    public void updatePersistsOrClearsEveryPreference(boolean include) {
        var command = new TestCommand(EditMode.UPDATE);
        command.setPreferences(include);
        command.canUpdate(command.company);
        assertFalse(command.hasExecutionErrors());
        command.doUpdate(command.company);
        assertEquals(3, command.updates);
        assertEquals(include ? command.language.getPrimaryKey() : null, command.savedParty.getPreferredLanguagePK());
        assertEquals(include ? command.currency.getPrimaryKey() : null, command.savedParty.getPreferredCurrencyPK());
        assertEquals(include ? command.timeZone.getPrimaryKey() : null, command.savedParty.getPreferredTimeZonePK());
        assertEquals(include ? command.dateTimeFormat.getPrimaryKey() : null, command.savedParty.getPreferredDateTimeFormatPK());
    }

    @ParameterizedTest
    @ValueSource(booleans = { false, true })
    public void lockCopiesCompanyGroupAndOptionalPreferences(boolean include) {
        var command = new TestCommand(EditMode.LOCK);
        command.currentPreferences = include;
        var edit = command.getEdit();
        command.doLock(edit, command.company);
        assertEquals("COMPANY", edit.getCompanyName());
        assertEquals("Old Company", edit.getName());
        assertEquals("false", edit.getIsDefault());
        assertEquals("0", edit.getSortOrder());
        assertEquals(include ? "en" : null, edit.getPreferredLanguageIsoName());
        assertEquals(include ? "USD" : null, edit.getPreferredCurrencyIsoName());
        assertEquals(include ? "America/New_York" : null, edit.getPreferredJavaTimeZoneName());
        assertEquals(include ? "FORMAT" : null, edit.getPreferredDateTimeFormatName());
    }

    @ParameterizedTest
    @ValueSource(ints = { 0, 1, 2 })
    public void rejectsDuplicateCompanyNameButAllowsSameCompany(int duplicateMode) {
        var command = new TestCommand(EditMode.UPDATE);
        command.duplicateMode = duplicateMode;
        command.canUpdate(command.company);
        assertEquals(duplicateMode == 2, command.hasExecutionErrors());
        if(duplicateMode == 2) {
            var error = command.getExecutionErrors().get(Messages.EXECUTION_ERROR).next();
            assertEquals(ExecutionErrors.DuplicateCompanyName.name(), error.getKey());
            assertArrayEquals(new Object[] { "NEW" }, error.getValues());
        }
        assertEquals(0, command.updates);
    }

    @ParameterizedTest
    @ValueSource(strings = { "language", "currency", "timeZone", "format" })
    public void unknownPreferencePreventsWrites(String preference) {
        var command = new TestCommand(EditMode.UPDATE);
        command.missingPreference = preference;
        command.canUpdate(command.company);
        assertTrue(command.hasExecutionErrors());
        assertEquals(0, command.updates);
    }

    @ParameterizedTest
    @ValueSource(booleans = { false, true })
    public void invalidEditRetainsResultWithoutDereferencingMissingCompany(boolean missingCompany) {
        var command = new TestCommand(EditMode.UPDATE);
        command.missingCompany = missingCompany;
        command.assertValidationErrorResult();
    }

}
