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

package com.echothree.model.control.security.server.transfer;

import com.echothree.model.control.security.common.transfer.PartySecurityRoleTemplateTransfer;
import com.echothree.model.control.security.server.control.SecurityControl;
import com.echothree.model.data.security.server.entity.PartySecurityRoleTemplate;
import com.echothree.model.data.security.server.entity.PartySecurityRoleTemplateDetail;
import com.echothree.model.data.user.server.entity.UserVisit;

public class PartySecurityRoleTemplateTransferCache
        extends BaseSecurityTransferCache<PartySecurityRoleTemplate, PartySecurityRoleTemplateTransfer> {
    
    /** Creates a new instance of PartySecurityRoleTemplateTransferCache */
    public PartySecurityRoleTemplateTransferCache(UserVisit userVisit, SecurityControl securityControl) {
        super(userVisit, securityControl);

        setIncludeEntityInstance(true);
    }
    
    public PartySecurityRoleTemplateTransfer getPartySecurityRoleTemplateTransfer(PartySecurityRoleTemplate partySecurityRoleTemplate) {
        PartySecurityRoleTemplateTransfer partySecurityRoleTemplateTransfer = get(partySecurityRoleTemplate);
        
        if(partySecurityRoleTemplateTransfer == null) {
            PartySecurityRoleTemplateDetail partySecurityRoleTemplateDetail = partySecurityRoleTemplate.getLastDetail();
            String partySecurityRoleTemplateName = partySecurityRoleTemplateDetail.getPartySecurityRoleTemplateName();
            Boolean isDefault = partySecurityRoleTemplateDetail.getIsDefault();
            Integer sortOrder = partySecurityRoleTemplateDetail.getSortOrder();
            String description = securityControl.getBestPartySecurityRoleTemplateDescription(partySecurityRoleTemplate, getLanguage());
            
            partySecurityRoleTemplateTransfer = new PartySecurityRoleTemplateTransfer(partySecurityRoleTemplateName, isDefault,
                    sortOrder, description);
            put(partySecurityRoleTemplate, partySecurityRoleTemplateTransfer);
        }
        
        return partySecurityRoleTemplateTransfer;
    }
    
}
