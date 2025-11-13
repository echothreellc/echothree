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

import com.echothree.model.control.batch.common.transfer.BatchTypeEntityTypeTransfer;
import com.echothree.model.control.batch.server.control.BatchControl;
import com.echothree.model.control.core.server.control.EntityTypeControl;
import com.echothree.model.data.batch.server.entity.BatchTypeEntityType;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;

public class BatchTypeEntityTypeTransferCache
        extends BaseBatchTransferCache<BatchTypeEntityType, BatchTypeEntityTypeTransfer> {
    
    EntityTypeControl entityTypeControl = Session.getModelController(EntityTypeControl.class);

    /** Creates a new instance of BatchTypeEntityTypeTransferCache */
    public BatchTypeEntityTypeTransferCache(BatchControl batchControl) {
        super(batchControl);
    }
    
    @Override
    public BatchTypeEntityTypeTransfer getTransfer(UserVisit userVisit, BatchTypeEntityType batchTypeEntityType) {
        var batchTypeEntityTypeTransfer = get(batchTypeEntityType);
        
        if(batchTypeEntityTypeTransfer == null) {
            var batchType = batchControl.getBatchTypeTransfer(userVisit, batchTypeEntityType.getBatchType());
            var entityType = entityTypeControl.getEntityTypeTransfer(userVisit, batchTypeEntityType.getEntityType());
            
            batchTypeEntityTypeTransfer = new BatchTypeEntityTypeTransfer(batchType, entityType);
            put(userVisit, batchTypeEntityType, batchTypeEntityTypeTransfer);
        }
        
        return batchTypeEntityTypeTransfer;
    }
    
}
