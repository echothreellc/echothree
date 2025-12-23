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

import com.echothree.model.control.cancellationpolicy.common.transfer.CancellationPolicyReasonTransfer;
import com.echothree.model.control.cancellationpolicy.server.control.CancellationPolicyControl;
import com.echothree.model.data.cancellationpolicy.server.entity.CancellationPolicyReason;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class CancellationPolicyReasonTransferCache
        extends BaseCancellationPolicyTransferCache<CancellationPolicyReason, CancellationPolicyReasonTransfer> {

    CancellationPolicyControl cancellationPolicyControl = Session.getModelController(CancellationPolicyControl.class);

    /** Creates a new instance of CancellationPolicyReasonTransferCache */
    protected CancellationPolicyReasonTransferCache() {
        super();
    }
    
    public CancellationPolicyReasonTransfer getCancellationPolicyReasonTransfer(UserVisit userVisit, CancellationPolicyReason cancellationPolicyReason) {
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
