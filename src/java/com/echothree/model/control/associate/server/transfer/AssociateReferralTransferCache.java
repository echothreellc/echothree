// --------------------------------------------------------------------------------
// Copyright 2002-2023 Echo Three, LLC
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

import com.echothree.model.control.associate.common.transfer.AssociatePartyContactMechanismTransfer;
import com.echothree.model.control.associate.common.transfer.AssociateReferralTransfer;
import com.echothree.model.control.associate.common.transfer.AssociateTransfer;
import com.echothree.model.control.associate.server.control.AssociateControl;
import com.echothree.model.control.core.common.transfer.EntityInstanceTransfer;
import com.echothree.model.control.core.server.control.CoreControl;
import com.echothree.model.data.associate.server.entity.AssociatePartyContactMechanism;
import com.echothree.model.data.associate.server.entity.AssociateReferral;
import com.echothree.model.data.associate.server.entity.AssociateReferralDetail;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;

public class AssociateReferralTransferCache
        extends BaseAssociateTransferCache<AssociateReferral, AssociateReferralTransfer> {
    
    CoreControl coreControl = Session.getModelController(CoreControl.class);
    
    /** Creates a new instance of AssociateReferralTransferCache */
    public AssociateReferralTransferCache(UserVisit userVisit, AssociateControl associateControl) {
        super(userVisit, associateControl);
        
        setIncludeEntityInstance(true);
    }
    
    @Override
    public AssociateReferralTransfer getTransfer(AssociateReferral associateReferral) {
        AssociateReferralTransfer associateReferralTransfer = get(associateReferral);
        
        if(associateReferralTransfer == null) {
            AssociateReferralDetail associateReferralDetail = associateReferral.getLastDetail();
            String associateReferralName = associateReferralDetail.getAssociateReferralName();
            AssociateTransfer associateTransfer = associateControl.getAssociateTransfer(userVisit, associateReferralDetail.getAssociate());
            AssociatePartyContactMechanism associatePartyContactMechanism = associateReferralDetail.getAssociatePartyContactMechanism();
            AssociatePartyContactMechanismTransfer associatePartyContactMechanismTransfer = associatePartyContactMechanism == null ? null : associateControl.getAssociatePartyContactMechanismTransfer(userVisit, associatePartyContactMechanism);
            EntityInstance targetEntityInstance = associateReferralDetail.getTargetEntityInstance();
            EntityInstanceTransfer targetEntityInstanceTransfer = targetEntityInstance == null ? null : coreControl.getEntityInstanceTransfer(userVisit, targetEntityInstance, false, false, false, false, false, false);
            Long unformattedAssociateReferralTime = associateReferralDetail.getAssociateReferralTime();
            String associateReferralTime = formatTypicalDateTime(unformattedAssociateReferralTime);

            associateReferralTransfer = new AssociateReferralTransfer(associateReferralName, associateTransfer, associatePartyContactMechanismTransfer,
                    targetEntityInstanceTransfer, unformattedAssociateReferralTime, associateReferralTime);
            put(associateReferral, associateReferralTransfer);
        }
        
        return associateReferralTransfer;
    }
    
}
