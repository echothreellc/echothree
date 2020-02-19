// --------------------------------------------------------------------------------
// Copyright 2002-2020 Echo Three, LLC
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
import com.echothree.control.user.period.common.edit.PeriodTypeEdit;
import com.echothree.control.user.period.common.form.EditPeriodTypeForm;
import com.echothree.control.user.period.common.result.EditPeriodTypeResult;
import com.echothree.control.user.period.common.result.PeriodResultFactory;
import com.echothree.control.user.period.common.spec.PeriodTypeSpec;
import com.echothree.model.control.core.common.EventTypes;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.period.server.PeriodControl;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.control.workflow.server.WorkflowControl;
import com.echothree.model.data.party.common.pk.PartyPK;
import com.echothree.model.data.period.server.entity.PeriodKind;
import com.echothree.model.data.period.server.entity.PeriodType;
import com.echothree.model.data.period.server.entity.PeriodTypeDescription;
import com.echothree.model.data.period.server.entity.PeriodTypeDetail;
import com.echothree.model.data.period.server.value.PeriodTypeDescriptionValue;
import com.echothree.model.data.period.server.value.PeriodTypeDetailValue;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.model.data.workflow.server.entity.Workflow;
import com.echothree.model.data.workflow.server.entity.WorkflowEntrance;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.command.EditMode;
import com.echothree.util.server.control.BaseEditCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class EditPeriodTypeCommand
        extends BaseEditCommand<PeriodTypeSpec, PeriodTypeEdit> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                    new SecurityRoleDefinition(SecurityRoleGroups.PeriodType.name(), SecurityRoles.Edit.name())
                    )))
                )));
        
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("PeriodKindName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("PeriodTypeName", FieldType.ENTITY_NAME, true, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("PeriodTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("ParentPeriodTypeName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("WorkflowName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("WorkflowEntranceName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 80L)
                ));
    }
    
    /** Creates a new instance of EditPeriodTypeCommand */
    public EditPeriodTypeCommand(UserVisitPK userVisitPK, EditPeriodTypeForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    protected BaseResult execute() {
        var periodControl = (PeriodControl)Session.getModelController(PeriodControl.class);
        EditPeriodTypeResult result = PeriodResultFactory.getEditPeriodTypeResult();
        String periodKindName = spec.getPeriodKindName();
        PeriodKind periodKind = periodControl.getPeriodKindByName(periodKindName);
        
        if(periodKind != null) {
            if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
                String periodTypeName = spec.getPeriodTypeName();
                PeriodType periodType = periodControl.getPeriodTypeByName(periodKind, periodTypeName);
                
                if(periodType != null) {
                    if(editMode.equals(EditMode.LOCK)) {
                        if(lockEntity(periodType)) {
                            PeriodTypeDescription periodTypeDescription = periodControl.getPeriodTypeDescription(periodType, getPreferredLanguage());
                            PeriodTypeEdit edit = PeriodEditFactory.getPeriodTypeEdit();
                            PeriodTypeDetail periodTypeDetail = periodType.getLastDetail();
                            PeriodType parentPeriodType = periodTypeDetail.getParentPeriodType();
                            WorkflowEntrance workflowEntrance = periodTypeDetail.getWorkflowEntrance();
                            Workflow workflow = workflowEntrance == null? null: workflowEntrance.getLastDetail().getWorkflow();

                            result.setPeriodType(periodControl.getPeriodTypeTransfer(getUserVisit(), periodType));
                            sendEventUsingNames(periodType.getPrimaryKey(), EventTypes.READ.name(), null, null, getPartyPK());

                            result.setEdit(edit);
                            edit.setPeriodTypeName(periodTypeDetail.getPeriodTypeName());
                            edit.setParentPeriodType(parentPeriodType == null? null: parentPeriodType.getLastDetail().getPeriodTypeName());
                            edit.setWorkflowName(workflow == null? null: workflow.getLastDetail().getWorkflowName());
                            edit.setWorkflowEntranceName(workflowEntrance == null? null: workflowEntrance.getLastDetail().getWorkflowEntranceName());
                            edit.setIsDefault(periodTypeDetail.getIsDefault().toString());
                            edit.setSortOrder(periodTypeDetail.getSortOrder().toString());

                            if(periodTypeDescription != null)
                                edit.setDescription(periodTypeDescription.getDescription());
                        } else {
                            addExecutionError(ExecutionErrors.EntityLockFailed.name());
                        }

                        result.setEntityLock(getEntityLockTransfer(periodType));
                    } else { // EditMode.ABANDON
                        unlockEntity(periodType);
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownPeriodTypeName.name(), periodTypeName);
                }
            } else if(editMode.equals(EditMode.UPDATE)) {
                String periodTypeName = spec.getPeriodTypeName();
                PeriodType periodType = periodControl.getPeriodTypeByNameForUpdate(periodKind, periodTypeName);
                
                if(periodType != null) {
                    periodTypeName = edit.getPeriodTypeName();
                    PeriodType duplicatePeriodType = periodControl.getPeriodTypeByName(periodKind, periodTypeName);

                    if(duplicatePeriodType == null || periodType.equals(duplicatePeriodType)) {
                        String parentPeriodTypeName = edit.getParentPeriodTypeName();
                        PeriodType parentPeriodType = null;

                        if(parentPeriodTypeName != null) {
                            parentPeriodType = periodControl.getPeriodTypeByName(periodKind, parentPeriodTypeName);
                        }

                        if(parentPeriodTypeName == null || parentPeriodType != null) {
                            if(periodControl.isParentPeriodTypeSafe(periodType, parentPeriodType)) {
                                String workflowName = edit.getWorkflowName();
                                String workflowEntranceName = edit.getWorkflowEntranceName();
                                int parameterCount = (workflowName == null ? 0 : 1) + (workflowEntranceName == null ? 0 : 1);

                                if(parameterCount == 0 || parameterCount == 2) {
                                    var workflowControl = (WorkflowControl)Session.getModelController(WorkflowControl.class);
                                    Workflow workflow = workflowName == null ? null : workflowControl.getWorkflowByName(workflowName);

                                    if(workflowName == null || workflow != null) {
                                         WorkflowEntrance workflowEntrance = workflowEntranceName == null? null: workflowControl.getWorkflowEntranceByName(workflow, workflowEntranceName);

                                        if(workflowEntranceName == null || workflowEntrance != null) {
                                            if(lockEntityForUpdate(periodType)) {
                                                try {
                                                    PartyPK partyPK = getPartyPK();
                                                    PeriodTypeDetailValue periodTypeDetailValue = periodControl.getPeriodTypeDetailValueForUpdate(periodType);
                                                    PeriodTypeDescription periodTypeDescription = periodControl.getPeriodTypeDescriptionForUpdate(periodType, getPreferredLanguage());
                                                    String description = edit.getDescription();

                                                    periodTypeDetailValue.setPeriodTypeName(edit.getPeriodTypeName());
                                                    periodTypeDetailValue.setParentPeriodTypePK(parentPeriodType == null? null: parentPeriodType.getPrimaryKey());
                                                    periodTypeDetailValue.setWorkflowEntrancePK(workflowEntrance == null? null: workflowEntrance.getPrimaryKey());
                                                    periodTypeDetailValue.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
                                                    periodTypeDetailValue.setSortOrder(Integer.valueOf(edit.getSortOrder()));

                                                    periodControl.updatePeriodTypeFromValue(periodTypeDetailValue, partyPK);

                                                    if(periodTypeDescription == null && description != null) {
                                                        periodControl.createPeriodTypeDescription(periodType, getPreferredLanguage(), description, partyPK);
                                                    } else if(periodTypeDescription != null && description == null) {
                                                        periodControl.deletePeriodTypeDescription(periodTypeDescription, partyPK);
                                                    } else if(periodTypeDescription != null && description != null) {
                                                        PeriodTypeDescriptionValue periodTypeDescriptionValue = periodControl.getPeriodTypeDescriptionValue(periodTypeDescription);

                                                        periodTypeDescriptionValue.setDescription(description);
                                                        periodControl.updatePeriodTypeDescriptionFromValue(periodTypeDescriptionValue, partyPK);
                                                    }
                                                } finally {
                                                    unlockEntity(periodType);
                                                }
                                            } else {
                                                addExecutionError(ExecutionErrors.EntityLockStale.name());
                                            }
                                        } else {
                                            addExecutionError(ExecutionErrors.UnknownWorkflowEntranceName.name(), workflowName, workflowEntranceName);
                                        }
                                    } else {
                                        addExecutionError(ExecutionErrors.UnknownWorkflowName.name(), workflowName);
                                    }
                                } else {
                                    addExecutionError(ExecutionErrors.InvalidParameterCount.name());
                                }
                            } else {
                                addExecutionError(ExecutionErrors.InvalidParentPeriodType.name());
                            }
                        } else {
                            addExecutionError(ExecutionErrors.UnknownParentPeriodTypeName.name(), parentPeriodTypeName);
                        }
                    } else {
                        addExecutionError(ExecutionErrors.DuplicatePeriodTypeName.name(), periodTypeName);
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownPeriodTypeName.name(), periodTypeName);
                }
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownPeriodKindName.name(), periodKindName);
        }

        return result;
    }

}
