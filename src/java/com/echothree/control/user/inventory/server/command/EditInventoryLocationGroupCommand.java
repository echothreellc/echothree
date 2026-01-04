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
import com.echothree.control.user.inventory.common.form.EditInventoryLocationGroupForm;
import com.echothree.control.user.inventory.common.result.InventoryResultFactory;
import com.echothree.control.user.inventory.common.spec.InventoryLocationGroupSpec;
import com.echothree.model.control.inventory.server.control.InventoryControl;
import com.echothree.model.control.warehouse.server.control.WarehouseControl;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.command.EditMode;
import com.echothree.util.server.control.BaseEditCommand;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.enterprise.context.Dependent;

@Dependent
public class EditInventoryLocationGroupCommand
        extends BaseEditCommand<InventoryLocationGroupSpec, InventoryLocationGroupEdit> {
    
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
            new FieldDefinition("WarehouseName", FieldType.ENTITY_NAME, true, null, null),
            new FieldDefinition("InventoryLocationGroupName", FieldType.ENTITY_NAME, true, null, null)
        ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
            new FieldDefinition("InventoryLocationGroupName", FieldType.ENTITY_NAME, true, null, null),
            new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
            new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
            new FieldDefinition("Description", FieldType.STRING, false, 1L, 132L)
        ));
    }
    
    /** Creates a new instance of EditInventoryLocationGroupCommand */
    public EditInventoryLocationGroupCommand() {
        super(null, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    protected BaseResult execute() {
        var warehouseControl = Session.getModelController(WarehouseControl.class);
        var result = InventoryResultFactory.getEditInventoryLocationGroupResult();
        var warehouseName = spec.getWarehouseName();
        var warehouse = warehouseControl.getWarehouseByName(warehouseName);
        
        if(warehouse != null) {
            var inventoryControl = Session.getModelController(InventoryControl.class);
            var warehouseParty = warehouse.getParty();
            
            if(editMode.equals(EditMode.LOCK)) {
                var inventoryLocationGroupName = spec.getInventoryLocationGroupName();
                var inventoryLocationGroup = inventoryControl.getInventoryLocationGroupByName(warehouseParty, inventoryLocationGroupName);
                
                if(inventoryLocationGroup != null) {
                    result.setInventoryLocationGroup(inventoryControl.getInventoryLocationGroupTransfer(getUserVisit(), inventoryLocationGroup));
                    
                    if(lockEntity(inventoryLocationGroup)) {
                        var inventoryLocationGroupDescription = inventoryControl.getInventoryLocationGroupDescription(inventoryLocationGroup, getPreferredLanguage());
                        var edit = InventoryEditFactory.getInventoryLocationGroupEdit();
                        var inventoryLocationGroupDetail = inventoryLocationGroup.getLastDetail();
                        
                        result.setEdit(edit);
                        edit.setInventoryLocationGroupName(inventoryLocationGroupDetail.getInventoryLocationGroupName());
                        edit.setIsDefault(inventoryLocationGroupDetail.getIsDefault().toString());
                        edit.setSortOrder(inventoryLocationGroupDetail.getSortOrder().toString());
                        
                        if(inventoryLocationGroupDescription != null) {
                            edit.setDescription(inventoryLocationGroupDescription.getDescription());
                        }
                    } else {
                        addExecutionError(ExecutionErrors.EntityLockFailed.name());
                    }
                    
                    result.setEntityLock(getEntityLockTransfer(inventoryLocationGroup));
                } else {
                    addExecutionError(ExecutionErrors.UnknownInventoryLocationGroupName.name(), inventoryLocationGroupName);
                }
            } else if(editMode.equals(EditMode.UPDATE)) {
                var inventoryLocationGroupName = spec.getInventoryLocationGroupName();
                var inventoryLocationGroup = inventoryControl.getInventoryLocationGroupByNameForUpdate(warehouseParty, inventoryLocationGroupName);
                
                if(inventoryLocationGroup != null) {
                    inventoryLocationGroupName = edit.getInventoryLocationGroupName();
                    var duplicateInventoryLocationGroup = inventoryControl.getInventoryLocationGroupByName(warehouseParty, inventoryLocationGroupName);
                    
                    if(duplicateInventoryLocationGroup == null || inventoryLocationGroup.equals(duplicateInventoryLocationGroup)) {
                        if(lockEntityForUpdate(inventoryLocationGroup)) {
                            try {
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
                            } finally {
                                unlockEntity(inventoryLocationGroup);
                            }
                        } else {
                            addExecutionError(ExecutionErrors.EntityLockStale.name());
                        }
                    } else {
                        addExecutionError(ExecutionErrors.DuplicateInventoryLocationGroupName.name(), inventoryLocationGroupName);
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownInventoryLocationGroupName.name(), inventoryLocationGroupName);
                }
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownWarehouseName.name(), warehouseName);
        }
        
        return result;
    }
    
}
