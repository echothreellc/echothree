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

package com.echothree.model.control.picklist.server.transfer;

import com.echothree.util.server.transfer.BaseTransferCaches;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

@RequestScoped
public class PicklistTransferCaches
        extends BaseTransferCaches {
    
    @Inject
    PicklistTypeTransferCache picklistTypeTransferCache;
    
    @Inject
    PicklistTypeDescriptionTransferCache picklistTypeDescriptionTransferCache;
    
    @Inject
    PicklistAliasTypeTransferCache picklistAliasTypeTransferCache;
    
    @Inject
    PicklistAliasTypeDescriptionTransferCache picklistAliasTypeDescriptionTransferCache;
    
    @Inject
    PicklistAliasTransferCache picklistAliasTransferCache;
    
    @Inject
    PicklistTimeTypeTransferCache picklistTimeTypeTransferCache;
    
    @Inject
    PicklistTimeTypeDescriptionTransferCache picklistTimeTypeDescriptionTransferCache;
    
    @Inject
    PicklistTimeTransferCache picklistTimeTransferCache;

    /** Creates a new instance of PicklistTransferCaches */
    protected PicklistTransferCaches() {
        super();
    }
    
    public PicklistTypeTransferCache getPicklistTypeTransferCache() {
        return picklistTypeTransferCache;
    }
    
    public PicklistTypeDescriptionTransferCache getPicklistTypeDescriptionTransferCache() {
        return picklistTypeDescriptionTransferCache;
    }
    
    public PicklistAliasTypeTransferCache getPicklistAliasTypeTransferCache() {
        return picklistAliasTypeTransferCache;
    }
    
    public PicklistAliasTypeDescriptionTransferCache getPicklistAliasTypeDescriptionTransferCache() {
        return picklistAliasTypeDescriptionTransferCache;
    }
    
    public PicklistAliasTransferCache getPicklistAliasTransferCache() {
        return picklistAliasTransferCache;
    }
    
    public PicklistTimeTypeTransferCache getPicklistTimeTypeTransferCache() {
        return picklistTimeTypeTransferCache;
    }

    public PicklistTimeTransferCache getPicklistTimeTransferCache() {
        return picklistTimeTransferCache;
    }

    public PicklistTimeTypeDescriptionTransferCache getPicklistTimeTypeDescriptionTransferCache() {
        return picklistTimeTypeDescriptionTransferCache;
    }

}
