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

import com.echothree.control.user.chain.common.spec.ChainTypeUniversalSpec;
import com.echothree.model.control.chain.common.exception.CannotDeleteChainTypeInUseException;
import com.echothree.model.control.chain.common.exception.DuplicateChainTypeNameException;
import com.echothree.model.control.chain.common.exception.UnknownChainTypeNameException;
import com.echothree.model.control.chain.common.exception.UnknownDefaultChainKindException;
import com.echothree.model.control.chain.common.exception.UnknownDefaultChainTypeException;
import com.echothree.model.control.chain.server.control.ChainControl;
import com.echothree.model.control.core.common.ComponentVendors;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.model.control.core.common.exception.InvalidParameterCountException;
import com.echothree.model.control.core.server.logic.EntityInstanceLogic;
import com.echothree.model.data.chain.server.entity.ChainKind;
import com.echothree.model.data.chain.server.entity.ChainType;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.validation.ParameterUtils;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class ChainTypeLogic
        extends BaseLogic {

    protected ChainTypeLogic() {
        super();
    }

    @Inject
    ChainControl chainControl;

    @Inject
    ChainKindLogic chainKindLogic;

    public ChainType createChainType(final ExecutionErrorAccumulator eea, final String chainKindName, final String chainTypeName,
            final Boolean isDefault, final Integer sortOrder, final Language language, final String description, final BasePK createdBy) {
        var chainKind = chainKindLogic.getChainKindByName(eea, chainKindName);
        ChainType chainType = null;

        if(eea == null || !eea.hasExecutionErrors()) {
            chainType = createChainType(eea, chainKind, chainTypeName, isDefault, sortOrder, language, description, createdBy);
        }

        return chainType;
    }

    public ChainType createChainType(final ExecutionErrorAccumulator eea, final ChainKind chainKind, final String chainTypeName,
            final Boolean isDefault, final Integer sortOrder, final Language language, final String description, final BasePK createdBy) {
        var chainType = chainControl.getChainTypeByName(chainKind, chainTypeName);

        if(chainType == null) {
            chainType = chainControl.createChainType(chainKind, chainTypeName, isDefault, sortOrder, createdBy);

            if(description != null) {
                chainControl.createChainTypeDescription(chainType, language, description, createdBy);
            }
        } else {
            handleExecutionError(DuplicateChainTypeNameException.class, eea, ExecutionErrors.DuplicateChainTypeName.name(), chainTypeName);
        }
        return chainType;
    }

    public ChainType getChainTypeByName(final ExecutionErrorAccumulator eea, final ChainKind chainKind, final String chainTypeName,
            final EntityPermission entityPermission) {
        var chainType = chainControl.getChainTypeByName(chainKind, chainTypeName, entityPermission);

        if(chainType == null) {
            handleExecutionError(UnknownChainTypeNameException.class, eea, ExecutionErrors.UnknownChainTypeName.name(),
                    chainKind.getLastDetail().getChainKindName(), chainTypeName);
        }

        return chainType;
    }

    public ChainType getChainTypeByName(final ExecutionErrorAccumulator eea, final ChainKind chainKind, final String chainTypeName) {
        return getChainTypeByName(eea, chainKind, chainTypeName, EntityPermission.READ_ONLY);
    }

    public ChainType getChainTypeByNameForUpdate(final ExecutionErrorAccumulator eea, final ChainKind chainKind, final String chainTypeName) {
        return getChainTypeByName(eea, chainKind, chainTypeName, EntityPermission.READ_WRITE);
    }

    public ChainType getChainTypeByName(final ExecutionErrorAccumulator eea, final String chainKindName, final String chainTypeName,
            final EntityPermission entityPermission) {
        var chainKind = chainKindLogic.getChainKindByName(eea, chainKindName);
        ChainType chainType = null;

        if(eea == null || !eea.hasExecutionErrors()) {
            chainType = getChainTypeByName(eea, chainKind, chainTypeName, entityPermission);
        }

        return chainType;
    }

    public ChainType getChainTypeByName(final ExecutionErrorAccumulator eea, final String chainKindName, final String chainTypeName) {
        return getChainTypeByName(eea, chainKindName, chainTypeName, EntityPermission.READ_ONLY);
    }

    public ChainType getChainTypeByNameForUpdate(final ExecutionErrorAccumulator eea, final String chainKindName, final String chainTypeName) {
        return getChainTypeByName(eea, chainKindName, chainTypeName, EntityPermission.READ_WRITE);
    }

    public ChainType getChainTypeByUniversalSpec(final ExecutionErrorAccumulator eea, final ChainTypeUniversalSpec universalSpec,
            final boolean allowDefault, final EntityPermission entityPermission) {
        var chainKindName = universalSpec.getChainKindName();
        var chainTypeName = universalSpec.getChainTypeName();
        var nameParameterCount = ParameterUtils.getInstance().countNonNullParameters(chainKindName, chainTypeName);
        var possibleEntitySpecs = EntityInstanceLogic.getInstance().countPossibleEntitySpecs(universalSpec);
        ChainType chainType = null;

        if(nameParameterCount < 3 && possibleEntitySpecs == 0) {
            ChainKind chainKind = null;

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

            if(eea == null || !eea.hasExecutionErrors()) {
                if(chainTypeName == null) {
                    if(allowDefault) {
                        chainType = chainControl.getDefaultChainType(chainKind, entityPermission);

                        if(chainType == null) {
                            handleExecutionError(UnknownDefaultChainTypeException.class, eea, ExecutionErrors.UnknownDefaultChainType.name());
                        }
                    } else {
                        handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
                    }
                } else {
                    chainType = getChainTypeByName(eea, chainKind, chainTypeName, entityPermission);
                }
            }
        } else if(nameParameterCount == 0 && possibleEntitySpecs == 1) {
            var entityInstance = EntityInstanceLogic.getInstance().getEntityInstance(eea, universalSpec,
                    ComponentVendors.ECHO_THREE.name(), EntityTypes.ChainType.name());

            if(eea == null || !eea.hasExecutionErrors()) {
                chainType = chainControl.getChainTypeByEntityInstance(entityInstance, entityPermission);
            }
        } else {
            handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
        }

        return chainType;
    }

    public ChainType getChainTypeByUniversalSpec(final ExecutionErrorAccumulator eea, final ChainTypeUniversalSpec universalSpec,
            boolean allowDefault) {
        return getChainTypeByUniversalSpec(eea, universalSpec, allowDefault, EntityPermission.READ_ONLY);
    }

    public ChainType getChainTypeByUniversalSpecForUpdate(final ExecutionErrorAccumulator eea, final ChainTypeUniversalSpec universalSpec,
            boolean allowDefault) {
        return getChainTypeByUniversalSpec(eea, universalSpec, allowDefault, EntityPermission.READ_WRITE);
    }

    public void deleteChainType(final ExecutionErrorAccumulator eea, final ChainType chainType,
            final BasePK deletedBy) {
        var chainCount = chainControl.countChainsByChainType(chainType);

        if(chainCount == 0) {
            chainControl.deleteChainType(chainType, deletedBy);
        } else {
            handleExecutionError(CannotDeleteChainTypeInUseException.class, eea, ExecutionErrors.CannotDeleteChainTypeInUse.name(),
                    chainType.getLastDetail().getChainKind().getLastDetail().getChainKindName(),
                    chainType.getLastDetail().getChainTypeName());
        }
    }
}
