// --------------------------------------------------------------------------------
// Copyright 2002-2022 Echo Three, LLC
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
    public PaymentTransferCaches(UserVisit userVisit) {
        super(userVisit);
    }

    public PaymentMethodTypeTransferCache getPaymentMethodTypeTransferCache() {
        if(paymentMethodTypeTransferCache == null)
            paymentMethodTypeTransferCache = new PaymentMethodTypeTransferCache(userVisit);

        return paymentMethodTypeTransferCache;
    }

    public PaymentMethodTypeDescriptionTransferCache getPaymentMethodTypeDescriptionTransferCache() {
        if(paymentMethodTypeDescriptionTransferCache == null)
            paymentMethodTypeDescriptionTransferCache = new PaymentMethodTypeDescriptionTransferCache(userVisit);

        return paymentMethodTypeDescriptionTransferCache;
    }

    public PaymentMethodTypePartyTypeTransferCache getPaymentMethodTypePartyTypeTransferCache() {
        if(paymentMethodTypePartyTypeTransferCache == null)
            paymentMethodTypePartyTypeTransferCache = new PaymentMethodTypePartyTypeTransferCache(userVisit);

        return paymentMethodTypePartyTypeTransferCache;
    }

    public PaymentMethodTransferCache getPaymentMethodTransferCache() {
        if(paymentMethodTransferCache == null)
            paymentMethodTransferCache = new PaymentMethodTransferCache(userVisit);
        
        return paymentMethodTransferCache;
    }
    
    public PaymentMethodDescriptionTransferCache getPaymentMethodDescriptionTransferCache() {
        if(paymentMethodDescriptionTransferCache == null)
            paymentMethodDescriptionTransferCache = new PaymentMethodDescriptionTransferCache(userVisit);
        
        return paymentMethodDescriptionTransferCache;
    }
    
    public BillingAccountTransferCache getBillingAccountTransferCache() {
        if(billingAccountTransferCache == null)
            billingAccountTransferCache = new BillingAccountTransferCache(userVisit);
        
        return billingAccountTransferCache;
    }
    
    public BillingAccountRoleTypeTransferCache getBillingAccountRoleTypeTransferCache() {
        if(billingAccountRoleTypeTransferCache == null)
            billingAccountRoleTypeTransferCache = new BillingAccountRoleTypeTransferCache(userVisit);
        
        return billingAccountRoleTypeTransferCache;
    }
    
    public BillingAccountRoleTransferCache getBillingAccountRoleTransferCache() {
        if(billingAccountRoleTransferCache == null)
            billingAccountRoleTransferCache = new BillingAccountRoleTransferCache(userVisit);
        
        return billingAccountRoleTransferCache;
    }
    
    public PartyPaymentMethodTransferCache getPartyPaymentMethodTransferCache() {
        if(partyPaymentMethodTransferCache == null)
            partyPaymentMethodTransferCache = new PartyPaymentMethodTransferCache(userVisit);
        
        return partyPaymentMethodTransferCache;
    }

    public PaymentProcessorTypeTransferCache getPaymentProcessorTypeTransferCache() {
        if(paymentProcessorTypeTransferCache == null)
            paymentProcessorTypeTransferCache = new PaymentProcessorTypeTransferCache(userVisit);

        return paymentProcessorTypeTransferCache;
    }

    public PaymentProcessorTypeDescriptionTransferCache getPaymentProcessorTypeDescriptionTransferCache() {
        if(paymentProcessorTypeDescriptionTransferCache == null)
            paymentProcessorTypeDescriptionTransferCache = new PaymentProcessorTypeDescriptionTransferCache(userVisit);

        return paymentProcessorTypeDescriptionTransferCache;
    }

    public PaymentProcessorTransferCache getPaymentProcessorTransferCache() {
        if(paymentProcessorTransferCache == null)
            paymentProcessorTransferCache = new PaymentProcessorTransferCache(userVisit);
        
        return paymentProcessorTransferCache;
    }
    
    public PaymentProcessorDescriptionTransferCache getPaymentProcessorDescriptionTransferCache() {
        if(paymentProcessorDescriptionTransferCache == null)
            paymentProcessorDescriptionTransferCache = new PaymentProcessorDescriptionTransferCache(userVisit);
        
        return paymentProcessorDescriptionTransferCache;
    }
    
    public PartyPaymentMethodContactMechanismTransferCache getPartyPaymentMethodContactMechanismTransferCache() {
        if(partyPaymentMethodContactMechanismTransferCache == null)
            partyPaymentMethodContactMechanismTransferCache = new PartyPaymentMethodContactMechanismTransferCache(userVisit);
        
        return partyPaymentMethodContactMechanismTransferCache;
    }

    public PaymentProcessorActionTypeTransferCache getPaymentProcessorActionTypeTransferCache() {
        if(paymentProcessorActionTypeTransferCache == null)
            paymentProcessorActionTypeTransferCache = new PaymentProcessorActionTypeTransferCache(userVisit);

        return paymentProcessorActionTypeTransferCache;
    }

    public PaymentProcessorActionTypeDescriptionTransferCache getPaymentProcessorActionTypeDescriptionTransferCache() {
        if(paymentProcessorActionTypeDescriptionTransferCache == null)
            paymentProcessorActionTypeDescriptionTransferCache = new PaymentProcessorActionTypeDescriptionTransferCache(userVisit);

        return paymentProcessorActionTypeDescriptionTransferCache;
    }

    public PaymentProcessorResultCodeTransferCache getPaymentProcessorResultCodeTransferCache() {
        if(paymentProcessorResultCodeTransferCache == null)
            paymentProcessorResultCodeTransferCache = new PaymentProcessorResultCodeTransferCache(userVisit);

        return paymentProcessorResultCodeTransferCache;
    }

    public PaymentProcessorResultCodeDescriptionTransferCache getPaymentProcessorResultCodeDescriptionTransferCache() {
        if(paymentProcessorResultCodeDescriptionTransferCache == null)
            paymentProcessorResultCodeDescriptionTransferCache = new PaymentProcessorResultCodeDescriptionTransferCache(userVisit);

        return paymentProcessorResultCodeDescriptionTransferCache;
    }

    public PaymentProcessorTypeCodeTypeTransferCache getPaymentProcessorTypeCodeTypeTransferCache() {
        if(paymentProcessorTypeCodeTypeTransferCache == null)
            paymentProcessorTypeCodeTypeTransferCache = new PaymentProcessorTypeCodeTypeTransferCache(userVisit);

        return paymentProcessorTypeCodeTypeTransferCache;
    }

    public PaymentProcessorTypeCodeTypeDescriptionTransferCache getPaymentProcessorTypeCodeTypeDescriptionTransferCache() {
        if(paymentProcessorTypeCodeTypeDescriptionTransferCache == null)
            paymentProcessorTypeCodeTypeDescriptionTransferCache = new PaymentProcessorTypeCodeTypeDescriptionTransferCache(userVisit);

        return paymentProcessorTypeCodeTypeDescriptionTransferCache;
    }

    public PaymentProcessorTypeCodeTransferCache getPaymentProcessorTypeCodeTransferCache() {
        if(paymentProcessorTypeCodeTransferCache == null)
            paymentProcessorTypeCodeTransferCache = new PaymentProcessorTypeCodeTransferCache(userVisit);

        return paymentProcessorTypeCodeTransferCache;
    }

    public PaymentProcessorTypeCodeDescriptionTransferCache getPaymentProcessorTypeCodeDescriptionTransferCache() {
        if(paymentProcessorTypeCodeDescriptionTransferCache == null)
            paymentProcessorTypeCodeDescriptionTransferCache = new PaymentProcessorTypeCodeDescriptionTransferCache(userVisit);

        return paymentProcessorTypeCodeDescriptionTransferCache;
    }

    public PaymentProcessorTypeActionTransferCache getPaymentProcessorTypeActionTransferCache() {
        if(paymentProcessorTypeActionTransferCache == null)
            paymentProcessorTypeActionTransferCache = new PaymentProcessorTypeActionTransferCache(userVisit);

        return paymentProcessorTypeActionTransferCache;
    }

    public PaymentProcessorActionTransferCache getPaymentProcessorActionTransferCache() {
        if(paymentProcessorActionTransferCache == null)
            paymentProcessorActionTransferCache = new PaymentProcessorActionTransferCache(userVisit);

        return paymentProcessorActionTransferCache;
    }

    public PaymentProcessorTransactionTransferCache getPaymentProcessorTransactionTransferCache() {
        if(paymentProcessorTransactionTransferCache == null)
            paymentProcessorTransactionTransferCache = new PaymentProcessorTransactionTransferCache(userVisit);

        return paymentProcessorTransactionTransferCache;
    }

    public PaymentProcessorTransactionCodeTransferCache getPaymentProcessorTransactionCodeTransferCache() {
        if(paymentProcessorTransactionCodeTransferCache == null)
            paymentProcessorTransactionCodeTransferCache = new PaymentProcessorTransactionCodeTransferCache(userVisit);

        return paymentProcessorTransactionCodeTransferCache;
    }

}
