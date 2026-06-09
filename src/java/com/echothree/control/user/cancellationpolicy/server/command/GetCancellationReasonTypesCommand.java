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

package com.echothree.control.user.cancellationpolicy.server.command;

import com.echothree.control.user.cancellationpolicy.common.form.GetCancellationReasonTypesForm;
import com.echothree.control.user.cancellationpolicy.common.result.CancellationPolicyResultFactory;
import com.echothree.model.control.cancellationpolicy.server.control.CancellationPolicyControl;
import com.echothree.model.control.cancellationpolicy.server.logic.CancellationKindLogic;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.cancellationpolicy.server.entity.CancellationKind;
import com.echothree.model.data.cancellationpolicy.server.entity.CancellationReason;
import com.echothree.model.data.cancellationpolicy.server.entity.CancellationReasonType;
import com.echothree.model.data.cancellationpolicy.server.entity.CancellationType;
import com.echothree.model.data.cancellationpolicy.server.factory.CancellationReasonTypeFactory;
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
public class GetCancellationReasonTypesCommand
        extends BasePaginatedMultipleEntitiesCommand<CancellationReasonType, GetCancellationReasonTypesForm> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), List.of(
                        new SecurityRoleDefinition(SecurityRoleGroups.CancellationReasonType.name(), SecurityRoles.List.name())
                        ))
                ));
        
        FORM_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("CancellationKindName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("CancellationReasonName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("CancellationTypeName", FieldType.ENTITY_NAME, false, null, null)
                );
    }

    @Inject
    CancellationPolicyControl cancellationPolicyControl;

    @Inject
    CancellationKindLogic cancellationKindLogic;
    
    /** Creates a new instance of GetCancellationReasonTypesCommand */
    public GetCancellationReasonTypesCommand() {
        super(COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, true);
    }

    CancellationKind cancellationKind;
    CancellationReason cancellationReason;
    CancellationType cancellationType;

    @Override
    protected void handleForm() {
        var cancellationKindName = form.getCancellationKindName();
        var cancellationReasonName = form.getCancellationReasonName();
        var cancellationTypeName = form.getCancellationTypeName();
        var parameterCount = (cancellationReasonName != null ? 1 : 0) + (cancellationTypeName != null ? 1 : 0);

        if(parameterCount == 1) {
            cancellationKind = cancellationKindLogic.getCancellationKindByName(this, cancellationKindName);

            if(!hasExecutionErrors()) {
                if(cancellationReasonName != null) {
                    cancellationReason = cancellationPolicyControl.getCancellationReasonByName(cancellationKind, cancellationReasonName);

                    if(cancellationReason == null) {
                        addExecutionError(ExecutionErrors.UnknownCancellationReasonName.name(), cancellationReasonName);
                    }
                } else {
                    cancellationType = cancellationPolicyControl.getCancellationTypeByName(cancellationKind, cancellationTypeName);

                    if(cancellationType == null) {
                        addExecutionError(ExecutionErrors.UnknownCancellationTypeName.name(), cancellationTypeName);
                    }
                }
            }
        } else {
            addExecutionError(ExecutionErrors.InvalidParameterCount.name());
        }
    }

    @Override
    protected Long getTotalEntities() {
        Long total = null;

        if(!hasExecutionErrors()) {
            if(cancellationReason != null) {
                total = cancellationPolicyControl.countCancellationReasonTypesByCancellationReason(cancellationReason);
            } else {
                total = cancellationPolicyControl.countCancellationReasonTypesByCancellationType(cancellationType);
            }
        }

        return total;
    }

    @Override
    protected Collection<CancellationReasonType> getEntities() {
        Collection<CancellationReasonType> entities = null;

        if(!hasExecutionErrors()) {
            if(cancellationReason != null) {
                entities = cancellationPolicyControl.getCancellationReasonTypesByCancellationReason(cancellationReason);
            } else {
                entities = cancellationPolicyControl.getCancellationReasonTypesByCancellationType(cancellationType);
            }
        }

        return entities;
    }

    @Override
    protected BaseResult getResult(Collection<CancellationReasonType> entities) {
        var result = CancellationPolicyResultFactory.getGetCancellationReasonTypesResult();

        if(entities != null) {
            var userVisit = getUserVisit();

            if(cancellationReason != null) {
                result.setCancellationReason(cancellationPolicyControl.getCancellationReasonTransfer(userVisit, cancellationReason));
            } else {
                result.setCancellationType(cancellationPolicyControl.getCancellationTypeTransfer(userVisit, cancellationType));
            }

            if(session.hasLimit(CancellationReasonTypeFactory.class)) {
                result.setCancellationReasonTypeCount(getTotalEntities());
            }

            result.setCancellationReasonTypes(cancellationPolicyControl.getCancellationReasonTypeTransfers(userVisit, entities));
        }

        return result;
    }
    
}
