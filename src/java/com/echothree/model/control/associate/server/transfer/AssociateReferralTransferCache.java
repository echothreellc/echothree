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

import com.echothree.model.control.associate.common.transfer.AssociateReferralTransfer;
import com.echothree.model.control.associate.server.control.AssociateControl;
import com.echothree.model.control.core.server.control.EntityInstanceControl;
import com.echothree.model.data.associate.server.entity.AssociateReferral;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;

public class AssociateReferralTransferCache
        extends BaseAssociateTransferCache<AssociateReferral, AssociateReferralTransfer> {

    EntityInstanceControl entityInstanceControl = Session.getModelController(EntityInstanceControl.class);
    
    /** Creates a new instance of AssociateReferralTransferCache */
    public AssociateReferralTransferCache(UserVisit userVisit, AssociateControl associateControl) {
        super(userVisit, associateControl);
        
        setIncludeEntityInstance(true);
    }
    
    @Override
    public AssociateReferralTransfer getTransfer(AssociateReferral associateReferral) {
        var associateReferralTransfer = get(associateReferral);
        
        if(associateReferralTransfer == null) {
            var associateReferralDetail = associateReferral.getLastDetail();
            var associateReferralName = associateReferralDetail.getAssociateReferralName();
            var associateTransfer = associateControl.getAssociateTransfer(userVisit, associateReferralDetail.getAssociate());
            var associatePartyContactMechanism = associateReferralDetail.getAssociatePartyContactMechanism();
            var associatePartyContactMechanismTransfer = associatePartyContactMechanism == null ? null : associateControl.getAssociatePartyContactMechanismTransfer(userVisit, associatePartyContactMechanism);
            var targetEntityInstance = associateReferralDetail.getTargetEntityInstance();
            var targetEntityInstanceTransfer = targetEntityInstance == null ? null : entityInstanceControl.getEntityInstanceTransfer(userVisit, targetEntityInstance, false, false, false, false);
            var unformattedAssociateReferralTime = associateReferralDetail.getAssociateReferralTime();
            var associateReferralTime = formatTypicalDateTime(unformattedAssociateReferralTime);

            associateReferralTransfer = new AssociateReferralTransfer(associateReferralName, associateTransfer, associatePartyContactMechanismTransfer,
                    targetEntityInstanceTransfer, unformattedAssociateReferralTime, associateReferralTime);
            put(associateReferral, associateReferralTransfer);
        }
        
        return associateReferralTransfer;
    }
    
}
