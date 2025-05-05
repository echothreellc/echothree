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

import com.echothree.control.user.payment.common.form.GetPaymentProcessorTypeCodeForm;
import com.echothree.control.user.payment.common.result.PaymentResultFactory;
import com.echothree.model.control.core.common.EventTypes;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.payment.server.control.PaymentProcessorTypeCodeControl;
import com.echothree.model.control.payment.server.logic.PaymentProcessorTypeCodeLogic;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.payment.server.entity.PaymentProcessorTypeCode;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseSingleEntityCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class GetPaymentProcessorTypeCodeCommand
        extends BaseSingleEntityCommand<PaymentProcessorTypeCode, GetPaymentProcessorTypeCodeForm> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;

    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.PaymentProcessorTypeCode.name(), SecurityRoles.Review.name())
                )))
        )));

        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("PaymentProcessorTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("PaymentProcessorTypeCodeTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("PaymentProcessorTypeCodeName", FieldType.ENTITY_NAME, true, null, null)
        ));
    }

    /** Creates a new instance of GetPaymentProcessorTypeCodeCommand */
    public GetPaymentProcessorTypeCodeCommand() {
        super(COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, true);
    }

    @Override
    protected PaymentProcessorTypeCode getEntity() {
        var paymentProcessorTypeCode = PaymentProcessorTypeCodeLogic.getInstance().getPaymentProcessorTypeCodeByNames(this,
                form.getPaymentProcessorTypeName(), form.getPaymentProcessorTypeCodeTypeName(), form.getPaymentProcessorTypeCodeName());

        if(paymentProcessorTypeCode != null) {
            sendEvent(paymentProcessorTypeCode.getPrimaryKey(), EventTypes.READ, null, null, getPartyPK());
        }

        return paymentProcessorTypeCode;
    }

    @Override
    protected BaseResult getResult(PaymentProcessorTypeCode paymentProcessorTypeCode) {
        var paymentProcessorTypeCodeControl = Session.getModelController(PaymentProcessorTypeCodeControl.class);
        var result = PaymentResultFactory.getGetPaymentProcessorTypeCodeResult();

        if(paymentProcessorTypeCode != null) {
            result.setPaymentProcessorTypeCode(paymentProcessorTypeCodeControl.getPaymentProcessorTypeCodeTransfer(getUserVisit(), paymentProcessorTypeCode));
        }

        return result;
    }

}
