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

package com.echothree.model.control.customer.server.transfer;

import com.echothree.model.control.customer.common.transfer.CustomerTypePaymentMethodTransfer;
import com.echothree.model.control.customer.server.control.CustomerControl;
import com.echothree.model.control.payment.server.control.PaymentMethodControl;
import com.echothree.model.data.customer.server.entity.CustomerTypePaymentMethod;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class CustomerTypePaymentMethodTransferCache
        extends BaseCustomerTransferCache<CustomerTypePaymentMethod, CustomerTypePaymentMethodTransfer> {

    CustomerControl customerControl = Session.getModelController(CustomerControl.class);
    PaymentMethodControl paymentMethodControl = Session.getModelController(PaymentMethodControl.class);

    /** Creates a new instance of CustomerTypePaymentMethodTransferCache */
    protected CustomerTypePaymentMethodTransferCache() {
        super();
    }
    
    public CustomerTypePaymentMethodTransfer getCustomerTypePaymentMethodTransfer(UserVisit userVisit, CustomerTypePaymentMethod customerTypePaymentMethod) {
        var customerTypePaymentMethodTransfer = get(customerTypePaymentMethod);
        
        if(customerTypePaymentMethodTransfer == null) {
            var customerType = customerControl.getCustomerTypeTransfer(userVisit, customerTypePaymentMethod.getCustomerType());
            var paymentMethod = paymentMethodControl.getPaymentMethodTransfer(userVisit, customerTypePaymentMethod.getPaymentMethod());
            var defaultSelectionPriority = customerTypePaymentMethod.getDefaultSelectionPriority();
            var isDefault = customerTypePaymentMethod.getIsDefault();
            var sortOrder = customerTypePaymentMethod.getSortOrder();
            
            customerTypePaymentMethodTransfer = new CustomerTypePaymentMethodTransfer(customerType, paymentMethod, defaultSelectionPriority, isDefault,
                    sortOrder);
            put(userVisit, customerTypePaymentMethod, customerTypePaymentMethodTransfer);
        }
        
        return customerTypePaymentMethodTransfer;
    }
    
}
