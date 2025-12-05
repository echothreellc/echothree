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

package com.echothree.model.control.accounting.server.logic;

import com.echothree.model.control.accounting.common.TransactionTimeTypes;
import com.echothree.model.control.accounting.server.control.AccountingControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.period.common.PeriodConstants;
import com.echothree.model.control.period.server.control.PeriodControl;
import com.echothree.model.data.accounting.server.entity.GlAccount;
import com.echothree.model.data.accounting.server.entity.Transaction;
import com.echothree.model.data.accounting.server.entity.TransactionGlEntry;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.model.data.period.server.entity.Period;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.persistence.Session;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.spi.CDI;

@ApplicationScoped
public class PostingLogic {

    protected PostingLogic() {
        super();
    }

    public static PostingLogic getInstance() {
        return CDI.current().select(PostingLogic.class).get();
    }
    
    private void updateGlAccountSummary(final AccountingControl accountingControl, final GlAccount glAccount,
            final Party groupParty, final Period period, final Long debit, final Long credit) {
        var glAccountSummary = accountingControl.getGlAccountSummaryForUpdate(glAccount, groupParty, period);

        var debitAdjustment = debit == null ? 0L : debit;
        var creditAdjustment = credit == null ? 0L : credit;
        var balanceAdjustment = creditAdjustment - debitAdjustment;
        if(glAccountSummary == null) {
            accountingControl.createGlAccountSummary(glAccount, groupParty, period, debitAdjustment, creditAdjustment,
                    balanceAdjustment);
        } else {
            glAccountSummary.setDebitTotal(glAccountSummary.getDebitTotal() + debitAdjustment);
            glAccountSummary.setCreditTotal(glAccountSummary.getCreditTotal() + creditAdjustment);
            glAccountSummary.setBalance(glAccountSummary.getBalance() + balanceAdjustment);
        }
    }
    
    private void postTransactionGlEntry(final AccountingControl accountingControl, final PartyControl partyControl,
            final TransactionGlEntry transactionGlEntry, final Period period) {
        var glAccount = transactionGlEntry.getGlAccount();
        var debit = transactionGlEntry.getDebit();
        var credit = transactionGlEntry.getCredit();

        // Go through this for each one of the COMPANY, DIVISION, and DEPARTMENT, updating the
        // GL Account Summaries as needed.
        var groupParty = transactionGlEntry.getGroupParty();
        for(var i = 0; i < 3; i++) {
            var partyTypeName = groupParty.getLastDetail().getPartyType().getPartyTypeName();
            
            updateGlAccountSummary(accountingControl, glAccount, groupParty, period, debit, credit);
            
            if(partyTypeName.equals(PartyTypes.COMPANY.name())) {
                break;
            } else if(partyTypeName.equals(PartyTypes.DIVISION.name())) {
                groupParty = partyControl.getPartyDivision(groupParty).getCompanyParty();
            } else if(partyTypeName.equals(PartyTypes.DEPARTMENT.name())) {
                groupParty = partyControl.getPartyDepartment(groupParty).getDivisionParty();
            }
        }
    }
    
    public void postTransaction(final Session session, final Transaction transaction, final BasePK createdBy) {
        var accountingControl = Session.getModelController(AccountingControl.class);
        var partyControl = Session.getModelController(PartyControl.class);
        var periodControl = Session.getModelController(PeriodControl.class);

        var postedTime = session.getStartTimeLong();
        var periods = periodControl.getContainingPeriodsUsingNames(PeriodConstants.PeriodKind_FISCAL, postedTime);
        var transactionGlEntries = accountingControl.getTransactionGlEntriesByTransaction(transaction);
        transactionGlEntries.forEach((transactionGlEntry) ->
                periods.forEach((period) ->
                        postTransactionGlEntry(accountingControl, partyControl, transactionGlEntry, period)
                )
        );

        TransactionTimeLogic.getInstance().createTransactionTime(null, transaction, TransactionTimeTypes.POSTED_TIME.name(),
                postedTime, createdBy);
    }
    
}
