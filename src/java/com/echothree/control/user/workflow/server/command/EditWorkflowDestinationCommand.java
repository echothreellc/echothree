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

import com.echothree.control.user.workflow.common.edit.WorkflowDestinationEdit;
import com.echothree.control.user.workflow.common.edit.WorkflowEditFactory;
import com.echothree.control.user.workflow.common.form.EditWorkflowDestinationForm;
import com.echothree.control.user.workflow.common.result.EditWorkflowDestinationResult;
import com.echothree.control.user.workflow.common.result.WorkflowResultFactory;
import com.echothree.control.user.workflow.common.spec.WorkflowDestinationUniversalSpec;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.control.workflow.server.control.WorkflowControl;
import com.echothree.model.control.workflow.server.logic.WorkflowDestinationLogic;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.model.data.workflow.server.entity.WorkflowDestination;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseAbstractEditCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
import java.util.List;
import javax.enterprise.context.Dependent;

@Dependent
public class EditWorkflowDestinationCommand
        extends BaseAbstractEditCommand<WorkflowDestinationUniversalSpec, WorkflowDestinationEdit, EditWorkflowDestinationResult, WorkflowDestination, WorkflowDestination> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), List.of(
                        new SecurityRoleDefinition(SecurityRoleGroups.WorkflowDestination.name(), SecurityRoles.Edit.name())
                ))
        ));
        
        SPEC_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("WorkflowName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("WorkflowStepName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("WorkflowDestinationName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("EntityRef", FieldType.ENTITY_REF, false, null, null),
                new FieldDefinition("Uuid", FieldType.UUID, false, null, null)
        );
        
        EDIT_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("WorkflowDestinationName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 132L)
        );
    }
    
    /** Creates a new instance of EditWorkflowDestinationCommand */
    public EditWorkflowDestinationCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }

    @Override
    public EditWorkflowDestinationResult getResult() {
        return WorkflowResultFactory.getEditWorkflowDestinationResult();
    }

    @Override
    public WorkflowDestinationEdit getEdit() {
        return WorkflowEditFactory.getWorkflowDestinationEdit();
    }

    @Override
    public WorkflowDestination getEntity(EditWorkflowDestinationResult result) {
        return WorkflowDestinationLogic.getInstance().getWorkflowDestinationByUniversalSpec(this, spec, false, editModeToEntityPermission(editMode));
    }

    @Override
    public WorkflowDestination getLockEntity(WorkflowDestination freeOnBoard) {
        return freeOnBoard;
    }

    @Override
    public void fillInResult(EditWorkflowDestinationResult result, WorkflowDestination freeOnBoard) {
        var workflow = Session.getModelController(WorkflowControl.class);

        result.setWorkflowDestination(workflow.getWorkflowDestinationTransfer(getUserVisit(), freeOnBoard));
    }

    @Override
    public void doLock(WorkflowDestinationEdit edit, WorkflowDestination workflowDestination) {
        var workflowControl = Session.getModelController(WorkflowControl.class);
        var workflowDestinationDescription = workflowControl.getWorkflowDestinationDescription(workflowDestination, getPreferredLanguage());
        var workflowDestinationDetail = workflowDestination.getLastDetail();

        edit.setWorkflowDestinationName(workflowDestinationDetail.getWorkflowDestinationName());
        edit.setIsDefault(workflowDestinationDetail.getIsDefault().toString());
        edit.setSortOrder(workflowDestinationDetail.getSortOrder().toString());

        if(workflowDestinationDescription != null) {
            edit.setDescription(workflowDestinationDescription.getDescription());
        }
    }

    @Override
    public void canUpdate(WorkflowDestination workflowDestination) {
        var workflowControl = Session.getModelController(WorkflowControl.class);
        var workflowStep = workflowDestination.getLastDetail().getWorkflowStep();
        var workflowDestinationName = edit.getWorkflowDestinationName();
        var duplicateWorkflowDestination = workflowControl.getWorkflowDestinationByName(workflowStep, workflowDestinationName);

        if(duplicateWorkflowDestination != null && !workflowDestination.equals(duplicateWorkflowDestination)) {
            addExecutionError(ExecutionErrors.DuplicateWorkflowDestinationName.name(), workflowDestinationName);
        }
    }

    @Override
    public void doUpdate(WorkflowDestination workflowDestination) {
        var workflowControl = Session.getModelController(WorkflowControl.class);
        var partyPK = getPartyPK();
        var workflowDestinationDetailValue = workflowControl.getWorkflowDestinationDetailValueForUpdate(workflowDestination);
        var workflowDestinationDescription = workflowControl.getWorkflowDestinationDescriptionForUpdate(workflowDestination, getPreferredLanguage());
        var description = edit.getDescription();

        workflowDestinationDetailValue.setWorkflowDestinationName(edit.getWorkflowDestinationName());
        workflowDestinationDetailValue.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
        workflowDestinationDetailValue.setSortOrder(Integer.valueOf(edit.getSortOrder()));

        workflowControl.updateWorkflowDestinationFromValue(workflowDestinationDetailValue, partyPK);

        if(workflowDestinationDescription == null && description != null) {
            workflowControl.createWorkflowDestinationDescription(workflowDestination, getPreferredLanguage(), description, partyPK);
        } else if(workflowDestinationDescription != null && description == null) {
            workflowControl.deleteWorkflowDestinationDescription(workflowDestinationDescription, partyPK);
        } else if(workflowDestinationDescription != null && description != null) {
            var workflowDestinationDescriptionValue = workflowControl.getWorkflowDestinationDescriptionValue(workflowDestinationDescription);

            workflowDestinationDescriptionValue.setDescription(description);
            workflowControl.updateWorkflowDestinationDescriptionFromValue(workflowDestinationDescriptionValue, partyPK);
        }
    }
    
}
