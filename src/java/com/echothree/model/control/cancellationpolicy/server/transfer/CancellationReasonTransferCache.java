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

import com.echothree.model.control.cancellationpolicy.common.transfer.CancellationReasonTransfer;
import com.echothree.model.control.cancellationpolicy.server.control.CancellationPolicyControl;
import com.echothree.model.data.cancellationpolicy.server.entity.CancellationReason;
import com.echothree.model.data.user.server.entity.UserVisit;

public class CancellationReasonTransferCache
        extends BaseCancellationPolicyTransferCache<CancellationReason, CancellationReasonTransfer> {
    
    /** Creates a new instance of CancellationReasonTransferCache */
    public CancellationReasonTransferCache(CancellationPolicyControl cancellationPolicyControl) {
        super(cancellationPolicyControl);
        
        setIncludeEntityInstance(true);
    }
    
    public CancellationReasonTransfer getCancellationReasonTransfer(CancellationReason cancellationReason) {
        var cancellationReasonTransfer = get(cancellationReason);
        
        if(cancellationReasonTransfer == null) {
            var cancellationReasonDetail = cancellationReason.getLastDetail();
            var cancellationKind = cancellationPolicyControl.getCancellationKindTransfer(userVisit, cancellationReasonDetail.getCancellationKind());
            var cancellationReasonName = cancellationReasonDetail.getCancellationReasonName();
            var isDefault = cancellationReasonDetail.getIsDefault();
            var sortOrder = cancellationReasonDetail.getSortOrder();
            var description = cancellationPolicyControl.getBestCancellationReasonDescription(cancellationReason, getLanguage(userVisit));
            
            cancellationReasonTransfer = new CancellationReasonTransfer(cancellationKind, cancellationReasonName, isDefault, sortOrder, description);
            put(userVisit, cancellationReason, cancellationReasonTransfer);
        }
        
        return cancellationReasonTransfer;
    }
    
}
