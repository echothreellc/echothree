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

package com.echothree.model.control.cancellationpolicy.server.transfer;

import com.echothree.model.control.cancellationpolicy.common.transfer.CancellationKindTransfer;
import com.echothree.model.control.cancellationpolicy.server.control.CancellationPolicyControl;
import com.echothree.model.control.sequence.server.control.SequenceControl;
import com.echothree.model.data.cancellationpolicy.server.entity.CancellationKind;
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
        var cancellationKindTransfer = get(cancellationKind);
        
        if(cancellationKindTransfer == null) {
            var cancellationKindDetail = cancellationKind.getLastDetail();
            var cancellationKindName = cancellationKindDetail.getCancellationKindName();
            var cancellationSequenceType = cancellationKindDetail.getCancellationSequenceType();
            var cancellationSequenceTypeTransfer = cancellationSequenceType == null? null: sequenceControl.getSequenceTypeTransfer(userVisit, cancellationSequenceType);
            var isDefault = cancellationKindDetail.getIsDefault();
            var sortOrder = cancellationKindDetail.getSortOrder();
            var description = cancellationPolicyControl.getBestCancellationKindDescription(cancellationKind, getLanguage(userVisit));
            
            cancellationKindTransfer = new CancellationKindTransfer(cancellationKindName, cancellationSequenceTypeTransfer, isDefault, sortOrder, description);
            put(userVisit, cancellationKind, cancellationKindTransfer);
        }
        
        return cancellationKindTransfer;
    }
    
}
