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

package com.echothree.control.user.inventory.server.command;

import com.echothree.control.user.inventory.common.edit.InventoryEditFactory;
import com.echothree.control.user.inventory.common.edit.InventoryTransactionTypeEdit;
import com.echothree.control.user.inventory.common.result.EditInventoryTransactionTypeResult;
import com.echothree.control.user.inventory.common.result.InventoryResultFactory;
import com.echothree.control.user.inventory.common.spec.InventoryTransactionTypeUniversalSpec;
import com.echothree.model.control.inventory.common.exception.UnknownInventoryTransactionWorkflowEntranceNameException;
import com.echothree.model.control.inventory.common.exception.UnknownInventoryTransactionWorkflowNameException;
import com.echothree.model.control.inventory.server.control.InventoryTransactionTypeControl;
import com.echothree.model.control.inventory.server.logic.InventoryTransactionTypeLogic;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.control.sequence.server.logic.SequenceTypeLogic;
import com.echothree.model.control.workflow.server.logic.WorkflowEntranceLogic;
import com.echothree.model.control.workflow.server.logic.WorkflowLogic;
import com.echothree.model.data.inventory.server.entity.InventoryTransactionType;
import com.echothree.model.data.sequence.server.entity.SequenceType;
import com.echothree.model.data.workflow.server.entity.Workflow;
import com.echothree.model.data.workflow.server.entity.WorkflowEntrance;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseAbstractEditCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class EditInventoryTransactionTypeCommand
        extends BaseAbstractEditCommand<InventoryTransactionTypeUniversalSpec, InventoryTransactionTypeEdit, EditInventoryTransactionTypeResult, InventoryTransactionType, InventoryTransactionType> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                    new SecurityRoleDefinition(SecurityRoleGroups.InventoryTransactionType.name(), SecurityRoles.Edit.name())
                    )))
                )));
        
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("InventoryTransactionTypeName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("EntityRef", FieldType.ENTITY_REF, false, null, null),
                new FieldDefinition("Uuid", FieldType.UUID, false, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("InventoryTransactionTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("InventoryTransactionSequenceTypeName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("InventoryTransactionWorkflowName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("InventoryTransactionWorkflowEntranceName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 132L)
                ));
    }
    
    /** Creates a new instance of EditInventoryTransactionTypeCommand */
    public EditInventoryTransactionTypeCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }

    @Override
    public EditInventoryTransactionTypeResult getResult() {
        return InventoryResultFactory.getEditInventoryTransactionTypeResult();
    }

    @Override
    public InventoryTransactionTypeEdit getEdit() {
        return InventoryEditFactory.getInventoryTransactionTypeEdit();
    }

    @Override
    public InventoryTransactionType getEntity(EditInventoryTransactionTypeResult result) {
        var inventoryTransactionTypeControl = Session.getModelController(InventoryTransactionTypeControl.class);
        var inventoryTransactionTypeName = spec.getInventoryTransactionTypeName();
        var inventoryTransactionType = InventoryTransactionTypeLogic.getInstance().getInventoryTransactionTypeByUniversalSpec(this, spec, false,
                editModeToEntityPermission(editMode));

        if(inventoryTransactionType != null) {
            result.setInventoryTransactionType(inventoryTransactionTypeControl.getInventoryTransactionTypeTransfer(getUserVisit(), inventoryTransactionType));
        } else {
            addExecutionError(ExecutionErrors.UnknownInventoryTransactionTypeName.name(), inventoryTransactionTypeName);
        }

        return inventoryTransactionType;
    }

    @Override
    public InventoryTransactionType getLockEntity(InventoryTransactionType inventoryTransactionType) {
        return inventoryTransactionType;
    }

    @Override
    public void fillInResult(EditInventoryTransactionTypeResult result, InventoryTransactionType inventoryTransactionType) {
        var inventoryTransactionTypeControl = Session.getModelController(InventoryTransactionTypeControl.class);

        result.setInventoryTransactionType(inventoryTransactionTypeControl.getInventoryTransactionTypeTransfer(getUserVisit(), inventoryTransactionType));
    }

    SequenceType inventoryTransactionSequenceType = null;
    Workflow inventoryTransactionWorkflow = null;
    WorkflowEntrance inventoryTransactionWorkflowEntrance = null;

    @Override
    public void doLock(InventoryTransactionTypeEdit edit, InventoryTransactionType inventoryTransactionType) {
        var inventoryTransactionTypeControl = Session.getModelController(InventoryTransactionTypeControl.class);
        var inventoryTransactionTypeDescription = inventoryTransactionTypeControl.getInventoryTransactionTypeDescription(inventoryTransactionType, getPreferredLanguage());
        var inventoryTransactionTypeDetail = inventoryTransactionType.getLastDetail();

        inventoryTransactionSequenceType = inventoryTransactionTypeDetail.getInventoryTransactionSequenceType();
        inventoryTransactionWorkflow = inventoryTransactionTypeDetail.getInventoryTransactionWorkflow();
        inventoryTransactionWorkflowEntrance = inventoryTransactionTypeDetail.getInventoryTransactionWorkflowEntrance();

        edit.setInventoryTransactionTypeName(inventoryTransactionTypeDetail.getInventoryTransactionTypeName());
        edit.setInventoryTransactionSequenceTypeName(inventoryTransactionSequenceType == null ? null : inventoryTransactionSequenceType.getLastDetail().getSequenceTypeName());
        edit.setInventoryTransactionWorkflowName(inventoryTransactionWorkflow == null ? null : inventoryTransactionWorkflow.getLastDetail().getWorkflowName());
        edit.setInventoryTransactionWorkflowEntranceName(inventoryTransactionWorkflowEntrance == null ? null : inventoryTransactionWorkflowEntrance.getLastDetail().getWorkflowEntranceName());
        edit.setIsDefault(inventoryTransactionTypeDetail.getIsDefault().toString());
        edit.setSortOrder(inventoryTransactionTypeDetail.getSortOrder().toString());

        if(inventoryTransactionTypeDescription != null) {
            edit.setDescription(inventoryTransactionTypeDescription.getDescription());
        }
    }

    @Override
    public void canUpdate(InventoryTransactionType inventoryTransactionType) {
        var inventoryTransactionTypeControl = Session.getModelController(InventoryTransactionTypeControl.class);
        var inventoryTransactionTypeName = edit.getInventoryTransactionTypeName();
        var duplicateInventoryTransactionType = inventoryTransactionTypeControl.getInventoryTransactionTypeByName(inventoryTransactionTypeName);

        if(duplicateInventoryTransactionType == null || inventoryTransactionType.equals(duplicateInventoryTransactionType)) {
            var inventoryTransactionSequenceTypeName = edit.getInventoryTransactionSequenceTypeName();

            inventoryTransactionSequenceType = inventoryTransactionSequenceTypeName == null ? null :
                    SequenceTypeLogic.getInstance().getSequenceTypeByName(this, inventoryTransactionSequenceTypeName);

            if(!hasExecutionErrors()) {
                var inventoryTransactionWorkflowName = edit.getInventoryTransactionWorkflowName();

                inventoryTransactionWorkflow = inventoryTransactionWorkflowName == null ? null : WorkflowLogic.getInstance().getWorkflowByName(
                        UnknownInventoryTransactionWorkflowNameException.class, ExecutionErrors.UnknownInventoryTransactionWorkflowName, this,
                        inventoryTransactionWorkflowName, EntityPermission.READ_ONLY);

                if(!hasExecutionErrors()) {
                    var inventoryTransactionWorkflowEntranceName = edit.getInventoryTransactionWorkflowEntranceName();

                    if(inventoryTransactionWorkflowEntranceName == null || inventoryTransactionWorkflow != null) {
                            inventoryTransactionWorkflowEntrance = inventoryTransactionWorkflowEntranceName == null ? null : WorkflowEntranceLogic.getInstance().getWorkflowEntranceByName(
                                    UnknownInventoryTransactionWorkflowEntranceNameException.class, ExecutionErrors.UnknownInventoryTransactionWorkflowEntranceName, this,
                                    inventoryTransactionWorkflow, inventoryTransactionWorkflowEntranceName);
                    } else {
                        addExecutionError(ExecutionErrors.MissingRequiredInventoryTransactionWorkflowName.name());
                    }
                }
            }
        }
    }

    @Override
    public void doUpdate(InventoryTransactionType inventoryTransactionType) {
        var inventoryTransactionTypeControl = Session.getModelController(InventoryTransactionTypeControl.class);
        var partyPK = getPartyPK();
        var inventoryTransactionTypeDetailValue = inventoryTransactionTypeControl.getInventoryTransactionTypeDetailValueForUpdate(inventoryTransactionType);
        var inventoryTransactionTypeDescription = inventoryTransactionTypeControl.getInventoryTransactionTypeDescriptionForUpdate(inventoryTransactionType, getPreferredLanguage());
        var description = edit.getDescription();

        inventoryTransactionTypeDetailValue.setInventoryTransactionTypeName(edit.getInventoryTransactionTypeName());
        inventoryTransactionTypeDetailValue.setInventoryTransactionSequenceTypePK(inventoryTransactionSequenceType == null ? null : inventoryTransactionSequenceType.getPrimaryKey());
        inventoryTransactionTypeDetailValue.setInventoryTransactionWorkflowPK(inventoryTransactionWorkflow == null ? null : inventoryTransactionWorkflow.getPrimaryKey());
        inventoryTransactionTypeDetailValue.setInventoryTransactionWorkflowEntrancePK(inventoryTransactionWorkflow == null ? null : inventoryTransactionWorkflowEntrance.getPrimaryKey());
        inventoryTransactionTypeDetailValue.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
        inventoryTransactionTypeDetailValue.setSortOrder(Integer.valueOf(edit.getSortOrder()));

        InventoryTransactionTypeLogic.getInstance().updateInventoryTransactionTypeFromValue(inventoryTransactionTypeDetailValue, partyPK);

        if(inventoryTransactionTypeDescription == null && description != null) {
            inventoryTransactionTypeControl.createInventoryTransactionTypeDescription(inventoryTransactionType, getPreferredLanguage(), description, partyPK);
        } else {
            if(inventoryTransactionTypeDescription != null && description == null) {
                inventoryTransactionTypeControl.deleteInventoryTransactionTypeDescription(inventoryTransactionTypeDescription, partyPK);
            } else {
                if(inventoryTransactionTypeDescription != null && description != null) {
                    var inventoryTransactionTypeDescriptionValue = inventoryTransactionTypeControl.getInventoryTransactionTypeDescriptionValue(inventoryTransactionTypeDescription);

                    inventoryTransactionTypeDescriptionValue.setDescription(description);
                    inventoryTransactionTypeControl.updateInventoryTransactionTypeDescriptionFromValue(inventoryTransactionTypeDescriptionValue, partyPK);
                }
            }
        }
    }

}
