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

import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.transfer.BaseTransferCaches;

public class PaymentTransferCaches
        extends BaseTransferCaches {
    
    protected PaymentMethodTypeTransferCache paymentMethodTypeTransferCache;
    protected PaymentMethodTypeDescriptionTransferCache paymentMethodTypeDescriptionTransferCache;
    protected PaymentMethodTypePartyTypeTransferCache paymentMethodTypePartyTypeTransferCache;
    protected PaymentMethodTransferCache paymentMethodTransferCache;
    protected PaymentMethodDescriptionTransferCache paymentMethodDescriptionTransferCache;
    protected BillingAccountTransferCache billingAccountTransferCache;
    protected BillingAccountRoleTypeTransferCache billingAccountRoleTypeTransferCache;
    protected BillingAccountRoleTransferCache billingAccountRoleTransferCache;
    protected PartyPaymentMethodTransferCache partyPaymentMethodTransferCache;
    protected PaymentProcessorTypeTransferCache paymentProcessorTypeTransferCache;
    protected PaymentProcessorTypeDescriptionTransferCache paymentProcessorTypeDescriptionTransferCache;
    protected PaymentProcessorTransferCache paymentProcessorTransferCache;
    protected PaymentProcessorDescriptionTransferCache paymentProcessorDescriptionTransferCache;
    protected PartyPaymentMethodContactMechanismTransferCache partyPaymentMethodContactMechanismTransferCache;
    protected PaymentProcessorActionTypeTransferCache paymentProcessorActionTypeTransferCache;
    protected PaymentProcessorActionTypeDescriptionTransferCache paymentProcessorActionTypeDescriptionTransferCache;
    protected PaymentProcessorResultCodeTransferCache paymentProcessorResultCodeTransferCache;
    protected PaymentProcessorResultCodeDescriptionTransferCache paymentProcessorResultCodeDescriptionTransferCache;
    protected PaymentProcessorTypeCodeTypeTransferCache paymentProcessorTypeCodeTypeTransferCache;
    protected PaymentProcessorTypeCodeTypeDescriptionTransferCache paymentProcessorTypeCodeTypeDescriptionTransferCache;
    protected PaymentProcessorTypeCodeTransferCache paymentProcessorTypeCodeTransferCache;
    protected PaymentProcessorTypeCodeDescriptionTransferCache paymentProcessorTypeCodeDescriptionTransferCache;
    protected PaymentProcessorTypeActionTransferCache paymentProcessorTypeActionTransferCache;
    protected PaymentProcessorActionTransferCache paymentProcessorActionTransferCache;
    protected PaymentProcessorTransactionTransferCache paymentProcessorTransactionTransferCache;
    protected PaymentProcessorTransactionCodeTransferCache paymentProcessorTransactionCodeTransferCache;
    
    /** Creates a new instance of PaymentTransferCaches */
    public PaymentTransferCaches() {
        super();
    }

    public PaymentMethodTypeTransferCache getPaymentMethodTypeTransferCache() {
        if(paymentMethodTypeTransferCache == null)
            paymentMethodTypeTransferCache = new PaymentMethodTypeTransferCache();

        return paymentMethodTypeTransferCache;
    }

    public PaymentMethodTypeDescriptionTransferCache getPaymentMethodTypeDescriptionTransferCache() {
        if(paymentMethodTypeDescriptionTransferCache == null)
            paymentMethodTypeDescriptionTransferCache = new PaymentMethodTypeDescriptionTransferCache();

        return paymentMethodTypeDescriptionTransferCache;
    }

    public PaymentMethodTypePartyTypeTransferCache getPaymentMethodTypePartyTypeTransferCache() {
        if(paymentMethodTypePartyTypeTransferCache == null)
            paymentMethodTypePartyTypeTransferCache = new PaymentMethodTypePartyTypeTransferCache();

        return paymentMethodTypePartyTypeTransferCache;
    }

    public PaymentMethodTransferCache getPaymentMethodTransferCache() {
        if(paymentMethodTransferCache == null)
            paymentMethodTransferCache = new PaymentMethodTransferCache();
        
        return paymentMethodTransferCache;
    }
    
    public PaymentMethodDescriptionTransferCache getPaymentMethodDescriptionTransferCache() {
        if(paymentMethodDescriptionTransferCache == null)
            paymentMethodDescriptionTransferCache = new PaymentMethodDescriptionTransferCache();
        
        return paymentMethodDescriptionTransferCache;
    }
    
    public BillingAccountTransferCache getBillingAccountTransferCache() {
        if(billingAccountTransferCache == null)
            billingAccountTransferCache = new BillingAccountTransferCache();
        
        return billingAccountTransferCache;
    }
    
    public BillingAccountRoleTypeTransferCache getBillingAccountRoleTypeTransferCache() {
        if(billingAccountRoleTypeTransferCache == null)
            billingAccountRoleTypeTransferCache = new BillingAccountRoleTypeTransferCache();
        
        return billingAccountRoleTypeTransferCache;
    }
    
    public BillingAccountRoleTransferCache getBillingAccountRoleTransferCache() {
        if(billingAccountRoleTransferCache == null)
            billingAccountRoleTransferCache = new BillingAccountRoleTransferCache();
        
        return billingAccountRoleTransferCache;
    }
    
    public PartyPaymentMethodTransferCache getPartyPaymentMethodTransferCache() {
        if(partyPaymentMethodTransferCache == null)
            partyPaymentMethodTransferCache = new PartyPaymentMethodTransferCache();
        
        return partyPaymentMethodTransferCache;
    }

    public PaymentProcessorTypeTransferCache getPaymentProcessorTypeTransferCache() {
        if(paymentProcessorTypeTransferCache == null)
            paymentProcessorTypeTransferCache = new PaymentProcessorTypeTransferCache();

        return paymentProcessorTypeTransferCache;
    }

    public PaymentProcessorTypeDescriptionTransferCache getPaymentProcessorTypeDescriptionTransferCache() {
        if(paymentProcessorTypeDescriptionTransferCache == null)
            paymentProcessorTypeDescriptionTransferCache = new PaymentProcessorTypeDescriptionTransferCache();

        return paymentProcessorTypeDescriptionTransferCache;
    }

    public PaymentProcessorTransferCache getPaymentProcessorTransferCache() {
        if(paymentProcessorTransferCache == null)
            paymentProcessorTransferCache = new PaymentProcessorTransferCache();
        
        return paymentProcessorTransferCache;
    }
    
    public PaymentProcessorDescriptionTransferCache getPaymentProcessorDescriptionTransferCache() {
        if(paymentProcessorDescriptionTransferCache == null)
            paymentProcessorDescriptionTransferCache = new PaymentProcessorDescriptionTransferCache();
        
        return paymentProcessorDescriptionTransferCache;
    }
    
    public PartyPaymentMethodContactMechanismTransferCache getPartyPaymentMethodContactMechanismTransferCache() {
        if(partyPaymentMethodContactMechanismTransferCache == null)
            partyPaymentMethodContactMechanismTransferCache = new PartyPaymentMethodContactMechanismTransferCache();
        
        return partyPaymentMethodContactMechanismTransferCache;
    }

    public PaymentProcessorActionTypeTransferCache getPaymentProcessorActionTypeTransferCache() {
        if(paymentProcessorActionTypeTransferCache == null)
            paymentProcessorActionTypeTransferCache = new PaymentProcessorActionTypeTransferCache();

        return paymentProcessorActionTypeTransferCache;
    }

    public PaymentProcessorActionTypeDescriptionTransferCache getPaymentProcessorActionTypeDescriptionTransferCache() {
        if(paymentProcessorActionTypeDescriptionTransferCache == null)
            paymentProcessorActionTypeDescriptionTransferCache = new PaymentProcessorActionTypeDescriptionTransferCache();

        return paymentProcessorActionTypeDescriptionTransferCache;
    }

    public PaymentProcessorResultCodeTransferCache getPaymentProcessorResultCodeTransferCache() {
        if(paymentProcessorResultCodeTransferCache == null)
            paymentProcessorResultCodeTransferCache = new PaymentProcessorResultCodeTransferCache();

        return paymentProcessorResultCodeTransferCache;
    }

    public PaymentProcessorResultCodeDescriptionTransferCache getPaymentProcessorResultCodeDescriptionTransferCache() {
        if(paymentProcessorResultCodeDescriptionTransferCache == null)
            paymentProcessorResultCodeDescriptionTransferCache = new PaymentProcessorResultCodeDescriptionTransferCache();

        return paymentProcessorResultCodeDescriptionTransferCache;
    }

    public PaymentProcessorTypeCodeTypeTransferCache getPaymentProcessorTypeCodeTypeTransferCache() {
        if(paymentProcessorTypeCodeTypeTransferCache == null)
            paymentProcessorTypeCodeTypeTransferCache = new PaymentProcessorTypeCodeTypeTransferCache();

        return paymentProcessorTypeCodeTypeTransferCache;
    }

    public PaymentProcessorTypeCodeTypeDescriptionTransferCache getPaymentProcessorTypeCodeTypeDescriptionTransferCache() {
        if(paymentProcessorTypeCodeTypeDescriptionTransferCache == null)
            paymentProcessorTypeCodeTypeDescriptionTransferCache = new PaymentProcessorTypeCodeTypeDescriptionTransferCache();

        return paymentProcessorTypeCodeTypeDescriptionTransferCache;
    }

    public PaymentProcessorTypeCodeTransferCache getPaymentProcessorTypeCodeTransferCache() {
        if(paymentProcessorTypeCodeTransferCache == null)
            paymentProcessorTypeCodeTransferCache = new PaymentProcessorTypeCodeTransferCache();

        return paymentProcessorTypeCodeTransferCache;
    }

    public PaymentProcessorTypeCodeDescriptionTransferCache getPaymentProcessorTypeCodeDescriptionTransferCache() {
        if(paymentProcessorTypeCodeDescriptionTransferCache == null)
            paymentProcessorTypeCodeDescriptionTransferCache = new PaymentProcessorTypeCodeDescriptionTransferCache();

        return paymentProcessorTypeCodeDescriptionTransferCache;
    }

    public PaymentProcessorTypeActionTransferCache getPaymentProcessorTypeActionTransferCache() {
        if(paymentProcessorTypeActionTransferCache == null)
            paymentProcessorTypeActionTransferCache = new PaymentProcessorTypeActionTransferCache();

        return paymentProcessorTypeActionTransferCache;
    }

    public PaymentProcessorActionTransferCache getPaymentProcessorActionTransferCache() {
        if(paymentProcessorActionTransferCache == null)
            paymentProcessorActionTransferCache = new PaymentProcessorActionTransferCache();

        return paymentProcessorActionTransferCache;
    }

    public PaymentProcessorTransactionTransferCache getPaymentProcessorTransactionTransferCache() {
        if(paymentProcessorTransactionTransferCache == null)
            paymentProcessorTransactionTransferCache = new PaymentProcessorTransactionTransferCache();

        return paymentProcessorTransactionTransferCache;
    }

    public PaymentProcessorTransactionCodeTransferCache getPaymentProcessorTransactionCodeTransferCache() {
        if(paymentProcessorTransactionCodeTransferCache == null)
            paymentProcessorTransactionCodeTransferCache = new PaymentProcessorTransactionCodeTransferCache();

        return paymentProcessorTransactionCodeTransferCache;
    }

}
