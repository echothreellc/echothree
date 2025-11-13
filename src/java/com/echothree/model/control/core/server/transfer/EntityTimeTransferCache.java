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

package com.echothree.model.control.core.server.transfer;

import com.echothree.model.control.core.common.CoreProperties;
import com.echothree.model.control.core.common.transfer.EntityTimeTransfer;
import com.echothree.model.data.core.server.entity.EntityTime;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.common.form.TransferProperties;

public class EntityTimeTransferCache
        extends BaseCoreTransferCache<EntityTime, EntityTimeTransfer> {
    
    TransferProperties transferProperties;
    boolean filterUnformattedCreatedTime;
    boolean filterCreatedTime;
    boolean filterUnformattedModifiedTime;
    boolean filterModifiedTime;
    boolean filterUnformattedDeletedTime;
    boolean filterDeletedTime;
    
    /** Creates a new instance of EntityTimeTransferCache */
    public EntityTimeTransferCache() {
        super();
        
        transferProperties = session.getTransferProperties();
        if(transferProperties != null) {
            var properties = transferProperties.getProperties(EntityTimeTransfer.class);
            
            if(properties != null) {
                filterUnformattedCreatedTime = !properties.contains(CoreProperties.UNFORMATTED_CREATED_TIME);
                filterCreatedTime = !properties.contains(CoreProperties.CREATED_TIME);
                filterUnformattedModifiedTime = !properties.contains(CoreProperties.UNFORMATTED_MODIFIED_TIME);
                filterModifiedTime = !properties.contains(CoreProperties.MODIFIED_TIME);
                filterUnformattedDeletedTime = !properties.contains(CoreProperties.UNFORMATTED_DELETED_TIME);
                filterDeletedTime = !properties.contains(CoreProperties.DELETED_TIME);
            }
        }
    }
    
    public EntityTimeTransfer getEntityTimeTransfer(UserVisit userVisit, EntityTime entityTime) {
        var entityTimeTransfer = get(entityTime);
        
        if(entityTimeTransfer == null) {
            var unformattedCreatedTime = filterUnformattedCreatedTime ? null : entityTime.getCreatedTime();
            var createdTime = formatTypicalDateTime(userVisit, unformattedCreatedTime);
            var unformattedModifiedTime = filterUnformattedModifiedTime ? null : entityTime.getModifiedTime();
            var modifiedTime = formatTypicalDateTime(userVisit, unformattedModifiedTime);
            var unformattedDeletedTime = filterUnformattedDeletedTime ? null : entityTime.getDeletedTime();
            var deletedTime = formatTypicalDateTime(userVisit, unformattedDeletedTime);
            
            entityTimeTransfer = new EntityTimeTransfer(unformattedCreatedTime, createdTime, unformattedModifiedTime, modifiedTime,
                    unformattedDeletedTime, deletedTime);
            put(userVisit, entityTime, entityTimeTransfer);
        }

        return entityTimeTransfer;
    }
    
}
