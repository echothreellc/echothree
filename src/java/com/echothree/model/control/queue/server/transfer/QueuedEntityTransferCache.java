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

package com.echothree.model.control.queue.server.transfer;

import com.echothree.model.control.core.server.control.EntityInstanceControl;
import com.echothree.model.control.queue.common.transfer.QueuedEntityTransfer;
import com.echothree.model.control.queue.server.control.QueueControl;
import com.echothree.model.data.queue.server.entity.QueuedEntity;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;

public class QueuedEntityTransferCache
        extends BaseQueueTransferCache<QueuedEntity, QueuedEntityTransfer> {

    EntityInstanceControl entityInstanceControl = Session.getModelController(EntityInstanceControl.class);
        
    /** Creates a new instance of QueuedEntityTransferCache */
    public QueuedEntityTransferCache(QueueControl queueControl) {
        super(queueControl);
    }

    public QueuedEntityTransfer getQueuedEntityTransfer(QueuedEntity queuedEntity) {
        var queuedEntityTransfer = get(queuedEntity);

        if(queuedEntityTransfer == null) {
            var queueType = queueControl.getQueueTypeTransfer(userVisit, queuedEntity.getQueueType());
            var entityInstance = entityInstanceControl.getEntityInstanceTransfer(userVisit, queuedEntity.getEntityInstance(), true, true, true, true);

            queuedEntityTransfer = new QueuedEntityTransfer(queueType, entityInstance);
            put(userVisit, queuedEntity, queuedEntityTransfer);
        }

        return queuedEntityTransfer;
    }

}
