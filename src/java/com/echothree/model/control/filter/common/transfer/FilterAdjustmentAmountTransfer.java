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

package com.echothree.model.control.filter.common.transfer;

import com.echothree.model.control.accounting.common.transfer.CurrencyTransfer;
import com.echothree.model.control.uom.common.transfer.UnitOfMeasureTypeTransfer;
import com.echothree.util.common.transfer.BaseTransfer;

public class FilterAdjustmentAmountTransfer
        extends BaseTransfer {

    private FilterAdjustmentTransfer filterAdjustment;
    private UnitOfMeasureTypeTransfer unitOfMeasureType;
    private CurrencyTransfer currency;
    private Long unformattedAmount;
    private String amount;

    /** Creates a new instance of FilterAdjustmentAmountTransfer */
    public FilterAdjustmentAmountTransfer(FilterAdjustmentTransfer filterAdjustment, UnitOfMeasureTypeTransfer unitOfMeasureType, CurrencyTransfer currency,
            Long unformattedAmount, String amount) {
        this.filterAdjustment = filterAdjustment;
        this.unitOfMeasureType = unitOfMeasureType;
        this.currency = currency;
        this.unformattedAmount = unformattedAmount;
        this.amount = amount;
    }

    /**
     * @return the filterAdjustment
     */
    public FilterAdjustmentTransfer getFilterAdjustment() {
        return filterAdjustment;
    }

    /**
     * @param filterAdjustment the filterAdjustment to set
     */
    public void setFilterAdjustment(FilterAdjustmentTransfer filterAdjustment) {
        this.filterAdjustment = filterAdjustment;
    }

    /**
     * @return the unitOfMeasureType
     */
    public UnitOfMeasureTypeTransfer getUnitOfMeasureType() {
        return unitOfMeasureType;
    }

    /**
     * @param unitOfMeasureType the unitOfMeasureType to set
     */
    public void setUnitOfMeasureType(UnitOfMeasureTypeTransfer unitOfMeasureType) {
        this.unitOfMeasureType = unitOfMeasureType;
    }

    /**
     * @return the currency
     */
    public CurrencyTransfer getCurrency() {
        return currency;
    }

    /**
     * @param currency the currency to set
     */
    public void setCurrency(CurrencyTransfer currency) {
        this.currency = currency;
    }

    /**
     * @return the unformattedAmount
     */
    public Long getUnformattedAmount() {
        return unformattedAmount;
    }

    /**
     * @param unformattedAmount the unformattedAmount to set
     */
    public void setUnformattedAmount(Long unformattedAmount) {
        this.unformattedAmount = unformattedAmount;
    }

    /**
     * @return the amount
     */
    public String getAmount() {
        return amount;
    }

    /**
     * @param amount the amount to set
     */
    public void setAmount(String amount) {
        this.amount = amount;
    }

}
