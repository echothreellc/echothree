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

import com.echothree.model.control.security.server.control.SecurityControl;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.transfer.BaseTransferCaches;

public class SecurityTransferCaches
        extends BaseTransferCaches {
    
    protected SecurityControl securityControl;
    
    protected SecurityRoleGroupTransferCache securityRoleGroupTransferCache;
    protected SecurityRoleGroupDescriptionTransferCache securityRoleGroupDescriptionTransferCache;
    protected SecurityRoleTransferCache securityRoleTransferCache;
    protected SecurityRoleDescriptionTransferCache securityRoleDescriptionTransferCache;
    protected SecurityRolePartyTypeTransferCache securityRolePartyTypeTransferCache;
    protected PartySecurityRoleTemplateTransferCache partySecurityRoleTemplateTransferCache;
    protected PartySecurityRoleTemplateDescriptionTransferCache partySecurityRoleTemplateDescriptionTransferCache;
    protected PartySecurityRoleTemplateRoleTransferCache partySecurityRoleTemplateRoleTransferCache;
    protected PartySecurityRoleTemplateTrainingClassTransferCache partySecurityRoleTemplateTrainingClassTransferCache;
    
    /** Creates a new instance of SecurityTransferCaches */
    public SecurityTransferCaches(SecurityControl securityControl) {
        super();
        
        this.securityControl = securityControl;
    }
    
    public SecurityRoleGroupTransferCache getSecurityRoleGroupTransferCache() {
        if(securityRoleGroupTransferCache == null)
            securityRoleGroupTransferCache = new SecurityRoleGroupTransferCache(securityControl);
        
        return securityRoleGroupTransferCache;
    }
    
    public SecurityRoleGroupDescriptionTransferCache getSecurityRoleGroupDescriptionTransferCache() {
        if(securityRoleGroupDescriptionTransferCache == null)
            securityRoleGroupDescriptionTransferCache = new SecurityRoleGroupDescriptionTransferCache(securityControl);
        
        return securityRoleGroupDescriptionTransferCache;
    }
    
    public SecurityRoleTransferCache getSecurityRoleTransferCache() {
        if(securityRoleTransferCache == null)
            securityRoleTransferCache = new SecurityRoleTransferCache(securityControl);
        
        return securityRoleTransferCache;
    }
    
    public SecurityRoleDescriptionTransferCache getSecurityRoleDescriptionTransferCache() {
        if(securityRoleDescriptionTransferCache == null)
            securityRoleDescriptionTransferCache = new SecurityRoleDescriptionTransferCache(securityControl);
        
        return securityRoleDescriptionTransferCache;
    }
    
    public SecurityRolePartyTypeTransferCache getSecurityRolePartyTypeTransferCache() {
        if(securityRolePartyTypeTransferCache == null)
            securityRolePartyTypeTransferCache = new SecurityRolePartyTypeTransferCache(securityControl);
        
        return securityRolePartyTypeTransferCache;
    }
    
    public PartySecurityRoleTemplateTransferCache getPartySecurityRoleTemplateTransferCache() {
        if(partySecurityRoleTemplateTransferCache == null)
            partySecurityRoleTemplateTransferCache = new PartySecurityRoleTemplateTransferCache(securityControl);
        
        return partySecurityRoleTemplateTransferCache;
    }
    
    public PartySecurityRoleTemplateDescriptionTransferCache getPartySecurityRoleTemplateDescriptionTransferCache() {
        if(partySecurityRoleTemplateDescriptionTransferCache == null)
            partySecurityRoleTemplateDescriptionTransferCache = new PartySecurityRoleTemplateDescriptionTransferCache(securityControl);
        
        return partySecurityRoleTemplateDescriptionTransferCache;
    }
    
    public PartySecurityRoleTemplateRoleTransferCache getPartySecurityRoleTemplateRoleTransferCache() {
        if(partySecurityRoleTemplateRoleTransferCache == null)
            partySecurityRoleTemplateRoleTransferCache = new PartySecurityRoleTemplateRoleTransferCache(securityControl);
        
        return partySecurityRoleTemplateRoleTransferCache;
    }
    
    public PartySecurityRoleTemplateTrainingClassTransferCache getPartySecurityRoleTemplateTrainingClassTransferCache() {
        if(partySecurityRoleTemplateTrainingClassTransferCache == null)
            partySecurityRoleTemplateTrainingClassTransferCache = new PartySecurityRoleTemplateTrainingClassTransferCache(securityControl);
        
        return partySecurityRoleTemplateTrainingClassTransferCache;
    }
    
}
