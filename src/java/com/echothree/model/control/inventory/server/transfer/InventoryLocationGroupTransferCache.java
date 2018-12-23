// --------------------------------------------------------------------------------
// Copyright 2002-2019 Echo Three, LLC
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

import com.echothree.model.control.core.server.CoreControl;
import com.echothree.model.control.inventory.common.InventoryOptions;
import com.echothree.model.control.inventory.common.transfer.InventoryLocationGroupTransfer;
import com.echothree.model.control.inventory.server.InventoryControl;
import com.echothree.model.control.warehouse.common.transfer.WarehouseTransfer;
import com.echothree.model.control.warehouse.server.WarehouseControl;
import com.echothree.model.control.inventory.common.workflow.InventoryLocationGroupStatusConstants;
import com.echothree.model.control.workflow.common.transfer.WorkflowEntityStatusTransfer;
import com.echothree.model.control.workflow.server.WorkflowControl;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.inventory.server.entity.InventoryLocationGroup;
import com.echothree.model.data.inventory.server.entity.InventoryLocationGroupDetail;
import com.echothree.model.data.inventory.server.entity.InventoryLocationGroupVolume;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.model.data.warehouse.server.entity.Warehouse;
import com.echothree.util.common.transfer.ListWrapper;
import com.echothree.util.server.persistence.Session;
import java.util.Set;

public class InventoryLocationGroupTransferCache
        extends BaseInventoryTransferCache<InventoryLocationGroup, InventoryLocationGroupTransfer> {
    
    WorkflowControl workflowControl;
    CoreControl coreControl;
    boolean includeCapacities;
    boolean includeVolume;
    
    /** Creates a new instance of InventoryLocationGroupTransferCache */
    public InventoryLocationGroupTransferCache(UserVisit userVisit, InventoryControl inventoryControl) {
        super(userVisit, inventoryControl);
        
        coreControl = (CoreControl)Session.getModelController(CoreControl.class);
        workflowControl = (WorkflowControl)Session.getModelController(WorkflowControl.class);
        
        Set<String> options = session.getOptions();
        if(options != null) {
            includeCapacities = options.contains(InventoryOptions.InventoryLocationGroupIncludeCapacities);
            includeVolume = options.contains(InventoryOptions.InventoryLocationGroupIncludeVolume);
        }
        
        setIncludeEntityInstance(true);
    }
    
    public InventoryLocationGroupTransfer getInventoryLocationGroupTransfer(InventoryLocationGroup inventoryLocationGroup) {
        InventoryLocationGroupTransfer inventoryLocationGroupTransfer = get(inventoryLocationGroup);
        
        if(inventoryLocationGroupTransfer == null) {
            WarehouseControl warehouseControl = (WarehouseControl)Session.getModelController(WarehouseControl.class);
            InventoryLocationGroupDetail inventoryLocationGroupDetail = inventoryLocationGroup.getLastDetail();
            Party warehouseParty = inventoryLocationGroupDetail.getWarehouseParty();
            Warehouse warehouse = warehouseControl.getWarehouse(warehouseParty);
            WarehouseTransfer warehouseTransfer = warehouseControl.getWarehouseTransferCaches(userVisit).getWarehouseTransferCache().getWarehouseTransfer(warehouse);
            String inventoryLocationGroupName = inventoryLocationGroupDetail.getInventoryLocationGroupName();
            Boolean isDefault = inventoryLocationGroupDetail.getIsDefault();
            Integer sortOrder = inventoryLocationGroupDetail.getSortOrder();
            String description = inventoryControl.getBestInventoryLocationGroupDescription(inventoryLocationGroup, getLanguage());
            
            EntityInstance entityInstance = coreControl.getEntityInstanceByBasePK(inventoryLocationGroup.getPrimaryKey());
            WorkflowEntityStatusTransfer inventoryLocationGroupStatusTransfer = workflowControl.getWorkflowEntityStatusTransferByEntityInstanceUsingNames(userVisit,
                    InventoryLocationGroupStatusConstants.Workflow_INVENTORY_LOCATION_GROUP_STATUS, entityInstance);
            
            inventoryLocationGroupTransfer = new InventoryLocationGroupTransfer(warehouseTransfer, inventoryLocationGroupName, isDefault, sortOrder,
                    description, inventoryLocationGroupStatusTransfer);
            put(inventoryLocationGroup, inventoryLocationGroupTransfer);
            
            if(includeCapacities) {
                inventoryLocationGroupTransfer.setInventoryLocationGroupCapacities(new ListWrapper<>(inventoryControl.getInventoryLocationGroupCapacityTransfersByInventoryLocationGroup(userVisit, inventoryLocationGroup)));
            }
            
            if(includeVolume) {
                InventoryLocationGroupVolume inventoryLocationGroupVolume = inventoryControl.getInventoryLocationGroupVolume(inventoryLocationGroup);
                
                if(inventoryLocationGroupVolume != null) {
                    inventoryLocationGroupTransfer.setInventoryLocationGroupVolume(inventoryControl.getInventoryLocationGroupVolumeTransfer(userVisit, inventoryLocationGroupVolume));
                }
            }
        }
        
        return inventoryLocationGroupTransfer;
    }
    
}
