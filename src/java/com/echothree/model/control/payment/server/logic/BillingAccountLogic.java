// --------------------------------------------------------------------------------
// Copyright 2002-2025 Echo Three, LLC
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

package com.echothree.model.control.payment.server.logic;

import com.echothree.model.control.contact.common.ContactMechanismPurposes;
import com.echothree.model.control.contact.server.control.ContactControl;
import com.echothree.model.control.payment.common.BillingAccountRoleTypes;
import com.echothree.model.control.payment.server.control.BillingControl;
import com.echothree.model.data.accounting.server.entity.Currency;
import com.echothree.model.data.contact.server.entity.PartyContactMechanism;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.model.data.payment.server.entity.BillingAccount;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.Session;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.spi.CDI;

@ApplicationScoped
public class BillingAccountLogic {

    protected BillingAccountLogic() {
        super();
    }

    public static BillingAccountLogic getInstance() {
        return CDI.current().select(BillingAccountLogic.class).get();
    }

    public BillingAccount getBillingAccount(final ExecutionErrorAccumulator ema, final Party billFrom, PartyContactMechanism billFromPartyContactMechanism,
            final Party billTo, PartyContactMechanism billToPartyContactMechanism, final Currency currency, final String reference, final String description, final BasePK createdBy) {
        var billingControl = Session.getModelController(BillingControl.class);
        var billingAccount = billingControl.getBillingAccount(billFrom, billTo, currency);

        // If the BillingAccount was found, the billFromPartyContactMechanism and billToPartyContactMechanism parameters are ignored. They're used only
        // when a new BillingAccount needs to be created.
        if(billingAccount == null) {
            if(billFromPartyContactMechanism == null || billToPartyContactMechanism == null) {
                var contactControl = Session.getModelController(ContactControl.class);
                var contactMechanismPurpose = contactControl.getContactMechanismPurposeByName(ContactMechanismPurposes.PHYSICAL_BILLING.name());
                
                if(billFromPartyContactMechanism == null) {
                    var partyContactMechanismPurpose = contactControl.getDefaultPartyContactMechanismPurpose(billFrom, contactMechanismPurpose);
                    
                    if(partyContactMechanismPurpose != null) {
                        billFromPartyContactMechanism = partyContactMechanismPurpose.getLastDetail().getPartyContactMechanism();
                    } else {
                        ema.addExecutionError(ExecutionErrors.UnknownBillFromPartyContactMechanismPurpose.name());
                    }
                } else {
                    // Make sure that the manually chosen PartyContactMechanism is marked with the PHYSICAL_BILLING ContactMechanismPurpose
                    var partyContactMechanismPurpose = contactControl.getPartyContactMechanismPurpose(billFromPartyContactMechanism, contactMechanismPurpose);
                    
                    if(partyContactMechanismPurpose == null) {
                        contactControl.createPartyContactMechanismPurpose(billFromPartyContactMechanism, contactMechanismPurpose, false, 1, createdBy);
                    }
                }

                if(billToPartyContactMechanism == null) {
                    var partyContactMechanismPurpose = contactControl.getDefaultPartyContactMechanismPurpose(billTo, contactMechanismPurpose);
                    
                    if(partyContactMechanismPurpose != null) {
                        billToPartyContactMechanism = partyContactMechanismPurpose.getLastDetail().getPartyContactMechanism();
                    } else {
                        ema.addExecutionError(ExecutionErrors.UnknownBillToPartyContactMechanismPurpose.name());
                    }
                } else {
                    // Make sure that the manually chosen PartyContactMechanism is marked with the PHYSICAL_BILLING ContactMechanismPurpose
                    var partyContactMechanismPurpose = contactControl.getPartyContactMechanismPurpose(billToPartyContactMechanism, contactMechanismPurpose);
                    
                    if(partyContactMechanismPurpose == null) {
                        contactControl.createPartyContactMechanismPurpose(billToPartyContactMechanism, contactMechanismPurpose, false, 1, createdBy);
                    }
                }
            }
            
            if(!ema.hasExecutionErrors()) {
                billingAccount = billingControl.createBillingAccount(billFrom, currency, reference, description, createdBy);
                billingControl.createBillingAccountRoleUsingNames(billingAccount, billFrom, billFromPartyContactMechanism, BillingAccountRoleTypes.BILL_FROM.name(), createdBy);
                billingControl.createBillingAccountRoleUsingNames(billingAccount, billTo, billToPartyContactMechanism, BillingAccountRoleTypes.BILL_TO.name(), createdBy);
            }
        }

        return billingAccount;
    }
}