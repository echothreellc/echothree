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
import com.echothree.model.control.filter.common.FilterKinds;
import com.echothree.model.control.filter.common.transfer.FilterAdjustmentFixedAmountTransfer;
import com.echothree.model.control.filter.server.control.FilterControl;
import com.echothree.model.control.uom.server.control.UomControl;
import com.echothree.model.data.filter.server.entity.FilterAdjustmentFixedAmount;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;
import com.echothree.util.server.string.AmountUtils;

public class FilterAdjustmentFixedAmountTransferCache
        extends BaseFilterTransferCache<FilterAdjustmentFixedAmount, FilterAdjustmentFixedAmountTransfer> {

    AccountingControl accountingControl = Session.getModelController(AccountingControl.class);
    FilterControl filterControl = Session.getModelController(FilterControl.class);
    UomControl uomControl = Session.getModelController(UomControl.class);

    /** Creates a new instance of FilterAdjustmentFixedAmountTransferCache */
    public FilterAdjustmentFixedAmountTransferCache() {
        super();
    }

    @Override
    public FilterAdjustmentFixedAmountTransfer getTransfer(FilterAdjustmentFixedAmount filterAdjustmentFixedAmount) {
        var filterAdjustmentFixedAmountTransfer = get(filterAdjustmentFixedAmount);
        
        if(filterAdjustmentFixedAmountTransfer == null) {
            var filterAdjustmentTransfer = filterControl.getFilterAdjustmentTransfer(userVisit, filterAdjustmentFixedAmount.getFilterAdjustment());
            var unitOfMeasureTypeTransfer = uomControl.getUnitOfMeasureTypeTransfer(userVisit, filterAdjustmentFixedAmount.getUnitOfMeasureType());
            var currency = filterAdjustmentFixedAmount.getCurrency();
            var currencyTransfer = accountingControl.getCurrencyTransfer(userVisit, currency);
            var unformattedUnitAmount = filterAdjustmentFixedAmount.getUnitAmount();
            String unitAmount = null;

            var filterKindName = filterAdjustmentTransfer.getFilterKind().getFilterKindName();
            if(FilterKinds.COST.name().equals(filterKindName)) {
                unitAmount = AmountUtils.getInstance().formatCostUnit(currency, unformattedUnitAmount);
            } else if(FilterKinds.PRICE.name().equals(filterKindName)) {
                unitAmount = AmountUtils.getInstance().formatPriceUnit(currency, unformattedUnitAmount);
            }
            
            filterAdjustmentFixedAmountTransfer = new FilterAdjustmentFixedAmountTransfer(filterAdjustmentTransfer, unitOfMeasureTypeTransfer, currencyTransfer,
                    unformattedUnitAmount, unitAmount);
            
            put(userVisit, filterAdjustmentFixedAmount, filterAdjustmentFixedAmountTransfer);
        }
        
        return filterAdjustmentFixedAmountTransfer;
    }
    
}
