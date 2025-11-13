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

package com.echothree.model.control.batch.server.transfer;

import com.echothree.model.control.batch.common.BatchOptions;
import com.echothree.model.control.batch.common.transfer.GenericBatchTransfer;
import com.echothree.model.control.batch.server.control.BatchControl;
import com.echothree.model.control.workflow.common.transfer.WorkflowEntityStatusTransfer;
import com.echothree.model.control.workflow.server.control.WorkflowControl;
import com.echothree.model.data.batch.server.entity.Batch;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.common.transfer.ListWrapper;
import com.echothree.util.server.persistence.Session;

public abstract class GenericBatchTransferCache<V extends GenericBatchTransfer>
        extends BaseBatchTransferCache<Batch, V> {
    
    WorkflowControl workflowControl = Session.getModelController(WorkflowControl.class);
    boolean includeAliases;
    boolean includeEntities;
    
    /** Creates a new instance of GenericBatchTransferCache */
    public GenericBatchTransferCache(BatchControl batchControl) {
        super(batchControl);
        
        var options = session.getOptions();
        if(options != null) {
            includeAliases = options.contains(BatchOptions.BatchIncludeAliases);
            includeEntities = options.contains(BatchOptions.BatchIncludeEntities);
        }
                
        setIncludeEntityInstance(true);
    }
    
    protected WorkflowEntityStatusTransfer getBatchStatus(UserVisit userVisit, Batch batch, EntityInstance entityInstance) {
        var batchWorkflow = batch.getLastDetail().getBatchType().getLastDetail().getBatchWorkflow();
        
        return batchWorkflow == null ? null : workflowControl.getWorkflowEntityStatusTransferByEntityInstance(userVisit, batchWorkflow, entityInstance);
    }
    
    protected void handleOptions(UserVisit userVisit, Batch batch, V genericBatchTransfer) {
        if(includeAliases) {
            genericBatchTransfer.setBatchAliases(new ListWrapper<>(batchControl.getBatchAliasTransfersByBatch(userVisit, batch)));
        }

        if(includeEntities) {
            genericBatchTransfer.setBatchEntities(new ListWrapper<>(batchControl.getBatchEntityTransfersByBatch(userVisit, batch)));
        }
    }
    
}
