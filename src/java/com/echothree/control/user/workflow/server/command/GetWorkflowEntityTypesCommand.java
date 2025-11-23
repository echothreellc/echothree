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

import com.echothree.control.user.workflow.common.form.GetWorkflowEntityTypesForm;
import com.echothree.control.user.workflow.common.result.WorkflowResultFactory;
import com.echothree.model.control.core.server.logic.EntityTypeLogic;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.control.workflow.server.control.WorkflowControl;
import com.echothree.model.control.workflow.server.logic.WorkflowLogic;
import com.echothree.model.data.core.server.entity.EntityType;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.model.data.workflow.server.entity.Workflow;
import com.echothree.model.data.workflow.server.entity.WorkflowEntityType;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.message.ExecutionErrors;
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
public class GetWorkflowEntityTypesCommand
        extends BasePaginatedMultipleEntitiesCommand<WorkflowEntityType, GetWorkflowEntityTypesForm> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), List.of(
                        new SecurityRoleDefinition(SecurityRoleGroups.Workflow.name(), SecurityRoles.EntityType.name())
                ))
        ));

        FORM_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("WorkflowName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("ComponentVendorName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("EntityTypeName", FieldType.ENTITY_TYPE_NAME, false, null, null)
        );
    }
    
    /** Creates a new instance of GetWorkflowEntityTypesCommand */
    public GetWorkflowEntityTypesCommand() {
        super(COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, true);
    }

    Workflow workflow;
    EntityType entityType;

    @Override
    protected void handleForm() {
        var workflowName = form.getWorkflowName();
        var componentVendorName = form.getComponentVendorName();
        var entityTypeName = form.getEntityTypeName();
        var parameterCount = (workflowName == null ? 0 : 1) + (componentVendorName == null && entityTypeName == null ? 0 : 1);

        if(parameterCount == 1) {
            if(workflowName != null) {
                workflow = WorkflowLogic.getInstance().getWorkflowByName(this, workflowName);
            } else {
                entityType = EntityTypeLogic.getInstance().getEntityTypeByName(this, componentVendorName, entityTypeName);
            }
        } else {
            addExecutionError(ExecutionErrors.InvalidParameterCount.name());
        }
    }

    @Override
    protected Long getTotalEntities() {
        var workflowControl = Session.getModelController(WorkflowControl.class);

        return hasExecutionErrors() ? null :
                workflow != null ?
                        workflowControl.countWorkflowEntityTypesByWorkflow(workflow) :
                        workflowControl.countWorkflowEntityTypesByEntityType(entityType);
    }

    @Override
    protected Collection<WorkflowEntityType> getEntities() {
        Collection<WorkflowEntityType> workflowEntityTypes = null;

        if(!hasExecutionErrors()) {
            var workflowControl = Session.getModelController(WorkflowControl.class);

            if(workflow != null) {
                workflowEntityTypes = workflowControl.getWorkflowEntityTypesByWorkflow(workflow);
            } else {
                workflowEntityTypes = workflowControl.getWorkflowEntityTypesByEntityType(entityType);
            }
        }

        return workflowEntityTypes;
    }

    @Override
    protected BaseResult getResult(Collection<WorkflowEntityType> entities) {
        var result = WorkflowResultFactory.getGetWorkflowEntityTypesResult();

        if(entities != null) {
            var workflowControl = Session.getModelController(WorkflowControl.class);
            var userVisit = getUserVisit();

            result.setWorkflow(workflow == null ? null : workflowControl.getWorkflowTransfer(userVisit, workflow));
            result.setEntityType(entityType == null ? null : entityTypeControl.getEntityTypeTransfer(userVisit, entityType));
            result.setWorkflowEntityTypes(workflowControl.getWorkflowEntityTypeTransfers(userVisit, entities));
        }

        return result;
    }

}
