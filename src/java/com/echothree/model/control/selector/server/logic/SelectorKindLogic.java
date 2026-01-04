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

package com.echothree.model.control.selector.server.logic;

import com.echothree.control.user.selector.common.spec.SelectorKindUniversalSpec;
import com.echothree.model.control.core.common.ComponentVendors;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.model.control.core.common.exception.InvalidParameterCountException;
import com.echothree.model.control.core.server.logic.EntityInstanceLogic;
import com.echothree.model.control.selector.common.exception.DuplicateSelectorKindNameException;
import com.echothree.model.control.selector.common.exception.UnknownDefaultSelectorKindException;
import com.echothree.model.control.selector.common.exception.UnknownSelectorKindNameException;
import com.echothree.model.control.selector.server.control.SelectorControl;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.selector.server.entity.SelectorKind;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.spi.CDI;

@ApplicationScoped
public class SelectorKindLogic
        extends BaseLogic {

    protected SelectorKindLogic() {
        super();
    }

    public static SelectorKindLogic getInstance() {
        return CDI.current().select(SelectorKindLogic.class).get();
    }

    public SelectorKind createSelectorKind(final ExecutionErrorAccumulator eea, final String selectorKindName,
            final Boolean isDefault, final Integer sortOrder, final Language language, final String description,
            final BasePK createdBy) {
        var selectorControl = Session.getModelController(SelectorControl.class);
        var selectorKind = selectorControl.getSelectorKindByName(selectorKindName);

        if(selectorKind == null) {
            selectorKind = selectorControl.createSelectorKind(selectorKindName, isDefault, sortOrder, createdBy);

            if(description != null) {
                selectorControl.createSelectorKindDescription(selectorKind, language, description, createdBy);
            }
        } else {
            handleExecutionError(DuplicateSelectorKindNameException.class, eea, ExecutionErrors.DuplicateSelectorKindName.name(), selectorKindName);
        }

        return selectorKind;
    }

    public SelectorKind getSelectorKindByName(final ExecutionErrorAccumulator eea, final String selectorKindName,
            final EntityPermission entityPermission) {
        var selectorControl = Session.getModelController(SelectorControl.class);
        var selectorKind = selectorControl.getSelectorKindByName(selectorKindName, entityPermission);

        if(selectorKind == null) {
            handleExecutionError(UnknownSelectorKindNameException.class, eea, ExecutionErrors.UnknownSelectorKindName.name(), selectorKindName);
        }

        return selectorKind;
    }

    public SelectorKind getSelectorKindByName(final ExecutionErrorAccumulator eea, final String selectorKindName) {
        return getSelectorKindByName(eea, selectorKindName, EntityPermission.READ_ONLY);
    }

    public SelectorKind getSelectorKindByNameForUpdate(final ExecutionErrorAccumulator eea, final String selectorKindName) {
        return getSelectorKindByName(eea, selectorKindName, EntityPermission.READ_WRITE);
    }

    public SelectorKind getSelectorKindByUniversalSpec(final ExecutionErrorAccumulator eea,
            final SelectorKindUniversalSpec universalSpec, boolean allowDefault, final EntityPermission entityPermission) {
        SelectorKind selectorKind = null;
        var selectorControl = Session.getModelController(SelectorControl.class);
        var selectorKindName = universalSpec.getSelectorKindName();
        var parameterCount = (selectorKindName == null ? 0 : 1) + EntityInstanceLogic.getInstance().countPossibleEntitySpecs(universalSpec);

        switch(parameterCount) {
            case 0 -> {
                if(allowDefault) {
                    selectorKind = selectorControl.getDefaultSelectorKind(entityPermission);

                    if(selectorKind == null) {
                        handleExecutionError(UnknownDefaultSelectorKindException.class, eea, ExecutionErrors.UnknownDefaultSelectorKind.name());
                    }
                } else {
                    handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
                }
            }
            case 1 -> {
                if(selectorKindName == null) {
                    var entityInstance = EntityInstanceLogic.getInstance().getEntityInstance(eea, universalSpec,
                            ComponentVendors.ECHO_THREE.name(), EntityTypes.SelectorKind.name());

                    if(!eea.hasExecutionErrors()) {
                        selectorKind = selectorControl.getSelectorKindByEntityInstance(entityInstance, entityPermission);
                    }
                } else {
                    selectorKind = getSelectorKindByName(eea, selectorKindName, entityPermission);
                }
            }
            default ->
                    handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
        }

        return selectorKind;
    }

    public SelectorKind getSelectorKindByUniversalSpec(final ExecutionErrorAccumulator eea,
            final SelectorKindUniversalSpec universalSpec, boolean allowDefault) {
        return getSelectorKindByUniversalSpec(eea, universalSpec, allowDefault, EntityPermission.READ_ONLY);
    }

    public SelectorKind getSelectorKindByUniversalSpecForUpdate(final ExecutionErrorAccumulator eea,
            final SelectorKindUniversalSpec universalSpec, boolean allowDefault) {
        return getSelectorKindByUniversalSpec(eea, universalSpec, allowDefault, EntityPermission.READ_WRITE);
    }

    public void deleteSelectorKind(final ExecutionErrorAccumulator eea, final SelectorKind selectorKind,
            final BasePK deletedBy) {
        var selectorControl = Session.getModelController(SelectorControl.class);

        selectorControl.deleteSelectorKind(selectorKind, deletedBy);
    }

}
