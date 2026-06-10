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

import com.echothree.control.user.returnpolicy.common.form.GetReturnPolicyReasonsForm;
import com.echothree.control.user.returnpolicy.common.result.ReturnPolicyResultFactory;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.returnpolicy.server.control.ReturnPolicyControl;
import com.echothree.model.control.returnpolicy.server.logic.ReturnKindLogic;
import com.echothree.model.control.returnpolicy.server.logic.ReturnPolicyLogic;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.returnpolicy.server.entity.ReturnPolicy;
import com.echothree.model.data.returnpolicy.server.entity.ReturnPolicyReason;
import com.echothree.model.data.returnpolicy.server.entity.ReturnReason;
import com.echothree.model.data.returnpolicy.server.factory.ReturnPolicyReasonFactory;
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
public class GetReturnPolicyReasonsCommand
        extends BasePaginatedMultipleEntitiesCommand<ReturnPolicyReason, GetReturnPolicyReasonsForm> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), List.of(
                        new SecurityRoleDefinition(SecurityRoleGroups.ReturnPolicyReason.name(), SecurityRoles.List.name())
                ))
        ));

        FORM_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("ReturnKindName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("ReturnPolicyName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("ReturnReasonName", FieldType.ENTITY_NAME, false, null, null)
        );
    }
    
    @Inject
    ReturnPolicyControl returnPolicyControl;

    @Inject
    ReturnKindLogic returnKindLogic;

    @Inject
    ReturnPolicyLogic returnPolicyLogic;

    /** Creates a new instance of GetReturnPolicyReasonsCommand */
    public GetReturnPolicyReasonsCommand() {
        super(COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, true);
    }

    ReturnPolicy returnPolicy;
    ReturnReason returnReason;

    @Override
    protected void handleForm() {
        var returnKindName = form.getReturnKindName();
        var returnKind = returnKindLogic.getReturnKindByName(this, returnKindName);

        if(!hasExecutionErrors()) {
            var returnPolicyName = form.getReturnPolicyName();
            var returnReasonName = form.getReturnReasonName();
            var parameterCount = (returnPolicyName != null ? 1 : 0) + (returnReasonName != null ? 1 : 0);

            if(parameterCount == 1) {
                if(returnPolicyName != null) {
                    returnPolicy = returnPolicyLogic.getReturnPolicyByName(this, returnKind, returnPolicyName);
                } else {
                    returnReason = returnPolicyControl.getReturnReasonByName(returnKind, returnReasonName);

                    if(returnReason == null) {
                        addExecutionError(ExecutionErrors.UnknownReturnReasonName.name(), returnReasonName);
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
            if(returnPolicy != null) {
                total = returnPolicyControl.countReturnPolicyReasonByReturnPolicy(returnPolicy);
            } else {
                total = returnPolicyControl.countReturnPolicyReasonByReturnReason(returnReason);
            }
        }

        return total;
    }

    @Override
    protected Collection<ReturnPolicyReason> getEntities() {
        Collection<ReturnPolicyReason> entities = null;

        if(!hasExecutionErrors()) {
            if(returnPolicy != null) {
                entities = returnPolicyControl.getReturnPolicyReasonsByReturnPolicy(returnPolicy);
            } else {
                entities = returnPolicyControl.getReturnPolicyReasonsByReturnReason(returnReason);
            }
        }

        return entities;
    }

    @Override
    protected BaseResult getResult(Collection<ReturnPolicyReason> entities) {
        var result = ReturnPolicyResultFactory.getGetReturnPolicyReasonsResult();

        if(entities != null) {
            var userVisit = getUserVisit();

            if(returnPolicy != null) {
                result.setReturnPolicy(returnPolicyControl.getReturnPolicyTransfer(userVisit, returnPolicy));
            } else {
                result.setReturnReason(returnPolicyControl.getReturnReasonTransfer(userVisit, returnReason));
            }

            if(session.hasLimit(ReturnPolicyReasonFactory.class)) {
                result.setReturnPolicyReasonCount(getTotalEntities());
            }

            result.setReturnPolicyReasons(returnPolicyControl.getReturnPolicyReasonTransfers(userVisit, entities));
        }

        return result;
    }
    
}
