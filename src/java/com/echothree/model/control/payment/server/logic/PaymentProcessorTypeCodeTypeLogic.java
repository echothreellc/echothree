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

import com.echothree.control.user.payment.common.spec.PaymentProcessorTypeCodeTypeUniversalSpec;
import com.echothree.model.control.core.common.ComponentVendors;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.model.control.core.common.exception.InvalidParameterCountException;
import com.echothree.model.control.core.server.logic.EntityInstanceLogic;
import com.echothree.model.control.party.server.logic.LanguageLogic;
import com.echothree.model.control.payment.common.exception.DuplicatePaymentProcessorTypeCodeTypeDescriptionException;
import com.echothree.model.control.payment.common.exception.DuplicatePaymentProcessorTypeCodeTypeNameException;
import com.echothree.model.control.payment.common.exception.UnknownDefaultPaymentProcessorTypeCodeTypeException;
import com.echothree.model.control.payment.common.exception.UnknownPaymentProcessorTypeCodeTypeNameException;
import com.echothree.model.control.payment.server.control.PaymentProcessorTypeCodeTypeControl;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.payment.server.entity.PaymentProcessorType;
import com.echothree.model.data.payment.server.entity.PaymentProcessorTypeCodeType;
import com.echothree.model.data.payment.server.entity.PaymentProcessorTypeCodeTypeDescription;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.spi.CDI;

@ApplicationScoped
public class PaymentProcessorTypeCodeTypeLogic
    extends BaseLogic {

    protected PaymentProcessorTypeCodeTypeLogic() {
        super();
    }

    public static PaymentProcessorTypeCodeTypeLogic getInstance() {
        return CDI.current().select(PaymentProcessorTypeCodeTypeLogic.class).get();
    }

    public PaymentProcessorTypeCodeType createPaymentProcessorTypeCodeType(final ExecutionErrorAccumulator eea,
            final PaymentProcessorType paymentProcessorType, final String paymentProcessorTypeCodeTypeName,
            final Boolean isDefault, final Integer sortOrder, final Language language, final String description,
            final BasePK createdBy) {
        var paymentProcessorTypeCodeTypeControl = Session.getModelController(PaymentProcessorTypeCodeTypeControl.class);
        var paymentProcessorTypeCodeType = paymentProcessorTypeCodeTypeControl.getPaymentProcessorTypeCodeTypeByName(paymentProcessorType, paymentProcessorTypeCodeTypeName);

        if(paymentProcessorTypeCodeType == null) {
            paymentProcessorTypeCodeType = paymentProcessorTypeCodeTypeControl.createPaymentProcessorTypeCodeType(paymentProcessorType, paymentProcessorTypeCodeTypeName, isDefault, sortOrder, createdBy);

            if(description != null) {
                paymentProcessorTypeCodeTypeControl.createPaymentProcessorTypeCodeTypeDescription(paymentProcessorTypeCodeType, language, description, createdBy);
            }
        } else {
            handleExecutionError(DuplicatePaymentProcessorTypeCodeTypeNameException.class, eea, ExecutionErrors.DuplicatePaymentProcessorTypeCodeTypeName.name(), paymentProcessorTypeCodeTypeName);
        }

        return paymentProcessorTypeCodeType;
    }

    public PaymentProcessorTypeCodeType createPaymentProcessorTypeCodeType(final ExecutionErrorAccumulator eea,
            final String paymentProcessorTypeName, final String paymentProcessorTypeCodeTypeName,
            final Boolean isDefault, final Integer sortOrder, final Language language, final String description,
            final BasePK createdBy) {
        PaymentProcessorTypeCodeType paymentProcessorTypeCodeType = null;
        var paymentProcessorType = PaymentProcessorTypeLogic.getInstance().getPaymentProcessorTypeByName(eea, paymentProcessorTypeName);

        if(!eea.hasExecutionErrors()) {
            paymentProcessorTypeCodeType = createPaymentProcessorTypeCodeType(eea, paymentProcessorType,
                    paymentProcessorTypeCodeTypeName, isDefault, sortOrder, language, description, createdBy);
        }

        return paymentProcessorTypeCodeType;
    }

    public PaymentProcessorTypeCodeType getPaymentProcessorTypeCodeTypeByName(final ExecutionErrorAccumulator eea,
            final PaymentProcessorType paymentProcessorType, final String paymentProcessorTypeCodeTypeName,
            final EntityPermission entityPermission) {
        var paymentProcessorTypeCodeTypeControl = Session.getModelController(PaymentProcessorTypeCodeTypeControl.class);
        var paymentProcessorTypeCodeType = paymentProcessorTypeCodeTypeControl.getPaymentProcessorTypeCodeTypeByName(paymentProcessorType,
                paymentProcessorTypeCodeTypeName, entityPermission);

        if(paymentProcessorTypeCodeType == null) {
            handleExecutionError(UnknownPaymentProcessorTypeCodeTypeNameException.class, eea, ExecutionErrors.UnknownPaymentProcessorTypeCodeTypeName.name(), paymentProcessorTypeCodeTypeName);
        }

        return paymentProcessorTypeCodeType;
    }

    public PaymentProcessorTypeCodeType getPaymentProcessorTypeCodeTypeByName(final ExecutionErrorAccumulator eea,
            final PaymentProcessorType paymentProcessorType, final String paymentProcessorTypeCodeTypeName) {
        return getPaymentProcessorTypeCodeTypeByName(eea, paymentProcessorType, paymentProcessorTypeCodeTypeName, EntityPermission.READ_ONLY);
    }

    public PaymentProcessorTypeCodeType getPaymentProcessorTypeCodeTypeByNameForUpdate(final ExecutionErrorAccumulator eea,
            final PaymentProcessorType paymentProcessorType, final String paymentProcessorTypeCodeTypeName) {
        return getPaymentProcessorTypeCodeTypeByName(eea, paymentProcessorType, paymentProcessorTypeCodeTypeName, EntityPermission.READ_WRITE);
    }

    public PaymentProcessorTypeCodeType getPaymentProcessorTypeCodeTypeByNames(final ExecutionErrorAccumulator eea,
            final String paymentProcessorTypeName, final String paymentProcessorTypeCodeTypeName,
            final EntityPermission entityPermission) {
        var paymentProcessorType = PaymentProcessorTypeLogic.getInstance().getPaymentProcessorTypeByName(eea, paymentProcessorTypeName);
        PaymentProcessorTypeCodeType paymentProcessorTypeCodeType = null;

        if(!eea.hasExecutionErrors()) {
            paymentProcessorTypeCodeType = getPaymentProcessorTypeCodeTypeByName(eea, paymentProcessorType, paymentProcessorTypeCodeTypeName, entityPermission);
        }

        return paymentProcessorTypeCodeType;
    }

    public PaymentProcessorTypeCodeType getPaymentProcessorTypeCodeTypeByNames(final ExecutionErrorAccumulator eea,
            final String paymentProcessorTypeName, final String paymentProcessorTypeCodeTypeName) {
        return getPaymentProcessorTypeCodeTypeByNames(eea, paymentProcessorTypeName, paymentProcessorTypeCodeTypeName, EntityPermission.READ_ONLY);
    }

    public PaymentProcessorTypeCodeType getPaymentProcessorTypeCodeTypeByNamesForUpdate(final ExecutionErrorAccumulator eea,
            final String paymentProcessorTypeName, final String paymentProcessorTypeCodeTypeName) {
        return getPaymentProcessorTypeCodeTypeByNames(eea, paymentProcessorTypeName, paymentProcessorTypeCodeTypeName, EntityPermission.READ_WRITE);
    }

    public PaymentProcessorTypeCodeType getPaymentProcessorTypeCodeTypeByUniversalSpec(final ExecutionErrorAccumulator eea,
            final PaymentProcessorTypeCodeTypeUniversalSpec universalSpec, boolean allowDefault, final EntityPermission entityPermission) {
        PaymentProcessorTypeCodeType paymentProcessorTypeCodeType = null;
        var paymentProcessorTypeCodeTypeControl = Session.getModelController(PaymentProcessorTypeCodeTypeControl.class);
        var paymentProcessorTypeName = universalSpec.getPaymentProcessorTypeName();
        var paymentProcessorTypeCodeTypeName = universalSpec.getPaymentProcessorTypeCodeTypeName();
        var parameterCount = (paymentProcessorTypeName != null && paymentProcessorTypeCodeTypeName != null ? 1 : 0) + EntityInstanceLogic.getInstance().countPossibleEntitySpecs(universalSpec);

        switch(parameterCount) {
            case 0 -> {
                if(allowDefault) {
                    if(paymentProcessorTypeName != null) {
                        var paymentProcessorType = PaymentProcessorTypeLogic.getInstance().getPaymentProcessorTypeByName(eea, universalSpec.getPaymentProcessorTypeName());

                        if(!eea.hasExecutionErrors()) {
                            paymentProcessorTypeCodeType = paymentProcessorTypeCodeTypeControl.getDefaultPaymentProcessorTypeCodeType(paymentProcessorType, entityPermission);

                            if(paymentProcessorTypeCodeType == null) {
                                handleExecutionError(UnknownDefaultPaymentProcessorTypeCodeTypeException.class, eea, ExecutionErrors.UnknownDefaultPaymentProcessorTypeCodeType.name());
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
                if(paymentProcessorTypeName != null && paymentProcessorTypeCodeTypeName != null) {
                    var paymentProcessorType = PaymentProcessorTypeLogic.getInstance().getPaymentProcessorTypeByName(eea, universalSpec.getPaymentProcessorTypeName());

                    if(!eea.hasExecutionErrors()) {
                        paymentProcessorTypeCodeType = getPaymentProcessorTypeCodeTypeByName(eea, paymentProcessorType,
                                paymentProcessorTypeCodeTypeName, entityPermission);
                    }
                } else {
                    var entityInstance = EntityInstanceLogic.getInstance().getEntityInstance(eea, universalSpec,
                            ComponentVendors.ECHO_THREE.name(), EntityTypes.PaymentProcessorTypeCodeType.name());

                    if(!eea.hasExecutionErrors()) {
                        paymentProcessorTypeCodeType = paymentProcessorTypeCodeTypeControl.getPaymentProcessorTypeCodeTypeByEntityInstance(entityInstance, entityPermission);
                    }
                }
            }
            default ->
                    handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
        }

        return paymentProcessorTypeCodeType;
    }

    public PaymentProcessorTypeCodeType getPaymentProcessorTypeCodeTypeByUniversalSpec(final ExecutionErrorAccumulator eea,
            final PaymentProcessorTypeCodeTypeUniversalSpec universalSpec, boolean allowDefault) {
        return getPaymentProcessorTypeCodeTypeByUniversalSpec(eea, universalSpec, allowDefault, EntityPermission.READ_ONLY);
    }

    public PaymentProcessorTypeCodeType getPaymentProcessorTypeCodeTypeByUniversalSpecForUpdate(final ExecutionErrorAccumulator eea,
            final PaymentProcessorTypeCodeTypeUniversalSpec universalSpec, boolean allowDefault) {
        return getPaymentProcessorTypeCodeTypeByUniversalSpec(eea, universalSpec, allowDefault, EntityPermission.READ_WRITE);
    }

    public void deletePaymentProcessorTypeCodeType(final ExecutionErrorAccumulator eea, final PaymentProcessorTypeCodeType paymentProcessorTypeCodeType,
            final BasePK deletedBy) {
        var paymentProcessorTypeCodeTypeControl = Session.getModelController(PaymentProcessorTypeCodeTypeControl.class);

        paymentProcessorTypeCodeTypeControl.deletePaymentProcessorTypeCodeType(paymentProcessorTypeCodeType, deletedBy);
    }

    public PaymentProcessorTypeCodeTypeDescription createPaymentProcessorTypeCodeTypeDescription(final ExecutionErrorAccumulator eea,
            final PaymentProcessorTypeCodeType paymentProcessorTypeCodeType, final Language language,
            final String description, final BasePK createdBy) {
        var paymentProcessorTypeCodeTypeControl = Session.getModelController(PaymentProcessorTypeCodeTypeControl.class);
        var paymentProcessorTypeCodeTypeDescription = paymentProcessorTypeCodeTypeControl.getPaymentProcessorTypeCodeTypeDescription(paymentProcessorTypeCodeType, language);

        if(paymentProcessorTypeCodeTypeDescription == null) {
            paymentProcessorTypeCodeTypeDescription = paymentProcessorTypeCodeTypeControl.createPaymentProcessorTypeCodeTypeDescription(paymentProcessorTypeCodeType,
                    language, description, createdBy);
        } else {
            handleExecutionError(DuplicatePaymentProcessorTypeCodeTypeDescriptionException.class, eea, ExecutionErrors.DuplicatePaymentProcessorTypeCodeTypeDescription.name());
        }

        return paymentProcessorTypeCodeTypeDescription;
    }

    public PaymentProcessorTypeCodeTypeDescription createPaymentProcessorTypeCodeTypeDescription(final ExecutionErrorAccumulator eea,
            final String paymentProcessorTypeName, final String paymentProcessorTypeCodeTypeName,
            final String languageIsoName, final String description, final BasePK createdBy) {
        var paymentProcessorTypeCodeType = getPaymentProcessorTypeCodeTypeByNames(eea, paymentProcessorTypeName,
                paymentProcessorTypeCodeTypeName);
        var language = LanguageLogic.getInstance().getLanguageByName(eea, languageIsoName);
        PaymentProcessorTypeCodeTypeDescription paymentProcessorTypeCodeTypeDescription = null;

        if(!eea.hasExecutionErrors()) {
            paymentProcessorTypeCodeTypeDescription = createPaymentProcessorTypeCodeTypeDescription(eea,
                    paymentProcessorTypeCodeType, language, description, createdBy);
        }

        return paymentProcessorTypeCodeTypeDescription;
    }
}
