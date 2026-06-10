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

package com.echothree.control.user.returnpolicy.server.command;

import com.echothree.control.user.returnpolicy.common.form.GetReturnReasonTypesForm;
import com.echothree.control.user.returnpolicy.common.result.ReturnPolicyResultFactory;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.returnpolicy.server.control.ReturnPolicyControl;
import com.echothree.model.control.returnpolicy.server.logic.ReturnKindLogic;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.returnpolicy.server.entity.ReturnKind;
import com.echothree.model.data.returnpolicy.server.entity.ReturnReason;
import com.echothree.model.data.returnpolicy.server.entity.ReturnReasonType;
import com.echothree.model.data.returnpolicy.server.entity.ReturnType;
import com.echothree.model.data.returnpolicy.server.factory.ReturnReasonTypeFactory;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.message.ExecutionErrors;
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
public class GetReturnReasonTypesCommand
        extends BasePaginatedMultipleEntitiesCommand<ReturnReasonType, GetReturnReasonTypesForm> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), List.of(
                        new SecurityRoleDefinition(SecurityRoleGroups.ReturnReasonType.name(), SecurityRoles.List.name())
                ))
        ));

        FORM_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("ReturnKindName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("ReturnReasonName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("ReturnTypeName", FieldType.ENTITY_NAME, false, null, null)
        );
    }

    @Inject
    ReturnPolicyControl returnPolicyControl;

    @Inject
    ReturnKindLogic returnKindLogic;

    /** Creates a new instance of GetReturnReasonTypesCommand */
    public GetReturnReasonTypesCommand() {
        super(COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, true);
    }

    ReturnKind returnKind;
    ReturnReason returnReason;
    ReturnType returnType;

    @Override
    protected void handleForm() {
        var returnKindName = form.getReturnKindName();

        returnKind = returnKindLogic.getReturnKindByName(this, returnKindName);

        if(!hasExecutionErrors()) {
            var returnReasonName = form.getReturnReasonName();
            var returnTypeName = form.getReturnTypeName();
            var parameterCount = (returnReasonName != null ? 1 : 0) + (returnTypeName != null ? 1 : 0);

            if(parameterCount == 1) {
                if(returnReasonName != null) {
                    returnReason = returnPolicyControl.getReturnReasonByName(returnKind, returnReasonName);

                    if(returnReason == null) {
                        addExecutionError(ExecutionErrors.UnknownReturnReasonName.name(), returnReasonName);
                    }
                } else {
                    returnType = returnPolicyControl.getReturnTypeByName(returnKind, returnTypeName);

                    if(returnType == null) {
                        addExecutionError(ExecutionErrors.UnknownReturnTypeName.name(), returnTypeName);
                    }
                }
            } else {
                addExecutionError(ExecutionErrors.InvalidParameterCount.name());
            }
        }
    }

    @Override
    protected Long getTotalEntities() {
        Long total = null;

        if(!hasExecutionErrors()) {
            if(returnReason != null) {
                total = returnPolicyControl.countReturnReasonTypesByReturnReason(returnReason);
            } else {
                total = returnPolicyControl.countReturnReasonTypesByReturnType(returnType);
            }
        }

        return total;
    }

    @Override
    protected Collection<ReturnReasonType> getEntities() {
        Collection<ReturnReasonType> entities = null;

        if(!hasExecutionErrors()) {
            if(returnReason != null) {
                entities = returnPolicyControl.getReturnReasonTypesByReturnReason(returnReason);
            } else {
                entities = returnPolicyControl.getReturnReasonTypesByReturnType(returnType);
            }
        }

        return entities;
    }

    @Override
    protected BaseResult getResult(Collection<ReturnReasonType> entities) {
        var result = ReturnPolicyResultFactory.getGetReturnReasonTypesResult();

        if(entities != null) {
            var userVisit = getUserVisit();

            if(returnReason != null) {
                result.setReturnReason(returnPolicyControl.getReturnReasonTransfer(userVisit, returnReason));
            } else {
                result.setReturnType(returnPolicyControl.getReturnTypeTransfer(userVisit, returnType));
            }

            if(session.hasLimit(ReturnReasonTypeFactory.class)) {
                result.setReturnReasonTypeCount(getTotalEntities());
            }

            result.setReturnReasonTypes(returnPolicyControl.getReturnReasonTypeTransfers(userVisit, entities));
        }

        return result;
    }

}
