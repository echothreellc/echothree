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

package com.echothree.model.control.party.server.logic;

import com.echothree.model.control.chain.common.ChainConstants;
import com.echothree.model.control.chain.server.control.ChainControl;
import com.echothree.model.control.chain.server.logic.BaseChainLogic;
import com.echothree.model.control.core.server.control.EntityInstanceControl;
import com.echothree.model.data.chain.server.entity.ChainInstance;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.Session;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.spi.CDI;

@ApplicationScoped
public class PartyChainLogic
        extends BaseChainLogic {

    protected PartyChainLogic() {
        super();
    }

    public static PartyChainLogic getInstance() {
        return CDI.current().select(PartyChainLogic.class).get();
    }
    
    protected ChainInstance createPartyChainInstance(final ExecutionErrorAccumulator eea, final String chainTypeName, final Party party,
            final boolean resetChainIfRunning, final BasePK createdBy) {
        var partyTypeName = party.getLastDetail().getPartyType().getPartyTypeName(); // Used as ChainTypeName & ChainEntityRoleTypeName
        var chainType = getChainTypeByName(eea, partyTypeName, chainTypeName);
        ChainInstance chainInstance = null;
        
        if(!hasExecutionErrors(eea)) {
            var chainEntityRoleType = getChainEntityRoleTypeByName(eea, chainType, partyTypeName);
            
            if(!hasExecutionErrors(eea)) {
                var entityInstanceControl = Session.getModelController(EntityInstanceControl.class);
                var entityInstance = entityInstanceControl.getEntityInstanceByBasePK(party.getPrimaryKey());
                
                if(resetChainIfRunning) {
                    deleteChainInstancedByChainEntityRoleTypeAndEntityInstance(chainEntityRoleType, entityInstance, createdBy);
                }
                
                chainInstance = createChainInstance(eea, chainType, party, createdBy);

                if(!hasExecutionErrors(eea) && chainInstance != null) {
                    var chainControl = Session.getModelController(ChainControl.class);

                    chainControl.createChainInstanceEntityRole(chainInstance, chainEntityRoleType, entityInstance, createdBy);
                }
            }
        }

        return chainInstance;
    }
    
    public ChainInstance createPartyWelcomeChainInstance(final ExecutionErrorAccumulator eea, final Party party, final BasePK createdBy) {
        return createPartyChainInstance(eea, ChainConstants.ChainType_WELCOME, party, false, createdBy);
    }
    
    public ChainInstance createPartyPasswordRecoveryChainInstance(final ExecutionErrorAccumulator eea, final Party party, final BasePK createdBy) {
        return createPartyChainInstance(eea, ChainConstants.ChainType_PASSWORD_RECOVERY, party, true, createdBy);
    }
    
    public ChainInstance createPartyTermChangedChainInstance(final ExecutionErrorAccumulator eea, final Party party, final BasePK createdBy) {
        return createPartyChainInstance(eea, ChainConstants.ChainType_PARTY_TERM_CHANGED, party, false, createdBy);
    }
    
    public ChainInstance createPartyCreditLimitChangedChainInstance(final ExecutionErrorAccumulator eea, final Party party, final BasePK createdBy) {
        return createPartyChainInstance(eea, ChainConstants.ChainType_PARTY_CREDIT_LIMIT_CHANGED, party, false, createdBy);
    }
    
    public ChainInstance createPartyCreditStatusChangedChainInstance(final ExecutionErrorAccumulator eea, final Party party, final BasePK createdBy) {
        return createPartyChainInstance(eea, ChainConstants.ChainType_PARTY_CREDIT_STATUS_CHANGED, party, false, createdBy);
    }
    
}