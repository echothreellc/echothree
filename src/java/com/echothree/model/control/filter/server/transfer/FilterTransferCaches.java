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

import com.echothree.util.server.transfer.BaseTransferCaches;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

@RequestScoped
public class FilterTransferCaches
        extends BaseTransferCaches {
    
    @Inject
    FilterKindTransferCache filterKindTransferCache;
    
    @Inject
    FilterKindDescriptionTransferCache filterKindDescriptionTransferCache;
    
    @Inject
    FilterTypeTransferCache filterTypeTransferCache;
    
    @Inject
    FilterTypeDescriptionTransferCache filterTypeDescriptionTransferCache;
    
    @Inject
    FilterAdjustmentDescriptionTransferCache filterAdjustmentDescriptionTransferCache;
    
    @Inject
    FilterAdjustmentTypeTransferCache filterAdjustmentTypeTransferCache;
    
    @Inject
    FilterAdjustmentSourceTransferCache filterAdjustmentSourceTransferCache;
    
    @Inject
    FilterAdjustmentTransferCache filterAdjustmentTransferCache;
    
    @Inject
    FilterTransferCache filterTransferCache;
    
    @Inject
    FilterDescriptionTransferCache filterDescriptionTransferCache;
    
    @Inject
    FilterAdjustmentAmountTransferCache filterAdjustmentAmountTransferCache;
    
    @Inject
    FilterAdjustmentFixedAmountTransferCache filterAdjustmentFixedAmountTransferCache;
    
    @Inject
    FilterAdjustmentPercentTransferCache filterAdjustmentPercentTransferCache;
    
    @Inject
    FilterStepTransferCache filterStepTransferCache;
    
    @Inject
    FilterStepDescriptionTransferCache filterStepDescriptionTransferCache;
    
    @Inject
    FilterEntranceStepTransferCache filterEntranceStepTransferCache;
    
    @Inject
    FilterStepDestinationTransferCache filterStepDestinationTransferCache;
    
    @Inject
    FilterStepElementTransferCache filterStepElementTransferCache;
    
    @Inject
    FilterStepElementDescriptionTransferCache filterStepElementDescriptionTransferCache;

    /** Creates a new instance of FilterTransferCaches */
    protected FilterTransferCaches() {
        super();
    }
    
    public FilterKindTransferCache getFilterKindTransferCache() {
        return filterKindTransferCache;
    }

    public FilterKindDescriptionTransferCache getFilterKindDescriptionTransferCache() {
        return filterKindDescriptionTransferCache;
    }

    public FilterTypeTransferCache getFilterTypeTransferCache() {
        return filterTypeTransferCache;
    }

    public FilterTypeDescriptionTransferCache getFilterTypeDescriptionTransferCache() {
        return filterTypeDescriptionTransferCache;
    }

    public FilterAdjustmentTypeTransferCache getFilterAdjustmentTypeTransferCache() {
        return filterAdjustmentTypeTransferCache;
    }
    
    public FilterAdjustmentSourceTransferCache getFilterAdjustmentSourceTransferCache() {
        return filterAdjustmentSourceTransferCache;
    }
    
    public FilterAdjustmentTransferCache getFilterAdjustmentTransferCache() {
        return filterAdjustmentTransferCache;
    }
    
    public FilterTransferCache getFilterTransferCache() {
        return filterTransferCache;
    }
    
    public FilterDescriptionTransferCache getFilterDescriptionTransferCache() {
        return filterDescriptionTransferCache;
    }
    
    public FilterAdjustmentDescriptionTransferCache getFilterAdjustmentDescriptionTransferCache() {
        return filterAdjustmentDescriptionTransferCache;
    }
    
    public FilterAdjustmentAmountTransferCache getFilterAdjustmentAmountTransferCache() {
        return filterAdjustmentAmountTransferCache;
    }
    
    public FilterAdjustmentFixedAmountTransferCache getFilterAdjustmentFixedAmountTransferCache() {
        return filterAdjustmentFixedAmountTransferCache;
    }
    
    public FilterAdjustmentPercentTransferCache getFilterAdjustmentPercentTransferCache() {
        return filterAdjustmentPercentTransferCache;
    }
    
    public FilterStepTransferCache getFilterStepTransferCache() {
        return filterStepTransferCache;
    }
    
    public FilterStepDescriptionTransferCache getFilterStepDescriptionTransferCache() {
        return filterStepDescriptionTransferCache;
    }
    
    public FilterEntranceStepTransferCache getFilterEntranceStepTransferCache() {
        return filterEntranceStepTransferCache;
    }
    
    public FilterStepDestinationTransferCache getFilterStepDestinationTransferCache() {
        return filterStepDestinationTransferCache;
    }
    
    public FilterStepElementTransferCache getFilterStepElementTransferCache() {
        return filterStepElementTransferCache;
    }
    
    public FilterStepElementDescriptionTransferCache getFilterStepElementDescriptionTransferCache() {
        return filterStepElementDescriptionTransferCache;
    }
    
}
