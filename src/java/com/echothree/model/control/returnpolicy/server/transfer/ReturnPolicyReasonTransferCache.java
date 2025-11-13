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

import com.echothree.model.control.returnpolicy.common.transfer.ReturnPolicyReasonTransfer;
import com.echothree.model.control.returnpolicy.server.control.ReturnPolicyControl;
import com.echothree.model.data.returnpolicy.server.entity.ReturnPolicyReason;
import com.echothree.model.data.user.server.entity.UserVisit;

public class ReturnPolicyReasonTransferCache
        extends BaseReturnPolicyTransferCache<ReturnPolicyReason, ReturnPolicyReasonTransfer> {
    
    /** Creates a new instance of ReturnPolicyReasonTransferCache */
    public ReturnPolicyReasonTransferCache(ReturnPolicyControl returnPolicyControl) {
        super(returnPolicyControl);
    }
    
    public ReturnPolicyReasonTransfer getReturnPolicyReasonTransfer(UserVisit userVisit, ReturnPolicyReason returnPolicyReason) {
        var returnPolicyReasonTransfer = get(returnPolicyReason);
        
        if(returnPolicyReasonTransfer == null) {
            var returnPolicy = returnPolicyControl.getReturnPolicyTransfer(userVisit, returnPolicyReason.getReturnPolicy());
            var returnReason = returnPolicyControl.getReturnReasonTransfer(userVisit, returnPolicyReason.getReturnReason());
            var isDefault = returnPolicyReason.getIsDefault();
            var sortOrder = returnPolicyReason.getSortOrder();
            
            returnPolicyReasonTransfer = new ReturnPolicyReasonTransfer(returnPolicy, returnReason, isDefault, sortOrder);
            put(userVisit, returnPolicyReason, returnPolicyReasonTransfer);
        }
        
        return returnPolicyReasonTransfer;
    }
    
}
