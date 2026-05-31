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

import com.echothree.control.user.chain.common.spec.ChainUniversalSpec;
import com.echothree.model.control.chain.common.exception.CannotDeleteChainInUseException;
import com.echothree.model.control.chain.common.exception.DuplicateChainNameException;
import com.echothree.model.control.chain.common.exception.UnknownChainNameException;
import com.echothree.model.control.chain.common.exception.UnknownDefaultChainException;
import com.echothree.model.control.chain.common.exception.UnknownDefaultChainKindException;
import com.echothree.model.control.chain.common.exception.UnknownDefaultChainTypeException;
import com.echothree.model.control.chain.server.control.ChainControl;
import com.echothree.model.control.core.common.ComponentVendors;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.model.control.core.common.exception.InvalidParameterCountException;
import com.echothree.model.control.core.server.logic.EntityInstanceLogic;
import com.echothree.model.control.customer.server.control.CustomerControl;
import com.echothree.model.control.offer.server.control.OfferControl;
import com.echothree.model.control.sequence.common.SequenceTypes;
import com.echothree.model.control.sequence.server.logic.SequenceLogic;
import com.echothree.model.data.chain.server.entity.Chain;
import com.echothree.model.data.chain.server.entity.ChainKind;
import com.echothree.model.data.chain.server.entity.ChainType;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.model.data.sequence.server.entity.Sequence;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import com.echothree.util.server.validation.ParameterUtils;
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

    @Inject
    SequenceLogic sequenceLogic;

    protected ChainLogic() {
        super();
    }

    public Chain createChain(final ExecutionErrorAccumulator eea, final String chainKindName, final String chainTypeName,
            final String chainName, final String initialChainAdjustmentName, final String chainItemSelectorName,
            final Boolean isDefault, final Integer sortOrder, final Language language, final String description,
            final BasePK createdBy) {
        var chainType = chainTypeLogic.getChainTypeByName(eea, chainKindName, chainTypeName);
        Chain chain = null;

        if(eea == null || !eea.hasExecutionErrors()) {
            var chainInstanceSequence = initialChainAdjustmentName == null ? null :
                    sequenceLogic.getSequenceByName(eea, SequenceTypes.CHAIN_INSTANCE.name(), initialChainAdjustmentName);

            if(eea == null || !eea.hasExecutionErrors()) {
                chain = createChain(eea, chainType, chainName, chainInstanceSequence,
                        isDefault, sortOrder, language, description, createdBy);
            }
        }

        return chain;
    }

    public Chain createChain(final ExecutionErrorAccumulator eea, final ChainType chainType, final String chainName,
            final Sequence chainInstanceSequence, final Boolean isDefault, final Integer sortOrder, final Language language,
            final String description, final BasePK createdBy) {
        var chainControl = Session.getModelController(ChainControl.class);
        var chain = chainControl.getChainByName(chainType, chainName);

        if(chain == null) {
            chain = chainControl.createChain(chainType, chainName, chainInstanceSequence, isDefault, sortOrder, createdBy);

            if(description != null) {
                chainControl.createChainDescription(chain, language, description, createdBy);
            }
        } else {
            handleExecutionError(DuplicateChainNameException.class, eea, ExecutionErrors.DuplicateChainName.name(), chainName);
        }
        return chain;
    }

    public Chain getChainByName(final ExecutionErrorAccumulator eea, final ChainType chainType, final String chainName,
            final EntityPermission entityPermission) {
        var chainControl = Session.getModelController(ChainControl.class);
        var chain = chainControl.getChainByName(chainType, chainName, entityPermission);

        if(chain == null) {
            handleExecutionError(UnknownChainNameException.class, eea, ExecutionErrors.UnknownChainName.name(),
                    chainType.getLastDetail().getChainKind().getLastDetail().getChainKindName(),
                    chainType.getLastDetail().getChainTypeName(), chainName);
        }

        return chain;
    }

    public Chain getChainByName(final ExecutionErrorAccumulator eea, final ChainType chainType, final String chainName) {
        return getChainByName(eea, chainType, chainName, EntityPermission.READ_ONLY);
    }

    public Chain getChainByNameForUpdate(final ExecutionErrorAccumulator eea, final ChainType chainType, final String chainName) {
        return getChainByName(eea, chainType, chainName, EntityPermission.READ_WRITE);
    }

    public Chain getChainByName(final ExecutionErrorAccumulator eea, final String chainKindName, final String chainTypeName,
            final String chainName, final EntityPermission entityPermission) {
        var chainType = chainTypeLogic.getChainTypeByName(eea, chainKindName, chainTypeName);
        Chain chain = null;

        if(!eea.hasExecutionErrors()) {
            chain = getChainByName(eea, chainType, chainName, entityPermission);
        }

        return chain;
    }

    public Chain getChainByName(final ExecutionErrorAccumulator eea, final String chainKindName, final String chainTypeName, final String chainName) {
        return getChainByName(eea, chainKindName, chainTypeName, chainName, EntityPermission.READ_ONLY);
    }

    public Chain getChainByNameForUpdate(final ExecutionErrorAccumulator eea, final String chainKindName, final String chainTypeName, final String chainName) {
        return getChainByName(eea, chainKindName, chainTypeName, chainName, EntityPermission.READ_WRITE);
    }

    public Chain getChainByUniversalSpec(final ExecutionErrorAccumulator eea, final ChainUniversalSpec universalSpec,
            final boolean allowDefault, final EntityPermission entityPermission) {
        var chainControl = Session.getModelController(ChainControl.class);
        var chainKindName = universalSpec.getChainKindName();
        var chainTypeName = universalSpec.getChainTypeName();
        var chainName = universalSpec.getChainName();
        var nameParameterCount= ParameterUtils.getInstance().countNonNullParameters(chainKindName, chainTypeName, chainName);
        var possibleEntitySpecs= EntityInstanceLogic.getInstance().countPossibleEntitySpecs(universalSpec);
        Chain chain = null;

        if(nameParameterCount < 4 && possibleEntitySpecs == 0) {
            ChainKind chainKind = null;
            ChainType chainType = null;

            if(chainKindName == null) {
                if(allowDefault) {
                    chainKind = chainControl.getDefaultChainKind();

                    if(chainKind == null) {
                        handleExecutionError(UnknownDefaultChainKindException.class, eea, ExecutionErrors.UnknownDefaultChainKind.name());
                    }
                } else {
                    handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
                }
            } else {
                chainKind = ChainKindLogic.getInstance().getChainKindByName(eea, chainKindName);
            }

            if(chainTypeName == null && !eea.hasExecutionErrors()) {
                if(allowDefault) {
                    chainType = chainControl.getDefaultChainType(chainKind);

                    if(chainType == null) {
                        handleExecutionError(UnknownDefaultChainTypeException.class, eea, ExecutionErrors.UnknownDefaultChainKind.name(),
                                chainKind.getLastDetail().getChainKindName());
                    }
                } else {
                    handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
                }
            } else {
                chainType = chainTypeLogic.getChainTypeByName(eea, chainKind, chainTypeName);
            }

            if(!eea.hasExecutionErrors()) {
                if(chainName == null) {
                    if(allowDefault) {
                        chain = chainControl.getDefaultChain(chainType, entityPermission);

                        if(chain == null) {
                            handleExecutionError(UnknownDefaultChainException.class, eea, ExecutionErrors.UnknownDefaultChain.name(),
                                    chainKind.getLastDetail().getChainKindName(),
                                    chainType.getLastDetail().getChainTypeName());
                        }
                    } else {
                        handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
                    }
                } else {
                    chain = getChainByName(eea, chainType, chainName, entityPermission);
                }
            }
        } else if(nameParameterCount == 0 && possibleEntitySpecs == 1) {
            var entityInstance = EntityInstanceLogic.getInstance().getEntityInstance(eea, universalSpec,
                    ComponentVendors.ECHO_THREE.name(), EntityTypes.Chain.name());

            if(!eea.hasExecutionErrors()) {
                chain = chainControl.getChainByEntityInstance(entityInstance, entityPermission);
            }
        } else {
            handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
        }

        return chain;
    }

    public Chain getChainByUniversalSpec(final ExecutionErrorAccumulator eea, final ChainUniversalSpec universalSpec,
            boolean allowDefault) {
        return getChainByUniversalSpec(eea, universalSpec, allowDefault, EntityPermission.READ_ONLY);
    }

    public Chain getChainByUniversalSpecForUpdate(final ExecutionErrorAccumulator eea, final ChainUniversalSpec universalSpec,
            boolean allowDefault) {
        return getChainByUniversalSpec(eea, universalSpec, allowDefault, EntityPermission.READ_WRITE);
    }

    public void deleteChain(final ExecutionErrorAccumulator eea, final Chain chain, final BasePK deletedBy) {
        if(chainControl.countChainActionSetsByChain(chain) == 0) {
            var chainControl = Session.getModelController(ChainControl.class);

            chainControl.deleteChain(chain, deletedBy);
        } else {
            var chainDetail = chain.getLastDetail();

            handleExecutionError(CannotDeleteChainInUseException.class, eea, ExecutionErrors.CannotDeleteChainInUse.name(),
                    chainDetail.getChainType().getLastDetail().getChainKind().getLastDetail().getChainKindName(),
                    chainDetail.getChainType().getLastDetail().getChainTypeName(),
                    chainDetail.getChainName());
        }
    }
    
    public Chain getChain(final ExecutionErrorAccumulator eea, final ChainType chainType, final Party party) {
        var customer = customerControl.getCustomer(party);
        var initialOfferUse = customer == null ? null : customer.getInitialOfferUse();
        var offerChainType = initialOfferUse == null ? null : offerControl.getOfferChainType(initialOfferUse.getLastDetail().getOffer(), chainType);

        return offerChainType == null ? chainControl.getDefaultChain(chainType) : offerChainType.getChain();
    }

}
