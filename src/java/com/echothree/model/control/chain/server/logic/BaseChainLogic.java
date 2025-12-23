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

import com.echothree.model.control.chain.common.exception.UnknownChainEntityRoleTypeNameException;
import com.echothree.model.control.chain.common.exception.UnknownChainKindNameException;
import com.echothree.model.control.chain.common.exception.UnknownChainTypeNameException;
import com.echothree.model.control.chain.server.control.ChainControl;
import com.echothree.model.control.customer.server.control.CustomerControl;
import com.echothree.model.control.offer.server.control.OfferControl;
import com.echothree.model.control.sequence.common.SequenceTypes;
import com.echothree.model.control.sequence.server.logic.SequenceGeneratorLogic;
import com.echothree.model.data.chain.server.entity.Chain;
import com.echothree.model.data.chain.server.entity.ChainEntityRoleType;
import com.echothree.model.data.chain.server.entity.ChainInstance;
import com.echothree.model.data.chain.server.entity.ChainKind;
import com.echothree.model.data.chain.server.entity.ChainType;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.Session;
import java.util.HashSet;
import java.util.Set;

public class BaseChainLogic
        extends BaseLogic {
    
    protected BaseChainLogic() {
        super();
    }
    
    public ChainKind getChainKindByName(final ExecutionErrorAccumulator eea, final String chainKindName) {
        var chainControl = Session.getModelController(ChainControl.class);
        var chainKind = chainControl.getChainKindByName(chainKindName);

        if(chainKind == null) {
            handleExecutionError(UnknownChainKindNameException.class, eea, ExecutionErrors.UnknownChainKindName.name(), chainKindName);
        }

        return chainKind;
    }

    public ChainType getChainTypeByName(final ExecutionErrorAccumulator eea, final ChainKind chainKind, final String chainTypeName) {
        var chainControl = Session.getModelController(ChainControl.class);
        var chainType = chainControl.getChainTypeByName(chainKind, chainTypeName);

        if(chainType == null) {
            handleExecutionError(UnknownChainTypeNameException.class, eea, ExecutionErrors.UnknownChainTypeName.name(), chainKind.getLastDetail().getChainKindName(),
                    chainTypeName);
        }
        
        return chainType;
    }
    
   public ChainType getChainTypeByName(final ExecutionErrorAccumulator eea, final String chainKindName, final String chainTypeName) {
       var chainKind = getChainKindByName(eea, chainKindName);
        ChainType chainType = null;
        
        if(chainKind != null) {
            chainType = getChainTypeByName(eea, chainKind, chainTypeName);
        }
        
        return chainType;
    }
    
    public ChainEntityRoleType getChainEntityRoleTypeByName(final ExecutionErrorAccumulator eea, final ChainType chainType, final String chainEntityRoleTypeName) {
        var chainControl = Session.getModelController(ChainControl.class);
        var chainEntityRoleType = chainControl.getChainEntityRoleTypeByName(chainType, chainEntityRoleTypeName);

        if(chainEntityRoleType == null) {
            handleExecutionError(UnknownChainEntityRoleTypeNameException.class, eea, ExecutionErrors.UnknownChainEntityRoleTypeName.name(),
                    chainType.getLastDetail().getChainTypeName(), chainEntityRoleTypeName);
        }
        
        return chainEntityRoleType;
    }
    
    protected long countChainInstanceEntityRolesByChainEntityRoleType(final ChainEntityRoleType chainEntityRoleType) {
        var chainControl = Session.getModelController(ChainControl.class);
        
        return chainControl.countChainInstanceEntityRolesByChainEntityRoleType(chainEntityRoleType);
    }

    protected long countChainInstanceEntityRolesByEntityInstance(final EntityInstance entityInstance) {
        var chainControl = Session.getModelController(ChainControl.class);
        
        return chainControl.countChainInstanceEntityRolesByEntityInstance(entityInstance);
    }

    protected long countChainInstanceEntityRoles(final ChainEntityRoleType chainEntityRoleType, final EntityInstance entityInstance) {
        var chainControl = Session.getModelController(ChainControl.class);
        
        return chainControl.countChainInstanceEntityRoles(chainEntityRoleType, entityInstance);
    }

    protected Long countChainInstanceEntityRoles(final ExecutionErrorAccumulator eea, final ChainType chainType, final String chainEntityRoleTypeName,
            final EntityInstance entityInstance) {
        var chainEntityRoleType = getChainEntityRoleTypeByName(eea, chainType, chainEntityRoleTypeName);
        
        return chainEntityRoleType == null ? null : countChainInstanceEntityRoles(chainEntityRoleType, entityInstance);
    }
    
    protected Chain getChain(final ExecutionErrorAccumulator eea, final ChainType chainType, final Party party) {
        var chainControl = Session.getModelController(ChainControl.class);
        var offerControl = Session.getModelController(OfferControl.class);
        var customerControl = Session.getModelController(CustomerControl.class);
        var customer = customerControl.getCustomer(party);
        var initialOfferUse = customer == null ? null : customer.getInitialOfferUse();
        var offerChainType = initialOfferUse == null ? null : offerControl.getOfferChainType(initialOfferUse.getLastDetail().getOffer(), chainType);

        return offerChainType == null ? chainControl.getDefaultChain(chainType) : offerChainType.getChain();
    }

    protected ChainInstance createChainInstance(final ExecutionErrorAccumulator eea, final Chain chain, final BasePK createdBy) {
        var chainControl = Session.getModelController(ChainControl.class);
        var defaultChainActionSet = chainControl.getDefaultChainActionSet(chain);
        ChainInstance chainInstance = null;
        
        // The lack of a defaultChainActionSet is not a reportable error - it just silently avoids creating a Chain Instance.
        if(defaultChainActionSet != null) {
            var sequence = chain.getLastDetail().getChainInstanceSequence();

            if(sequence == null) {
                sequence = SequenceGeneratorLogic.getInstance().getDefaultSequence(eea, SequenceTypes.CHAIN_INSTANCE.name());
            }

            if(!hasExecutionErrors(eea)) {
                chainInstance = chainControl.createChainInstance(SequenceGeneratorLogic.getInstance().getNextSequenceValue(sequence), defaultChainActionSet, createdBy);
            }
        }
        
        return chainInstance;
    }
    
    protected ChainInstance createChainInstance(final ExecutionErrorAccumulator eea, final ChainType chainType, final Party party, final BasePK createdBy) {
        var chain = getChain(eea, chainType, party);
        ChainInstance chainInstance = null;

        if(chain != null) {
            chainInstance = createChainInstance(eea, chain, createdBy);
        }

        return chainInstance;
    }
    
    protected ChainInstance createChainInstance(final ExecutionErrorAccumulator eea, final String chainKindName, final String chainTypeName, final Party party,
            final BasePK createdBy) {
        var chainType = getChainTypeByName(eea, chainKindName, chainTypeName);
        ChainInstance chainInstance = null;

        if(!hasExecutionErrors(eea)) {
            chainInstance = createChainInstance(eea, chainType, party, createdBy);
        }

        return chainInstance;
    }
    
    protected void deleteChainInstancedByChainEntityRoleTypeAndEntityInstance(final ChainEntityRoleType chainEntityRoleType, final EntityInstance entityInstance,
            final BasePK deletedBy) {
        var chainControl = Session.getModelController(ChainControl.class);
        var chainInstanceEntityRoles = chainControl.getChainInstanceEntityRoles(chainEntityRoleType, entityInstance);
        Set<ChainInstance> chainInstances = new HashSet<>();
        
        chainInstanceEntityRoles.forEach((chainInstanceEntityRole) -> {
            chainInstances.add(chainInstanceEntityRole.getChainInstanceForUpdate());
        });
        
        chainControl.deleteChainInstances(chainInstances, deletedBy);
    }
    
}