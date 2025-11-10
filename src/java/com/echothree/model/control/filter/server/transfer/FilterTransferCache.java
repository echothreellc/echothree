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

import com.echothree.model.control.filter.common.FilterOptions;
import com.echothree.model.control.filter.common.transfer.FilterTransfer;
import com.echothree.model.control.filter.server.control.FilterControl;
import com.echothree.model.control.selector.server.control.SelectorControl;
import com.echothree.model.data.filter.server.entity.Filter;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.common.transfer.ListWrapper;
import com.echothree.util.server.persistence.Session;

public class FilterTransferCache
        extends BaseFilterTransferCache<Filter, FilterTransfer> {

    FilterControl filterControl = Session.getModelController(FilterControl.class);
    SelectorControl selectorControl = Session.getModelController(SelectorControl.class);

    boolean includeFilterEntranceSteps;
    boolean includeFilterSteps;
    
    /** Creates a new instance of FilterTransferCache */
    public FilterTransferCache(UserVisit userVisit) {
        super(userVisit);

        var options = session.getOptions();
        if(options != null) {
            includeFilterEntranceSteps = options.contains(FilterOptions.FilterIncludeFilterEntranceSteps);
            includeFilterSteps = options.contains(FilterOptions.FilterIncludeFilterSteps);
        }
        
        setIncludeEntityInstance(true);
    }

    @Override
    public FilterTransfer getTransfer(Filter filter) {
        var filterTransfer = get(filter);
        
        if(filterTransfer == null) {
            var filterDetail = filter.getLastDetail();
            var filterTypeTransfer = filterControl.getFilterTypeTransfer(userVisit, filterDetail.getFilterType());
            var filterName = filterDetail.getFilterName();
            var initialFilterAdjustmentTransfer = filterControl.getFilterAdjustmentTransfer(userVisit, filterDetail.getInitialFilterAdjustment());
            var filterItemSelector = filterDetail.getFilterItemSelector();
            var filterItemSelectorTransfer = filterItemSelector == null? null: selectorControl.getSelectorTransfer(userVisit, filterItemSelector);
            var isDefault = filterDetail.getIsDefault();
            var sortOrder = filterDetail.getSortOrder();
            var description = filterControl.getBestFilterDescription(filter, getLanguage(userVisit));
            
            filterTransfer = new FilterTransfer(filterTypeTransfer, filterName, initialFilterAdjustmentTransfer,
                    filterItemSelectorTransfer, isDefault, sortOrder, description);
            put(userVisit, filter, filterTransfer);
            
            if(includeFilterEntranceSteps) {
                filterTransfer.setFilterEntranceSteps(new ListWrapper<>(filterControl.getFilterEntranceStepTransfersByFilter(userVisit, filter)));
            }
            
            if(includeFilterSteps) {
                filterTransfer.setFilterSteps(new ListWrapper<>(filterControl.getFilterStepTransfersByFilter(userVisit, filter)));
            }
        }
        return filterTransfer;
    }
    
}
