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

package com.echothree.control.user.customer.common.result;

import com.echothree.model.control.customer.common.transfer.CustomerTypePaymentMethodTransfer;
import com.echothree.model.control.customer.common.transfer.CustomerTypeTransfer;
import com.echothree.util.common.command.BaseResult;
import java.util.List;

public interface GetCustomerTypePaymentMethodsResult
        extends BaseResult {
    
    CustomerTypeTransfer getCustomerType();
    void setCustomerType(CustomerTypeTransfer customerType);
    
    List<CustomerTypePaymentMethodTransfer> getCustomerTypePaymentMethods();
    void setCustomerTypePaymentMethods(List<CustomerTypePaymentMethodTransfer> customerTypePaymentMethods);
    
}
