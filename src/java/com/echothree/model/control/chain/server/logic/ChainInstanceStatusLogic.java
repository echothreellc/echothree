// --------------------------------------------------------------------------------
// Copyright 2002-2026 Echo Three, LLC
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

package com.echothree.model.control.chain.server.logic;

import com.echothree.model.control.chain.common.ChainActionTypes;
import com.echothree.model.control.chain.server.control.ChainControl;
import com.echothree.model.control.contactlist.server.control.ContactListControl;
import com.echothree.model.control.core.common.ComponentVendors;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.model.control.core.server.control.ComponentControl;
import com.echothree.model.control.core.server.control.EntityTypeControl;
import com.echothree.model.control.letter.server.control.LetterControl;
import com.echothree.model.control.party.server.logic.PartyLogic;
import com.echothree.model.data.chain.server.entity.ChainAction;
import com.echothree.model.data.chain.server.entity.ChainInstance;
import com.echothree.model.data.chain.server.entity.ChainInstanceStatus;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.persistence.Session;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class ChainInstanceStatusLogic
        extends BaseLogic {

    @Inject
    ChainControl chainControl;

    @Inject
    ComponentControl componentControl;

    @Inject
    ContactListControl contactListControl;

    @Inject
    EntityTypeControl entityTypeControl;

    @Inject
    LetterControl letterControl;

    @Inject
    PartyLogic partyLogic;

    protected ChainInstanceStatusLogic() {
        super();
    }
    
    private void processChainActionLetter(final ChainInstance chainInstance, final ChainAction chainAction, final BasePK processedBy) {
        var chainActionLetter = chainControl.getChainActionLetter(chainAction);
        var letter = chainActionLetter.getLetter();
        var contactList = letter.getLastDetail().getContactList();
        
        if(contactList != null) {
            var chainType = chainInstance.getLastDetail().getChain().getLastDetail().getChainType();
            var partyEntityType = entityTypeControl.getEntityTypeByName(componentControl.getComponentVendorByName(ComponentVendors.ECHO_THREE.name()), EntityTypes.Party.name());
            
            for(var chainEntityRoleType: chainControl.getChainEntityRoleTypes(chainType)) {
                if(chainEntityRoleType.getLastDetail().getEntityType().equals(partyEntityType)) {
                    var chainInstanceEntityRole = chainControl.getChainInstanceEntityRole(chainInstance, chainEntityRoleType);
                    
                    if(chainInstanceEntityRole != null) {
                        var entityInstance = chainInstanceEntityRole.getEntityInstance();
                        var party = partyLogic.getPartyFromEntityInstance(entityInstance);
                        var partyContactList = contactListControl.getPartyContactList(party, contactList);
                        
                        if(partyContactList == null) {
                            letter = null; // Don't send.
                        }
                    }
                }
            }
        }
        
        if(letter != null) {
            letterControl.createQueuedLetter(chainInstance, letter);
        }
    }    
    
    private void processChainActionSurvey(final ChainInstance chainInstance, final ChainAction chainAction, final BasePK processedBy) {
        var chainActionSurvey = chainControl.getChainActionSurvey(chainAction);
        
        // TODO
    }    
    
    private void processChainActionChainActionSet(final Session session, final ChainInstanceStatus chainInstanceStatus, final ChainAction chainAction, final BasePK processedBy) {
        var chainActionChainActionSet = chainControl.getChainActionChainActionSet(chainAction);
        var nextChainActionSet = chainActionChainActionSet.getNextChainActionSet();
        Long nextChainActionSetTime = session.getStartTime() + chainActionChainActionSet.getDelayTime();

        chainInstanceStatus.setNextChainActionSet(nextChainActionSet);
        chainInstanceStatus.setNextChainActionSetTime(nextChainActionSetTime);
    }    
    
    public void processChainInstanceStatus(final Session session, final ChainInstanceStatus chainInstanceStatus, final BasePK processedBy) {
        var chainInstance = chainInstanceStatus.getChainInstance();
        var chainActionSet = chainInstanceStatus.getNextChainActionSet();
        var hasNextChainActionSet = false;

        for(var chainAction: chainControl.getChainActionsByChainActionSet(chainActionSet)) {
            var chainActionTypeName = chainAction.getLastDetail().getChainActionType().getLastDetail().getChainActionTypeName();
            
            if(chainActionTypeName.equals(ChainActionTypes.LETTER.name())) {
                processChainActionLetter(chainInstance, chainAction, processedBy);
            } else if(chainActionTypeName.equals(ChainActionTypes.SURVEY.name())) {
                processChainActionSurvey(chainInstance, chainAction, processedBy);
            } else if(chainActionTypeName.equals(ChainActionTypes.CHAIN_ACTION_SET.name())) {
                processChainActionChainActionSet(session, chainInstanceStatus, chainAction, processedBy);
                hasNextChainActionSet = true;
            }
        }

        if(!hasNextChainActionSet) {
            chainControl.removeChainInstanceStatusByChainInstance(chainInstance);
        }
    }
    
}
