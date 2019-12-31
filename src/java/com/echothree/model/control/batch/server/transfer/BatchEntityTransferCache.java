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

import com.echothree.model.control.batch.common.transfer.BatchEntityTransfer;
import com.echothree.model.control.batch.server.BatchControl;
import com.echothree.model.control.core.common.transfer.EntityInstanceTransfer;
import com.echothree.model.control.core.server.CoreControl;
import com.echothree.model.data.batch.server.entity.BatchEntity;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;

public class BatchEntityTransferCache
        extends BaseBatchTransferCache<BatchEntity, BatchEntityTransfer> {

    CoreControl coreControl = (CoreControl)Session.getModelController(CoreControl.class);

    /** Creates a new instance of BatchEntityTransferCache */
    public BatchEntityTransferCache(UserVisit userVisit, BatchControl batchControl) {
        super(userVisit, batchControl);
    }

    @Override
    public BatchEntityTransfer getTransfer(BatchEntity batchEntity) {
        BatchEntityTransfer batchEntityTransfer = get(batchEntity);

        if(batchEntityTransfer == null) {
            EntityInstanceTransfer entityInstanceTransfer = coreControl.getEntityInstanceTransfer(userVisit, batchEntity.getEntityInstance(), false, false, false, false, false);

            batchEntityTransfer = new BatchEntityTransfer(entityInstanceTransfer);
            put(batchEntity, batchEntityTransfer);
        }

        return batchEntityTransfer;
    }
}