// --------------------------------------------------------------------------------
// Copyright 2002-2020 Echo Three, LLC
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

import com.echothree.model.control.core.common.transfer.MimeTypeTransfer;
import com.echothree.model.control.core.server.control.CoreControl;
import com.echothree.model.control.party.common.transfer.LanguageTransfer;
import com.echothree.model.control.returnpolicy.common.transfer.ReturnPolicyTransfer;
import com.echothree.model.control.returnpolicy.common.transfer.ReturnPolicyTranslationTransfer;
import com.echothree.model.control.returnpolicy.server.control.ReturnPolicyControl;
import com.echothree.model.data.core.server.entity.MimeType;
import com.echothree.model.data.returnpolicy.server.entity.ReturnPolicyTranslation;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;

public class ReturnPolicyTranslationTransferCache
        extends BaseReturnPolicyDescriptionTransferCache<ReturnPolicyTranslation, ReturnPolicyTranslationTransfer> {
    
    CoreControl coreControl = (CoreControl)Session.getModelController(CoreControl.class);
    
    /** Creates a new instance of ReturnPolicyTranslationTransferCache */
    public ReturnPolicyTranslationTransferCache(UserVisit userVisit, ReturnPolicyControl returnPolicyControl) {
        super(userVisit, returnPolicyControl);
    }
    
    public ReturnPolicyTranslationTransfer getReturnPolicyTranslationTransfer(ReturnPolicyTranslation returnPolicyTranslation) {
        ReturnPolicyTranslationTransfer returnPolicyTranslationTransfer = get(returnPolicyTranslation);
        
        if(returnPolicyTranslationTransfer == null) {
            LanguageTransfer languageTransfer = partyControl.getLanguageTransfer(userVisit, returnPolicyTranslation.getLanguage());
            ReturnPolicyTransfer returnPolicyTransfer = returnPolicyControl.getReturnPolicyTransfer(userVisit, returnPolicyTranslation.getReturnPolicy());
            String description = returnPolicyTranslation.getDescription();
            MimeType policyMimeType = returnPolicyTranslation.getPolicyMimeType();
            MimeTypeTransfer policyMimeTypeTransfer = policyMimeType == null? null: coreControl.getMimeTypeTransfer(userVisit, policyMimeType);
            String policy = returnPolicyTranslation.getPolicy();
            
            returnPolicyTranslationTransfer = new ReturnPolicyTranslationTransfer(returnPolicyTransfer, languageTransfer, description,
                    policyMimeTypeTransfer, policy);
            put(returnPolicyTranslation, returnPolicyTranslationTransfer);
        }
        
        return returnPolicyTranslationTransfer;
    }
    
}
