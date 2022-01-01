// --------------------------------------------------------------------------------
// Copyright 2002-2022 Echo Three, LLC
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
import com.echothree.model.control.sequence.common.transfer.SequenceTypeTransfer;
import com.echothree.model.control.sequence.server.control.SequenceControl;
import com.echothree.model.control.workflow.common.transfer.WorkflowEntranceTransfer;
import com.echothree.model.control.workflow.common.transfer.WorkflowTransfer;
import com.echothree.model.control.workflow.server.control.WorkflowControl;
import com.echothree.model.data.batch.server.entity.BatchType;
import com.echothree.model.data.batch.server.entity.BatchTypeDetail;
import com.echothree.model.data.sequence.server.entity.SequenceType;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.model.data.workflow.server.entity.Workflow;
import com.echothree.model.data.workflow.server.entity.WorkflowEntrance;
import com.echothree.util.server.persistence.Session;

public class BatchTypeTransferCache
        extends BaseBatchTransferCache<BatchType, BatchTypeTransfer> {
    
    SequenceControl sequenceControl = Session.getModelController(SequenceControl.class);
    WorkflowControl workflowControl = Session.getModelController(WorkflowControl.class);
    
    /** Creates a new instance of BatchTypeTransferCache */
    public BatchTypeTransferCache(UserVisit userVisit, BatchControl batchControl) {
        super(userVisit, batchControl);
        
        setIncludeEntityInstance(true);
    }
    
    @Override
    public BatchTypeTransfer getTransfer(BatchType batchType) {
        BatchTypeTransfer batchTypeTransfer = get(batchType);
        
        if(batchTypeTransfer == null) {
            BatchTypeDetail batchTypeDetail = batchType.getLastDetail();
            String batchTypeName = batchTypeDetail.getBatchTypeName();
            BatchType parentBatchType = batchTypeDetail.getParentBatchType();
            BatchTypeTransfer parentBatchTypeTransfer = parentBatchType == null? null: getTransfer(parentBatchType);
            SequenceType batchSequenceType = batchTypeDetail.getBatchSequenceType();
            SequenceTypeTransfer batchSequenceTypeTransfer = batchSequenceType == null? null: sequenceControl.getSequenceTypeTransfer(userVisit, batchSequenceType);
            Workflow batchWorkflow = batchTypeDetail.getBatchWorkflow();
            WorkflowTransfer batchWorkflowTransfer = batchWorkflow == null? null: workflowControl.getWorkflowTransfer(userVisit, batchWorkflow);
            WorkflowEntrance batchWorkflowEntrance = batchTypeDetail.getBatchWorkflowEntrance();
            WorkflowEntranceTransfer batchWorkflowEntranceTransfer = batchWorkflowEntrance == null? null: workflowControl.getWorkflowEntranceTransfer(userVisit, batchWorkflowEntrance);
            Boolean isDefault = batchTypeDetail.getIsDefault();
            Integer sortOrder = batchTypeDetail.getSortOrder();
            String description = batchControl.getBestBatchTypeDescription(batchType, getLanguage());
            
            batchTypeTransfer = new BatchTypeTransfer(batchTypeName, parentBatchTypeTransfer, batchSequenceTypeTransfer, batchWorkflowTransfer,
                    batchWorkflowEntranceTransfer, isDefault, sortOrder, description);
            put(batchType, batchTypeTransfer);
        }
        
        return batchTypeTransfer;
    }
    
}
