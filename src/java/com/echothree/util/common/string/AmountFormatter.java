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

public class AmountFormatter {
    
    CurrencyTransfer currency;
    AmountFormatType amountFormatType;

    /** Creates a new instance of AmountFormatter */
    public AmountFormatter(CurrencyTransfer currency, AmountFormatType amountFormatType) {
        this.currency = currency;
        this.amountFormatType = amountFormatType;
    }
    
    /** Retains the Integer entry point, including calls with an untyped null. */
    public String format(Integer amount) {
        return format((Number)amount);
    }

    /**
     * Formats a scaled integral value according to this formatter's amount type.
     *
     * @param amount scaled Byte, Short, Integer, or Long value, or null
     * @return exact plain decimal text, or null for an absent value
     * @throws DecimalUtils.ParseException if the value has an unsupported numeric type or its format settings are invalid
     */
    public String format(Number amount) {
        var amountUtils = AmountUtils.getInstance();

        return switch(amountFormatType) {
            case AMOUNT -> amountUtils.formatAmount(currency, amount);
            case PRICE_UNIT -> amountUtils.formatPriceUnit(currency, amount);
            case PRICE_LINE -> amountUtils.formatPriceLine(currency, amount);
            case COST_UNIT -> amountUtils.formatCostUnit(currency, amount);
            case COST_LINE -> amountUtils.formatCostLine(currency, amount);
        };
    }

}
