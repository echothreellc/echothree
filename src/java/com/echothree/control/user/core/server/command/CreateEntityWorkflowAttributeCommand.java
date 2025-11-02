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

package com.echothree.control.user.core.server.command;

import com.echothree.control.user.core.common.form.CreateEntityWorkflowAttributeForm;
import com.echothree.model.control.core.common.EntityAttributeTypes;
import com.echothree.model.control.core.server.logic.EntityAttributeLogic;
import com.echothree.model.control.core.server.logic.EntityInstanceLogic;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.workflow.server.control.WorkflowControl;
import com.echothree.model.control.workflow.server.logic.WorkflowEntranceLogic;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.model.data.workflow.server.entity.WorkflowEntrance;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class CreateEntityWorkflowAttributeCommand
        extends BaseSimpleCommand<CreateEntityWorkflowAttributeForm> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), null)
        ));

        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("EntityRef", FieldType.ENTITY_REF, false, null, null),
                new FieldDefinition("Uuid", FieldType.UUID, false, null, null),
                new FieldDefinition("EntityAttributeName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("EntityAttributeUuid", FieldType.UUID, false, null, null),
                new FieldDefinition("WorkflowEntranceName", FieldType.ENTITY_NAME, false, null, null)
                ));
    }
    
    /** Creates a new instance of CreateEntityWorkflowAttributeCommand */
    public CreateEntityWorkflowAttributeCommand() {
        super(COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        var entityInstance = EntityInstanceLogic.getInstance().getEntityInstance(this, form);

        if(!hasExecutionErrors()) {
            var entityAttribute = EntityAttributeLogic.getInstance().getEntityAttribute(this, entityInstance, form, form,
                    EntityAttributeTypes.WORKFLOW);

            if(!hasExecutionErrors()) {
                var workflowControl = Session.getModelController(WorkflowControl.class);
                var entityAttributeWorkflow = coreControl.getEntityAttributeWorkflow(entityAttribute);
                var workflow = entityAttributeWorkflow.getWorkflow();

                if(workflowControl.countWorkflowEntityStatusesByWorkflowAndEntityInstance(workflow, entityInstance) == 0) {
                    var workflowEntranceName = form.getWorkflowEntranceName();
                    WorkflowEntrance workflowEntrance;

                    if(workflowEntranceName == null) {
                        workflowEntrance = workflowControl.getDefaultWorkflowEntrance(workflow);
                    } else {
                        workflowEntrance = WorkflowEntranceLogic.getInstance().getWorkflowEntranceByName(this, workflow, workflowEntranceName);
                    }

                    if(!hasExecutionErrors()) {
                        if(workflowEntrance == null) {
                            var workflowName = workflow.getLastDetail().getWorkflowName();

                            addExecutionError(ExecutionErrors.MissingDefaultWorkflowEntrance.name(), workflowName);
                        } else {
                            workflowControl.addEntityToWorkflow(workflowEntrance, entityInstance, null, null, getPartyPK());
                        }
                    }
                } else {
                    addExecutionError(ExecutionErrors.DuplicateEntityWorkflowAttribute.name(),
                            EntityInstanceLogic.getInstance().getEntityRefFromEntityInstance(entityInstance),
                            entityAttribute.getLastDetail().getEntityAttributeName());
                }
            }
        }
        
        return null;
    }
    
}
