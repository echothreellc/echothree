// --------------------------------------------------------------------------------
// Copyright 2002-2018 Echo Three, LLC
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

package com.echothree.model.control.contact.server.transfer;

import com.echothree.model.control.contact.common.ContactOptions;
import com.echothree.model.control.contact.common.transfer.ContactMechanismTransfer;
import com.echothree.model.control.contact.common.transfer.PartyContactMechanismRelationshipTransfer;
import com.echothree.model.control.contact.common.transfer.PartyContactMechanismTransfer;
import com.echothree.model.control.contact.server.ContactControl;
import com.echothree.model.control.party.common.transfer.PartyTransfer;
import com.echothree.model.control.party.server.PartyControl;
import com.echothree.model.data.contact.server.entity.PartyContactMechanism;
import com.echothree.model.data.contact.server.entity.PartyContactMechanismDetail;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.common.transfer.ListWrapper;
import com.echothree.util.server.persistence.Session;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class PartyContactMechanismTransferCache
        extends BaseContactTransferCache<PartyContactMechanism, PartyContactMechanismTransfer> {
    
    PartyControl partyControl = (PartyControl)Session.getModelController(PartyControl.class);
    boolean includePartyContactMechanismPurposes;
    boolean includePartyContactMechanismRelationships;
    boolean includePartyContactMechanismRelationshipsByFromPartyContactMechanism;
    boolean includePartyContactMechanismRelationshipsByToPartyContactMechanism;
    
    /** Creates a new instance of PartyContactMechanismTransferCache */
    public PartyContactMechanismTransferCache(UserVisit userVisit, ContactControl contactControl) {
        super(userVisit, contactControl);
        
        Set<String> options = session.getOptions();
        if(options != null) {
            includePartyContactMechanismPurposes = options.contains(ContactOptions.PartyContactMechanismIncludePartyContactMechanismPurposes);
            includePartyContactMechanismRelationships = options.contains(ContactOptions.PartyContactMechanismIncludePartyContactMechanismRelationships);
            includePartyContactMechanismRelationshipsByFromPartyContactMechanism = options.contains(ContactOptions.PartyContactMechanismIncludePartyContactMechanismRelationshipsByFromPartyContactMechanism);
            includePartyContactMechanismRelationshipsByToPartyContactMechanism = options.contains(ContactOptions.PartyContactMechanismIncludePartyContactMechanismRelationshipsByToPartyContactMechanism);
            setIncludeKey(options.contains(ContactOptions.PartyContactMechanismIncludeKey));
            setIncludeGuid(options.contains(ContactOptions.PartyContactMechanismIncludeGuid));
        }
        
        setIncludeEntityInstance(true);
    }
    
    public PartyContactMechanismTransfer getPartyContactMechanismTransfer(PartyContactMechanism partyContactMechanism) {
        PartyContactMechanismTransfer partyContactMechanismTransfer = get(partyContactMechanism);
        
        if(partyContactMechanismTransfer == null) {
            PartyContactMechanismDetail partyContactMechanismDetail = partyContactMechanism.getLastDetail();
            PartyTransfer party = partyControl.getPartyTransfer(userVisit, partyContactMechanismDetail.getParty());
            ContactMechanismTransfer contactMechanism = contactControl.getContactMechanismTransfer(userVisit, partyContactMechanismDetail.getContactMechanism());
            Boolean isDefault = partyContactMechanismDetail.getIsDefault();
            Integer sortOrder = partyContactMechanismDetail.getSortOrder();
            String description = partyContactMechanismDetail.getDescription();
            
            partyContactMechanismTransfer = new PartyContactMechanismTransfer(party, contactMechanism, isDefault, sortOrder, description);
            put(partyContactMechanism, partyContactMechanismTransfer);

            if(includePartyContactMechanismPurposes) {
                partyContactMechanismTransfer.setPartyContactMechanismPurposes(new ListWrapper<>(contactControl.getPartyContactMechanismPurposeTransfersByPartyContactMechanism(userVisit, partyContactMechanism)));
            }
            
            if(includePartyContactMechanismRelationships || includePartyContactMechanismRelationshipsByFromPartyContactMechanism || includePartyContactMechanismRelationshipsByToPartyContactMechanism) {
                Set<PartyContactMechanismRelationshipTransfer> partyContactMechanismRelationships = new HashSet<>();

                if(includePartyContactMechanismRelationships || includePartyContactMechanismRelationshipsByFromPartyContactMechanism) {
                    partyContactMechanismRelationships.addAll(contactControl.getPartyContactMechanismRelationshipTransfersByFromPartyContactMechanism(userVisit, partyContactMechanism));
                }

                if(includePartyContactMechanismRelationships || includePartyContactMechanismRelationshipsByToPartyContactMechanism) {
                    partyContactMechanismRelationships.addAll(contactControl.getPartyContactMechanismRelationshipTransfersByToPartyContactMechanism(userVisit, partyContactMechanism));
                }

                partyContactMechanismTransfer.setPartyContactMechanismRelationships(new ListWrapper<>(new ArrayList<>(partyContactMechanismRelationships)));
            }
        }
        
        return partyContactMechanismTransfer;
    }
    
}
