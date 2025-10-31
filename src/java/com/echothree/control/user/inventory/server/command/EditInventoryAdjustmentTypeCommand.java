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

import com.echothree.control.user.inventory.common.edit.InventoryAdjustmentTypeEdit;
import com.echothree.control.user.inventory.common.edit.InventoryEditFactory;
import com.echothree.control.user.inventory.common.result.EditInventoryAdjustmentTypeResult;
import com.echothree.control.user.inventory.common.result.InventoryResultFactory;
import com.echothree.control.user.inventory.common.spec.InventoryAdjustmentTypeUniversalSpec;
import com.echothree.model.control.inventory.server.control.InventoryAdjustmentTypeControl;
import com.echothree.model.control.inventory.server.logic.InventoryAdjustmentTypeLogic;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.inventory.server.entity.InventoryAdjustmentType;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
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
public class EditInventoryAdjustmentTypeCommand
        extends BaseAbstractEditCommand<InventoryAdjustmentTypeUniversalSpec, InventoryAdjustmentTypeEdit, EditInventoryAdjustmentTypeResult, InventoryAdjustmentType, InventoryAdjustmentType> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                    new SecurityRoleDefinition(SecurityRoleGroups.InventoryAdjustmentType.name(), SecurityRoles.Edit.name())
                    )))
                )));
        
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("InventoryAdjustmentTypeName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("EntityRef", FieldType.ENTITY_REF, false, null, null),
                new FieldDefinition("Uuid", FieldType.UUID, false, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("InventoryAdjustmentTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 132L)
                ));
    }
    
    /** Creates a new instance of EditInventoryAdjustmentTypeCommand */
    public EditInventoryAdjustmentTypeCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }

    @Override
    public EditInventoryAdjustmentTypeResult getResult() {
        return InventoryResultFactory.getEditInventoryAdjustmentTypeResult();
    }

    @Override
    public InventoryAdjustmentTypeEdit getEdit() {
        return InventoryEditFactory.getInventoryAdjustmentTypeEdit();
    }

    @Override
    public InventoryAdjustmentType getEntity(EditInventoryAdjustmentTypeResult result) {
        var inventoryAdjustmentTypeControl = Session.getModelController(InventoryAdjustmentTypeControl.class);
        var inventoryAdjustmentTypeName = spec.getInventoryAdjustmentTypeName();
        var inventoryAdjustmentType = InventoryAdjustmentTypeLogic.getInstance().getInventoryAdjustmentTypeByUniversalSpec(this, spec, false,
                editModeToEntityPermission(editMode));

        if(inventoryAdjustmentType != null) {
            result.setInventoryAdjustmentType(inventoryAdjustmentTypeControl.getInventoryAdjustmentTypeTransfer(getUserVisit(), inventoryAdjustmentType));
        } else {
            addExecutionError(ExecutionErrors.UnknownInventoryAdjustmentTypeName.name(), inventoryAdjustmentTypeName);
        }

        return inventoryAdjustmentType;
    }

    @Override
    public InventoryAdjustmentType getLockEntity(InventoryAdjustmentType inventoryAdjustmentType) {
        return inventoryAdjustmentType;
    }

    @Override
    public void fillInResult(EditInventoryAdjustmentTypeResult result, InventoryAdjustmentType inventoryAdjustmentType) {
        var inventoryAdjustmentTypeControl = Session.getModelController(InventoryAdjustmentTypeControl.class);

        result.setInventoryAdjustmentType(inventoryAdjustmentTypeControl.getInventoryAdjustmentTypeTransfer(getUserVisit(), inventoryAdjustmentType));
    }

    @Override
    public void doLock(InventoryAdjustmentTypeEdit edit, InventoryAdjustmentType inventoryAdjustmentType) {
        var inventoryAdjustmentTypeControl = Session.getModelController(InventoryAdjustmentTypeControl.class);
        var inventoryAdjustmentTypeDescription = inventoryAdjustmentTypeControl.getInventoryAdjustmentTypeDescription(inventoryAdjustmentType, getPreferredLanguage());
        var inventoryAdjustmentTypeDetail = inventoryAdjustmentType.getLastDetail();

        edit.setInventoryAdjustmentTypeName(inventoryAdjustmentTypeDetail.getInventoryAdjustmentTypeName());
        edit.setIsDefault(inventoryAdjustmentTypeDetail.getIsDefault().toString());
        edit.setSortOrder(inventoryAdjustmentTypeDetail.getSortOrder().toString());

        if(inventoryAdjustmentTypeDescription != null) {
            edit.setDescription(inventoryAdjustmentTypeDescription.getDescription());
        }
    }

    @Override
    public void canUpdate(InventoryAdjustmentType inventoryAdjustmentType) {
        var inventoryAdjustmentTypeControl = Session.getModelController(InventoryAdjustmentTypeControl.class);
        var inventoryAdjustmentTypeName = edit.getInventoryAdjustmentTypeName();
        var duplicateInventoryAdjustmentType = inventoryAdjustmentTypeControl.getInventoryAdjustmentTypeByName(inventoryAdjustmentTypeName);

        if(duplicateInventoryAdjustmentType != null && !inventoryAdjustmentType.equals(duplicateInventoryAdjustmentType)) {
            addExecutionError(ExecutionErrors.DuplicateInventoryAdjustmentTypeName.name(), inventoryAdjustmentTypeName);
        }
    }

    @Override
    public void doUpdate(InventoryAdjustmentType inventoryAdjustmentType) {
        var inventoryAdjustmentTypeControl = Session.getModelController(InventoryAdjustmentTypeControl.class);
        var partyPK = getPartyPK();
        var inventoryAdjustmentTypeDetailValue = inventoryAdjustmentTypeControl.getInventoryAdjustmentTypeDetailValueForUpdate(inventoryAdjustmentType);
        var inventoryAdjustmentTypeDescription = inventoryAdjustmentTypeControl.getInventoryAdjustmentTypeDescriptionForUpdate(inventoryAdjustmentType, getPreferredLanguage());
        var description = edit.getDescription();

        inventoryAdjustmentTypeDetailValue.setInventoryAdjustmentTypeName(edit.getInventoryAdjustmentTypeName());
        inventoryAdjustmentTypeDetailValue.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
        inventoryAdjustmentTypeDetailValue.setSortOrder(Integer.valueOf(edit.getSortOrder()));

        InventoryAdjustmentTypeLogic.getInstance().updateInventoryAdjustmentTypeFromValue(inventoryAdjustmentTypeDetailValue, partyPK);

        if(inventoryAdjustmentTypeDescription == null && description != null) {
            inventoryAdjustmentTypeControl.createInventoryAdjustmentTypeDescription(inventoryAdjustmentType, getPreferredLanguage(), description, partyPK);
        } else {
            if(inventoryAdjustmentTypeDescription != null && description == null) {
                inventoryAdjustmentTypeControl.deleteInventoryAdjustmentTypeDescription(inventoryAdjustmentTypeDescription, partyPK);
            } else {
                if(inventoryAdjustmentTypeDescription != null && description != null) {
                    var inventoryAdjustmentTypeDescriptionValue = inventoryAdjustmentTypeControl.getInventoryAdjustmentTypeDescriptionValue(inventoryAdjustmentTypeDescription);

                    inventoryAdjustmentTypeDescriptionValue.setDescription(description);
                    inventoryAdjustmentTypeControl.updateInventoryAdjustmentTypeDescriptionFromValue(inventoryAdjustmentTypeDescriptionValue, partyPK);
                }
            }
        }
    }

}
