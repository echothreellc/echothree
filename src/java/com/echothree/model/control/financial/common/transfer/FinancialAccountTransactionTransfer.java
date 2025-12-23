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

package com.echothree.model.control.financial.common.transfer;

import com.echothree.util.common.transfer.BaseTransfer;

public class FinancialAccountTransactionTransfer
        extends BaseTransfer {

    private String financialAccountTransactionName;
    private FinancialAccountTransfer financialAccount;
    private FinancialAccountTransactionTypeTransfer financialAccountTransactionType;
    private Long unformattedAmount;
    private String amount;
    private String comment;

    /** Creates a new instance of FinancialAccountTransactionTransfer */
    public FinancialAccountTransactionTransfer(String financialAccountTransactionName, FinancialAccountTransfer financialAccount,
            FinancialAccountTransactionTypeTransfer financialAccountTransactionType, Long unformattedAmount, String amount, String comment) {
        this.financialAccountTransactionName = financialAccountTransactionName;
        this.financialAccount = financialAccount;
        this.financialAccountTransactionType = financialAccountTransactionType;
        this.unformattedAmount = unformattedAmount;
        this.amount = amount;
        this.comment = comment;
    }

    public String getFinancialAccountTransactionName() {
        return financialAccountTransactionName;
    }

    public void setFinancialAccountTransactionName(String financialAccountTransactionName) {
        this.financialAccountTransactionName = financialAccountTransactionName;
    }

    public FinancialAccountTransfer getFinancialAccount() {
        return financialAccount;
    }

    public void setFinancialAccount(FinancialAccountTransfer financialAccount) {
        this.financialAccount = financialAccount;
    }

    public FinancialAccountTransactionTypeTransfer getFinancialAccountTransactionType() {
        return financialAccountTransactionType;
    }

    public void setFinancialAccountTransactionType(FinancialAccountTransactionTypeTransfer financialAccountTransactionType) {
        this.financialAccountTransactionType = financialAccountTransactionType;
    }

    public Long getUnformattedAmount() {
        return unformattedAmount;
    }

    public void setUnformattedAmount(Long unformattedAmount) {
        this.unformattedAmount = unformattedAmount;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

}
