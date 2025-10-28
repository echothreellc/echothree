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

import com.echothree.model.control.inventory.common.transfer.InventoryAdjustmentTypeTransfer;
import com.echothree.model.control.inventory.server.control.InventoryControl;
import com.echothree.model.control.inventory.server.control.InventoryAdjustmentTypeControl;
import com.echothree.model.control.sequence.server.control.SequenceControl;
import com.echothree.model.control.workflow.server.control.WorkflowControl;
import com.echothree.model.data.inventory.server.entity.InventoryAdjustmentType;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;

public class InventoryAdjustmentTypeTransferCache
        extends BaseInventoryTransferCache<InventoryAdjustmentType, InventoryAdjustmentTypeTransfer> {

    InventoryAdjustmentTypeControl inventoryAdjustmentTypeControl = Session.getModelController(InventoryAdjustmentTypeControl.class);
    SequenceControl sequenceControl = Session.getModelController(SequenceControl.class);
    WorkflowControl workflowControl = Session.getModelController(WorkflowControl.class);
    
    /** Creates a new instance of InventoryAdjustmentTypeTransferCache */
    public InventoryAdjustmentTypeTransferCache(UserVisit userVisit, InventoryControl inventoryControl) {
        super(userVisit, inventoryControl);
        
        setIncludeEntityInstance(true);
    }

    @Override
    public InventoryAdjustmentTypeTransfer getTransfer(InventoryAdjustmentType inventoryAdjustmentType) {
        var inventoryAdjustmentTypeTransfer = get(inventoryAdjustmentType);
        
        if(inventoryAdjustmentTypeTransfer == null) {
            var inventoryAdjustmentTypeDetail = inventoryAdjustmentType.getLastDetail();
            var inventoryAdjustmentTypeName = inventoryAdjustmentTypeDetail.getInventoryAdjustmentTypeName();
            var isDefault = inventoryAdjustmentTypeDetail.getIsDefault();
            var sortOrder = inventoryAdjustmentTypeDetail.getSortOrder();
            var description = inventoryAdjustmentTypeControl.getBestInventoryAdjustmentTypeDescription(inventoryAdjustmentType, getLanguage());
            
            inventoryAdjustmentTypeTransfer = new InventoryAdjustmentTypeTransfer(inventoryAdjustmentTypeName, isDefault,
                    sortOrder, description);
            put(inventoryAdjustmentType, inventoryAdjustmentTypeTransfer);
        }
        
        return inventoryAdjustmentTypeTransfer;
    }
    
}
