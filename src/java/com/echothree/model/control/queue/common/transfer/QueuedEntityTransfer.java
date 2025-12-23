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

package com.echothree.model.control.queue.common.transfer;

import com.echothree.model.control.core.common.transfer.EntityInstanceTransfer;
import com.echothree.util.common.transfer.BaseTransfer;

public class QueuedEntityTransfer
        extends BaseTransfer {
    
    private QueueTypeTransfer queueType;
    private EntityInstanceTransfer entityInstance;
    
    /** Creates a new instance of QueueTypeTransfer */
    public QueuedEntityTransfer(QueueTypeTransfer queueType, EntityInstanceTransfer entityInstance) {
        this.queueType = queueType;
        this.entityInstance = entityInstance;
    }

    /**
     * Returns the queueType.
     * @return the queueType
     */
    public QueueTypeTransfer getQueueType() {
        return queueType;
    }

    /**
     * Sets the queueType.
     * @param queueType the queueType to set
     */
    public void setQueueType(QueueTypeTransfer queueType) {
        this.queueType = queueType;
    }

    /**
     * Returns the entityInstance.
     * @return the entityInstance
     */
    @Override
    public EntityInstanceTransfer getEntityInstance() {
        return entityInstance;
    }

    /**
     * Sets the entityInstance.
     * @param entityInstance the entityInstance to set
     */
    @Override
    public void setEntityInstance(EntityInstanceTransfer entityInstance) {
        this.entityInstance = entityInstance;
    }

}
