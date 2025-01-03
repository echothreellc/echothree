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

import com.echothree.model.control.returnpolicy.common.transfer.ReturnKindTransfer;
import com.echothree.model.control.returnpolicy.server.control.ReturnPolicyControl;
import com.echothree.model.control.sequence.server.control.SequenceControl;
import com.echothree.model.data.returnpolicy.server.entity.ReturnKind;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;

public class ReturnKindTransferCache
        extends BaseReturnPolicyTransferCache<ReturnKind, ReturnKindTransfer> {
    
    SequenceControl sequenceControl = Session.getModelController(SequenceControl.class);
    
    /** Creates a new instance of ReturnKindTransferCache */
    public ReturnKindTransferCache(UserVisit userVisit, ReturnPolicyControl returnPolicyControl) {
        super(userVisit, returnPolicyControl);
        
        setIncludeEntityInstance(true);
    }
    
    public ReturnKindTransfer getReturnKindTransfer(ReturnKind returnKind) {
        var returnKindTransfer = get(returnKind);
        
        if(returnKindTransfer == null) {
            var returnKindDetail = returnKind.getLastDetail();
            var returnKindName = returnKindDetail.getReturnKindName();
            var returnSequenceType = returnKindDetail.getReturnSequenceType();
            var returnSequenceTypeTransfer = returnSequenceType == null? null: sequenceControl.getSequenceTypeTransfer(userVisit, returnSequenceType);
            var isDefault = returnKindDetail.getIsDefault();
            var sortOrder = returnKindDetail.getSortOrder();
            var description = returnPolicyControl.getBestReturnKindDescription(returnKind, getLanguage());
            
            returnKindTransfer = new ReturnKindTransfer(returnKindName, returnSequenceTypeTransfer, isDefault, sortOrder, description);
            put(returnKind, returnKindTransfer);
        }
        
        return returnKindTransfer;
    }
    
}
