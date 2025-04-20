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

import com.echothree.model.control.batch.common.transfer.BatchEntityTransfer;
import com.echothree.model.control.batch.server.control.BatchControl;
import com.echothree.model.control.core.server.control.EntityInstanceControl;
import com.echothree.model.data.batch.server.entity.BatchEntity;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;

public class BatchEntityTransferCache
        extends BaseBatchTransferCache<BatchEntity, BatchEntityTransfer> {

    EntityInstanceControl entityInstanceControl = Session.getModelController(EntityInstanceControl.class);

    /** Creates a new instance of BatchEntityTransferCache */
    public BatchEntityTransferCache(UserVisit userVisit, BatchControl batchControl) {
        super(userVisit, batchControl);
    }

    @Override
    public BatchEntityTransfer getTransfer(BatchEntity batchEntity) {
        var batchEntityTransfer = get(batchEntity);

        if(batchEntityTransfer == null) {
            var entityInstanceTransfer = entityInstanceControl.getEntityInstanceTransfer(userVisit, batchEntity.getEntityInstance(), false, false, false, false);

            batchEntityTransfer = new BatchEntityTransfer(entityInstanceTransfer);
            put(batchEntity, batchEntityTransfer);
        }

        return batchEntityTransfer;
    }
}