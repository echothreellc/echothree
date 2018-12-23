// --------------------------------------------------------------------------------
// Copyright 2002-2019 Echo Three, LLC
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
import com.echothree.model.control.contact.server.ContactControl;
import com.echothree.model.control.payment.common.PaymentConstants;
import com.echothree.model.control.payment.server.PaymentControl;
import com.echothree.model.data.accounting.server.entity.Currency;
import com.echothree.model.data.contact.server.entity.ContactMechanismPurpose;
import com.echothree.model.data.contact.server.entity.PartyContactMechanism;
import com.echothree.model.data.contact.server.entity.PartyContactMechanismPurpose;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.model.data.payment.server.entity.BillingAccount;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.Session;

public class BillingAccountLogic {

    private BillingAccountLogic() {
        super();
    }

    private static class BillingAccountLogicHolder {
        static BillingAccountLogic instance = new BillingAccountLogic();
    }

    public static BillingAccountLogic getInstance() {
        return BillingAccountLogicHolder.instance;
    }

    public BillingAccount getBillingAccount(final ExecutionErrorAccumulator ema, final Party billFrom, PartyContactMechanism billFromPartyContactMechanism,
            final Party billTo, PartyContactMechanism billToPartyContactMechanism, final Currency currency, final String reference, final String description, final BasePK createdBy) {
        PaymentControl paymentControl = (PaymentControl)Session.getModelController(PaymentControl.class);
        BillingAccount billingAccount = paymentControl.getBillingAccount(billFrom, billTo, currency);

        // If the BillingAccount was found, the billFromPartyContactMechanism and billToPartyContactMechanism parameters are ignored. They're used only
        // when a new BillingAccount needs to be created.
        if(billingAccount == null) {
            if(billFromPartyContactMechanism == null || billToPartyContactMechanism == null) {
                ContactControl contactControl = (ContactControl)Session.getModelController(ContactControl.class);
                ContactMechanismPurpose contactMechanismPurpose = contactControl.getContactMechanismPurposeByName(ContactMechanismPurposes.PHYSICAL_BILLING.name());
                
                if(billFromPartyContactMechanism == null) {
                    PartyContactMechanismPurpose partyContactMechanismPurpose = contactControl.getDefaultPartyContactMechanismPurpose(billFrom, contactMechanismPurpose);
                    
                    if(partyContactMechanismPurpose != null) {
                        billFromPartyContactMechanism = partyContactMechanismPurpose.getLastDetail().getPartyContactMechanism();
                    } else {
                        ema.addExecutionError(ExecutionErrors.UnknownBillFromPartyContactMechanismPurpose.name());
                    }
                } else {
                    // Make sure that the manually choosen PartyContactMechanism is marked with the PHYSICAL_BILLING ContactMechanismPurpose
                    PartyContactMechanismPurpose partyContactMechanismPurpose = contactControl.getPartyContactMechanismPurpose(billFromPartyContactMechanism, contactMechanismPurpose);
                    
                    if(partyContactMechanismPurpose == null) {
                        contactControl.createPartyContactMechanismPurpose(billFromPartyContactMechanism, contactMechanismPurpose, Boolean.FALSE, 1, createdBy);
                    }
                }

                if(billToPartyContactMechanism == null) {
                    PartyContactMechanismPurpose partyContactMechanismPurpose = contactControl.getDefaultPartyContactMechanismPurpose(billTo, contactMechanismPurpose);
                    
                    if(partyContactMechanismPurpose != null) {
                        billToPartyContactMechanism = partyContactMechanismPurpose.getLastDetail().getPartyContactMechanism();
                    } else {
                        ema.addExecutionError(ExecutionErrors.UnknownBillToPartyContactMechanismPurpose.name());
                    }
                } else {
                    // Make sure that the manually choosen PartyContactMechanism is marked with the PHYSICAL_BILLING ContactMechanismPurpose
                    PartyContactMechanismPurpose partyContactMechanismPurpose = contactControl.getPartyContactMechanismPurpose(billToPartyContactMechanism, contactMechanismPurpose);
                    
                    if(partyContactMechanismPurpose == null) {
                        contactControl.createPartyContactMechanismPurpose(billToPartyContactMechanism, contactMechanismPurpose, Boolean.FALSE, 1, createdBy);
                    }
                }
            }
            
            if(!ema.hasExecutionErrors()) {
                billingAccount = paymentControl.createBillingAccount(billFrom, currency, reference, description, createdBy);
                paymentControl.createBillingAccountRoleUsingNames(billingAccount, billFrom, billFromPartyContactMechanism, PaymentConstants.BillingAccountRoleType_BILL_FROM, createdBy);
                paymentControl.createBillingAccountRoleUsingNames(billingAccount, billTo, billToPartyContactMechanism, PaymentConstants.BillingAccountRoleType_BILL_TO, createdBy);
            }
        }

        return billingAccount;
    }
}