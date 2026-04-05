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

package com.echothree.control.user.inventory.server.command;

import com.echothree.control.user.inventory.common.edit.InventoryEditFactory;
import com.echothree.control.user.inventory.common.edit.InventoryLocationGroupEdit;
import com.echothree.control.user.inventory.common.result.EditInventoryLocationGroupResult;
import com.echothree.control.user.inventory.common.result.InventoryResultFactory;
import com.echothree.control.user.inventory.common.spec.InventoryLocationGroupSpec;
import com.echothree.model.control.inventory.server.control.InventoryControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.control.warehouse.server.control.WarehouseControl;
import com.echothree.model.data.inventory.server.entity.InventoryLocationGroup;
import com.echothree.model.data.warehouse.server.entity.Warehouse;
import com.echothree.util.common.command.EditMode;
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
public class EditInventoryLocationGroupCommand
        extends BaseAbstractEditCommand<InventoryLocationGroupSpec, InventoryLocationGroupEdit, EditInventoryLocationGroupResult, InventoryLocationGroup, InventoryLocationGroup> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), List.of(
                        new SecurityRoleDefinition(SecurityRoleGroups.InventoryLocationGroup.name(), SecurityRoles.Edit.name())
                ))
        ));

        SPEC_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("WarehouseName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("InventoryLocationGroupName", FieldType.ENTITY_NAME, true, null, null)
        );
        
        EDIT_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("InventoryLocationGroupName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 132L)
        );
    }

    @Inject
    InventoryControl inventoryControl;

    @Inject
    WarehouseControl warehouseControl;

    /** Creates a new instance of EditInventoryLocationGroupCommand */
    public EditInventoryLocationGroupCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }

    @Override
    public EditInventoryLocationGroupResult getResult() {
        return InventoryResultFactory.getEditInventoryLocationGroupResult();
    }

    @Override
    public InventoryLocationGroupEdit getEdit() {
        return InventoryEditFactory.getInventoryLocationGroupEdit();
    }

    Warehouse warehouse;

    @Override
    public InventoryLocationGroup getEntity(EditInventoryLocationGroupResult result) {
        var warehouseName = spec.getWarehouseName();
        InventoryLocationGroup inventoryLocationGroup = null;

        warehouse = warehouseControl.getWarehouseByName(warehouseName);

        if(warehouse != null) {
            var warehouseParty = warehouse.getParty();
            var inventoryLocationGroupName = spec.getInventoryLocationGroupName();

            if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
                inventoryLocationGroup = inventoryControl.getInventoryLocationGroupByName(warehouseParty, inventoryLocationGroupName);
            } else { // EditMode.UPDATE
                inventoryLocationGroup = inventoryControl.getInventoryLocationGroupByNameForUpdate(warehouseParty, inventoryLocationGroupName);
            }

            if(inventoryLocationGroup == null) {
                addExecutionError(ExecutionErrors.UnknownInventoryLocationGroupName.name(), warehouseName, inventoryLocationGroupName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownWarehouseName.name(), warehouseName);
        }

        return inventoryLocationGroup;
    }

    @Override
    public InventoryLocationGroup getLockEntity(InventoryLocationGroup inventoryLocationGroup) {
        return inventoryLocationGroup;
    }

    @Override
    public void fillInResult(EditInventoryLocationGroupResult result, InventoryLocationGroup inventoryLocationGroup) {
        result.setInventoryLocationGroup(inventoryControl.getInventoryLocationGroupTransfer(getUserVisit(), inventoryLocationGroup));
    }

    @Override
    public void doLock(InventoryLocationGroupEdit edit, InventoryLocationGroup inventoryLocationGroup) {
        var inventoryLocationGroupDescription = inventoryControl.getInventoryLocationGroupDescription(inventoryLocationGroup, getPreferredLanguage());
        var inventoryLocationGroupDetail = inventoryLocationGroup.getLastDetail();

        edit.setInventoryLocationGroupName(inventoryLocationGroupDetail.getInventoryLocationGroupName());
        edit.setIsDefault(inventoryLocationGroupDetail.getIsDefault().toString());
        edit.setSortOrder(inventoryLocationGroupDetail.getSortOrder().toString());

        if(inventoryLocationGroupDescription != null) {
            edit.setDescription(inventoryLocationGroupDescription.getDescription());
        }
    }

    @Override
    public void canUpdate(InventoryLocationGroup inventoryLocationGroup) {
        var warehouseParty = warehouse.getParty();
        var inventoryLocationGroupName = edit.getInventoryLocationGroupName();
        var duplicateInventoryLocationGroup = inventoryControl.getInventoryLocationGroupByName(warehouseParty, inventoryLocationGroupName);

        if(duplicateInventoryLocationGroup != null && !inventoryLocationGroup.equals(duplicateInventoryLocationGroup)) {
            addExecutionError(ExecutionErrors.DuplicateInventoryLocationGroupName.name(), inventoryLocationGroupName);
        }
    }

    @Override
    public void doUpdate(InventoryLocationGroup inventoryLocationGroup) {
        var partyPK = getPartyPK();
        var inventoryLocationGroupDetailValue = inventoryControl.getInventoryLocationGroupDetailValueForUpdate(inventoryLocationGroup);
        var inventoryLocationGroupDescription = inventoryControl.getInventoryLocationGroupDescriptionForUpdate(inventoryLocationGroup, getPreferredLanguage());
        var description = edit.getDescription();

        inventoryLocationGroupDetailValue.setInventoryLocationGroupName(edit.getInventoryLocationGroupName());
        inventoryLocationGroupDetailValue.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
        inventoryLocationGroupDetailValue.setSortOrder(Integer.valueOf(edit.getSortOrder()));

        inventoryControl.updateInventoryLocationGroupFromValue(inventoryLocationGroupDetailValue, partyPK);

        if(inventoryLocationGroupDescription == null && description != null) {
            inventoryControl.createInventoryLocationGroupDescription(inventoryLocationGroup, getPreferredLanguage(), description, partyPK);
        } else if(inventoryLocationGroupDescription != null && description == null) {
            inventoryControl.deleteInventoryLocationGroupDescription(inventoryLocationGroupDescription, partyPK);
        } else if(inventoryLocationGroupDescription != null && description != null) {
            var inventoryLocationGroupDescriptionValue = inventoryControl.getInventoryLocationGroupDescriptionValue(inventoryLocationGroupDescription);

            inventoryLocationGroupDescriptionValue.setDescription(description);
            inventoryControl.updateInventoryLocationGroupDescriptionFromValue(inventoryLocationGroupDescriptionValue, partyPK);
        }
    }
    
}
