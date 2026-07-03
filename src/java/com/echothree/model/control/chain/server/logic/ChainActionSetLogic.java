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

import com.echothree.control.user.chain.common.spec.ChainActionSetUniversalSpec;
import com.echothree.model.control.chain.common.exception.UnknownChainActionSetNameException;
import com.echothree.model.control.chain.common.exception.UnknownDefaultChainActionSetException;
import com.echothree.model.control.chain.common.exception.UnknownDefaultChainException;
import com.echothree.model.control.chain.common.exception.UnknownDefaultChainKindException;
import com.echothree.model.control.chain.common.exception.UnknownDefaultChainTypeException;
import com.echothree.model.control.chain.server.control.ChainControl;
import com.echothree.model.control.core.common.ComponentVendors;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.model.control.core.common.exception.InvalidParameterCountException;
import com.echothree.model.control.core.server.logic.EntityInstanceLogic;
import com.echothree.model.data.chain.server.entity.Chain;
import com.echothree.model.data.chain.server.entity.ChainActionSet;
import com.echothree.model.data.chain.server.entity.ChainKind;
import com.echothree.model.data.chain.server.entity.ChainType;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.validation.ParameterUtils;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class ChainActionSetLogic
        extends BaseLogic {

    @Inject
    ChainControl chainControl;

    @Inject
    ChainKindLogic chainKindLogic;

    @Inject
    ChainLogic chainLogic;

    @Inject
    ChainTypeLogic chainTypeLogic;

    @Inject
    EntityInstanceLogic entityInstanceLogic;

    @Inject
    ParameterUtils parameterUtils;

    protected ChainActionSetLogic() {
        super();
    }

    public ChainActionSet getChainActionSetByName(final ExecutionErrorAccumulator eea, final Chain chain, final String chainActionSetName,
            final EntityPermission entityPermission) {
        var chainActionSet = chainControl.getChainActionSetByName(chain, chainActionSetName, entityPermission);

        if(chainActionSet == null) {
            handleExecutionError(UnknownChainActionSetNameException.class, eea, ExecutionErrors.UnknownChainActionSetName.name(),
                    chain.getLastDetail().getChainType().getLastDetail().getChainKind().getLastDetail().getChainKindName(),
                    chain.getLastDetail().getChainType().getLastDetail().getChainTypeName(),
                    chain.getLastDetail().getChainName(),
                    chainActionSetName);
        }

        return chainActionSet;
    }

    public ChainActionSet getChainActionSetByName(final ExecutionErrorAccumulator eea, final Chain chain, final String chainActionSetName) {
        return getChainActionSetByName(eea, chain, chainActionSetName, EntityPermission.READ_ONLY);
    }

    public ChainActionSet getChainActionSetByNameForUpdate(final ExecutionErrorAccumulator eea, final Chain chain, final String chainActionSetName) {
        return getChainActionSetByName(eea, chain, chainActionSetName, EntityPermission.READ_WRITE);
    }

    public ChainActionSet getChainActionSetByName(final ExecutionErrorAccumulator eea, final String chainKindName,
            final String chainTypeName, final String chainName, final String chainActionSetName, final EntityPermission entityPermission) {
        var chain = chainLogic.getChainByName(eea, chainKindName, chainTypeName, chainName);
        ChainActionSet chainActionSet = null;

        if(!hasExecutionErrors(eea)) {
            chainActionSet = getChainActionSetByName(eea, chain, chainActionSetName, entityPermission);
        }

        return chainActionSet;
    }

    public ChainActionSet getChainActionSetByName(final ExecutionErrorAccumulator eea, final String chainKindName,
            final String chainTypeName, final String chainName, final String chainActionSetName) {
        return getChainActionSetByName(eea, chainKindName, chainTypeName, chainName, chainActionSetName, EntityPermission.READ_ONLY);
    }

    public ChainActionSet getChainActionSetByNameForUpdate(final ExecutionErrorAccumulator eea, final String chainKindName,
            final String chainTypeName, final String chainName, final String chainActionSetName) {
        return getChainActionSetByName(eea, chainKindName, chainTypeName, chainName, chainActionSetName, EntityPermission.READ_WRITE);
    }

    public ChainActionSet getChainActionSetByUniversalSpec(final ExecutionErrorAccumulator eea,
            final ChainActionSetUniversalSpec universalSpec, final boolean allowDefault, final EntityPermission entityPermission) {
        var chainKindName = universalSpec.getChainKindName();
        var chainTypeName = universalSpec.getChainTypeName();
        var chainName = universalSpec.getChainName();
        var chainActionSetName = universalSpec.getChainActionSetName();
        var nameParameterCount = parameterUtils.countNonNullParameters(chainKindName, chainTypeName, chainName, chainActionSetName);
        var possibleEntitySpecs = entityInstanceLogic.countPossibleEntitySpecs(universalSpec);
        ChainActionSet chainActionSet = null;

        if(nameParameterCount != 0 && possibleEntitySpecs == 0) {
            ChainKind chainKind = null;
            ChainType chainType = null;
            Chain chain = null;

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
                chainKind = chainKindLogic.getChainKindByName(eea, chainKindName);
            }

            if(!eea.hasExecutionErrors()) {
                if(chainTypeName == null) {
                    if(allowDefault) {
                        chainType = chainControl.getDefaultChainType(chainKind);

                        if(chainType == null) {
                            handleExecutionError(UnknownDefaultChainTypeException.class, eea, ExecutionErrors.UnknownDefaultChainType.name(),
                                    chainKind.getLastDetail().getChainKindName());
                        }
                    } else {
                        handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
                    }
                } else {
                    chainType = chainTypeLogic.getChainTypeByName(eea, chainKind, chainTypeName);
                }
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
                    chain = chainLogic.getChainByName(eea, chainType, chainName, entityPermission);
                }
            }

            if(!eea.hasExecutionErrors()) {
                if(chainActionSetName == null) {
                    if(allowDefault) {
                        chainActionSet = chainControl.getDefaultChainActionSet(chain, entityPermission);

                        if(chainActionSet == null) {
                            handleExecutionError(UnknownDefaultChainActionSetException.class, eea, ExecutionErrors.UnknownDefaultChainActionSet.name(),
                                    chain.getLastDetail().getChainType().getLastDetail().getChainKind().getLastDetail().getChainKindName(),
                                    chain.getLastDetail().getChainType().getLastDetail().getChainTypeName(),
                                    chain.getLastDetail().getChainName());
                        }
                    } else {
                        handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
                    }
                } else {
                    chainActionSet = getChainActionSetByName(eea, chain, chainActionSetName, entityPermission);
                }
            }
        } else if(nameParameterCount == 0 && possibleEntitySpecs == 1) {
            var entityInstance = entityInstanceLogic.getEntityInstance(eea, universalSpec,
                    ComponentVendors.ECHO_THREE.name(), EntityTypes.ChainActionSet.name());

            if(!eea.hasExecutionErrors()) {
                chainActionSet = chainControl.getChainActionSetByEntityInstance(entityInstance, entityPermission);
            }
        } else {
            handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
        }

        return chainActionSet;
    }

    public ChainActionSet getChainActionSetByUniversalSpec(final ExecutionErrorAccumulator eea,
            final ChainActionSetUniversalSpec universalSpec, final boolean allowDefault) {
        return getChainActionSetByUniversalSpec(eea, universalSpec, allowDefault, EntityPermission.READ_ONLY);
    }

    public ChainActionSet getChainActionSetByUniversalSpecForUpdate(final ExecutionErrorAccumulator eea,
            final ChainActionSetUniversalSpec universalSpec, final boolean allowDefault) {
        return getChainActionSetByUniversalSpec(eea, universalSpec, allowDefault, EntityPermission.READ_WRITE);
    }

}
