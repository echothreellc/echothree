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

import com.echothree.control.user.workflow.common.form.GetWorkflowEntityStatusesForm;
import com.echothree.control.user.workflow.common.result.WorkflowResultFactory;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.control.workflow.server.control.WorkflowControl;
import com.echothree.model.control.workflow.server.logic.WorkflowLogic;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.model.data.workflow.server.entity.Workflow;
import com.echothree.model.data.workflow.server.entity.WorkflowEntityStatus;
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
import javax.enterprise.context.Dependent;

@Dependent
public class GetWorkflowEntityStatusesCommand
        extends BasePaginatedMultipleEntitiesCommand<WorkflowEntityStatus, GetWorkflowEntityStatusesForm> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), List.of(
                        new SecurityRoleDefinition(SecurityRoleGroups.WorkflowEntityStatus.name(), SecurityRoles.List.name())
                ))
        ));

        FORM_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("WorkflowName", FieldType.ENTITY_NAME, true, null, null)
        );
    }
    
    /** Creates a new instance of GetWorkflowEntityStatusesCommand */
    public GetWorkflowEntityStatusesCommand() {
        super(COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, true);
    }

    Workflow workflow;

    @Override
    protected void handleForm() {
        var workflowName = form.getWorkflowName();

        workflow = WorkflowLogic.getInstance().getWorkflowByName(this, workflowName);
    }

    @Override
    protected Long getTotalEntities() {
        var workflowControl = Session.getModelController(WorkflowControl.class);

        return hasExecutionErrors() ? null :
                workflowControl.countWorkflowEntityStatusesByWorkflow(workflow);
    }

    @Override
    protected Collection<WorkflowEntityStatus> getEntities() {
        Collection<WorkflowEntityStatus> workflowEntityStatuses = null;

        if(!hasExecutionErrors()) {
            var workflowControl = Session.getModelController(WorkflowControl.class);

            workflowEntityStatuses = workflowControl.getWorkflowEntityStatusesByWorkflow(workflow);
        }

        return workflowEntityStatuses;
    }

    @Override
    protected BaseResult getResult(Collection<WorkflowEntityStatus> entities) {
        var result = WorkflowResultFactory.getGetWorkflowEntityStatusesResult();

        if(entities != null) {
            var workflowControl = Session.getModelController(WorkflowControl.class);
            var userVisit = getUserVisit();

            result.setWorkflow(workflowControl.getWorkflowTransfer(userVisit, workflow));
            result.setWorkflowEntityStatuses(workflowControl.getWorkflowEntityStatusTransfers(getUserVisit(), entities));
        }

        return result;
    }

}
