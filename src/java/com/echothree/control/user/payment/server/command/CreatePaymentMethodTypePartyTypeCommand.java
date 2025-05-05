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

import com.echothree.control.user.payment.common.form.CreatePaymentMethodTypePartyTypeForm;
import com.echothree.control.user.payment.common.result.PaymentResultFactory;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.payment.server.logic.PaymentMethodTypePartyTypeLogic;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CreatePaymentMethodTypePartyTypeCommand
        extends BaseSimpleCommand<CreatePaymentMethodTypePartyTypeForm> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.PaymentMethodType.name(), SecurityRoles.PaymentMethodTypePartyType.name())
                )))
        )));

        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("PaymentMethodTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("PartyTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("PartyPaymentMethodWorkflowName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("ContactMechanismWorkflowName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null)
        ));
    }
    
    /** Creates a new instance of CreatePaymentMethodTypePartyTypeCommand */
    public CreatePaymentMethodTypePartyTypeCommand() {
        super(COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        var result = PaymentResultFactory.getCreatePaymentMethodTypePartyTypeResult();
        var paymentMethodTypeName = form.getPaymentMethodTypeName();
        var partyTypeName = form.getPartyTypeName();
        var partyPaymentMethodWorkflowName = form.getPartyPaymentMethodWorkflowName();
        var contactMechanismWorkflowName = form.getContactMechanismWorkflowName();
        var isDefault = Boolean.valueOf(form.getIsDefault());
        var sortOrder = Integer.valueOf(form.getSortOrder());

        var paymentMethodTypePartyType = PaymentMethodTypePartyTypeLogic.getInstance().createPaymentMethodTypePartyType(this,
                paymentMethodTypeName, partyTypeName, partyPaymentMethodWorkflowName, contactMechanismWorkflowName,
                isDefault, sortOrder, getPartyPK());

        if(paymentMethodTypePartyType != null && !hasExecutionErrors()) {
            result.setPaymentMethodTypeName(paymentMethodTypePartyType.getLastDetail().getPaymentMethodType().getLastDetail().getPaymentMethodTypeName());
            result.setPartyTypeName(paymentMethodTypePartyType.getLastDetail().getPartyType().getPartyTypeName());
            result.setEntityRef(paymentMethodTypePartyType.getPrimaryKey().getEntityRef());
        }
        
        return result;
    }
    
}
