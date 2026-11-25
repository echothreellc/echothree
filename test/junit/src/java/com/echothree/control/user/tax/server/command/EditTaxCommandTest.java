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

package com.echothree.control.user.tax.server.command;

import com.echothree.control.user.tax.common.edit.TaxEditFactory;
import com.echothree.model.control.accounting.server.logic.GlAccountLogic;
import com.echothree.model.control.contact.common.ContactMechanismTypes;
import com.echothree.model.control.contact.server.logic.ContactMechanismPurposeLogic;
import com.echothree.model.control.tax.server.control.TaxControl;
import com.echothree.model.data.accounting.common.pk.GlAccountPK;
import com.echothree.model.data.accounting.server.entity.GlAccount;
import com.echothree.model.data.accounting.server.value.GlAccountValue;
import com.echothree.model.data.contact.common.pk.ContactMechanismPurposePK;
import com.echothree.model.data.contact.common.pk.ContactMechanismTypePK;
import com.echothree.model.data.contact.server.entity.ContactMechanismPurpose;
import com.echothree.model.data.contact.server.entity.ContactMechanismType;
import com.echothree.model.data.contact.server.value.ContactMechanismPurposeValue;
import com.echothree.model.data.contact.server.value.ContactMechanismTypeValue;
import com.echothree.model.data.tax.common.pk.TaxPK;
import com.echothree.model.data.tax.server.entity.Tax;
import com.echothree.model.data.tax.server.value.TaxValue;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.message.Messages;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.EntityPermission;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import static org.junit.jupiter.api.Assertions.*;

@SuppressWarnings("UnnecessaryAssignment") // Supply CDI dependencies without a container.
public class EditTaxCommandTest {

    private static class TestCommand extends EditTaxCommand {
        private final Tax selectedTax = new Tax(new TaxValue(new TaxPK(1L), null, null), EntityPermission.READ_WRITE);

        TestCommand(String contactMechanismTypeName) {
            edit = TaxEditFactory.getTaxEdit();
            edit.setTaxName("TAX");
            edit.setContactMechanismPurposeName("PURPOSE");
            edit.setGlAccountName("ACCOUNT");
            taxControl = new TaxControl() {
                @Override
                public Tax getTaxByName(String taxName) {
                    return selectedTax;
                }
            };
            contactMechanismPurposeLogic = new ContactMechanismPurposeLogic() {
                @Override
                public ContactMechanismPurpose getContactMechanismPurposeByName(ExecutionErrorAccumulator eea, String purposeName) {
                    assertEquals("PURPOSE", purposeName);
                    var contactMechanismType = new ContactMechanismType(new ContactMechanismTypeValue(
                            new ContactMechanismTypePK(2L), contactMechanismTypeName, null, false, 0), EntityPermission.READ_ONLY);
                    return new ContactMechanismPurpose(new ContactMechanismPurposeValue(new ContactMechanismPurposePK(3L),
                            purposeName, contactMechanismType.getPrimaryKey(), false, false, 0), EntityPermission.READ_ONLY) {
                        @Override
                        public ContactMechanismType getContactMechanismType() {
                            return contactMechanismType;
                        }
                    };
                }
            };
            glAccountLogic = new GlAccountLogic() {
                @Override
                public GlAccount getGlAccountByName(ExecutionErrorAccumulator eea, String glAccountName) {
                    assertEquals("ACCOUNT", glAccountName);
                    return new GlAccount(new GlAccountValue(new GlAccountPK(4L), null, null), EntityPermission.READ_ONLY);
                }
            };
        }
    }

    @ParameterizedTest
    @ValueSource(strings = { "POSTAL_ADDRESS", "EMAIL_ADDRESS" })
    public void acceptsOnlyPostalAddressPurposesAndKeepsTaxAsLockTarget(String contactMechanismTypeName) {
        var command = new TestCommand(contactMechanismTypeName);
        command.canUpdate(command.selectedTax);

        assertSame(command.selectedTax, command.getLockEntity(command.selectedTax));
        assertEquals(!ContactMechanismTypes.POSTAL_ADDRESS.name().equals(contactMechanismTypeName), command.hasExecutionErrors());
        if(command.hasExecutionErrors()) {
            var error = command.getExecutionErrors().get(Messages.EXECUTION_ERROR).next();
            assertEquals(ExecutionErrors.InvalidContactMechanismPurpose.name(), error.getKey());
            assertArrayEquals(new Object[] { "PURPOSE" }, error.getValues());
        }
    }

}
