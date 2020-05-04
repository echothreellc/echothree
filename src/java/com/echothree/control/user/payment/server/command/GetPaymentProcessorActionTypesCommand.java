// --------------------------------------------------------------------------------
// Copyright 2002-2020 Echo Three, LLC
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

import com.echothree.control.user.payment.common.form.GetPaymentProcessorActionTypesForm;
import com.echothree.control.user.payment.common.result.PaymentResultFactory;
import com.echothree.control.user.payment.common.result.GetPaymentProcessorActionTypesResult;
import com.echothree.model.control.payment.server.PaymentProcessorActionTypeControl;
import com.echothree.model.data.payment.server.entity.PaymentProcessorActionType;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.server.control.BaseMultipleEntitiesCommand;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class GetPaymentProcessorActionTypesCommand
        extends BaseMultipleEntitiesCommand<PaymentProcessorActionType, GetPaymentProcessorActionTypesForm> {
    
    // No COMMAND_SECURITY_DEFINITION, anyone may execute this command.
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;

    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                ));
    }
    
    /** Creates a new instance of GetPaymentProcessorActionTypesCommand */
    public GetPaymentProcessorActionTypesCommand(UserVisitPK userVisitPK, GetPaymentProcessorActionTypesForm form) {
        super(userVisitPK, form, null, FORM_FIELD_DEFINITIONS, true);
    }
    
    @Override
    protected Collection<PaymentProcessorActionType> getEntities() {
        var paymentProcessorActionTypeControl = (PaymentProcessorActionTypeControl)Session.getModelController(PaymentProcessorActionTypeControl.class);
        
        return paymentProcessorActionTypeControl.getPaymentProcessorActionTypes();
    }
    
    @Override
    protected BaseResult getTransfers(Collection<PaymentProcessorActionType> entities) {
        GetPaymentProcessorActionTypesResult result = PaymentResultFactory.getGetPaymentProcessorActionTypesResult();
        var paymentProcessorActionTypeControl = (PaymentProcessorActionTypeControl)Session.getModelController(PaymentProcessorActionTypeControl.class);
        
        result.setPaymentProcessorActionTypes(paymentProcessorActionTypeControl.getPaymentProcessorActionTypeTransfers(getUserVisit(), entities));
        
        return result;
    }
    
}
