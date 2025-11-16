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
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

@RequestScoped
public class BatchTransferCaches
        extends BaseTransferCaches {
    
    @Inject
    BatchTypeTransferCache batchTypeTransferCache;
    
    @Inject
    BatchTypeDescriptionTransferCache batchTypeDescriptionTransferCache;
    
    @Inject
    BatchTypeEntityTypeTransferCache batchTypeEntityTypeTransferCache;
    
    @Inject
    BatchAliasTypeTransferCache batchAliasTypeTransferCache;
    
    @Inject
    BatchAliasTypeDescriptionTransferCache batchAliasTypeDescriptionTransferCache;
    
    @Inject
    BatchTransferCache batchTransferCache;
    
    @Inject
    BatchAliasTransferCache batchAliasTransferCache;
    
    @Inject
    BatchEntityTransferCache batchEntityTransferCache;
    
    /** Creates a new instance of BatchTransferCaches */
    protected BatchTransferCaches() {
        super();
    }
    
    public BatchTypeTransferCache getBatchTypeTransferCache() {
        return batchTypeTransferCache;
    }
    
    public BatchTypeDescriptionTransferCache getBatchTypeDescriptionTransferCache() {
        return batchTypeDescriptionTransferCache;
    }

    public BatchTypeEntityTypeTransferCache getBatchTypeEntityTypeTransferCache() {
        return batchTypeEntityTypeTransferCache;
    }

    public BatchAliasTypeTransferCache getBatchAliasTypeTransferCache() {
        return batchAliasTypeTransferCache;
    }
    
    public BatchAliasTypeDescriptionTransferCache getBatchAliasTypeDescriptionTransferCache() {
        return batchAliasTypeDescriptionTransferCache;
    }
    
    public BatchTransferCache getBatchTransferCache() {
        return batchTransferCache;
    }
    
    public BatchAliasTransferCache getBatchAliasTransferCache() {
        return batchAliasTransferCache;
    }

    public BatchEntityTransferCache getBatchEntityTransferCache() {
        return batchEntityTransferCache;
    }

}
