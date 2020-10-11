// --------------------------------------------------------------------------------
// Copyright 2002-2020 Echo Three, LLC
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

package com.echothree.model.control.term.server.transfer;

import com.echothree.model.control.accounting.common.transfer.CurrencyTransfer;
import com.echothree.model.control.accounting.server.control.AccountingControl;
import com.echothree.model.control.party.common.transfer.PartyTransfer;
import com.echothree.model.control.party.server.PartyControl;
import com.echothree.model.control.term.common.transfer.PartyCreditLimitTransfer;
import com.echothree.model.control.term.server.TermControl;
import com.echothree.model.data.accounting.server.entity.Currency;
import com.echothree.model.data.term.server.entity.PartyCreditLimit;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;
import com.echothree.util.server.string.AmountUtils;

public class PartyCreditLimitTransferCache
        extends BaseTermTransferCache<PartyCreditLimit, PartyCreditLimitTransfer> {
    
    AccountingControl accountingControl;
    PartyControl partyControl;
    
    /** Creates a new instance of PartyCreditLimitTransferCache */
    public PartyCreditLimitTransferCache(UserVisit userVisit, TermControl termControl) {
        super(userVisit, termControl);
        
        accountingControl = (AccountingControl)Session.getModelController(AccountingControl.class);
        partyControl = (PartyControl)Session.getModelController(PartyControl.class);
    }
    
    public PartyCreditLimitTransfer getPartyCreditLimitTransfer(PartyCreditLimit partyCreditLimit) {
        PartyCreditLimitTransfer partyCreditLimitTransfer = get(partyCreditLimit);
        
        if(partyCreditLimitTransfer == null) {
            PartyTransfer partyTransfer = partyControl.getPartyTransfer(userVisit, partyCreditLimit.getParty());
            Currency currency = partyCreditLimit.getCurrency();
            CurrencyTransfer currencyTransfer = accountingControl.getCurrencyTransfer(userVisit, currency);
            String creditLimit = AmountUtils.getInstance().formatAmount(currency, partyCreditLimit.getCreditLimit());
            String potentialCreditLimit = AmountUtils.getInstance().formatAmount(currency, partyCreditLimit.getPotentialCreditLimit());
            
            partyCreditLimitTransfer = new PartyCreditLimitTransfer(partyTransfer, currencyTransfer, creditLimit,
                    potentialCreditLimit);
            put(partyCreditLimit, partyCreditLimitTransfer);
        }
        
        return partyCreditLimitTransfer;
    }
    
}
