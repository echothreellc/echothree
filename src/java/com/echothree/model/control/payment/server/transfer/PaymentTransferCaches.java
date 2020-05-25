// --------------------------------------------------------------------------------
// Copyright 2002-2020 Echo Three, LLC
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

import com.echothree.model.control.payment.server.control.PaymentControl;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.transfer.BaseTransferCaches;

public class PaymentTransferCaches
        extends BaseTransferCaches {
    
    protected PaymentControl paymentControl;

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
    public PaymentTransferCaches(UserVisit userVisit, PaymentControl paymentControl) {
        super(userVisit);
        
        this.paymentControl = paymentControl;
    }

    public PaymentMethodTypeTransferCache getPaymentMethodTypeTransferCache() {
        if(paymentMethodTypeTransferCache == null)
            paymentMethodTypeTransferCache = new PaymentMethodTypeTransferCache(userVisit, paymentControl);

        return paymentMethodTypeTransferCache;
    }

    public PaymentMethodTypeDescriptionTransferCache getPaymentMethodTypeDescriptionTransferCache() {
        if(paymentMethodTypeDescriptionTransferCache == null)
            paymentMethodTypeDescriptionTransferCache = new PaymentMethodTypeDescriptionTransferCache(userVisit, paymentControl);

        return paymentMethodTypeDescriptionTransferCache;
    }

    public PaymentMethodTypePartyTypeTransferCache getPaymentMethodTypePartyTypeTransferCache() {
        if(paymentMethodTypePartyTypeTransferCache == null)
            paymentMethodTypePartyTypeTransferCache = new PaymentMethodTypePartyTypeTransferCache(userVisit, paymentControl);

        return paymentMethodTypePartyTypeTransferCache;
    }

    public PaymentMethodTransferCache getPaymentMethodTransferCache() {
        if(paymentMethodTransferCache == null)
            paymentMethodTransferCache = new PaymentMethodTransferCache(userVisit, paymentControl);
        
        return paymentMethodTransferCache;
    }
    
    public PaymentMethodDescriptionTransferCache getPaymentMethodDescriptionTransferCache() {
        if(paymentMethodDescriptionTransferCache == null)
            paymentMethodDescriptionTransferCache = new PaymentMethodDescriptionTransferCache(userVisit, paymentControl);
        
        return paymentMethodDescriptionTransferCache;
    }
    
    public BillingAccountTransferCache getBillingAccountTransferCache() {
        if(billingAccountTransferCache == null)
            billingAccountTransferCache = new BillingAccountTransferCache(userVisit, paymentControl);
        
        return billingAccountTransferCache;
    }
    
    public BillingAccountRoleTypeTransferCache getBillingAccountRoleTypeTransferCache() {
        if(billingAccountRoleTypeTransferCache == null)
            billingAccountRoleTypeTransferCache = new BillingAccountRoleTypeTransferCache(userVisit, paymentControl);
        
        return billingAccountRoleTypeTransferCache;
    }
    
    public BillingAccountRoleTransferCache getBillingAccountRoleTransferCache() {
        if(billingAccountRoleTransferCache == null)
            billingAccountRoleTransferCache = new BillingAccountRoleTransferCache(userVisit, paymentControl);
        
        return billingAccountRoleTransferCache;
    }
    
    public PartyPaymentMethodTransferCache getPartyPaymentMethodTransferCache() {
        if(partyPaymentMethodTransferCache == null)
            partyPaymentMethodTransferCache = new PartyPaymentMethodTransferCache(userVisit, paymentControl);
        
        return partyPaymentMethodTransferCache;
    }

    public PaymentProcessorTypeTransferCache getPaymentProcessorTypeTransferCache() {
        if(paymentProcessorTypeTransferCache == null)
            paymentProcessorTypeTransferCache = new PaymentProcessorTypeTransferCache(userVisit, paymentControl);

        return paymentProcessorTypeTransferCache;
    }

    public PaymentProcessorTypeDescriptionTransferCache getPaymentProcessorTypeDescriptionTransferCache() {
        if(paymentProcessorTypeDescriptionTransferCache == null)
            paymentProcessorTypeDescriptionTransferCache = new PaymentProcessorTypeDescriptionTransferCache(userVisit, paymentControl);

        return paymentProcessorTypeDescriptionTransferCache;
    }

    public PaymentProcessorTransferCache getPaymentProcessorTransferCache() {
        if(paymentProcessorTransferCache == null)
            paymentProcessorTransferCache = new PaymentProcessorTransferCache(userVisit, paymentControl);
        
        return paymentProcessorTransferCache;
    }
    
    public PaymentProcessorDescriptionTransferCache getPaymentProcessorDescriptionTransferCache() {
        if(paymentProcessorDescriptionTransferCache == null)
            paymentProcessorDescriptionTransferCache = new PaymentProcessorDescriptionTransferCache(userVisit, paymentControl);
        
        return paymentProcessorDescriptionTransferCache;
    }
    
    public PartyPaymentMethodContactMechanismTransferCache getPartyPaymentMethodContactMechanismTransferCache() {
        if(partyPaymentMethodContactMechanismTransferCache == null)
            partyPaymentMethodContactMechanismTransferCache = new PartyPaymentMethodContactMechanismTransferCache(userVisit, paymentControl);
        
        return partyPaymentMethodContactMechanismTransferCache;
    }

    public PaymentProcessorActionTypeTransferCache getPaymentProcessorActionTypeTransferCache() {
        if(paymentProcessorActionTypeTransferCache == null)
            paymentProcessorActionTypeTransferCache = new PaymentProcessorActionTypeTransferCache(userVisit, paymentControl);

        return paymentProcessorActionTypeTransferCache;
    }

    public PaymentProcessorActionTypeDescriptionTransferCache getPaymentProcessorActionTypeDescriptionTransferCache() {
        if(paymentProcessorActionTypeDescriptionTransferCache == null)
            paymentProcessorActionTypeDescriptionTransferCache = new PaymentProcessorActionTypeDescriptionTransferCache(userVisit, paymentControl);

        return paymentProcessorActionTypeDescriptionTransferCache;
    }

    public PaymentProcessorResultCodeTransferCache getPaymentProcessorResultCodeTransferCache() {
        if(paymentProcessorResultCodeTransferCache == null)
            paymentProcessorResultCodeTransferCache = new PaymentProcessorResultCodeTransferCache(userVisit, paymentControl);

        return paymentProcessorResultCodeTransferCache;
    }

    public PaymentProcessorResultCodeDescriptionTransferCache getPaymentProcessorResultCodeDescriptionTransferCache() {
        if(paymentProcessorResultCodeDescriptionTransferCache == null)
            paymentProcessorResultCodeDescriptionTransferCache = new PaymentProcessorResultCodeDescriptionTransferCache(userVisit, paymentControl);

        return paymentProcessorResultCodeDescriptionTransferCache;
    }

    public PaymentProcessorTypeCodeTypeTransferCache getPaymentProcessorTypeCodeTypeTransferCache() {
        if(paymentProcessorTypeCodeTypeTransferCache == null)
            paymentProcessorTypeCodeTypeTransferCache = new PaymentProcessorTypeCodeTypeTransferCache(userVisit, paymentControl);

        return paymentProcessorTypeCodeTypeTransferCache;
    }

    public PaymentProcessorTypeCodeTypeDescriptionTransferCache getPaymentProcessorTypeCodeTypeDescriptionTransferCache() {
        if(paymentProcessorTypeCodeTypeDescriptionTransferCache == null)
            paymentProcessorTypeCodeTypeDescriptionTransferCache = new PaymentProcessorTypeCodeTypeDescriptionTransferCache(userVisit, paymentControl);

        return paymentProcessorTypeCodeTypeDescriptionTransferCache;
    }

    public PaymentProcessorTypeCodeTransferCache getPaymentProcessorTypeCodeTransferCache() {
        if(paymentProcessorTypeCodeTransferCache == null)
            paymentProcessorTypeCodeTransferCache = new PaymentProcessorTypeCodeTransferCache(userVisit, paymentControl);

        return paymentProcessorTypeCodeTransferCache;
    }

    public PaymentProcessorTypeCodeDescriptionTransferCache getPaymentProcessorTypeCodeDescriptionTransferCache() {
        if(paymentProcessorTypeCodeDescriptionTransferCache == null)
            paymentProcessorTypeCodeDescriptionTransferCache = new PaymentProcessorTypeCodeDescriptionTransferCache(userVisit, paymentControl);

        return paymentProcessorTypeCodeDescriptionTransferCache;
    }

    public PaymentProcessorTypeActionTransferCache getPaymentProcessorTypeActionTransferCache() {
        if(paymentProcessorTypeActionTransferCache == null)
            paymentProcessorTypeActionTransferCache = new PaymentProcessorTypeActionTransferCache(userVisit, paymentControl);

        return paymentProcessorTypeActionTransferCache;
    }

    public PaymentProcessorActionTransferCache getPaymentProcessorActionTransferCache() {
        if(paymentProcessorActionTransferCache == null)
            paymentProcessorActionTransferCache = new PaymentProcessorActionTransferCache(userVisit, paymentControl);

        return paymentProcessorActionTransferCache;
    }

    public PaymentProcessorTransactionTransferCache getPaymentProcessorTransactionTransferCache() {
        if(paymentProcessorTransactionTransferCache == null)
            paymentProcessorTransactionTransferCache = new PaymentProcessorTransactionTransferCache(userVisit, paymentControl);

        return paymentProcessorTransactionTransferCache;
    }

    public PaymentProcessorTransactionCodeTransferCache getPaymentProcessorTransactionCodeTransferCache() {
        if(paymentProcessorTransactionCodeTransferCache == null)
            paymentProcessorTransactionCodeTransferCache = new PaymentProcessorTransactionCodeTransferCache(userVisit, paymentControl);

        return paymentProcessorTransactionCodeTransferCache;
    }

}
