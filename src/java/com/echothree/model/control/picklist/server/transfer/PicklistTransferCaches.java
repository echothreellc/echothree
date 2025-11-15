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

public class PicklistTransferCaches
        extends BaseTransferCaches {
    
    protected PicklistTypeTransferCache picklistTypeTransferCache;
    protected PicklistTypeDescriptionTransferCache picklistTypeDescriptionTransferCache;
    protected PicklistAliasTypeTransferCache picklistAliasTypeTransferCache;
    protected PicklistAliasTypeDescriptionTransferCache picklistAliasTypeDescriptionTransferCache;
    protected PicklistAliasTransferCache picklistAliasTransferCache;
    protected PicklistTimeTypeTransferCache picklistTimeTypeTransferCache;
    protected PicklistTimeTypeDescriptionTransferCache picklistTimeTypeDescriptionTransferCache;
    protected PicklistTimeTransferCache picklistTimeTransferCache;
    
    /** Creates a new instance of PicklistTransferCaches */
    public PicklistTransferCaches() {
        super();
    }
    
    public PicklistTypeTransferCache getPicklistTypeTransferCache() {
        if(picklistTypeTransferCache == null)
            picklistTypeTransferCache = new PicklistTypeTransferCache();
        
        return picklistTypeTransferCache;
    }
    
    public PicklistTypeDescriptionTransferCache getPicklistTypeDescriptionTransferCache() {
        if(picklistTypeDescriptionTransferCache == null)
            picklistTypeDescriptionTransferCache = new PicklistTypeDescriptionTransferCache();
        
        return picklistTypeDescriptionTransferCache;
    }
    
    public PicklistAliasTypeTransferCache getPicklistAliasTypeTransferCache() {
        if(picklistAliasTypeTransferCache == null)
            picklistAliasTypeTransferCache = new PicklistAliasTypeTransferCache();
        
        return picklistAliasTypeTransferCache;
    }
    
    public PicklistAliasTypeDescriptionTransferCache getPicklistAliasTypeDescriptionTransferCache() {
        if(picklistAliasTypeDescriptionTransferCache == null)
            picklistAliasTypeDescriptionTransferCache = new PicklistAliasTypeDescriptionTransferCache();
        
        return picklistAliasTypeDescriptionTransferCache;
    }
    
    public PicklistAliasTransferCache getPicklistAliasTransferCache() {
        if(picklistAliasTransferCache == null)
            picklistAliasTransferCache = new PicklistAliasTransferCache();
        
        return picklistAliasTransferCache;
    }
    
    public PicklistTimeTypeTransferCache getPicklistTimeTypeTransferCache() {
        if(picklistTimeTypeTransferCache == null)
            picklistTimeTypeTransferCache = new PicklistTimeTypeTransferCache();

        return picklistTimeTypeTransferCache;
    }

    public PicklistTimeTransferCache getPicklistTimeTransferCache() {
        if(picklistTimeTransferCache == null)
            picklistTimeTransferCache = new PicklistTimeTransferCache();

        return picklistTimeTransferCache;
    }

    public PicklistTimeTypeDescriptionTransferCache getPicklistTimeTypeDescriptionTransferCache() {
        if(picklistTimeTypeDescriptionTransferCache == null)
            picklistTimeTypeDescriptionTransferCache = new PicklistTimeTypeDescriptionTransferCache();

        return picklistTimeTypeDescriptionTransferCache;
    }

}
