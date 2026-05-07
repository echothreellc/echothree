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

import com.echothree.control.user.payment.common.form.GetPaymentMethodsForm;
import com.echothree.control.user.payment.common.result.PaymentResultFactory;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.payment.server.control.PaymentMethodControl;
import com.echothree.model.control.payment.server.control.PaymentMethodTypeControl;
import com.echothree.model.control.payment.server.logic.PaymentMethodTypeLogic;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.payment.server.entity.PaymentMethod;
import com.echothree.model.data.payment.server.entity.PaymentMethodType;
import com.echothree.model.data.payment.server.factory.PaymentMethodFactory;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BasePaginatedMultipleEntitiesCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import java.util.Collection;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class GetPaymentMethodsCommand
        extends BasePaginatedMultipleEntitiesCommand<PaymentMethod, GetPaymentMethodsForm> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;

    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), List.of(
                        new SecurityRoleDefinition(SecurityRoleGroups.PaymentMethod.name(), SecurityRoles.List.name())
                ))
        ));

        FORM_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("PaymentMethodTypeName", FieldType.ENTITY_NAME, false, null, null)
        );
    }

    @Inject
    PaymentMethodControl paymentMethodControl;

    @Inject
    PaymentMethodTypeControl paymentMethodTypeControl;

    @Inject
    PaymentMethodTypeLogic paymentMethodTypeLogic;

    /** Creates a new instance of GetPaymentMethodsCommand */
    public GetPaymentMethodsCommand() {
        super(COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, true);
    }

    private PaymentMethodType paymentMethodType;

    @Override
    protected void handleForm() {
        var paymentMethodTypeName = form.getPaymentMethodTypeName();

        paymentMethodType = paymentMethodTypeName == null ? null : paymentMethodTypeLogic.getPaymentMethodTypeByName(this, paymentMethodTypeName);
    }

    @Override
    protected Long getTotalEntities() {
        return hasExecutionErrors() ? null :
                paymentMethodType == null ? paymentMethodControl.countPaymentMethods() :
                        paymentMethodControl.countPaymentMethodsByPaymentMethodType(paymentMethodType);
    }

    @Override
    protected Collection<PaymentMethod> getEntities() {
        return hasExecutionErrors() ? null :
                paymentMethodType == null ? paymentMethodControl.getPaymentMethods() :
                        paymentMethodControl.getPaymentMethodsByPaymentMethodType(paymentMethodType);
    }

    @Override
    protected BaseResult getResult(Collection<PaymentMethod> entities) {
        var result = PaymentResultFactory.getGetPaymentMethodsResult();

        if(entities != null) {
            var userVisit = getUserVisit();

            if(paymentMethodType != null) {
                result.setPaymentMethodType(paymentMethodTypeControl.getPaymentMethodTypeTransfer(userVisit, paymentMethodType));
            }

            if(session.hasLimit(PaymentMethodFactory.class)) {
                result.setPaymentMethodCount(getTotalEntities());
            }

            result.setPaymentMethods(paymentMethodControl.getPaymentMethodTransfers(userVisit, entities));
        }

        return result;
    }
    
}
