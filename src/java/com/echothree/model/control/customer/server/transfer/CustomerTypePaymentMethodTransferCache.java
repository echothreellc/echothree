// --------------------------------------------------------------------------------
// Copyright 2002-2021 Echo Three, LLC
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

import com.echothree.model.control.customer.common.transfer.CustomerTypePaymentMethodTransfer;
import com.echothree.model.control.customer.common.transfer.CustomerTypeTransfer;
import com.echothree.model.control.customer.server.control.CustomerControl;
import com.echothree.model.control.payment.common.transfer.PaymentMethodTransfer;
import com.echothree.model.control.payment.server.control.PaymentMethodControl;
import com.echothree.model.data.customer.server.entity.CustomerTypePaymentMethod;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;

public class CustomerTypePaymentMethodTransferCache
        extends BaseCustomerTransferCache<CustomerTypePaymentMethod, CustomerTypePaymentMethodTransfer> {

    PaymentMethodControl paymentMethodControl = Session.getModelController(PaymentMethodControl.class);

    /** Creates a new instance of CustomerTypePaymentMethodTransferCache */
    public CustomerTypePaymentMethodTransferCache(UserVisit userVisit, CustomerControl customerControl) {
        super(userVisit, customerControl);
    }
    
    public CustomerTypePaymentMethodTransfer getCustomerTypePaymentMethodTransfer(CustomerTypePaymentMethod customerTypePaymentMethod) {
        CustomerTypePaymentMethodTransfer customerTypePaymentMethodTransfer = get(customerTypePaymentMethod);
        
        if(customerTypePaymentMethodTransfer == null) {
            CustomerTypeTransfer customerType = customerControl.getCustomerTypeTransfer(userVisit, customerTypePaymentMethod.getCustomerType());
            PaymentMethodTransfer paymentMethod = paymentMethodControl.getPaymentMethodTransfer(userVisit, customerTypePaymentMethod.getPaymentMethod());
            Integer defaultSelectionPriority = customerTypePaymentMethod.getDefaultSelectionPriority();
            Boolean isDefault = customerTypePaymentMethod.getIsDefault();
            Integer sortOrder = customerTypePaymentMethod.getSortOrder();
            
            customerTypePaymentMethodTransfer = new CustomerTypePaymentMethodTransfer(customerType, paymentMethod, defaultSelectionPriority, isDefault,
                    sortOrder);
            put(customerTypePaymentMethod, customerTypePaymentMethodTransfer);
        }
        
        return customerTypePaymentMethodTransfer;
    }
    
}
