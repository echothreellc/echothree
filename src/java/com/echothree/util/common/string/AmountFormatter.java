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
    
    public String format(Integer amount) {
        String result = null;
        
        if(amountFormatType.equals(AmountFormatType.AMOUNT)) {
            result = AmountUtils.getInstance().formatAmount(currency, amount);
        } else if(amountFormatType.equals(AmountFormatType.PRICE_UNIT)) {
            result = AmountUtils.getInstance().formatPriceUnit(currency, amount);
        } else if(amountFormatType.equals(AmountFormatType.PRICE_LINE)) {
            result = AmountUtils.getInstance().formatPriceLine(currency, amount);
        } else if(amountFormatType.equals(AmountFormatType.COST_UNIT)) {
            result = AmountUtils.getInstance().formatCostUnit(currency, amount);
        } else if(amountFormatType.equals(AmountFormatType.COST_LINE)) {
            result = AmountUtils.getInstance().formatCostLine(currency, amount);
        }
        
        return result;
    }
    
}
