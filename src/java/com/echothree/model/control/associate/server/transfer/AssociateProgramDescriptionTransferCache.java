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

package com.echothree.model.control.associate.server.transfer;

import com.echothree.model.control.associate.common.transfer.AssociateProgramDescriptionTransfer;
import com.echothree.model.control.associate.server.control.AssociateControl;
import com.echothree.model.data.associate.server.entity.AssociateProgramDescription;
import com.echothree.model.data.user.server.entity.UserVisit;

public class AssociateProgramDescriptionTransferCache
        extends BaseAssociateDescriptionTransferCache<AssociateProgramDescription, AssociateProgramDescriptionTransfer> {
    
    /** Creates a new instance of AssociateProgramDescriptionTransferCache */
    public AssociateProgramDescriptionTransferCache(UserVisit userVisit, AssociateControl associateControl) {
        super(userVisit, associateControl);
    }
    
    @Override
    public AssociateProgramDescriptionTransfer getTransfer(AssociateProgramDescription associateProgramDescription) {
        var associateProgramDescriptionTransfer = get(associateProgramDescription);
        
        if(associateProgramDescriptionTransfer == null) {
            var associateProgramTransfer = associateControl.getAssociateProgramTransfer(userVisit, associateProgramDescription.getAssociateProgram());
            var languageTransfer = partyControl.getLanguageTransfer(userVisit, associateProgramDescription.getLanguage());
            
            associateProgramDescriptionTransfer = new AssociateProgramDescriptionTransfer(languageTransfer, associateProgramTransfer, associateProgramDescription.getDescription());
            put(userVisit, associateProgramDescription, associateProgramDescriptionTransfer);
        }
        
        return associateProgramDescriptionTransfer;
    }
    
}
