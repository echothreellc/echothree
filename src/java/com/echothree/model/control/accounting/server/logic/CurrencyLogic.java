// --------------------------------------------------------------------------------
// Copyright 2002-2023 Echo Three, LLC
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

package com.echothree.model.control.accounting.server.logic;

import com.echothree.model.control.accounting.common.exception.MissingDefaultCurrencyException;
import com.echothree.model.control.accounting.common.exception.UnknownCurrencyIsoNameException;
import com.echothree.model.control.accounting.server.control.AccountingControl;
import com.echothree.model.data.accounting.server.entity.Currency;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.Session;

public class CurrencyLogic
        extends BaseLogic {

    private CurrencyLogic() {
        super();
    }

    private static class CurrencyLogicHolder {
        static CurrencyLogic instance = new CurrencyLogic();
    }

    public static CurrencyLogic getInstance() {
        return CurrencyLogicHolder.instance;
    }
    
    public Currency getCurrencyByName(final ExecutionErrorAccumulator eea, final String currencyIsoName) {
        var accountingControl = Session.getModelController(AccountingControl.class);
        Currency currency = accountingControl.getCurrencyByIsoName(currencyIsoName);

        if(currency == null) {
            handleExecutionError(UnknownCurrencyIsoNameException.class, eea, ExecutionErrors.UnknownCurrencyIsoName.name(), currencyIsoName);
        }

        return currency;
    }
    
    public Currency getDefaultCurrency(final ExecutionErrorAccumulator eea) {
        var accountingControl = Session.getModelController(AccountingControl.class);
        Currency currency = accountingControl.getDefaultCurrency();

        if(currency == null) {
            handleExecutionError(MissingDefaultCurrencyException.class, eea, ExecutionErrors.MissingDefaultCurrency.name());
        }

        return currency;
    }
    
}
