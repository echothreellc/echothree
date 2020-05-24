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

import com.echothree.control.user.payment.common.form.GetPaymentProcessorsForm;
import com.echothree.control.user.payment.common.result.PaymentResultFactory;
import com.echothree.control.user.payment.common.result.GetPaymentProcessorsResult;
import com.echothree.model.control.payment.server.control.PaymentControl;
import com.echothree.model.data.payment.server.entity.PaymentProcessor;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.server.control.BaseMultipleEntitiesCommand;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class GetPaymentProcessorsCommand
        extends BaseMultipleEntitiesCommand<PaymentProcessor, GetPaymentProcessorsForm> {

    // No COMMAND_SECURITY_DEFINITION, anyone may execute this command.
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;

    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
        ));
    }

    /** Creates a new instance of GetPaymentProcessorsCommand */
    public GetPaymentProcessorsCommand(UserVisitPK userVisitPK, GetPaymentProcessorsForm form) {
        super(userVisitPK, form, null, FORM_FIELD_DEFINITIONS, true);
    }

    @Override
    protected Collection<PaymentProcessor> getEntities() {
        var paymentControl = (PaymentControl)Session.getModelController(PaymentControl.class);

        return paymentControl.getPaymentProcessors();
    }

    @Override
    protected BaseResult getTransfers(Collection<PaymentProcessor> entities) {
        GetPaymentProcessorsResult result = PaymentResultFactory.getGetPaymentProcessorsResult();
        var paymentControl = (PaymentControl)Session.getModelController(PaymentControl.class);

        result.setPaymentProcessors(paymentControl.getPaymentProcessorTransfers(getUserVisit(), entities));

        return result;
    }

}
