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

import com.echothree.model.control.cancellationpolicy.common.transfer.CancellationPolicyTransfer;
import com.echothree.model.control.cancellationpolicy.common.transfer.CancellationPolicyTranslationTransfer;
import com.echothree.model.control.cancellationpolicy.server.CancellationPolicyControl;
import com.echothree.model.control.core.common.transfer.MimeTypeTransfer;
import com.echothree.model.control.core.server.CoreControl;
import com.echothree.model.control.party.common.transfer.LanguageTransfer;
import com.echothree.model.data.cancellationpolicy.server.entity.CancellationPolicyTranslation;
import com.echothree.model.data.core.server.entity.MimeType;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;

public class CancellationPolicyTranslationTransferCache
        extends BaseCancellationPolicyDescriptionTransferCache<CancellationPolicyTranslation, CancellationPolicyTranslationTransfer> {
    
    CoreControl coreControl = (CoreControl)Session.getModelController(CoreControl.class);
    
    /** Creates a new instance of CancellationPolicyTranslationTransferCache */
    public CancellationPolicyTranslationTransferCache(UserVisit userVisit, CancellationPolicyControl cancellationPolicyControl) {
        super(userVisit, cancellationPolicyControl);
    }
    
    public CancellationPolicyTranslationTransfer getCancellationPolicyTranslationTransfer(CancellationPolicyTranslation cancellationPolicyTranslation) {
        CancellationPolicyTranslationTransfer cancellationPolicyTranslationTransfer = get(cancellationPolicyTranslation);
        
        if(cancellationPolicyTranslationTransfer == null) {
            LanguageTransfer languageTransfer = partyControl.getLanguageTransfer(userVisit, cancellationPolicyTranslation.getLanguage());
            CancellationPolicyTransfer cancellationPolicyTransfer = cancellationPolicyControl.getCancellationPolicyTransfer(userVisit, cancellationPolicyTranslation.getCancellationPolicy());
            String description = cancellationPolicyTranslation.getDescription();
            MimeType policyMimeType = cancellationPolicyTranslation.getPolicyMimeType();
            MimeTypeTransfer policyMimeTypeTransfer = policyMimeType == null? null: coreControl.getMimeTypeTransfer(userVisit, policyMimeType);
            String policy = cancellationPolicyTranslation.getPolicy();
            
            cancellationPolicyTranslationTransfer = new CancellationPolicyTranslationTransfer(cancellationPolicyTransfer, languageTransfer, description,
                    policyMimeTypeTransfer, policy);
            put(cancellationPolicyTranslation, cancellationPolicyTranslationTransfer);
        }
        
        return cancellationPolicyTranslationTransfer;
    }
    
}
