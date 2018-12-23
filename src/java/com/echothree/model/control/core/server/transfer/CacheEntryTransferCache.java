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

import com.echothree.model.control.core.common.CoreOptions;
import com.echothree.model.control.core.common.CoreProperties;
import com.echothree.model.control.core.common.EntityAttributeTypes;
import com.echothree.model.control.core.common.transfer.CacheEntryTransfer;
import com.echothree.model.control.core.common.transfer.MimeTypeTransfer;
import com.echothree.model.control.core.server.CoreControl;
import com.echothree.model.data.core.server.entity.CacheBlobEntry;
import com.echothree.model.data.core.server.entity.CacheClobEntry;
import com.echothree.model.data.core.server.entity.CacheEntry;
import com.echothree.model.data.core.server.entity.MimeType;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.common.form.TransferProperties;
import com.echothree.util.common.persistence.type.ByteArray;
import com.echothree.util.common.transfer.ListWrapper;
import java.util.Set;

public class CacheEntryTransferCache
        extends BaseCoreTransferCache<CacheEntry, CacheEntryTransfer> {
    
    boolean includeBlob;
    boolean includeClob;
    boolean includeCacheEntryDependencies;
    
    TransferProperties transferProperties;
    boolean filterCacheEntryKey;
    boolean filterMimeType;
    boolean filterCreatedTime;
    boolean filterUnformattedCreatedTime;
    boolean filterValidUntilTime;
    boolean filterUnformattedValidUntilTime;

    /** Creates a new instance of CacheEntryTransferCache */
    public CacheEntryTransferCache(UserVisit userVisit, CoreControl coreControl) {
        super(userVisit, coreControl);

        Set<String> options = session.getOptions();
        if(options != null) {
            includeBlob = options.contains(CoreOptions.CacheEntryIncludeBlob);
            includeClob = options.contains(CoreOptions.CacheEntryIncludeClob);
            includeCacheEntryDependencies = options.contains(CoreOptions.CacheEntryIncludeCacheEntryDependencies);
        }
        
        transferProperties = session.getTransferProperties();
        if(transferProperties != null) {
            Set<String> properties = transferProperties.getProperties(CacheEntryTransfer.class);
            
            if(properties != null) {
                filterCacheEntryKey = !properties.contains(CoreProperties.CACHE_ENTRY_KEY);
                filterMimeType = !properties.contains(CoreProperties.MIME_TYPE);
                filterCreatedTime = !properties.contains(CoreProperties.CREATED_TIME);
                filterUnformattedCreatedTime = !properties.contains(CoreProperties.UNFORMATTED_CREATED_TIME);
                filterValidUntilTime = !properties.contains(CoreProperties.VALID_UNTIL_TIME);
                filterUnformattedValidUntilTime = !properties.contains(CoreProperties.UNFORMATTED_VALID_UNTIL_TIME);
            }
        }
    }
    
    public CacheEntryTransfer getCacheEntryTransfer(CacheEntry cacheEntry) {
        CacheEntryTransfer cacheEntryTransfer = get(cacheEntry);
        
        if(cacheEntryTransfer == null) {
            String cacheEntryKey = filterCacheEntryKey ? null : cacheEntry.getCacheEntryKey();
            MimeType mimeType = cacheEntry.getMimeType();
            MimeTypeTransfer mimeTypeTransfer = filterMimeType ? null : coreControl.getMimeTypeTransfer(userVisit, mimeType);
            Long unformattedCreatedTime = cacheEntry.getCreatedTime();
            String createdTime = filterCreatedTime ? null : formatTypicalDateTime(unformattedCreatedTime);
            Long unformattedValidUntilTime = cacheEntry.getValidUntilTime();
            String validUntilTime = filterValidUntilTime ? null : formatTypicalDateTime(unformattedValidUntilTime);
            String clob = null;
            ByteArray blob = null;

            String entityAttributeTypeName = mimeType.getLastDetail().getEntityAttributeType().getEntityAttributeTypeName();
            if(entityAttributeTypeName.equals(EntityAttributeTypes.CLOB.name())) {
                if(includeClob) {
                    CacheClobEntry cacheClobEntry = coreControl.getCacheClobEntryByCacheEntry(cacheEntry);

                    if(cacheClobEntry != null) {
                        clob = cacheClobEntry.getClob();
                    }
                }
            } else if(entityAttributeTypeName.equals(EntityAttributeTypes.BLOB.name())) {
                if(includeBlob) {
                    CacheBlobEntry cacheBlobEntry = coreControl.getCacheBlobEntryByCacheEntry(cacheEntry);

                    if(cacheBlobEntry != null) {
                        blob = cacheBlobEntry.getBlob();
                    }
                }
            }

            cacheEntryTransfer = new CacheEntryTransfer(cacheEntryKey, mimeTypeTransfer, createdTime,
                    filterUnformattedCreatedTime ? null : unformattedCreatedTime, validUntilTime,
                    filterUnformattedValidUntilTime ? null : unformattedValidUntilTime, clob, blob);
            put(cacheEntry, cacheEntryTransfer);
            
            if(includeCacheEntryDependencies) {
                cacheEntryTransfer.setCacheEntryDependencies(new ListWrapper<>(coreControl.getCacheEntryDependencyTransfersByCacheEntry(userVisit, cacheEntry)));
            }
        }
        
        return cacheEntryTransfer;
    }
    
}
