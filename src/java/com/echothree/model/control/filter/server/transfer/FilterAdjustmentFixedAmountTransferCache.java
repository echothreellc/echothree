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
import com.echothree.model.control.filter.common.transfer.FilterAdjustmentFixedAmountTransfer;
import com.echothree.model.control.filter.common.transfer.FilterAdjustmentTransfer;
import com.echothree.model.control.filter.server.control.FilterControl;
import com.echothree.model.control.uom.common.transfer.UnitOfMeasureTypeTransfer;
import com.echothree.model.control.uom.server.control.UomControl;
import com.echothree.model.data.accounting.server.entity.Currency;
import com.echothree.model.data.filter.server.entity.FilterAdjustmentFixedAmount;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;
import com.echothree.util.server.string.AmountUtils;

public class FilterAdjustmentFixedAmountTransferCache
        extends BaseFilterTransferCache<FilterAdjustmentFixedAmount, FilterAdjustmentFixedAmountTransfer> {
    
    /** Creates a new instance of FilterAdjustmentFixedAmountTransferCache */
    public FilterAdjustmentFixedAmountTransferCache(UserVisit userVisit, FilterControl filterControl) {
        super(userVisit, filterControl);
    }
    
    public FilterAdjustmentFixedAmountTransfer getFilterAdjustmentFixedAmountTransfer(FilterAdjustmentFixedAmount filterAdjustmentFixedAmount) {
        FilterAdjustmentFixedAmountTransfer filterAdjustmentFixedAmountTransfer = get(filterAdjustmentFixedAmount);
        
        if(filterAdjustmentFixedAmountTransfer == null) {
            AccountingControl accountingControl = (AccountingControl)Session.getModelController(AccountingControl.class);
            UomControl uomControl = (UomControl)Session.getModelController(UomControl.class);
            FilterAdjustmentTransfer filterAdjustmentTransfer = filterControl.getFilterAdjustmentTransfer(userVisit, filterAdjustmentFixedAmount.getFilterAdjustment());
            UnitOfMeasureTypeTransfer unitOfMeasureTypeTransfer = uomControl.getUnitOfMeasureTypeTransfer(userVisit, filterAdjustmentFixedAmount.getUnitOfMeasureType());
            Currency currency = filterAdjustmentFixedAmount.getCurrency();
            CurrencyTransfer currencyTransfer = accountingControl.getCurrencyTransfer(userVisit, currency);
            Long unformattedUnitAmount = filterAdjustmentFixedAmount.getUnitAmount();
            String unitAmount = null;
            
            String filterKindName = filterAdjustmentTransfer.getFilterKind().getFilterKindName();
            if(FilterConstants.FilterKind_COST.equals(filterKindName)) {
                unitAmount = AmountUtils.getInstance().formatCostUnit(currency, unformattedUnitAmount);
            } else if(FilterConstants.FilterKind_PRICE.equals(filterKindName)) {
                unitAmount = AmountUtils.getInstance().formatPriceUnit(currency, unformattedUnitAmount);
            }
            
            filterAdjustmentFixedAmountTransfer = new FilterAdjustmentFixedAmountTransfer(filterAdjustmentTransfer, unitOfMeasureTypeTransfer, currencyTransfer,
                    unformattedUnitAmount, unitAmount);
            
            put(filterAdjustmentFixedAmount, filterAdjustmentFixedAmountTransfer);
        }
        
        return filterAdjustmentFixedAmountTransfer;
    }
    
}
