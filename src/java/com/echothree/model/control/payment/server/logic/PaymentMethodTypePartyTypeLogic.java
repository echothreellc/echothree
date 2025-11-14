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

import com.echothree.control.user.payment.common.spec.PaymentMethodTypePartyTypeUniversalSpec;
import com.echothree.model.control.core.common.ComponentVendors;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.model.control.core.common.exception.InvalidParameterCountException;
import com.echothree.model.control.core.server.logic.EntityInstanceLogic;
import com.echothree.model.control.party.server.logic.PartyLogic;
import com.echothree.model.control.payment.common.exception.DuplicatePaymentMethodTypePartyTypeException;
import com.echothree.model.control.payment.common.exception.UnknownDefaultPaymentMethodTypePartyTypeException;
import com.echothree.model.control.payment.common.exception.UnknownPaymentMethodTypePartyTypeException;
import com.echothree.model.control.payment.server.control.PaymentMethodTypePartyTypeControl;
import com.echothree.model.control.workflow.server.logic.WorkflowLogic;
import com.echothree.model.data.party.server.entity.PartyType;
import com.echothree.model.data.payment.server.entity.PaymentMethodType;
import com.echothree.model.data.payment.server.entity.PaymentMethodTypePartyType;
import com.echothree.model.data.workflow.server.entity.Workflow;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.spi.CDI;

@ApplicationScoped
public class PaymentMethodTypePartyTypeLogic
    extends BaseLogic {

    protected PaymentMethodTypePartyTypeLogic() {
        super();
    }

    public static PaymentMethodTypePartyTypeLogic getInstance() {
        return CDI.current().select(PaymentMethodTypePartyTypeLogic.class).get();
    }

    public PaymentMethodTypePartyType createPaymentMethodTypePartyType(final ExecutionErrorAccumulator eea,
            final PaymentMethodType paymentMethodType, final PartyType partyType, final Workflow partyPaymentMethodWorkflow,
            final Workflow partyContactMechanismWorkflow, final Boolean isDefault, final Integer sortOrder, final BasePK createdBy) {
        var paymentMethodTypePartyTypeControl = Session.getModelController(PaymentMethodTypePartyTypeControl.class);
        var paymentMethodTypePartyType = paymentMethodTypePartyTypeControl.getPaymentMethodTypePartyType(paymentMethodType, partyType);

        if(paymentMethodTypePartyType == null) {
            paymentMethodTypePartyType = paymentMethodTypePartyTypeControl.createPaymentMethodTypePartyType(paymentMethodType,
                    partyType, partyPaymentMethodWorkflow, partyContactMechanismWorkflow, isDefault, sortOrder, createdBy);
        } else {
            handleExecutionError(DuplicatePaymentMethodTypePartyTypeException.class, eea, ExecutionErrors.DuplicatePaymentMethodTypePartyType.name(),
                    paymentMethodType.getLastDetail().getPaymentMethodTypeName(), partyType.getPartyTypeName());
        }

        return paymentMethodTypePartyType;
    }

    public PaymentMethodTypePartyType createPaymentMethodTypePartyType(final ExecutionErrorAccumulator eea,
            final String paymentMethodTypeName, final String partyTypeName, final String partyPaymentMethodWorkflowName,
            final String partyContactMechanismWorkflowName, final Boolean isDefault, final Integer sortOrder,
            final BasePK createdBy) {
        PaymentMethodTypePartyType paymentMethodTypePartyType = null;
        var paymentMethodType = PaymentMethodTypeLogic.getInstance().getPaymentMethodTypeByName(eea, paymentMethodTypeName);
        var partyType = PartyLogic.getInstance().getPartyTypeByName(eea, partyTypeName);
        var partyPaymentMethodWorkflow = partyPaymentMethodWorkflowName == null ? null : WorkflowLogic.getInstance().getWorkflowByName(eea, partyPaymentMethodWorkflowName);
        var partyContactMechanismWorkflow = partyContactMechanismWorkflowName == null ? null : WorkflowLogic.getInstance().getWorkflowByName(eea, partyContactMechanismWorkflowName);

        if(!eea.hasExecutionErrors()) {
            paymentMethodTypePartyType = createPaymentMethodTypePartyType(eea, paymentMethodType, partyType,
                    partyPaymentMethodWorkflow, partyContactMechanismWorkflow, isDefault, sortOrder, createdBy);
        }

        return paymentMethodTypePartyType;
    }

    public PaymentMethodTypePartyType getPaymentMethodTypePartyType(final ExecutionErrorAccumulator eea,
            final PaymentMethodType paymentMethodType, final PartyType partyType,
            final EntityPermission entityPermission) {
        var paymentMethodTypePartyTypeControl = Session.getModelController(PaymentMethodTypePartyTypeControl.class);
        var paymentMethodTypePartyType = paymentMethodTypePartyTypeControl.getPaymentMethodTypePartyType(paymentMethodType,
                partyType, entityPermission);

        if(paymentMethodTypePartyType == null) {
            handleExecutionError(UnknownPaymentMethodTypePartyTypeException.class, eea, ExecutionErrors.UnknownPaymentMethodTypePartyType.name(),
                    paymentMethodType.getLastDetail().getPaymentMethodTypeName(), partyType.getPartyTypeName());
        }

        return paymentMethodTypePartyType;
    }

    public PaymentMethodTypePartyType getPaymentMethodTypePartyType(final ExecutionErrorAccumulator eea,
            final PaymentMethodType paymentMethodType, final PartyType partyType) {
        return getPaymentMethodTypePartyType(eea, paymentMethodType, partyType, EntityPermission.READ_ONLY);
    }

    public PaymentMethodTypePartyType getPaymentMethodTypePartyTypeForUpdate(final ExecutionErrorAccumulator eea,
            final PaymentMethodType paymentMethodType, final PartyType partyType) {
        return getPaymentMethodTypePartyType(eea, paymentMethodType, partyType, EntityPermission.READ_WRITE);
    }

    public PaymentMethodTypePartyType getPaymentMethodTypePartyTypeByNames(final ExecutionErrorAccumulator eea,
            final String paymentMethodTypeName, final String partyTypeName, final EntityPermission entityPermission) {
        var paymentMethodType = PaymentMethodTypeLogic.getInstance().getPaymentMethodTypeByName(eea, paymentMethodTypeName);
        var partyType = PartyLogic.getInstance().getPartyTypeByName(eea, partyTypeName);
        PaymentMethodTypePartyType paymentMethodTypePartyType = null;

        if(!eea.hasExecutionErrors()) {
            paymentMethodTypePartyType = getPaymentMethodTypePartyType(eea, paymentMethodType, partyType, entityPermission);
        }

        return paymentMethodTypePartyType;
    }

    public PaymentMethodTypePartyType getPaymentMethodTypePartyTypeByNames(final ExecutionErrorAccumulator eea,
            final String paymentMethodTypeName, final String partyTypeName) {
        return getPaymentMethodTypePartyTypeByNames(eea, paymentMethodTypeName, partyTypeName,
                EntityPermission.READ_ONLY);
    }

    public PaymentMethodTypePartyType getPaymentMethodTypePartyTypeByNamesForUpdate(final ExecutionErrorAccumulator eea,
            final String paymentMethodTypeName, final String partyTypeName) {
        return getPaymentMethodTypePartyTypeByNames(eea, paymentMethodTypeName, partyTypeName,
                EntityPermission.READ_WRITE);
    }

    public PaymentMethodTypePartyType getPaymentMethodTypePartyTypeByUniversalSpec(final ExecutionErrorAccumulator eea,
            final PaymentMethodTypePartyTypeUniversalSpec universalSpec, boolean allowDefault, final EntityPermission entityPermission) {
        PaymentMethodTypePartyType paymentMethodTypePartyType = null;
        var paymentMethodTypePartyTypeControl = Session.getModelController(PaymentMethodTypePartyTypeControl.class);
        var paymentMethodTypeName = universalSpec.getPaymentMethodTypeName();
        var partyTypeName = universalSpec.getPartyTypeName();
        var fullySpecifiedName = paymentMethodTypeName != null && partyTypeName != null;
        var parameterCount = (fullySpecifiedName ? 1 : 0) + EntityInstanceLogic.getInstance().countPossibleEntitySpecs(universalSpec);

        if(parameterCount == 0) {
            if(allowDefault) {
                if(paymentMethodTypeName != null) {
                    var paymentMethodType = PaymentMethodTypeLogic.getInstance().getPaymentMethodTypeByName(eea,
                            paymentMethodTypeName);

                    if(!eea.hasExecutionErrors()) {
                        paymentMethodTypePartyType = paymentMethodTypePartyTypeControl.getDefaultPaymentMethodTypePartyType(paymentMethodType, entityPermission);

                        if(paymentMethodTypePartyType == null) {
                            handleExecutionError(UnknownDefaultPaymentMethodTypePartyTypeException.class, eea, ExecutionErrors.UnknownDefaultPaymentMethodTypePartyType.name());
                        }
                    }
                } else {
                    handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
                }
            } else {
                handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
            }
        } else if(parameterCount == 1) {
            if(fullySpecifiedName) {
                var paymentMethodType = PaymentMethodTypeLogic.getInstance().getPaymentMethodTypeByName(eea, paymentMethodTypeName);
                var partyType = PartyLogic.getInstance().getPartyTypeByName(eea, partyTypeName);

                if(!eea.hasExecutionErrors()) {
                    paymentMethodTypePartyType = getPaymentMethodTypePartyType(eea, paymentMethodType, partyType, entityPermission);
                }
            } else {
                var entityInstance = EntityInstanceLogic.getInstance().getEntityInstance(eea, universalSpec,
                        ComponentVendors.ECHO_THREE.name(), EntityTypes.PaymentMethodTypePartyType.name());

                if(!eea.hasExecutionErrors()) {
                    paymentMethodTypePartyType = paymentMethodTypePartyTypeControl.getPaymentMethodTypePartyTypeByEntityInstance(entityInstance, entityPermission);
                }
            }
        } else {
            handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
        }

        return paymentMethodTypePartyType;
    }

    public PaymentMethodTypePartyType getPaymentMethodTypePartyTypeByUniversalSpec(final ExecutionErrorAccumulator eea,
            final PaymentMethodTypePartyTypeUniversalSpec universalSpec, boolean allowDefault) {
        return getPaymentMethodTypePartyTypeByUniversalSpec(eea, universalSpec, allowDefault, EntityPermission.READ_ONLY);
    }

    public PaymentMethodTypePartyType getPaymentMethodTypePartyTypeByUniversalSpecForUpdate(final ExecutionErrorAccumulator eea,
            final PaymentMethodTypePartyTypeUniversalSpec universalSpec, boolean allowDefault) {
        return getPaymentMethodTypePartyTypeByUniversalSpec(eea, universalSpec, allowDefault, EntityPermission.READ_WRITE);
    }

    public void deletePaymentMethodTypePartyType(final ExecutionErrorAccumulator eea, final PaymentMethodTypePartyType paymentMethodTypePartyType,
            final BasePK deletedBy) {
        var paymentMethodTypePartyTypeControl = Session.getModelController(PaymentMethodTypePartyTypeControl.class);

        paymentMethodTypePartyTypeControl.deletePaymentMethodTypePartyType(paymentMethodTypePartyType, deletedBy);
    }

}
