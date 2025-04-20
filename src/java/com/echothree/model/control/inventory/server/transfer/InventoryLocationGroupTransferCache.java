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

package com.echothree.model.control.inventory.server.transfer;

import com.echothree.model.control.core.server.control.EntityInstanceControl;
import com.echothree.model.control.inventory.common.InventoryOptions;
import com.echothree.model.control.inventory.common.transfer.InventoryLocationGroupTransfer;
import com.echothree.model.control.inventory.common.workflow.InventoryLocationGroupStatusConstants;
import com.echothree.model.control.inventory.server.control.InventoryControl;
import com.echothree.model.control.warehouse.server.control.WarehouseControl;
import com.echothree.model.control.workflow.server.control.WorkflowControl;
import com.echothree.model.data.inventory.server.entity.InventoryLocationGroup;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.common.transfer.ListWrapper;
import com.echothree.util.server.persistence.Session;

public class InventoryLocationGroupTransferCache
        extends BaseInventoryTransferCache<InventoryLocationGroup, InventoryLocationGroupTransfer> {
    
    EntityInstanceControl entityInstanceControl = Session.getModelController(EntityInstanceControl.class);
    WorkflowControl workflowControl = Session.getModelController(WorkflowControl.class);
    boolean includeCapacities;
    boolean includeVolume;
    
    /** Creates a new instance of InventoryLocationGroupTransferCache */
    public InventoryLocationGroupTransferCache(UserVisit userVisit, InventoryControl inventoryControl) {
        super(userVisit, inventoryControl);
        
        var options = session.getOptions();
        if(options != null) {
            includeCapacities = options.contains(InventoryOptions.InventoryLocationGroupIncludeCapacities);
            includeVolume = options.contains(InventoryOptions.InventoryLocationGroupIncludeVolume);
        }
        
        setIncludeEntityInstance(true);
    }
    
    @Override
    public InventoryLocationGroupTransfer getTransfer(InventoryLocationGroup inventoryLocationGroup) {
        var inventoryLocationGroupTransfer = get(inventoryLocationGroup);
        
        if(inventoryLocationGroupTransfer == null) {
            var warehouseControl = Session.getModelController(WarehouseControl.class);
            var inventoryLocationGroupDetail = inventoryLocationGroup.getLastDetail();
            var warehouseParty = inventoryLocationGroupDetail.getWarehouseParty();
            var warehouse = warehouseControl.getWarehouse(warehouseParty);
            var warehouseTransfer = warehouseControl.getWarehouseTransferCaches(userVisit).getWarehouseTransferCache().getWarehouseTransfer(warehouse);
            var inventoryLocationGroupName = inventoryLocationGroupDetail.getInventoryLocationGroupName();
            var isDefault = inventoryLocationGroupDetail.getIsDefault();
            var sortOrder = inventoryLocationGroupDetail.getSortOrder();
            var description = inventoryControl.getBestInventoryLocationGroupDescription(inventoryLocationGroup, getLanguage());

            var entityInstance = entityInstanceControl.getEntityInstanceByBasePK(inventoryLocationGroup.getPrimaryKey());
            var inventoryLocationGroupStatusTransfer = workflowControl.getWorkflowEntityStatusTransferByEntityInstanceUsingNames(userVisit,
                    InventoryLocationGroupStatusConstants.Workflow_INVENTORY_LOCATION_GROUP_STATUS, entityInstance);
            
            inventoryLocationGroupTransfer = new InventoryLocationGroupTransfer(warehouseTransfer, inventoryLocationGroupName, isDefault, sortOrder,
                    description, inventoryLocationGroupStatusTransfer);
            put(inventoryLocationGroup, inventoryLocationGroupTransfer);
            
            if(includeCapacities) {
                inventoryLocationGroupTransfer.setInventoryLocationGroupCapacities(new ListWrapper<>(inventoryControl.getInventoryLocationGroupCapacityTransfersByInventoryLocationGroup(userVisit, inventoryLocationGroup)));
            }
            
            if(includeVolume) {
                var inventoryLocationGroupVolume = inventoryControl.getInventoryLocationGroupVolume(inventoryLocationGroup);
                
                if(inventoryLocationGroupVolume != null) {
                    inventoryLocationGroupTransfer.setInventoryLocationGroupVolume(inventoryControl.getInventoryLocationGroupVolumeTransfer(userVisit, inventoryLocationGroupVolume));
                }
            }
        }
        
        return inventoryLocationGroupTransfer;
    }
    
}
