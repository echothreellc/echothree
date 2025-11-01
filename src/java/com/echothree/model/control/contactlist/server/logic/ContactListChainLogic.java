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

package com.echothree.model.control.contactlist.server.logic;

import com.echothree.model.control.chain.common.ChainConstants;
import com.echothree.model.control.chain.server.control.ChainControl;
import com.echothree.model.control.chain.server.logic.BaseChainLogic;
import com.echothree.model.data.chain.server.entity.Chain;
import com.echothree.model.data.chain.server.entity.ChainInstance;
import com.echothree.model.data.contactlist.server.entity.PartyContactList;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.Session;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.spi.CDI;

@ApplicationScoped
public class ContactListChainLogic
        extends BaseChainLogic {

    protected ContactListChainLogic() {
        super();
    }

    public static ContactListChainLogic getInstance() {
        return CDI.current().select(ContactListChainLogic.class).get();
    }
    
    /** In looking for the Chain, the following are checked:
     * 1) Check for PartyTypeContactList, if exists, use it; skip 2-3.
     * 2) Check for PartyTypeContactListGroup, if exists, use it; skip 3.
     * 3) Check for default for the particular type of chain needed.
     */
    protected ChainInstance createChainInstance(final ExecutionErrorAccumulator eea, final String chainKindName, final String chainTypeName,
            final PartyContactList partyContactList, final BasePK createdBy) {
        var partyContactListDetail = partyContactList.getLastDetail();
        var party = partyContactListDetail.getParty();
        var contactListTypeDetail = partyContactListDetail.getContactList().getLastDetail().getContactListType().getLastDetail();
        Chain chain = null;
        ChainInstance chainInstance = null;
        
        if(chainTypeName.equals(ChainConstants.ChainType_CONFIRMATION_REQUEST)) {
            chain = contactListTypeDetail.getConfirmationRequestChain();
        } else if(chainTypeName.equals(ChainConstants.ChainType_SUBSCRIBE)) {
            chain = contactListTypeDetail.getSubscribeChain();
        } else if(chainTypeName.equals(ChainConstants.ChainType_UNSUBSCRIBE)) {
            chain = contactListTypeDetail.getUnsubscribeChain();
        }
        
        if(chain == null) {
            var chainType = getChainTypeByName(eea, chainKindName, chainTypeName);
            
            if(chain != null) {
                chain = getChain(eea, chainType, party);
            }
        }
        
        if(chain != null) {
            chainInstance = createChainInstance(eea, chain, createdBy);
        }
        
        return chainInstance;
    }
    
    protected ChainInstance createContactListChainInstance(final ExecutionErrorAccumulator eea, final String chainTypeName,
            final PartyContactList partyContactList, final BasePK createdBy) {
        var chainInstance = createChainInstance(eea, ChainConstants.ChainKind_CONTACT_LIST, chainTypeName, partyContactList, createdBy);
        
        if(chainInstance != null) {
            var chainControl = Session.getModelController(ChainControl.class);
            var chainType = chainInstance.getLastDetail().getChain().getLastDetail().getChainType();
            
            chainControl.createChainInstanceEntityRole(chainInstance, chainControl.getChainEntityRoleTypeByName(chainType,
                    ChainConstants.ChainEntityRoleType_PARTY_CONTACT_LIST), partyContactList.getPrimaryKey(), createdBy);
        }
        
        return chainInstance;
    }
    
    public ChainInstance createContactListConfirmationChainInstance(final ExecutionErrorAccumulator eea, final Party party, final PartyContactList partyContactList,
            final BasePK createdBy) {
        return createContactListChainInstance(eea, ChainConstants.ChainType_CONFIRMATION_REQUEST, partyContactList, createdBy);
    }
    
    public ChainInstance createContactListSubscribeChainInstance(final ExecutionErrorAccumulator eea, final Party party, final PartyContactList partyContactList,
            final BasePK createdBy) {
        return createContactListChainInstance(eea, ChainConstants.ChainType_SUBSCRIBE, partyContactList, createdBy);
    }
    
    public ChainInstance createContactListUnsubscribeChainInstance(final ExecutionErrorAccumulator eea, final Party party, final PartyContactList partyContactList,
            final BasePK createdBy) {
        return createContactListChainInstance(eea, ChainConstants.ChainType_UNSUBSCRIBE, partyContactList, createdBy);
    }
    
}
