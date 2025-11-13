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

package com.echothree.model.control.customer.server.transfer;

import com.echothree.model.control.customer.server.control.CustomerControl;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.transfer.BaseTransferCaches;

public class CustomerTransferCaches
        extends BaseTransferCaches {
    
    protected CustomerControl customerControl;
    
    protected CustomerTypeTransferCache customerTypeTransferCache;
    protected CustomerTypeDescriptionTransferCache customerTypeDescriptionTransferCache;
    protected CustomerTransferCache customerTransferCache;
    protected CustomerTypePaymentMethodTransferCache customerTypePaymentMethodTransferCache;
    protected CustomerTypeShippingMethodTransferCache customerTypeShippingMethodTransferCache;
    
    /** Creates a new instance of CustomerTransferCaches */
    public CustomerTransferCaches(CustomerControl customerControl) {
        super();
        
        this.customerControl = customerControl;
    }
    
    public CustomerTypeTransferCache getCustomerTypeTransferCache() {
        if(customerTypeTransferCache == null)
            customerTypeTransferCache = new CustomerTypeTransferCache(customerControl);
        
        return customerTypeTransferCache;
    }
    
    public CustomerTypeDescriptionTransferCache getCustomerTypeDescriptionTransferCache() {
        if(customerTypeDescriptionTransferCache == null)
            customerTypeDescriptionTransferCache = new CustomerTypeDescriptionTransferCache(customerControl);
        
        return customerTypeDescriptionTransferCache;
    }
    
    public CustomerTransferCache getCustomerTransferCache() {
        if(customerTransferCache == null)
            customerTransferCache = new CustomerTransferCache(customerControl);
        
        return customerTransferCache;
    }
    
    public CustomerTypePaymentMethodTransferCache getCustomerTypePaymentMethodTransferCache() {
        if(customerTypePaymentMethodTransferCache == null)
            customerTypePaymentMethodTransferCache = new CustomerTypePaymentMethodTransferCache(customerControl);
        
        return customerTypePaymentMethodTransferCache;
    }
    
    public CustomerTypeShippingMethodTransferCache getCustomerTypeShippingMethodTransferCache() {
        if(customerTypeShippingMethodTransferCache == null)
            customerTypeShippingMethodTransferCache = new CustomerTypeShippingMethodTransferCache(customerControl);
        
        return customerTypeShippingMethodTransferCache;
    }
    
}
