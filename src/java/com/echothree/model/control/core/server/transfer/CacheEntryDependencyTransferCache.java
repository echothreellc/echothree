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
import com.echothree.model.control.core.common.transfer.CacheEntryDependencyTransfer;
import com.echothree.model.control.core.server.control.CacheEntryControl;
import com.echothree.model.control.core.server.control.EntityInstanceControl;
import com.echothree.model.data.core.server.entity.CacheEntryDependency;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.common.form.TransferProperties;
import com.echothree.util.server.persistence.Session;

public class CacheEntryDependencyTransferCache
        extends BaseCoreTransferCache<CacheEntryDependency, CacheEntryDependencyTransfer> {

    CacheEntryControl cacheEntryControl = Session.getModelController(CacheEntryControl.class);
    EntityInstanceControl entityInstanceControl = Session.getModelController(EntityInstanceControl.class);

    TransferProperties transferProperties;
    boolean filterCacheEntry;
    boolean filterEntityInstance;
    
    /** Creates a new instance of CacheEntryTransferCache */
    public CacheEntryDependencyTransferCache(UserVisit userVisit) {
        super(userVisit);
        
        transferProperties = session.getTransferProperties();
        if(transferProperties != null) {
            var properties = transferProperties.getProperties(CacheEntryDependencyTransfer.class);
            
            if(properties != null) {
                filterCacheEntry = !properties.contains(CoreProperties.CACHE_ENTRY);
                filterEntityInstance = !properties.contains(CoreProperties.ENTITY_INSTANCE);
            }
        }
    }
    
    public CacheEntryDependencyTransfer getCacheEntryDependencyTransfer(CacheEntryDependency cacheEntryDependency) {
        var cacheEntryDependencyTransfer = get(cacheEntryDependency);
        
        if(cacheEntryDependencyTransfer == null) {
            var cacheEntry = filterCacheEntry ? null : cacheEntryControl.getCacheEntryTransfer(userVisit, cacheEntryDependency.getCacheEntry());
            var entityInstance = filterEntityInstance ? null : entityInstanceControl.getEntityInstanceTransfer(userVisit, cacheEntryDependency.getEntityInstance(), false, false, false, false);

            cacheEntryDependencyTransfer = new CacheEntryDependencyTransfer(cacheEntry, entityInstance);
            put(cacheEntryDependency, cacheEntryDependencyTransfer);
        }
        
        return cacheEntryDependencyTransfer;
    }
    
}
