// --------------------------------------------------------------------------------
// Copyright 2002-2025 Echo Three, LLC
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

import com.echothree.control.user.accounting.common.form.GetCurrencyForm;
import com.echothree.control.user.accounting.common.result.AccountingResultFactory;
import com.echothree.model.control.accounting.server.control.AccountingControl;
import com.echothree.model.control.accounting.server.logic.CurrencyLogic;
import com.echothree.model.control.core.common.ComponentVendors;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.model.control.core.common.EventTypes;
import com.echothree.model.control.core.server.logic.EntityInstanceLogic;
import com.echothree.model.data.accounting.server.entity.Currency;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.server.control.BaseSingleEntityCommand;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class GetCurrencyCommand
        extends BaseSingleEntityCommand<Currency, GetCurrencyForm> {
    
    // No COMMAND_SECURITY_DEFINITION, anyone may execute this command.
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("CurrencyIsoName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("EntityRef", FieldType.ENTITY_REF, false, null, null),
                new FieldDefinition("Uuid", FieldType.UUID, false, null, null)
                ));
    }
    
    /** Creates a new instance of GetCurrencyCommand */
    public GetCurrencyCommand() {
        super(null, FORM_FIELD_DEFINITIONS, true);
    }
    
    @Override
    protected Currency getEntity() {
        var accountingControl = Session.getModelController(AccountingControl.class);
        Currency currency = null;
        var currencyIsoName = form.getCurrencyIsoName();
        var parameterCount = (currencyIsoName == null ? 0 : 1) + EntityInstanceLogic.getInstance().countPossibleEntitySpecs(form);

        switch(parameterCount) {
            case 0 -> currency = accountingControl.getDefaultCurrency();
            case 1 -> {
                if(currencyIsoName == null) {
                    var entityInstance = EntityInstanceLogic.getInstance().getEntityInstance(this, form,
                            ComponentVendors.ECHO_THREE.name(), EntityTypes.Currency.name());

                    if(!hasExecutionErrors()) {
                        currency = accountingControl.getCurrencyByEntityInstance(entityInstance);
                    }
                } else {
                    currency = CurrencyLogic.getInstance().getCurrencyByName(this, currencyIsoName);
                }
            }
            default -> addExecutionError(ExecutionErrors.InvalidParameterCount.name());
        }
        
        if(currency != null) {
            sendEvent(currency.getPrimaryKey(), EventTypes.READ, null, null, getPartyPK());
        }

        return currency;
    }
    
    @Override
    protected BaseResult getResult(Currency currency) {
        var accountingControl = Session.getModelController(AccountingControl.class);
        var result = AccountingResultFactory.getGetCurrencyResult();

        if(currency != null) {
            result.setCurrency(accountingControl.getCurrencyTransfer(getUserVisit(), currency));
        }

        return result;
    }
    
}
