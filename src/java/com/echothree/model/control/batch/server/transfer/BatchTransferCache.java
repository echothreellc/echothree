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

import com.echothree.model.control.batch.common.transfer.BatchTransfer;
import com.echothree.model.control.batch.server.control.BatchControl;
import com.echothree.model.control.core.server.control.EntityInstanceControl;
import com.echothree.model.data.batch.server.entity.Batch;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;

public class BatchTransferCache
        extends GenericBatchTransferCache<BatchTransfer> {

    EntityInstanceControl entityInstanceControl = Session.getModelController(EntityInstanceControl.class);
    
    /** Creates a new instance of BatchTransferCache */
    public BatchTransferCache(UserVisit userVisit, BatchControl batchControl) {
        super(userVisit, batchControl);
        
        setIncludeEntityInstance(true);
    }
    
    @Override
    public BatchTransfer getTransfer(Batch batch) {
        var batchTransfer = get(batch);
        
        if(batchTransfer == null) {
            var batchDetail = batch.getLastDetail();
            var batchType = batchDetail.getBatchType();
            var batchTypeTransfer = batchControl.getBatchTypeTransfer(userVisit, batchType);
            var batchName = batchDetail.getBatchName();
            var entityInstance = entityInstanceControl.getEntityInstanceByBasePK(batch.getPrimaryKey());
            
            batchTransfer = new BatchTransfer(batchTypeTransfer, batchName, getBatchStatus(batch, entityInstance));
            put(userVisit, batch, batchTransfer, entityInstance);

            handleOptions(batch, batchTransfer);
        }
        
        return batchTransfer;
    }
    
  }
