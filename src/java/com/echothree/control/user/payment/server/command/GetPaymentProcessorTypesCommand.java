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

import com.echothree.control.user.payment.common.form.GetPaymentProcessorTypesForm;
import com.echothree.control.user.payment.common.result.PaymentResultFactory;
import com.echothree.model.control.payment.server.control.PaymentProcessorTypeControl;
import com.echothree.model.data.payment.server.entity.PaymentProcessorType;
import com.echothree.model.data.payment.server.factory.PaymentProcessorTypeFactory;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.server.control.BasePaginatedMultipleEntitiesCommand;
import java.util.Collection;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class GetPaymentProcessorTypesCommand
        extends BasePaginatedMultipleEntitiesCommand<PaymentProcessorType, GetPaymentProcessorTypesForm> {
    
    @Inject
    PaymentProcessorTypeControl paymentProcessorTypeControl;
    
    // No COMMAND_SECURITY_DEFINITION, anyone may execute this command.
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;

    static {
        FORM_FIELD_DEFINITIONS = List.of();
    }
    
    /** Creates a new instance of GetPaymentProcessorTypesCommand */
    public GetPaymentProcessorTypesCommand() {
        super(null, FORM_FIELD_DEFINITIONS, true);
    }
    
    @Override
    protected void handleForm() {
        // No form fields.
    }

    @Override
    protected Long getTotalEntities() {
        return paymentProcessorTypeControl.countPaymentProcessorTypes();
    }

    @Override
    protected Collection<PaymentProcessorType> getEntities() {
        return paymentProcessorTypeControl.getPaymentProcessorTypes();
    }
    
    @Override
    protected BaseResult getResult(Collection<PaymentProcessorType> entities) {
        var result = PaymentResultFactory.getGetPaymentProcessorTypesResult();

        if(entities != null) {
            if(session.hasLimit(PaymentProcessorTypeFactory.class)) {
                result.setPaymentProcessorTypeCount(getTotalEntities());
            }

            result.setPaymentProcessorTypes(paymentProcessorTypeControl.getPaymentProcessorTypeTransfers(getUserVisit(), entities));
        }

        return result;
    }
    
}
