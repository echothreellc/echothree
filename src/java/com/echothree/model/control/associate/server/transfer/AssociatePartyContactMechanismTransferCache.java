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

import com.echothree.model.control.associate.common.transfer.AssociatePartyContactMechanismTransfer;
import com.echothree.model.control.associate.server.control.AssociateControl;
import com.echothree.model.control.contact.server.control.ContactControl;
import com.echothree.model.data.associate.server.entity.AssociatePartyContactMechanism;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;

public class AssociatePartyContactMechanismTransferCache
        extends BaseAssociateTransferCache<AssociatePartyContactMechanism, AssociatePartyContactMechanismTransfer> {

    AssociateControl associateControl = Session.getModelController(AssociateControl.class);
    ContactControl contactControl = Session.getModelController(ContactControl.class);
    
    /** Creates a new instance of AssociatePartyContactMechanismTransferCache */
    public AssociatePartyContactMechanismTransferCache() {
        super();
        
        setIncludeEntityInstance(true);
    }
    
    @Override
    public AssociatePartyContactMechanismTransfer getTransfer(UserVisit userVisit, AssociatePartyContactMechanism associatePartyContactMechanism) {
        var associatePartyContactMechanismTransfer = get(associatePartyContactMechanism);
        
        if(associatePartyContactMechanismTransfer == null) {
            var associatePartyContactMechanismDetail = associatePartyContactMechanism.getLastDetail();
            var associate = associateControl.getAssociateTransfer(userVisit, associatePartyContactMechanismDetail.getAssociate());
            var associatePartyContactMechanismName = associatePartyContactMechanismDetail.getAssociatePartyContactMechanismName();
            var partyContactMechanism = contactControl.getPartyContactMechanismTransfer(userVisit, associatePartyContactMechanismDetail.getPartyContactMechanism());
            var isDefault = associatePartyContactMechanismDetail.getIsDefault();
            var sortOrder = associatePartyContactMechanismDetail.getSortOrder();
            
            associatePartyContactMechanismTransfer = new AssociatePartyContactMechanismTransfer(associate, associatePartyContactMechanismName,
                    partyContactMechanism, isDefault, sortOrder);
            put(userVisit, associatePartyContactMechanism, associatePartyContactMechanismTransfer);
        }
        return associatePartyContactMechanismTransfer;
    }
    
}
