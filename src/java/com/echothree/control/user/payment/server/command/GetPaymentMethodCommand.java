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

import com.echothree.control.user.payment.common.form.GetPaymentMethodForm;
import com.echothree.control.user.payment.common.result.PaymentResultFactory;
import com.echothree.model.control.core.common.EventTypes;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.payment.server.control.PaymentMethodControl;
import com.echothree.model.control.payment.server.logic.PaymentMethodLogic;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.payment.server.entity.PaymentMethod;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseSingleEntityCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class GetPaymentMethodCommand
        extends BaseSingleEntityCommand<PaymentMethod, GetPaymentMethodForm> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), List.of(
                        new SecurityRoleDefinition(SecurityRoleGroups.PaymentMethod.name(), SecurityRoles.Review.name())
                ))
        ));

        FORM_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("PaymentMethodName", FieldType.ENTITY_NAME, true, null, null)
        );
    }

    @Inject
    PaymentMethodControl paymentMethodControl;

    @Inject
    PaymentMethodLogic paymentMethodLogic;

    /** Creates a new instance of GetPaymentMethodCommand */
    public GetPaymentMethodCommand() {
        super(COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, true);
    }
    
    @Override
    protected PaymentMethod getEntity() {
        var paymentMethodName = form.getPaymentMethodName();
        var paymentMethod = paymentMethodLogic.getPaymentMethodByName(this, paymentMethodName);

        if(paymentMethod != null) {
            sendEvent(paymentMethod.getPrimaryKey(), EventTypes.READ, null, null, getPartyPK());
        }

        return paymentMethod;
    }

    @Override
    protected BaseResult getResult(PaymentMethod paymentMethod) {
        var result = PaymentResultFactory.getGetPaymentMethodResult();

        if(paymentMethod != null) {
            result.setPaymentMethod(paymentMethodControl.getPaymentMethodTransfer(getUserVisit(), paymentMethod));
        }

        return result;
    }
    
}
