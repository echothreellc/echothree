// --------------------------------------------------------------------------------
// Copyright 2002-2019 Echo Three, LLC
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

package com.echothree.control.user.returnpolicy.server.command;

import com.echothree.control.user.returnpolicy.common.form.GetReturnTypeShippingMethodsForm;
import com.echothree.control.user.returnpolicy.common.result.GetReturnTypeShippingMethodsResult;
import com.echothree.control.user.returnpolicy.common.result.ReturnPolicyResultFactory;
import com.echothree.model.control.party.common.PartyConstants;
import com.echothree.model.control.returnpolicy.server.ReturnPolicyControl;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.control.shipping.server.ShippingControl;
import com.echothree.model.data.returnpolicy.server.entity.ReturnKind;
import com.echothree.model.data.returnpolicy.server.entity.ReturnType;
import com.echothree.model.data.shipping.server.entity.ShippingMethod;
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

public class GetReturnTypeShippingMethodsCommand
        extends BaseSimpleCommand<GetReturnTypeShippingMethodsForm> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyConstants.PartyType_UTILITY, null),
                new PartyTypeDefinition(PartyConstants.PartyType_EMPLOYEE, Collections.unmodifiableList(Arrays.asList(
                    new SecurityRoleDefinition(SecurityRoleGroups.ReturnTypeShippingMethod.name(), SecurityRoles.List.name())
                    )))
                )));

        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ReturnKindName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("ReturnTypeName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("ShippingMethodName", FieldType.ENTITY_NAME, false, null, null)
                ));
    }
    
    /** Creates a new instance of GetReturnTypeShippingMethodsCommand */
    public GetReturnTypeShippingMethodsCommand(UserVisitPK userVisitPK, GetReturnTypeShippingMethodsForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, true);
    }
    
    @Override
    protected BaseResult execute() {
        var returnPolicyControl = (ReturnPolicyControl)Session.getModelController(ReturnPolicyControl.class);
        GetReturnTypeShippingMethodsResult result = ReturnPolicyResultFactory.getGetReturnTypeShippingMethodsResult();
        String returnKindName = form.getReturnKindName();
        String returnTypeName = form.getReturnTypeName();
        String shippingMethodName = form.getShippingMethodName();
        int parameterCount = (returnKindName != null && returnTypeName != null? 1: 0) + (shippingMethodName != null? 1: 0);
        
        if(parameterCount == 1) {
            if(returnKindName != null && returnTypeName != null) {
                ReturnKind returnKind = returnPolicyControl.getReturnKindByName(returnKindName);
                
                if(returnKind != null) {
                    ReturnType returnType = returnPolicyControl.getReturnTypeByName(returnKind, returnTypeName);
                    
                    if(returnType != null) {
                        result.setReturnType(returnPolicyControl.getReturnTypeTransfer(getUserVisit(), returnType));
                        result.setReturnTypeShippingMethods(returnPolicyControl.getReturnTypeShippingMethodTransfersByReturnType(getUserVisit(),
                                returnType));
                    } else {
                        addExecutionError(ExecutionErrors.UnknownReturnTypeName.name(), returnTypeName);
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownReturnKindName.name(), returnKindName);
                }
            } else if(shippingMethodName != null) {
                var shippingControl = (ShippingControl)Session.getModelController(ShippingControl.class);
                ShippingMethod shippingMethod = shippingControl.getShippingMethodByName(shippingMethodName);
                
                if(shippingMethod != null) {
                    result.setShippingMethod(shippingControl.getShippingMethodTransfer(getUserVisit(), shippingMethod));
                    result.setReturnTypeShippingMethods(returnPolicyControl.getReturnTypeShippingMethodTransfersByShippingMethod(getUserVisit(),
                            shippingMethod));
                } else {
                    addExecutionError(ExecutionErrors.UnknownShippingMethodName.name(), shippingMethodName);
                }
            }
        } else {
            addExecutionError(ExecutionErrors.InvalidParameterCount.name());
        }
        
        return result;
    }
    
}
