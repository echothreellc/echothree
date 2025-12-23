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

import com.echothree.control.user.payment.common.spec.PaymentProcessorUniversalSpec;
import com.echothree.model.control.core.common.ComponentVendors;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.model.control.core.common.exception.InvalidParameterCountException;
import com.echothree.model.control.core.server.logic.EntityInstanceLogic;
import com.echothree.model.control.payment.common.exception.DuplicatePaymentProcessorNameException;
import com.echothree.model.control.payment.common.exception.UnknownDefaultPaymentProcessorException;
import com.echothree.model.control.payment.common.exception.UnknownPaymentProcessorNameException;
import com.echothree.model.control.payment.server.control.PaymentProcessorControl;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.payment.server.entity.PaymentProcessor;
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
public class PaymentProcessorLogic
    extends BaseLogic {

    protected PaymentProcessorLogic() {
        super();
    }

    public static PaymentProcessorLogic getInstance() {
        return CDI.current().select(PaymentProcessorLogic.class).get();
    }

    public PaymentProcessor createPaymentProcessor(final ExecutionErrorAccumulator eea, final String paymentProcessorName,
            PaymentProcessorType paymentProcessorType, final Boolean isDefault, final Integer sortOrder,
            final Language language, final String description, final BasePK createdBy) {
        var paymentProcessorControl = Session.getModelController(PaymentProcessorControl.class);
        var paymentProcessor = paymentProcessorControl.getPaymentProcessorByName(paymentProcessorName);

        if(paymentProcessor == null) {
            paymentProcessor = paymentProcessorControl.createPaymentProcessor(paymentProcessorName, paymentProcessorType, isDefault,
                    sortOrder, createdBy);

            if(description != null) {
                paymentProcessorControl.createPaymentProcessorDescription(paymentProcessor, language, description, createdBy);
            }
        } else {
            handleExecutionError(DuplicatePaymentProcessorNameException.class, eea, ExecutionErrors.DuplicatePaymentProcessorName.name(), paymentProcessorName);
        }

        return paymentProcessor;
    }

    public PaymentProcessor getPaymentProcessorByName(final ExecutionErrorAccumulator eea, final String paymentProcessorName,
            final EntityPermission entityPermission) {
        var paymentProcessorControl = Session.getModelController(PaymentProcessorControl.class);
        var paymentProcessor = paymentProcessorControl.getPaymentProcessorByName(paymentProcessorName, entityPermission);

        if(paymentProcessor == null) {
            handleExecutionError(UnknownPaymentProcessorNameException.class, eea, ExecutionErrors.UnknownPaymentProcessorName.name(), paymentProcessorName);
        }

        return paymentProcessor;
    }

    public PaymentProcessor getPaymentProcessorByName(final ExecutionErrorAccumulator eea, final String paymentProcessorName) {
        return getPaymentProcessorByName(eea, paymentProcessorName, EntityPermission.READ_ONLY);
    }

    public PaymentProcessor getPaymentProcessorByNameForUpdate(final ExecutionErrorAccumulator eea, final String paymentProcessorName) {
        return getPaymentProcessorByName(eea, paymentProcessorName, EntityPermission.READ_WRITE);
    }

    public PaymentProcessor getPaymentProcessorByUniversalSpec(final ExecutionErrorAccumulator eea,
            final PaymentProcessorUniversalSpec universalSpec, boolean allowDefault, final EntityPermission entityPermission) {
        PaymentProcessor paymentProcessor = null;
        var paymentProcessorControl = Session.getModelController(PaymentProcessorControl.class);
        var paymentProcessorName = universalSpec.getPaymentProcessorName();
        var parameterCount = (paymentProcessorName == null ? 0 : 1) + EntityInstanceLogic.getInstance().countPossibleEntitySpecs(universalSpec);

        if(parameterCount == 0) {
            if(allowDefault) {
                paymentProcessor = paymentProcessorControl.getDefaultPaymentProcessor(entityPermission);

                if(paymentProcessor == null) {
                    handleExecutionError(UnknownDefaultPaymentProcessorException.class, eea, ExecutionErrors.UnknownDefaultPaymentProcessor.name());
                }
            } else {
                handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
            }
        } else if(parameterCount == 1) {
            if(paymentProcessorName == null) {
                var entityInstance = EntityInstanceLogic.getInstance().getEntityInstance(eea, universalSpec,
                        ComponentVendors.ECHO_THREE.name(), EntityTypes.PaymentProcessor.name());

                if(!eea.hasExecutionErrors()) {
                    paymentProcessor = paymentProcessorControl.getPaymentProcessorByEntityInstance(entityInstance, entityPermission);
                }
            } else {
                paymentProcessor = getPaymentProcessorByName(eea, paymentProcessorName, entityPermission);
            }
        } else {
            handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
        }

        return paymentProcessor;
    }

    public PaymentProcessor getPaymentProcessorByUniversalSpec(final ExecutionErrorAccumulator eea,
            final PaymentProcessorUniversalSpec universalSpec, boolean allowDefault) {
        return getPaymentProcessorByUniversalSpec(eea, universalSpec, allowDefault, EntityPermission.READ_ONLY);
    }

    public PaymentProcessor getPaymentProcessorByUniversalSpecForUpdate(final ExecutionErrorAccumulator eea,
            final PaymentProcessorUniversalSpec universalSpec, boolean allowDefault) {
        return getPaymentProcessorByUniversalSpec(eea, universalSpec, allowDefault, EntityPermission.READ_WRITE);
    }

    public void deletePaymentProcessor(final ExecutionErrorAccumulator eea, final PaymentProcessor paymentProcessor,
            final BasePK deletedBy) {
        var paymentProcessorControl = Session.getModelController(PaymentProcessorControl.class);

        paymentProcessorControl.deletePaymentProcessor(paymentProcessor, deletedBy);
    }

}
