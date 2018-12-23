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

package com.echothree.model.control.accounting.common.transfer;

import com.echothree.util.common.transfer.BaseTransfer;

public class CurrencyTransfer
        extends BaseTransfer {
    
    private String currencyIsoName;
    private String symbol;
    private SymbolPositionTransfer symbolPosition;
    private Boolean symbolOnListStart;
    private Boolean symbolOnListMember;
    private Boolean symbolOnSubtotal;
    private Boolean symbolOnTotal;
    private String groupingSeparator;
    private Integer groupingSize;
    private String fractionSeparator;
    private Integer defaultFractionDigits;
    private Integer priceUnitFractionDigits;
    private Integer priceLineFractionDigits;
    private Integer costUnitFractionDigits;
    private Integer costLineFractionDigits;
    private String minusSign;
    private Boolean isDefault;
    private Integer sortOrder;
    private String description;
    
    /** Creates a new instance of CurrencyTransfer */
    public CurrencyTransfer(String currencyIsoName, String symbol, SymbolPositionTransfer symbolPosition, Boolean symbolOnListStart,
            Boolean symbolOnListMember, Boolean symbolOnSubtotal, Boolean symbolOnTotal, String groupingSeparator, Integer groupingSize,
            String fractionSeparator, Integer defaultFractionDigits, Integer priceUnitFractionDigits, Integer priceLineFractionDigits,
            Integer costUnitFractionDigits, Integer costLineFractionDigits, String minusSign, Boolean isDefault, Integer sortOrder,
            String description) {
        this.currencyIsoName = currencyIsoName;
        this.symbol = symbol;
        this.symbolPosition = symbolPosition;
        this.symbolOnListStart = symbolOnListStart;
        this.symbolOnListMember = symbolOnListMember;
        this.symbolOnSubtotal = symbolOnSubtotal;
        this.symbolOnTotal = symbolOnTotal;
        this.groupingSeparator = groupingSeparator;
        this.groupingSize = groupingSize;
        this.fractionSeparator = fractionSeparator;
        this.defaultFractionDigits = defaultFractionDigits;
        this.priceUnitFractionDigits = priceUnitFractionDigits;
        this.priceLineFractionDigits = priceLineFractionDigits;
        this.costUnitFractionDigits = costUnitFractionDigits;
        this.costLineFractionDigits = costLineFractionDigits;
        this.minusSign = minusSign;
        this.isDefault = isDefault;
        this.sortOrder = sortOrder;
        this.description = description;
    }

    /**
     * @return the currencyIsoName
     */
    public String getCurrencyIsoName() {
        return currencyIsoName;
    }

    /**
     * @param currencyIsoName the currencyIsoName to set
     */
    public void setCurrencyIsoName(String currencyIsoName) {
        this.currencyIsoName = currencyIsoName;
    }

    /**
     * @return the symbol
     */
    public String getSymbol() {
        return symbol;
    }

    /**
     * @param symbol the symbol to set
     */
    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    /**
     * @return the symbolPosition
     */
    public SymbolPositionTransfer getSymbolPosition() {
        return symbolPosition;
    }

    /**
     * @param symbolPosition the symbolPosition to set
     */
    public void setSymbolPosition(SymbolPositionTransfer symbolPosition) {
        this.symbolPosition = symbolPosition;
    }

    /**
     * @return the symbolOnListStart
     */
    public Boolean getSymbolOnListStart() {
        return symbolOnListStart;
    }

    /**
     * @param symbolOnListStart the symbolOnListStart to set
     */
    public void setSymbolOnListStart(Boolean symbolOnListStart) {
        this.symbolOnListStart = symbolOnListStart;
    }

    /**
     * @return the symbolOnListMember
     */
    public Boolean getSymbolOnListMember() {
        return symbolOnListMember;
    }

    /**
     * @param symbolOnListMember the symbolOnListMember to set
     */
    public void setSymbolOnListMember(Boolean symbolOnListMember) {
        this.symbolOnListMember = symbolOnListMember;
    }

    /**
     * @return the symbolOnSubtotal
     */
    public Boolean getSymbolOnSubtotal() {
        return symbolOnSubtotal;
    }

    /**
     * @param symbolOnSubtotal the symbolOnSubtotal to set
     */
    public void setSymbolOnSubtotal(Boolean symbolOnSubtotal) {
        this.symbolOnSubtotal = symbolOnSubtotal;
    }

    /**
     * @return the symbolOnTotal
     */
    public Boolean getSymbolOnTotal() {
        return symbolOnTotal;
    }

    /**
     * @param symbolOnTotal the symbolOnTotal to set
     */
    public void setSymbolOnTotal(Boolean symbolOnTotal) {
        this.symbolOnTotal = symbolOnTotal;
    }

    /**
     * @return the groupingSeparator
     */
    public String getGroupingSeparator() {
        return groupingSeparator;
    }

    /**
     * @param groupingSeparator the groupingSeparator to set
     */
    public void setGroupingSeparator(String groupingSeparator) {
        this.groupingSeparator = groupingSeparator;
    }

    /**
     * @return the groupingSize
     */
    public Integer getGroupingSize() {
        return groupingSize;
    }

    /**
     * @param groupingSize the groupingSize to set
     */
    public void setGroupingSize(Integer groupingSize) {
        this.groupingSize = groupingSize;
    }

    /**
     * @return the fractionSeparator
     */
    public String getFractionSeparator() {
        return fractionSeparator;
    }

    /**
     * @param fractionSeparator the fractionSeparator to set
     */
    public void setFractionSeparator(String fractionSeparator) {
        this.fractionSeparator = fractionSeparator;
    }

    /**
     * @return the defaultFractionDigits
     */
    public Integer getDefaultFractionDigits() {
        return defaultFractionDigits;
    }

    /**
     * @param defaultFractionDigits the defaultFractionDigits to set
     */
    public void setDefaultFractionDigits(Integer defaultFractionDigits) {
        this.defaultFractionDigits = defaultFractionDigits;
    }

    /**
     * @return the priceUnitFractionDigits
     */
    public Integer getPriceUnitFractionDigits() {
        return priceUnitFractionDigits;
    }

    /**
     * @param priceUnitFractionDigits the priceUnitFractionDigits to set
     */
    public void setPriceUnitFractionDigits(Integer priceUnitFractionDigits) {
        this.priceUnitFractionDigits = priceUnitFractionDigits;
    }

    /**
     * @return the priceLineFractionDigits
     */
    public Integer getPriceLineFractionDigits() {
        return priceLineFractionDigits;
    }

    /**
     * @param priceLineFractionDigits the priceLineFractionDigits to set
     */
    public void setPriceLineFractionDigits(Integer priceLineFractionDigits) {
        this.priceLineFractionDigits = priceLineFractionDigits;
    }

    /**
     * @return the costUnitFractionDigits
     */
    public Integer getCostUnitFractionDigits() {
        return costUnitFractionDigits;
    }

    /**
     * @param costUnitFractionDigits the costUnitFractionDigits to set
     */
    public void setCostUnitFractionDigits(Integer costUnitFractionDigits) {
        this.costUnitFractionDigits = costUnitFractionDigits;
    }

    /**
     * @return the costLineFractionDigits
     */
    public Integer getCostLineFractionDigits() {
        return costLineFractionDigits;
    }

    /**
     * @param costLineFractionDigits the costLineFractionDigits to set
     */
    public void setCostLineFractionDigits(Integer costLineFractionDigits) {
        this.costLineFractionDigits = costLineFractionDigits;
    }

    /**
     * @return the minusSign
     */
    public String getMinusSign() {
        return minusSign;
    }

    /**
     * @param minusSign the minusSign to set
     */
    public void setMinusSign(String minusSign) {
        this.minusSign = minusSign;
    }

    /**
     * @return the isDefault
     */
    public Boolean getIsDefault() {
        return isDefault;
    }

    /**
     * @param isDefault the isDefault to set
     */
    public void setIsDefault(Boolean isDefault) {
        this.isDefault = isDefault;
    }

    /**
     * @return the sortOrder
     */
    public Integer getSortOrder() {
        return sortOrder;
    }

    /**
     * @param sortOrder the sortOrder to set
     */
    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }
    
}
