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

package com.echothree.model.control.batch.server.transfer;

import com.echothree.util.server.transfer.BaseTransferCaches;
import javax.enterprise.inject.spi.CDI;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class BatchTransferCaches
        extends BaseTransferCaches {
    
    protected BatchTypeTransferCache batchTypeTransferCache;
    protected BatchTypeDescriptionTransferCache batchTypeDescriptionTransferCache;
    protected BatchTypeEntityTypeTransferCache batchTypeEntityTypeTransferCache;
    protected BatchAliasTypeTransferCache batchAliasTypeTransferCache;
    protected BatchAliasTypeDescriptionTransferCache batchAliasTypeDescriptionTransferCache;
    protected BatchTransferCache batchTransferCache;
    protected BatchAliasTransferCache batchAliasTransferCache;
    protected BatchEntityTransferCache batchEntityTransferCache;
    
    /** Creates a new instance of BatchTransferCaches */
    protected BatchTransferCaches() {
        super();
    }
    
    public BatchTypeTransferCache getBatchTypeTransferCache() {
        if(batchTypeTransferCache == null)
            batchTypeTransferCache = CDI.current().select(BatchTypeTransferCache.class).get();
        
        return batchTypeTransferCache;
    }
    
    public BatchTypeDescriptionTransferCache getBatchTypeDescriptionTransferCache() {
        if(batchTypeDescriptionTransferCache == null)
            batchTypeDescriptionTransferCache = CDI.current().select(BatchTypeDescriptionTransferCache.class).get();

        return batchTypeDescriptionTransferCache;
    }

    public BatchTypeEntityTypeTransferCache getBatchTypeEntityTypeTransferCache() {
        if(batchTypeEntityTypeTransferCache == null)
            batchTypeEntityTypeTransferCache = CDI.current().select(BatchTypeEntityTypeTransferCache.class).get();

        return batchTypeEntityTypeTransferCache;
    }

    public BatchAliasTypeTransferCache getBatchAliasTypeTransferCache() {
        if(batchAliasTypeTransferCache == null)
            batchAliasTypeTransferCache = CDI.current().select(BatchAliasTypeTransferCache.class).get();
        
        return batchAliasTypeTransferCache;
    }
    
    public BatchAliasTypeDescriptionTransferCache getBatchAliasTypeDescriptionTransferCache() {
        if(batchAliasTypeDescriptionTransferCache == null)
            batchAliasTypeDescriptionTransferCache = CDI.current().select(BatchAliasTypeDescriptionTransferCache.class).get();
        
        return batchAliasTypeDescriptionTransferCache;
    }
    
    public BatchTransferCache getBatchTransferCache() {
        if(batchTransferCache == null)
            batchTransferCache = CDI.current().select(BatchTransferCache.class).get();
        
        return batchTransferCache;
    }
    
    public BatchAliasTransferCache getBatchAliasTransferCache() {
        if(batchAliasTransferCache == null)
            batchAliasTransferCache = CDI.current().select(BatchAliasTransferCache.class).get();

        return batchAliasTransferCache;
    }

    public BatchEntityTransferCache getBatchEntityTransferCache() {
        if(batchEntityTransferCache == null)
            batchEntityTransferCache = CDI.current().select(BatchEntityTransferCache.class).get();

        return batchEntityTransferCache;
    }

}
