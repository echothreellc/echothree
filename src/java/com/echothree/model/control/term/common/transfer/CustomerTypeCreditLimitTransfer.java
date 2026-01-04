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

package com.echothree.model.control.term.common.transfer;

import com.echothree.model.control.accounting.common.transfer.CurrencyTransfer;
import com.echothree.model.control.customer.common.transfer.CustomerTypeTransfer;
import com.echothree.util.common.transfer.BaseTransfer;

public class CustomerTypeCreditLimitTransfer
        extends BaseTransfer {
    
    private CustomerTypeTransfer customerType;
    private CurrencyTransfer currency;
    private String creditLimit;
    private String potentialCreditLimit;
    
    /** Creates a new instance of CustomerTypeCreditLimitTransfer */
    public CustomerTypeCreditLimitTransfer(CustomerTypeTransfer customerType, CurrencyTransfer currency, String creditLimit,
            String potentialCreditLimit) {
        this.customerType = customerType;
        this.currency = currency;
        this.creditLimit = creditLimit;
        this.potentialCreditLimit = potentialCreditLimit;
    }
    
    public CustomerTypeTransfer getCustomerType() {
        return customerType;
    }
    
    public void setCustomerType(CustomerTypeTransfer customerType) {
        this.customerType = customerType;
    }
    
    public CurrencyTransfer getCurrency() {
        return currency;
    }
    
    public void setCurrency(CurrencyTransfer currency) {
        this.currency = currency;
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
    
}
