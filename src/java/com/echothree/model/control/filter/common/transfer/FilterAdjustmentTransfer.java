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

import com.echothree.util.common.transfer.BaseTransfer;
import com.echothree.util.common.transfer.ListWrapper;

public class FilterAdjustmentTransfer
        extends BaseTransfer {
    
    private FilterKindTransfer filterKind;
    private String filterAdjustmentName;
    private FilterAdjustmentSourceTransfer filterAdjustmentSource;
    private FilterAdjustmentTypeTransfer filterAdjustmentType;
    private Boolean isDefault;
    private Integer sortOrder;
    private String description;
    private ListWrapper<FilterAdjustmentAmountTransfer> filterAdjustmentAmounts;
    private ListWrapper<FilterAdjustmentFixedAmountTransfer> filterAdjustmentFixedAmounts;
    private ListWrapper<FilterAdjustmentPercentTransfer> filterAdjustmentPercents;
    
    /** Creates a new instance of FilterAdjustmentTransfer */
    public FilterAdjustmentTransfer(FilterKindTransfer filterKind, String filterAdjustmentName,
            FilterAdjustmentSourceTransfer filterAdjustmentSource, FilterAdjustmentTypeTransfer filterAdjustmentType,
            Boolean isDefault, Integer sortOrder, String description) {
        this.filterKind = filterKind;
        this.filterAdjustmentName = filterAdjustmentName;
        this.filterAdjustmentSource = filterAdjustmentSource;
        this.filterAdjustmentType = filterAdjustmentType;
        this.isDefault = isDefault;
        this.sortOrder = sortOrder;
        this.description = description;
    }
    
    public FilterKindTransfer getFilterKind() {
        return filterKind;
    }
    
    public void setFilterKind(FilterKindTransfer filterKind) {
        this.filterKind = filterKind;
    }
    
    public String getFilterAdjustmentName() {
        return filterAdjustmentName;
    }
    
    public void setFilterAdjustmentName(String filterAdjustmentName) {
        this.filterAdjustmentName = filterAdjustmentName;
    }
    
    public FilterAdjustmentSourceTransfer getFilterAdjustmentSource() {
        return filterAdjustmentSource;
    }
    
    public void setFilterAdjustmentSource(FilterAdjustmentSourceTransfer filterAdjustmentSource) {
        this.filterAdjustmentSource = filterAdjustmentSource;
    }
    
    public FilterAdjustmentTypeTransfer getFilterAdjustmentType() {
        return filterAdjustmentType;
    }
    
    public void setFilterAdjustmentType(FilterAdjustmentTypeTransfer filterAdjustmentType) {
        this.filterAdjustmentType = filterAdjustmentType;
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
    
    public ListWrapper<FilterAdjustmentAmountTransfer> getFilterAdjustmentAmounts() {
        return filterAdjustmentAmounts;
    }
    
    public void setFilterAdjustmentAmounts(ListWrapper<FilterAdjustmentAmountTransfer> filterAdjustmentAmounts) {
        this.filterAdjustmentAmounts = filterAdjustmentAmounts;
    }
    
    public ListWrapper<FilterAdjustmentFixedAmountTransfer> getFilterAdjustmentFixedAmounts() {
        return filterAdjustmentFixedAmounts;
    }
    
    public void setFilterAdjustmentFixedAmounts(ListWrapper<FilterAdjustmentFixedAmountTransfer> filterAdjustmentFixedAmounts) {
        this.filterAdjustmentFixedAmounts = filterAdjustmentFixedAmounts;
    }
    
    public ListWrapper<FilterAdjustmentPercentTransfer> getFilterAdjustmentPercents() {
        return filterAdjustmentPercents;
    }
    
    public void setFilterAdjustmentPercents(ListWrapper<FilterAdjustmentPercentTransfer> filterAdjustmentPercents) {
        this.filterAdjustmentPercents = filterAdjustmentPercents;
    }
    
}
