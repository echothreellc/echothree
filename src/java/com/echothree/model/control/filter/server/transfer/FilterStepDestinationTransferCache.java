// --------------------------------------------------------------------------------
// Copyright 2002-2020 Echo Three, LLC
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

import com.echothree.model.control.filter.common.transfer.FilterStepDestinationTransfer;
import com.echothree.model.control.filter.common.transfer.FilterStepTransfer;
import com.echothree.model.control.filter.server.FilterControl;
import com.echothree.model.data.filter.server.entity.FilterStepDestination;
import com.echothree.model.data.user.server.entity.UserVisit;

public class FilterStepDestinationTransferCache
        extends BaseFilterTransferCache<FilterStepDestination, FilterStepDestinationTransfer> {
    
    /** Creates a new instance of FilterStepDestinationTransferCache */
    public FilterStepDestinationTransferCache(UserVisit userVisit, FilterControl filterControl) {
        super(userVisit, filterControl);
    }
    
    public FilterStepDestinationTransfer getFilterStepDestinationTransfer(FilterStepDestination filterStepDestination) {
        FilterStepDestinationTransfer filterStepDestinationTransfer = get(filterStepDestination);
        
        if(filterStepDestinationTransfer == null) {
            FilterStepTransferCache filterStepTransferCache = filterControl.getFilterTransferCaches(userVisit).getFilterStepTransferCache();
            FilterStepTransfer fromFilterStep = filterStepTransferCache.getFilterStepTransfer(filterStepDestination.getFromFilterStep());
            FilterStepTransfer toFilterStep = filterStepTransferCache.getFilterStepTransfer(filterStepDestination.getToFilterStep());
            
            filterStepDestinationTransfer = new FilterStepDestinationTransfer(fromFilterStep, toFilterStep);
            put(filterStepDestination, filterStepDestinationTransfer);
        }
        
        return filterStepDestinationTransfer;
    }
    
}
