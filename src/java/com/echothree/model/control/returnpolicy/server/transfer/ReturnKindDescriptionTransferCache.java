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

package com.echothree.model.control.returnpolicy.server.transfer;

import com.echothree.model.control.returnpolicy.common.transfer.ReturnKindDescriptionTransfer;
import com.echothree.model.control.returnpolicy.server.control.ReturnPolicyControl;
import com.echothree.model.data.returnpolicy.server.entity.ReturnKindDescription;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class ReturnKindDescriptionTransferCache
        extends BaseReturnPolicyDescriptionTransferCache<ReturnKindDescription, ReturnKindDescriptionTransfer> {

    ReturnPolicyControl returnPolicyControl = Session.getModelController(ReturnPolicyControl.class);

    /** Creates a new instance of ReturnKindDescriptionTransferCache */
    protected ReturnKindDescriptionTransferCache() {
        super();
    }
    
    public ReturnKindDescriptionTransfer getReturnKindDescriptionTransfer(UserVisit userVisit, ReturnKindDescription returnKindDescription) {
        var returnKindDescriptionTransfer = get(returnKindDescription);
        
        if(returnKindDescriptionTransfer == null) {
            var returnKindTransfer = returnPolicyControl.getReturnKindTransfer(userVisit, returnKindDescription.getReturnKind());
            var languageTransfer = partyControl.getLanguageTransfer(userVisit, returnKindDescription.getLanguage());
            
            returnKindDescriptionTransfer = new ReturnKindDescriptionTransfer(languageTransfer, returnKindTransfer, returnKindDescription.getDescription());
            put(userVisit, returnKindDescription, returnKindDescriptionTransfer);
        }
        
        return returnKindDescriptionTransfer;
    }
    
}
