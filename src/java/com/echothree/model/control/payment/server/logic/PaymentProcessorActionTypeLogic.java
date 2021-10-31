// --------------------------------------------------------------------------------
// Copyright 2002-2021 Echo Three, LLC
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

import com.echothree.control.user.payment.common.spec.PaymentProcessorActionTypeUniversalSpec;
import com.echothree.model.control.payment.common.exception.DuplicatePaymentProcessorActionTypeNameException;
import com.echothree.model.control.payment.common.exception.UnknownPaymentProcessorActionTypeNameException;
import com.echothree.model.control.payment.common.exception.UnknownDefaultPaymentProcessorActionTypeException;
import com.echothree.model.control.payment.server.control.PaymentProcessorActionTypeControl;
import com.echothree.model.control.core.common.ComponentVendors;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.model.control.core.common.exception.InvalidParameterCountException;
import com.echothree.model.control.core.server.logic.EntityInstanceLogic;
import com.echothree.model.data.payment.server.entity.PaymentProcessorActionType;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;

public class PaymentProcessorActionTypeLogic
    extends BaseLogic {
    
    private PaymentProcessorActionTypeLogic() {
        super();
    }
    
    private static class PaymentProcessorActionTypeLogicHolder {
        static PaymentProcessorActionTypeLogic instance = new PaymentProcessorActionTypeLogic();
    }
    
    public static PaymentProcessorActionTypeLogic getInstance() {
        return PaymentProcessorActionTypeLogicHolder.instance;
    }

    public PaymentProcessorActionType createPaymentProcessorActionType(final ExecutionErrorAccumulator eea, final String paymentProcessorActionTypeName,
            final Boolean isDefault, final Integer sortOrder, final Language language, final String description,
            final BasePK createdBy) {
        var paymentProcessorActionTypeControl = Session.getModelController(PaymentProcessorActionTypeControl.class);
        PaymentProcessorActionType paymentProcessorActionType = paymentProcessorActionTypeControl.getPaymentProcessorActionTypeByName(paymentProcessorActionTypeName);

        if(paymentProcessorActionType == null) {
            paymentProcessorActionType = paymentProcessorActionTypeControl.createPaymentProcessorActionType(paymentProcessorActionTypeName, isDefault, sortOrder, createdBy);

            if(description != null) {
                paymentProcessorActionTypeControl.createPaymentProcessorActionTypeDescription(paymentProcessorActionType, language, description, createdBy);
            }
        } else {
            handleExecutionError(DuplicatePaymentProcessorActionTypeNameException.class, eea, ExecutionErrors.DuplicatePaymentProcessorActionTypeName.name(), paymentProcessorActionTypeName);
        }

        return paymentProcessorActionType;
    }

    public PaymentProcessorActionType getPaymentProcessorActionTypeByName(final ExecutionErrorAccumulator eea, final String paymentProcessorActionTypeName,
            final EntityPermission entityPermission) {
        var paymentProcessorActionTypeControl = Session.getModelController(PaymentProcessorActionTypeControl.class);
        PaymentProcessorActionType paymentProcessorActionType = paymentProcessorActionTypeControl.getPaymentProcessorActionTypeByName(paymentProcessorActionTypeName, entityPermission);

        if(paymentProcessorActionType == null) {
            handleExecutionError(UnknownPaymentProcessorActionTypeNameException.class, eea, ExecutionErrors.UnknownPaymentProcessorActionTypeName.name(), paymentProcessorActionTypeName);
        }

        return paymentProcessorActionType;
    }

    public PaymentProcessorActionType getPaymentProcessorActionTypeByName(final ExecutionErrorAccumulator eea, final String paymentProcessorActionTypeName) {
        return getPaymentProcessorActionTypeByName(eea, paymentProcessorActionTypeName, EntityPermission.READ_ONLY);
    }

    public PaymentProcessorActionType getPaymentProcessorActionTypeByNameForUpdate(final ExecutionErrorAccumulator eea, final String paymentProcessorActionTypeName) {
        return getPaymentProcessorActionTypeByName(eea, paymentProcessorActionTypeName, EntityPermission.READ_WRITE);
    }

    public PaymentProcessorActionType getPaymentProcessorActionTypeByUniversalSpec(final ExecutionErrorAccumulator eea,
            final PaymentProcessorActionTypeUniversalSpec universalSpec, boolean allowDefault, final EntityPermission entityPermission) {
        PaymentProcessorActionType paymentProcessorActionType = null;
        var paymentProcessorActionTypeControl = Session.getModelController(PaymentProcessorActionTypeControl.class);
        String paymentProcessorActionTypeName = universalSpec.getPaymentProcessorActionTypeName();
        var parameterCount = (paymentProcessorActionTypeName == null ? 0 : 1) + EntityInstanceLogic.getInstance().countPossibleEntitySpecs(universalSpec);

        switch(parameterCount) {
            case 0:
                if(allowDefault) {
                    paymentProcessorActionType = paymentProcessorActionTypeControl.getDefaultPaymentProcessorActionType(entityPermission);

                    if(paymentProcessorActionType == null) {
                        handleExecutionError(UnknownDefaultPaymentProcessorActionTypeException.class, eea, ExecutionErrors.UnknownDefaultPaymentProcessorActionType.name());
                    }
                } else {
                    handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
                }
                break;
            case 1:
                if(paymentProcessorActionTypeName == null) {
                    var entityInstance = EntityInstanceLogic.getInstance().getEntityInstance(eea, universalSpec,
                            ComponentVendors.ECHOTHREE.name(), EntityTypes.PaymentProcessorActionType.name());

                    if(!eea.hasExecutionErrors()) {
                        paymentProcessorActionType = paymentProcessorActionTypeControl.getPaymentProcessorActionTypeByEntityInstance(entityInstance, entityPermission);
                    }
                } else {
                    paymentProcessorActionType = getPaymentProcessorActionTypeByName(eea, paymentProcessorActionTypeName, entityPermission);
                }
                break;
            default:
                handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
                break;
        }

        return paymentProcessorActionType;
    }

    public PaymentProcessorActionType getPaymentProcessorActionTypeByUniversalSpec(final ExecutionErrorAccumulator eea,
            final PaymentProcessorActionTypeUniversalSpec universalSpec, boolean allowDefault) {
        return getPaymentProcessorActionTypeByUniversalSpec(eea, universalSpec, allowDefault, EntityPermission.READ_ONLY);
    }

    public PaymentProcessorActionType getPaymentProcessorActionTypeByUniversalSpecForUpdate(final ExecutionErrorAccumulator eea,
            final PaymentProcessorActionTypeUniversalSpec universalSpec, boolean allowDefault) {
        return getPaymentProcessorActionTypeByUniversalSpec(eea, universalSpec, allowDefault, EntityPermission.READ_WRITE);
    }

    public void deletePaymentProcessorActionType(final ExecutionErrorAccumulator eea, final PaymentProcessorActionType paymentProcessorActionType,
            final BasePK deletedBy) {
        var paymentProcessorActionTypeControl = Session.getModelController(PaymentProcessorActionTypeControl.class);

        paymentProcessorActionTypeControl.deletePaymentProcessorActionType(paymentProcessorActionType, deletedBy);
    }
}
