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

import com.echothree.control.user.core.common.form.SetEntityWorkflowAttributeStatusForm;
import com.echothree.model.control.core.common.EntityAttributeTypes;
import com.echothree.model.control.core.server.logic.EntityAttributeLogic;
import com.echothree.model.control.core.server.logic.EntityInstanceLogic;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.workflow.server.control.WorkflowControl;
import com.echothree.model.control.workflow.server.logic.WorkflowDestinationLogic;
import com.echothree.model.control.workflow.server.logic.WorkflowStepLogic;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.model.data.workflow.server.entity.WorkflowDestination;
import com.echothree.model.data.workflow.server.entity.WorkflowEntityStatus;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.persistence.Session;
import java.util.List;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class SetEntityWorkflowAttributeStatusCommand
        extends BaseSimpleCommand<SetEntityWorkflowAttributeStatusForm> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;

    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), null)
        ));

        FORM_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("EntityRef", FieldType.ENTITY_REF, false, null, null),
                new FieldDefinition("Uuid", FieldType.UUID, false, null, null),
                new FieldDefinition("EntityAttributeName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("EntityAttributeUuid", FieldType.UUID, false, null, null),
                new FieldDefinition("WorkflowStepName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("WorkflowDestinationName", FieldType.ENTITY_NAME, false, null, null)
        );
    }
    
    /** Creates a new instance of SetEntityWorkflowAttributeStatusCommand */
    public SetEntityWorkflowAttributeStatusCommand() {
        super(COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        var entityInstance = EntityInstanceLogic.getInstance().getEntityInstance(this, form);

        if(!hasExecutionErrors()) {
            var entityAttribute = EntityAttributeLogic.getInstance().getEntityAttribute(this, entityInstance, form, form,
                    EntityAttributeTypes.WORKFLOW);
            var entityAttributeWorkflow = coreControl.getEntityAttributeWorkflow(entityAttribute);
            var workflow = entityAttributeWorkflow.getWorkflow();
            var workflowStepName = form.getWorkflowStepName();
            var workflowStep = WorkflowStepLogic.getInstance().getWorkflowStepByName(this, workflow, workflowStepName);

            if(!hasExecutionErrors()) {
                var workflowControl = Session.getModelController(WorkflowControl.class);
                var workflowDestinationName = form.getWorkflowDestinationName();
                WorkflowDestination workflowDestination;

                if(workflowDestinationName == null) {
                    workflowDestination = workflowControl.getDefaultWorkflowDestination(workflowStep);
                } else {
                    workflowDestination = WorkflowDestinationLogic.getInstance().getWorkflowDestinationByName(this, workflowStep, workflowDestinationName);
                }

                if(!hasExecutionErrors()) {
                    if(workflowDestination == null) {
                        addExecutionError(ExecutionErrors.MissingDefaultWorkflowDestination.name(),
                                workflow.getLastDetail().getWorkflowName(),
                                workflowStep.getLastDetail().getWorkflowStepName());
                    } else {
                        if(entityInstance.getEntityType().equals(entityAttribute.getLastDetail().getEntityType())) {
                            var workflowEntityStatuses = workflowControl.getWorkflowEntityStatusesByEntityInstanceForUpdate(workflow, entityInstance);
                            WorkflowEntityStatus foundWorkflowEntityStatus = null;

                            for(var workflowEntityStatus : workflowEntityStatuses) {
                                if(workflowEntityStatus.getWorkflowStep().equals(workflowStep)) {
                                    foundWorkflowEntityStatus = workflowEntityStatus;
                                    break;
                                }
                            }

                            if(foundWorkflowEntityStatus == null) {
                                addExecutionError(ExecutionErrors.UnknownWorkflowEntityStatus.name());
                            } else {
                                workflowControl.transitionEntityInWorkflow(this, foundWorkflowEntityStatus, workflowDestination, null, getPartyPK());
                            }
                        } else {
                            var componentVendorName = entityInstance.getEntityType().getLastDetail().getComponentVendor().getLastDetail().getComponentVendorName();
                            var entityTypeName = entityInstance.getEntityType().getLastDetail().getEntityTypeName();
                            var attributeComponentVendorName = entityAttribute.getLastDetail().getEntityType().getLastDetail().getComponentVendor().getLastDetail().getComponentVendorName();
                            var attributeEntityTypeName = entityAttribute.getLastDetail().getEntityType().getLastDetail().getEntityTypeName();

                            addExecutionError(ExecutionErrors.MismatchedEntityType.name(), componentVendorName, entityTypeName, attributeComponentVendorName, attributeEntityTypeName);
                        }
                    }
                }                
            }
        }

        return null;
    }
    
}
