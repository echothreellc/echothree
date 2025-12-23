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

package com.echothree.model.control.shipping.common.transfer;

import com.echothree.util.common.transfer.BaseTransfer;

public class ShippingMethodResultTransfer
        extends BaseTransfer {
    
    private String shippingMethodName;
    private ShippingMethodTransfer shippingMethod;
    
    /** Creates a new instance of ShippingMethodResultTransfer */
    public ShippingMethodResultTransfer(String shippingMethodName,
            ShippingMethodTransfer shippingMethod) {
        this.shippingMethodName = shippingMethodName;
        this.shippingMethod = shippingMethod;
    }

    /**
     * Returns the shippingMethodName.
     * @return the shippingMethodName
     */
    public String getShippingMethodName() {
        return shippingMethodName;
    }

    /**
     * Sets the shippingMethodName.
     * @param shippingMethodName the shippingMethodName to set
     */
    public void setShippingMethodName(String shippingMethodName) {
        this.shippingMethodName = shippingMethodName;
    }

    /**
     * Returns the shippingMethod.
     * @return the shippingMethod
     */
    public ShippingMethodTransfer getShippingMethod() {
        return shippingMethod;
    }

    /**
     * Sets the shippingMethod.
     * @param shippingMethod the shippingMethod to set
     */
    public void setShippingMethod(ShippingMethodTransfer shippingMethod) {
        this.shippingMethod = shippingMethod;
    }

 }
