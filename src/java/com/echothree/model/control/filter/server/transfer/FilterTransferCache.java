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

import com.echothree.model.control.filter.common.FilterOptions;
import com.echothree.model.control.filter.common.transfer.FilterAdjustmentTransfer;
import com.echothree.model.control.filter.common.transfer.FilterTransfer;
import com.echothree.model.control.filter.common.transfer.FilterTypeTransfer;
import com.echothree.model.control.filter.server.control.FilterControl;
import com.echothree.model.control.selector.common.transfer.SelectorTransfer;
import com.echothree.model.control.selector.server.control.SelectorControl;
import com.echothree.model.data.filter.server.entity.Filter;
import com.echothree.model.data.filter.server.entity.FilterDetail;
import com.echothree.model.data.selector.server.entity.Selector;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.common.transfer.ListWrapper;
import com.echothree.util.server.persistence.Session;
import java.util.Set;

public class FilterTransferCache
        extends BaseFilterTransferCache<Filter, FilterTransfer> {
    
    SelectorControl selectorControl;
    boolean includeFilterEntranceSteps;
    boolean includeFilterSteps;
    
    /** Creates a new instance of FilterTransferCache */
    public FilterTransferCache(UserVisit userVisit, FilterControl filterControl) {
        super(userVisit, filterControl);
        
        selectorControl = (SelectorControl)Session.getModelController(SelectorControl.class);
        
        Set<String> options = session.getOptions();
        if(options != null) {
            includeFilterEntranceSteps = options.contains(FilterOptions.FilterIncludeFilterEntranceSteps);
            includeFilterSteps = options.contains(FilterOptions.FilterIncludeFilterSteps);
        }
        
        setIncludeEntityInstance(true);
    }
    
    public FilterTransfer getFilterTransfer(Filter filter) {
        FilterTransfer filterTransfer = get(filter);
        
        if(filterTransfer == null) {
            FilterDetail filterDetail = filter.getLastDetail();
            FilterTypeTransfer filterTypeTransfer = filterControl.getFilterTypeTransfer(userVisit, filterDetail.getFilterType());
            String filterName = filterDetail.getFilterName();
            FilterAdjustmentTransfer initialFilterAdjustmentTransfer = filterControl.getFilterAdjustmentTransfer(userVisit, filterDetail.getInitialFilterAdjustment());
            Selector filterItemSelector = filterDetail.getFilterItemSelector();
            SelectorTransfer filterItemSelectorTransfer = filterItemSelector == null? null: selectorControl.getSelectorTransfer(userVisit, filterItemSelector);
            Boolean isDefault = filterDetail.getIsDefault();
            Integer sortOrder = filterDetail.getSortOrder();
            String description = filterControl.getBestFilterDescription(filter, getLanguage());
            
            filterTransfer = new FilterTransfer(filterTypeTransfer, filterName, initialFilterAdjustmentTransfer,
                    filterItemSelectorTransfer, isDefault, sortOrder, description);
            put(filter, filterTransfer);
            
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
