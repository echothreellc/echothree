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

package com.echothree.control.user.vendor.server.command;

import com.echothree.model.control.accounting.server.control.AccountingControl;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.vendor.server.control.VendorControl;
import com.echothree.model.data.accounting.common.pk.CurrencyPK;
import com.echothree.model.data.accounting.common.pk.SymbolPositionPK;
import com.echothree.model.data.accounting.server.entity.Currency;
import com.echothree.model.data.accounting.server.value.CurrencyValue;
import com.echothree.model.data.party.common.pk.PartyPK;
import com.echothree.model.data.party.common.pk.PartyTypePK;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.model.data.party.server.entity.PartyDetail;
import com.echothree.model.data.party.server.entity.PartyGroup;
import com.echothree.model.data.party.server.entity.Person;
import com.echothree.model.data.party.server.value.PartyDetailValue;
import com.echothree.model.data.party.server.value.PartyValue;
import com.echothree.model.data.vendor.common.pk.VendorTypePK;
import com.echothree.model.data.vendor.server.entity.Vendor;
import com.echothree.model.data.vendor.server.entity.VendorType;
import com.echothree.model.data.vendor.server.entity.VendorTypeDetail;
import com.echothree.model.data.vendor.server.value.VendorTypeDetailValue;
import com.echothree.model.data.vendor.server.value.VendorTypeValue;
import com.echothree.model.data.vendor.server.value.VendorValue;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.validation.Validator;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class EditVendorCommandTest {

    @SuppressWarnings("UnnecessaryAssignment") // Supply CDI dependencies without a container.
    private static class TestCommand extends EditVendorCommand {
        private final Currency defaultCurrency = currency(1L, "USD", ".", 2);
        private final Currency alternateCurrency = currency(2L, "EUR", ",", 3);
        private final PartyPK vendorPartyPK = new PartyPK(1L);
        private final VendorTypePK vendorTypePK = new VendorTypePK(1L);
        private VendorValue vendorValue = new VendorValue(vendorPartyPK, "VENDOR", vendorTypePK, null, null, 1234L, 5678L, false,
                null, null, null, null, false, false, false, false, false, false, null, null, null, 0L, Long.MAX_VALUE);
        private PartyDetailValue partyDetailValue = new PartyDetailValue(vendorPartyPK, "PARTY", new PartyTypePK(1L), null, null, null, null,
                0L, Long.MAX_VALUE);
        private final Party vendorParty = new Party(new PartyValue(vendorPartyPK, null, null), EntityPermission.READ_ONLY) {
            @Override
            public PartyDetail getLastDetail() {
                return new PartyDetail(partyDetailValue, EntityPermission.READ_ONLY) {
                    @Override
                    public Currency getPreferredCurrency() {
                        return alternateCurrency.getPrimaryKey().equals(getPreferredCurrencyPK()) ? alternateCurrency : null;
                    }
                };
            }
        };
        private final VendorType vendorType = new VendorType(new VendorTypeValue(vendorTypePK, null, null), EntityPermission.READ_ONLY) {
            @Override
            public VendorTypeDetail getLastDetail() {
                var vendorTypeDetailValue = new VendorTypeDetailValue(vendorTypePK, "DEFAULT", null, null, null, null, null,
                        false, false, false, false, false, false, null, true, 0, 0L, Long.MAX_VALUE);
                return new VendorTypeDetail(vendorTypeDetailValue, EntityPermission.READ_ONLY);
            }
        };

        TestCommand() {
            accountingControl = new AccountingControl() {
                @Override
                public Currency getDefaultCurrency() {
                    return defaultCurrency;
                }

                @Override
                public Currency getCurrencyByIsoName(String currencyIsoName) {
                    return "EUR".equals(currencyIsoName) ? alternateCurrency : null;
                }
            };
            partyControl = new PartyControl() {
                @Override
                public PartyDetailValue getPartyDetailValueForUpdate(Party party) {
                    return partyDetailValue.clone();
                }

                @Override
                public void updatePartyFromValue(PartyDetailValue value, BasePK updatedBy) {
                    partyDetailValue = value;
                }

                @Override
                public PartyGroup getPartyGroup(Party party) { return null; }

                @Override
                public PartyGroup getPartyGroupForUpdate(Party party) { return null; }

                @Override
                public Person getPerson(Party party) { return null; }

                @Override
                public Person getPersonForUpdate(Party party) { return null; }
            };
            vendorControl = new VendorControl() {
                @Override
                public Vendor getVendor(Party party) {
                    return new Vendor(vendorValue, EntityPermission.READ_ONLY) {
                        @Override
                        public VendorType getVendorType() {
                            return vendorType;
                        }
                    };
                }

                @Override
                public Vendor getVendorForUpdate(Party party) {
                    return getVendor(party);
                }

                @Override
                public Vendor getVendorByName(String vendorName) { return null; }

                @Override
                public VendorType getVendorTypeByName(String vendorTypeName) {
                    return vendorType;
                }

                @Override
                public void updateVendorFromValue(VendorValue value, BasePK updatedBy) {
                    vendorValue = value;
                }
            };
        }

        private Currency currency(long id, String currencyIsoName, String separator, int costLineDigits) {
            return new Currency(new CurrencyValue(new CurrencyPK(id), currencyIsoName, null, new SymbolPositionPK(1L),
                    false, false, false, false, ",", 3, separator, 2, 4, 2, 4, costLineDigits, "-", false, 0),
                    EntityPermission.READ_ONLY);
        }

        @Override
        public Currency getPreferredCurrency(Party party) {
            var preference = party.getLastDetail().getPreferredCurrency();
            return preference == null ? defaultCurrency : preference;
        }

        @Override
        public PartyPK getPartyPK() {
            return vendorPartyPK;
        }

        void lock() {
            edit = getEdit();
            doLock(edit, vendorParty);
        }

        void changeCurrency(String currencyIsoName, String minimum, String maximum) {
            edit.setPreferredCurrencyIsoName(currencyIsoName);
            edit.setMinimumPurchaseOrderAmount(minimum);
            edit.setMaximumPurchaseOrderAmount(maximum);

            var validator = new Validator(this) {
                @Override
                public AccountingControl getAccountingControl() {
                    return accountingControl;
                }
            };
            setupValidatorForEdit(validator, null);
            assertFalse(validateEdit(validator).getHasErrors());
            canUpdate(vendorParty);
            assertFalse(hasExecutionErrors());
            doUpdate(vendorParty);
        }

        void assertLockedLimits(String currencyIsoName, String minimum, String maximum) {
            assertEquals(currencyIsoName, edit.getPreferredCurrencyIsoName());
            assertEquals(minimum, edit.getMinimumPurchaseOrderAmount());
            assertEquals(maximum, edit.getMaximumPurchaseOrderAmount());
        }
    }

    @Test
    void purchaseOrderLimitsUseTheUpdatedEffectiveCurrency() {
        var command = new TestCommand();
        command.lock();
        command.assertLockedLimits(null, "12.34", "56.78");

        command.changeCurrency("EUR", "12,345", "67,890");
        assertEquals(new CurrencyPK(2L), command.partyDetailValue.getPreferredCurrencyPK());
        assertEquals(12345L, command.vendorValue.getMinimumPurchaseOrderAmount());
        assertEquals(67890L, command.vendorValue.getMaximumPurchaseOrderAmount());
        command.lock();
        command.assertLockedLimits("EUR", "12,345", "67,890");

        command.changeCurrency(null, "12.34", "67.89");
        assertNull(command.partyDetailValue.getPreferredCurrencyPK());
        assertEquals(1234L, command.vendorValue.getMinimumPurchaseOrderAmount());
        assertEquals(6789L, command.vendorValue.getMaximumPurchaseOrderAmount());
        command.lock();
        command.assertLockedLimits(null, "12.34", "67.89");

        command.changeCurrency("EUR", "", null);
        assertNull(command.vendorValue.getMinimumPurchaseOrderAmount());
        assertNull(command.vendorValue.getMaximumPurchaseOrderAmount());
        command.lock();
        command.assertLockedLimits("EUR", null, null);
    }

}
