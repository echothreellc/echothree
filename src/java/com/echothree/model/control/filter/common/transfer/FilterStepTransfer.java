// --------------------------------------------------------------------------------
// Copyright 2002-2026 Echo Three, LLC
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

package com.echothree.model.control.filter.common.transfer;

import com.echothree.model.control.selector.common.transfer.SelectorTransfer;
import com.echothree.util.common.transfer.BaseTransfer;
import com.echothree.util.common.transfer.ListWrapper;

public class FilterStepTransfer
        extends BaseTransfer {
    
    private FilterTransfer filter;
    private String filterStepName;
    private SelectorTransfer filterItemSelector;
    private String description;
    private ListWrapper<FilterStepElementTransfer> filterStepElements;
    private ListWrapper<FilterStepDestinationTransfer> filterStepDestinations;
    
    /** Creates a new instance of FilterStepTransfer */
    public FilterStepTransfer(FilterTransfer filter, String filterStepName, SelectorTransfer filterItemSelector, String description) {
        this.filter = filter;
        this.filterStepName = filterStepName;
        this.filterItemSelector = filterItemSelector;
        this.description = description;
    }
    
    public FilterTransfer getFilter() {
        return filter;
    }
    
    public void setFilter(FilterTransfer filter) {
        this.filter = filter;
    }
    
    public String getFilterStepName() {
        return filterStepName;
    }
    
    public void setFilterStepName(String filterStepName) {
        this.filterStepName = filterStepName;
    }
    
    public SelectorTransfer getFilterItemSelector() {
        return filterItemSelector;
    }
    
    public void setFilterItemSelector(SelectorTransfer filterItemSelector) {
        this.filterItemSelector = filterItemSelector;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public ListWrapper<FilterStepElementTransfer> getFilterStepElements() {
        return filterStepElements;
    }
    
    public void setFilterStepElements(ListWrapper<FilterStepElementTransfer> filterStepElements) {
        this.filterStepElements = filterStepElements;
    }
    
    public ListWrapper<FilterStepDestinationTransfer> getFilterStepDestinations() {
        return filterStepDestinations;
    }
    
    public void setFilterStepDestinations(ListWrapper<FilterStepDestinationTransfer> filterStepDestinations) {
        this.filterStepDestinations = filterStepDestinations;
    }
    
}
