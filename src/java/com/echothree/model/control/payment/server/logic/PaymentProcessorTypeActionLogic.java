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

package com.echothree.model.control.payment.server.logic;

import com.echothree.control.user.payment.common.spec.PaymentProcessorTypeActionUniversalSpec;
import com.echothree.model.control.core.common.ComponentVendors;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.model.control.core.common.exception.InvalidParameterCountException;
import com.echothree.model.control.core.server.logic.EntityInstanceLogic;
import com.echothree.model.control.payment.common.exception.DuplicatePaymentProcessorTypeActionException;
import com.echothree.model.control.payment.common.exception.UnknownDefaultPaymentProcessorTypeActionException;
import com.echothree.model.control.payment.common.exception.UnknownPaymentProcessorTypeActionException;
import com.echothree.model.control.payment.server.control.PaymentProcessorTypeActionControl;
import com.echothree.model.data.payment.server.entity.PaymentProcessorActionType;
import com.echothree.model.data.payment.server.entity.PaymentProcessorType;
import com.echothree.model.data.payment.server.entity.PaymentProcessorTypeAction;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.spi.CDI;

@ApplicationScoped
public class PaymentProcessorTypeActionLogic
    extends BaseLogic {

    protected PaymentProcessorTypeActionLogic() {
        super();
    }

    public static PaymentProcessorTypeActionLogic getInstance() {
        return CDI.current().select(PaymentProcessorTypeActionLogic.class).get();
    }

    public PaymentProcessorTypeAction createPaymentProcessorTypeAction(final ExecutionErrorAccumulator eea,
            final PaymentProcessorType paymentProcessorType, final PaymentProcessorActionType paymentProcessorActionType,
            final Boolean isDefault, final Integer sortOrder, final BasePK createdBy) {
        var paymentProcessorTypeActionControl = Session.getModelController(PaymentProcessorTypeActionControl.class);
        var paymentProcessorTypeAction = paymentProcessorTypeActionControl.getPaymentProcessorTypeAction(paymentProcessorType, paymentProcessorActionType);

        if(paymentProcessorTypeAction == null) {
            paymentProcessorTypeAction = paymentProcessorTypeActionControl.createPaymentProcessorTypeAction(paymentProcessorType,
                    paymentProcessorActionType, isDefault, sortOrder, createdBy);
        } else {
            handleExecutionError(DuplicatePaymentProcessorTypeActionException.class, eea, ExecutionErrors.DuplicatePaymentProcessorTypeAction.name(),
                    paymentProcessorType.getLastDetail().getPaymentProcessorTypeName(), paymentProcessorActionType.getLastDetail().getPaymentProcessorActionTypeName());
        }

        return paymentProcessorTypeAction;
    }

    public PaymentProcessorTypeAction createPaymentProcessorTypeAction(final ExecutionErrorAccumulator eea,
            final String paymentProcessorTypeName, final String paymentProcessorActionTypeName,
            final Boolean isDefault, final Integer sortOrder, final BasePK createdBy) {
        PaymentProcessorTypeAction paymentProcessorTypeAction = null;
        var paymentProcessorType = PaymentProcessorTypeLogic.getInstance().getPaymentProcessorTypeByName(eea,
                paymentProcessorTypeName);
        var paymentProcessorActionType = PaymentProcessorActionTypeLogic.getInstance().getPaymentProcessorActionTypeByName(eea,
                paymentProcessorActionTypeName);

        if(!eea.hasExecutionErrors()) {
            paymentProcessorTypeAction = createPaymentProcessorTypeAction(eea, paymentProcessorType,
                    paymentProcessorActionType, isDefault, sortOrder, createdBy);
        }

        return paymentProcessorTypeAction;
    }

    public PaymentProcessorTypeAction getPaymentProcessorTypeAction(final ExecutionErrorAccumulator eea,
            final PaymentProcessorType paymentProcessorType, final PaymentProcessorActionType paymentProcessorActionType,
            final EntityPermission entityPermission) {
        var paymentProcessorTypeActionControl = Session.getModelController(PaymentProcessorTypeActionControl.class);
        var paymentProcessorTypeAction = paymentProcessorTypeActionControl.getPaymentProcessorTypeAction(paymentProcessorType,
                paymentProcessorActionType, entityPermission);

        if(paymentProcessorTypeAction == null) {
            handleExecutionError(UnknownPaymentProcessorTypeActionException.class, eea, ExecutionErrors.UnknownPaymentProcessorTypeAction.name(),
                    paymentProcessorType.getLastDetail().getPaymentProcessorTypeName(), paymentProcessorActionType.getLastDetail().getPaymentProcessorActionTypeName());
        }

        return paymentProcessorTypeAction;
    }

    public PaymentProcessorTypeAction getPaymentProcessorTypeAction(final ExecutionErrorAccumulator eea,
            final PaymentProcessorType paymentProcessorType, final PaymentProcessorActionType paymentProcessorActionType) {
        return getPaymentProcessorTypeAction(eea, paymentProcessorType, paymentProcessorActionType, EntityPermission.READ_ONLY);
    }

    public PaymentProcessorTypeAction getPaymentProcessorTypeActionForUpdate(final ExecutionErrorAccumulator eea,
            final PaymentProcessorType paymentProcessorType, final PaymentProcessorActionType paymentProcessorActionType) {
        return getPaymentProcessorTypeAction(eea, paymentProcessorType, paymentProcessorActionType, EntityPermission.READ_WRITE);
    }

    public PaymentProcessorTypeAction getPaymentProcessorTypeActionByNames(final ExecutionErrorAccumulator eea,
            final String paymentProcessorTypeName, final String paymentProcessorActionTypeName,
            final EntityPermission entityPermission) {
        var paymentProcessorType = PaymentProcessorTypeLogic.getInstance().getPaymentProcessorTypeByName(eea,
                paymentProcessorTypeName);
        var paymentProcessorActionType = PaymentProcessorActionTypeLogic.getInstance().getPaymentProcessorActionTypeByName(eea,
                paymentProcessorActionTypeName);
        PaymentProcessorTypeAction paymentProcessorTypeAction = null;

        if(!eea.hasExecutionErrors()) {
            paymentProcessorTypeAction = getPaymentProcessorTypeAction(eea, paymentProcessorType, paymentProcessorActionType, entityPermission);
        }

        return paymentProcessorTypeAction;
    }

    public PaymentProcessorTypeAction getPaymentProcessorTypeActionByNames(final ExecutionErrorAccumulator eea,
            final String paymentProcessorTypeName, final String paymentProcessorActionTypeName) {
        return getPaymentProcessorTypeActionByNames(eea, paymentProcessorTypeName, paymentProcessorActionTypeName,
                EntityPermission.READ_ONLY);
    }

    public PaymentProcessorTypeAction getPaymentProcessorTypeActionByNamesForUpdate(final ExecutionErrorAccumulator eea,
            final String paymentProcessorTypeName, final String paymentProcessorActionTypeName) {
        return getPaymentProcessorTypeActionByNames(eea, paymentProcessorTypeName, paymentProcessorActionTypeName,
                EntityPermission.READ_WRITE);
    }

    public PaymentProcessorTypeAction getPaymentProcessorTypeActionByUniversalSpec(final ExecutionErrorAccumulator eea,
            final PaymentProcessorTypeActionUniversalSpec universalSpec, boolean allowDefault, final EntityPermission entityPermission) {
        PaymentProcessorTypeAction paymentProcessorTypeAction = null;
        var paymentProcessorTypeActionControl = Session.getModelController(PaymentProcessorTypeActionControl.class);
        var paymentProcessorTypeName = universalSpec.getPaymentProcessorTypeName();
        var paymentProcessorActionTypeName = universalSpec.getPaymentProcessorActionTypeName();
        var fullySpecifiedName = paymentProcessorTypeName != null && paymentProcessorActionTypeName != null;
        var parameterCount = (fullySpecifiedName ? 1 : 0) + EntityInstanceLogic.getInstance().countPossibleEntitySpecs(universalSpec);

        switch(parameterCount) {
            case 0 -> {
                if(allowDefault) {
                    if(paymentProcessorTypeName != null) {
                        var paymentProcessorType = PaymentProcessorTypeLogic.getInstance().getPaymentProcessorTypeByName(eea,
                                paymentProcessorTypeName);

                        if(!eea.hasExecutionErrors()) {
                            paymentProcessorTypeAction = paymentProcessorTypeActionControl.getDefaultPaymentProcessorTypeAction(paymentProcessorType, entityPermission);

                            if(paymentProcessorTypeAction == null) {
                                handleExecutionError(UnknownDefaultPaymentProcessorTypeActionException.class, eea, ExecutionErrors.UnknownDefaultPaymentProcessorTypeAction.name());
                            }
                        }
                    } else {
                        handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
                    }
                } else {
                    handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
                }
            }
            case 1 -> {
                if(fullySpecifiedName) {
                    var paymentProcessorType = PaymentProcessorTypeLogic.getInstance().getPaymentProcessorTypeByName(eea,
                            paymentProcessorTypeName);
                    var paymentProcessorActionType = PaymentProcessorActionTypeLogic.getInstance().getPaymentProcessorActionTypeByName(eea,
                            paymentProcessorActionTypeName);

                    if(!eea.hasExecutionErrors()) {
                        paymentProcessorTypeAction = getPaymentProcessorTypeAction(eea, paymentProcessorType,
                                paymentProcessorActionType, entityPermission);
                    }
                } else {
                    var entityInstance = EntityInstanceLogic.getInstance().getEntityInstance(eea, universalSpec,
                            ComponentVendors.ECHO_THREE.name(), EntityTypes.PaymentProcessorTypeAction.name());

                    if(!eea.hasExecutionErrors()) {
                        paymentProcessorTypeAction = paymentProcessorTypeActionControl.getPaymentProcessorTypeActionByEntityInstance(entityInstance, entityPermission);
                    }
                }
            }
            default ->
                    handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
        }

        return paymentProcessorTypeAction;
    }

    public PaymentProcessorTypeAction getPaymentProcessorTypeActionByUniversalSpec(final ExecutionErrorAccumulator eea,
            final PaymentProcessorTypeActionUniversalSpec universalSpec, boolean allowDefault) {
        return getPaymentProcessorTypeActionByUniversalSpec(eea, universalSpec, allowDefault, EntityPermission.READ_ONLY);
    }

    public PaymentProcessorTypeAction getPaymentProcessorTypeActionByUniversalSpecForUpdate(final ExecutionErrorAccumulator eea,
            final PaymentProcessorTypeActionUniversalSpec universalSpec, boolean allowDefault) {
        return getPaymentProcessorTypeActionByUniversalSpec(eea, universalSpec, allowDefault, EntityPermission.READ_WRITE);
    }

    public void deletePaymentProcessorTypeAction(final ExecutionErrorAccumulator eea, final PaymentProcessorTypeAction paymentProcessorTypeAction,
            final BasePK deletedBy) {
        var paymentProcessorTypeActionControl = Session.getModelController(PaymentProcessorTypeActionControl.class);

        paymentProcessorTypeActionControl.deletePaymentProcessorTypeAction(paymentProcessorTypeAction, deletedBy);
    }

}
