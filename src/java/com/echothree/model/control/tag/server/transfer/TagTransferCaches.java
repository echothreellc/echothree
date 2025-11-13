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

package com.echothree.model.control.tag.server.transfer;

import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.transfer.BaseTransferCaches;

public class TagTransferCaches
        extends BaseTransferCaches {
    
    protected TagScopeTransferCache tagScopeTransferCache;
    protected TagScopeDescriptionTransferCache tagScopeDescriptionTransferCache;
    protected TagScopeEntityTypeTransferCache tagScopeEntityTypeTransferCache;
    protected TagTransferCache tagTransferCache;
    protected EntityTagTransferCache entityTagTransferCache;
    
    /** Creates a new instance of TagTransferCaches */
    public TagTransferCaches() {
        super();
    }
    
    public TagScopeTransferCache getTagScopeTransferCache() {
        if(tagScopeTransferCache == null)
            tagScopeTransferCache = new TagScopeTransferCache();
        
        return tagScopeTransferCache;
    }
    
    public TagScopeDescriptionTransferCache getTagScopeDescriptionTransferCache() {
        if(tagScopeDescriptionTransferCache == null)
            tagScopeDescriptionTransferCache = new TagScopeDescriptionTransferCache();
        
        return tagScopeDescriptionTransferCache;
    }
    
    public TagScopeEntityTypeTransferCache getTagScopeEntityTypeTransferCache() {
        if(tagScopeEntityTypeTransferCache == null)
            tagScopeEntityTypeTransferCache = new TagScopeEntityTypeTransferCache();
        
        return tagScopeEntityTypeTransferCache;
    }
    
    public TagTransferCache getTagTransferCache() {
        if(tagTransferCache == null)
            tagTransferCache = new TagTransferCache();
        
        return tagTransferCache;
    }
    
    public EntityTagTransferCache getEntityTagTransferCache() {
        if(entityTagTransferCache == null)
            entityTagTransferCache = new EntityTagTransferCache();
        
        return entityTagTransferCache;
    }
    
}
