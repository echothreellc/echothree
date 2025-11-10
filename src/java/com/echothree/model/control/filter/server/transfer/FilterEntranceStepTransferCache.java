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

import com.echothree.model.control.filter.common.transfer.FilterEntranceStepTransfer;
import com.echothree.model.control.filter.server.control.FilterControl;
import com.echothree.model.data.filter.server.entity.FilterEntranceStep;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;

public class FilterEntranceStepTransferCache
        extends BaseFilterTransferCache<FilterEntranceStep, FilterEntranceStepTransfer> {

    FilterControl filterControl = Session.getModelController(FilterControl.class);

    /** Creates a new instance of FilterEntranceStepTransferCache */
    public FilterEntranceStepTransferCache(UserVisit userVisit) {
        super(userVisit);
    }

    @Override
    public FilterEntranceStepTransfer getTransfer(FilterEntranceStep filterEntranceStep) {
        var filterEntranceStepTransfer = get(filterEntranceStep);
        
        if(filterEntranceStepTransfer == null) {
            var filter = filterControl.getFilterTransfer(userVisit, filterEntranceStep.getFilter());
            var filterStep = filterControl.getFilterStepTransfer(userVisit, filterEntranceStep.getFilterStep());
            
            filterEntranceStepTransfer = new FilterEntranceStepTransfer(filter, filterStep);
            put(userVisit, filterEntranceStep, filterEntranceStepTransfer);
        }
        
        return filterEntranceStepTransfer;
    }
    
}
