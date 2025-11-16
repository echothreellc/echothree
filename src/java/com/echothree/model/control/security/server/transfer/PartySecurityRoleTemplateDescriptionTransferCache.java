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

package com.echothree.model.control.security.server.transfer;

import com.echothree.model.control.security.common.transfer.PartySecurityRoleTemplateDescriptionTransfer;
import com.echothree.model.control.security.server.control.SecurityControl;
import com.echothree.model.data.security.server.entity.PartySecurityRoleTemplateDescription;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class PartySecurityRoleTemplateDescriptionTransferCache
        extends BaseSecurityDescriptionTransferCache<PartySecurityRoleTemplateDescription, PartySecurityRoleTemplateDescriptionTransfer> {

    SecurityControl securityControl = Session.getModelController(SecurityControl.class);

    /** Creates a new instance of PartySecurityRoleTemplateDescriptionTransferCache */
    protected PartySecurityRoleTemplateDescriptionTransferCache() {
        super();
    }
    
    public PartySecurityRoleTemplateDescriptionTransfer getPartySecurityRoleTemplateDescriptionTransfer(UserVisit userVisit, PartySecurityRoleTemplateDescription partySecurityRoleTemplateDescription) {
        var partySecurityRoleTemplateDescriptionTransfer = get(partySecurityRoleTemplateDescription);
        
        if(partySecurityRoleTemplateDescriptionTransfer == null) {
            var partySecurityRoleTemplateTransferCache = securityControl.getSecurityTransferCaches().getPartySecurityRoleTemplateTransferCache();
            var partySecurityRoleTemplateTransfer = partySecurityRoleTemplateTransferCache.getPartySecurityRoleTemplateTransfer(userVisit, partySecurityRoleTemplateDescription.getPartySecurityRoleTemplate());
            var languageTransfer = partyControl.getLanguageTransfer(userVisit, partySecurityRoleTemplateDescription.getLanguage());
            
            partySecurityRoleTemplateDescriptionTransfer = new PartySecurityRoleTemplateDescriptionTransfer(languageTransfer, partySecurityRoleTemplateTransfer, partySecurityRoleTemplateDescription.getDescription());
            put(userVisit, partySecurityRoleTemplateDescription, partySecurityRoleTemplateDescriptionTransfer);
        }
        
        return partySecurityRoleTemplateDescriptionTransfer;
    }
    
}
