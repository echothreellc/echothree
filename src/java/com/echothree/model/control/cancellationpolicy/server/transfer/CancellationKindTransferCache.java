// --------------------------------------------------------------------------------
// Copyright 2002-2022 Echo Three, LLC
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

package com.echothree.model.control.cancellationpolicy.server.transfer;

import com.echothree.model.control.cancellationpolicy.common.transfer.CancellationKindTransfer;
import com.echothree.model.control.cancellationpolicy.server.control.CancellationPolicyControl;
import com.echothree.model.control.sequence.common.transfer.SequenceTypeTransfer;
import com.echothree.model.control.sequence.server.control.SequenceControl;
import com.echothree.model.data.cancellationpolicy.server.entity.CancellationKind;
import com.echothree.model.data.cancellationpolicy.server.entity.CancellationKindDetail;
import com.echothree.model.data.sequence.server.entity.SequenceType;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;

public class CancellationKindTransferCache
        extends BaseCancellationPolicyTransferCache<CancellationKind, CancellationKindTransfer> {
    
    SequenceControl sequenceControl = Session.getModelController(SequenceControl.class);
    
    /** Creates a new instance of CancellationKindTransferCache */
    public CancellationKindTransferCache(UserVisit userVisit, CancellationPolicyControl cancellationPolicyControl) {
        super(userVisit, cancellationPolicyControl);
        
        setIncludeEntityInstance(true);
    }
    
    public CancellationKindTransfer getCancellationKindTransfer(CancellationKind cancellationKind) {
        CancellationKindTransfer cancellationKindTransfer = get(cancellationKind);
        
        if(cancellationKindTransfer == null) {
            CancellationKindDetail cancellationKindDetail = cancellationKind.getLastDetail();
            String cancellationKindName = cancellationKindDetail.getCancellationKindName();
            SequenceType cancellationSequenceType = cancellationKindDetail.getCancellationSequenceType();
            SequenceTypeTransfer cancellationSequenceTypeTransfer = cancellationSequenceType == null? null: sequenceControl.getSequenceTypeTransfer(userVisit, cancellationSequenceType);
            Boolean isDefault = cancellationKindDetail.getIsDefault();
            Integer sortOrder = cancellationKindDetail.getSortOrder();
            String description = cancellationPolicyControl.getBestCancellationKindDescription(cancellationKind, getLanguage());
            
            cancellationKindTransfer = new CancellationKindTransfer(cancellationKindName, cancellationSequenceTypeTransfer, isDefault, sortOrder, description);
            put(cancellationKind, cancellationKindTransfer);
        }
        
        return cancellationKindTransfer;
    }
    
}
