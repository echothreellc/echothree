// --------------------------------------------------------------------------------
// Copyright 2002-2020 Echo Three, LLC
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
import com.echothree.model.control.batch.common.transfer.BatchTypeTransfer;
import com.echothree.model.control.batch.server.control.BatchControl;
import com.echothree.model.control.core.server.control.CoreControl;
import com.echothree.model.data.batch.server.entity.Batch;
import com.echothree.model.data.batch.server.entity.BatchDetail;
import com.echothree.model.data.batch.server.entity.BatchType;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;

public class BatchTransferCache
        extends GenericBatchTransferCache<BatchTransfer> {
    
    CoreControl coreControl = Session.getModelController(CoreControl.class);
    
    /** Creates a new instance of BatchTransferCache */
    public BatchTransferCache(UserVisit userVisit, BatchControl batchControl) {
        super(userVisit, batchControl);
        
        setIncludeEntityInstance(true);
    }
    
    @Override
    public BatchTransfer getTransfer(Batch batch) {
        BatchTransfer batchTransfer = get(batch);
        
        if(batchTransfer == null) {
            BatchDetail batchDetail = batch.getLastDetail();
            BatchType batchType = batchDetail.getBatchType();
            BatchTypeTransfer batchTypeTransfer = batchControl.getBatchTypeTransfer(userVisit, batchType);
            String batchName = batchDetail.getBatchName();
            EntityInstance entityInstance = coreControl.getEntityInstanceByBasePK(batch.getPrimaryKey());
            
            batchTransfer = new BatchTransfer(batchTypeTransfer, batchName, getBatchStatus(batch, entityInstance));
            put(batch, batchTransfer, entityInstance);

            handleOptions(batch, batchTransfer);
        }
        
        return batchTransfer;
    }
    
  }
