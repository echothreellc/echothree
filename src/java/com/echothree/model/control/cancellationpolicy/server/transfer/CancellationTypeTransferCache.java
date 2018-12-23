// --------------------------------------------------------------------------------
// Copyright 2002-2019 Echo Three, LLC
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
import com.echothree.model.control.cancellationpolicy.common.transfer.CancellationTypeTransfer;
import com.echothree.model.control.cancellationpolicy.server.CancellationPolicyControl;
import com.echothree.model.control.sequence.common.transfer.SequenceTransfer;
import com.echothree.model.control.sequence.server.SequenceControl;
import com.echothree.model.data.cancellationpolicy.server.entity.CancellationType;
import com.echothree.model.data.cancellationpolicy.server.entity.CancellationTypeDetail;
import com.echothree.model.data.sequence.server.entity.Sequence;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;

public class CancellationTypeTransferCache
        extends BaseCancellationPolicyTransferCache<CancellationType, CancellationTypeTransfer> {
    
    SequenceControl sequenceControl = (SequenceControl)Session.getModelController(SequenceControl.class);
    
    /** Creates a new instance of CancellationTypeTransferCache */
    public CancellationTypeTransferCache(UserVisit userVisit, CancellationPolicyControl cancellationPolicyControl) {
        super(userVisit, cancellationPolicyControl);
        
        setIncludeEntityInstance(true);
    }
    
    public CancellationTypeTransfer getCancellationTypeTransfer(CancellationType cancellationType) {
        CancellationTypeTransfer cancellationTypeTransfer = get(cancellationType);
        
        if(cancellationTypeTransfer == null) {
            CancellationTypeDetail cancellationTypeDetail = cancellationType.getLastDetail();
            CancellationKindTransfer cancellationKindTransfer = cancellationPolicyControl.getCancellationKindTransfer(userVisit, cancellationTypeDetail.getCancellationKind());
            String cancellationTypeName = cancellationTypeDetail.getCancellationTypeName();
            Sequence cancellationSequence = cancellationTypeDetail.getCancellationSequence();
            SequenceTransfer cancellationSequenceTransfer = cancellationSequence == null? null: sequenceControl.getSequenceTransfer(userVisit, cancellationSequence);
            Boolean isDefault = cancellationTypeDetail.getIsDefault();
            Integer sortOrder = cancellationTypeDetail.getSortOrder();
            String description = cancellationPolicyControl.getBestCancellationTypeDescription(cancellationType, getLanguage());
            
            cancellationTypeTransfer = new CancellationTypeTransfer(cancellationKindTransfer, cancellationTypeName, cancellationSequenceTransfer, isDefault,
                    sortOrder, description);
            put(cancellationType, cancellationTypeTransfer);
        }
        
        return cancellationTypeTransfer;
    }
    
}
