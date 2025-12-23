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

package com.echothree.ui.web.main.action.customer.customertypecreditlimit;

import com.echothree.view.client.web.struts.BaseActionForm;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForm;

@SproutForm(name="CustomerTypeCreditLimitEdit")
public class EditActionForm
        extends BaseActionForm {
    
    private String customerTypeName;
    private String currencyIsoName;
    private String creditLimit;
    private String potentialCreditLimit;
    
    public String getCustomerTypeName() {
        return customerTypeName;
    }
    
    public void setCustomerTypeName(String customerTypeName) {
        this.customerTypeName = customerTypeName;
    }
    
    public String getCurrencyIsoName() {
        return currencyIsoName;
    }
    
    public void setCurrencyIsoName(String currencyIsoName) {
        this.currencyIsoName = currencyIsoName;
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
