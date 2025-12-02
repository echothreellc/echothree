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

package com.echothree.control.user.payment.server.command;

import com.echothree.control.user.payment.common.form.GetPaymentProcessorActionTypeForm;
import com.echothree.control.user.payment.common.result.PaymentResultFactory;
import com.echothree.model.control.payment.server.control.PaymentProcessorActionTypeControl;
import com.echothree.model.control.payment.server.logic.PaymentProcessorActionTypeLogic;
import com.echothree.model.control.core.common.EventTypes;
import com.echothree.model.data.payment.server.entity.PaymentProcessorActionType;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.server.control.BaseSingleEntityCommand;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.enterprise.context.Dependent;

@Dependent
public class GetPaymentProcessorActionTypeCommand
        extends BaseSingleEntityCommand<PaymentProcessorActionType, GetPaymentProcessorActionTypeForm> {
    
    // No COMMAND_SECURITY_DEFINITION, anyone may execute this command.
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("PaymentProcessorActionTypeName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("EntityRef", FieldType.ENTITY_REF, false, null, null),
                new FieldDefinition("Uuid", FieldType.UUID, false, null, null)
                ));
    }
    
    /** Creates a new instance of GetPaymentProcessorActionTypeCommand */
    public GetPaymentProcessorActionTypeCommand() {
        super(null, FORM_FIELD_DEFINITIONS, true);
    }
    
    @Override
    protected PaymentProcessorActionType getEntity() {
        var paymentProcessorActionType = PaymentProcessorActionTypeLogic.getInstance().getPaymentProcessorActionTypeByUniversalSpec(this, form, true);

        if(paymentProcessorActionType != null) {
            sendEvent(paymentProcessorActionType.getPrimaryKey(), EventTypes.READ, null, null, getPartyPK());
        }

        return paymentProcessorActionType;
    }
    
    @Override
    protected BaseResult getResult(PaymentProcessorActionType paymentProcessorActionType) {
        var paymentProcessorActionTypeControl = Session.getModelController(PaymentProcessorActionTypeControl.class);
        var result = PaymentResultFactory.getGetPaymentProcessorActionTypeResult();

        if(paymentProcessorActionType != null) {
            result.setPaymentProcessorActionType(paymentProcessorActionTypeControl.getPaymentProcessorActionTypeTransfer(getUserVisit(), paymentProcessorActionType));
        }

        return result;
    }
    
}
