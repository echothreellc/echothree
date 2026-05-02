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

import com.echothree.control.user.payment.common.form.GetPaymentProcessorResultCodesForm;
import com.echothree.control.user.payment.common.result.PaymentResultFactory;
import com.echothree.model.control.payment.server.control.PaymentProcessorResultCodeControl;
import com.echothree.model.data.payment.server.entity.PaymentProcessorResultCode;
import com.echothree.model.data.payment.server.factory.PaymentProcessorResultCodeFactory;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.server.control.BasePaginatedMultipleEntitiesCommand;
import java.util.Collection;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class GetPaymentProcessorResultCodesCommand
        extends BasePaginatedMultipleEntitiesCommand<PaymentProcessorResultCode, GetPaymentProcessorResultCodesForm> {
    
    // No COMMAND_SECURITY_DEFINITION, anyone may execute this command.
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;

    static {
        FORM_FIELD_DEFINITIONS = List.of();
    }
    
    /** Creates a new instance of GetPaymentProcessorResultCodesCommand */
    public GetPaymentProcessorResultCodesCommand() {
        super(null, FORM_FIELD_DEFINITIONS, true);
    }
    
    @Inject
    PaymentProcessorResultCodeControl paymentProcessorResultCodeControl;

    @Override
    protected void handleForm() {
        // No form fields.
    }

    @Override
    protected Long getTotalEntities() {
        return paymentProcessorResultCodeControl.countPaymentProcessorResultCodes();
    }

    @Override
    protected Collection<PaymentProcessorResultCode> getEntities() {
        return paymentProcessorResultCodeControl.getPaymentProcessorResultCodes();
    }
    
    @Override
    protected BaseResult getResult(Collection<PaymentProcessorResultCode> entities) {
        var result = PaymentResultFactory.getGetPaymentProcessorResultCodesResult();
        
        if(entities != null) {
            if(session.hasLimit(PaymentProcessorResultCodeFactory.class)) {
                result.setPaymentProcessorResultCodeCount(getTotalEntities());
            }

            result.setPaymentProcessorResultCodes(paymentProcessorResultCodeControl.getPaymentProcessorResultCodeTransfers(getUserVisit(), entities));
        }
        
        return result;
    }
    
}
