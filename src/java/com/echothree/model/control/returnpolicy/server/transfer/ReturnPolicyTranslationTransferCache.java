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

package com.echothree.model.control.returnpolicy.server.transfer;

import com.echothree.model.control.core.server.control.MimeTypeControl;
import com.echothree.model.control.returnpolicy.common.transfer.ReturnPolicyTranslationTransfer;
import com.echothree.model.control.returnpolicy.server.control.ReturnPolicyControl;
import com.echothree.model.data.returnpolicy.server.entity.ReturnPolicyTranslation;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;

public class ReturnPolicyTranslationTransferCache
        extends BaseReturnPolicyDescriptionTransferCache<ReturnPolicyTranslation, ReturnPolicyTranslationTransfer> {

    MimeTypeControl mimeTypeControl = Session.getModelController(MimeTypeControl.class);
    ReturnPolicyControl returnPolicyControl = Session.getModelController(ReturnPolicyControl.class);

    /** Creates a new instance of ReturnPolicyTranslationTransferCache */
    public ReturnPolicyTranslationTransferCache() {
        super();
    }
    
    public ReturnPolicyTranslationTransfer getReturnPolicyTranslationTransfer(UserVisit userVisit, ReturnPolicyTranslation returnPolicyTranslation) {
        var returnPolicyTranslationTransfer = get(returnPolicyTranslation);
        
        if(returnPolicyTranslationTransfer == null) {
            var languageTransfer = partyControl.getLanguageTransfer(userVisit, returnPolicyTranslation.getLanguage());
            var returnPolicyTransfer = returnPolicyControl.getReturnPolicyTransfer(userVisit, returnPolicyTranslation.getReturnPolicy());
            var description = returnPolicyTranslation.getDescription();
            var policyMimeType = returnPolicyTranslation.getPolicyMimeType();
            var policyMimeTypeTransfer = policyMimeType == null? null: mimeTypeControl.getMimeTypeTransfer(userVisit, policyMimeType);
            var policy = returnPolicyTranslation.getPolicy();
            
            returnPolicyTranslationTransfer = new ReturnPolicyTranslationTransfer(returnPolicyTransfer, languageTransfer, description,
                    policyMimeTypeTransfer, policy);
            put(userVisit, returnPolicyTranslation, returnPolicyTranslationTransfer);
        }
        
        return returnPolicyTranslationTransfer;
    }
    
}
