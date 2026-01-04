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

package com.echothree.control.user.payment.server.command;

import com.echothree.control.user.payment.common.form.GetPaymentProcessorTypeForm;
import com.echothree.control.user.payment.common.result.PaymentResultFactory;
import com.echothree.model.control.payment.server.control.PaymentProcessorTypeControl;
import com.echothree.model.control.payment.server.logic.PaymentProcessorTypeLogic;
import com.echothree.model.control.core.common.EventTypes;
import com.echothree.model.data.payment.server.entity.PaymentProcessorType;
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
public class GetPaymentProcessorTypeCommand
        extends BaseSingleEntityCommand<PaymentProcessorType, GetPaymentProcessorTypeForm> {
    
    // No COMMAND_SECURITY_DEFINITION, anyone may execute this command.
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("PaymentProcessorTypeName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("EntityRef", FieldType.ENTITY_REF, false, null, null),
                new FieldDefinition("Uuid", FieldType.UUID, false, null, null)
                ));
    }
    
    /** Creates a new instance of GetPaymentProcessorTypeCommand */
    public GetPaymentProcessorTypeCommand() {
        super(null, FORM_FIELD_DEFINITIONS, true);
    }
    
    @Override
    protected PaymentProcessorType getEntity() {
        var paymentProcessorType = PaymentProcessorTypeLogic.getInstance().getPaymentProcessorTypeByUniversalSpec(this, form, true);

        if(paymentProcessorType != null) {
            sendEvent(paymentProcessorType.getPrimaryKey(), EventTypes.READ, null, null, getPartyPK());
        }

        return paymentProcessorType;
    }
    
    @Override
    protected BaseResult getResult(PaymentProcessorType paymentProcessorType) {
        var paymentProcessorTypeControl = Session.getModelController(PaymentProcessorTypeControl.class);
        var result = PaymentResultFactory.getGetPaymentProcessorTypeResult();

        if(paymentProcessorType != null) {
            result.setPaymentProcessorType(paymentProcessorTypeControl.getPaymentProcessorTypeTransfer(getUserVisit(), paymentProcessorType));
        }

        return result;
    }
    
}
