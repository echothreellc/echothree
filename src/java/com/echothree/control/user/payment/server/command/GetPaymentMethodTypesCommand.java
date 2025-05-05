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

import com.echothree.control.user.payment.common.form.GetPaymentMethodTypesForm;
import com.echothree.control.user.payment.common.result.PaymentResultFactory;
import com.echothree.model.control.payment.server.control.PaymentMethodTypeControl;
import com.echothree.model.data.payment.server.entity.PaymentMethodType;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.server.control.BaseMultipleEntitiesCommand;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class GetPaymentMethodTypesCommand
        extends BaseMultipleEntitiesCommand<PaymentMethodType, GetPaymentMethodTypesForm> {
    
    // No COMMAND_SECURITY_DEFINITION, anyone may execute this command.
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;

    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                ));
    }
    
    /** Creates a new instance of GetPaymentMethodTypesCommand */
    public GetPaymentMethodTypesCommand() {
        super(null, FORM_FIELD_DEFINITIONS, true);
    }
    
    @Override
    protected Collection<PaymentMethodType> getEntities() {
        var paymentMethodTypeControl = Session.getModelController(PaymentMethodTypeControl.class);
        
        return paymentMethodTypeControl.getPaymentMethodTypes();
    }
    
    @Override
    protected BaseResult getResult(Collection<PaymentMethodType> entities) {
        var result = PaymentResultFactory.getGetPaymentMethodTypesResult();
        var paymentMethodTypeControl = Session.getModelController(PaymentMethodTypeControl.class);
        
        result.setPaymentMethodTypes(paymentMethodTypeControl.getPaymentMethodTypeTransfers(getUserVisit(), entities));
        
        return result;
    }
    
}
