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

import com.echothree.control.user.workflow.common.edit.WorkflowEditFactory;
import com.echothree.control.user.workflow.common.edit.WorkflowStepDescriptionEdit;
import com.echothree.control.user.workflow.common.result.EditWorkflowStepDescriptionResult;
import com.echothree.control.user.workflow.common.result.WorkflowResultFactory;
import com.echothree.control.user.workflow.common.spec.WorkflowStepDescriptionSpec;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.control.workflow.server.control.WorkflowControl;
import com.echothree.model.data.workflow.server.entity.WorkflowStep;
import com.echothree.model.data.workflow.server.entity.WorkflowStepDescription;
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
public class EditWorkflowStepDescriptionCommand
        extends BaseAbstractEditCommand<WorkflowStepDescriptionSpec, WorkflowStepDescriptionEdit, EditWorkflowStepDescriptionResult, WorkflowStepDescription, WorkflowStep> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), List.of(
                        new SecurityRoleDefinition(SecurityRoleGroups.WorkflowStep.name(), SecurityRoles.Description.name())
                ))
        ));
        
        SPEC_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("WorkflowName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("WorkflowStepName", FieldType.ENTITY_NAME, true, null, null),
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

    /** Creates a new instance of EditWorkflowStepDescriptionCommand */
    public EditWorkflowStepDescriptionCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }

    @Override
    public EditWorkflowStepDescriptionResult getResult() {
        return WorkflowResultFactory.getEditWorkflowStepDescriptionResult();
    }

    @Override
    public WorkflowStepDescriptionEdit getEdit() {
        return WorkflowEditFactory.getWorkflowStepDescriptionEdit();
    }

    @Override
    public WorkflowStepDescription getEntity(EditWorkflowStepDescriptionResult result) {
        WorkflowStepDescription workflowStepDescription = null;
        var workflowName = spec.getWorkflowName();
        var workflow = workflowControl.getWorkflowByName(workflowName);

        if(workflow != null) {
            var workflowStepName = spec.getWorkflowStepName();
            var workflowStep = workflowControl.getWorkflowStepByName(workflow, workflowStepName);

            if(workflowStep != null) {
                var languageIsoName = spec.getLanguageIsoName();
                var language = partyControl.getLanguageByIsoName(languageIsoName);

                if(language != null) {
                    if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
                        workflowStepDescription = workflowControl.getWorkflowStepDescription(workflowStep, language);
                    } else { // EditMode.UPDATE
                        workflowStepDescription = workflowControl.getWorkflowStepDescriptionForUpdate(workflowStep, language);
                    }

                    if(workflowStepDescription == null) {
                        addExecutionError(ExecutionErrors.UnknownWorkflowStepDescription.name(), workflowName, workflowStepName, languageIsoName);
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownLanguageIsoName.name(), languageIsoName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownWorkflowStepName.name(), workflowName, workflowStepName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownWorkflowName.name(), workflowName);
        }

        return workflowStepDescription;
    }

    @Override
    public WorkflowStep getLockEntity(WorkflowStepDescription workflowStepDescription) {
        return workflowStepDescription.getWorkflowStep();
    }

    @Override
    public void fillInResult(EditWorkflowStepDescriptionResult result, WorkflowStepDescription workflowStepDescription) {
        result.setWorkflowStepDescription(workflowControl.getWorkflowStepDescriptionTransfer(getUserVisit(), workflowStepDescription));
    }

    @Override
    public void doLock(WorkflowStepDescriptionEdit edit, WorkflowStepDescription workflowStepDescription) {
        edit.setDescription(workflowStepDescription.getDescription());
    }

    @Override
    public void doUpdate(WorkflowStepDescription workflowStepDescription) {
        var workflowStepDescriptionValue = workflowControl.getWorkflowStepDescriptionValue(workflowStepDescription);

        workflowStepDescriptionValue.setDescription(edit.getDescription());

        workflowControl.updateWorkflowStepDescriptionFromValue(workflowStepDescriptionValue, getPartyPK());
    }
    
}
