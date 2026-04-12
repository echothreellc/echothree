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

package com.echothree.control.user.workflow.server.command;

import com.echothree.control.user.workflow.common.edit.WorkflowDestinationDescriptionEdit;
import com.echothree.control.user.workflow.common.edit.WorkflowEditFactory;
import com.echothree.control.user.workflow.common.result.EditWorkflowDestinationDescriptionResult;
import com.echothree.control.user.workflow.common.result.WorkflowResultFactory;
import com.echothree.control.user.workflow.common.spec.WorkflowDestinationDescriptionSpec;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.control.workflow.server.control.WorkflowControl;
import com.echothree.model.data.workflow.server.entity.WorkflowDestination;
import com.echothree.model.data.workflow.server.entity.WorkflowDestinationDescription;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.EditMode;
import com.echothree.util.server.control.BaseAbstractEditCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class EditWorkflowDestinationDescriptionCommand
        extends BaseAbstractEditCommand<WorkflowDestinationDescriptionSpec, WorkflowDestinationDescriptionEdit, EditWorkflowDestinationDescriptionResult, WorkflowDestinationDescription, WorkflowDestination> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), List.of(
                        new SecurityRoleDefinition(SecurityRoleGroups.WorkflowDestination.name(), SecurityRoles.Description.name())
                ))
        ));
        
        SPEC_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("WorkflowName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("WorkflowStepName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("WorkflowDestinationName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, true, null, null)
        );
        
        EDIT_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("Description", FieldType.STRING, true, 1L, 132L)
        );
    }

    @Inject
    PartyControl partyControl;

    @Inject
    WorkflowControl workflowControl;

    /** Creates a new instance of EditWorkflowDestinationDescriptionCommand */
    public EditWorkflowDestinationDescriptionCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }

    @Override
    public EditWorkflowDestinationDescriptionResult getResult() {
        return WorkflowResultFactory.getEditWorkflowDestinationDescriptionResult();
    }

    @Override
    public WorkflowDestinationDescriptionEdit getEdit() {
        return WorkflowEditFactory.getWorkflowDestinationDescriptionEdit();
    }

    @Override
    public WorkflowDestinationDescription getEntity(EditWorkflowDestinationDescriptionResult result) {
        WorkflowDestinationDescription workflowDestinationDescription = null;
        var workflowName = spec.getWorkflowName();
        var workflow = workflowControl.getWorkflowByName(workflowName);

        if(workflow != null) {
            var workflowStepName = spec.getWorkflowStepName();
            var workflowStep = workflowControl.getWorkflowStepByName(workflow, workflowStepName);

            if(workflowStep != null) {
                var workflowDestinationName = spec.getWorkflowDestinationName();
                var workflowDestination = workflowControl.getWorkflowDestinationByName(workflowStep, workflowDestinationName);

                if(workflowDestination != null) {
                    var languageIsoName = spec.getLanguageIsoName();
                    var language = partyControl.getLanguageByIsoName(languageIsoName);

                    if(language != null) {
                        if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
                            workflowDestinationDescription = workflowControl.getWorkflowDestinationDescription(workflowDestination, language);
                        } else { // EditMode.UPDATE
                            workflowDestinationDescription = workflowControl.getWorkflowDestinationDescriptionForUpdate(workflowDestination, language);
                        }

                        if(workflowDestinationDescription == null) {
                            addExecutionError(ExecutionErrors.UnknownWorkflowDestinationDescription.name(), workflowName, workflowStepName, workflowDestinationName, languageIsoName);
                        }
                    } else {
                        addExecutionError(ExecutionErrors.UnknownLanguageIsoName.name(), languageIsoName);
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownWorkflowDestinationName.name(), workflowName, workflowStepName, workflowDestinationName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownWorkflowStepName.name(), workflowName, workflowStepName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownWorkflowName.name(), workflowName);
        }

        return workflowDestinationDescription;
    }

    @Override
    public WorkflowDestination getLockEntity(WorkflowDestinationDescription workflowDestinationDescription) {
        return workflowDestinationDescription.getWorkflowDestination();
    }

    @Override
    public void fillInResult(EditWorkflowDestinationDescriptionResult result, WorkflowDestinationDescription workflowDestinationDescription) {
        result.setWorkflowDestinationDescription(workflowControl.getWorkflowDestinationDescriptionTransfer(getUserVisit(), workflowDestinationDescription));
    }

    @Override
    public void doLock(WorkflowDestinationDescriptionEdit edit, WorkflowDestinationDescription workflowDestinationDescription) {
        edit.setDescription(workflowDestinationDescription.getDescription());
    }

    @Override
    public void doUpdate(WorkflowDestinationDescription workflowDestinationDescription) {
        var workflowDestinationDescriptionValue = workflowControl.getWorkflowDestinationDescriptionValue(workflowDestinationDescription);

        workflowDestinationDescriptionValue.setDescription(edit.getDescription());

        workflowControl.updateWorkflowDestinationDescriptionFromValue(workflowDestinationDescriptionValue, getPartyPK());
    }
    
}
