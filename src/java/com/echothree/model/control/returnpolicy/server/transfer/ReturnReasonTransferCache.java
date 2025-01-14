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

import com.echothree.model.control.returnpolicy.common.transfer.ReturnReasonTransfer;
import com.echothree.model.control.returnpolicy.server.control.ReturnPolicyControl;
import com.echothree.model.data.returnpolicy.server.entity.ReturnReason;
import com.echothree.model.data.user.server.entity.UserVisit;

public class ReturnReasonTransferCache
        extends BaseReturnPolicyTransferCache<ReturnReason, ReturnReasonTransfer> {
    
    /** Creates a new instance of ReturnReasonTransferCache */
    public ReturnReasonTransferCache(UserVisit userVisit, ReturnPolicyControl returnPolicyControl) {
        super(userVisit, returnPolicyControl);
        
        setIncludeEntityInstance(true);
    }
    
    public ReturnReasonTransfer getReturnReasonTransfer(ReturnReason returnReason) {
        var returnReasonTransfer = get(returnReason);
        
        if(returnReasonTransfer == null) {
            var returnReasonDetail = returnReason.getLastDetail();
            var returnKind = returnPolicyControl.getReturnKindTransfer(userVisit, returnReasonDetail.getReturnKind());
            var returnReasonName = returnReasonDetail.getReturnReasonName();
            var isDefault = returnReasonDetail.getIsDefault();
            var sortOrder = returnReasonDetail.getSortOrder();
            var description = returnPolicyControl.getBestReturnReasonDescription(returnReason, getLanguage());
            
            returnReasonTransfer = new ReturnReasonTransfer(returnKind, returnReasonName, isDefault, sortOrder, description);
            put(returnReason, returnReasonTransfer);
        }
        
        return returnReasonTransfer;
    }
    
}
