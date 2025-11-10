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

import com.echothree.model.control.cancellationpolicy.common.transfer.CancellationPolicyReasonTransfer;
import com.echothree.model.control.cancellationpolicy.server.control.CancellationPolicyControl;
import com.echothree.model.data.cancellationpolicy.server.entity.CancellationPolicyReason;
import com.echothree.model.data.user.server.entity.UserVisit;

public class CancellationPolicyReasonTransferCache
        extends BaseCancellationPolicyTransferCache<CancellationPolicyReason, CancellationPolicyReasonTransfer> {
    
    /** Creates a new instance of CancellationPolicyReasonTransferCache */
    public CancellationPolicyReasonTransferCache(UserVisit userVisit, CancellationPolicyControl cancellationPolicyControl) {
        super(userVisit, cancellationPolicyControl);
    }
    
    public CancellationPolicyReasonTransfer getCancellationPolicyReasonTransfer(CancellationPolicyReason cancellationPolicyReason) {
        var cancellationPolicyReasonTransfer = get(cancellationPolicyReason);
        
        if(cancellationPolicyReasonTransfer == null) {
            var cancellationPolicy = cancellationPolicyControl.getCancellationPolicyTransfer(userVisit, cancellationPolicyReason.getCancellationPolicy());
            var cancellationReason = cancellationPolicyControl.getCancellationReasonTransfer(userVisit, cancellationPolicyReason.getCancellationReason());
            var isDefault = cancellationPolicyReason.getIsDefault();
            var sortOrder = cancellationPolicyReason.getSortOrder();
            
            cancellationPolicyReasonTransfer = new CancellationPolicyReasonTransfer(cancellationPolicy, cancellationReason, isDefault, sortOrder);
            put(userVisit, cancellationPolicyReason, cancellationPolicyReasonTransfer);
        }
        
        return cancellationPolicyReasonTransfer;
    }
    
}
