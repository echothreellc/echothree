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
import com.google.common.math.IntMath;
import java.util.Formatter;

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
        String result = null;

        if(amount != null) {
            int fractionDigits = currency.getDefaultFractionDigits();

            if(fractionDigits == 0) {
                result = amount.toString();
            } else {
                long rawAmount = amount;
                var i = IntMath.checkedPow(10, fractionDigits);
                var builtResult = new StringBuilder().append(rawAmount / i).append(currency.getFractionSeparator());

                new Formatter(builtResult).format("%0" + fractionDigits + "d", Math.abs(rawAmount) % i);
                result = builtResult.toString();
            }
        }

        return result;
    }

    public String formatPriceUnit(Currency currency, Long priceUnit) {
        String result = null;

        if(priceUnit != null) {
            var priceUnitFractionDigits = currency.getPriceUnitFractionDigits();
            var fractionDigits = priceUnitFractionDigits == null ? 0 : priceUnitFractionDigits;

            if(fractionDigits == 0) {
                result = priceUnit.toString();
            } else {
                long rawPriceUnit = priceUnit;
                var i = IntMath.checkedPow(10, fractionDigits);
                var builtResult = new StringBuilder().append(rawPriceUnit / i).append(currency.getFractionSeparator());

                new Formatter(builtResult).format("%0" + fractionDigits + "d", Math.abs(rawPriceUnit) % i);
                result = builtResult.toString();
            }
        }

        return result;
    }

    public String formatPriceLine(Currency currency, Long priceLine) {
        String result = null;

        if(priceLine != null) {
            var priceLineFractionDigits = currency.getPriceLineFractionDigits();
            var fractionDigits = priceLineFractionDigits == null ? 0 : priceLineFractionDigits;

            if(fractionDigits == 0) {
                result = priceLine.toString();
            } else {
                long rawPriceLine = priceLine;
                var i = IntMath.checkedPow(10, fractionDigits);
                var builtResult = new StringBuilder().append(rawPriceLine / i).append(currency.getFractionSeparator());

                new Formatter(builtResult).format("%0" + fractionDigits + 'd', Math.abs(rawPriceLine) % i);
                result = builtResult.toString();
            }
        }

        return result;
    }

    public String formatCostUnit(Currency currency, Long costUnit) {
        String result = null;

        if(costUnit != null) {
            var costUnitFractionDigits = currency.getCostUnitFractionDigits();
            var fractionDigits = costUnitFractionDigits == null ? 0 : costUnitFractionDigits;

            if(fractionDigits == 0) {
                result = costUnit.toString();
            } else {
                long rawCostUnit = costUnit;
                var i = IntMath.checkedPow(10, fractionDigits);
                var builtResult = new StringBuilder().append(rawCostUnit / i).append(currency.getFractionSeparator());

                new Formatter(builtResult).format("%0" + fractionDigits + 'd', Math.abs(rawCostUnit) % i);
                result = builtResult.toString();
            }
        }

        return result;
    }

    public String formatCostLine(Currency currency, Long costLine) {
        String result = null;

        if(costLine != null) {
            var costLineFractionDigits = currency.getCostLineFractionDigits();
            var fractionDigits = costLineFractionDigits == null ? 0 : costLineFractionDigits;

            if(fractionDigits == 0) {
                result = costLine.toString();
            } else {
                long rawCostLine = costLine;
                var i = IntMath.checkedPow(10, fractionDigits);
                var builtResult = new StringBuilder().append(rawCostLine / i).append(currency.getFractionSeparator());

                new Formatter(builtResult).format("%0" + fractionDigits + 'd', Math.abs(rawCostLine) % i);
                result = builtResult.toString();
            }
        }

        return result;
    }

}
