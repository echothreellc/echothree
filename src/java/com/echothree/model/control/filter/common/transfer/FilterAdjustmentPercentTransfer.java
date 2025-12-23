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

import com.echothree.model.control.accounting.common.transfer.CurrencyTransfer;
import com.echothree.model.control.uom.common.transfer.UnitOfMeasureTypeTransfer;
import com.echothree.util.common.transfer.BaseTransfer;

public class FilterAdjustmentPercentTransfer
        extends BaseTransfer {
    
    private FilterAdjustmentTransfer filterAdjustment;
    private UnitOfMeasureTypeTransfer unitOfMeasureType;
    private CurrencyTransfer currency;
    private String percent;
    
    /** Creates a new instance of FilterAdjustmentPercentTransfer */
    public FilterAdjustmentPercentTransfer(FilterAdjustmentTransfer filterAdjustment, UnitOfMeasureTypeTransfer unitOfMeasureType,
    CurrencyTransfer currency, String percent) {
        this.filterAdjustment = filterAdjustment;
        this.unitOfMeasureType = unitOfMeasureType;
        this.currency = currency;
        this.percent = percent;
    }
    
    public FilterAdjustmentTransfer getFilterAdjustment() {
        return filterAdjustment;
    }
    
    public void setFilterAdjustment(FilterAdjustmentTransfer filterAdjustment) {
        this.filterAdjustment = filterAdjustment;
    }
    
    public UnitOfMeasureTypeTransfer getUnitOfMeasureType() {
        return unitOfMeasureType;
    }
    
    public void setUnitOfMeasureType(UnitOfMeasureTypeTransfer unitOfMeasureType) {
        this.unitOfMeasureType = unitOfMeasureType;
    }
    
    public CurrencyTransfer getCurrency() {
        return currency;
    }
    
    public void setCurrency(CurrencyTransfer currency) {
        this.currency = currency;
    }
    
    public String getPercent() {
        return percent;
    }
    
    public void setPercent(String percent) {
        this.percent = percent;
    }
    
}
