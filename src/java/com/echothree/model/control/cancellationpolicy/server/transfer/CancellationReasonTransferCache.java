// --------------------------------------------------------------------------------
// Copyright 2002-2018 Echo Three, LLC
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
import com.echothree.model.control.cancellationpolicy.common.transfer.CancellationReasonTransfer;
import com.echothree.model.control.cancellationpolicy.server.CancellationPolicyControl;
import com.echothree.model.data.cancellationpolicy.server.entity.CancellationReason;
import com.echothree.model.data.cancellationpolicy.server.entity.CancellationReasonDetail;
import com.echothree.model.data.user.server.entity.UserVisit;

public class CancellationReasonTransferCache
        extends BaseCancellationPolicyTransferCache<CancellationReason, CancellationReasonTransfer> {
    
    /** Creates a new instance of CancellationReasonTransferCache */
    public CancellationReasonTransferCache(UserVisit userVisit, CancellationPolicyControl cancellationPolicyControl) {
        super(userVisit, cancellationPolicyControl);
        
        setIncludeEntityInstance(true);
    }
    
    public CancellationReasonTransfer getCancellationReasonTransfer(CancellationReason cancellationReason) {
        CancellationReasonTransfer cancellationReasonTransfer = get(cancellationReason);
        
        if(cancellationReasonTransfer == null) {
            CancellationReasonDetail cancellationReasonDetail = cancellationReason.getLastDetail();
            CancellationKindTransfer cancellationKind = cancellationPolicyControl.getCancellationKindTransfer(userVisit, cancellationReasonDetail.getCancellationKind());
            String cancellationReasonName = cancellationReasonDetail.getCancellationReasonName();
            Boolean isDefault = cancellationReasonDetail.getIsDefault();
            Integer sortOrder = cancellationReasonDetail.getSortOrder();
            String description = cancellationPolicyControl.getBestCancellationReasonDescription(cancellationReason, getLanguage());
            
            cancellationReasonTransfer = new CancellationReasonTransfer(cancellationKind, cancellationReasonName, isDefault, sortOrder, description);
            put(cancellationReason, cancellationReasonTransfer);
        }
        
        return cancellationReasonTransfer;
    }
    
}
