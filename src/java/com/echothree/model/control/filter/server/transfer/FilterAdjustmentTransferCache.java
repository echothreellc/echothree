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

package com.echothree.model.control.filter.server.transfer;

import com.echothree.model.control.filter.common.FilterOptions;
import com.echothree.model.control.filter.common.transfer.FilterAdjustmentTransfer;
import com.echothree.model.control.filter.server.control.FilterControl;
import com.echothree.model.data.filter.server.entity.FilterAdjustment;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.common.transfer.ListWrapper;
import com.echothree.util.server.persistence.Session;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class FilterAdjustmentTransferCache
        extends BaseFilterTransferCache<FilterAdjustment, FilterAdjustmentTransfer> {

    FilterControl filterControl = Session.getModelController(FilterControl.class);

    boolean includeFilterAdjustmentAmounts;
    boolean includeFilterAdjustmentFixedAmounts;
    boolean includeFilterAdjustmentPercents;
    
    /** Creates a new instance of FilterAdjustmentTransferCache */
    protected FilterAdjustmentTransferCache() {
        super();
        
        var options = session.getOptions();
        if(options != null) {
            includeFilterAdjustmentAmounts = options.contains(FilterOptions.FilterAdjustmentIncludeFilterAdjustmentAmounts);
            includeFilterAdjustmentFixedAmounts = options.contains(FilterOptions.FilterAdjustmentIncludeFilterAdjustmentFixedAmounts);
            includeFilterAdjustmentPercents = options.contains(FilterOptions.FilterAdjustmentIncludeFilterAdjustmentPercents);
        }
        
        setIncludeEntityInstance(true);
    }

    @Override
    public FilterAdjustmentTransfer getTransfer(UserVisit userVisit, FilterAdjustment filterAdjustment) {
        var filterAdjustmentTransfer = get(filterAdjustment);
        
        if(filterAdjustmentTransfer == null) {
            var filterAdjustmentDetail = filterAdjustment.getLastDetail();
            var filterKindTransfer = filterControl.getFilterKindTransfer(userVisit, filterAdjustmentDetail.getFilterKind());
            var filterAdjustmentName = filterAdjustmentDetail.getFilterAdjustmentName();
            var filterAdjustmentSourceTransfer = filterControl.getFilterAdjustmentSourceTransfer(userVisit, filterAdjustmentDetail.getFilterAdjustmentSource());
            var filterAdjustmentType = filterAdjustmentDetail.getFilterAdjustmentType();
            var filterAdjustmentTypeTransfer = filterAdjustmentType == null ? null : filterControl.getFilterAdjustmentTypeTransfer(userVisit, filterAdjustmentType);
            var isDefault = filterAdjustmentDetail.getIsDefault();
            var sortOrder = filterAdjustmentDetail.getSortOrder();
            var description = filterControl.getBestFilterAdjustmentDescription(filterAdjustment, getLanguage(userVisit));
            
            filterAdjustmentTransfer = new FilterAdjustmentTransfer(filterKindTransfer, filterAdjustmentName,
                    filterAdjustmentSourceTransfer, filterAdjustmentTypeTransfer, isDefault, sortOrder,
                    description);
            put(userVisit, filterAdjustment, filterAdjustmentTransfer);
            
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
