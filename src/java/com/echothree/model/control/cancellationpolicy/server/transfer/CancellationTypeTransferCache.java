// --------------------------------------------------------------------------------
// Copyright 2002-2026 Echo Three, LLC
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

import com.echothree.model.control.cancellationpolicy.common.transfer.CancellationTypeTransfer;
import com.echothree.model.control.cancellationpolicy.server.control.CancellationPolicyControl;
import com.echothree.model.control.sequence.server.control.SequenceControl;
import com.echothree.model.data.cancellationpolicy.server.entity.CancellationType;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class CancellationTypeTransferCache
        extends BaseCancellationPolicyTransferCache<CancellationType, CancellationTypeTransfer> {

    CancellationPolicyControl cancellationPolicyControl = Session.getModelController(CancellationPolicyControl.class);
    SequenceControl sequenceControl = Session.getModelController(SequenceControl.class);
    
    /** Creates a new instance of CancellationTypeTransferCache */
    protected CancellationTypeTransferCache() {
        super();
        
        setIncludeEntityInstance(true);
    }
    
    public CancellationTypeTransfer getCancellationTypeTransfer(UserVisit userVisit, CancellationType cancellationType) {
        var cancellationTypeTransfer = get(cancellationType);
        
        if(cancellationTypeTransfer == null) {
            var cancellationTypeDetail = cancellationType.getLastDetail();
            var cancellationKindTransfer = cancellationPolicyControl.getCancellationKindTransfer(userVisit, cancellationTypeDetail.getCancellationKind());
            var cancellationTypeName = cancellationTypeDetail.getCancellationTypeName();
            var cancellationSequence = cancellationTypeDetail.getCancellationSequence();
            var cancellationSequenceTransfer = cancellationSequence == null? null: sequenceControl.getSequenceTransfer(userVisit, cancellationSequence);
            var isDefault = cancellationTypeDetail.getIsDefault();
            var sortOrder = cancellationTypeDetail.getSortOrder();
            var description = cancellationPolicyControl.getBestCancellationTypeDescription(cancellationType, getLanguage(userVisit));
            
            cancellationTypeTransfer = new CancellationTypeTransfer(cancellationKindTransfer, cancellationTypeName, cancellationSequenceTransfer, isDefault,
                    sortOrder, description);
            put(userVisit, cancellationType, cancellationTypeTransfer);
        }
        
        return cancellationTypeTransfer;
    }
    
}
