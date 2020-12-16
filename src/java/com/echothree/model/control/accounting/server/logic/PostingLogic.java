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

package com.echothree.model.control.accounting.server.logic;

import com.echothree.model.control.accounting.server.control.AccountingControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.period.common.PeriodConstants;
import com.echothree.model.control.period.server.control.PeriodControl;
import com.echothree.model.data.accounting.server.entity.GlAccount;
import com.echothree.model.data.accounting.server.entity.GlAccountSummary;
import com.echothree.model.data.accounting.server.entity.Transaction;
import com.echothree.model.data.accounting.server.entity.TransactionGlEntry;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.model.data.period.server.entity.Period;
import com.echothree.util.server.persistence.Session;
import java.util.List;

public class PostingLogic {

    private PostingLogic() {
        super();
    }

    private static class PostingLogicHolder {
        static PostingLogic instance = new PostingLogic();
    }

    public static PostingLogic getInstance() {
        return PostingLogicHolder.instance;
    }
    
    private void updateGlAccountSummary(final AccountingControl accountingControl, final GlAccount glAccount, final Party groupParty, final Period period,
            final long amount) {
        GlAccountSummary glAccountSummary = accountingControl.getGlAccountSummaryForUpdate(glAccount, groupParty, period);
        
        if(glAccountSummary == null) {
            glAccountSummary = accountingControl.createGlAccountSummary(glAccount, groupParty, period, amount);
        } else {
            glAccountSummary.setBalance(glAccountSummary.getBalance() + amount);
        }
        
    }
    
    private void postTransactionGlEntry(final AccountingControl accountingControl, final PartyControl partyControl, final TransactionGlEntry transactionGlEntry,
            final Period period) {
        long amount = transactionGlEntry.getAmount();
        Party groupParty = transactionGlEntry.getGroupParty();
        GlAccount glAccount = transactionGlEntry.getGlAccount();
        
        for(int i = 0; i < 3; i++) {
            String partyTypeName = groupParty.getLastDetail().getPartyType().getPartyTypeName();
            
            updateGlAccountSummary(accountingControl, glAccount, groupParty, period, amount);
            
            if(partyTypeName.equals(PartyTypes.COMPANY.name())) {
                break;
            } else if(partyTypeName.equals(PartyTypes.DIVISION.name())) {
                groupParty = partyControl.getPartyDivision(groupParty).getCompanyParty();
            } else if(partyTypeName.equals(PartyTypes.DEPARTMENT.name())) {
                groupParty = partyControl.getPartyDepartment(groupParty).getDivisionParty();
            }
        }
    }
    
    public void postTransaction(final Transaction transaction) {
        var accountingControl = Session.getModelController(AccountingControl.class);
        var partyControl = Session.getModelController(PartyControl.class);
        var periodControl = Session.getModelController(PeriodControl.class);
        Long postingTime = transaction.getLastDetail().getPostingTime();
        List<TransactionGlEntry> transactionGlEntries = accountingControl.getTransactionGlEntriesByTransaction(transaction);
        List<Period> periods = periodControl.getContainingPeriodsUsingNames(PeriodConstants.PeriodKind_FISCAL, postingTime);
        
        transactionGlEntries.forEach((transactionGlEntry) -> {
            periods.forEach((period) -> {
                postTransactionGlEntry(accountingControl, partyControl, transactionGlEntry, period);
            });
        });
    }
    
}
