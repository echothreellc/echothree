// --------------------------------------------------------------------------------
// Copyright 2002-2018 Echo Three, LLC
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

import com.echothree.control.user.returnpolicy.remote.form.GetReturnPolicyReasonsForm;
import com.echothree.control.user.returnpolicy.remote.result.GetReturnPolicyReasonsResult;
import com.echothree.control.user.returnpolicy.remote.result.ReturnPolicyResultFactory;
import com.echothree.model.control.party.common.PartyConstants;
import com.echothree.model.control.returnpolicy.server.ReturnPolicyControl;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.returnpolicy.server.entity.ReturnKind;
import com.echothree.model.data.returnpolicy.server.entity.ReturnPolicy;
import com.echothree.model.data.returnpolicy.server.entity.ReturnReason;
import com.echothree.model.data.user.remote.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.remote.command.BaseResult;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class GetReturnPolicyReasonsCommand
        extends BaseSimpleCommand<GetReturnPolicyReasonsForm> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyConstants.PartyType_UTILITY, null),
                new PartyTypeDefinition(PartyConstants.PartyType_EMPLOYEE, Collections.unmodifiableList(Arrays.asList(
                    new SecurityRoleDefinition(SecurityRoleGroups.ReturnPolicyReason.name(), SecurityRoles.List.name())
                    )))
                )));

        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ReturnKindName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("ReturnPolicyName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("ReturnReasonName", FieldType.ENTITY_NAME, false, null, null)
                ));
    }
    
    /** Creates a new instance of GetReturnPolicyReasonsCommand */
    public GetReturnPolicyReasonsCommand(UserVisitPK userVisitPK, GetReturnPolicyReasonsForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, true);
    }
    
    @Override
    protected BaseResult execute() {
        ReturnPolicyControl returnPolicyControl = (ReturnPolicyControl)Session.getModelController(ReturnPolicyControl.class);
        GetReturnPolicyReasonsResult result = ReturnPolicyResultFactory.getGetReturnPolicyReasonsResult();
        String returnPolicyName = form.getReturnPolicyName();
        String returnReasonName = form.getReturnReasonName();
        int parameterCount = (returnReasonName != null? 1: 0) + (returnPolicyName != null? 1: 0);
        
        if(parameterCount == 1) {
            String returnKindName = form.getReturnKindName();
            ReturnKind returnKind = returnPolicyControl.getReturnKindByName(returnKindName);
            
            if(returnKind != null) {
                if(returnPolicyName != null) {
                    ReturnPolicy returnPolicy = returnPolicyControl.getReturnPolicyByName(returnKind, returnPolicyName);
                    
                    if(returnPolicy != null) {
                        result.setReturnPolicy(returnPolicyControl.getReturnPolicyTransfer(getUserVisit(), returnPolicy));
                        result.setReturnPolicyReasons(returnPolicyControl.getReturnPolicyReasonTransfersByReturnPolicy(getUserVisit(), returnPolicy));
                    } else {
                        addExecutionError(ExecutionErrors.UnknownReturnPolicyName.name(), returnPolicyName);
                    }
                } else if(returnKindName != null && returnReasonName != null) {
                    ReturnReason returnReason = returnPolicyControl.getReturnReasonByName(returnKind, returnReasonName);
                    
                    if(returnReason != null) {
                        result.setReturnReason(returnPolicyControl.getReturnReasonTransfer(getUserVisit(), returnReason));
                        result.setReturnPolicyReasons(returnPolicyControl.getReturnPolicyReasonTransfersByReturnReason(getUserVisit(), returnReason));
                    } else {
                        addExecutionError(ExecutionErrors.UnknownReturnReasonName.name(), returnReasonName);
                    }
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownReturnKindName.name(), returnKindName);
            }
        } else {
            addExecutionError(ExecutionErrors.InvalidParameterCount.name());
        }
        
        return result;
    }
    
}
