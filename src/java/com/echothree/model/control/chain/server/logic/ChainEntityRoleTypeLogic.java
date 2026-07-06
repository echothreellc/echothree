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

import com.echothree.control.user.chain.common.spec.ChainEntityRoleTypeUniversalSpec;
import com.echothree.model.control.chain.common.exception.UnknownChainEntityRoleTypeNameException;
import com.echothree.model.control.chain.common.exception.UnknownDefaultChainKindException;
import com.echothree.model.control.chain.common.exception.UnknownDefaultChainTypeException;
import com.echothree.model.control.chain.server.control.ChainControl;
import com.echothree.model.control.core.common.ComponentVendors;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.model.control.core.common.exception.InvalidParameterCountException;
import com.echothree.model.control.core.server.logic.EntityInstanceLogic;
import com.echothree.model.data.chain.server.entity.ChainEntityRoleType;
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
public class ChainEntityRoleTypeLogic
        extends BaseLogic {

    @Inject
    protected ChainControl chainControl;

    @Inject
    protected ChainKindLogic chainKindLogic;

    @Inject
    protected ChainTypeLogic chainTypeLogic;

    @Inject
    protected EntityInstanceLogic entityInstanceLogic;

    @Inject
    protected ParameterUtils parameterUtils;

    protected ChainEntityRoleTypeLogic() {
        super();
    }

    public ChainEntityRoleType getChainEntityRoleTypeByName(final ExecutionErrorAccumulator eea, final ChainType chainType,
            final String chainEntityRoleTypeName, final EntityPermission entityPermission) {
        var chainEntityRoleType = chainControl.getChainEntityRoleTypeByName(chainType, chainEntityRoleTypeName, entityPermission);

        if(chainEntityRoleType == null) {
            handleExecutionError(UnknownChainEntityRoleTypeNameException.class, eea, ExecutionErrors.UnknownChainEntityRoleTypeName.name(),
                    chainType.getLastDetail().getChainKind().getLastDetail().getChainKindName(),
                    chainType.getLastDetail().getChainTypeName(),
                    chainEntityRoleTypeName);
        }

        return chainEntityRoleType;
    }

    public ChainEntityRoleType getChainEntityRoleTypeByName(final ExecutionErrorAccumulator eea, final ChainType chainType,
            final String chainEntityRoleTypeName) {
        return getChainEntityRoleTypeByName(eea, chainType, chainEntityRoleTypeName, EntityPermission.READ_ONLY);
    }

    public ChainEntityRoleType getChainEntityRoleTypeByNameForUpdate(final ExecutionErrorAccumulator eea, final ChainType chainType,
            final String chainEntityRoleTypeName) {
        return getChainEntityRoleTypeByName(eea, chainType, chainEntityRoleTypeName, EntityPermission.READ_WRITE);
    }

    public ChainEntityRoleType getChainEntityRoleTypeByName(final ExecutionErrorAccumulator eea, final String chainKindName,
            final String chainTypeName, final String chainEntityRoleTypeName, final EntityPermission entityPermission) {
        ChainEntityRoleType chainEntityRoleType = null;
        var chainType = chainTypeLogic.getChainTypeByName(eea, chainKindName, chainTypeName);

        if(!eea.hasExecutionErrors()) {
            chainEntityRoleType = getChainEntityRoleTypeByName(eea, chainType, chainEntityRoleTypeName, entityPermission);
        }

        return chainEntityRoleType;
    }

    public ChainEntityRoleType getChainEntityRoleTypeByName(final ExecutionErrorAccumulator eea, final String chainKindName,
            final String chainTypeName, final String chainEntityRoleTypeName) {
        return getChainEntityRoleTypeByName(eea, chainKindName, chainTypeName, chainEntityRoleTypeName, EntityPermission.READ_ONLY);
    }

    public ChainEntityRoleType getChainEntityRoleTypeByNameForUpdate(final ExecutionErrorAccumulator eea, final String chainKindName,
            final String chainTypeName, final String chainEntityRoleTypeName) {
        return getChainEntityRoleTypeByName(eea, chainKindName, chainTypeName, chainEntityRoleTypeName, EntityPermission.READ_WRITE);
    }

    public ChainEntityRoleType getChainEntityRoleTypeByUniversalSpec(final ExecutionErrorAccumulator eea,
            final ChainEntityRoleTypeUniversalSpec universalSpec, final boolean allowDefault, final EntityPermission entityPermission) {
        ChainEntityRoleType chainEntityRoleType = null;
        var chainKindName = universalSpec.getChainKindName();
        var chainTypeName = universalSpec.getChainTypeName();
        var chainEntityRoleTypeName = universalSpec.getChainEntityRoleTypeName();
        var nameParameterCount = parameterUtils.countNonNullParameters(chainKindName, chainTypeName, chainEntityRoleTypeName);
        var possibleEntitySpecs = entityInstanceLogic.countPossibleEntitySpecs(universalSpec);

        if(nameParameterCount != 0 && possibleEntitySpecs == 0) {
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
                if(chainEntityRoleTypeName != null) {
                    chainEntityRoleType = getChainEntityRoleTypeByName(eea, chainType, chainEntityRoleTypeName, entityPermission);
                } else {
                    handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
                }
            }
        } else if(nameParameterCount == 0 && possibleEntitySpecs == 1) {
            var entityInstance = entityInstanceLogic.getEntityInstance(eea, universalSpec,
                    ComponentVendors.ECHO_THREE.name(), EntityTypes.ChainEntityRoleType.name());

            if(!eea.hasExecutionErrors()) {
                chainEntityRoleType = chainControl.getChainEntityRoleTypeByEntityInstance(entityInstance, entityPermission);
            }
        } else {
            handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
        }

        return chainEntityRoleType;
    }

    public ChainEntityRoleType getChainEntityRoleTypeByUniversalSpec(final ExecutionErrorAccumulator eea,
            final ChainEntityRoleTypeUniversalSpec universalSpec, final boolean allowDefault) {
        return getChainEntityRoleTypeByUniversalSpec(eea, universalSpec, allowDefault, EntityPermission.READ_ONLY);
    }

    public ChainEntityRoleType getChainEntityRoleTypeByUniversalSpecForUpdate(final ExecutionErrorAccumulator eea,
            final ChainEntityRoleTypeUniversalSpec universalSpec, final boolean allowDefault) {
        return getChainEntityRoleTypeByUniversalSpec(eea, universalSpec, allowDefault, EntityPermission.READ_WRITE);
    }

}
