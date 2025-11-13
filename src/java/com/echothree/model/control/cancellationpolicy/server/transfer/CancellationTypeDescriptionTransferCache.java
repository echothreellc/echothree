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

import com.echothree.model.control.cancellationpolicy.common.transfer.CancellationTypeDescriptionTransfer;
import com.echothree.model.control.cancellationpolicy.server.control.CancellationPolicyControl;
import com.echothree.model.data.cancellationpolicy.server.entity.CancellationTypeDescription;
import com.echothree.model.data.user.server.entity.UserVisit;

public class CancellationTypeDescriptionTransferCache
        extends BaseCancellationPolicyDescriptionTransferCache<CancellationTypeDescription, CancellationTypeDescriptionTransfer> {
    
    /** Creates a new instance of CancellationTypeDescriptionTransferCache */
    public CancellationTypeDescriptionTransferCache(CancellationPolicyControl cancellationPolicyControl) {
        super(cancellationPolicyControl);
    }
    
    public CancellationTypeDescriptionTransfer getCancellationTypeDescriptionTransfer(UserVisit userVisit, CancellationTypeDescription cancellationTypeDescription) {
        var cancellationTypeDescriptionTransfer = get(cancellationTypeDescription);
        
        if(cancellationTypeDescriptionTransfer == null) {
            var cancellationTypeTransfer = cancellationPolicyControl.getCancellationTypeTransfer(userVisit, cancellationTypeDescription.getCancellationType());
            var languageTransfer = partyControl.getLanguageTransfer(userVisit, cancellationTypeDescription.getLanguage());
            
            cancellationTypeDescriptionTransfer = new CancellationTypeDescriptionTransfer(languageTransfer, cancellationTypeTransfer, cancellationTypeDescription.getDescription());
            put(userVisit, cancellationTypeDescription, cancellationTypeDescriptionTransfer);
        }
        
        return cancellationTypeDescriptionTransfer;
    }
    
}
