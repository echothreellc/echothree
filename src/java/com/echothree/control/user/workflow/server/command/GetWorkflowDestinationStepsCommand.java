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

package com.echothree.control.user.workflow.server.command;

import com.echothree.control.user.workflow.common.form.GetWorkflowDestinationStepsForm;
import com.echothree.control.user.workflow.common.result.WorkflowResultFactory;
import com.echothree.model.control.party.common.PartyConstants;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.control.workflow.server.WorkflowControl;
import com.echothree.model.control.workflow.server.logic.WorkflowDestinationLogic;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.model.data.workflow.server.entity.WorkflowDestination;
import com.echothree.model.data.workflow.server.entity.WorkflowDestinationStep;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseMultipleEntitiesCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class GetWorkflowDestinationStepsCommand
        extends BaseMultipleEntitiesCommand<WorkflowDestinationStep, GetWorkflowDestinationStepsForm> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyConstants.PartyType_UTILITY, null),
                new PartyTypeDefinition(PartyConstants.PartyType_EMPLOYEE, Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.WorkflowDestination.name(), SecurityRoles.WorkflowStep.name())
                        )))
                )));

        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("WorkflowName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("WorkflowStepName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("WorkflowDestinationName", FieldType.ENTITY_NAME, true, null, null)
                ));
    }
    
    /** Creates a new instance of GetWorkflowDestinationStepsCommand */
    public GetWorkflowDestinationStepsCommand(UserVisitPK userVisitPK, GetWorkflowDestinationStepsForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, true);
    }
    
    WorkflowDestination workflowDestination;

    @Override
    protected Collection<WorkflowDestinationStep> getEntities() {
        var workflowName = form.getWorkflowName();
        var workflowStepName = form.getWorkflowStepName();
        var workflowDestinationName = form.getWorkflowDestinationName();
        Collection<WorkflowDestinationStep> workflowDestinationSteps = null;

        workflowDestination = WorkflowDestinationLogic.getInstance().getWorkflowDestinationByName(this, workflowName,
                workflowStepName, workflowDestinationName);

        if(!this.hasExecutionErrors()) {
            var workflowControl = (WorkflowControl)Session.getModelController(WorkflowControl.class);

            workflowDestinationSteps = workflowControl.getWorkflowDestinationStepsByWorkflowDestination(workflowDestination);
        }

        return workflowDestinationSteps;
    }

    @Override
    protected BaseResult getTransfers(Collection<WorkflowDestinationStep> entities) {
        var result = WorkflowResultFactory.getGetWorkflowDestinationStepsResult();

        if(entities != null) {
            var workflowControl = (WorkflowControl)Session.getModelController(WorkflowControl.class);
            var userVisit = getUserVisit();

            result.setWorkflowDestination(workflowControl.getWorkflowDestinationTransfer(userVisit, workflowDestination));
            result.setWorkflowDestinationSteps(workflowControl.getWorkflowDestinationStepTransfers(userVisit, entities));
        }

        return result;
    }
    
}
