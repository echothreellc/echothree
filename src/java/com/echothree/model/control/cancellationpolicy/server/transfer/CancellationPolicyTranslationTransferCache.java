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

import com.echothree.model.control.cancellationpolicy.common.transfer.CancellationPolicyTranslationTransfer;
import com.echothree.model.control.cancellationpolicy.server.control.CancellationPolicyControl;
import com.echothree.model.control.core.server.control.MimeTypeControl;
import com.echothree.model.data.cancellationpolicy.server.entity.CancellationPolicyTranslation;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;

public class CancellationPolicyTranslationTransferCache
        extends BaseCancellationPolicyDescriptionTransferCache<CancellationPolicyTranslation, CancellationPolicyTranslationTransfer> {

    MimeTypeControl mimeTypeControl = Session.getModelController(MimeTypeControl.class);
    
    /** Creates a new instance of CancellationPolicyTranslationTransferCache */
    public CancellationPolicyTranslationTransferCache(CancellationPolicyControl cancellationPolicyControl) {
        super(cancellationPolicyControl);
    }
    
    public CancellationPolicyTranslationTransfer getCancellationPolicyTranslationTransfer(CancellationPolicyTranslation cancellationPolicyTranslation) {
        var cancellationPolicyTranslationTransfer = get(cancellationPolicyTranslation);
        
        if(cancellationPolicyTranslationTransfer == null) {
            var languageTransfer = partyControl.getLanguageTransfer(userVisit, cancellationPolicyTranslation.getLanguage());
            var cancellationPolicyTransfer = cancellationPolicyControl.getCancellationPolicyTransfer(userVisit, cancellationPolicyTranslation.getCancellationPolicy());
            var description = cancellationPolicyTranslation.getDescription();
            var policyMimeType = cancellationPolicyTranslation.getPolicyMimeType();
            var policyMimeTypeTransfer = policyMimeType == null? null: mimeTypeControl.getMimeTypeTransfer(userVisit, policyMimeType);
            var policy = cancellationPolicyTranslation.getPolicy();
            
            cancellationPolicyTranslationTransfer = new CancellationPolicyTranslationTransfer(cancellationPolicyTransfer, languageTransfer, description,
                    policyMimeTypeTransfer, policy);
            put(userVisit, cancellationPolicyTranslation, cancellationPolicyTranslationTransfer);
        }
        
        return cancellationPolicyTranslationTransfer;
    }
    
}
