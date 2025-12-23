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

import com.echothree.model.control.accounting.common.transfer.CurrencyTransfer;
import com.echothree.model.control.accounting.common.transfer.GlAccountTransfer;
import com.echothree.util.common.transfer.BaseTransfer;
import com.echothree.util.common.transfer.ListWrapper;
import com.echothree.util.common.transfer.MapWrapper;

public class FinancialAccountTransfer
        extends BaseTransfer {

    private FinancialAccountTypeTransfer financialAccountType;
    private String financialAccountName;
    private CurrencyTransfer currency;
    private GlAccountTransfer glAccount;
    private String reference;
    private String description;
    private Long unformattedActualBalance;
    private String actualBalance;
    private Long unformattedAvailableBalance;
    private String availableBalance;

    private MapWrapper<FinancialAccountRoleTransfer> financialAccountRoles;
    private ListWrapper<FinancialAccountTransactionTransfer> financialAccountTransactions;
    
    /** Creates a new instance of FinancialAccountTransfer */
    public FinancialAccountTransfer(FinancialAccountTypeTransfer financialAccountType, String financialAccountName, CurrencyTransfer currency,
            GlAccountTransfer glAccount, String reference, String description, Long unformattedActualBalance, String actualBalance,
            Long unformattedAvailableBalance, String availableBalance) {
        this.financialAccountType = financialAccountType;
        this.financialAccountName = financialAccountName;
        this.currency = currency;
        this.glAccount = glAccount;
        this.reference = reference;
        this.description = description;
        this.unformattedActualBalance = unformattedActualBalance;
        this.actualBalance = actualBalance;
        this.unformattedAvailableBalance = unformattedAvailableBalance;
        this.availableBalance = availableBalance;
    }

    public FinancialAccountTypeTransfer getFinancialAccountType() {
        return financialAccountType;
    }

    public void setFinancialAccountType(FinancialAccountTypeTransfer financialAccountType) {
        this.financialAccountType = financialAccountType;
    }

    public String getFinancialAccountName() {
        return financialAccountName;
    }

    public void setFinancialAccountName(String financialAccountName) {
        this.financialAccountName = financialAccountName;
    }

    public CurrencyTransfer getCurrency() {
        return currency;
    }

    public void setCurrency(CurrencyTransfer currency) {
        this.currency = currency;
    }

    public GlAccountTransfer getGlAccount() {
        return glAccount;
    }

    public void setGlAccount(GlAccountTransfer glAccount) {
        this.glAccount = glAccount;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getUnformattedActualBalance() {
        return unformattedActualBalance;
    }

    public void setUnformattedActualBalance(Long unformattedActualBalance) {
        this.unformattedActualBalance = unformattedActualBalance;
    }

    public String getActualBalance() {
        return actualBalance;
    }

    public void setActualBalance(String actualBalance) {
        this.actualBalance = actualBalance;
    }

    public Long getUnformattedAvailableBalance() {
        return unformattedAvailableBalance;
    }

    public void setUnformattedAvailableBalance(Long unformattedAvailableBalance) {
        this.unformattedAvailableBalance = unformattedAvailableBalance;
    }

    public String getAvailableBalance() {
        return availableBalance;
    }

    public void setAvailableBalance(String availableBalance) {
        this.availableBalance = availableBalance;
    }

    public MapWrapper<FinancialAccountRoleTransfer> getFinancialAccountRoles() {
        return financialAccountRoles;
    }

    public void setFinancialAccountRoles(MapWrapper<FinancialAccountRoleTransfer> financialAccountRoles) {
        this.financialAccountRoles = financialAccountRoles;
    }

    public ListWrapper<FinancialAccountTransactionTransfer> getFinancialAccountTransactions() {
        return financialAccountTransactions;
    }

    public void setFinancialAccountTransactions(ListWrapper<FinancialAccountTransactionTransfer> financialAccountTransactions) {
        this.financialAccountTransactions = financialAccountTransactions;
    }

}
