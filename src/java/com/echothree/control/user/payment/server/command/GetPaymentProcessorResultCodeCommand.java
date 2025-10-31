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

import com.echothree.control.user.payment.common.form.GetPaymentProcessorResultCodeForm;
import com.echothree.control.user.payment.common.result.PaymentResultFactory;
import com.echothree.model.control.payment.server.control.PaymentProcessorResultCodeControl;
import com.echothree.model.control.payment.server.logic.PaymentProcessorResultCodeLogic;
import com.echothree.model.control.core.common.EventTypes;
import com.echothree.model.data.payment.server.entity.PaymentProcessorResultCode;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.server.control.BaseSingleEntityCommand;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class GetPaymentProcessorResultCodeCommand
        extends BaseSingleEntityCommand<PaymentProcessorResultCode, GetPaymentProcessorResultCodeForm> {
    
    // No COMMAND_SECURITY_DEFINITION, anyone may execute this command.
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("PaymentProcessorResultCodeName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("EntityRef", FieldType.ENTITY_REF, false, null, null),
                new FieldDefinition("Uuid", FieldType.UUID, false, null, null)
                ));
    }
    
    /** Creates a new instance of GetPaymentProcessorResultCodeCommand */
    public GetPaymentProcessorResultCodeCommand() {
        super(null, FORM_FIELD_DEFINITIONS, true);
    }
    
    @Override
    protected PaymentProcessorResultCode getEntity() {
        var paymentProcessorResultCode = PaymentProcessorResultCodeLogic.getInstance().getPaymentProcessorResultCodeByUniversalSpec(this, form, true);

        if(paymentProcessorResultCode != null) {
            sendEvent(paymentProcessorResultCode.getPrimaryKey(), EventTypes.READ, null, null, getPartyPK());
        }

        return paymentProcessorResultCode;
    }
    
    @Override
    protected BaseResult getResult(PaymentProcessorResultCode paymentProcessorResultCode) {
        var paymentProcessorResultCodeControl = Session.getModelController(PaymentProcessorResultCodeControl.class);
        var result = PaymentResultFactory.getGetPaymentProcessorResultCodeResult();

        if(paymentProcessorResultCode != null) {
            result.setPaymentProcessorResultCode(paymentProcessorResultCodeControl.getPaymentProcessorResultCodeTransfer(getUserVisit(), paymentProcessorResultCode));
        }

        return result;
    }
    
}
