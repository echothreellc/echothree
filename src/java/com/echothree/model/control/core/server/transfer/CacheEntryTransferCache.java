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

package com.echothree.model.control.core.server.transfer;

import com.echothree.model.control.core.common.CoreOptions;
import com.echothree.model.control.core.common.CoreProperties;
import com.echothree.model.control.core.common.EntityAttributeTypes;
import com.echothree.model.control.core.common.transfer.CacheEntryTransfer;
import com.echothree.model.control.core.server.control.CacheEntryControl;
import com.echothree.model.control.core.server.control.MimeTypeControl;
import com.echothree.model.data.core.server.entity.CacheEntry;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.common.form.TransferProperties;
import com.echothree.util.common.persistence.type.ByteArray;
import com.echothree.util.common.transfer.ListWrapper;
import com.echothree.util.server.persistence.Session;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class CacheEntryTransferCache
        extends BaseCoreTransferCache<CacheEntry, CacheEntryTransfer> {

    CacheEntryControl cacheEntryControl = Session.getModelController(CacheEntryControl.class);
    MimeTypeControl mimeTypeControl = Session.getModelController(MimeTypeControl.class);

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
    protected CacheEntryTransferCache() {
        super();

        var options = session.getOptions();
        if(options != null) {
            includeBlob = options.contains(CoreOptions.CacheEntryIncludeBlob);
            includeClob = options.contains(CoreOptions.CacheEntryIncludeClob);
            includeCacheEntryDependencies = options.contains(CoreOptions.CacheEntryIncludeCacheEntryDependencies);
        }
        
        transferProperties = session.getTransferProperties();
        if(transferProperties != null) {
            var properties = transferProperties.getProperties(CacheEntryTransfer.class);
            
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
    
    public CacheEntryTransfer getCacheEntryTransfer(UserVisit userVisit, CacheEntry cacheEntry) {
        var cacheEntryTransfer = get(cacheEntry);
        
        if(cacheEntryTransfer == null) {
            var cacheEntryKey = filterCacheEntryKey ? null : cacheEntry.getCacheEntryKey();
            var mimeType = cacheEntry.getMimeType();
            var mimeTypeTransfer = filterMimeType ? null : mimeTypeControl.getMimeTypeTransfer(userVisit, mimeType);
            var unformattedCreatedTime = cacheEntry.getCreatedTime();
            var createdTime = filterCreatedTime ? null : formatTypicalDateTime(userVisit, unformattedCreatedTime);
            var unformattedValidUntilTime = cacheEntry.getValidUntilTime();
            var validUntilTime = filterValidUntilTime ? null : formatTypicalDateTime(userVisit, unformattedValidUntilTime);
            String clob = null;
            ByteArray blob = null;

            var entityAttributeTypeName = mimeType.getLastDetail().getEntityAttributeType().getEntityAttributeTypeName();
            if(entityAttributeTypeName.equals(EntityAttributeTypes.CLOB.name())) {
                if(includeClob) {
                    var cacheClobEntry = cacheEntryControl.getCacheClobEntryByCacheEntry(cacheEntry);

                    if(cacheClobEntry != null) {
                        clob = cacheClobEntry.getClob();
                    }
                }
            } else if(entityAttributeTypeName.equals(EntityAttributeTypes.BLOB.name())) {
                if(includeBlob) {
                    var cacheBlobEntry = cacheEntryControl.getCacheBlobEntryByCacheEntry(cacheEntry);

                    if(cacheBlobEntry != null) {
                        blob = cacheBlobEntry.getBlob();
                    }
                }
            }

            cacheEntryTransfer = new CacheEntryTransfer(cacheEntryKey, mimeTypeTransfer, createdTime,
                    filterUnformattedCreatedTime ? null : unformattedCreatedTime, validUntilTime,
                    filterUnformattedValidUntilTime ? null : unformattedValidUntilTime, clob, blob);
            put(userVisit, cacheEntry, cacheEntryTransfer);
            
            if(includeCacheEntryDependencies) {
                cacheEntryTransfer.setCacheEntryDependencies(new ListWrapper<>(cacheEntryControl.getCacheEntryDependencyTransfersByCacheEntry(userVisit, cacheEntry)));
            }
        }
        
        return cacheEntryTransfer;
    }
    
}
