// --------------------------------------------------------------------------------
// Copyright 2002-2018 Echo Three, LLC
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

package com.echothree.control.user.term.server.command;

import com.echothree.control.user.term.common.form.CreatePartyCreditLimitForm;
import com.echothree.model.control.accounting.server.AccountingControl;
import com.echothree.model.control.party.common.PartyConstants;
import com.echothree.model.control.party.server.PartyControl;
import com.echothree.model.control.term.server.TermControl;
import com.echothree.model.data.accounting.server.entity.Currency;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.model.data.term.server.entity.PartyCreditLimit;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CreatePartyCreditLimitCommand
        extends BaseSimpleCommand<CreatePartyCreditLimitForm> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("PartyName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("CurrencyIsoName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("CreditLimit:CurrencyIsoName,CurrencyIsoName", FieldType.UNSIGNED_PRICE_LINE, Boolean.FALSE, null, null),
                new FieldDefinition("PotentialCreditLimit:CurrencyIsoName,CurrencyIsoName", FieldType.UNSIGNED_PRICE_LINE, Boolean.FALSE, null, null)
                ));
    }
    
    /** Creates a new instance of CreatePartyCreditLimitCommand */
    public CreatePartyCreditLimitCommand(UserVisitPK userVisitPK, CreatePartyCreditLimitForm form) {
        super(userVisitPK, form, null, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        PartyControl partyControl = (PartyControl)Session.getModelController(PartyControl.class);
        String partyName = form.getPartyName();
        Party party = partyControl.getPartyByName(partyName);
        
        if(party != null) {
            String partyTypeName = party.getLastDetail().getPartyType().getPartyTypeName();
            
            if(partyTypeName.equals(PartyConstants.PartyType_CUSTOMER) || partyTypeName.equals(PartyConstants.PartyType_VENDOR)) {
                AccountingControl accountingControl = (AccountingControl)Session.getModelController(AccountingControl.class);
                String currencyIsoName = form.getCurrencyIsoName();
                Currency currency = accountingControl.getCurrencyByIsoName(currencyIsoName);
                
                if(currency != null) {
                    TermControl termControl = (TermControl)Session.getModelController(TermControl.class);
                    PartyCreditLimit partyCreditLimit = termControl.getPartyCreditLimit(party, currency);
                    
                    if(partyCreditLimit == null) {
                        String strCreditLimit = form.getCreditLimit();
                        Long creditLimit = strCreditLimit == null? null: Long.valueOf(strCreditLimit);
                        String strPotentialCreditLimit = form.getPotentialCreditLimit();
                        Long potentialCreditLimit = strPotentialCreditLimit == null? null: Long.valueOf(strPotentialCreditLimit);
                        
                        if(creditLimit != null || potentialCreditLimit != null) {
                            termControl.createPartyCreditLimit(party, currency, creditLimit, potentialCreditLimit, getPartyPK());
                        }
                    } else {
                        addExecutionError(ExecutionErrors.DuplicatePartyCreditLimit.name());
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownCurrencyIsoName.name(), currencyIsoName);
                }
            } else {
                addExecutionError(ExecutionErrors.InvalidPartyType.name(), partyTypeName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownPartyName.name(), partyName);
        }
        
        return null;
    }
    
}
