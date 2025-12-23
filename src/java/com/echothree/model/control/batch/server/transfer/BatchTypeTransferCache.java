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

package com.echothree.model.control.batch.server.transfer;

import com.echothree.model.control.batch.common.transfer.BatchTypeTransfer;
import com.echothree.model.control.batch.server.control.BatchControl;
import com.echothree.model.control.sequence.server.control.SequenceControl;
import com.echothree.model.control.workflow.server.control.WorkflowControl;
import com.echothree.model.data.batch.server.entity.BatchType;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class BatchTypeTransferCache
        extends BaseBatchTransferCache<BatchType, BatchTypeTransfer> {

    BatchControl batchControl = Session.getModelController(BatchControl.class);
    SequenceControl sequenceControl = Session.getModelController(SequenceControl.class);
    WorkflowControl workflowControl = Session.getModelController(WorkflowControl.class);
    
    /** Creates a new instance of BatchTypeTransferCache */
    protected BatchTypeTransferCache() {
        super();
        
        setIncludeEntityInstance(true);
    }
    
    @Override
    public BatchTypeTransfer getTransfer(UserVisit userVisit, BatchType batchType) {
        var batchTypeTransfer = get(batchType);
        
        if(batchTypeTransfer == null) {
            var batchTypeDetail = batchType.getLastDetail();
            var batchTypeName = batchTypeDetail.getBatchTypeName();
            var parentBatchType = batchTypeDetail.getParentBatchType();
            var parentBatchTypeTransfer = parentBatchType == null ? null : getTransfer(userVisit, parentBatchType);
            var batchSequenceType = batchTypeDetail.getBatchSequenceType();
            var batchSequenceTypeTransfer = batchSequenceType == null? null: sequenceControl.getSequenceTypeTransfer(userVisit, batchSequenceType);
            var batchWorkflow = batchTypeDetail.getBatchWorkflow();
            var batchWorkflowTransfer = batchWorkflow == null? null: workflowControl.getWorkflowTransfer(userVisit, batchWorkflow);
            var batchWorkflowEntrance = batchTypeDetail.getBatchWorkflowEntrance();
            var batchWorkflowEntranceTransfer = batchWorkflowEntrance == null? null: workflowControl.getWorkflowEntranceTransfer(userVisit, batchWorkflowEntrance);
            var isDefault = batchTypeDetail.getIsDefault();
            var sortOrder = batchTypeDetail.getSortOrder();
            var description = batchControl.getBestBatchTypeDescription(batchType, getLanguage(userVisit));
            
            batchTypeTransfer = new BatchTypeTransfer(batchTypeName, parentBatchTypeTransfer, batchSequenceTypeTransfer, batchWorkflowTransfer,
                    batchWorkflowEntranceTransfer, isDefault, sortOrder, description);
            put(userVisit, batchType, batchTypeTransfer);
        }
        
        return batchTypeTransfer;
    }
    
}
