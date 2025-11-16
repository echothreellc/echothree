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
import javax.enterprise.inject.spi.CDI;
import javax.enterprise.context.RequestScoped;

@RequestScoped
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
    protected PaymentTransferCaches() {
        super();
    }

    public PaymentMethodTypeTransferCache getPaymentMethodTypeTransferCache() {
        if(paymentMethodTypeTransferCache == null)
            paymentMethodTypeTransferCache = CDI.current().select(PaymentMethodTypeTransferCache.class).get();

        return paymentMethodTypeTransferCache;
    }

    public PaymentMethodTypeDescriptionTransferCache getPaymentMethodTypeDescriptionTransferCache() {
        if(paymentMethodTypeDescriptionTransferCache == null)
            paymentMethodTypeDescriptionTransferCache = CDI.current().select(PaymentMethodTypeDescriptionTransferCache.class).get();

        return paymentMethodTypeDescriptionTransferCache;
    }

    public PaymentMethodTypePartyTypeTransferCache getPaymentMethodTypePartyTypeTransferCache() {
        if(paymentMethodTypePartyTypeTransferCache == null)
            paymentMethodTypePartyTypeTransferCache = CDI.current().select(PaymentMethodTypePartyTypeTransferCache.class).get();

        return paymentMethodTypePartyTypeTransferCache;
    }

    public PaymentMethodTransferCache getPaymentMethodTransferCache() {
        if(paymentMethodTransferCache == null)
            paymentMethodTransferCache = CDI.current().select(PaymentMethodTransferCache.class).get();
        
        return paymentMethodTransferCache;
    }
    
    public PaymentMethodDescriptionTransferCache getPaymentMethodDescriptionTransferCache() {
        if(paymentMethodDescriptionTransferCache == null)
            paymentMethodDescriptionTransferCache = CDI.current().select(PaymentMethodDescriptionTransferCache.class).get();
        
        return paymentMethodDescriptionTransferCache;
    }
    
    public BillingAccountTransferCache getBillingAccountTransferCache() {
        if(billingAccountTransferCache == null)
            billingAccountTransferCache = CDI.current().select(BillingAccountTransferCache.class).get();
        
        return billingAccountTransferCache;
    }
    
    public BillingAccountRoleTypeTransferCache getBillingAccountRoleTypeTransferCache() {
        if(billingAccountRoleTypeTransferCache == null)
            billingAccountRoleTypeTransferCache = CDI.current().select(BillingAccountRoleTypeTransferCache.class).get();
        
        return billingAccountRoleTypeTransferCache;
    }
    
    public BillingAccountRoleTransferCache getBillingAccountRoleTransferCache() {
        if(billingAccountRoleTransferCache == null)
            billingAccountRoleTransferCache = CDI.current().select(BillingAccountRoleTransferCache.class).get();
        
        return billingAccountRoleTransferCache;
    }
    
    public PartyPaymentMethodTransferCache getPartyPaymentMethodTransferCache() {
        if(partyPaymentMethodTransferCache == null)
            partyPaymentMethodTransferCache = CDI.current().select(PartyPaymentMethodTransferCache.class).get();
        
        return partyPaymentMethodTransferCache;
    }

    public PaymentProcessorTypeTransferCache getPaymentProcessorTypeTransferCache() {
        if(paymentProcessorTypeTransferCache == null)
            paymentProcessorTypeTransferCache = CDI.current().select(PaymentProcessorTypeTransferCache.class).get();

        return paymentProcessorTypeTransferCache;
    }

    public PaymentProcessorTypeDescriptionTransferCache getPaymentProcessorTypeDescriptionTransferCache() {
        if(paymentProcessorTypeDescriptionTransferCache == null)
            paymentProcessorTypeDescriptionTransferCache = CDI.current().select(PaymentProcessorTypeDescriptionTransferCache.class).get();

        return paymentProcessorTypeDescriptionTransferCache;
    }

    public PaymentProcessorTransferCache getPaymentProcessorTransferCache() {
        if(paymentProcessorTransferCache == null)
            paymentProcessorTransferCache = CDI.current().select(PaymentProcessorTransferCache.class).get();
        
        return paymentProcessorTransferCache;
    }
    
    public PaymentProcessorDescriptionTransferCache getPaymentProcessorDescriptionTransferCache() {
        if(paymentProcessorDescriptionTransferCache == null)
            paymentProcessorDescriptionTransferCache = CDI.current().select(PaymentProcessorDescriptionTransferCache.class).get();
        
        return paymentProcessorDescriptionTransferCache;
    }
    
    public PartyPaymentMethodContactMechanismTransferCache getPartyPaymentMethodContactMechanismTransferCache() {
        if(partyPaymentMethodContactMechanismTransferCache == null)
            partyPaymentMethodContactMechanismTransferCache = CDI.current().select(PartyPaymentMethodContactMechanismTransferCache.class).get();
        
        return partyPaymentMethodContactMechanismTransferCache;
    }

    public PaymentProcessorActionTypeTransferCache getPaymentProcessorActionTypeTransferCache() {
        if(paymentProcessorActionTypeTransferCache == null)
            paymentProcessorActionTypeTransferCache = CDI.current().select(PaymentProcessorActionTypeTransferCache.class).get();

        return paymentProcessorActionTypeTransferCache;
    }

    public PaymentProcessorActionTypeDescriptionTransferCache getPaymentProcessorActionTypeDescriptionTransferCache() {
        if(paymentProcessorActionTypeDescriptionTransferCache == null)
            paymentProcessorActionTypeDescriptionTransferCache = CDI.current().select(PaymentProcessorActionTypeDescriptionTransferCache.class).get();

        return paymentProcessorActionTypeDescriptionTransferCache;
    }

    public PaymentProcessorResultCodeTransferCache getPaymentProcessorResultCodeTransferCache() {
        if(paymentProcessorResultCodeTransferCache == null)
            paymentProcessorResultCodeTransferCache = CDI.current().select(PaymentProcessorResultCodeTransferCache.class).get();

        return paymentProcessorResultCodeTransferCache;
    }

    public PaymentProcessorResultCodeDescriptionTransferCache getPaymentProcessorResultCodeDescriptionTransferCache() {
        if(paymentProcessorResultCodeDescriptionTransferCache == null)
            paymentProcessorResultCodeDescriptionTransferCache = CDI.current().select(PaymentProcessorResultCodeDescriptionTransferCache.class).get();

        return paymentProcessorResultCodeDescriptionTransferCache;
    }

    public PaymentProcessorTypeCodeTypeTransferCache getPaymentProcessorTypeCodeTypeTransferCache() {
        if(paymentProcessorTypeCodeTypeTransferCache == null)
            paymentProcessorTypeCodeTypeTransferCache = CDI.current().select(PaymentProcessorTypeCodeTypeTransferCache.class).get();

        return paymentProcessorTypeCodeTypeTransferCache;
    }

    public PaymentProcessorTypeCodeTypeDescriptionTransferCache getPaymentProcessorTypeCodeTypeDescriptionTransferCache() {
        if(paymentProcessorTypeCodeTypeDescriptionTransferCache == null)
            paymentProcessorTypeCodeTypeDescriptionTransferCache = CDI.current().select(PaymentProcessorTypeCodeTypeDescriptionTransferCache.class).get();

        return paymentProcessorTypeCodeTypeDescriptionTransferCache;
    }

    public PaymentProcessorTypeCodeTransferCache getPaymentProcessorTypeCodeTransferCache() {
        if(paymentProcessorTypeCodeTransferCache == null)
            paymentProcessorTypeCodeTransferCache = CDI.current().select(PaymentProcessorTypeCodeTransferCache.class).get();

        return paymentProcessorTypeCodeTransferCache;
    }

    public PaymentProcessorTypeCodeDescriptionTransferCache getPaymentProcessorTypeCodeDescriptionTransferCache() {
        if(paymentProcessorTypeCodeDescriptionTransferCache == null)
            paymentProcessorTypeCodeDescriptionTransferCache = CDI.current().select(PaymentProcessorTypeCodeDescriptionTransferCache.class).get();

        return paymentProcessorTypeCodeDescriptionTransferCache;
    }

    public PaymentProcessorTypeActionTransferCache getPaymentProcessorTypeActionTransferCache() {
        if(paymentProcessorTypeActionTransferCache == null)
            paymentProcessorTypeActionTransferCache = CDI.current().select(PaymentProcessorTypeActionTransferCache.class).get();

        return paymentProcessorTypeActionTransferCache;
    }

    public PaymentProcessorActionTransferCache getPaymentProcessorActionTransferCache() {
        if(paymentProcessorActionTransferCache == null)
            paymentProcessorActionTransferCache = CDI.current().select(PaymentProcessorActionTransferCache.class).get();

        return paymentProcessorActionTransferCache;
    }

    public PaymentProcessorTransactionTransferCache getPaymentProcessorTransactionTransferCache() {
        if(paymentProcessorTransactionTransferCache == null)
            paymentProcessorTransactionTransferCache = CDI.current().select(PaymentProcessorTransactionTransferCache.class).get();

        return paymentProcessorTransactionTransferCache;
    }

    public PaymentProcessorTransactionCodeTransferCache getPaymentProcessorTransactionCodeTransferCache() {
        if(paymentProcessorTransactionCodeTransferCache == null)
            paymentProcessorTransactionCodeTransferCache = CDI.current().select(PaymentProcessorTransactionCodeTransferCache.class).get();

        return paymentProcessorTransactionCodeTransferCache;
    }

}
