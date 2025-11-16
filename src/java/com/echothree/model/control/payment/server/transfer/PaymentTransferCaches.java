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

package com.echothree.model.control.payment.server.transfer;

import com.echothree.util.server.transfer.BaseTransferCaches;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

@RequestScoped
public class PaymentTransferCaches
        extends BaseTransferCaches {
    
    @Inject
    PaymentMethodTypeTransferCache paymentMethodTypeTransferCache;
    
    @Inject
    PaymentMethodTypeDescriptionTransferCache paymentMethodTypeDescriptionTransferCache;
    
    @Inject
    PaymentMethodTypePartyTypeTransferCache paymentMethodTypePartyTypeTransferCache;
    
    @Inject
    PaymentMethodTransferCache paymentMethodTransferCache;
    
    @Inject
    PaymentMethodDescriptionTransferCache paymentMethodDescriptionTransferCache;
    
    @Inject
    BillingAccountTransferCache billingAccountTransferCache;
    
    @Inject
    BillingAccountRoleTypeTransferCache billingAccountRoleTypeTransferCache;
    
    @Inject
    BillingAccountRoleTransferCache billingAccountRoleTransferCache;
    
    @Inject
    PartyPaymentMethodTransferCache partyPaymentMethodTransferCache;
    
    @Inject
    PaymentProcessorTypeTransferCache paymentProcessorTypeTransferCache;
    
    @Inject
    PaymentProcessorTypeDescriptionTransferCache paymentProcessorTypeDescriptionTransferCache;
    
    @Inject
    PaymentProcessorTransferCache paymentProcessorTransferCache;
    
    @Inject
    PaymentProcessorDescriptionTransferCache paymentProcessorDescriptionTransferCache;
    
    @Inject
    PartyPaymentMethodContactMechanismTransferCache partyPaymentMethodContactMechanismTransferCache;
    
    @Inject
    PaymentProcessorActionTypeTransferCache paymentProcessorActionTypeTransferCache;
    
    @Inject
    PaymentProcessorActionTypeDescriptionTransferCache paymentProcessorActionTypeDescriptionTransferCache;
    
    @Inject
    PaymentProcessorResultCodeTransferCache paymentProcessorResultCodeTransferCache;
    
    @Inject
    PaymentProcessorResultCodeDescriptionTransferCache paymentProcessorResultCodeDescriptionTransferCache;
    
    @Inject
    PaymentProcessorTypeCodeTypeTransferCache paymentProcessorTypeCodeTypeTransferCache;
    
    @Inject
    PaymentProcessorTypeCodeTypeDescriptionTransferCache paymentProcessorTypeCodeTypeDescriptionTransferCache;
    
    @Inject
    PaymentProcessorTypeCodeTransferCache paymentProcessorTypeCodeTransferCache;
    
    @Inject
    PaymentProcessorTypeCodeDescriptionTransferCache paymentProcessorTypeCodeDescriptionTransferCache;
    
    @Inject
    PaymentProcessorTypeActionTransferCache paymentProcessorTypeActionTransferCache;
    
    @Inject
    PaymentProcessorActionTransferCache paymentProcessorActionTransferCache;
    
    @Inject
    PaymentProcessorTransactionTransferCache paymentProcessorTransactionTransferCache;
    
    @Inject
    PaymentProcessorTransactionCodeTransferCache paymentProcessorTransactionCodeTransferCache;

    /** Creates a new instance of PaymentTransferCaches */
    protected PaymentTransferCaches() {
        super();
    }

    public PaymentMethodTypeTransferCache getPaymentMethodTypeTransferCache() {
        return paymentMethodTypeTransferCache;
    }

    public PaymentMethodTypeDescriptionTransferCache getPaymentMethodTypeDescriptionTransferCache() {
        return paymentMethodTypeDescriptionTransferCache;
    }

    public PaymentMethodTypePartyTypeTransferCache getPaymentMethodTypePartyTypeTransferCache() {
        return paymentMethodTypePartyTypeTransferCache;
    }

    public PaymentMethodTransferCache getPaymentMethodTransferCache() {
        return paymentMethodTransferCache;
    }
    
    public PaymentMethodDescriptionTransferCache getPaymentMethodDescriptionTransferCache() {
        return paymentMethodDescriptionTransferCache;
    }
    
    public BillingAccountTransferCache getBillingAccountTransferCache() {
        return billingAccountTransferCache;
    }
    
    public BillingAccountRoleTypeTransferCache getBillingAccountRoleTypeTransferCache() {
        return billingAccountRoleTypeTransferCache;
    }
    
    public BillingAccountRoleTransferCache getBillingAccountRoleTransferCache() {
        return billingAccountRoleTransferCache;
    }
    
    public PartyPaymentMethodTransferCache getPartyPaymentMethodTransferCache() {
        return partyPaymentMethodTransferCache;
    }

    public PaymentProcessorTypeTransferCache getPaymentProcessorTypeTransferCache() {
        return paymentProcessorTypeTransferCache;
    }

    public PaymentProcessorTypeDescriptionTransferCache getPaymentProcessorTypeDescriptionTransferCache() {
        return paymentProcessorTypeDescriptionTransferCache;
    }

    public PaymentProcessorTransferCache getPaymentProcessorTransferCache() {
        return paymentProcessorTransferCache;
    }
    
    public PaymentProcessorDescriptionTransferCache getPaymentProcessorDescriptionTransferCache() {
        return paymentProcessorDescriptionTransferCache;
    }
    
    public PartyPaymentMethodContactMechanismTransferCache getPartyPaymentMethodContactMechanismTransferCache() {
        return partyPaymentMethodContactMechanismTransferCache;
    }

    public PaymentProcessorActionTypeTransferCache getPaymentProcessorActionTypeTransferCache() {
        return paymentProcessorActionTypeTransferCache;
    }

    public PaymentProcessorActionTypeDescriptionTransferCache getPaymentProcessorActionTypeDescriptionTransferCache() {
        return paymentProcessorActionTypeDescriptionTransferCache;
    }

    public PaymentProcessorResultCodeTransferCache getPaymentProcessorResultCodeTransferCache() {
        return paymentProcessorResultCodeTransferCache;
    }

    public PaymentProcessorResultCodeDescriptionTransferCache getPaymentProcessorResultCodeDescriptionTransferCache() {
        return paymentProcessorResultCodeDescriptionTransferCache;
    }

    public PaymentProcessorTypeCodeTypeTransferCache getPaymentProcessorTypeCodeTypeTransferCache() {
        return paymentProcessorTypeCodeTypeTransferCache;
    }

    public PaymentProcessorTypeCodeTypeDescriptionTransferCache getPaymentProcessorTypeCodeTypeDescriptionTransferCache() {
        return paymentProcessorTypeCodeTypeDescriptionTransferCache;
    }

    public PaymentProcessorTypeCodeTransferCache getPaymentProcessorTypeCodeTransferCache() {
        return paymentProcessorTypeCodeTransferCache;
    }

    public PaymentProcessorTypeCodeDescriptionTransferCache getPaymentProcessorTypeCodeDescriptionTransferCache() {
        return paymentProcessorTypeCodeDescriptionTransferCache;
    }

    public PaymentProcessorTypeActionTransferCache getPaymentProcessorTypeActionTransferCache() {
        return paymentProcessorTypeActionTransferCache;
    }

    public PaymentProcessorActionTransferCache getPaymentProcessorActionTransferCache() {
        return paymentProcessorActionTransferCache;
    }

    public PaymentProcessorTransactionTransferCache getPaymentProcessorTransactionTransferCache() {
        return paymentProcessorTransactionTransferCache;
    }

    public PaymentProcessorTransactionCodeTransferCache getPaymentProcessorTransactionCodeTransferCache() {
        return paymentProcessorTransactionCodeTransferCache;
    }

}
