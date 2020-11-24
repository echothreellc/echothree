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

import com.echothree.model.control.accounting.common.transfer.CurrencyTransfer;
import com.echothree.model.control.accounting.server.control.AccountingControl;
import com.echothree.model.control.filter.common.FilterConstants;
import com.echothree.model.control.filter.common.transfer.FilterAdjustmentAmountTransfer;
import com.echothree.model.control.filter.common.transfer.FilterAdjustmentTransfer;
import com.echothree.model.control.filter.server.control.FilterControl;
import com.echothree.model.control.uom.common.transfer.UnitOfMeasureTypeTransfer;
import com.echothree.model.control.uom.server.control.UomControl;
import com.echothree.model.data.accounting.server.entity.Currency;
import com.echothree.model.data.filter.server.entity.FilterAdjustmentAmount;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;
import com.echothree.util.server.string.AmountUtils;

public class FilterAdjustmentAmountTransferCache
        extends BaseFilterTransferCache<FilterAdjustmentAmount, FilterAdjustmentAmountTransfer> {
    
    /** Creates a new instance of FilterAdjustmentAmountTransferCache */
    public FilterAdjustmentAmountTransferCache(UserVisit userVisit, FilterControl filterControl) {
        super(userVisit, filterControl);
    }
    
    public FilterAdjustmentAmountTransfer getFilterAdjustmentAmountTransfer(FilterAdjustmentAmount filterAdjustmentAmount) {
        FilterAdjustmentAmountTransfer filterAdjustmentAmountTransfer = get(filterAdjustmentAmount);
        
        if(filterAdjustmentAmountTransfer == null) {
            AccountingControl accountingControl = Session.getModelController(AccountingControl.class);
            UomControl uomControl = Session.getModelController(UomControl.class);
            FilterAdjustmentTransfer filterAdjustmentTransfer = filterControl.getFilterAdjustmentTransfer(userVisit, filterAdjustmentAmount.getFilterAdjustment());
            UnitOfMeasureTypeTransfer unitOfMeasureTypeTransfer = uomControl.getUnitOfMeasureTypeTransfer(userVisit, filterAdjustmentAmount.getUnitOfMeasureType());
            Currency currency = filterAdjustmentAmount.getCurrency();
            CurrencyTransfer currencyTransfer = accountingControl.getCurrencyTransfer(userVisit, currency);
            Long unformattedAmount = filterAdjustmentAmount.getAmount();
            String amount = null;
            
            String filterKindName = filterAdjustmentTransfer.getFilterKind().getFilterKindName();
            if(FilterConstants.FilterKind_COST.equals(filterKindName)) {
                amount = AmountUtils.getInstance().formatCostUnit(currency, unformattedAmount);
            } else if(FilterConstants.FilterKind_PRICE.equals(filterKindName)) {
                amount = AmountUtils.getInstance().formatPriceUnit(currency, unformattedAmount);
            }
            
            filterAdjustmentAmountTransfer = new FilterAdjustmentAmountTransfer(filterAdjustmentTransfer, unitOfMeasureTypeTransfer, currencyTransfer,
                    unformattedAmount, amount);
            
            put(filterAdjustmentAmount, filterAdjustmentAmountTransfer);
        }
        
        return filterAdjustmentAmountTransfer;
    }
    
}
