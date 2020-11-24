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

package com.echothree.model.control.associate.server.transfer;

import com.echothree.model.control.associate.common.transfer.AssociatePartyContactMechanismTransfer;
import com.echothree.model.control.associate.common.transfer.AssociateTransfer;
import com.echothree.model.control.associate.server.control.AssociateControl;
import com.echothree.model.control.contact.common.transfer.PartyContactMechanismTransfer;
import com.echothree.model.control.contact.server.control.ContactControl;
import com.echothree.model.data.associate.server.entity.AssociatePartyContactMechanism;
import com.echothree.model.data.associate.server.entity.AssociatePartyContactMechanismDetail;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;

public class AssociatePartyContactMechanismTransferCache
        extends BaseAssociateTransferCache<AssociatePartyContactMechanism, AssociatePartyContactMechanismTransfer> {
    
    ContactControl contactControl = Session.getModelController(ContactControl.class);
    
    /** Creates a new instance of AssociatePartyContactMechanismTransferCache */
    public AssociatePartyContactMechanismTransferCache(UserVisit userVisit, AssociateControl associateControl) {
        super(userVisit, associateControl);
        
        setIncludeEntityInstance(true);
    }
    
    @Override
    public AssociatePartyContactMechanismTransfer getTransfer(AssociatePartyContactMechanism associatePartyContactMechanism) {
        AssociatePartyContactMechanismTransfer associatePartyContactMechanismTransfer = get(associatePartyContactMechanism);
        
        if(associatePartyContactMechanismTransfer == null) {
            AssociatePartyContactMechanismDetail associatePartyContactMechanismDetail = associatePartyContactMechanism.getLastDetail();
            AssociateTransfer associate = associateControl.getAssociateTransfer(userVisit, associatePartyContactMechanismDetail.getAssociate());
            String associatePartyContactMechanismName = associatePartyContactMechanismDetail.getAssociatePartyContactMechanismName();
            PartyContactMechanismTransfer partyContactMechanism = contactControl.getPartyContactMechanismTransfer(userVisit, associatePartyContactMechanismDetail.getPartyContactMechanism());
            Boolean isDefault = associatePartyContactMechanismDetail.getIsDefault();
            Integer sortOrder = associatePartyContactMechanismDetail.getSortOrder();
            
            associatePartyContactMechanismTransfer = new AssociatePartyContactMechanismTransfer(associate, associatePartyContactMechanismName,
                    partyContactMechanism, isDefault, sortOrder);
            put(associatePartyContactMechanism, associatePartyContactMechanismTransfer);
        }
        return associatePartyContactMechanismTransfer;
    }
    
}
