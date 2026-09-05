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

package com.echothree.control.user.accounting.server.command;

import com.echothree.control.user.accounting.common.form.GetPreferredCurrencyForm;
import com.echothree.control.user.accounting.common.result.AccountingResultFactory;
import com.echothree.model.control.accounting.server.control.AccountingControl;
import com.echothree.model.control.core.common.EventTypes;
import com.echothree.model.data.accounting.server.entity.Currency;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.server.control.BaseSingleEntityCommand;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class GetPreferredCurrencyCommand
        extends BaseSingleEntityCommand<Currency, GetPreferredCurrencyForm> {

    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;

    static {
        FORM_FIELD_DEFINITIONS = List.of();
    }

    @Inject
    AccountingControl accountingControl;

    /** Creates a new instance of GetPreferredCurrencyCommand */
    public GetPreferredCurrencyCommand() {
        super(null, FORM_FIELD_DEFINITIONS, true);
    }

    @Override
    protected Currency getEntity() {
        var currency = getPreferredCurrency();

        sendEvent(currency.getPrimaryKey(), EventTypes.READ, null, null, getPartyPK());

        return currency;
    }

    @Override
    protected BaseResult getResult(Currency currency) {
        var result = AccountingResultFactory.getGetPreferredCurrencyResult();

        result.setPreferredCurrency(accountingControl.getCurrencyTransfer(getUserVisit(), currency));

        return result;
    }

}
