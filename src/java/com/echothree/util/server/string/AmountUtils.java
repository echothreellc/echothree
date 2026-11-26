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

package com.echothree.util.server.string;

import com.echothree.model.data.accounting.server.entity.Currency;
import com.echothree.util.common.string.DecimalUtils;

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

    public String formatAmount(Currency currency, Long amount) {
        return amount == null ? null : DecimalUtils.getInstance().format(currency.getMinusSign(), currency.getFractionSeparator(),
                currency.getDefaultFractionDigits(), amount);
    }

    public String formatPriceUnit(Currency currency, Long priceUnit) {
        return priceUnit == null ? null : DecimalUtils.getInstance().format(currency.getMinusSign(), currency.getFractionSeparator(),
                currency.getPriceUnitFractionDigits(), priceUnit);
    }

    public String formatPriceLine(Currency currency, Long priceLine) {
        return priceLine == null ? null : DecimalUtils.getInstance().format(currency.getMinusSign(), currency.getFractionSeparator(),
                currency.getPriceLineFractionDigits(), priceLine);
    }

    public String formatCostUnit(Currency currency, Long costUnit) {
        return costUnit == null ? null : DecimalUtils.getInstance().format(currency.getMinusSign(), currency.getFractionSeparator(),
                currency.getCostUnitFractionDigits(), costUnit);
    }

    public String formatCostLine(Currency currency, Long costLine) {
        return costLine == null ? null : DecimalUtils.getInstance().format(currency.getMinusSign(), currency.getFractionSeparator(),
                currency.getCostLineFractionDigits(), costLine);
    }

}
