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

import com.echothree.control.user.workrequirement.common.form.GetWorkRequirementTypesForm;
import com.echothree.control.user.workrequirement.common.result.WorkRequirementResultFactory;
import com.echothree.model.control.workeffort.server.control.WorkEffortControl;
import com.echothree.model.control.workrequirement.server.control.WorkRequirementControl;
import com.echothree.model.data.workeffort.server.entity.WorkEffortType;
import com.echothree.model.data.workrequirement.server.entity.WorkRequirementType;
import com.echothree.model.data.workrequirement.server.factory.WorkRequirementTypeFactory;
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
public class GetWorkRequirementTypesCommand
        extends BasePaginatedMultipleEntitiesCommand<WorkRequirementType, GetWorkRequirementTypesForm> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("WorkEffortTypeName", FieldType.ENTITY_NAME, true, null, null)
        );
    }

    @Inject
    WorkEffortControl workEffortControl;

    @Inject
    WorkRequirementControl workRequirementControl;

    /** Creates a new instance of GetWorkRequirementTypesCommand */
    public GetWorkRequirementTypesCommand() {
        super(null, FORM_FIELD_DEFINITIONS, true);
    }

    WorkEffortType workEffortType;

    @Override
    protected void handleForm() {
        var workEffortTypeName = form.getWorkEffortTypeName();

        workEffortType = workEffortControl.getWorkEffortTypeByName(workEffortTypeName);

        if(workEffortType == null) {
            addExecutionError(ExecutionErrors.UnknownWorkEffortTypeName.name(), workEffortTypeName);
        }
    }

    @Override
    protected Long getTotalEntities() {
        return hasExecutionErrors() ? null : workRequirementControl.countWorkRequirementTypesByWorkEffortType(workEffortType);
    }

    @Override
    protected Collection<WorkRequirementType> getEntities() {
        return hasExecutionErrors() ? null : workRequirementControl.getWorkRequirementTypes(workEffortType);
    }

    @Override
    protected BaseResult getResult(Collection<WorkRequirementType> entities) {
        var result = WorkRequirementResultFactory.getGetWorkRequirementTypesResult();

        if(entities != null) {
            var userVisit = getUserVisit();

            result.setWorkEffortType(workEffortControl.getWorkEffortTypeTransfer(userVisit, workEffortType));

            if(session.hasLimit(WorkRequirementTypeFactory.class)) {
                result.setWorkRequirementTypeCount(getTotalEntities());
            }

            result.setWorkRequirementTypes(workRequirementControl.getWorkRequirementTypeTransfers(userVisit, entities));
        }

        return result;
    }

}
