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

import com.echothree.control.user.workflow.common.edit.WorkflowEditFactory;
import com.echothree.control.user.workflow.common.edit.WorkflowEntranceEdit;
import com.echothree.control.user.workflow.common.form.EditWorkflowEntranceForm;
import com.echothree.control.user.workflow.common.result.EditWorkflowEntranceResult;
import com.echothree.control.user.workflow.common.result.WorkflowResultFactory;
import com.echothree.control.user.workflow.common.spec.WorkflowEntranceUniversalSpec;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.control.workflow.server.control.WorkflowControl;
import com.echothree.model.control.workflow.server.logic.WorkflowEntranceLogic;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.model.data.workflow.server.entity.WorkflowEntrance;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseAbstractEditCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
import java.util.List;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class EditWorkflowEntranceCommand
        extends BaseAbstractEditCommand<WorkflowEntranceUniversalSpec, WorkflowEntranceEdit, EditWorkflowEntranceResult, WorkflowEntrance, WorkflowEntrance> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), List.of(
                        new SecurityRoleDefinition(SecurityRoleGroups.WorkflowEntrance.name(), SecurityRoles.Edit.name())
                ))
        ));
        
        SPEC_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("WorkflowName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("WorkflowEntranceName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("EntityRef", FieldType.ENTITY_REF, false, null, null),
                new FieldDefinition("Uuid", FieldType.UUID, false, null, null)
        );
        
        EDIT_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("WorkflowEntranceName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 132L)
        );
    }
    
    /** Creates a new instance of EditWorkflowEntranceCommand */
    public EditWorkflowEntranceCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }

    @Override
    public EditWorkflowEntranceResult getResult() {
        return WorkflowResultFactory.getEditWorkflowEntranceResult();
    }

    @Override
    public WorkflowEntranceEdit getEdit() {
        return WorkflowEditFactory.getWorkflowEntranceEdit();
    }

    @Override
    public WorkflowEntrance getEntity(EditWorkflowEntranceResult result) {
        return WorkflowEntranceLogic.getInstance().getWorkflowEntranceByUniversalSpec(this, spec, false, editModeToEntityPermission(editMode));
    }

    @Override
    public WorkflowEntrance getLockEntity(WorkflowEntrance freeOnBoard) {
        return freeOnBoard;
    }

    @Override
    public void fillInResult(EditWorkflowEntranceResult result, WorkflowEntrance freeOnBoard) {
        var workflow = Session.getModelController(WorkflowControl.class);

        result.setWorkflowEntrance(workflow.getWorkflowEntranceTransfer(getUserVisit(), freeOnBoard));
    }

    @Override
    public void doLock(WorkflowEntranceEdit edit, WorkflowEntrance workflowEntrance) {
        var workflowControl = Session.getModelController(WorkflowControl.class);
        var workflowEntranceDescription = workflowControl.getWorkflowEntranceDescription(workflowEntrance, getPreferredLanguage());
        var workflowEntranceDetail = workflowEntrance.getLastDetail();

        edit.setWorkflowEntranceName(workflowEntranceDetail.getWorkflowEntranceName());
        edit.setIsDefault(workflowEntranceDetail.getIsDefault().toString());
        edit.setSortOrder(workflowEntranceDetail.getSortOrder().toString());

        if(workflowEntranceDescription != null) {
            edit.setDescription(workflowEntranceDescription.getDescription());
        }
    }

    @Override
    public void canUpdate(WorkflowEntrance workflowEntrance) {
        var workflowControl = Session.getModelController(WorkflowControl.class);
        var workflow = workflowEntrance.getLastDetail().getWorkflow();
        var workflowEntranceName = edit.getWorkflowEntranceName();
        var duplicateWorkflowEntrance = workflowControl.getWorkflowEntranceByName(workflow, workflowEntranceName);

        if(duplicateWorkflowEntrance != null && !workflowEntrance.equals(duplicateWorkflowEntrance)) {
            addExecutionError(ExecutionErrors.DuplicateWorkflowEntranceName.name(), workflowEntranceName);
        }
    }

    @Override
    public void doUpdate(WorkflowEntrance workflowEntrance) {
        var workflowControl = Session.getModelController(WorkflowControl.class);
        var partyPK = getPartyPK();
        var workflowEntranceDetailValue = workflowControl.getWorkflowEntranceDetailValueForUpdate(workflowEntrance);
        var workflowEntranceDescription = workflowControl.getWorkflowEntranceDescriptionForUpdate(workflowEntrance, getPreferredLanguage());
        var description = edit.getDescription();

        workflowEntranceDetailValue.setWorkflowEntranceName(edit.getWorkflowEntranceName());
        workflowEntranceDetailValue.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
        workflowEntranceDetailValue.setSortOrder(Integer.valueOf(edit.getSortOrder()));

        workflowControl.updateWorkflowEntranceFromValue(workflowEntranceDetailValue, partyPK);

        if(workflowEntranceDescription == null && description != null) {
            workflowControl.createWorkflowEntranceDescription(workflowEntrance, getPreferredLanguage(), description, partyPK);
        } else if(workflowEntranceDescription != null && description == null) {
            workflowControl.deleteWorkflowEntranceDescription(workflowEntranceDescription, partyPK);
        } else if(workflowEntranceDescription != null && description != null) {
            var workflowEntranceDescriptionValue = workflowControl.getWorkflowEntranceDescriptionValue(workflowEntranceDescription);

            workflowEntranceDescriptionValue.setDescription(description);
            workflowControl.updateWorkflowEntranceDescriptionFromValue(workflowEntranceDescriptionValue, partyPK);
        }
    }

}
