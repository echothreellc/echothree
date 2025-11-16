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
import com.echothree.model.control.filter.common.transfer.FilterStepTransfer;
import com.echothree.model.control.filter.server.control.FilterControl;
import com.echothree.model.control.selector.server.control.SelectorControl;
import com.echothree.model.data.filter.server.entity.FilterStep;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.common.transfer.ListWrapper;
import com.echothree.util.server.persistence.Session;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class FilterStepTransferCache
        extends BaseFilterTransferCache<FilterStep, FilterStepTransfer> {

    FilterControl filterControl = Session.getModelController(FilterControl.class);
    SelectorControl selectorControl = Session.getModelController(SelectorControl.class);

    boolean includeFilterStepElements;
    boolean includeFilterStepDestinations;
    
    /** Creates a new instance of FilterStepTransferCache */
    public FilterStepTransferCache() {
        super();
        
        var options = session.getOptions();
        if(options != null) {
            includeFilterStepElements = options.contains(FilterOptions.FilterStepIncludeFilterStepElements);
            includeFilterStepDestinations = options.contains(FilterOptions.FilterStepIncludeFilterStepDestinations);
        }

        setIncludeEntityInstance(true);
    }

    @Override
    public FilterStepTransfer getTransfer(UserVisit userVisit, FilterStep filterStep) {
        var filterStepTransfer = get(filterStep);
        
        if(filterStepTransfer == null) {
            var filterStepDetail = filterStep.getLastDetail();
            var filter = filterControl.getFilterTransfer(userVisit, filterStepDetail.getFilter());
            var filterStepName = filterStepDetail.getFilterStepName();
            var filterItemSelector = filterStepDetail.getFilterItemSelector();
            var filterItemSelectorTransfer = filterItemSelector == null? null: selectorControl.getSelectorTransfer(userVisit, filterItemSelector);
            var description = filterControl.getBestFilterStepDescription(filterStep, getLanguage(userVisit));
            
            filterStepTransfer = new FilterStepTransfer(filter, filterStepName, filterItemSelectorTransfer, description);
            put(userVisit, filterStep, filterStepTransfer);
            
            if(includeFilterStepElements) {
                filterStepTransfer.setFilterStepElements(new ListWrapper<>(filterControl.getFilterStepElementTransfersByFilterStep(userVisit, filterStep)));
            }
            
            if(includeFilterStepDestinations) {
                filterStepTransfer.setFilterStepDestinations(new ListWrapper<>(filterControl.getFilterStepDestinationTransfersByFromFilterStep(userVisit, filterStep)));
            }
        }
        
        return filterStepTransfer;
    }
    
}
