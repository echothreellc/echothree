// --------------------------------------------------------------------------------
// Copyright 2002-2019 Echo Three, LLC
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
import com.echothree.model.control.accounting.server.AccountingControl;
import com.echothree.model.control.accounting.server.transfer.CurrencyTransferCache;
import com.echothree.model.control.filter.common.transfer.FilterAdjustmentPercentTransfer;
import com.echothree.model.control.filter.common.transfer.FilterAdjustmentTransfer;
import com.echothree.model.control.filter.server.FilterControl;
import com.echothree.model.control.uom.common.transfer.UnitOfMeasureTypeTransfer;
import com.echothree.model.control.uom.server.UomControl;
import com.echothree.model.control.uom.server.transfer.UnitOfMeasureTypeTransferCache;
import com.echothree.model.data.filter.server.entity.FilterAdjustmentPercent;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;
import com.echothree.util.server.string.PercentUtils;

public class FilterAdjustmentPercentTransferCache
        extends BaseFilterTransferCache<FilterAdjustmentPercent, FilterAdjustmentPercentTransfer> {
    
    /** Creates a new instance of FilterAdjustmentPercentTransferCache */
    public FilterAdjustmentPercentTransferCache(UserVisit userVisit, FilterControl filterControl) {
        super(userVisit, filterControl);
    }
    
    public FilterAdjustmentPercentTransfer getFilterAdjustmentPercentTransfer(FilterAdjustmentPercent filterAdjustmentPercent) {
        FilterAdjustmentPercentTransfer filterAdjustmentPercentTransfer = get(filterAdjustmentPercent);
        
        if(filterAdjustmentPercentTransfer == null) {
            AccountingControl accountingControl = (AccountingControl)Session.getModelController(AccountingControl.class);
            UomControl uomControl = (UomControl)Session.getModelController(UomControl.class);
            FilterAdjustmentTransferCache filterAdjustmentTransferCache = filterControl.getFilterTransferCaches(userVisit).getFilterAdjustmentTransferCache();
            FilterAdjustmentTransfer filterAdjustment = filterAdjustmentTransferCache.getFilterAdjustmentTransfer(filterAdjustmentPercent.getFilterAdjustment());
            UnitOfMeasureTypeTransferCache unitOfMeasureTypeTransferCache = uomControl.getUomTransferCaches(userVisit).getUnitOfMeasureTypeTransferCache();
            UnitOfMeasureTypeTransfer unitOfMeasureType = unitOfMeasureTypeTransferCache.getUnitOfMeasureTypeTransfer(filterAdjustmentPercent.getUnitOfMeasureType());
            CurrencyTransferCache currencyTransferCache = accountingControl.getAccountingTransferCaches(userVisit).getCurrencyTransferCache();
            CurrencyTransfer currency = currencyTransferCache.getTransfer(filterAdjustmentPercent.getCurrency());
            String percent = PercentUtils.getInstance().formatFractionalPercent(filterAdjustmentPercent.getPercent());
            
            filterAdjustmentPercentTransfer = new FilterAdjustmentPercentTransfer(filterAdjustment, unitOfMeasureType, currency, percent);
            
            put(filterAdjustmentPercent, filterAdjustmentPercentTransfer);
        }
        
        return filterAdjustmentPercentTransfer;
    }
    
}
