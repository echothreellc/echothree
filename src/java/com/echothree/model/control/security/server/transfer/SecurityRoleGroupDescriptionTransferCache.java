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

import com.echothree.model.control.party.common.transfer.LanguageTransfer;
import com.echothree.model.control.security.common.transfer.SecurityRoleGroupDescriptionTransfer;
import com.echothree.model.control.security.common.transfer.SecurityRoleGroupTransfer;
import com.echothree.model.control.security.server.control.SecurityControl;
import com.echothree.model.data.security.server.entity.SecurityRoleGroupDescription;
import com.echothree.model.data.user.server.entity.UserVisit;

public class SecurityRoleGroupDescriptionTransferCache
        extends BaseSecurityDescriptionTransferCache<SecurityRoleGroupDescription, SecurityRoleGroupDescriptionTransfer> {
    
    /** Creates a new instance of SecurityRoleGroupDescriptionTransferCache */
    public SecurityRoleGroupDescriptionTransferCache(UserVisit userVisit, SecurityControl securityControl) {
        super(userVisit, securityControl);
    }
    
    public SecurityRoleGroupDescriptionTransfer getSecurityRoleGroupDescriptionTransfer(SecurityRoleGroupDescription securityRoleGroupDescription) {
        SecurityRoleGroupDescriptionTransfer securityRoleGroupDescriptionTransfer = get(securityRoleGroupDescription);
        
        if(securityRoleGroupDescriptionTransfer == null) {
            SecurityRoleGroupTransferCache securityRoleGroupTransferCache = securityControl.getSecurityTransferCaches(userVisit).getSecurityRoleGroupTransferCache();
            SecurityRoleGroupTransfer securityRoleGroupTransfer = securityRoleGroupTransferCache.getSecurityRoleGroupTransfer(securityRoleGroupDescription.getSecurityRoleGroup());
            LanguageTransfer languageTransfer = partyControl.getLanguageTransfer(userVisit, securityRoleGroupDescription.getLanguage());
            
            securityRoleGroupDescriptionTransfer = new SecurityRoleGroupDescriptionTransfer(languageTransfer, securityRoleGroupTransfer, securityRoleGroupDescription.getDescription());
            put(securityRoleGroupDescription, securityRoleGroupDescriptionTransfer);
        }
        
        return securityRoleGroupDescriptionTransfer;
    }
    
}
