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

package com.echothree.model.control.returnpolicy.server.transfer;

import com.echothree.model.control.returnpolicy.common.transfer.ReturnTypeShippingMethodTransfer;
import com.echothree.model.control.returnpolicy.server.control.ReturnPolicyControl;
import com.echothree.model.control.shipping.server.control.ShippingControl;
import com.echothree.model.data.returnpolicy.server.entity.ReturnTypeShippingMethod;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;

public class ReturnTypeShippingMethodTransferCache
        extends BaseReturnPolicyTransferCache<ReturnTypeShippingMethod, ReturnTypeShippingMethodTransfer> {
    
    ShippingControl shippingControl;
    
    /** Creates a new instance of ReturnTypeShippingMethodTransferCache */
    public ReturnTypeShippingMethodTransferCache(ReturnPolicyControl returnPolicyControl) {
        super(returnPolicyControl);
        
        shippingControl = Session.getModelController(ShippingControl.class);
    }
    
    public ReturnTypeShippingMethodTransfer getReturnTypeShippingMethodTransfer(ReturnTypeShippingMethod returnTypeShippingMethod) {
        var returnTypeShippingMethodTransfer = get(returnTypeShippingMethod);
        
        if(returnTypeShippingMethodTransfer == null) {
            var returnType = returnPolicyControl.getReturnTypeTransfer(userVisit, returnTypeShippingMethod.getReturnType());
            var shippingMethod = shippingControl.getShippingMethodTransfer(userVisit, returnTypeShippingMethod.getShippingMethod());
            var isDefault = returnTypeShippingMethod.getIsDefault();
            var sortOrder = returnTypeShippingMethod.getSortOrder();
            
            returnTypeShippingMethodTransfer = new ReturnTypeShippingMethodTransfer(returnType, shippingMethod, isDefault,
                    sortOrder);
            put(userVisit, returnTypeShippingMethod, returnTypeShippingMethodTransfer);
        }
        
        return returnTypeShippingMethodTransfer;
    }
    
}
