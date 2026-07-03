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

import com.echothree.control.user.chain.common.spec.ChainActionUniversalSpec;
import com.echothree.model.control.chain.common.exception.UnknownChainActionNameException;
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
import com.echothree.model.data.chain.server.entity.ChainAction;
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
public class ChainActionLogic
        extends BaseLogic {

    @Inject
    ChainControl chainControl;

    @Inject
    ChainActionSetLogic chainActionSetLogic;

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

    protected ChainActionLogic() {
        super();
    }

    public ChainAction getChainActionByName(final ExecutionErrorAccumulator eea, final ChainActionSet chainActionSet, final String chainActionName,
            final EntityPermission entityPermission) {
        var chainAction = entityPermission == EntityPermission.READ_WRITE
                ? chainControl.getChainActionByNameForUpdate(chainActionSet, chainActionName)
                : chainControl.getChainActionByName(chainActionSet, chainActionName);

        if(chainAction == null) {
            handleExecutionError(UnknownChainActionNameException.class, eea, ExecutionErrors.UnknownChainActionName.name(),
                    chainActionSet.getLastDetail().getChain().getLastDetail().getChainType().getLastDetail().getChainKind().getLastDetail().getChainKindName(),
                    chainActionSet.getLastDetail().getChain().getLastDetail().getChainType().getLastDetail().getChainTypeName(),
                    chainActionSet.getLastDetail().getChain().getLastDetail().getChainName(),
                    chainActionSet.getLastDetail().getChainActionSetName(),
                    chainActionName);
        }

        return chainAction;
    }

    public ChainAction getChainActionByName(final ExecutionErrorAccumulator eea, final ChainActionSet chainActionSet, final String chainActionName) {
        return getChainActionByName(eea, chainActionSet, chainActionName, EntityPermission.READ_ONLY);
    }

    public ChainAction getChainActionByNameForUpdate(final ExecutionErrorAccumulator eea, final ChainActionSet chainActionSet, final String chainActionName) {
        return getChainActionByName(eea, chainActionSet, chainActionName, EntityPermission.READ_WRITE);
    }

    public ChainAction getChainActionByName(final ExecutionErrorAccumulator eea, final String chainKindName,
            final String chainTypeName, final String chainName, final String chainActionSetName, final String chainActionName,
            final EntityPermission entityPermission) {
        var chainActionSet = chainActionSetLogic.getChainActionSetByName(eea, chainKindName, chainTypeName, chainName, chainActionSetName);
        ChainAction chainAction = null;

        if(!hasExecutionErrors(eea)) {
            chainAction = getChainActionByName(eea, chainActionSet, chainActionName, entityPermission);
        }

        return chainAction;
    }

    public ChainAction getChainActionByName(final ExecutionErrorAccumulator eea, final String chainKindName,
            final String chainTypeName, final String chainName, final String chainActionSetName, final String chainActionName) {
        return getChainActionByName(eea, chainKindName, chainTypeName, chainName, chainActionSetName, chainActionName, EntityPermission.READ_ONLY);
    }

    public ChainAction getChainActionByNameForUpdate(final ExecutionErrorAccumulator eea, final String chainKindName,
            final String chainTypeName, final String chainName, final String chainActionSetName, final String chainActionName) {
        return getChainActionByName(eea, chainKindName, chainTypeName, chainName, chainActionSetName, chainActionName, EntityPermission.READ_WRITE);
    }

    public ChainAction getChainActionByUniversalSpec(final ExecutionErrorAccumulator eea,
            final ChainActionUniversalSpec universalSpec, final boolean allowDefault, final EntityPermission entityPermission) {
        var chainKindName = universalSpec.getChainKindName();
        var chainTypeName = universalSpec.getChainTypeName();
        var chainName = universalSpec.getChainName();
        var chainActionSetName = universalSpec.getChainActionSetName();
        var chainActionName = universalSpec.getChainActionName();
        var nameParameterCount = parameterUtils.countNonNullParameters(chainKindName, chainTypeName, chainName, chainActionSetName, chainActionName);
        var possibleEntitySpecs = entityInstanceLogic.countPossibleEntitySpecs(universalSpec);
        ChainAction chainAction = null;

        if(nameParameterCount != 0 && possibleEntitySpecs == 0) {
            ChainKind chainKind = null;
            ChainType chainType = null;
            Chain chain = null;
            ChainActionSet chainActionSet = null;

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
                    chainActionSet = chainActionSetLogic.getChainActionSetByName(eea, chain, chainActionSetName, entityPermission);
                }
            }

            if(!eea.hasExecutionErrors()) {
                chainAction = getChainActionByName(eea, chainActionSet, chainActionName, entityPermission);
            }
        } else if(nameParameterCount == 0 && possibleEntitySpecs == 1) {
            var entityInstance = entityInstanceLogic.getEntityInstance(eea, universalSpec,
                    ComponentVendors.ECHO_THREE.name(), EntityTypes.ChainAction.name());

            if(!eea.hasExecutionErrors()) {
                chainAction = chainControl.getChainActionByEntityInstance(entityInstance, entityPermission);
            }
        } else {
            handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
        }

        return chainAction;
    }

    public ChainAction getChainActionByUniversalSpec(final ExecutionErrorAccumulator eea,
            final ChainActionUniversalSpec universalSpec, final boolean allowDefault) {
        return getChainActionByUniversalSpec(eea, universalSpec, allowDefault, EntityPermission.READ_ONLY);
    }

    public ChainAction getChainActionByUniversalSpecForUpdate(final ExecutionErrorAccumulator eea,
            final ChainActionUniversalSpec universalSpec, final boolean allowDefault) {
        return getChainActionByUniversalSpec(eea, universalSpec, allowDefault, EntityPermission.READ_WRITE);
    }

}
