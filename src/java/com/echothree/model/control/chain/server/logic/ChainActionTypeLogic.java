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

import com.echothree.control.user.chain.common.spec.ChainActionTypeUniversalSpec;
import com.echothree.model.control.chain.common.exception.CannotDeleteChainActionTypeInUseException;
import com.echothree.model.control.chain.common.exception.DuplicateChainActionTypeNameException;
import com.echothree.model.control.chain.common.exception.UnknownChainActionTypeNameException;
import com.echothree.model.control.chain.common.exception.UnknownDefaultChainActionTypeException;
import com.echothree.model.control.chain.server.control.ChainControl;
import com.echothree.model.control.core.common.ComponentVendors;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.model.control.core.common.exception.InvalidParameterCountException;
import com.echothree.model.control.core.server.logic.EntityInstanceLogic;
import com.echothree.model.data.chain.server.entity.ChainActionType;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.EntityPermission;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.spi.CDI;
import javax.inject.Inject;

@ApplicationScoped
public class ChainActionTypeLogic
        extends BaseLogic {

    protected ChainActionTypeLogic() {
        super();
    }

    public static ChainActionTypeLogic getInstance() {
        return CDI.current().select(ChainActionTypeLogic.class).get();
    }

    @Inject
    ChainControl chainControl;

    public ChainActionType createChainActionType(final ExecutionErrorAccumulator eea, final String chainActionTypeName,
            final Boolean allowMultiple, final Boolean isDefault, final Integer sortOrder, final Language language,
            final String description, final BasePK createdBy) {
        var chainActionType = chainControl.getChainActionTypeByName(chainActionTypeName);

        if(chainActionType == null) {
            chainActionType = chainControl.createChainActionType(chainActionTypeName, allowMultiple, isDefault, sortOrder, createdBy);

            if(description != null) {
                chainControl.createChainActionTypeDescription(chainActionType, language, description, createdBy);
            }
        } else {
            handleExecutionError(DuplicateChainActionTypeNameException.class, eea, ExecutionErrors.DuplicateChainActionTypeName.name(), chainActionTypeName);
        }

        return chainActionType;
    }

    public ChainActionType getChainActionTypeByName(final ExecutionErrorAccumulator eea, final String chainActionTypeName,
            final EntityPermission entityPermission) {
        var chainActionType = chainControl.getChainActionTypeByName(chainActionTypeName, entityPermission);

        if(chainActionType == null) {
            handleExecutionError(UnknownChainActionTypeNameException.class, eea, ExecutionErrors.UnknownChainActionTypeName.name(), chainActionTypeName);
        }

        return chainActionType;
    }

    public ChainActionType getChainActionTypeByName(final ExecutionErrorAccumulator eea, final String chainActionTypeName) {
        return getChainActionTypeByName(eea, chainActionTypeName, EntityPermission.READ_ONLY);
    }

    public ChainActionType getChainActionTypeByNameForUpdate(final ExecutionErrorAccumulator eea, final String chainActionTypeName) {
        return getChainActionTypeByName(eea, chainActionTypeName, EntityPermission.READ_WRITE);
    }

    public ChainActionType getChainActionTypeByUniversalSpec(final ExecutionErrorAccumulator eea,
            final ChainActionTypeUniversalSpec universalSpec, boolean allowDefault, final EntityPermission entityPermission) {
        ChainActionType chainActionType = null;
        var chainActionTypeName = universalSpec.getChainActionTypeName();
        var parameterCount = (chainActionTypeName == null ? 0 : 1) + EntityInstanceLogic.getInstance().countPossibleEntitySpecs(universalSpec);

        switch(parameterCount) {
            case 0 -> {
                if(allowDefault) {
                    chainActionType = chainControl.getDefaultChainActionType(entityPermission);

                    if(chainActionType == null) {
                        handleExecutionError(UnknownDefaultChainActionTypeException.class, eea, ExecutionErrors.UnknownDefaultChainActionType.name());
                    }
                } else {
                    handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
                }
            }
            case 1 -> {
                if(chainActionTypeName == null) {
                    var entityInstance = EntityInstanceLogic.getInstance().getEntityInstance(eea, universalSpec,
                            ComponentVendors.ECHO_THREE.name(), EntityTypes.ChainActionType.name());

                    if(eea == null || !eea.hasExecutionErrors()) {
                        chainActionType = chainControl.getChainActionTypeByEntityInstance(entityInstance, entityPermission);
                    }
                } else {
                    chainActionType = getChainActionTypeByName(eea, chainActionTypeName, entityPermission);
                }
            }
            default ->
                    handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
        }

        return chainActionType;
    }

    public ChainActionType getChainActionTypeByUniversalSpec(final ExecutionErrorAccumulator eea,
            final ChainActionTypeUniversalSpec universalSpec, boolean allowDefault) {
        return getChainActionTypeByUniversalSpec(eea, universalSpec, allowDefault, EntityPermission.READ_ONLY);
    }

    public ChainActionType getChainActionTypeByUniversalSpecForUpdate(final ExecutionErrorAccumulator eea,
            final ChainActionTypeUniversalSpec universalSpec, boolean allowDefault) {
        return getChainActionTypeByUniversalSpec(eea, universalSpec, allowDefault, EntityPermission.READ_WRITE);
    }

    public void deleteChainActionType(final ExecutionErrorAccumulator eea, final ChainActionType chainActionType,
            final BasePK deletedBy) {
        var chainActionCount = chainControl.countChainActionsByChainActionType(chainActionType);

        if(chainActionCount == 0) {
            chainControl.deleteChainActionType(chainActionType, deletedBy);
        } else {
            handleExecutionError(CannotDeleteChainActionTypeInUseException.class, eea, ExecutionErrors.CannotDeleteChainActionTypeInUse.name(),
                    chainActionType.getLastDetail().getChainActionTypeName());
        }
    }

}
