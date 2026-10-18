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

package com.echothree.control.user.period.server.command;

import com.echothree.control.user.period.common.edit.PeriodEditFactory;
import com.echothree.control.user.period.common.edit.PeriodKindEdit;
import com.echothree.control.user.period.common.result.EditPeriodKindResult;
import com.echothree.control.user.period.common.result.PeriodResultFactory;
import com.echothree.control.user.period.common.spec.PeriodKindSpec;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.period.server.control.PeriodControl;
import com.echothree.model.control.period.server.logic.PeriodKindLogic;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.control.workflow.server.logic.WorkflowEntranceLogic;
import com.echothree.model.data.period.server.entity.PeriodKind;
import com.echothree.model.data.workflow.server.entity.WorkflowEntrance;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseAbstractEditCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class EditPeriodKindCommand
        extends BaseAbstractEditCommand<PeriodKindSpec, PeriodKindEdit, EditPeriodKindResult, PeriodKind, PeriodKind> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;

    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), List.of(
                        new SecurityRoleDefinition(SecurityRoleGroups.PeriodKind.name(), SecurityRoles.Edit.name())
                ))
        ));

        SPEC_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("PeriodKindName", FieldType.ENTITY_NAME, true, null, null)
        );

        EDIT_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("PeriodKindName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("WorkflowName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("WorkflowEntranceName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 132L)
        );
    }

    @Inject
    PeriodControl periodControl;

    @Inject
    PeriodKindLogic periodKindLogic;

    @Inject
    WorkflowEntranceLogic workflowEntranceLogic;

    /** Creates a new instance of EditPeriodKindCommand */
    public EditPeriodKindCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }

    @Override
    public EditPeriodKindResult getResult() {
        return PeriodResultFactory.getEditPeriodKindResult();
    }

    @Override
    public PeriodKindEdit getEdit() {
        return PeriodEditFactory.getPeriodKindEdit();
    }

    @Override
    public PeriodKind getEntity(EditPeriodKindResult result) {
        return periodKindLogic.getPeriodKindByName(this, spec.getPeriodKindName(), editModeToEntityPermission(editMode));
    }

    @Override
    public PeriodKind getLockEntity(PeriodKind periodKind) {
        return periodKind;
    }

    @Override
    public void fillInResult(EditPeriodKindResult result, PeriodKind periodKind) {
        result.setPeriodKind(periodControl.getPeriodKindTransfer(getUserVisit(), periodKind));
    }

    private WorkflowEntrance workflowEntrance;

    @Override
    public void doLock(PeriodKindEdit edit, PeriodKind periodKind) {
        var periodKindDescription = periodControl.getPeriodKindDescription(periodKind, getPreferredLanguage());
        var periodKindDetail = periodKind.getLastDetail();
        var workflowEntrance = periodKindDetail.getWorkflowEntrance();
        var workflow = workflowEntrance == null? null: workflowEntrance.getLastDetail().getWorkflow();

        edit.setPeriodKindName(periodKindDetail.getPeriodKindName());
        edit.setWorkflowName(workflow == null? null: workflow.getLastDetail().getWorkflowName());
        edit.setWorkflowEntranceName(workflowEntrance == null? null: workflowEntrance.getLastDetail().getWorkflowEntranceName());
        edit.setIsDefault(periodKindDetail.getIsDefault().toString());
        edit.setSortOrder(periodKindDetail.getSortOrder().toString());

        if(periodKindDescription != null) {
            edit.setDescription(periodKindDescription.getDescription());
        }
    }

    @Override
    public void canUpdate(PeriodKind periodKind) {
        var periodKindName = edit.getPeriodKindName();
        var duplicatePeriodKind = periodControl.getPeriodKindByName(periodKindName);

        if(duplicatePeriodKind == null || periodKind.equals(duplicatePeriodKind)) {
            var workflowName = edit.getWorkflowName();
            var workflowEntranceName = edit.getWorkflowEntranceName();
            var parameterCount = (workflowName == null ? 0 : 1) + (workflowEntranceName == null ? 0 : 1);

            if(parameterCount == 0 || parameterCount == 2) {
                workflowEntrance = parameterCount == 0? null: workflowEntranceLogic.getWorkflowEntranceByName(this, workflowName, workflowEntranceName);
            } else {
                addExecutionError(ExecutionErrors.InvalidParameterCount.name());
            }
        } else {
            addExecutionError(ExecutionErrors.DuplicatePeriodKindName.name(), periodKindName);
        }
    }

    @Override
    public void doUpdate(PeriodKind periodKind) {
        var partyPK = getPartyPK();
        var periodKindDetailValue = periodControl.getPeriodKindDetailValueForUpdate(periodKind);
        var periodKindDescription = periodControl.getPeriodKindDescriptionForUpdate(periodKind, getPreferredLanguage());
        var description = edit.getDescription();

        periodKindDetailValue.setPeriodKindName(edit.getPeriodKindName());
        periodKindDetailValue.setWorkflowEntrancePK(workflowEntrance == null? null: workflowEntrance.getPrimaryKey());
        periodKindDetailValue.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
        periodKindDetailValue.setSortOrder(Integer.valueOf(edit.getSortOrder()));

        periodControl.updatePeriodKindFromValue(periodKindDetailValue, partyPK);

        if(periodKindDescription == null && description != null) {
            periodControl.createPeriodKindDescription(periodKind, getPreferredLanguage(), description, partyPK);
        } else if(periodKindDescription != null && description == null) {
            periodControl.deletePeriodKindDescription(periodKindDescription, partyPK);
        } else if(periodKindDescription != null && description != null) {
            var periodKindDescriptionValue = periodControl.getPeriodKindDescriptionValue(periodKindDescription);

            periodKindDescriptionValue.setDescription(description);
            periodControl.updatePeriodKindDescriptionFromValue(periodKindDescriptionValue, partyPK);
        }
    }

}
