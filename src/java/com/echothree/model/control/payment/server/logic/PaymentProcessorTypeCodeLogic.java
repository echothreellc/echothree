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

import com.echothree.control.user.payment.common.spec.PaymentProcessorTypeCodeUniversalSpec;
import com.echothree.model.control.core.common.ComponentVendors;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.model.control.core.common.exception.InvalidParameterCountException;
import com.echothree.model.control.core.server.logic.EntityInstanceLogic;
import com.echothree.model.control.party.server.logic.LanguageLogic;
import com.echothree.model.control.payment.common.exception.DuplicatePaymentProcessorTypeCodeDescriptionException;
import com.echothree.model.control.payment.common.exception.DuplicatePaymentProcessorTypeCodeNameException;
import com.echothree.model.control.payment.common.exception.UnknownDefaultPaymentProcessorTypeCodeException;
import com.echothree.model.control.payment.common.exception.UnknownPaymentProcessorTypeCodeNameException;
import com.echothree.model.control.payment.server.control.PaymentProcessorTypeCodeControl;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.payment.server.entity.PaymentProcessorTypeCode;
import com.echothree.model.data.payment.server.entity.PaymentProcessorTypeCodeDescription;
import com.echothree.model.data.payment.server.entity.PaymentProcessorTypeCodeType;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.spi.CDI;

@ApplicationScoped
public class PaymentProcessorTypeCodeLogic
    extends BaseLogic {

    protected PaymentProcessorTypeCodeLogic() {
        super();
    }

    public static PaymentProcessorTypeCodeLogic getInstance() {
        return CDI.current().select(PaymentProcessorTypeCodeLogic.class).get();
    }

    public PaymentProcessorTypeCode createPaymentProcessorTypeCode(final ExecutionErrorAccumulator eea,
            final PaymentProcessorTypeCodeType paymentProcessorTypeCodeType, final String paymentProcessorTypeCodeName,
            final Boolean isDefault, final Integer sortOrder, final Language language, final String description,
            final BasePK createdBy) {
        var paymentProcessorTypeCodeControl = Session.getModelController(PaymentProcessorTypeCodeControl.class);
        var paymentProcessorTypeCode = paymentProcessorTypeCodeControl.getPaymentProcessorTypeCodeByName(paymentProcessorTypeCodeType, paymentProcessorTypeCodeName);

        if(paymentProcessorTypeCode == null) {
            paymentProcessorTypeCode = paymentProcessorTypeCodeControl.createPaymentProcessorTypeCode(paymentProcessorTypeCodeType,
                    paymentProcessorTypeCodeName, isDefault, sortOrder, createdBy);

            if(description != null) {
                paymentProcessorTypeCodeControl.createPaymentProcessorTypeCodeDescription(paymentProcessorTypeCode, language, description, createdBy);
            }
        } else {
            handleExecutionError(DuplicatePaymentProcessorTypeCodeNameException.class, eea, ExecutionErrors.DuplicatePaymentProcessorTypeCodeName.name(), paymentProcessorTypeCodeName);
        }

        return paymentProcessorTypeCode;
    }

    public PaymentProcessorTypeCode createPaymentProcessorTypeCode(final ExecutionErrorAccumulator eea,
            final String paymentProcessorTypeName, final String paymentProcessorTypeCodeTypeName,
            final String paymentProcessorTypeCodeName, final Boolean isDefault, final Integer sortOrder,
            final Language language, final String description, final BasePK createdBy) {
        PaymentProcessorTypeCode paymentProcessorTypeCode = null;
        var paymentProcessorTypeCodeType = PaymentProcessorTypeCodeTypeLogic.getInstance().getPaymentProcessorTypeCodeTypeByNames(eea,
                paymentProcessorTypeName, paymentProcessorTypeCodeTypeName);

        if(!eea.hasExecutionErrors()) {
            paymentProcessorTypeCode = createPaymentProcessorTypeCode(eea, paymentProcessorTypeCodeType,
                    paymentProcessorTypeCodeName, isDefault, sortOrder, language, description, createdBy);
        }

        return paymentProcessorTypeCode;
    }

    public PaymentProcessorTypeCode getPaymentProcessorTypeCodeByName(final ExecutionErrorAccumulator eea,
            final PaymentProcessorTypeCodeType paymentProcessorTypeCodeType, final String paymentProcessorTypeCodeName,
            final EntityPermission entityPermission) {
        var paymentProcessorTypeCodeControl = Session.getModelController(PaymentProcessorTypeCodeControl.class);
        var paymentProcessorTypeCode = paymentProcessorTypeCodeControl.getPaymentProcessorTypeCodeByName(paymentProcessorTypeCodeType,
                paymentProcessorTypeCodeName, entityPermission);

        if(paymentProcessorTypeCode == null) {
            handleExecutionError(UnknownPaymentProcessorTypeCodeNameException.class, eea, ExecutionErrors.UnknownPaymentProcessorTypeCodeName.name(), paymentProcessorTypeCodeName);
        }

        return paymentProcessorTypeCode;
    }

    public PaymentProcessorTypeCode getPaymentProcessorTypeCodeByName(final ExecutionErrorAccumulator eea,
            final PaymentProcessorTypeCodeType paymentProcessorTypeCodeType, final String paymentProcessorTypeCodeName) {
        return getPaymentProcessorTypeCodeByName(eea, paymentProcessorTypeCodeType, paymentProcessorTypeCodeName, EntityPermission.READ_ONLY);
    }

    public PaymentProcessorTypeCode getPaymentProcessorTypeCodeByNameForUpdate(final ExecutionErrorAccumulator eea,
            final PaymentProcessorTypeCodeType paymentProcessorTypeCodeType, final String paymentProcessorTypeCodeName) {
        return getPaymentProcessorTypeCodeByName(eea, paymentProcessorTypeCodeType, paymentProcessorTypeCodeName, EntityPermission.READ_WRITE);
    }

    public PaymentProcessorTypeCode getPaymentProcessorTypeCodeByNames(final ExecutionErrorAccumulator eea,
            final String paymentProcessorTypeName, final String paymentProcessorTypeCodeTypeName,
            final String paymentProcessorTypeCodeName, final EntityPermission entityPermission) {
        var paymentProcessorTypeCodeType = PaymentProcessorTypeCodeTypeLogic.getInstance().getPaymentProcessorTypeCodeTypeByNames(eea,
                paymentProcessorTypeName, paymentProcessorTypeCodeTypeName);
        PaymentProcessorTypeCode paymentProcessorTypeCode = null;

        if(!eea.hasExecutionErrors()) {
            paymentProcessorTypeCode = getPaymentProcessorTypeCodeByName(eea, paymentProcessorTypeCodeType, paymentProcessorTypeCodeName, entityPermission);
        }

        return paymentProcessorTypeCode;
    }

    public PaymentProcessorTypeCode getPaymentProcessorTypeCodeByNames(final ExecutionErrorAccumulator eea,
            final String paymentProcessorTypeName, final String paymentProcessorTypeCodeTypeName, final String paymentProcessorTypeCodeName) {
        return getPaymentProcessorTypeCodeByNames(eea, paymentProcessorTypeName, paymentProcessorTypeCodeTypeName, paymentProcessorTypeCodeName, EntityPermission.READ_ONLY);
    }

    public PaymentProcessorTypeCode getPaymentProcessorTypeCodeByNamesForUpdate(final ExecutionErrorAccumulator eea,
            final String paymentProcessorTypeName, final String paymentProcessorTypeCodeTypeName, final String paymentProcessorTypeCodeName) {
        return getPaymentProcessorTypeCodeByNames(eea, paymentProcessorTypeName, paymentProcessorTypeCodeTypeName, paymentProcessorTypeCodeName, EntityPermission.READ_WRITE);
    }

    public PaymentProcessorTypeCode getPaymentProcessorTypeCodeByUniversalSpec(final ExecutionErrorAccumulator eea,
            final PaymentProcessorTypeCodeUniversalSpec universalSpec, boolean allowDefault, final EntityPermission entityPermission) {
        PaymentProcessorTypeCode paymentProcessorTypeCode = null;
        var paymentProcessorTypeCodeControl = Session.getModelController(PaymentProcessorTypeCodeControl.class);
        var paymentProcessorTypeName = universalSpec.getPaymentProcessorTypeName();
        var paymentProcessorTypeCodeTypeName = universalSpec.getPaymentProcessorTypeCodeTypeName();
        var paymentProcessorTypeCodeName = universalSpec.getPaymentProcessorTypeCodeName();
        var fullySpecifiedName = paymentProcessorTypeName != null && paymentProcessorTypeCodeTypeName != null && paymentProcessorTypeCodeName != null;
        var parameterCount = (fullySpecifiedName ? 1 : 0) + EntityInstanceLogic.getInstance().countPossibleEntitySpecs(universalSpec);

        switch(parameterCount) {
            case 0 -> {
                if(allowDefault) {
                    if(paymentProcessorTypeCodeTypeName != null) {
                        var paymentProcessorTypeCodeType = PaymentProcessorTypeCodeTypeLogic.getInstance().getPaymentProcessorTypeCodeTypeByNames(eea,
                                paymentProcessorTypeName, paymentProcessorTypeCodeTypeName);

                        if(!eea.hasExecutionErrors()) {
                            paymentProcessorTypeCode = paymentProcessorTypeCodeControl.getDefaultPaymentProcessorTypeCode(paymentProcessorTypeCodeType, entityPermission);

                            if(paymentProcessorTypeCode == null) {
                                handleExecutionError(UnknownDefaultPaymentProcessorTypeCodeException.class, eea, ExecutionErrors.UnknownDefaultPaymentProcessorTypeCode.name());
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
                    var paymentProcessorTypeCodeType = PaymentProcessorTypeCodeTypeLogic.getInstance().getPaymentProcessorTypeCodeTypeByNames(eea,
                            paymentProcessorTypeName, paymentProcessorTypeCodeTypeName);

                    if(!eea.hasExecutionErrors()) {
                        paymentProcessorTypeCode = getPaymentProcessorTypeCodeByName(eea, paymentProcessorTypeCodeType,
                                paymentProcessorTypeCodeName, entityPermission);
                    }
                } else {
                    var entityInstance = EntityInstanceLogic.getInstance().getEntityInstance(eea, universalSpec,
                            ComponentVendors.ECHO_THREE.name(), EntityTypes.PaymentProcessorTypeCode.name());

                    if(!eea.hasExecutionErrors()) {
                        paymentProcessorTypeCode = paymentProcessorTypeCodeControl.getPaymentProcessorTypeCodeByEntityInstance(entityInstance, entityPermission);
                    }
                }
            }
            default ->
                    handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
        }

        return paymentProcessorTypeCode;
    }

    public PaymentProcessorTypeCode getPaymentProcessorTypeCodeByUniversalSpec(final ExecutionErrorAccumulator eea,
            final PaymentProcessorTypeCodeUniversalSpec universalSpec, boolean allowDefault) {
        return getPaymentProcessorTypeCodeByUniversalSpec(eea, universalSpec, allowDefault, EntityPermission.READ_ONLY);
    }

    public PaymentProcessorTypeCode getPaymentProcessorTypeCodeByUniversalSpecForUpdate(final ExecutionErrorAccumulator eea,
            final PaymentProcessorTypeCodeUniversalSpec universalSpec, boolean allowDefault) {
        return getPaymentProcessorTypeCodeByUniversalSpec(eea, universalSpec, allowDefault, EntityPermission.READ_WRITE);
    }

    public void deletePaymentProcessorTypeCode(final ExecutionErrorAccumulator eea, final PaymentProcessorTypeCode paymentProcessorTypeCode,
            final BasePK deletedBy) {
        var paymentProcessorTypeCodeControl = Session.getModelController(PaymentProcessorTypeCodeControl.class);

        paymentProcessorTypeCodeControl.deletePaymentProcessorTypeCode(paymentProcessorTypeCode, deletedBy);
    }

    public PaymentProcessorTypeCodeDescription createPaymentProcessorTypeCodeDescription(final ExecutionErrorAccumulator eea,
            final PaymentProcessorTypeCode paymentProcessorTypeCode, final Language language,
            final String description, final BasePK createdBy) {
        var paymentProcessorTypeCodeControl = Session.getModelController(PaymentProcessorTypeCodeControl.class);
        var paymentProcessorTypeCodeDescription = paymentProcessorTypeCodeControl.getPaymentProcessorTypeCodeDescription(paymentProcessorTypeCode, language);

        if(paymentProcessorTypeCodeDescription == null) {
            paymentProcessorTypeCodeDescription = paymentProcessorTypeCodeControl.createPaymentProcessorTypeCodeDescription(paymentProcessorTypeCode,
                    language, description, createdBy);
        } else {
            handleExecutionError(DuplicatePaymentProcessorTypeCodeDescriptionException.class, eea, ExecutionErrors.DuplicatePaymentProcessorTypeCodeDescription.name());
        }

        return paymentProcessorTypeCodeDescription;
    }

    public PaymentProcessorTypeCodeDescription createPaymentProcessorTypeCodeDescription(final ExecutionErrorAccumulator eea,
            final String paymentProcessorTypeName, final String paymentProcessorTypeCodeTypeName, final String paymentProcessorTypeCodeName,
            final String languageIsoName, final String description, final BasePK createdBy) {
        var paymentProcessorTypeCode = getPaymentProcessorTypeCodeByNames(eea, paymentProcessorTypeName,
                paymentProcessorTypeCodeTypeName, paymentProcessorTypeCodeName);
        var language = LanguageLogic.getInstance().getLanguageByName(eea, languageIsoName);
        PaymentProcessorTypeCodeDescription paymentProcessorTypeCodeDescription = null;

        if(!eea.hasExecutionErrors()) {
            paymentProcessorTypeCodeDescription = createPaymentProcessorTypeCodeDescription(eea,
                    paymentProcessorTypeCode, language, description, createdBy);
        }

        return paymentProcessorTypeCodeDescription;
    }
}
