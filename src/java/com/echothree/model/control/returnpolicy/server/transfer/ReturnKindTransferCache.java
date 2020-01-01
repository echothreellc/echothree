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
import com.echothree.model.control.returnpolicy.server.ReturnPolicyControl;
import com.echothree.model.control.sequence.common.transfer.SequenceTypeTransfer;
import com.echothree.model.control.sequence.server.SequenceControl;
import com.echothree.model.data.returnpolicy.server.entity.ReturnKind;
import com.echothree.model.data.returnpolicy.server.entity.ReturnKindDetail;
import com.echothree.model.data.sequence.server.entity.SequenceType;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;

public class ReturnKindTransferCache
        extends BaseReturnPolicyTransferCache<ReturnKind, ReturnKindTransfer> {
    
    SequenceControl sequenceControl = (SequenceControl)Session.getModelController(SequenceControl.class);
    
    /** Creates a new instance of ReturnKindTransferCache */
    public ReturnKindTransferCache(UserVisit userVisit, ReturnPolicyControl returnPolicyControl) {
        super(userVisit, returnPolicyControl);
        
        setIncludeEntityInstance(true);
    }
    
    public ReturnKindTransfer getReturnKindTransfer(ReturnKind returnKind) {
        ReturnKindTransfer returnKindTransfer = get(returnKind);
        
        if(returnKindTransfer == null) {
            ReturnKindDetail returnKindDetail = returnKind.getLastDetail();
            String returnKindName = returnKindDetail.getReturnKindName();
            SequenceType returnSequenceType = returnKindDetail.getReturnSequenceType();
            SequenceTypeTransfer returnSequenceTypeTransfer = returnSequenceType == null? null: sequenceControl.getSequenceTypeTransfer(userVisit, returnSequenceType);
            Boolean isDefault = returnKindDetail.getIsDefault();
            Integer sortOrder = returnKindDetail.getSortOrder();
            String description = returnPolicyControl.getBestReturnKindDescription(returnKind, getLanguage());
            
            returnKindTransfer = new ReturnKindTransfer(returnKindName, returnSequenceTypeTransfer, isDefault, sortOrder, description);
            put(returnKind, returnKindTransfer);
        }
        
        return returnKindTransfer;
    }
    
}
