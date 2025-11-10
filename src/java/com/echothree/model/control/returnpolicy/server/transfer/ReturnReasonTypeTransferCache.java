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

import com.echothree.model.control.returnpolicy.common.transfer.ReturnReasonTypeTransfer;
import com.echothree.model.control.returnpolicy.server.control.ReturnPolicyControl;
import com.echothree.model.data.returnpolicy.server.entity.ReturnReasonType;
import com.echothree.model.data.user.server.entity.UserVisit;

public class ReturnReasonTypeTransferCache
        extends BaseReturnPolicyTransferCache<ReturnReasonType, ReturnReasonTypeTransfer> {
    
    /** Creates a new instance of ReturnReasonTypeTransferCache */
    public ReturnReasonTypeTransferCache(UserVisit userVisit, ReturnPolicyControl returnPolicyControl) {
        super(userVisit, returnPolicyControl);
    }
    
    public ReturnReasonTypeTransfer getReturnReasonTypeTransfer(ReturnReasonType returnReasonType) {
        var returnReasonTypeTransfer = get(returnReasonType);
        
        if(returnReasonTypeTransfer == null) {
            var returnReason = returnPolicyControl.getReturnReasonTransfer(userVisit, returnReasonType.getReturnReason());
            var returnType = returnPolicyControl.getReturnTypeTransfer(userVisit, returnReasonType.getReturnType());
            var isDefault = returnReasonType.getIsDefault();
            var sortOrder = returnReasonType.getSortOrder();
            
            returnReasonTypeTransfer = new ReturnReasonTypeTransfer(returnReason, returnType, isDefault, sortOrder);
            put(userVisit, returnReasonType, returnReasonTypeTransfer);
        }
        
        return returnReasonTypeTransfer;
    }
    
}
