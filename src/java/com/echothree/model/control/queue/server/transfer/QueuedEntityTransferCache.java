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

package com.echothree.model.control.queue.server.transfer;

import com.echothree.model.control.core.remote.transfer.EntityInstanceTransfer;
import com.echothree.model.control.core.server.CoreControl;
import com.echothree.model.control.queue.remote.transfer.QueueTypeTransfer;
import com.echothree.model.control.queue.remote.transfer.QueuedEntityTransfer;
import com.echothree.model.control.queue.server.QueueControl;
import com.echothree.model.data.queue.server.entity.QueuedEntity;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;

public class QueuedEntityTransferCache
        extends BaseQueueTransferCache<QueuedEntity, QueuedEntityTransfer> {

    CoreControl coreControl = (CoreControl)Session.getModelController(CoreControl.class);
        
    /** Creates a new instance of QueuedEntityTransferCache */
    public QueuedEntityTransferCache(UserVisit userVisit, QueueControl queueControl) {
        super(userVisit, queueControl);
    }

    public QueuedEntityTransfer getQueuedEntityTransfer(QueuedEntity queuedEntity) {
        QueuedEntityTransfer queuedEntityTransfer = get(queuedEntity);

        if(queuedEntityTransfer == null) {
            QueueTypeTransfer queueType = queueControl.getQueueTypeTransfer(userVisit, queuedEntity.getQueueType());
            EntityInstanceTransfer entityInstance = coreControl.getEntityInstanceTransfer(userVisit, queuedEntity.getEntityInstance(), true, true, true, true, true);

            queuedEntityTransfer = new QueuedEntityTransfer(queueType, entityInstance);
            put(queuedEntity, queuedEntityTransfer);
        }

        return queuedEntityTransfer;
    }

}
