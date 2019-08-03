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

import com.echothree.control.user.workflow.common.form.GetWorkflowEntityTypesForm;
import com.echothree.control.user.workflow.common.result.WorkflowResultFactory;
import com.echothree.model.control.core.server.logic.EntityTypeLogic;
import com.echothree.model.control.party.common.PartyConstants;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.control.workflow.server.WorkflowControl;
import com.echothree.model.control.workflow.server.logic.WorkflowLogic;
import com.echothree.model.data.core.server.entity.EntityType;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.model.data.workflow.server.entity.Workflow;
import com.echothree.model.data.workflow.server.entity.WorkflowEntityType;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.message.ExecutionErrors;
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

public class GetWorkflowEntityTypesCommand
        extends BaseMultipleEntitiesCommand<WorkflowEntityType, GetWorkflowEntityTypesForm> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyConstants.PartyType_UTILITY, null),
                new PartyTypeDefinition(PartyConstants.PartyType_EMPLOYEE, Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.Workflow.name(), SecurityRoles.EntityType.name())
                        )))
                )));

        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("WorkflowName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("ComponentVendorName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("EntityTypeName", FieldType.ENTITY_TYPE_NAME, false, null, null)
                ));
    }
    
    /** Creates a new instance of GetWorkflowEntityTypesCommand */
    public GetWorkflowEntityTypesCommand(UserVisitPK userVisitPK, GetWorkflowEntityTypesForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, true);
    }

    Workflow workflow;
    EntityType entityType;

    @Override
    protected Collection<WorkflowEntityType> getEntities() {
        var workflowName = form.getWorkflowName();
        var componentVendorName = form.getComponentVendorName();
        var entityTypeName = form.getEntityTypeName();
        int parameterCount = (workflowName == null ? 0 : 1) + (componentVendorName == null && entityTypeName == null ? 0 : 1);
        Collection<WorkflowEntityType> workflowEntityTypes = null;

        if(parameterCount == 1) {
            if(workflowName != null) {
                workflow = WorkflowLogic.getInstance().getWorkflowByName(this, workflowName);

                if(!hasExecutionErrors()) {
                    var workflowControl = (WorkflowControl)Session.getModelController(WorkflowControl.class);

                    workflowEntityTypes = workflowControl.getWorkflowEntityTypesByWorkflow(workflow);
                }
            } else {
                entityType = EntityTypeLogic.getInstance().getEntityTypeByName(this, componentVendorName, entityTypeName);

                if(!hasExecutionErrors()) {
                    var workflowControl = (WorkflowControl)Session.getModelController(WorkflowControl.class);

                    workflowEntityTypes = workflowControl.getWorkflowEntityTypesByEntityType(entityType);
                }
            }
        } else {
            addExecutionError(ExecutionErrors.InvalidParameterCount.name());
        }

        return workflowEntityTypes;
    }

    @Override
    protected BaseResult getTransfers(Collection<WorkflowEntityType> entities) {
        var result = WorkflowResultFactory.getGetWorkflowEntityTypesResult();

        if(entities != null) {
            var workflowControl = (WorkflowControl)Session.getModelController(WorkflowControl.class);
            var userVisit = getUserVisit();

            result.setWorkflow(workflow == null ? null : workflowControl.getWorkflowTransfer(userVisit, workflow));
            result.setEntityType(entityType == null ? null : getCoreControl().getEntityTypeTransfer(userVisit, entityType));
            result.setWorkflowEntityTypes(workflowControl.getWorkflowEntityTypeTransfers(userVisit, entities));
        }

        return result;
    }

}
