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

import com.echothree.control.user.payment.common.form.SetDefaultPaymentProcessorActionTypeForm;
import com.echothree.model.control.payment.server.PaymentProcessorActionTypeControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.payment.server.value.PaymentProcessorActionTypeDetailValue;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class SetDefaultPaymentProcessorActionTypeCommand
        extends BaseSimpleCommand<SetDefaultPaymentProcessorActionTypeForm> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                    new SecurityRoleDefinition(SecurityRoleGroups.PaymentProcessorActionType.name(), SecurityRoles.Edit.name())
                    )))
                )));
        
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("PaymentProcessorActionTypeName", FieldType.ENTITY_NAME, true, null, null)
                ));
    }
    
    /** Creates a new instance of SetDefaultPaymentProcessorActionTypeCommand */
    public SetDefaultPaymentProcessorActionTypeCommand(UserVisitPK userVisitPK, SetDefaultPaymentProcessorActionTypeForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        var paymentProcessorActionTypeControl = (PaymentProcessorActionTypeControl)Session.getModelController(PaymentProcessorActionTypeControl.class);
        String paymentProcessorActionTypeName = form.getPaymentProcessorActionTypeName();
        PaymentProcessorActionTypeDetailValue paymentProcessorActionTypeDetailValue = paymentProcessorActionTypeControl.getPaymentProcessorActionTypeDetailValueByNameForUpdate(paymentProcessorActionTypeName);
        
        if(paymentProcessorActionTypeDetailValue != null) {
            paymentProcessorActionTypeDetailValue.setIsDefault(Boolean.TRUE);
            paymentProcessorActionTypeControl.updatePaymentProcessorActionTypeFromValue(paymentProcessorActionTypeDetailValue, getPartyPK());
        } else {
            addExecutionError(ExecutionErrors.UnknownPaymentProcessorActionTypeName.name(), paymentProcessorActionTypeName);
        }
        
        return null;
    }
    
}