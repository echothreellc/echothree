// --------------------------------------------------------------------------------
// Copyright 2002-2021 Echo Three, LLC
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

import com.echothree.model.control.filter.common.transfer.FilterAdjustmentSourceTransfer;
import com.echothree.model.control.filter.server.control.FilterControl;
import com.echothree.model.data.filter.server.entity.FilterAdjustmentSource;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;

public class FilterAdjustmentSourceTransferCache
        extends BaseFilterTransferCache<FilterAdjustmentSource, FilterAdjustmentSourceTransfer> {

    FilterControl filterControl = Session.getModelController(FilterControl.class);

    /** Creates a new instance of FilterAdjustmentSourceTransferCache */
    public FilterAdjustmentSourceTransferCache(UserVisit userVisit) {
        super(userVisit);
    }

    @Override
    public FilterAdjustmentSourceTransfer getTransfer(FilterAdjustmentSource filterAdjustmentSource) {
        FilterAdjustmentSourceTransfer filterAdjustmentSourceTransfer = get(filterAdjustmentSource);
        
        if(filterAdjustmentSourceTransfer == null) {
            String filterAdjustmentSourceName = filterAdjustmentSource.getFilterAdjustmentSourceName();
            Boolean allowedForInitialAmount = filterAdjustmentSource.getAllowedForInitialAmount();
            Boolean isDefault = filterAdjustmentSource.getIsDefault();
            Integer sortOrder = filterAdjustmentSource.getSortOrder();
            String description = filterControl.getBestFilterAdjustmentSourceDescription(filterAdjustmentSource, getLanguage());
            
            filterAdjustmentSourceTransfer = new FilterAdjustmentSourceTransfer(filterAdjustmentSourceName, allowedForInitialAmount, isDefault,
            sortOrder, description);
            put(filterAdjustmentSource, filterAdjustmentSourceTransfer);
        }
        return filterAdjustmentSourceTransfer;
    }
    
}
