// --------------------------------------------------------------------------------
// Copyright 2002-2022 Echo Three, LLC
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

package com.echothree.model.control.security.server.transfer;

import com.echothree.model.control.party.common.transfer.LanguageTransfer;
import com.echothree.model.control.security.common.transfer.PartySecurityRoleTemplateDescriptionTransfer;
import com.echothree.model.control.security.common.transfer.PartySecurityRoleTemplateTransfer;
import com.echothree.model.control.security.server.control.SecurityControl;
import com.echothree.model.data.security.server.entity.PartySecurityRoleTemplateDescription;
import com.echothree.model.data.user.server.entity.UserVisit;

public class PartySecurityRoleTemplateDescriptionTransferCache
        extends BaseSecurityDescriptionTransferCache<PartySecurityRoleTemplateDescription, PartySecurityRoleTemplateDescriptionTransfer> {
    
    /** Creates a new instance of PartySecurityRoleTemplateDescriptionTransferCache */
    public PartySecurityRoleTemplateDescriptionTransferCache(UserVisit userVisit, SecurityControl securityControl) {
        super(userVisit, securityControl);
    }
    
    public PartySecurityRoleTemplateDescriptionTransfer getPartySecurityRoleTemplateDescriptionTransfer(PartySecurityRoleTemplateDescription partySecurityRoleTemplateDescription) {
        PartySecurityRoleTemplateDescriptionTransfer partySecurityRoleTemplateDescriptionTransfer = get(partySecurityRoleTemplateDescription);
        
        if(partySecurityRoleTemplateDescriptionTransfer == null) {
            PartySecurityRoleTemplateTransferCache partySecurityRoleTemplateTransferCache = securityControl.getSecurityTransferCaches(userVisit).getPartySecurityRoleTemplateTransferCache();
            PartySecurityRoleTemplateTransfer partySecurityRoleTemplateTransfer = partySecurityRoleTemplateTransferCache.getPartySecurityRoleTemplateTransfer(partySecurityRoleTemplateDescription.getPartySecurityRoleTemplate());
            LanguageTransfer languageTransfer = partyControl.getLanguageTransfer(userVisit, partySecurityRoleTemplateDescription.getLanguage());
            
            partySecurityRoleTemplateDescriptionTransfer = new PartySecurityRoleTemplateDescriptionTransfer(languageTransfer, partySecurityRoleTemplateTransfer, partySecurityRoleTemplateDescription.getDescription());
            put(partySecurityRoleTemplateDescription, partySecurityRoleTemplateDescriptionTransfer);
        }
        
        return partySecurityRoleTemplateDescriptionTransfer;
    }
    
}
