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

import com.echothree.util.server.transfer.BaseTransferCaches;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

@RequestScoped
public class TagTransferCaches
        extends BaseTransferCaches {
    
    @Inject
    TagScopeTransferCache tagScopeTransferCache;
    
    @Inject
    TagScopeDescriptionTransferCache tagScopeDescriptionTransferCache;
    
    @Inject
    TagScopeEntityTypeTransferCache tagScopeEntityTypeTransferCache;
    
    @Inject
    TagTransferCache tagTransferCache;
    
    @Inject
    EntityTagTransferCache entityTagTransferCache;

    /** Creates a new instance of TagTransferCaches */
    protected TagTransferCaches() {
        super();
    }
    
    public TagScopeTransferCache getTagScopeTransferCache() {
        return tagScopeTransferCache;
    }
    
    public TagScopeDescriptionTransferCache getTagScopeDescriptionTransferCache() {
        return tagScopeDescriptionTransferCache;
    }
    
    public TagScopeEntityTypeTransferCache getTagScopeEntityTypeTransferCache() {
        return tagScopeEntityTypeTransferCache;
    }
    
    public TagTransferCache getTagTransferCache() {
        return tagTransferCache;
    }
    
    public EntityTagTransferCache getEntityTagTransferCache() {
        return entityTagTransferCache;
    }
    
}
