// --------------------------------------------------------------------------------
// Copyright 2002-2022 Echo Three, LLC
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

import com.echothree.model.control.filter.common.transfer.FilterAdjustmentTransfer;
import com.echothree.model.control.filter.common.transfer.FilterStepElementTransfer;
import com.echothree.model.control.filter.common.transfer.FilterStepTransfer;
import com.echothree.model.control.filter.server.control.FilterControl;
import com.echothree.model.control.selector.common.transfer.SelectorTransfer;
import com.echothree.model.control.selector.server.control.SelectorControl;
import com.echothree.model.data.filter.server.entity.FilterStepElement;
import com.echothree.model.data.filter.server.entity.FilterStepElementDetail;
import com.echothree.model.data.selector.server.entity.Selector;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;

public class FilterStepElementTransferCache
        extends BaseFilterTransferCache<FilterStepElement, FilterStepElementTransfer> {

    FilterControl filterControl = Session.getModelController(FilterControl.class);

    /** Creates a new instance of FilterStepElementTransferCache */
    public FilterStepElementTransferCache(UserVisit userVisit) {
        super(userVisit);
    }

    @Override
    public FilterStepElementTransfer getTransfer(FilterStepElement filterStepElement) {
        FilterStepElementTransfer filterStepElementTransfer = get(filterStepElement);
        
        if(filterStepElementTransfer == null) {
            SelectorControl selectorControl = Session.getModelController(SelectorControl.class);
            FilterStepElementDetail filterStepElementDetail = filterStepElement.getLastDetail();
            FilterStepTransfer filterStepTransfer = filterControl.getFilterStepTransfer(userVisit, filterStepElementDetail.getFilterStep());
            String filterStepElementName = filterStepElementDetail.getFilterStepElementName();
            Selector filterItemSelector = filterStepElementDetail.getFilterItemSelector();
            SelectorTransfer filterItemSelectorTransfer = filterItemSelector == null? null: selectorControl.getSelectorTransfer(userVisit, filterItemSelector);
            FilterAdjustmentTransfer filterAdjustmentTransfer = filterControl.getFilterAdjustmentTransfer(userVisit, filterStepElementDetail.getFilterAdjustment());
            String description = filterControl.getBestFilterStepElementDescription(filterStepElement, getLanguage());
            
            filterStepElementTransfer = new FilterStepElementTransfer(filterStepTransfer, filterStepElementName,
                    filterItemSelectorTransfer, filterAdjustmentTransfer, description);
            put(filterStepElement, filterStepElementTransfer);
        }
        
        return filterStepElementTransfer;
    }
    
}
