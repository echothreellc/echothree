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

public class FilterTransfer
        extends BaseTransfer {
    
    private FilterTypeTransfer filterType;
    private String filterName;
    private FilterAdjustmentTransfer initialFilterAdjustment;
    private SelectorTransfer filterItemSelector;
    private Boolean isDefault;
    private Integer sortOrder;
    private String description;
    private ListWrapper<FilterEntranceStepTransfer> filterEntranceSteps;
    private ListWrapper<FilterStepTransfer> filterSteps;
    
    /** Creates a new instance of FilterTransfer */
    public FilterTransfer(FilterTypeTransfer filterType, String filterName, FilterAdjustmentTransfer initialFilterAdjustment,
            SelectorTransfer filterItemSelector, Boolean isDefault, Integer sortOrder,
            String description) {
        this.filterType = filterType;
        this.filterName = filterName;
        this.initialFilterAdjustment = initialFilterAdjustment;
        this.filterItemSelector = filterItemSelector;
        this.isDefault = isDefault;
        this.sortOrder = sortOrder;
        this.description = description;
    }
    
    public FilterTypeTransfer getFilterType() {
        return filterType;
    }
    
    public void setFilterType(FilterTypeTransfer filterType) {
        this.filterType = filterType;
    }
    
    public String getFilterName() {
        return filterName;
    }
    
    public void setFilterName(String filterName) {
        this.filterName = filterName;
    }
    
    public FilterAdjustmentTransfer getInitialFilterAdjustment() {
        return initialFilterAdjustment;
    }
    
    public void setInitialFilterAdjustment(FilterAdjustmentTransfer initialFilterAdjustment) {
        this.initialFilterAdjustment = initialFilterAdjustment;
    }
    
    public SelectorTransfer getFilterItemSelector() {
        return filterItemSelector;
    }
    
    public void setFilterItemSelector(SelectorTransfer filterItemSelector) {
        this.filterItemSelector = filterItemSelector;
    }
    
    public Boolean getIsDefault() {
        return isDefault;
    }
    
    public void setIsDefault(Boolean isDefault) {
        this.isDefault = isDefault;
    }
    
    public Integer getSortOrder() {
        return sortOrder;
    }
    
    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public ListWrapper<FilterEntranceStepTransfer> getFilterEntranceSteps() {
        return filterEntranceSteps;
    }
    
    public void setFilterEntranceSteps(ListWrapper<FilterEntranceStepTransfer> filterEntranceSteps) {
        this.filterEntranceSteps = filterEntranceSteps;
    }
    
    public ListWrapper<FilterStepTransfer> getFilterSteps() {
        return filterSteps;
    }
    
    public void setFilterSteps(ListWrapper<FilterStepTransfer> filterSteps) {
        this.filterSteps = filterSteps;
    }
    
}
