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

import com.echothree.model.control.filter.common.transfer.FilterStepElementTransfer;
import com.echothree.model.control.filter.server.control.FilterControl;
import com.echothree.model.control.selector.server.control.SelectorControl;
import com.echothree.model.data.filter.server.entity.FilterStepElement;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class FilterStepElementTransferCache
        extends BaseFilterTransferCache<FilterStepElement, FilterStepElementTransfer> {

    FilterControl filterControl = Session.getModelController(FilterControl.class);

    /** Creates a new instance of FilterStepElementTransferCache */
    public FilterStepElementTransferCache() {
        super();
    }

    @Override
    public FilterStepElementTransfer getTransfer(UserVisit userVisit, FilterStepElement filterStepElement) {
        var filterStepElementTransfer = get(filterStepElement);
        
        if(filterStepElementTransfer == null) {
            var selectorControl = Session.getModelController(SelectorControl.class);
            var filterStepElementDetail = filterStepElement.getLastDetail();
            var filterStepTransfer = filterControl.getFilterStepTransfer(userVisit, filterStepElementDetail.getFilterStep());
            var filterStepElementName = filterStepElementDetail.getFilterStepElementName();
            var filterItemSelector = filterStepElementDetail.getFilterItemSelector();
            var filterItemSelectorTransfer = filterItemSelector == null? null: selectorControl.getSelectorTransfer(userVisit, filterItemSelector);
            var filterAdjustmentTransfer = filterControl.getFilterAdjustmentTransfer(userVisit, filterStepElementDetail.getFilterAdjustment());
            var description = filterControl.getBestFilterStepElementDescription(filterStepElement, getLanguage(userVisit));
            
            filterStepElementTransfer = new FilterStepElementTransfer(filterStepTransfer, filterStepElementName,
                    filterItemSelectorTransfer, filterAdjustmentTransfer, description);
            put(userVisit, filterStepElement, filterStepElementTransfer);
        }
        
        return filterStepElementTransfer;
    }
    
}
