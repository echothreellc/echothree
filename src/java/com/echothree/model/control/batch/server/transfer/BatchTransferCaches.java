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

import com.echothree.model.control.batch.server.control.BatchControl;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.transfer.BaseTransferCaches;

public class BatchTransferCaches
        extends BaseTransferCaches {
    
    protected BatchControl batchControl;
    
    protected BatchTypeTransferCache batchTypeTransferCache;
    protected BatchTypeDescriptionTransferCache batchTypeDescriptionTransferCache;
    protected BatchTypeEntityTypeTransferCache batchTypeEntityTypeTransferCache;
    protected BatchAliasTypeTransferCache batchAliasTypeTransferCache;
    protected BatchAliasTypeDescriptionTransferCache batchAliasTypeDescriptionTransferCache;
    protected BatchTransferCache batchTransferCache;
    protected BatchAliasTransferCache batchAliasTransferCache;
    protected BatchEntityTransferCache batchEntityTransferCache;
    
    /** Creates a new instance of BatchTransferCaches */
    public BatchTransferCaches(BatchControl batchControl) {
        super();
        
        this.batchControl = batchControl;
    }
    
    public BatchTypeTransferCache getBatchTypeTransferCache() {
        if(batchTypeTransferCache == null)
            batchTypeTransferCache = new BatchTypeTransferCache(batchControl);
        
        return batchTypeTransferCache;
    }
    
    public BatchTypeDescriptionTransferCache getBatchTypeDescriptionTransferCache() {
        if(batchTypeDescriptionTransferCache == null)
            batchTypeDescriptionTransferCache = new BatchTypeDescriptionTransferCache(batchControl);

        return batchTypeDescriptionTransferCache;
    }

    public BatchTypeEntityTypeTransferCache getBatchTypeEntityTypeTransferCache() {
        if(batchTypeEntityTypeTransferCache == null)
            batchTypeEntityTypeTransferCache = new BatchTypeEntityTypeTransferCache(batchControl);

        return batchTypeEntityTypeTransferCache;
    }

    public BatchAliasTypeTransferCache getBatchAliasTypeTransferCache() {
        if(batchAliasTypeTransferCache == null)
            batchAliasTypeTransferCache = new BatchAliasTypeTransferCache(batchControl);
        
        return batchAliasTypeTransferCache;
    }
    
    public BatchAliasTypeDescriptionTransferCache getBatchAliasTypeDescriptionTransferCache() {
        if(batchAliasTypeDescriptionTransferCache == null)
            batchAliasTypeDescriptionTransferCache = new BatchAliasTypeDescriptionTransferCache(batchControl);
        
        return batchAliasTypeDescriptionTransferCache;
    }
    
    public BatchTransferCache getBatchTransferCache() {
        if(batchTransferCache == null)
            batchTransferCache = new BatchTransferCache(batchControl);
        
        return batchTransferCache;
    }
    
    public BatchAliasTransferCache getBatchAliasTransferCache() {
        if(batchAliasTransferCache == null)
            batchAliasTransferCache = new BatchAliasTransferCache(batchControl);

        return batchAliasTransferCache;
    }

    public BatchEntityTransferCache getBatchEntityTransferCache() {
        if(batchEntityTransferCache == null)
            batchEntityTransferCache = new BatchEntityTransferCache(batchControl);

        return batchEntityTransferCache;
    }

}
