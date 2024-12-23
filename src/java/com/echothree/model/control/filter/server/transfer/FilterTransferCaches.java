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

package com.echothree.model.control.filter.server.transfer;

import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.transfer.BaseTransferCaches;

public class FilterTransferCaches
        extends BaseTransferCaches {
    
    protected FilterKindTransferCache filterKindTransferCache;
    protected FilterKindDescriptionTransferCache filterKindDescriptionTransferCache;
    protected FilterTypeTransferCache filterTypeTransferCache;
    protected FilterTypeDescriptionTransferCache filterTypeDescriptionTransferCache;
    protected FilterAdjustmentDescriptionTransferCache filterAdjustmentDescriptionTransferCache;
    protected FilterAdjustmentTypeTransferCache filterAdjustmentTypeTransferCache;
    protected FilterAdjustmentSourceTransferCache filterAdjustmentSourceTransferCache;
    protected FilterAdjustmentTransferCache filterAdjustmentTransferCache;
    protected FilterTransferCache filterTransferCache;
    protected FilterDescriptionTransferCache filterDescriptionTransferCache;
    protected FilterAdjustmentAmountTransferCache filterAdjustmentAmountTransferCache;
    protected FilterAdjustmentFixedAmountTransferCache filterAdjustmentFixedAmountTransferCache;
    protected FilterAdjustmentPercentTransferCache filterAdjustmentPercentTransferCache;
    protected FilterStepTransferCache filterStepTransferCache;
    protected FilterStepDescriptionTransferCache filterStepDescriptionTransferCache;
    protected FilterEntranceStepTransferCache filterEntranceStepTransferCache;
    protected FilterStepDestinationTransferCache filterStepDestinationTransferCache;
    protected FilterStepElementTransferCache filterStepElementTransferCache;
    protected FilterStepElementDescriptionTransferCache filterStepElementDescriptionTransferCache;
    
    /** Creates a new instance of FilterTransferCaches */
    public FilterTransferCaches(UserVisit userVisit) {
        super(userVisit);
    }
    
    public FilterKindTransferCache getFilterKindTransferCache() {
        if(filterKindTransferCache == null)
            filterKindTransferCache = new FilterKindTransferCache(userVisit);

        return filterKindTransferCache;
    }

    public FilterKindDescriptionTransferCache getFilterKindDescriptionTransferCache() {
        if(filterKindDescriptionTransferCache == null)
            filterKindDescriptionTransferCache = new FilterKindDescriptionTransferCache(userVisit);

        return filterKindDescriptionTransferCache;
    }

    public FilterTypeTransferCache getFilterTypeTransferCache() {
        if(filterTypeTransferCache == null)
            filterTypeTransferCache = new FilterTypeTransferCache(userVisit);

        return filterTypeTransferCache;
    }

    public FilterTypeDescriptionTransferCache getFilterTypeDescriptionTransferCache() {
        if(filterTypeDescriptionTransferCache == null)
            filterTypeDescriptionTransferCache = new FilterTypeDescriptionTransferCache(userVisit);

        return filterTypeDescriptionTransferCache;
    }

    public FilterAdjustmentTypeTransferCache getFilterAdjustmentTypeTransferCache() {
        if(filterAdjustmentTypeTransferCache == null)
            filterAdjustmentTypeTransferCache = new FilterAdjustmentTypeTransferCache(userVisit);
        
        return filterAdjustmentTypeTransferCache;
    }
    
    public FilterAdjustmentSourceTransferCache getFilterAdjustmentSourceTransferCache() {
        if(filterAdjustmentSourceTransferCache == null)
            filterAdjustmentSourceTransferCache = new FilterAdjustmentSourceTransferCache(userVisit);
        
        return filterAdjustmentSourceTransferCache;
    }
    
    public FilterAdjustmentTransferCache getFilterAdjustmentTransferCache() {
        if(filterAdjustmentTransferCache == null)
            filterAdjustmentTransferCache = new FilterAdjustmentTransferCache(userVisit);
        
        return filterAdjustmentTransferCache;
    }
    
    public FilterTransferCache getFilterTransferCache() {
        if(filterTransferCache == null)
            filterTransferCache = new FilterTransferCache(userVisit);
        
        return filterTransferCache;
    }
    
    public FilterDescriptionTransferCache getFilterDescriptionTransferCache() {
        if(filterDescriptionTransferCache == null)
            filterDescriptionTransferCache = new FilterDescriptionTransferCache(userVisit);
        
        return filterDescriptionTransferCache;
    }
    
    public FilterAdjustmentDescriptionTransferCache getFilterAdjustmentDescriptionTransferCache() {
        if(filterAdjustmentDescriptionTransferCache == null)
            filterAdjustmentDescriptionTransferCache = new FilterAdjustmentDescriptionTransferCache(userVisit);
        
        return filterAdjustmentDescriptionTransferCache;
    }
    
    public FilterAdjustmentAmountTransferCache getFilterAdjustmentAmountTransferCache() {
        if(filterAdjustmentAmountTransferCache == null)
            filterAdjustmentAmountTransferCache = new FilterAdjustmentAmountTransferCache(userVisit);
        
        return filterAdjustmentAmountTransferCache;
    }
    
    public FilterAdjustmentFixedAmountTransferCache getFilterAdjustmentFixedAmountTransferCache() {
        if(filterAdjustmentFixedAmountTransferCache == null)
            filterAdjustmentFixedAmountTransferCache = new FilterAdjustmentFixedAmountTransferCache(userVisit);
        
        return filterAdjustmentFixedAmountTransferCache;
    }
    
    public FilterAdjustmentPercentTransferCache getFilterAdjustmentPercentTransferCache() {
        if(filterAdjustmentPercentTransferCache == null)
            filterAdjustmentPercentTransferCache = new FilterAdjustmentPercentTransferCache(userVisit);
        
        return filterAdjustmentPercentTransferCache;
    }
    
    public FilterStepTransferCache getFilterStepTransferCache() {
        if(filterStepTransferCache == null)
            filterStepTransferCache = new FilterStepTransferCache(userVisit);
        
        return filterStepTransferCache;
    }
    
    public FilterStepDescriptionTransferCache getFilterStepDescriptionTransferCache() {
        if(filterStepDescriptionTransferCache == null)
            filterStepDescriptionTransferCache = new FilterStepDescriptionTransferCache(userVisit);
        
        return filterStepDescriptionTransferCache;
    }
    
    public FilterEntranceStepTransferCache getFilterEntranceStepTransferCache() {
        if(filterEntranceStepTransferCache == null)
            filterEntranceStepTransferCache = new FilterEntranceStepTransferCache(userVisit);
        
        return filterEntranceStepTransferCache;
    }
    
    public FilterStepDestinationTransferCache getFilterStepDestinationTransferCache() {
        if(filterStepDestinationTransferCache == null)
            filterStepDestinationTransferCache = new FilterStepDestinationTransferCache(userVisit);
        
        return filterStepDestinationTransferCache;
    }
    
    public FilterStepElementTransferCache getFilterStepElementTransferCache() {
        if(filterStepElementTransferCache == null)
            filterStepElementTransferCache = new FilterStepElementTransferCache(userVisit);
        
        return filterStepElementTransferCache;
    }
    
    public FilterStepElementDescriptionTransferCache getFilterStepElementDescriptionTransferCache() {
        if(filterStepElementDescriptionTransferCache == null)
            filterStepElementDescriptionTransferCache = new FilterStepElementDescriptionTransferCache(userVisit);
        
        return filterStepElementDescriptionTransferCache;
    }
    
}
