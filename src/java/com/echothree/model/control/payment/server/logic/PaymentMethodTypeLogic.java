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

import com.echothree.control.user.payment.common.spec.PaymentMethodTypeUniversalSpec;
import com.echothree.model.control.core.common.ComponentVendors;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.model.control.core.common.exception.InvalidParameterCountException;
import com.echothree.model.control.core.server.logic.EntityInstanceLogic;
import com.echothree.model.control.payment.common.exception.DuplicatePaymentMethodTypeNameException;
import com.echothree.model.control.payment.common.exception.UnknownDefaultPaymentMethodTypeException;
import com.echothree.model.control.payment.common.exception.UnknownPaymentMethodTypeNameException;
import com.echothree.model.control.payment.server.control.PaymentMethodTypeControl;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.payment.server.entity.PaymentMethodType;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.spi.CDI;

@ApplicationScoped
public class PaymentMethodTypeLogic
    extends BaseLogic {

    protected PaymentMethodTypeLogic() {
        super();
    }

    public static PaymentMethodTypeLogic getInstance() {
        return CDI.current().select(PaymentMethodTypeLogic.class).get();
    }

    public PaymentMethodType createPaymentMethodType(final ExecutionErrorAccumulator eea, final String paymentMethodTypeName,
            final Boolean isDefault, final Integer sortOrder, final Language language, final String description,
            final BasePK createdBy) {
        var paymentMethodTypeControl = Session.getModelController(PaymentMethodTypeControl.class);
        var paymentMethodType = paymentMethodTypeControl.getPaymentMethodTypeByName(paymentMethodTypeName);

        if(paymentMethodType == null) {
            paymentMethodType = paymentMethodTypeControl.createPaymentMethodType(paymentMethodTypeName, isDefault, sortOrder, createdBy);

            if(description != null) {
                paymentMethodTypeControl.createPaymentMethodTypeDescription(paymentMethodType, language, description, createdBy);
            }
        } else {
            handleExecutionError(DuplicatePaymentMethodTypeNameException.class, eea, ExecutionErrors.DuplicatePaymentMethodTypeName.name(), paymentMethodTypeName);
        }

        return paymentMethodType;
    }

    public PaymentMethodType getPaymentMethodTypeByName(final ExecutionErrorAccumulator eea, final String paymentMethodTypeName,
            final EntityPermission entityPermission) {
        var paymentMethodTypeControl = Session.getModelController(PaymentMethodTypeControl.class);
        var paymentMethodType = paymentMethodTypeControl.getPaymentMethodTypeByName(paymentMethodTypeName, entityPermission);

        if(paymentMethodType == null) {
            handleExecutionError(UnknownPaymentMethodTypeNameException.class, eea, ExecutionErrors.UnknownPaymentMethodTypeName.name(), paymentMethodTypeName);
        }

        return paymentMethodType;
    }

    public PaymentMethodType getPaymentMethodTypeByName(final ExecutionErrorAccumulator eea, final String paymentMethodTypeName) {
        return getPaymentMethodTypeByName(eea, paymentMethodTypeName, EntityPermission.READ_ONLY);
    }

    public PaymentMethodType getPaymentMethodTypeByNameForUpdate(final ExecutionErrorAccumulator eea, final String paymentMethodTypeName) {
        return getPaymentMethodTypeByName(eea, paymentMethodTypeName, EntityPermission.READ_WRITE);
    }

    public PaymentMethodType getPaymentMethodTypeByUniversalSpec(final ExecutionErrorAccumulator eea,
            final PaymentMethodTypeUniversalSpec universalSpec, boolean allowDefault, final EntityPermission entityPermission) {
        PaymentMethodType paymentMethodType = null;
        var paymentMethodTypeControl = Session.getModelController(PaymentMethodTypeControl.class);
        var paymentMethodTypeName = universalSpec.getPaymentMethodTypeName();
        var parameterCount = (paymentMethodTypeName == null ? 0 : 1) + EntityInstanceLogic.getInstance().countPossibleEntitySpecs(universalSpec);

        switch(parameterCount) {
            case 0:
                if(allowDefault) {
                    paymentMethodType = paymentMethodTypeControl.getDefaultPaymentMethodType(entityPermission);

                    if(paymentMethodType == null) {
                        handleExecutionError(UnknownDefaultPaymentMethodTypeException.class, eea, ExecutionErrors.UnknownDefaultPaymentMethodType.name());
                    }
                } else {
                    handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
                }
                break;
            case 1:
                if(paymentMethodTypeName == null) {
                    var entityInstance = EntityInstanceLogic.getInstance().getEntityInstance(eea, universalSpec,
                            ComponentVendors.ECHO_THREE.name(), EntityTypes.PaymentMethodType.name());

                    if(!eea.hasExecutionErrors()) {
                        paymentMethodType = paymentMethodTypeControl.getPaymentMethodTypeByEntityInstance(entityInstance, entityPermission);
                    }
                } else {
                    paymentMethodType = getPaymentMethodTypeByName(eea, paymentMethodTypeName, entityPermission);
                }
                break;
            default:
                handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
                break;
        }

        return paymentMethodType;
    }

    public PaymentMethodType getPaymentMethodTypeByUniversalSpec(final ExecutionErrorAccumulator eea,
            final PaymentMethodTypeUniversalSpec universalSpec, boolean allowDefault) {
        return getPaymentMethodTypeByUniversalSpec(eea, universalSpec, allowDefault, EntityPermission.READ_ONLY);
    }

    public PaymentMethodType getPaymentMethodTypeByUniversalSpecForUpdate(final ExecutionErrorAccumulator eea,
            final PaymentMethodTypeUniversalSpec universalSpec, boolean allowDefault) {
        return getPaymentMethodTypeByUniversalSpec(eea, universalSpec, allowDefault, EntityPermission.READ_WRITE);
    }

    public void deletePaymentMethodType(final ExecutionErrorAccumulator eea, final PaymentMethodType paymentMethodType,
            final BasePK deletedBy) {
        var paymentMethodTypeControl = Session.getModelController(PaymentMethodTypeControl.class);

        paymentMethodTypeControl.deletePaymentMethodType(paymentMethodType, deletedBy);
    }
}
