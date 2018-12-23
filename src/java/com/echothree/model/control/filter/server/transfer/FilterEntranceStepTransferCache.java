// --------------------------------------------------------------------------------
// Copyright 2002-2019 Echo Three, LLC
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

import com.echothree.model.control.filter.common.transfer.FilterEntranceStepTransfer;
import com.echothree.model.control.filter.common.transfer.FilterStepTransfer;
import com.echothree.model.control.filter.common.transfer.FilterTransfer;
import com.echothree.model.control.filter.server.FilterControl;
import com.echothree.model.data.filter.server.entity.FilterEntranceStep;
import com.echothree.model.data.user.server.entity.UserVisit;

public class FilterEntranceStepTransferCache
        extends BaseFilterTransferCache<FilterEntranceStep, FilterEntranceStepTransfer> {
    
    /** Creates a new instance of FilterEntranceStepTransferCache */
    public FilterEntranceStepTransferCache(UserVisit userVisit, FilterControl filterControl) {
        super(userVisit, filterControl);
    }
    
    public FilterEntranceStepTransfer getFilterEntranceStepTransfer(FilterEntranceStep filterEntranceStep) {
        FilterEntranceStepTransfer filterEntranceStepTransfer = get(filterEntranceStep);
        
        if(filterEntranceStepTransfer == null) {
            FilterTransferCache filterTransferCache = filterControl.getFilterTransferCaches(userVisit).getFilterTransferCache();
            FilterTransfer filter = filterTransferCache.getFilterTransfer(filterEntranceStep.getFilter());
            FilterStepTransferCache filterStepTransferCache = filterControl.getFilterTransferCaches(userVisit).getFilterStepTransferCache();
            FilterStepTransfer filterStep = filterStepTransferCache.getFilterStepTransfer(filterEntranceStep.getFilterStep());
            
            filterEntranceStepTransfer = new FilterEntranceStepTransfer(filter, filterStep);
            put(filterEntranceStep, filterEntranceStepTransfer);
        }
        
        return filterEntranceStepTransfer;
    }
    
}
