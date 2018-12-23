// --------------------------------------------------------------------------------
// Copyright 2002-2019 Echo Three, LLC
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
import com.echothree.model.control.core.server.CoreControl;
import com.echothree.model.data.core.server.entity.EntityTime;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.common.form.TransferProperties;
import java.util.Date;
import java.util.Set;

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
    public EntityTimeTransferCache(UserVisit userVisit, CoreControl coreControl) {
        super(userVisit, coreControl);
        
        transferProperties = session.getTransferProperties();
        if(transferProperties != null) {
            Set<String> properties = transferProperties.getProperties(EntityTimeTransfer.class);
            
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
    
    public EntityTimeTransfer getEntityTimeTransfer(EntityTime entityTime) {
        EntityTimeTransfer entityTimeTransfer = get(entityTime);
        
        if(entityTimeTransfer == null) {
            Long unformattedCreatedTime = filterUnformattedCreatedTime ? null : entityTime.getCreatedTime();
            Date time = !filterUnformattedCreatedTime || !filterUnformattedModifiedTime || !filterUnformattedDeletedTime ? new Date() : null;
            String createdTime;
            Long unformattedModifiedTime = filterUnformattedModifiedTime ? null : entityTime.getModifiedTime();
            String modifiedTime;
            Long unformattedDeletedTime = filterUnformattedDeletedTime ? null : entityTime.getDeletedTime();
            String deletedTime;
            
            if(unformattedCreatedTime == null) {
                createdTime = null;
            } else {
                time.setTime(unformattedCreatedTime);
                createdTime = formatTypicalDateTime(time);
            }
            
            if(unformattedModifiedTime == null) {
                modifiedTime = null;
            } else {
                time.setTime(unformattedModifiedTime);
                modifiedTime = formatTypicalDateTime(time);
            }
            
            if(unformattedDeletedTime == null) {
                deletedTime = null;
            } else {
                time.setTime(unformattedDeletedTime);
                deletedTime = formatTypicalDateTime(time);
            }
            
            entityTimeTransfer = new EntityTimeTransfer(unformattedCreatedTime, createdTime, unformattedModifiedTime, modifiedTime,
                    unformattedDeletedTime, deletedTime);
            put(entityTime, entityTimeTransfer);
        }
        return entityTimeTransfer;
    }
    
}
