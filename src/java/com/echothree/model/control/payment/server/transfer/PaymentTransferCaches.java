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

import com.echothree.model.control.payment.server.PaymentControl;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.transfer.BaseTransferCaches;

public class PaymentTransferCaches
        extends BaseTransferCaches {
    
    protected PaymentControl paymentControl;
    
    protected PaymentMethodTypeTransferCache paymentMethodTypeTransferCache;
    protected PaymentMethodTypePartyTypeTransferCache paymentMethodTypePartyTypeTransferCache;
    protected PaymentMethodTransferCache paymentMethodTransferCache;
    protected PaymentMethodDescriptionTransferCache paymentMethodDescriptionTransferCache;
    protected BillingAccountTransferCache billingAccountTransferCache;
    protected BillingAccountRoleTypeTransferCache billingAccountRoleTypeTransferCache;
    protected BillingAccountRoleTransferCache billingAccountRoleTransferCache;
    protected PartyPaymentMethodTransferCache partyPaymentMethodTransferCache;
    protected PaymentProcessorTypeTransferCache paymentProcessorTypeTransferCache;
    protected PaymentProcessorTransferCache paymentProcessorTransferCache;
    protected PaymentProcessorDescriptionTransferCache paymentProcessorDescriptionTransferCache;
    protected PartyPaymentMethodContactMechanismTransferCache partyPaymentMethodContactMechanismTransferCache;
    
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
    
}
