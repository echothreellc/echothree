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

package com.echothree.model.control.chain.server.logic;

import com.echothree.model.control.chain.common.ChainConstants;
import com.echothree.model.control.chain.server.control.ChainControl;
import com.echothree.model.control.contactlist.server.ContactListControl;
import com.echothree.model.control.core.common.ComponentVendors;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.model.control.core.server.control.CoreControl;
import com.echothree.model.control.letter.server.control.LetterControl;
import com.echothree.model.control.party.server.logic.PartyLogic;
import com.echothree.model.data.chain.server.entity.ChainAction;
import com.echothree.model.data.chain.server.entity.ChainActionChainActionSet;
import com.echothree.model.data.chain.server.entity.ChainActionLetter;
import com.echothree.model.data.chain.server.entity.ChainActionSet;
import com.echothree.model.data.chain.server.entity.ChainActionSurvey;
import com.echothree.model.data.chain.server.entity.ChainEntityRoleType;
import com.echothree.model.data.chain.server.entity.ChainInstance;
import com.echothree.model.data.chain.server.entity.ChainInstanceEntityRole;
import com.echothree.model.data.chain.server.entity.ChainInstanceStatus;
import com.echothree.model.data.chain.server.entity.ChainType;
import com.echothree.model.data.contactlist.server.entity.ContactList;
import com.echothree.model.data.contactlist.server.entity.PartyContactList;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.core.server.entity.EntityType;
import com.echothree.model.data.letter.server.entity.Letter;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.persistence.Session;

public class ChainInstanceStatusLogic {
    
    private ChainInstanceStatusLogic() {
        super();
    }
    
    private static class ChainInstanceStatusLogicHolder {
        static ChainInstanceStatusLogic instance = new ChainInstanceStatusLogic();
    }
    
    public static ChainInstanceStatusLogic getInstance() {
        return ChainInstanceStatusLogicHolder.instance;
    }
    
    private void processChainActionLetter(final ChainControl chainControl, final ChainInstance chainInstance, final ChainAction chainAction, final BasePK processedBy) {
        ChainActionLetter chainActionLetter = chainControl.getChainActionLetter(chainAction);
        Letter letter = chainActionLetter.getLetter();
        ContactList contactList = letter.getLastDetail().getContactList();
        
        if(contactList != null) {
            var coreControl = Session.getModelController(CoreControl.class);
            ChainType chainType = chainInstance.getLastDetail().getChain().getLastDetail().getChainType();
            EntityType partyEntityType = coreControl.getEntityTypeByName(coreControl.getComponentVendorByName(ComponentVendors.ECHOTHREE.name()), EntityTypes.Party.name());
            
            for(ChainEntityRoleType chainEntityRoleType: chainControl.getChainEntityRoleTypes(chainType)) {
                if(chainEntityRoleType.getLastDetail().getEntityType().equals(partyEntityType)) {
                    ChainInstanceEntityRole chainInstanceEntityRole = chainControl.getChainInstanceEntityRole(chainInstance, chainEntityRoleType);
                    
                    if(chainInstanceEntityRole != null) {
                        var contactListControl = Session.getModelController(ContactListControl.class);
                        EntityInstance entityInstance = chainInstanceEntityRole.getEntityInstance();
                        Party party = PartyLogic.getInstance().getPartyFromEntityInstance(entityInstance);
                        PartyContactList partyContactList = contactListControl.getPartyContactList(party, contactList);
                        
                        if(partyContactList == null) {
                            letter = null; // Don't send.
                        }
                    }
                }
            }
        }
        
        if(letter != null) {
            var letterControl = Session.getModelController(LetterControl.class);
            
            letterControl.createQueuedLetter(chainInstance, letter);
        }
    }    
    
    private void processChainActionSurvey(final ChainControl chainControl, final ChainInstance chainInstance, final ChainAction chainAction, final BasePK processedBy) {
        ChainActionSurvey chainActionSurvey = chainControl.getChainActionSurvey(chainAction);
        
        // TODO
    }    
    
    private void processChainActionChainActionSet(final Session session, final ChainControl chainControl, final ChainInstanceStatus chainInstanceStatus, final ChainAction chainAction, final BasePK processedBy) {
        ChainActionChainActionSet chainActionChainActionSet = chainControl.getChainActionChainActionSet(chainAction);
        ChainActionSet nextChainActionSet = chainActionChainActionSet.getNextChainActionSet();
        Long nextChainActionSetTime = session.START_TIME + chainActionChainActionSet.getDelayTime();

        chainInstanceStatus.setNextChainActionSet(nextChainActionSet);
        chainInstanceStatus.setNextChainActionSetTime(nextChainActionSetTime);
    }    
    
    public void processChainInstanceStatus(final Session session, final ChainControl chainControl, final ChainInstanceStatus chainInstanceStatus, final BasePK processedBy) {
        ChainInstance chainInstance = chainInstanceStatus.getChainInstance();
        ChainActionSet chainActionSet = chainInstanceStatus.getNextChainActionSet();
        boolean hasNextChainActionSet = false;

        for(ChainAction chainAction: chainControl.getChainActionsByChainActionSet(chainActionSet)) {
            String chainActionTypeName = chainAction.getLastDetail().getChainActionType().getLastDetail().getChainActionTypeName();
            
            if(chainActionTypeName.equals(ChainConstants.ChainActionType_LETTER)) {
                processChainActionLetter(chainControl, chainInstance, chainAction, processedBy);
            } else if(chainActionTypeName.equals(ChainConstants.ChainActionType_SURVEY)) {
                processChainActionSurvey(chainControl, chainInstance, chainAction, processedBy);
            } else if(chainActionTypeName.equals(ChainConstants.ChainActionType_CHAIN_ACTION_SET)) {
                processChainActionChainActionSet(session, chainControl, chainInstanceStatus, chainAction, processedBy);
                hasNextChainActionSet = true;
            }
        }

        if(!hasNextChainActionSet) {
            chainControl.removeChainInstanceStatusByChainInstance(chainInstance);
        }
    }
    
}
