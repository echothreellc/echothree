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

package com.echothree.model.control.payment.common.transfer;

import com.echothree.model.control.accounting.common.transfer.CurrencyTransfer;
import com.echothree.util.common.transfer.BaseTransfer;
import com.echothree.util.common.transfer.MapWrapper;

public class BillingAccountTransfer
        extends BaseTransfer {

    private String billingAccountName;
    private CurrencyTransfer currency;
    private String reference;
    private String description;
    private String creditLimit;
    private String potentialCreditLimit;

    private MapWrapper<BillingAccountRoleTransfer> billingAccountRoles;
    
    /** Creates a new instance of BillingAccountTransfer */
    public BillingAccountTransfer(String billingAccountName, CurrencyTransfer currency, String reference, String description, String creditLimit, String potentialCreditLimit) {
        this.billingAccountName = billingAccountName;
        this.currency = currency;
        this.reference = reference;
        this.description = description;
        this.creditLimit = creditLimit;
        this.potentialCreditLimit = potentialCreditLimit;
    }

    public String getBillingAccountName() {
        return billingAccountName;
    }

    public void setBillingAccountName(String billingAccountName) {
        this.billingAccountName = billingAccountName;
    }

    public CurrencyTransfer getCurrency() {
        return currency;
    }

    public void setCurrency(CurrencyTransfer currency) {
        this.currency = currency;
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

    public String getCreditLimit() {
        return creditLimit;
    }

    public void setCreditLimit(String creditLimit) {
        this.creditLimit = creditLimit;
    }

    public String getPotentialCreditLimit() {
        return potentialCreditLimit;
    }

    public void setPotentialCreditLimit(String potentialCreditLimit) {
        this.potentialCreditLimit = potentialCreditLimit;
    }

    public MapWrapper<BillingAccountRoleTransfer> getBillingAccountRoles() {
        return billingAccountRoles;
    }

    public void setBillingAccountRoles(MapWrapper<BillingAccountRoleTransfer> billingAccountRoles) {
        this.billingAccountRoles = billingAccountRoles;
    }

}