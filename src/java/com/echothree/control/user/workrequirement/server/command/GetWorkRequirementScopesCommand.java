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

package com.echothree.control.user.workrequirement.server.command;

import com.echothree.control.user.workrequirement.common.form.GetWorkRequirementScopesForm;
import com.echothree.control.user.workrequirement.common.result.WorkRequirementResultFactory;
import com.echothree.model.control.workeffort.server.control.WorkEffortControl;
import com.echothree.model.control.workrequirement.server.control.WorkRequirementControl;
import com.echothree.model.data.workeffort.server.entity.WorkEffortScope;
import com.echothree.model.data.workrequirement.server.entity.WorkRequirementScope;
import com.echothree.model.data.workrequirement.server.entity.WorkRequirementType;
import com.echothree.model.data.workrequirement.server.factory.WorkRequirementScopeFactory;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BasePaginatedMultipleEntitiesCommand;
import java.util.Collection;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class GetWorkRequirementScopesCommand
        extends BasePaginatedMultipleEntitiesCommand<WorkRequirementScope, GetWorkRequirementScopesForm> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("WorkEffortTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("WorkRequirementTypeName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("WorkEffortScopeName", FieldType.ENTITY_NAME, false, null, null)
        );
    }
    
    @Inject
    WorkEffortControl workEffortControl;

    @Inject
    WorkRequirementControl workRequirementControl;

    /** Creates a new instance of GetWorkRequirementScopesCommand */
    public GetWorkRequirementScopesCommand() {
        super(null, FORM_FIELD_DEFINITIONS, true);
    }

    private WorkRequirementType workRequirementType;
    private WorkEffortScope workEffortScope;

    @Override
    protected void handleForm() {
        var workEffortTypeName = form.getWorkEffortTypeName();
        var workEffortType = workEffortControl.getWorkEffortTypeByName(workEffortTypeName);

        if(workEffortType != null) {
            var workEffortScopeName = form.getWorkEffortScopeName();
            var workRequirementTypeName = form.getWorkRequirementTypeName();
            var parameterCount = (workEffortScopeName == null ? 0 : 1) + (workRequirementTypeName == null ? 0 : 1);

            if(parameterCount == 1) {
                if(workEffortScopeName != null) {
                    workEffortScope = workEffortControl.getWorkEffortScopeByName(workEffortType, workEffortScopeName);

                    if(workEffortScope == null) {
                        addExecutionError(ExecutionErrors.UnknownWorkEffortScopeName.name(), workEffortScopeName);
                    }
                } else {
                    workRequirementType = workRequirementControl.getWorkRequirementTypeByName(workEffortType, workRequirementTypeName);

                    if(workRequirementType == null) {
                        addExecutionError(ExecutionErrors.UnknownWorkRequirementTypeName.name(), workRequirementTypeName);
                    }
                }
            } else {
                addExecutionError(ExecutionErrors.InvalidParameterCount.name());
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownWorkEffortTypeName.name(), workEffortTypeName);
        }
    }

    @Override
    protected Long getTotalEntities() {
        Long totalEntities = null;

        if(!hasExecutionErrors()) {
            if(workEffortScope != null) {
                totalEntities = workRequirementControl.countWorkRequirementScopesByWorkEffortScope(workEffortScope);
            } else {
                totalEntities = workRequirementControl.countWorkRequirementScopesByWorkRequirementType(workRequirementType);
            }
        }

        return totalEntities;
    }

    @Override
    protected Collection<WorkRequirementScope> getEntities() {
        Collection<WorkRequirementScope> entities = null;

        if(!hasExecutionErrors()) {
            if(workEffortScope != null) {
                entities = workRequirementControl.getWorkRequirementScopesByWorkEffortScope(workEffortScope);
            } else {
                entities = workRequirementControl.getWorkRequirementScopesByWorkRequirementType(workRequirementType);
            }
        }

        return entities;
    }

    @Override
    protected BaseResult getResult(Collection<WorkRequirementScope> entities) {
        var result = WorkRequirementResultFactory.getGetWorkRequirementScopesResult();

        if(entities != null) {
            var userVisit = getUserVisit();

            if(workEffortScope != null) {
                result.setWorkEffortScope(workEffortControl.getWorkEffortScopeTransfer(userVisit, workEffortScope));
            } else {
                result.setWorkRequirementType(workRequirementControl.getWorkRequirementTypeTransfer(userVisit, workRequirementType));
            }

            if(session.hasLimit(WorkRequirementScopeFactory.class)) {
                result.setWorkRequirementScopeCount(getTotalEntities());
            }

            result.setWorkRequirementScopes(workRequirementControl.getWorkRequirementScopeTransfers(userVisit, entities));
        }

        return result;
    }

}
