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

package com.echothree.model.control.inventory.server.transfer;

import com.echothree.model.control.inventory.common.transfer.InventoryTransactionTypeTransfer;
import com.echothree.model.control.inventory.server.control.InventoryTransactionTypeControl;
import com.echothree.model.control.sequence.server.control.SequenceControl;
import com.echothree.model.control.workflow.server.control.WorkflowControl;
import com.echothree.model.data.inventory.server.entity.InventoryTransactionType;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class InventoryTransactionTypeTransferCache
        extends BaseInventoryTransferCache<InventoryTransactionType, InventoryTransactionTypeTransfer> {

    InventoryTransactionTypeControl inventoryTransactionTypeControl = Session.getModelController(InventoryTransactionTypeControl.class);
    SequenceControl sequenceControl = Session.getModelController(SequenceControl.class);
    WorkflowControl workflowControl = Session.getModelController(WorkflowControl.class);
    
    /** Creates a new instance of InventoryTransactionTypeTransferCache */
    protected InventoryTransactionTypeTransferCache() {
        super();
        
        setIncludeEntityInstance(true);
    }

    @Override
    public InventoryTransactionTypeTransfer getTransfer(UserVisit userVisit, InventoryTransactionType inventoryTransactionType) {
        var inventoryTransactionTypeTransfer = get(inventoryTransactionType);
        
        if(inventoryTransactionTypeTransfer == null) {
            var inventoryTransactionTypeDetail = inventoryTransactionType.getLastDetail();
            var inventoryTransactionTypeName = inventoryTransactionTypeDetail.getInventoryTransactionTypeName();
            var inventoryTransactionSequenceType = inventoryTransactionTypeDetail.getInventoryTransactionSequenceType();
            var inventoryTransactionSequenceTypeTransfer = inventoryTransactionSequenceType == null? null: sequenceControl.getSequenceTypeTransfer(userVisit, inventoryTransactionSequenceType);
            var inventoryTransactionWorkflow = inventoryTransactionTypeDetail.getInventoryTransactionWorkflow();
            var inventoryTransactionWorkflowTransfer = inventoryTransactionWorkflow == null? null: workflowControl.getWorkflowTransfer(userVisit, inventoryTransactionWorkflow);
            var inventoryTransactionWorkflowEntrance = inventoryTransactionTypeDetail.getInventoryTransactionWorkflowEntrance();
            var inventoryTransactionWorkflowEntranceTransfer = inventoryTransactionWorkflowEntrance == null? null: workflowControl.getWorkflowEntranceTransfer(userVisit, inventoryTransactionWorkflowEntrance);
            var isDefault = inventoryTransactionTypeDetail.getIsDefault();
            var sortOrder = inventoryTransactionTypeDetail.getSortOrder();
            var description = inventoryTransactionTypeControl.getBestInventoryTransactionTypeDescription(inventoryTransactionType, getLanguage(userVisit));
            
            inventoryTransactionTypeTransfer = new InventoryTransactionTypeTransfer(inventoryTransactionTypeName, inventoryTransactionSequenceTypeTransfer, inventoryTransactionWorkflowTransfer,
                    inventoryTransactionWorkflowEntranceTransfer, isDefault, sortOrder, description);
            put(userVisit, inventoryTransactionType, inventoryTransactionTypeTransfer);
        }
        
        return inventoryTransactionTypeTransfer;
    }
    
}
