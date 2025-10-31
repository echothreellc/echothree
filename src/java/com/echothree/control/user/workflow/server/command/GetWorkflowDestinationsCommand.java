// --------------------------------------------------------------------------------
// Copyright 2002-2025 Echo Three, LLC
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
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.control.workflow.server.control.WorkflowControl;
import com.echothree.model.control.workflow.server.logic.WorkflowStepLogic;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.model.data.workflow.server.entity.WorkflowDestination;
import com.echothree.model.data.workflow.server.entity.WorkflowStep;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BasePaginatedMultipleEntitiesCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
import java.util.Collection;
import java.util.List;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class GetWorkflowDestinationsCommand
        extends BasePaginatedMultipleEntitiesCommand<WorkflowDestination, GetWorkflowDestinationsForm> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), List.of(
                        new SecurityRoleDefinition(SecurityRoleGroups.WorkflowDestination.name(), SecurityRoles.List.name())
                ))
        ));

        FORM_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("WorkflowName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("WorkflowStepName", FieldType.ENTITY_NAME, true, null, null)
        );
    }
    
    /** Creates a new instance of GetWorkflowDestinationsCommand */
    public GetWorkflowDestinationsCommand() {
        super(COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, true);
    }
    
    WorkflowStep workflowStep;

    @Override
    protected void handleForm() {
        var workflowName = form.getWorkflowName();
        var workflowStepName = form.getWorkflowStepName();

        workflowStep = WorkflowStepLogic.getInstance().getWorkflowStepByName(this, workflowName, workflowStepName);
    }

    @Override
    protected Long getTotalEntities() {
        var workflowControl = Session.getModelController(WorkflowControl.class);

        return hasExecutionErrors() ? null :
                workflowControl.countWorkflowDestinationsByWorkflowStep(workflowStep);
    }

    @Override
    protected Collection<WorkflowDestination> getEntities() {
        Collection<WorkflowDestination> workflowDestinations = null;

        if(!hasExecutionErrors()) {
            var workflowControl = Session.getModelController(WorkflowControl.class);

            workflowDestinations = workflowControl.getWorkflowDestinationsByWorkflowStep(workflowStep);
        }

        return workflowDestinations;
    }

    @Override
    protected BaseResult getResult(Collection<WorkflowDestination> entities) {
        var result = WorkflowResultFactory.getGetWorkflowDestinationsResult();

        if(entities != null) {
            var workflowControl = Session.getModelController(WorkflowControl.class);
            var userVisit = getUserVisit();

            result.setWorkflowStep(workflowControl.getWorkflowStepTransfer(userVisit, workflowStep));
            result.setWorkflowDestinations(workflowControl.getWorkflowDestinationTransfers(getUserVisit(), entities));
        }

        return result;
    }

}
