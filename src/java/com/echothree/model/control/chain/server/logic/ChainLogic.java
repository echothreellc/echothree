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

import com.echothree.model.control.chain.common.exception.UnknownChainNameException;
import com.echothree.model.control.chain.common.exception.UnknownChainTypeNameException;
import com.echothree.model.control.chain.server.control.ChainControl;
import com.echothree.model.control.customer.server.control.CustomerControl;
import com.echothree.model.control.offer.server.control.OfferControl;
import com.echothree.model.data.chain.server.entity.Chain;
import com.echothree.model.data.chain.server.entity.ChainKind;
import com.echothree.model.data.chain.server.entity.ChainType;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class ChainLogic
        extends BaseLogic {

    @Inject
    ChainControl chainControl;

    @Inject
    OfferControl offerControl;

    @Inject
    CustomerControl customerControl;

    @Inject
    ChainTypeLogic chainTypeLogic;

    protected ChainLogic() {
        super();
    }

    public Chain getChainByName(final ExecutionErrorAccumulator eea, final ChainType chainType, final String chainName) {
        var chain = chainControl.getChainByName(chainType, chainName);

        if(chain == null) {
            handleExecutionError(UnknownChainNameException.class, eea, ExecutionErrors.UnknownChainName.name(), chainType.getLastDetail().getChainTypeName(),
                    chainName);
        }

        return chain;
    }

    public Chain getChainByName(final ExecutionErrorAccumulator eea, final String chainKindName, final String chainTypeName, final String chainName) {
        var chainType = chainTypeLogic.getChainTypeByName(eea, chainKindName, chainTypeName);
        Chain chain = null;

        if(chainType != null) {
            chain = getChainByName(eea, chainType, chainName);
        }

        return chain;
    }
    
    public Chain getChain(final ExecutionErrorAccumulator eea, final ChainType chainType, final Party party) {
        var customer = customerControl.getCustomer(party);
        var initialOfferUse = customer == null ? null : customer.getInitialOfferUse();
        var offerChainType = initialOfferUse == null ? null : offerControl.getOfferChainType(initialOfferUse.getLastDetail().getOffer(), chainType);

        return offerChainType == null ? chainControl.getDefaultChain(chainType) : offerChainType.getChain();
    }

}
