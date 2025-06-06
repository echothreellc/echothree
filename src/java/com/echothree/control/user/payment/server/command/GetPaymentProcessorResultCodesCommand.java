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

import com.echothree.control.user.payment.common.form.GetPaymentProcessorResultCodesForm;
import com.echothree.control.user.payment.common.result.PaymentResultFactory;
import com.echothree.model.control.payment.server.control.PaymentProcessorResultCodeControl;
import com.echothree.model.data.payment.server.entity.PaymentProcessorResultCode;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.server.control.BaseMultipleEntitiesCommand;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class GetPaymentProcessorResultCodesCommand
        extends BaseMultipleEntitiesCommand<PaymentProcessorResultCode, GetPaymentProcessorResultCodesForm> {
    
    // No COMMAND_SECURITY_DEFINITION, anyone may execute this command.
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;

    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                ));
    }
    
    /** Creates a new instance of GetPaymentProcessorResultCodesCommand */
    public GetPaymentProcessorResultCodesCommand() {
        super(null, FORM_FIELD_DEFINITIONS, true);
    }
    
    @Override
    protected Collection<PaymentProcessorResultCode> getEntities() {
        var paymentProcessorResultCodeControl = Session.getModelController(PaymentProcessorResultCodeControl.class);
        
        return paymentProcessorResultCodeControl.getPaymentProcessorResultCodes();
    }
    
    @Override
    protected BaseResult getResult(Collection<PaymentProcessorResultCode> entities) {
        var result = PaymentResultFactory.getGetPaymentProcessorResultCodesResult();
        var paymentProcessorResultCodeControl = Session.getModelController(PaymentProcessorResultCodeControl.class);
        
        result.setPaymentProcessorResultCodes(paymentProcessorResultCodeControl.getPaymentProcessorResultCodeTransfers(getUserVisit(), entities));
        
        return result;
    }
    
}
