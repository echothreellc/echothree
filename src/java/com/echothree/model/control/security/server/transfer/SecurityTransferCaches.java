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
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

@RequestScoped
public class SecurityTransferCaches
        extends BaseTransferCaches {
    
    @Inject
    SecurityRoleGroupTransferCache securityRoleGroupTransferCache;
    
    @Inject
    SecurityRoleGroupDescriptionTransferCache securityRoleGroupDescriptionTransferCache;
    
    @Inject
    SecurityRoleTransferCache securityRoleTransferCache;
    
    @Inject
    SecurityRoleDescriptionTransferCache securityRoleDescriptionTransferCache;
    
    @Inject
    SecurityRolePartyTypeTransferCache securityRolePartyTypeTransferCache;
    
    @Inject
    PartySecurityRoleTemplateTransferCache partySecurityRoleTemplateTransferCache;
    
    @Inject
    PartySecurityRoleTemplateDescriptionTransferCache partySecurityRoleTemplateDescriptionTransferCache;
    
    @Inject
    PartySecurityRoleTemplateRoleTransferCache partySecurityRoleTemplateRoleTransferCache;
    
    @Inject
    PartySecurityRoleTemplateTrainingClassTransferCache partySecurityRoleTemplateTrainingClassTransferCache;

    /** Creates a new instance of SecurityTransferCaches */
    protected SecurityTransferCaches() {
        super();
    }
    
    public SecurityRoleGroupTransferCache getSecurityRoleGroupTransferCache() {
        return securityRoleGroupTransferCache;
    }
    
    public SecurityRoleGroupDescriptionTransferCache getSecurityRoleGroupDescriptionTransferCache() {
        return securityRoleGroupDescriptionTransferCache;
    }
    
    public SecurityRoleTransferCache getSecurityRoleTransferCache() {
        return securityRoleTransferCache;
    }
    
    public SecurityRoleDescriptionTransferCache getSecurityRoleDescriptionTransferCache() {
        return securityRoleDescriptionTransferCache;
    }
    
    public SecurityRolePartyTypeTransferCache getSecurityRolePartyTypeTransferCache() {
        return securityRolePartyTypeTransferCache;
    }
    
    public PartySecurityRoleTemplateTransferCache getPartySecurityRoleTemplateTransferCache() {
        return partySecurityRoleTemplateTransferCache;
    }
    
    public PartySecurityRoleTemplateDescriptionTransferCache getPartySecurityRoleTemplateDescriptionTransferCache() {
        return partySecurityRoleTemplateDescriptionTransferCache;
    }
    
    public PartySecurityRoleTemplateRoleTransferCache getPartySecurityRoleTemplateRoleTransferCache() {
        return partySecurityRoleTemplateRoleTransferCache;
    }
    
    public PartySecurityRoleTemplateTrainingClassTransferCache getPartySecurityRoleTemplateTrainingClassTransferCache() {
        return partySecurityRoleTemplateTrainingClassTransferCache;
    }
    
}
