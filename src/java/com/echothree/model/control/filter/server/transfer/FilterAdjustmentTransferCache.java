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
import com.echothree.model.control.filter.common.transfer.FilterAdjustmentSourceTransfer;
import com.echothree.model.control.filter.common.transfer.FilterAdjustmentTransfer;
import com.echothree.model.control.filter.common.transfer.FilterAdjustmentTypeTransfer;
import com.echothree.model.control.filter.common.transfer.FilterKindTransfer;
import com.echothree.model.control.filter.server.FilterControl;
import com.echothree.model.data.filter.server.entity.FilterAdjustment;
import com.echothree.model.data.filter.server.entity.FilterAdjustmentDetail;
import com.echothree.model.data.filter.server.entity.FilterAdjustmentType;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.common.transfer.ListWrapper;
import java.util.Set;

public class FilterAdjustmentTransferCache
        extends BaseFilterTransferCache<FilterAdjustment, FilterAdjustmentTransfer> {
    
    boolean includeFilterAdjustmentAmounts;
    boolean includeFilterAdjustmentFixedAmounts;
    boolean includeFilterAdjustmentPercents;
    
    /** Creates a new instance of FilterAdjustmentTransferCache */
    public FilterAdjustmentTransferCache(UserVisit userVisit, FilterControl filterControl) {
        super(userVisit, filterControl);
        
        Set<String> options = session.getOptions();
        if(options != null) {
            includeFilterAdjustmentAmounts = options.contains(FilterOptions.FilterAdjustmentIncludeFilterAdjustmentAmounts);
            includeFilterAdjustmentFixedAmounts = options.contains(FilterOptions.FilterAdjustmentIncludeFilterAdjustmentFixedAmounts);
            includeFilterAdjustmentPercents = options.contains(FilterOptions.FilterAdjustmentIncludeFilterAdjustmentPercents);
        }
        
        setIncludeEntityInstance(true);
    }
    
    public FilterAdjustmentTransfer getFilterAdjustmentTransfer(FilterAdjustment filterAdjustment) {
        FilterAdjustmentTransfer filterAdjustmentTransfer = get(filterAdjustment);
        
        if(filterAdjustmentTransfer == null) {
            FilterAdjustmentDetail filterAdjustmentDetail = filterAdjustment.getLastDetail();
            FilterKindTransferCache filterKindTransferCache = filterControl.getFilterTransferCaches(userVisit).getFilterKindTransferCache();
            FilterKindTransfer filterKindTransfer = filterKindTransferCache.getFilterKindTransfer(filterAdjustmentDetail.getFilterKind());
            String filterAdjustmentName = filterAdjustmentDetail.getFilterAdjustmentName();
            FilterAdjustmentSourceTransferCache filterAdjustmentSourceTransferCache = filterControl.getFilterTransferCaches(userVisit).getFilterAdjustmentSourceTransferCache();
            FilterAdjustmentSourceTransfer filterAdjustmentSourceTransfer = filterAdjustmentSourceTransferCache.getFilterAdjustmentSourceTransfer(filterAdjustmentDetail.getFilterAdjustmentSource());
            FilterAdjustmentTypeTransferCache filterAdjustmentTypeTransferCache = filterControl.getFilterTransferCaches(userVisit).getFilterAdjustmentTypeTransferCache();
            FilterAdjustmentType filterAdjustmentType = filterAdjustmentDetail.getFilterAdjustmentType();
            FilterAdjustmentTypeTransfer filterAdjustmentTypeTransfer = filterAdjustmentType == null? null: filterAdjustmentTypeTransferCache.getFilterAdjustmentTypeTransfer(filterAdjustmentType);
            Boolean isDefault = filterAdjustmentDetail.getIsDefault();
            Integer sortOrder = filterAdjustmentDetail.getSortOrder();
            String description = filterControl.getBestFilterAdjustmentDescription(filterAdjustment, getLanguage());
            
            filterAdjustmentTransfer = new FilterAdjustmentTransfer(filterKindTransfer, filterAdjustmentName,
                    filterAdjustmentSourceTransfer, filterAdjustmentTypeTransfer, isDefault, sortOrder,
                    description);
            put(filterAdjustment, filterAdjustmentTransfer);
            
            if(includeFilterAdjustmentAmounts) {
                filterAdjustmentTransfer.setFilterAdjustmentAmounts(new ListWrapper<>(filterControl.getFilterAdjustmentAmountTransfers(userVisit, filterAdjustment)));
            }
            
            if(includeFilterAdjustmentFixedAmounts) {
                filterAdjustmentTransfer.setFilterAdjustmentFixedAmounts(new ListWrapper<>(filterControl.getFilterAdjustmentFixedAmountTransfers(userVisit, filterAdjustment)));
            }
            
            if(includeFilterAdjustmentPercents) {
                filterAdjustmentTransfer.setFilterAdjustmentPercents(new ListWrapper<>(filterControl.getFilterAdjustmentPercentTransfers(userVisit, filterAdjustment)));
            }
        }
        return filterAdjustmentTransfer;
    }
    
}
