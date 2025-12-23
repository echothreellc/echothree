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

public class FilterAdjustmentFixedAmountTransfer
        extends BaseTransfer {

    private FilterAdjustmentTransfer filterAdjustment;
    private UnitOfMeasureTypeTransfer unitOfMeasureType;
    private CurrencyTransfer currency;
    private Long unformattedUnitAmount;
    private String unitAmount;

    /** Creates a new instance of FilterAdjustmentFixedAmountTransfer */
    public FilterAdjustmentFixedAmountTransfer(FilterAdjustmentTransfer filterAdjustment, UnitOfMeasureTypeTransfer unitOfMeasureType,
            CurrencyTransfer currency, Long unformattedUnitAmount, String unitAmount) {
        this.filterAdjustment = filterAdjustment;
        this.unitOfMeasureType = unitOfMeasureType;
        this.currency = currency;
        this.unformattedUnitAmount = unformattedUnitAmount;
        this.unitAmount = unitAmount;
    }

    /**
     * Returns the filterAdjustment.
     * @return the filterAdjustment
     */
    public FilterAdjustmentTransfer getFilterAdjustment() {
        return filterAdjustment;
    }

    /**
     * Sets the filterAdjustment.
     * @param filterAdjustment the filterAdjustment to set
     */
    public void setFilterAdjustment(FilterAdjustmentTransfer filterAdjustment) {
        this.filterAdjustment = filterAdjustment;
    }

    /**
     * Returns the unitOfMeasureType.
     * @return the unitOfMeasureType
     */
    public UnitOfMeasureTypeTransfer getUnitOfMeasureType() {
        return unitOfMeasureType;
    }

    /**
     * Sets the unitOfMeasureType.
     * @param unitOfMeasureType the unitOfMeasureType to set
     */
    public void setUnitOfMeasureType(UnitOfMeasureTypeTransfer unitOfMeasureType) {
        this.unitOfMeasureType = unitOfMeasureType;
    }

    /**
     * Returns the currency.
     * @return the currency
     */
    public CurrencyTransfer getCurrency() {
        return currency;
    }

    /**
     * Sets the currency.
     * @param currency the currency to set
     */
    public void setCurrency(CurrencyTransfer currency) {
        this.currency = currency;
    }

    /**
     * Returns the unformattedUnitAmount.
     * @return the unformattedUnitAmount
     */
    public Long getUnformattedUnitAmount() {
        return unformattedUnitAmount;
    }

    /**
     * Sets the unformattedUnitAmount.
     * @param unformattedUnitAmount the unformattedUnitAmount to set
     */
    public void setUnformattedUnitAmount(Long unformattedUnitAmount) {
        this.unformattedUnitAmount = unformattedUnitAmount;
    }

    /**
     * Returns the unitAmount.
     * @return the unitAmount
     */
    public String getUnitAmount() {
        return unitAmount;
    }

    /**
     * Sets the unitAmount.
     * @param unitAmount the unitAmount to set
     */
    public void setUnitAmount(String unitAmount) {
        this.unitAmount = unitAmount;
    }

}
