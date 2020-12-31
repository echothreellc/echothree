// --------------------------------------------------------------------------------
// Copyright 2002-2021 Echo Three, LLC
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

package com.echothree.control.user.workflow.server.command;

import com.echothree.control.user.workflow.common.form.GetWorkflowDestinationsForm;
import com.echothree.control.user.workflow.common.result.WorkflowResultFactory;
import com.echothree.model.control.workflow.server.control.WorkflowControl;
import com.echothree.model.control.workflow.server.logic.WorkflowStepLogic;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.model.data.workflow.server.entity.WorkflowDestination;
import com.echothree.model.data.workflow.server.entity.WorkflowStep;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseMultipleEntitiesCommand;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class GetWorkflowDestinationsCommand
        extends BaseMultipleEntitiesCommand<WorkflowDestination, GetWorkflowDestinationsForm> {
    
    // No COMMAND_SECURITY_DEFINITION, anyone may execute this command.
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("WorkflowName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("WorkflowStepName", FieldType.ENTITY_NAME, true, null, null)
                ));
    }
    
    /** Creates a new instance of GetWorkflowDestinationsCommand */
    public GetWorkflowDestinationsCommand(UserVisitPK userVisitPK, GetWorkflowDestinationsForm form) {
        super(userVisitPK, form, null, FORM_FIELD_DEFINITIONS, true);
    }
    
    WorkflowStep workflowStep;

    @Override
    protected Collection<WorkflowDestination> getEntities() {
        var workflowName = form.getWorkflowName();
        var workflowStepName = form.getWorkflowStepName();
        Collection<WorkflowDestination> workflowDestinations = null;

        workflowStep = WorkflowStepLogic.getInstance().getWorkflowStepByName(this, workflowName, workflowStepName);

        if(!this.hasExecutionErrors()) {
            var workflowControl = Session.getModelController(WorkflowControl.class);

            workflowDestinations = workflowControl.getWorkflowDestinationsByWorkflowStep(workflowStep);
        }

        return workflowDestinations;
    }

    @Override
    protected BaseResult getTransfers(Collection<WorkflowDestination> entities) {
        var result = WorkflowResultFactory.getGetWorkflowDestinationsResult();

        if(entities != null) {
            var workflowControl = Session.getModelController(WorkflowControl.class);
            var userVisit = getUserVisit();

            result.setWorkflow(workflowControl.getWorkflowTransfer(userVisit, workflowStep.getLastDetail().getWorkflow()));
            result.setWorkflowStep(workflowControl.getWorkflowStepTransfer(userVisit, workflowStep));
            result.setWorkflowDestinations(workflowControl.getWorkflowDestinationTransfers(getUserVisit(), entities));
        }

        return result;
    }

}
