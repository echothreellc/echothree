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

import com.echothree.util.server.transfer.BaseTransferCaches;
import javax.enterprise.inject.spi.CDI;

public class QueueTransferCaches
        extends BaseTransferCaches {
    
    protected QueueTypeTransferCache queueTypeTransferCache;
    protected QueueTypeDescriptionTransferCache queueTypeDescriptionTransferCache;
    protected QueuedEntityTransferCache queuedEntityTransferCache;
    
    /** Creates a new instance of QueueTransferCaches */
    public QueueTransferCaches() {
        super();
    }
    
    public QueueTypeTransferCache getQueueTypeTransferCache() {
        if(queueTypeTransferCache == null) {
            queueTypeTransferCache = CDI.current().select(QueueTypeTransferCache.class).get();
        }

        return queueTypeTransferCache;
    }

    public QueueTypeDescriptionTransferCache getQueueTypeDescriptionTransferCache() {
        if(queueTypeDescriptionTransferCache == null) {
            queueTypeDescriptionTransferCache = CDI.current().select(QueueTypeDescriptionTransferCache.class).get();
        }

        return queueTypeDescriptionTransferCache;
    }

    public QueuedEntityTransferCache getQueuedEntityTransferCache() {
        if(queuedEntityTransferCache == null) {
            queuedEntityTransferCache = CDI.current().select(QueuedEntityTransferCache.class).get();
        }

        return queuedEntityTransferCache;
    }

}
