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

package com.echothree.model.control.payment.server.logic;

import com.echothree.control.user.payment.common.spec.PaymentProcessorTransactionUniversalSpec;
import com.echothree.model.control.core.common.ComponentVendors;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.model.control.core.common.exception.InvalidParameterCountException;
import com.echothree.model.control.core.server.logic.EntityInstanceLogic;
import com.echothree.model.control.payment.common.exception.DuplicatePaymentProcessorTransactionNameException;
import com.echothree.model.control.payment.common.exception.UnknownPaymentProcessorTransactionNameException;
import com.echothree.model.control.payment.server.control.PaymentProcessorTransactionControl;
import com.echothree.model.control.sequence.common.SequenceTypes;
import com.echothree.model.control.sequence.server.logic.SequenceGeneratorLogic;
import com.echothree.model.data.payment.server.entity.PaymentProcessor;
import com.echothree.model.data.payment.server.entity.PaymentProcessorActionType;
import com.echothree.model.data.payment.server.entity.PaymentProcessorResultCode;
import com.echothree.model.data.payment.server.entity.PaymentProcessorTransaction;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.spi.CDI;

@ApplicationScoped
public class PaymentProcessorTransactionLogic
    extends BaseLogic {

    protected PaymentProcessorTransactionLogic() {
        super();
    }

    public static PaymentProcessorTransactionLogic getInstance() {
        return CDI.current().select(PaymentProcessorTransactionLogic.class).get();
    }

    public PaymentProcessorTransaction createPaymentProcessorTransaction(final ExecutionErrorAccumulator eea, String paymentProcessorTransactionName,
            final PaymentProcessor paymentProcessor, final PaymentProcessorActionType paymentProcessorActionType,
            final PaymentProcessorResultCode paymentProcessorResultCode, final BasePK createdBy) {
        PaymentProcessorTransaction paymentProcessorTransaction = null;

        if(paymentProcessorTransactionName == null) {
            paymentProcessorTransactionName = SequenceGeneratorLogic.getInstance().getNextSequenceValue(eea, SequenceTypes.PAYMENT_PROCESSOR_TRANSACTION.name());
        }

        if(!eea.hasExecutionErrors()) {
            var paymentProcessorTransactionControl = Session.getModelController(PaymentProcessorTransactionControl.class);

            paymentProcessorTransaction = paymentProcessorTransactionControl.getPaymentProcessorTransactionByName(paymentProcessorTransactionName);
            if(paymentProcessorTransaction == null) {
                paymentProcessorTransaction = paymentProcessorTransactionControl.createPaymentProcessorTransaction(paymentProcessorTransactionName,
                        paymentProcessor, paymentProcessorActionType, paymentProcessorResultCode, createdBy);
            } else {
                handleExecutionError(DuplicatePaymentProcessorTransactionNameException.class, eea, ExecutionErrors.DuplicatePaymentProcessorTransactionName.name(), paymentProcessorTransactionName);
            }
        }

        return paymentProcessorTransaction;
    }

    public PaymentProcessorTransaction getPaymentProcessorTransactionByName(final ExecutionErrorAccumulator eea, final String paymentProcessorTransactionName,
            final EntityPermission entityPermission) {
        var paymentProcessorTransactionControl = Session.getModelController(PaymentProcessorTransactionControl.class);
        var paymentProcessorTransaction = paymentProcessorTransactionControl.getPaymentProcessorTransactionByName(paymentProcessorTransactionName, entityPermission);

        if(paymentProcessorTransaction == null) {
            handleExecutionError(UnknownPaymentProcessorTransactionNameException.class, eea, ExecutionErrors.UnknownPaymentProcessorTransactionName.name(), paymentProcessorTransactionName);
        }

        return paymentProcessorTransaction;
    }

    public PaymentProcessorTransaction getPaymentProcessorTransactionByName(final ExecutionErrorAccumulator eea, final String paymentProcessorTransactionName) {
        return getPaymentProcessorTransactionByName(eea, paymentProcessorTransactionName, EntityPermission.READ_ONLY);
    }

    public PaymentProcessorTransaction getPaymentProcessorTransactionByNameForUpdate(final ExecutionErrorAccumulator eea, final String paymentProcessorTransactionName) {
        return getPaymentProcessorTransactionByName(eea, paymentProcessorTransactionName, EntityPermission.READ_WRITE);
    }

    public PaymentProcessorTransaction getPaymentProcessorTransactionByUniversalSpec(final ExecutionErrorAccumulator eea,
            final PaymentProcessorTransactionUniversalSpec universalSpec, final EntityPermission entityPermission) {
        PaymentProcessorTransaction paymentProcessorTransaction = null;
        var paymentProcessorTransactionControl = Session.getModelController(PaymentProcessorTransactionControl.class);
        var paymentProcessorTransactionName = universalSpec.getPaymentProcessorTransactionName();
        var parameterCount = (paymentProcessorTransactionName == null ? 0 : 1) + EntityInstanceLogic.getInstance().countPossibleEntitySpecs(universalSpec);

        switch(parameterCount) {
            case 1 -> {
                if(paymentProcessorTransactionName == null) {
                    var entityInstance = EntityInstanceLogic.getInstance().getEntityInstance(eea, universalSpec,
                            ComponentVendors.ECHO_THREE.name(), EntityTypes.PaymentProcessorTransaction.name());

                    if(!eea.hasExecutionErrors()) {
                        paymentProcessorTransaction = paymentProcessorTransactionControl.getPaymentProcessorTransactionByEntityInstance(entityInstance, entityPermission);
                    }
                } else {
                    paymentProcessorTransaction = getPaymentProcessorTransactionByName(eea, paymentProcessorTransactionName, entityPermission);
                }
            }
            default ->
                    handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
        }

        return paymentProcessorTransaction;
    }

    public PaymentProcessorTransaction getPaymentProcessorTransactionByUniversalSpec(final ExecutionErrorAccumulator eea,
            final PaymentProcessorTransactionUniversalSpec universalSpec) {
        return getPaymentProcessorTransactionByUniversalSpec(eea, universalSpec, EntityPermission.READ_ONLY);
    }

    public PaymentProcessorTransaction getPaymentProcessorTransactionByUniversalSpecForUpdate(final ExecutionErrorAccumulator eea,
            final PaymentProcessorTransactionUniversalSpec universalSpec) {
        return getPaymentProcessorTransactionByUniversalSpec(eea, universalSpec, EntityPermission.READ_WRITE);
    }

    public void deletePaymentProcessorTransaction(final ExecutionErrorAccumulator eea, final PaymentProcessorTransaction paymentProcessorTransaction,
            final BasePK deletedBy) {
        var paymentProcessorTransactionControl = Session.getModelController(PaymentProcessorTransactionControl.class);

        paymentProcessorTransactionControl.deletePaymentProcessorTransaction(paymentProcessorTransaction, deletedBy);
    }
}
