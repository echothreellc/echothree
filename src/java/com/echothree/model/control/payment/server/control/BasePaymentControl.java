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

package com.echothree.model.control.payment.server.control;

import com.echothree.model.control.payment.server.transfer.BillingAccountRoleTransferCache;
import com.echothree.model.control.payment.server.transfer.BillingAccountRoleTypeTransferCache;
import com.echothree.model.control.payment.server.transfer.BillingAccountTransferCache;
import com.echothree.model.control.payment.server.transfer.PartyPaymentMethodContactMechanismTransferCache;
import com.echothree.model.control.payment.server.transfer.PartyPaymentMethodTransferCache;
import com.echothree.model.control.payment.server.transfer.PaymentMethodDescriptionTransferCache;
import com.echothree.model.control.payment.server.transfer.PaymentMethodTransferCache;
import com.echothree.model.control.payment.server.transfer.PaymentMethodTypeDescriptionTransferCache;
import com.echothree.model.control.payment.server.transfer.PaymentMethodTypePartyTypeTransferCache;
import com.echothree.model.control.payment.server.transfer.PaymentMethodTypeTransferCache;
import com.echothree.model.control.payment.server.transfer.PaymentProcessorActionTransferCache;
import com.echothree.model.control.payment.server.transfer.PaymentProcessorActionTypeDescriptionTransferCache;
import com.echothree.model.control.payment.server.transfer.PaymentProcessorActionTypeTransferCache;
import com.echothree.model.control.payment.server.transfer.PaymentProcessorDescriptionTransferCache;
import com.echothree.model.control.payment.server.transfer.PaymentProcessorResultCodeDescriptionTransferCache;
import com.echothree.model.control.payment.server.transfer.PaymentProcessorResultCodeTransferCache;
import com.echothree.model.control.payment.server.transfer.PaymentProcessorTransactionCodeTransferCache;
import com.echothree.model.control.payment.server.transfer.PaymentProcessorTransactionTransferCache;
import com.echothree.model.control.payment.server.transfer.PaymentProcessorTransferCache;
import com.echothree.model.control.payment.server.transfer.PaymentProcessorTypeActionTransferCache;
import com.echothree.model.control.payment.server.transfer.PaymentProcessorTypeCodeDescriptionTransferCache;
import com.echothree.model.control.payment.server.transfer.PaymentProcessorTypeCodeTransferCache;
import com.echothree.model.control.payment.server.transfer.PaymentProcessorTypeCodeTypeDescriptionTransferCache;
import com.echothree.model.control.payment.server.transfer.PaymentProcessorTypeCodeTypeTransferCache;
import com.echothree.model.control.payment.server.transfer.PaymentProcessorTypeDescriptionTransferCache;
import com.echothree.model.control.payment.server.transfer.PaymentProcessorTypeTransferCache;
import com.echothree.util.server.control.BaseModelControl;
import javax.inject.Inject;

public abstract class BasePaymentControl
        extends BaseModelControl {

    /** Creates a new instance of BasePaymentControl */
    protected BasePaymentControl() {
        super();
    }
    
    // --------------------------------------------------------------------------------
    //   Payment Transfer Caches
    // --------------------------------------------------------------------------------

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

 }
