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

package com.echothree.model.control.customer.common.transfer;

import com.echothree.model.control.payment.common.transfer.PaymentMethodTransfer;
import com.echothree.util.common.transfer.BaseTransfer;

public class CustomerTypePaymentMethodTransfer
        extends BaseTransfer {
    
    private CustomerTypeTransfer customerType;
    private PaymentMethodTransfer paymentMethod;
    private Integer defaultSelectionPriority;
    private Boolean isDefault;
    private Integer sortOrder;
    
    /** Creates a new instance of CustomerTypePaymentMethodTransfer */
    public CustomerTypePaymentMethodTransfer(CustomerTypeTransfer customerType, PaymentMethodTransfer paymentMethod, Integer defaultSelectionPriority,
            Boolean isDefault, Integer sortOrder) {
        this.customerType = customerType;
        this.paymentMethod = paymentMethod;
        this.defaultSelectionPriority = defaultSelectionPriority;
        this.isDefault = isDefault;
        this.sortOrder = sortOrder;
    }

    /**
     * Returns the customerType.
     * @return the customerType
     */
    public CustomerTypeTransfer getCustomerType() {
        return customerType;
    }

    /**
     * Sets the customerType.
     * @param customerType the customerType to set
     */
    public void setCustomerType(CustomerTypeTransfer customerType) {
        this.customerType = customerType;
    }

    /**
     * Returns the paymentMethod.
     * @return the paymentMethod
     */
    public PaymentMethodTransfer getPaymentMethod() {
        return paymentMethod;
    }

    /**
     * Sets the paymentMethod.
     * @param paymentMethod the paymentMethod to set
     */
    public void setPaymentMethod(PaymentMethodTransfer paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    /**
     * Returns the defaultSelectionPriority.
     * @return the defaultSelectionPriority
     */
    public Integer getDefaultSelectionPriority() {
        return defaultSelectionPriority;
    }

    /**
     * Sets the defaultSelectionPriority.
     * @param defaultSelectionPriority the defaultSelectionPriority to set
     */
    public void setDefaultSelectionPriority(Integer defaultSelectionPriority) {
        this.defaultSelectionPriority = defaultSelectionPriority;
    }

    /**
     * Returns the isDefault.
     * @return the isDefault
     */
    public Boolean getIsDefault() {
        return isDefault;
    }

    /**
     * Sets the isDefault.
     * @param isDefault the isDefault to set
     */
    public void setIsDefault(Boolean isDefault) {
        this.isDefault = isDefault;
    }

    /**
     * Returns the sortOrder.
     * @return the sortOrder
     */
    public Integer getSortOrder() {
        return sortOrder;
    }

    /**
     * Sets the sortOrder.
     * @param sortOrder the sortOrder to set
     */
    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }
    
}
