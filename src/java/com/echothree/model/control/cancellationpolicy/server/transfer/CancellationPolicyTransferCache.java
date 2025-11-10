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

import com.echothree.model.control.cancellationpolicy.common.transfer.CancellationPolicyTransfer;
import com.echothree.model.control.cancellationpolicy.server.control.CancellationPolicyControl;
import com.echothree.model.data.cancellationpolicy.server.entity.CancellationPolicy;
import com.echothree.model.data.user.server.entity.UserVisit;

public class CancellationPolicyTransferCache
        extends BaseCancellationPolicyTransferCache<CancellationPolicy, CancellationPolicyTransfer> {
    
    /** Creates a new instance of CancellationPolicyTransferCache */
    public CancellationPolicyTransferCache(UserVisit userVisit, CancellationPolicyControl cancellationPolicyControl) {
        super(userVisit, cancellationPolicyControl);
        
        setIncludeEntityInstance(true);
    }
    
    public CancellationPolicyTransfer getCancellationPolicyTransfer(CancellationPolicy cancellationPolicy) {
        var cancellationPolicyTransfer = get(cancellationPolicy);
        
        if(cancellationPolicyTransfer == null) {
            var cancellationPolicyDetail = cancellationPolicy.getLastDetail();
            var cancellationKind = cancellationPolicyControl.getCancellationKindTransfer(userVisit, cancellationPolicyDetail.getCancellationKind());
            var cancellationPolicyName = cancellationPolicyDetail.getCancellationPolicyName();
            var isDefault = cancellationPolicyDetail.getIsDefault();
            var sortOrder = cancellationPolicyDetail.getSortOrder();
            var cancellationPolicyTranslation = cancellationPolicyControl.getBestCancellationPolicyTranslation(cancellationPolicy, getLanguage(userVisit));
            var description = cancellationPolicyTranslation == null ? cancellationPolicyName : cancellationPolicyTranslation.getDescription();
            
            cancellationPolicyTransfer = new CancellationPolicyTransfer(cancellationKind, cancellationPolicyName, isDefault, sortOrder, description);
            put(userVisit, cancellationPolicy, cancellationPolicyTransfer);
        }
        return cancellationPolicyTransfer;
    }
    
}
