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

import com.echothree.util.server.transfer.BaseTransferCaches;
import javax.enterprise.inject.spi.CDI;

public class AssociateTransferCaches
        extends BaseTransferCaches {
    
    protected AssociateProgramTransferCache associateProgramTransferCache;
    protected AssociateProgramDescriptionTransferCache associateProgramDescriptionTransferCache;
    protected AssociateTransferCache associateTransferCache;
    protected AssociatePartyContactMechanismTransferCache associatePartyContactMechanismTransferCache;
    protected AssociateReferralTransferCache associateReferralTransferCache;
    
    /** Creates a new instance of AssociateTransferCaches */
    public AssociateTransferCaches() {
        super();
    }
    
    public AssociateProgramTransferCache getAssociateProgramTransferCache() {
        if(associateProgramTransferCache == null)
            associateProgramTransferCache = CDI.current().select(AssociateProgramTransferCache.class).get();
        
        return associateProgramTransferCache;
    }
    
    public AssociateProgramDescriptionTransferCache getAssociateProgramDescriptionTransferCache() {
        if(associateProgramDescriptionTransferCache == null)
            associateProgramDescriptionTransferCache = CDI.current().select(AssociateProgramDescriptionTransferCache.class).get();
        
        return associateProgramDescriptionTransferCache;
    }
    
    public AssociateTransferCache getAssociateTransferCache() {
        if(associateTransferCache == null)
            associateTransferCache = CDI.current().select(AssociateTransferCache.class).get();
        
        return associateTransferCache;
    }
    
    public AssociatePartyContactMechanismTransferCache getAssociatePartyContactMechanismTransferCache() {
        if(associatePartyContactMechanismTransferCache == null)
            associatePartyContactMechanismTransferCache = CDI.current().select(AssociatePartyContactMechanismTransferCache.class).get();
        
        return associatePartyContactMechanismTransferCache;
    }
    
    public AssociateReferralTransferCache getAssociateReferralTransferCache() {
        if(associateReferralTransferCache == null)
            associateReferralTransferCache = CDI.current().select(AssociateReferralTransferCache.class).get();
        
        return associateReferralTransferCache;
    }
    
}
