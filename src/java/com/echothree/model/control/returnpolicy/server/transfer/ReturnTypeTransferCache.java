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

import com.echothree.model.control.returnpolicy.common.transfer.ReturnTypeTransfer;
import com.echothree.model.control.returnpolicy.server.control.ReturnPolicyControl;
import com.echothree.model.control.sequence.server.control.SequenceControl;
import com.echothree.model.data.returnpolicy.server.entity.ReturnType;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;

public class ReturnTypeTransferCache
        extends BaseReturnPolicyTransferCache<ReturnType, ReturnTypeTransfer> {
    
    SequenceControl sequenceControl = Session.getModelController(SequenceControl.class);
    
    /** Creates a new instance of ReturnTypeTransferCache */
    public ReturnTypeTransferCache(UserVisit userVisit, ReturnPolicyControl returnPolicyControl) {
        super(userVisit, returnPolicyControl);
        
        setIncludeEntityInstance(true);
    }
    
    public ReturnTypeTransfer getReturnTypeTransfer(ReturnType returnType) {
        var returnTypeTransfer = get(returnType);
        
        if(returnTypeTransfer == null) {
            var returnTypeDetail = returnType.getLastDetail();
            var returnKindTransfer = returnPolicyControl.getReturnKindTransfer(userVisit, returnTypeDetail.getReturnKind());
            var returnTypeName = returnTypeDetail.getReturnTypeName();
            var returnSequence = returnTypeDetail.getReturnSequence();
            var returnSequenceTransfer = returnSequence == null? null: sequenceControl.getSequenceTransfer(userVisit, returnSequence);
            var isDefault = returnTypeDetail.getIsDefault();
            var sortOrder = returnTypeDetail.getSortOrder();
            var description = returnPolicyControl.getBestReturnTypeDescription(returnType, getLanguage(userVisit));
            
            returnTypeTransfer = new ReturnTypeTransfer(returnKindTransfer, returnTypeName, returnSequenceTransfer, isDefault, sortOrder, description);
            put(userVisit, returnType, returnTypeTransfer);
        }
        
        return returnTypeTransfer;
    }
    
}
