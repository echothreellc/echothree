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

import com.echothree.control.user.cancellationpolicy.common.form.GetCancellationPolicyReasonsForm;
import com.echothree.control.user.cancellationpolicy.common.result.CancellationPolicyResultFactory;
import com.echothree.model.control.cancellationpolicy.server.control.CancellationPolicyControl;
import com.echothree.model.control.cancellationpolicy.server.logic.CancellationKindLogic;
import com.echothree.model.control.cancellationpolicy.server.logic.CancellationPolicyLogic;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.cancellationpolicy.server.entity.CancellationPolicy;
import com.echothree.model.data.cancellationpolicy.server.entity.CancellationPolicyReason;
import com.echothree.model.data.cancellationpolicy.server.entity.CancellationReason;
import com.echothree.model.data.cancellationpolicy.server.factory.CancellationPolicyReasonFactory;
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
public class GetCancellationPolicyReasonsCommand
        extends BasePaginatedMultipleEntitiesCommand<CancellationPolicyReason, GetCancellationPolicyReasonsForm> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), List.of(
                        new SecurityRoleDefinition(SecurityRoleGroups.CancellationPolicyReason.name(), SecurityRoles.List.name())
                ))
        ));
        
        FORM_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("CancellationKindName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("CancellationPolicyName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("CancellationReasonName", FieldType.ENTITY_NAME, false, null, null)
        );
    }
    
    @Inject
    CancellationPolicyControl cancellationPolicyControl;

    @Inject
    CancellationKindLogic cancellationKindLogic;

    @Inject
    CancellationPolicyLogic cancellationPolicyLogic;

    /** Creates a new instance of GetCancellationPolicyReasonsCommand */
    public GetCancellationPolicyReasonsCommand() {
        super(COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, true);
    }

    CancellationPolicy cancellationPolicy;
    CancellationReason cancellationReason;

    @Override
    protected void handleForm() {
        var cancellationKindName = form.getCancellationKindName();
        var cancellationKind = cancellationKindLogic.getCancellationKindByName(this, cancellationKindName);

        if(!hasExecutionErrors()) {
            var cancellationPolicyName = form.getCancellationPolicyName();
            var cancellationReasonName = form.getCancellationReasonName();
            var parameterCount = (cancellationReasonName != null ? 1 : 0) + (cancellationPolicyName != null ? 1 : 0);

            if(parameterCount == 1) {
                if(cancellationPolicyName != null) {
                    cancellationPolicy = cancellationPolicyLogic.getCancellationPolicyByName(this, cancellationKind, cancellationPolicyName);
                } else {
                    cancellationReason = cancellationPolicyControl.getCancellationReasonByName(cancellationKind, cancellationReasonName);

                    if(cancellationReason == null) {
                        addExecutionError(ExecutionErrors.UnknownCancellationReasonName.name(), cancellationReasonName);
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
            if(cancellationPolicy != null) {
                total = cancellationPolicyControl.countCancellationPolicyReasonsByCancellationPolicy(cancellationPolicy);
            } else {
                total = cancellationPolicyControl.countCancellationPolicyReasonsByCancellationReason(cancellationReason);
            }
        }

        return total;
    }

    @Override
    protected Collection<CancellationPolicyReason> getEntities() {
        Collection<CancellationPolicyReason> entities = null;

        if(!hasExecutionErrors()) {
            if(cancellationPolicy != null) {
                entities = cancellationPolicyControl.getCancellationPolicyReasonsByCancellationPolicy(cancellationPolicy);
            } else {
                entities = cancellationPolicyControl.getCancellationPolicyReasonsByCancellationReason(cancellationReason);
            }
        }

        return entities;
    }

    @Override
    protected BaseResult getResult(Collection<CancellationPolicyReason> entities) {
        var result = CancellationPolicyResultFactory.getGetCancellationPolicyReasonsResult();

        if(entities != null) {
            var userVisit = getUserVisit();

            if(cancellationPolicy != null) {
                result.setCancellationPolicy(cancellationPolicyControl.getCancellationPolicyTransfer(userVisit, cancellationPolicy));
            } else {
                result.setCancellationReason(cancellationPolicyControl.getCancellationReasonTransfer(userVisit, cancellationReason));
            }

            if(session.hasLimit(CancellationPolicyReasonFactory.class)) {
                result.setCancellationPolicyReasonsCount(getTotalEntities());
            }

            result.setCancellationPolicyReasons(cancellationPolicyControl.getCancellationPolicyReasonTransfers(userVisit, entities));
        }

        return result;
    }
    
}
