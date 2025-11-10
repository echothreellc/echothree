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

import com.echothree.model.control.queue.common.QueueOptions;
import com.echothree.model.control.queue.common.transfer.QueueTypeTransfer;
import com.echothree.model.control.queue.server.control.QueueControl;
import com.echothree.model.data.queue.server.entity.QueueType;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.common.transfer.ListWrapper;

public class QueueTypeTransferCache
        extends BaseQueueTransferCache<QueueType, QueueTypeTransfer> {

    boolean includeQueuedEntityCount;
    boolean includeOldestQueuedEntityTime;
    boolean includeLatestQueuedEntityTime;
    boolean includeQueuedEntities;
    
    /** Creates a new instance of QueueTypeTransferCache */
    public QueueTypeTransferCache(UserVisit userVisit, QueueControl queueControl) {
        super(userVisit, queueControl);
        
        var options = session.getOptions();
        if(options != null) {
            includeQueuedEntityCount = options.contains(QueueOptions.QueueTypeIncludeQueuedEntitiesCount);
            includeOldestQueuedEntityTime = options.contains(QueueOptions.QueueTypeIncludeOldestQueuedEntityTime);
            includeLatestQueuedEntityTime = options.contains(QueueOptions.QueueTypeIncludeLatestQueuedEntityTime);
            includeQueuedEntities = options.contains(QueueOptions.QueueTypeIncludeQueuedEntities);
            setIncludeUuid(options.contains(QueueOptions.QueueTypeIncludeUuid));
        }
        
        setIncludeEntityInstance(true);
    }

    public QueueTypeTransfer getQueueTypeTransfer(QueueType queueType) {
        var queueTypeTransfer = get(queueType);

        if(queueTypeTransfer == null) {
            var queueTypeDetail = queueType.getLastDetail();
            var queueTypeName = queueTypeDetail.getQueueTypeName();
            var isDefault = queueTypeDetail.getIsDefault();
            var sortOrder = queueTypeDetail.getSortOrder();
            var description = queueControl.getBestQueueTypeDescription(queueType, getLanguage(userVisit));

            queueTypeTransfer = new QueueTypeTransfer(queueTypeName, isDefault, sortOrder, description);
            put(userVisit, queueType, queueTypeTransfer);
            
            if(includeQueuedEntityCount) {
                queueTypeTransfer.setQueuedEntityCount(queueControl.countQueuedEntitiesByQueueType(queueType));
            }
            
            if(includeOldestQueuedEntityTime) {
                var unformattedOldestQueuedEntityTime = queueControl.oldestQueuedEntityTimeByQueueType(queueType);
                
                if(unformattedOldestQueuedEntityTime != null) {
                    queueTypeTransfer.setUnformattedOldestQueuedEntityTime(unformattedOldestQueuedEntityTime);
                    queueTypeTransfer.setOldestQueuedEntityTime(formatTypicalDateTime(userVisit, unformattedOldestQueuedEntityTime));
                }
            }

            if(includeLatestQueuedEntityTime) {
                var unformattedLatestQueuedEntityTime = queueControl.latestQueuedEntityTimeByQueueType(queueType);
                
                if(unformattedLatestQueuedEntityTime != null) {
                    queueTypeTransfer.setUnformattedLatestQueuedEntityTime(unformattedLatestQueuedEntityTime);
                    queueTypeTransfer.setLatestQueuedEntityTime(formatTypicalDateTime(userVisit, unformattedLatestQueuedEntityTime));
                }
            }
            
            if(includeQueuedEntities) {
                queueTypeTransfer.setQueuedEntities(new ListWrapper<>(queueControl.getQueuedEntityTransfers(userVisit, queueType)));
            }
        }

        return queueTypeTransfer;
    }

}
