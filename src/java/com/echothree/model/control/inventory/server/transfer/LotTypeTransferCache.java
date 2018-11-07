// --------------------------------------------------------------------------------
// Copyright 2002-2018 Echo Three, LLC
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

import com.echothree.model.control.inventory.common.transfer.LotTypeTransfer;
import com.echothree.model.control.inventory.server.InventoryControl;
import com.echothree.model.control.sequence.common.transfer.SequenceTypeTransfer;
import com.echothree.model.control.sequence.server.SequenceControl;
import com.echothree.model.control.workflow.common.transfer.WorkflowEntranceTransfer;
import com.echothree.model.control.workflow.common.transfer.WorkflowTransfer;
import com.echothree.model.control.workflow.server.WorkflowControl;
import com.echothree.model.data.inventory.server.entity.LotType;
import com.echothree.model.data.inventory.server.entity.LotTypeDetail;
import com.echothree.model.data.sequence.server.entity.SequenceType;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.model.data.workflow.server.entity.Workflow;
import com.echothree.model.data.workflow.server.entity.WorkflowEntrance;
import com.echothree.util.server.persistence.Session;

public class LotTypeTransferCache
        extends BaseInventoryTransferCache<LotType, LotTypeTransfer> {
    
    SequenceControl sequenceControl = (SequenceControl)Session.getModelController(SequenceControl.class);
    WorkflowControl workflowControl = (WorkflowControl)Session.getModelController(WorkflowControl.class);
    
    /** Creates a new instance of LotTypeTransferCache */
    public LotTypeTransferCache(UserVisit userVisit, InventoryControl inventoryControl) {
        super(userVisit, inventoryControl);
        
        setIncludeEntityInstance(true);
    }
    
    public LotTypeTransfer getLotTypeTransfer(LotType lotType) {
        LotTypeTransfer lotTypeTransfer = get(lotType);
        
        if(lotTypeTransfer == null) {
            LotTypeDetail lotTypeDetail = lotType.getLastDetail();
            String lotTypeName = lotTypeDetail.getLotTypeName();
            LotType parentLotType = lotTypeDetail.getParentLotType();
            LotTypeTransfer parentLotTypeTransfer = parentLotType == null? null: getLotTypeTransfer(parentLotType);
            SequenceType lotSequenceType = lotTypeDetail.getLotSequenceType();
            SequenceTypeTransfer lotSequenceTypeTransfer = lotSequenceType == null? null: sequenceControl.getSequenceTypeTransfer(userVisit, lotSequenceType);
            Workflow lotWorkflow = lotTypeDetail.getLotWorkflow();
            WorkflowTransfer lotWorkflowTransfer = lotWorkflow == null? null: workflowControl.getWorkflowTransfer(userVisit, lotWorkflow);
            WorkflowEntrance lotWorkflowEntrance = lotTypeDetail.getLotWorkflowEntrance();
            WorkflowEntranceTransfer lotWorkflowEntranceTransfer = lotWorkflowEntrance == null? null: workflowControl.getWorkflowEntranceTransfer(userVisit, lotWorkflowEntrance);
            Boolean isDefault = lotTypeDetail.getIsDefault();
            Integer sortOrder = lotTypeDetail.getSortOrder();
            String description = inventoryControl.getBestLotTypeDescription(lotType, getLanguage());
            
            lotTypeTransfer = new LotTypeTransfer(lotTypeName, parentLotTypeTransfer, lotSequenceTypeTransfer, lotWorkflowTransfer,
                    lotWorkflowEntranceTransfer, isDefault, sortOrder, description);
            put(lotType, lotTypeTransfer);
        }
        
        return lotTypeTransfer;
    }
    
}
