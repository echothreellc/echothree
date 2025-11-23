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

import com.echothree.control.user.payment.common.spec.PaymentProcessorTypeUniversalSpec;
import com.echothree.model.control.core.common.ComponentVendors;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.model.control.core.common.exception.InvalidParameterCountException;
import com.echothree.model.control.core.server.logic.EntityInstanceLogic;
import com.echothree.model.control.payment.common.exception.DuplicatePaymentProcessorTypeNameException;
import com.echothree.model.control.payment.common.exception.UnknownDefaultPaymentProcessorTypeException;
import com.echothree.model.control.payment.common.exception.UnknownPaymentProcessorTypeNameException;
import com.echothree.model.control.payment.server.control.PaymentProcessorTypeControl;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.payment.server.entity.PaymentProcessorType;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.spi.CDI;

@ApplicationScoped
public class PaymentProcessorTypeLogic
    extends BaseLogic {

    protected PaymentProcessorTypeLogic() {
        super();
    }

    public static PaymentProcessorTypeLogic getInstance() {
        return CDI.current().select(PaymentProcessorTypeLogic.class).get();
    }

    public PaymentProcessorType createPaymentProcessorType(final ExecutionErrorAccumulator eea, final String paymentProcessorTypeName,
            final Boolean isDefault, final Integer sortOrder, final Language language, final String description,
            final BasePK createdBy) {
        var paymentProcessorTypeControl = Session.getModelController(PaymentProcessorTypeControl.class);
        var paymentProcessorType = paymentProcessorTypeControl.getPaymentProcessorTypeByName(paymentProcessorTypeName);

        if(paymentProcessorType == null) {
            paymentProcessorType = paymentProcessorTypeControl.createPaymentProcessorType(paymentProcessorTypeName, isDefault, sortOrder, createdBy);

            if(description != null) {
                paymentProcessorTypeControl.createPaymentProcessorTypeDescription(paymentProcessorType, language, description, createdBy);
            }
        } else {
            handleExecutionError(DuplicatePaymentProcessorTypeNameException.class, eea, ExecutionErrors.DuplicatePaymentProcessorTypeName.name(), paymentProcessorTypeName);
        }

        return paymentProcessorType;
    }

    public PaymentProcessorType getPaymentProcessorTypeByName(final ExecutionErrorAccumulator eea, final String paymentProcessorTypeName,
            final EntityPermission entityPermission) {
        var paymentProcessorTypeControl = Session.getModelController(PaymentProcessorTypeControl.class);
        var paymentProcessorType = paymentProcessorTypeControl.getPaymentProcessorTypeByName(paymentProcessorTypeName, entityPermission);

        if(paymentProcessorType == null) {
            handleExecutionError(UnknownPaymentProcessorTypeNameException.class, eea, ExecutionErrors.UnknownPaymentProcessorTypeName.name(), paymentProcessorTypeName);
        }

        return paymentProcessorType;
    }

    public PaymentProcessorType getPaymentProcessorTypeByName(final ExecutionErrorAccumulator eea, final String paymentProcessorTypeName) {
        return getPaymentProcessorTypeByName(eea, paymentProcessorTypeName, EntityPermission.READ_ONLY);
    }

    public PaymentProcessorType getPaymentProcessorTypeByNameForUpdate(final ExecutionErrorAccumulator eea, final String paymentProcessorTypeName) {
        return getPaymentProcessorTypeByName(eea, paymentProcessorTypeName, EntityPermission.READ_WRITE);
    }

    public PaymentProcessorType getPaymentProcessorTypeByUniversalSpec(final ExecutionErrorAccumulator eea,
            final PaymentProcessorTypeUniversalSpec universalSpec, boolean allowDefault, final EntityPermission entityPermission) {
        PaymentProcessorType paymentProcessorType = null;
        var paymentProcessorTypeControl = Session.getModelController(PaymentProcessorTypeControl.class);
        var paymentProcessorTypeName = universalSpec.getPaymentProcessorTypeName();
        var parameterCount = (paymentProcessorTypeName == null ? 0 : 1) + EntityInstanceLogic.getInstance().countPossibleEntitySpecs(universalSpec);

        switch(parameterCount) {
            case 0 -> {
                if(allowDefault) {
                    paymentProcessorType = paymentProcessorTypeControl.getDefaultPaymentProcessorType(entityPermission);

                    if(paymentProcessorType == null) {
                        handleExecutionError(UnknownDefaultPaymentProcessorTypeException.class, eea, ExecutionErrors.UnknownDefaultPaymentProcessorType.name());
                    }
                } else {
                    handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
                }
            }
            case 1 -> {
                if(paymentProcessorTypeName == null) {
                    var entityInstance = EntityInstanceLogic.getInstance().getEntityInstance(eea, universalSpec,
                            ComponentVendors.ECHO_THREE.name(), EntityTypes.PaymentProcessorType.name());

                    if(!eea.hasExecutionErrors()) {
                        paymentProcessorType = paymentProcessorTypeControl.getPaymentProcessorTypeByEntityInstance(entityInstance, entityPermission);
                    }
                } else {
                    paymentProcessorType = getPaymentProcessorTypeByName(eea, paymentProcessorTypeName, entityPermission);
                }
            }
            default ->
                    handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
        }

        return paymentProcessorType;
    }

    public PaymentProcessorType getPaymentProcessorTypeByUniversalSpec(final ExecutionErrorAccumulator eea,
            final PaymentProcessorTypeUniversalSpec universalSpec, boolean allowDefault) {
        return getPaymentProcessorTypeByUniversalSpec(eea, universalSpec, allowDefault, EntityPermission.READ_ONLY);
    }

    public PaymentProcessorType getPaymentProcessorTypeByUniversalSpecForUpdate(final ExecutionErrorAccumulator eea,
            final PaymentProcessorTypeUniversalSpec universalSpec, boolean allowDefault) {
        return getPaymentProcessorTypeByUniversalSpec(eea, universalSpec, allowDefault, EntityPermission.READ_WRITE);
    }

    public void deletePaymentProcessorType(final ExecutionErrorAccumulator eea, final PaymentProcessorType paymentProcessorType,
            final BasePK deletedBy) {
        var paymentProcessorTypeControl = Session.getModelController(PaymentProcessorTypeControl.class);

        paymentProcessorTypeControl.deletePaymentProcessorType(paymentProcessorType, deletedBy);
    }
}
