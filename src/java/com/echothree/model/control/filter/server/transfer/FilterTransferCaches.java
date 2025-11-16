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
import javax.enterprise.inject.spi.CDI;

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
    public FilterTransferCaches() {
        super();
    }
    
    public FilterKindTransferCache getFilterKindTransferCache() {
        if(filterKindTransferCache == null)
            filterKindTransferCache = CDI.current().select(FilterKindTransferCache.class).get();

        return filterKindTransferCache;
    }

    public FilterKindDescriptionTransferCache getFilterKindDescriptionTransferCache() {
        if(filterKindDescriptionTransferCache == null)
            filterKindDescriptionTransferCache = CDI.current().select(FilterKindDescriptionTransferCache.class).get();

        return filterKindDescriptionTransferCache;
    }

    public FilterTypeTransferCache getFilterTypeTransferCache() {
        if(filterTypeTransferCache == null)
            filterTypeTransferCache = CDI.current().select(FilterTypeTransferCache.class).get();

        return filterTypeTransferCache;
    }

    public FilterTypeDescriptionTransferCache getFilterTypeDescriptionTransferCache() {
        if(filterTypeDescriptionTransferCache == null)
            filterTypeDescriptionTransferCache = CDI.current().select(FilterTypeDescriptionTransferCache.class).get();

        return filterTypeDescriptionTransferCache;
    }

    public FilterAdjustmentTypeTransferCache getFilterAdjustmentTypeTransferCache() {
        if(filterAdjustmentTypeTransferCache == null)
            filterAdjustmentTypeTransferCache = CDI.current().select(FilterAdjustmentTypeTransferCache.class).get();
        
        return filterAdjustmentTypeTransferCache;
    }
    
    public FilterAdjustmentSourceTransferCache getFilterAdjustmentSourceTransferCache() {
        if(filterAdjustmentSourceTransferCache == null)
            filterAdjustmentSourceTransferCache = CDI.current().select(FilterAdjustmentSourceTransferCache.class).get();
        
        return filterAdjustmentSourceTransferCache;
    }
    
    public FilterAdjustmentTransferCache getFilterAdjustmentTransferCache() {
        if(filterAdjustmentTransferCache == null)
            filterAdjustmentTransferCache = CDI.current().select(FilterAdjustmentTransferCache.class).get();
        
        return filterAdjustmentTransferCache;
    }
    
    public FilterTransferCache getFilterTransferCache() {
        if(filterTransferCache == null)
            filterTransferCache = CDI.current().select(FilterTransferCache.class).get();
        
        return filterTransferCache;
    }
    
    public FilterDescriptionTransferCache getFilterDescriptionTransferCache() {
        if(filterDescriptionTransferCache == null)
            filterDescriptionTransferCache = CDI.current().select(FilterDescriptionTransferCache.class).get();
        
        return filterDescriptionTransferCache;
    }
    
    public FilterAdjustmentDescriptionTransferCache getFilterAdjustmentDescriptionTransferCache() {
        if(filterAdjustmentDescriptionTransferCache == null)
            filterAdjustmentDescriptionTransferCache = CDI.current().select(FilterAdjustmentDescriptionTransferCache.class).get();
        
        return filterAdjustmentDescriptionTransferCache;
    }
    
    public FilterAdjustmentAmountTransferCache getFilterAdjustmentAmountTransferCache() {
        if(filterAdjustmentAmountTransferCache == null)
            filterAdjustmentAmountTransferCache = CDI.current().select(FilterAdjustmentAmountTransferCache.class).get();
        
        return filterAdjustmentAmountTransferCache;
    }
    
    public FilterAdjustmentFixedAmountTransferCache getFilterAdjustmentFixedAmountTransferCache() {
        if(filterAdjustmentFixedAmountTransferCache == null)
            filterAdjustmentFixedAmountTransferCache = CDI.current().select(FilterAdjustmentFixedAmountTransferCache.class).get();
        
        return filterAdjustmentFixedAmountTransferCache;
    }
    
    public FilterAdjustmentPercentTransferCache getFilterAdjustmentPercentTransferCache() {
        if(filterAdjustmentPercentTransferCache == null)
            filterAdjustmentPercentTransferCache = CDI.current().select(FilterAdjustmentPercentTransferCache.class).get();
        
        return filterAdjustmentPercentTransferCache;
    }
    
    public FilterStepTransferCache getFilterStepTransferCache() {
        if(filterStepTransferCache == null)
            filterStepTransferCache = CDI.current().select(FilterStepTransferCache.class).get();
        
        return filterStepTransferCache;
    }
    
    public FilterStepDescriptionTransferCache getFilterStepDescriptionTransferCache() {
        if(filterStepDescriptionTransferCache == null)
            filterStepDescriptionTransferCache = CDI.current().select(FilterStepDescriptionTransferCache.class).get();
        
        return filterStepDescriptionTransferCache;
    }
    
    public FilterEntranceStepTransferCache getFilterEntranceStepTransferCache() {
        if(filterEntranceStepTransferCache == null)
            filterEntranceStepTransferCache = CDI.current().select(FilterEntranceStepTransferCache.class).get();
        
        return filterEntranceStepTransferCache;
    }
    
    public FilterStepDestinationTransferCache getFilterStepDestinationTransferCache() {
        if(filterStepDestinationTransferCache == null)
            filterStepDestinationTransferCache = CDI.current().select(FilterStepDestinationTransferCache.class).get();
        
        return filterStepDestinationTransferCache;
    }
    
    public FilterStepElementTransferCache getFilterStepElementTransferCache() {
        if(filterStepElementTransferCache == null)
            filterStepElementTransferCache = CDI.current().select(FilterStepElementTransferCache.class).get();
        
        return filterStepElementTransferCache;
    }
    
    public FilterStepElementDescriptionTransferCache getFilterStepElementDescriptionTransferCache() {
        if(filterStepElementDescriptionTransferCache == null)
            filterStepElementDescriptionTransferCache = CDI.current().select(FilterStepElementDescriptionTransferCache.class).get();
        
        return filterStepElementDescriptionTransferCache;
    }
    
}
