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

import com.echothree.model.control.payment.common.exception.DuplicatePaymentProcessorActionException;
import com.echothree.model.control.payment.common.exception.UnknownPaymentProcessorActionException;
import com.echothree.model.control.payment.server.control.PaymentProcessorActionControl;
import com.echothree.model.data.payment.server.entity.PaymentProcessorAction;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.EntityPermission;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.spi.CDI;
import javax.inject.Inject;

@ApplicationScoped
public class PaymentProcessorActionLogic
    extends BaseLogic {

    @Inject
    PaymentProcessorActionControl paymentProcessorActionControl;

    @Inject
    PaymentProcessorActionTypeLogic paymentProcessorActionTypeLogic;

    @Inject
    PaymentProcessorLogic paymentProcessorLogic;

    @Inject
    PaymentProcessorTypeActionLogic paymentProcessorTypeActionLogic;

    protected PaymentProcessorActionLogic() {
        super();
    }

    public static PaymentProcessorActionLogic getInstance() {
        return CDI.current().select(PaymentProcessorActionLogic.class).get();
    }

    public PaymentProcessorAction createPaymentProcessorAction(final ExecutionErrorAccumulator eea, final String paymentProcessorName,
            final String paymentProcessorActionTypeName, final BasePK createdBy) {
        var paymentProcessor = paymentProcessorLogic.getPaymentProcessorByName(eea, paymentProcessorName);
        var paymentProcessorActionType = paymentProcessorActionTypeLogic.getPaymentProcessorActionTypeByName(eea, paymentProcessorActionTypeName);
        PaymentProcessorAction paymentProcessorAction = null;

        if(eea == null || !eea.hasExecutionErrors()) {
            var paymentProcessorTypeAction = paymentProcessorTypeActionLogic.getPaymentProcessorTypeAction(eea,
                    paymentProcessor.getLastDetail().getPaymentProcessorType(), paymentProcessorActionType);

            if(eea == null || !eea.hasExecutionErrors()) {
                paymentProcessorAction = paymentProcessorActionControl.getPaymentProcessorAction(paymentProcessor, paymentProcessorTypeAction);

                if(paymentProcessorAction == null) {
                    paymentProcessorAction = paymentProcessorActionControl.createPaymentProcessorAction(paymentProcessor, paymentProcessorTypeAction, createdBy);

                } else {
                    handleExecutionError(DuplicatePaymentProcessorActionException.class, eea, ExecutionErrors.DuplicatePaymentProcessorAction.name(),
                            paymentProcessor.getLastDetail().getPaymentProcessorName(), paymentProcessorActionType.getLastDetail().getPaymentProcessorActionTypeName());
                }
            }
        }

        return paymentProcessorAction;
    }

    public PaymentProcessorAction getPaymentProcessorActionByNames(final ExecutionErrorAccumulator eea, final String paymentProcessorName,
            final String paymentProcessorActionTypeName, final EntityPermission entityPermission) {
        var paymentProcessor = paymentProcessorLogic.getPaymentProcessorByName(eea, paymentProcessorName);
        var paymentProcessorActionType = paymentProcessorActionTypeLogic.getPaymentProcessorActionTypeByName(eea, paymentProcessorActionTypeName);
        PaymentProcessorAction paymentProcessorAction = null;

        if(eea == null || !eea.hasExecutionErrors()) {
            var paymentProcessorTypeAction = paymentProcessorTypeActionLogic.getPaymentProcessorTypeAction(eea,
                    paymentProcessor.getLastDetail().getPaymentProcessorType(), paymentProcessorActionType);

            if(eea == null || !eea.hasExecutionErrors()) {
                paymentProcessorAction = paymentProcessorActionControl.getPaymentProcessorAction(paymentProcessor, paymentProcessorTypeAction, entityPermission);

                if(paymentProcessorAction == null) {
                    handleExecutionError(UnknownPaymentProcessorActionException.class, eea, ExecutionErrors.UnknownPaymentProcessorAction.name(),
                            paymentProcessor.getLastDetail().getPaymentProcessorName(), paymentProcessorActionType.getLastDetail().getPaymentProcessorActionTypeName());
                }
            }
        }

        return paymentProcessorAction;
    }

    public PaymentProcessorAction getPaymentProcessorActionByNames(final ExecutionErrorAccumulator eea, final String paymentProcessorName,
            final String paymentProcessorActionTypeName) {
        return getPaymentProcessorActionByNames(eea, paymentProcessorName, paymentProcessorActionTypeName, EntityPermission.READ_ONLY);
    }

    public PaymentProcessorAction getPaymentProcessorActionByNamesForUpdate(final ExecutionErrorAccumulator eea, final String paymentProcessorName,
            final String paymentProcessorActionTypeName) {
        return getPaymentProcessorActionByNames(eea, paymentProcessorName, paymentProcessorActionTypeName, EntityPermission.READ_WRITE);
    }

    public void deletePaymentProcessorAction(final ExecutionErrorAccumulator eea, final PaymentProcessorAction paymentProcessorAction,
            final BasePK deletedBy) {
        paymentProcessorActionControl.deletePaymentProcessorAction(paymentProcessorAction, deletedBy);
    }
}
