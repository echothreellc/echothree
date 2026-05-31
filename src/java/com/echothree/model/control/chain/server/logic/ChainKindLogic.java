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

import com.echothree.control.user.chain.common.spec.ChainKindUniversalSpec;
import com.echothree.model.control.chain.common.exception.DuplicateChainKindNameException;
import com.echothree.model.control.chain.common.exception.UnknownChainKindNameException;
import com.echothree.model.control.chain.common.exception.UnknownDefaultChainKindException;
import com.echothree.model.control.chain.server.control.ChainControl;
import com.echothree.model.control.core.common.ComponentVendors;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.model.control.core.common.exception.InvalidParameterCountException;
import com.echothree.model.control.core.server.logic.EntityInstanceLogic;
import com.echothree.model.data.chain.server.entity.ChainKind;
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
public class ChainKindLogic
        extends BaseLogic {

    @Inject
    ChainControl chainControl;

    protected ChainKindLogic() {
        super();
    }

    public static ChainKindLogic getInstance() {
        return CDI.current().select(ChainKindLogic.class).get();
    }

    public ChainKind createChainKind(final ExecutionErrorAccumulator eea, final String chainKindName,
            final Boolean isDefault, final Integer sortOrder, final Language language, final String description,
            final BasePK createdBy) {
        var chainKind = chainControl.getChainKindByName(chainKindName);

        if(chainKind == null) {
            chainKind = chainControl.createChainKind(chainKindName, isDefault, sortOrder, createdBy);

            if(description != null) {
                chainControl.createChainKindDescription(chainKind, language, description, createdBy);
            }
        } else {
            handleExecutionError(DuplicateChainKindNameException.class, eea, ExecutionErrors.DuplicateChainKindName.name(), chainKindName);
        }

        return chainKind;
    }

    public ChainKind getChainKindByName(final ExecutionErrorAccumulator eea, final String chainKindName,
            final EntityPermission entityPermission) {
        var chainKind = chainControl.getChainKindByName(chainKindName, entityPermission);

        if(chainKind == null) {
            handleExecutionError(UnknownChainKindNameException.class, eea, ExecutionErrors.UnknownChainKindName.name(), chainKindName);
        }

        return chainKind;
    }

    public ChainKind getChainKindByName(final ExecutionErrorAccumulator eea, final String chainKindName) {
        return getChainKindByName(eea, chainKindName, EntityPermission.READ_ONLY);
    }

    public ChainKind getChainKindByNameForUpdate(final ExecutionErrorAccumulator eea, final String chainKindName) {
        return getChainKindByName(eea, chainKindName, EntityPermission.READ_WRITE);
    }

    public ChainKind getChainKindByUniversalSpec(final ExecutionErrorAccumulator eea,
            final ChainKindUniversalSpec universalSpec, boolean allowDefault, final EntityPermission entityPermission) {
        ChainKind chainKind = null;
        var chainKindName = universalSpec.getChainKindName();
        var parameterCount = (chainKindName == null ? 0 : 1) + EntityInstanceLogic.getInstance().countPossibleEntitySpecs(universalSpec);

        switch(parameterCount) {
            case 0 -> {
                if(allowDefault) {
                    chainKind = chainControl.getDefaultChainKind(entityPermission);

                    if(chainKind == null) {
                        handleExecutionError(UnknownDefaultChainKindException.class, eea, ExecutionErrors.UnknownDefaultChainKind.name());
                    }
                } else {
                    handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
                }
            }
            case 1 -> {
                if(chainKindName == null) {
                    var entityInstance = EntityInstanceLogic.getInstance().getEntityInstance(eea, universalSpec,
                            ComponentVendors.ECHO_THREE.name(), EntityTypes.ChainKind.name());

                    if(!eea.hasExecutionErrors()) {
                        chainKind = chainControl.getChainKindByEntityInstance(entityInstance, entityPermission);
                    }
                } else {
                    chainKind = getChainKindByName(eea, chainKindName, entityPermission);
                }
            }
            default ->
                    handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
        }

        return chainKind;
    }

    public ChainKind getChainKindByUniversalSpec(final ExecutionErrorAccumulator eea,
            final ChainKindUniversalSpec universalSpec, boolean allowDefault) {
        return getChainKindByUniversalSpec(eea, universalSpec, allowDefault, EntityPermission.READ_ONLY);
    }

    public ChainKind getChainKindByUniversalSpecForUpdate(final ExecutionErrorAccumulator eea,
            final ChainKindUniversalSpec universalSpec, boolean allowDefault) {
        return getChainKindByUniversalSpec(eea, universalSpec, allowDefault, EntityPermission.READ_WRITE);
    }

    public void deleteChainKind(final ExecutionErrorAccumulator eea, final ChainKind chainKind,
            final BasePK deletedBy) {
        chainControl.deleteChainKind(chainKind, deletedBy);
    }

}
