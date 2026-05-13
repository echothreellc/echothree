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

import com.echothree.model.control.chain.server.control.ChainControl;
import com.echothree.model.control.sequence.common.SequenceTypes;
import com.echothree.model.control.sequence.server.logic.SequenceGeneratorLogic;
import com.echothree.model.data.chain.server.entity.Chain;
import com.echothree.model.data.chain.server.entity.ChainEntityRoleType;
import com.echothree.model.data.chain.server.entity.ChainInstance;
import com.echothree.model.data.chain.server.entity.ChainType;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import java.util.HashSet;
import java.util.Set;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class ChainInstanceLogic
        extends BaseLogic {

    @Inject
    protected ChainControl chainControl;

    @Inject
    protected ChainLogic chainLogic;

    @Inject
    protected ChainTypeLogic chainTypeLogic;

    protected ChainInstanceLogic() {
        super();
    }

    public ChainInstance createChainInstance(final ExecutionErrorAccumulator eea, final Chain chain, final BasePK createdBy) {
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

    public ChainInstance createChainInstance(final ExecutionErrorAccumulator eea, final ChainType chainType, final Party party, final BasePK createdBy) {
        var chain = chainLogic.getChain(eea, chainType, party);
        ChainInstance chainInstance = null;

        if(chain != null) {
            chainInstance = createChainInstance(eea, chain, createdBy);
        }

        return chainInstance;
    }

    public ChainInstance createChainInstance(final ExecutionErrorAccumulator eea, final String chainKindName, final String chainTypeName, final Party party,
            final BasePK createdBy) {
        var chainType = chainTypeLogic.getChainTypeByName(eea, chainKindName, chainTypeName);
        ChainInstance chainInstance = null;

        if(!hasExecutionErrors(eea)) {
            chainInstance = createChainInstance(eea, chainType, party, createdBy);
        }

        return chainInstance;
    }

    public void deleteChainInstanceByChainEntityRoleTypeAndEntityInstance(final ChainEntityRoleType chainEntityRoleType, final EntityInstance entityInstance,
            final BasePK deletedBy) {
        var chainInstanceEntityRoles = chainControl.getChainInstanceEntityRoles(chainEntityRoleType, entityInstance);
        Set<ChainInstance> chainInstances = new HashSet<>();

        chainInstanceEntityRoles.forEach((chainInstanceEntityRole) -> {
            chainInstances.add(chainInstanceEntityRole.getChainInstanceForUpdate());
        });

        chainControl.deleteChainInstances(chainInstances, deletedBy);
    }

}
