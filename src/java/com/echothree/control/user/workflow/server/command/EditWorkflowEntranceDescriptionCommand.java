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
import com.echothree.control.user.workflow.common.edit.WorkflowEntranceDescriptionEdit;
import com.echothree.control.user.workflow.common.result.EditWorkflowEntranceDescriptionResult;
import com.echothree.control.user.workflow.common.result.WorkflowResultFactory;
import com.echothree.control.user.workflow.common.spec.WorkflowEntranceDescriptionSpec;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.control.workflow.server.control.WorkflowControl;
import com.echothree.model.data.workflow.server.entity.WorkflowEntrance;
import com.echothree.model.data.workflow.server.entity.WorkflowEntranceDescription;
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
public class EditWorkflowEntranceDescriptionCommand
        extends BaseAbstractEditCommand<WorkflowEntranceDescriptionSpec, WorkflowEntranceDescriptionEdit, EditWorkflowEntranceDescriptionResult, WorkflowEntranceDescription, WorkflowEntrance> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), List.of(
                        new SecurityRoleDefinition(SecurityRoleGroups.WorkflowEntrance.name(), SecurityRoles.Description.name())
                ))
        ));
        
        SPEC_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("WorkflowName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("WorkflowEntranceName", FieldType.ENTITY_NAME, true, null, null),
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

    /** Creates a new instance of EditWorkflowEntranceDescriptionCommand */
    public EditWorkflowEntranceDescriptionCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }

    @Override
    public EditWorkflowEntranceDescriptionResult getResult() {
        return WorkflowResultFactory.getEditWorkflowEntranceDescriptionResult();
    }

    @Override
    public WorkflowEntranceDescriptionEdit getEdit() {
        return WorkflowEditFactory.getWorkflowEntranceDescriptionEdit();
    }

    @Override
    public WorkflowEntranceDescription getEntity(EditWorkflowEntranceDescriptionResult result) {
        WorkflowEntranceDescription workflowEntranceDescription = null;
        var workflowName = spec.getWorkflowName();
        var workflow = workflowControl.getWorkflowByName(workflowName);

        if(workflow != null) {
            var workflowEntranceName = spec.getWorkflowEntranceName();
            var workflowEntrance = workflowControl.getWorkflowEntranceByName(workflow, workflowEntranceName);

            if(workflowEntrance != null) {
                var languageIsoName = spec.getLanguageIsoName();
                var language = partyControl.getLanguageByIsoName(languageIsoName);

                if(language != null) {
                    if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
                        workflowEntranceDescription = workflowControl.getWorkflowEntranceDescription(workflowEntrance, language);
                    } else { // EditMode.UPDATE
                        workflowEntranceDescription = workflowControl.getWorkflowEntranceDescriptionForUpdate(workflowEntrance, language);
                    }

                    if(workflowEntranceDescription == null) {
                        addExecutionError(ExecutionErrors.UnknownWorkflowEntranceDescription.name(), workflowName, workflowEntranceName, languageIsoName);
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownLanguageIsoName.name(), languageIsoName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownWorkflowEntranceName.name(), workflowName, workflowEntranceName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownWorkflowName.name(), workflowName);
        }

        return workflowEntranceDescription;
    }

    @Override
    public WorkflowEntrance getLockEntity(WorkflowEntranceDescription workflowEntranceDescription) {
        return workflowEntranceDescription.getWorkflowEntrance();
    }

    @Override
    public void fillInResult(EditWorkflowEntranceDescriptionResult result, WorkflowEntranceDescription workflowEntranceDescription) {
        result.setWorkflowEntranceDescription(workflowControl.getWorkflowEntranceDescriptionTransfer(getUserVisit(), workflowEntranceDescription));
    }

    @Override
    public void doLock(WorkflowEntranceDescriptionEdit edit, WorkflowEntranceDescription workflowEntranceDescription) {
        edit.setDescription(workflowEntranceDescription.getDescription());
    }

    @Override
    public void doUpdate(WorkflowEntranceDescription workflowEntranceDescription) {
        var workflowEntranceDescriptionValue = workflowControl.getWorkflowEntranceDescriptionValue(workflowEntranceDescription);

        workflowEntranceDescriptionValue.setDescription(edit.getDescription());

        workflowControl.updateWorkflowEntranceDescriptionFromValue(workflowEntranceDescriptionValue, getPartyPK());
    }
    
}
