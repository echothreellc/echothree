// --------------------------------------------------------------------------------
// Copyright 2002-2020 Echo Three, LLC
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

import com.echothree.model.control.returnpolicy.common.transfer.ReturnKindTransfer;
import com.echothree.model.control.returnpolicy.common.transfer.ReturnPolicyTransfer;
import com.echothree.model.control.returnpolicy.server.ReturnPolicyControl;
import com.echothree.model.data.returnpolicy.server.entity.ReturnPolicy;
import com.echothree.model.data.returnpolicy.server.entity.ReturnPolicyDetail;
import com.echothree.model.data.returnpolicy.server.entity.ReturnPolicyTranslation;
import com.echothree.model.data.user.server.entity.UserVisit;

public class ReturnPolicyTransferCache
        extends BaseReturnPolicyTransferCache<ReturnPolicy, ReturnPolicyTransfer> {
    
    /** Creates a new instance of ReturnPolicyTransferCache */
    public ReturnPolicyTransferCache(UserVisit userVisit, ReturnPolicyControl returnPolicyControl) {
        super(userVisit, returnPolicyControl);

        setIncludeEntityInstance(true);
    }
    
    public ReturnPolicyTransfer getReturnPolicyTransfer(ReturnPolicy returnPolicy) {
        ReturnPolicyTransfer returnPolicyTransfer = get(returnPolicy);
        
        if(returnPolicyTransfer == null) {
            ReturnPolicyDetail returnPolicyDetail = returnPolicy.getLastDetail();
            ReturnKindTransfer returnKind = returnPolicyControl.getReturnKindTransfer(userVisit, returnPolicyDetail.getReturnKind());
            String returnPolicyName = returnPolicyDetail.getReturnPolicyName();
            Boolean isDefault = returnPolicyDetail.getIsDefault();
            Integer sortOrder = returnPolicyDetail.getSortOrder();
            ReturnPolicyTranslation returnPolicyTranslation = returnPolicyControl.getBestReturnPolicyTranslation(returnPolicy, getLanguage());
            String description = returnPolicyTranslation == null ? returnPolicyName : returnPolicyTranslation.getDescription();
            
            returnPolicyTransfer = new ReturnPolicyTransfer(returnKind, returnPolicyName, isDefault, sortOrder, description);
            put(returnPolicy, returnPolicyTransfer);
        }
        return returnPolicyTransfer;
    }
    
}
