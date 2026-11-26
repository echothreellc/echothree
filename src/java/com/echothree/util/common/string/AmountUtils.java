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

package com.echothree.util.common.string;

import com.echothree.model.control.accounting.common.transfer.CurrencyTransfer;

public class AmountUtils {

    private AmountUtils() {
        super();
    }

    private static class AmountUtilsHolder {
        static AmountUtils instance = new AmountUtils();
    }

    public static AmountUtils getInstance() {
        return AmountUtilsHolder.instance;
    }

    /**
     * Converts integral wrapper values without narrowing; rejects other Number implementations rather than truncating.
     */
    private Long toLong(Number amount) {
        Long result = null;

        if(amount != null) {
            if(amount instanceof Byte || amount instanceof Short || amount instanceof Integer || amount instanceof Long) {
                result = amount.longValue();
            } else {
                throw new DecimalUtils.ParseException(DecimalUtils.ParseError.INVALID_FORMAT);
            }
        }

        return result;
    }

    /** Retains the Integer entry point; delegates to the exact integral-value formatter. */
    public String formatAmount(CurrencyTransfer currency, Integer amount) {
        return formatAmount(currency, (Number)amount);
    }

    /**
     * Formats a scaled integral value with the currency's defaultFractionDigits precision.
     *
     * @param currency currency format settings; unused when the value is null
     * @param amount scaled Byte, Short, Integer, or Long value, or null
     * @return exact plain decimal text, or null for an absent value
     * @throws DecimalUtils.ParseException if the value has an unsupported numeric type or its format settings are invalid
     */
    public String formatAmount(CurrencyTransfer currency, Number amount) {
        return amount == null ? null : DecimalUtils.getInstance().format(currency.getMinusSign(), currency.getFractionSeparator(),
                currency.getDefaultFractionDigits(), toLong(amount));
    }

    /** Retains the Integer entry point; delegates to the exact integral-value formatter. */
    public String formatPriceUnit(CurrencyTransfer currency, Integer priceUnit) {
        return formatPriceUnit(currency, (Number)priceUnit);
    }

    /**
     * Formats a scaled integral value with the currency's priceUnitFractionDigits precision.
     *
     * @param currency currency format settings; unused when the value is null
     * @param priceUnit scaled Byte, Short, Integer, or Long value, or null
     * @return exact plain decimal text, or null for an absent value
     * @throws DecimalUtils.ParseException if the value has an unsupported numeric type or its format settings are invalid
     */
    public String formatPriceUnit(CurrencyTransfer currency, Number priceUnit) {
        return priceUnit == null ? null : DecimalUtils.getInstance().format(currency.getMinusSign(), currency.getFractionSeparator(),
                currency.getPriceUnitFractionDigits(), toLong(priceUnit));
    }

    /** Retains the Integer entry point; delegates to the exact integral-value formatter. */
    public String formatPriceLine(CurrencyTransfer currency, Integer priceLine) {
        return formatPriceLine(currency, (Number)priceLine);
    }

    /**
     * Formats a scaled integral value with the currency's priceLineFractionDigits precision.
     *
     * @param currency currency format settings; unused when the value is null
     * @param priceLine scaled Byte, Short, Integer, or Long value, or null
     * @return exact plain decimal text, or null for an absent value
     * @throws DecimalUtils.ParseException if the value has an unsupported numeric type or its format settings are invalid
     */
    public String formatPriceLine(CurrencyTransfer currency, Number priceLine) {
        return priceLine == null ? null : DecimalUtils.getInstance().format(currency.getMinusSign(), currency.getFractionSeparator(),
                currency.getPriceLineFractionDigits(), toLong(priceLine));
    }

    /** Retains the Integer entry point; delegates to the exact integral-value formatter. */
    public String formatCostUnit(CurrencyTransfer currency, Integer costUnit) {
        return formatCostUnit(currency, (Number)costUnit);
    }

    /**
     * Formats a scaled integral value with the currency's costUnitFractionDigits precision.
     *
     * @param currency currency format settings; unused when the value is null
     * @param costUnit scaled Byte, Short, Integer, or Long value, or null
     * @return exact plain decimal text, or null for an absent value
     * @throws DecimalUtils.ParseException if the value has an unsupported numeric type or its format settings are invalid
     */
    public String formatCostUnit(CurrencyTransfer currency, Number costUnit) {
        return costUnit == null ? null : DecimalUtils.getInstance().format(currency.getMinusSign(), currency.getFractionSeparator(),
                currency.getCostUnitFractionDigits(), toLong(costUnit));
    }

    /** Retains the Integer entry point; delegates to the exact integral-value formatter. */
    public String formatCostLine(CurrencyTransfer currency, Integer costLine) {
        return formatCostLine(currency, (Number)costLine);
    }

    /**
     * Formats a scaled integral value with the currency's costLineFractionDigits precision.
     *
     * @param currency currency format settings; unused when the value is null
     * @param costLine scaled Byte, Short, Integer, or Long value, or null
     * @return exact plain decimal text, or null for an absent value
     * @throws DecimalUtils.ParseException if the value has an unsupported numeric type or its format settings are invalid
     */
    public String formatCostLine(CurrencyTransfer currency, Number costLine) {
        return costLine == null ? null : DecimalUtils.getInstance().format(currency.getMinusSign(), currency.getFractionSeparator(),
                currency.getCostLineFractionDigits(), toLong(costLine));
    }

    public AmountFormatter getAmountFormatter(CurrencyTransfer currency, AmountFormatType amountFormatType) {
        return new AmountFormatter(currency, amountFormatType);
    }
    
}
