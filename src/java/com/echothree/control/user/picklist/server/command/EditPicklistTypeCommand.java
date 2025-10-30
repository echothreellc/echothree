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

package com.echothree.control.user.picklist.server.command;

import com.echothree.control.user.picklist.common.edit.PicklistEditFactory;
import com.echothree.control.user.picklist.common.edit.PicklistTypeEdit;
import com.echothree.control.user.picklist.common.form.EditPicklistTypeForm;
import com.echothree.control.user.picklist.common.result.EditPicklistTypeResult;
import com.echothree.control.user.picklist.common.result.PicklistResultFactory;
import com.echothree.control.user.picklist.common.spec.PicklistTypeSpec;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.picklist.server.control.PicklistControl;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.control.sequence.server.control.SequenceControl;
import com.echothree.model.control.workflow.server.control.WorkflowControl;
import com.echothree.model.data.picklist.server.entity.PicklistType;
import com.echothree.model.data.sequence.server.entity.SequenceType;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.model.data.workflow.server.entity.Workflow;
import com.echothree.model.data.workflow.server.entity.WorkflowEntrance;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.EditMode;
import com.echothree.util.server.control.BaseAbstractEditCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class EditPicklistTypeCommand
        extends BaseAbstractEditCommand<PicklistTypeSpec, PicklistTypeEdit, EditPicklistTypeResult, PicklistType, PicklistType> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                    new SecurityRoleDefinition(SecurityRoleGroups.PicklistType.name(), SecurityRoles.Edit.name())
                    )))
                )));
        
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("PicklistTypeName", FieldType.ENTITY_NAME, true, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("PicklistTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("ParentPicklistTypeName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("PicklistSequenceTypeName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("PicklistWorkflowName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("PicklistWorkflowEntranceName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 132L)
                ));
    }
    
    /** Creates a new instance of EditPicklistTypeCommand */
    public EditPicklistTypeCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }

    @Override
    public EditPicklistTypeResult getResult() {
        return PicklistResultFactory.getEditPicklistTypeResult();
    }

    @Override
    public PicklistTypeEdit getEdit() {
        return PicklistEditFactory.getPicklistTypeEdit();
    }

    @Override
    public PicklistType getEntity(EditPicklistTypeResult result) {
        var picklistControl = Session.getModelController(PicklistControl.class);
        PicklistType picklistType;
        var picklistTypeName = spec.getPicklistTypeName();

        if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
            picklistType = picklistControl.getPicklistTypeByName(picklistTypeName);
        } else { // EditMode.UPDATE
            picklistType = picklistControl.getPicklistTypeByNameForUpdate(picklistTypeName);
        }

        if(picklistType != null) {
            result.setPicklistType(picklistControl.getPicklistTypeTransfer(getUserVisit(), picklistType));
        } else {
            addExecutionError(ExecutionErrors.UnknownPicklistTypeName.name(), picklistTypeName);
        }

        return picklistType;
    }

    @Override
    public PicklistType getLockEntity(PicklistType picklistType) {
        return picklistType;
    }

    @Override
    public void fillInResult(EditPicklistTypeResult result, PicklistType picklistType) {
        var picklistControl = Session.getModelController(PicklistControl.class);

        result.setPicklistType(picklistControl.getPicklistTypeTransfer(getUserVisit(), picklistType));
    }

    PicklistType parentPicklistType = null;
    SequenceType picklistSequenceType = null;
    Workflow picklistWorkflow = null;
    WorkflowEntrance picklistWorkflowEntrance = null;

    @Override
    public void doLock(PicklistTypeEdit edit, PicklistType picklistType) {
        var picklistControl = Session.getModelController(PicklistControl.class);
        var picklistTypeDescription = picklistControl.getPicklistTypeDescription(picklistType, getPreferredLanguage());
        var picklistTypeDetail = picklistType.getLastDetail();

        parentPicklistType = picklistTypeDetail.getParentPicklistType();
        picklistSequenceType = picklistTypeDetail.getPicklistSequenceType();
        picklistWorkflow = picklistTypeDetail.getPicklistWorkflow();
        picklistWorkflowEntrance = picklistTypeDetail.getPicklistWorkflowEntrance();

        edit.setPicklistTypeName(picklistTypeDetail.getPicklistTypeName());
        edit.setParentPicklistTypeName(parentPicklistType == null ? null : parentPicklistType.getLastDetail().getPicklistTypeName());
        edit.setPicklistSequenceTypeName(picklistSequenceType == null ? null : picklistSequenceType.getLastDetail().getSequenceTypeName());
        edit.setPicklistWorkflowName(picklistWorkflow == null ? null : picklistWorkflow.getLastDetail().getWorkflowName());
        edit.setPicklistWorkflowEntranceName(picklistWorkflowEntrance == null ? null : picklistWorkflowEntrance.getLastDetail().getWorkflowEntranceName());
        edit.setIsDefault(picklistTypeDetail.getIsDefault().toString());
        edit.setSortOrder(picklistTypeDetail.getSortOrder().toString());

        if(picklistTypeDescription != null) {
            edit.setDescription(picklistTypeDescription.getDescription());
        }
    }

    @Override
    public void canUpdate(PicklistType picklistType) {
        var picklistControl = Session.getModelController(PicklistControl.class);
        var picklistTypeName = edit.getPicklistTypeName();
        var duplicatePicklistType = picklistControl.getPicklistTypeByName(picklistTypeName);

        if(duplicatePicklistType == null || picklistType.equals(duplicatePicklistType)) {
            var parentPicklistTypeName = edit.getParentPicklistTypeName();

            if(parentPicklistTypeName != null) {
                parentPicklistType = picklistControl.getPicklistTypeByName(parentPicklistTypeName);
            }

            if(parentPicklistTypeName == null || parentPicklistType != null) {
                if(picklistControl.isParentPicklistTypeSafe(picklistType, parentPicklistType)) {
                    var sequenceControl = Session.getModelController(SequenceControl.class);
                    var picklistSequenceTypeName = edit.getPicklistSequenceTypeName();

                    picklistSequenceType = sequenceControl.getSequenceTypeByName(picklistSequenceTypeName);

                    if(picklistSequenceTypeName == null || picklistSequenceType != null) {
                        var workflowControl = Session.getModelController(WorkflowControl.class);
                        var picklistWorkflowName = edit.getPicklistWorkflowName();

                        picklistWorkflow = picklistWorkflowName == null ? null : workflowControl.getWorkflowByName(picklistWorkflowName);

                        if(picklistWorkflowName == null || picklistWorkflow != null) {
                            var picklistWorkflowEntranceName = edit.getPicklistWorkflowEntranceName();

                            if(picklistWorkflowEntranceName == null || (picklistWorkflow != null && picklistWorkflowEntranceName != null)) {
                                picklistWorkflowEntrance = picklistWorkflowEntranceName == null ? null : workflowControl.getWorkflowEntranceByName(picklistWorkflow, picklistWorkflowEntranceName);

                                if(picklistWorkflowEntranceName != null && picklistWorkflowEntrance == null) {
                                    addExecutionError(ExecutionErrors.UnknownPicklistWorkflowEntranceName.name(), picklistWorkflowName, picklistWorkflowEntranceName);
                                }
                            } else {
                                addExecutionError(ExecutionErrors.MissingRequiredPicklistWorkflowName.name());
                            }
                        } else {
                            addExecutionError(ExecutionErrors.UnknownPicklistWorkflowName.name(), picklistWorkflowName);
                        }
                    } else {
                        addExecutionError(ExecutionErrors.UnknownPicklistSequenceTypeName.name(), picklistSequenceTypeName);
                    }
                } else {
                    addExecutionError(ExecutionErrors.InvalidParentPicklistType.name());
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownParentPicklistTypeName.name(), parentPicklistTypeName);
            }
        } else {
            addExecutionError(ExecutionErrors.DuplicatePicklistTypeName.name(), picklistTypeName);
        }
    }

    @Override
    public void doUpdate(PicklistType picklistType) {
        var picklistControl = Session.getModelController(PicklistControl.class);
        var partyPK = getPartyPK();
        var picklistTypeDetailValue = picklistControl.getPicklistTypeDetailValueForUpdate(picklistType);
        var picklistTypeDescription = picklistControl.getPicklistTypeDescriptionForUpdate(picklistType, getPreferredLanguage());
        var description = edit.getDescription();

        picklistTypeDetailValue.setPicklistTypeName(edit.getPicklistTypeName());
        picklistTypeDetailValue.setParentPicklistTypePK(parentPicklistType == null ? null : parentPicklistType.getPrimaryKey());
        picklistTypeDetailValue.setPicklistSequenceTypePK(picklistSequenceType == null ? null : picklistSequenceType.getPrimaryKey());
        picklistTypeDetailValue.setPicklistWorkflowPK(picklistWorkflow == null ? null : picklistWorkflow.getPrimaryKey());
        picklistTypeDetailValue.setPicklistWorkflowEntrancePK(picklistWorkflow == null ? null : picklistWorkflowEntrance.getPrimaryKey());
        picklistTypeDetailValue.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
        picklistTypeDetailValue.setSortOrder(Integer.valueOf(edit.getSortOrder()));

        picklistControl.updatePicklistTypeFromValue(picklistTypeDetailValue, partyPK);

        if(picklistTypeDescription == null && description != null) {
            picklistControl.createPicklistTypeDescription(picklistType, getPreferredLanguage(), description, partyPK);
        } else {
            if(picklistTypeDescription != null && description == null) {
                picklistControl.deletePicklistTypeDescription(picklistTypeDescription, partyPK);
            } else {
                if(picklistTypeDescription != null && description != null) {
                    var picklistTypeDescriptionValue = picklistControl.getPicklistTypeDescriptionValue(picklistTypeDescription);

                    picklistTypeDescriptionValue.setDescription(description);
                    picklistControl.updatePicklistTypeDescriptionFromValue(picklistTypeDescriptionValue, partyPK);
                }
            }
        }
    }

}
