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

import com.echothree.util.server.transfer.BaseTransferCaches;
import javax.enterprise.inject.spi.CDI;

public class SecurityTransferCaches
        extends BaseTransferCaches {
    
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
    public SecurityTransferCaches() {
        super();
    }
    
    public SecurityRoleGroupTransferCache getSecurityRoleGroupTransferCache() {
        if(securityRoleGroupTransferCache == null)
            securityRoleGroupTransferCache = CDI.current().select(SecurityRoleGroupTransferCache.class).get();
        
        return securityRoleGroupTransferCache;
    }
    
    public SecurityRoleGroupDescriptionTransferCache getSecurityRoleGroupDescriptionTransferCache() {
        if(securityRoleGroupDescriptionTransferCache == null)
            securityRoleGroupDescriptionTransferCache = CDI.current().select(SecurityRoleGroupDescriptionTransferCache.class).get();
        
        return securityRoleGroupDescriptionTransferCache;
    }
    
    public SecurityRoleTransferCache getSecurityRoleTransferCache() {
        if(securityRoleTransferCache == null)
            securityRoleTransferCache = CDI.current().select(SecurityRoleTransferCache.class).get();
        
        return securityRoleTransferCache;
    }
    
    public SecurityRoleDescriptionTransferCache getSecurityRoleDescriptionTransferCache() {
        if(securityRoleDescriptionTransferCache == null)
            securityRoleDescriptionTransferCache = CDI.current().select(SecurityRoleDescriptionTransferCache.class).get();
        
        return securityRoleDescriptionTransferCache;
    }
    
    public SecurityRolePartyTypeTransferCache getSecurityRolePartyTypeTransferCache() {
        if(securityRolePartyTypeTransferCache == null)
            securityRolePartyTypeTransferCache = CDI.current().select(SecurityRolePartyTypeTransferCache.class).get();
        
        return securityRolePartyTypeTransferCache;
    }
    
    public PartySecurityRoleTemplateTransferCache getPartySecurityRoleTemplateTransferCache() {
        if(partySecurityRoleTemplateTransferCache == null)
            partySecurityRoleTemplateTransferCache = CDI.current().select(PartySecurityRoleTemplateTransferCache.class).get();
        
        return partySecurityRoleTemplateTransferCache;
    }
    
    public PartySecurityRoleTemplateDescriptionTransferCache getPartySecurityRoleTemplateDescriptionTransferCache() {
        if(partySecurityRoleTemplateDescriptionTransferCache == null)
            partySecurityRoleTemplateDescriptionTransferCache = CDI.current().select(PartySecurityRoleTemplateDescriptionTransferCache.class).get();
        
        return partySecurityRoleTemplateDescriptionTransferCache;
    }
    
    public PartySecurityRoleTemplateRoleTransferCache getPartySecurityRoleTemplateRoleTransferCache() {
        if(partySecurityRoleTemplateRoleTransferCache == null)
            partySecurityRoleTemplateRoleTransferCache = CDI.current().select(PartySecurityRoleTemplateRoleTransferCache.class).get();
        
        return partySecurityRoleTemplateRoleTransferCache;
    }
    
    public PartySecurityRoleTemplateTrainingClassTransferCache getPartySecurityRoleTemplateTrainingClassTransferCache() {
        if(partySecurityRoleTemplateTrainingClassTransferCache == null)
            partySecurityRoleTemplateTrainingClassTransferCache = CDI.current().select(PartySecurityRoleTemplateTrainingClassTransferCache.class).get();
        
        return partySecurityRoleTemplateTrainingClassTransferCache;
    }
    
}
