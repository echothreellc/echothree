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

import com.echothree.model.control.accounting.server.control.AccountingControl;
import com.echothree.model.control.filter.common.transfer.FilterAdjustmentPercentTransfer;
import com.echothree.model.control.filter.server.control.FilterControl;
import com.echothree.model.control.uom.server.control.UomControl;
import com.echothree.model.data.filter.server.entity.FilterAdjustmentPercent;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;
import com.echothree.util.server.string.PercentUtils;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class FilterAdjustmentPercentTransferCache
        extends BaseFilterTransferCache<FilterAdjustmentPercent, FilterAdjustmentPercentTransfer> {

    AccountingControl accountingControl = Session.getModelController(AccountingControl.class);
    FilterControl filterControl = Session.getModelController(FilterControl.class);
    UomControl uomControl = Session.getModelController(UomControl.class);

    /** Creates a new instance of FilterAdjustmentPercentTransferCache */
    public FilterAdjustmentPercentTransferCache() {
        super();
    }

    @Override
    public FilterAdjustmentPercentTransfer getTransfer(UserVisit userVisit, FilterAdjustmentPercent filterAdjustmentPercent) {
        var filterAdjustmentPercentTransfer = get(filterAdjustmentPercent);
        
        if(filterAdjustmentPercentTransfer == null) {
            var filterAdjustment = filterControl.getFilterAdjustmentTransfer(userVisit, filterAdjustmentPercent.getFilterAdjustment());
            var unitOfMeasureType = uomControl.getUnitOfMeasureTypeTransfer(userVisit, filterAdjustmentPercent.getUnitOfMeasureType());
            var currency = accountingControl.getCurrencyTransfer(userVisit, filterAdjustmentPercent.getCurrency());
            var percent = PercentUtils.getInstance().formatFractionalPercent(filterAdjustmentPercent.getPercent());
            
            filterAdjustmentPercentTransfer = new FilterAdjustmentPercentTransfer(filterAdjustment, unitOfMeasureType, currency, percent);
            
            put(userVisit, filterAdjustmentPercent, filterAdjustmentPercentTransfer);
        }
        
        return filterAdjustmentPercentTransfer;
    }
    
}
