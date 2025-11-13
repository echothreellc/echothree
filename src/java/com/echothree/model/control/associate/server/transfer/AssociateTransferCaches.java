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

import com.echothree.model.control.associate.server.control.AssociateControl;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.transfer.BaseTransferCaches;

public class AssociateTransferCaches
        extends BaseTransferCaches {
    
    protected AssociateControl associateControl;
    
    protected AssociateProgramTransferCache associateProgramTransferCache;
    protected AssociateProgramDescriptionTransferCache associateProgramDescriptionTransferCache;
    protected AssociateTransferCache associateTransferCache;
    protected AssociatePartyContactMechanismTransferCache associatePartyContactMechanismTransferCache;
    protected AssociateReferralTransferCache associateReferralTransferCache;
    
    /** Creates a new instance of AssociateTransferCaches */
    public AssociateTransferCaches(AssociateControl associateControl) {
        super();
        
        this.associateControl = associateControl;
    }
    
    public AssociateProgramTransferCache getAssociateProgramTransferCache() {
        if(associateProgramTransferCache == null)
            associateProgramTransferCache = new AssociateProgramTransferCache(associateControl);
        
        return associateProgramTransferCache;
    }
    
    public AssociateProgramDescriptionTransferCache getAssociateProgramDescriptionTransferCache() {
        if(associateProgramDescriptionTransferCache == null)
            associateProgramDescriptionTransferCache = new AssociateProgramDescriptionTransferCache(associateControl);
        
        return associateProgramDescriptionTransferCache;
    }
    
    public AssociateTransferCache getAssociateTransferCache() {
        if(associateTransferCache == null)
            associateTransferCache = new AssociateTransferCache(associateControl);
        
        return associateTransferCache;
    }
    
    public AssociatePartyContactMechanismTransferCache getAssociatePartyContactMechanismTransferCache() {
        if(associatePartyContactMechanismTransferCache == null)
            associatePartyContactMechanismTransferCache = new AssociatePartyContactMechanismTransferCache(associateControl);
        
        return associatePartyContactMechanismTransferCache;
    }
    
    public AssociateReferralTransferCache getAssociateReferralTransferCache() {
        if(associateReferralTransferCache == null)
            associateReferralTransferCache = new AssociateReferralTransferCache(associateControl);
        
        return associateReferralTransferCache;
    }
    
}
