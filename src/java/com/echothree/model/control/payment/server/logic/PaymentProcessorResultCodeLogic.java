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

import com.echothree.control.user.payment.common.spec.PaymentProcessorResultCodeUniversalSpec;
import com.echothree.model.control.core.common.ComponentVendors;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.model.control.core.common.exception.InvalidParameterCountException;
import com.echothree.model.control.core.server.logic.EntityInstanceLogic;
import com.echothree.model.control.payment.common.exception.DuplicatePaymentProcessorResultCodeNameException;
import com.echothree.model.control.payment.common.exception.UnknownDefaultPaymentProcessorResultCodeException;
import com.echothree.model.control.payment.common.exception.UnknownPaymentProcessorResultCodeNameException;
import com.echothree.model.control.payment.server.control.PaymentProcessorResultCodeControl;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.payment.server.entity.PaymentProcessorResultCode;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.spi.CDI;

@ApplicationScoped
public class PaymentProcessorResultCodeLogic
    extends BaseLogic {

    protected PaymentProcessorResultCodeLogic() {
        super();
    }

    public static PaymentProcessorResultCodeLogic getInstance() {
        return CDI.current().select(PaymentProcessorResultCodeLogic.class).get();
    }

    public PaymentProcessorResultCode createPaymentProcessorResultCode(final ExecutionErrorAccumulator eea, final String paymentProcessorResultCodeName,
            final Boolean isDefault, final Integer sortOrder, final Language language, final String description,
            final BasePK createdBy) {
        var paymentProcessorResultCodeControl = Session.getModelController(PaymentProcessorResultCodeControl.class);
        var paymentProcessorResultCode = paymentProcessorResultCodeControl.getPaymentProcessorResultCodeByName(paymentProcessorResultCodeName);

        if(paymentProcessorResultCode == null) {
            paymentProcessorResultCode = paymentProcessorResultCodeControl.createPaymentProcessorResultCode(paymentProcessorResultCodeName, isDefault, sortOrder, createdBy);

            if(description != null) {
                paymentProcessorResultCodeControl.createPaymentProcessorResultCodeDescription(paymentProcessorResultCode, language, description, createdBy);
            }
        } else {
            handleExecutionError(DuplicatePaymentProcessorResultCodeNameException.class, eea, ExecutionErrors.DuplicatePaymentProcessorResultCodeName.name(), paymentProcessorResultCodeName);
        }

        return paymentProcessorResultCode;
    }

    public PaymentProcessorResultCode getPaymentProcessorResultCodeByName(final ExecutionErrorAccumulator eea, final String paymentProcessorResultCodeName,
            final EntityPermission entityPermission) {
        var paymentProcessorResultCodeControl = Session.getModelController(PaymentProcessorResultCodeControl.class);
        var paymentProcessorResultCode = paymentProcessorResultCodeControl.getPaymentProcessorResultCodeByName(paymentProcessorResultCodeName, entityPermission);

        if(paymentProcessorResultCode == null) {
            handleExecutionError(UnknownPaymentProcessorResultCodeNameException.class, eea, ExecutionErrors.UnknownPaymentProcessorResultCodeName.name(), paymentProcessorResultCodeName);
        }

        return paymentProcessorResultCode;
    }

    public PaymentProcessorResultCode getPaymentProcessorResultCodeByName(final ExecutionErrorAccumulator eea, final String paymentProcessorResultCodeName) {
        return getPaymentProcessorResultCodeByName(eea, paymentProcessorResultCodeName, EntityPermission.READ_ONLY);
    }

    public PaymentProcessorResultCode getPaymentProcessorResultCodeByNameForUpdate(final ExecutionErrorAccumulator eea, final String paymentProcessorResultCodeName) {
        return getPaymentProcessorResultCodeByName(eea, paymentProcessorResultCodeName, EntityPermission.READ_WRITE);
    }

    public PaymentProcessorResultCode getPaymentProcessorResultCodeByUniversalSpec(final ExecutionErrorAccumulator eea,
            final PaymentProcessorResultCodeUniversalSpec universalSpec, boolean allowDefault, final EntityPermission entityPermission) {
        PaymentProcessorResultCode paymentProcessorResultCode = null;
        var paymentProcessorResultCodeControl = Session.getModelController(PaymentProcessorResultCodeControl.class);
        var paymentProcessorResultCodeName = universalSpec.getPaymentProcessorResultCodeName();
        var parameterCount = (paymentProcessorResultCodeName == null ? 0 : 1) + EntityInstanceLogic.getInstance().countPossibleEntitySpecs(universalSpec);

        switch(parameterCount) {
            case 0:
                if(allowDefault) {
                    paymentProcessorResultCode = paymentProcessorResultCodeControl.getDefaultPaymentProcessorResultCode(entityPermission);

                    if(paymentProcessorResultCode == null) {
                        handleExecutionError(UnknownDefaultPaymentProcessorResultCodeException.class, eea, ExecutionErrors.UnknownDefaultPaymentProcessorResultCode.name());
                    }
                } else {
                    handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
                }
                break;
            case 1:
                if(paymentProcessorResultCodeName == null) {
                    var entityInstance = EntityInstanceLogic.getInstance().getEntityInstance(eea, universalSpec,
                            ComponentVendors.ECHO_THREE.name(), EntityTypes.PaymentProcessorResultCode.name());

                    if(!eea.hasExecutionErrors()) {
                        paymentProcessorResultCode = paymentProcessorResultCodeControl.getPaymentProcessorResultCodeByEntityInstance(entityInstance, entityPermission);
                    }
                } else {
                    paymentProcessorResultCode = getPaymentProcessorResultCodeByName(eea, paymentProcessorResultCodeName, entityPermission);
                }
                break;
            default:
                handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
                break;
        }

        return paymentProcessorResultCode;
    }

    public PaymentProcessorResultCode getPaymentProcessorResultCodeByUniversalSpec(final ExecutionErrorAccumulator eea,
            final PaymentProcessorResultCodeUniversalSpec universalSpec, boolean allowDefault) {
        return getPaymentProcessorResultCodeByUniversalSpec(eea, universalSpec, allowDefault, EntityPermission.READ_ONLY);
    }

    public PaymentProcessorResultCode getPaymentProcessorResultCodeByUniversalSpecForUpdate(final ExecutionErrorAccumulator eea,
            final PaymentProcessorResultCodeUniversalSpec universalSpec, boolean allowDefault) {
        return getPaymentProcessorResultCodeByUniversalSpec(eea, universalSpec, allowDefault, EntityPermission.READ_WRITE);
    }

    public void deletePaymentProcessorResultCode(final ExecutionErrorAccumulator eea, final PaymentProcessorResultCode paymentProcessorResultCode,
            final BasePK deletedBy) {
        var paymentProcessorResultCodeControl = Session.getModelController(PaymentProcessorResultCodeControl.class);

        paymentProcessorResultCodeControl.deletePaymentProcessorResultCode(paymentProcessorResultCode, deletedBy);
    }
}
