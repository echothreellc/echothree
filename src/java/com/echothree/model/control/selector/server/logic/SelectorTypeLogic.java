// --------------------------------------------------------------------------------
// Copyright 2002-2025 Echo Three, LLC
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

import com.echothree.control.user.selector.common.spec.SelectorTypeUniversalSpec;
import com.echothree.model.control.core.common.ComponentVendors;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.model.control.core.common.exception.InvalidParameterCountException;
import com.echothree.model.control.core.server.logic.EntityInstanceLogic;
import com.echothree.model.control.selector.common.exception.DuplicateSelectorTypeNameException;
import com.echothree.model.control.selector.common.exception.UnknownDefaultSelectorKindException;
import com.echothree.model.control.selector.common.exception.UnknownDefaultSelectorTypeException;
import com.echothree.model.control.selector.common.exception.UnknownSelectorTypeNameException;
import com.echothree.model.control.selector.server.control.SelectorControl;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.selector.server.entity.SelectorKind;
import com.echothree.model.data.selector.server.entity.SelectorType;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import com.echothree.util.server.validation.ParameterUtils;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.spi.CDI;

@ApplicationScoped
public class SelectorTypeLogic
        extends BaseLogic {

    protected SelectorTypeLogic() {
        super();
    }

    public static SelectorTypeLogic getInstance() {
        return CDI.current().select(SelectorTypeLogic.class).get();
    }

    public SelectorType createSelectorType(final ExecutionErrorAccumulator eea, final String selectorKindName, final String selectorTypeName,
            final Boolean isDefault, final Integer sortOrder, final Language language, final String description, final BasePK createdBy) {
        var selectorKind = SelectorKindLogic.getInstance().getSelectorKindByName(eea, selectorKindName);
        SelectorType selectorType = null;

        if(eea == null || !eea.hasExecutionErrors()) {
            selectorType = createSelectorType(eea, selectorKind, selectorTypeName, isDefault, sortOrder, language, description, createdBy);
        }

        return selectorType;
    }

    public SelectorType createSelectorType(final ExecutionErrorAccumulator eea, final SelectorKind selectorKind, final String selectorTypeName,
            final Boolean isDefault, final Integer sortOrder, final Language language, final String description, final BasePK createdBy) {
        var selectorControl = Session.getModelController(SelectorControl.class);
        var selectorType = selectorControl.getSelectorTypeByName(selectorKind, selectorTypeName);

        if(selectorType == null) {
            selectorType = selectorControl.createSelectorType(selectorKind, selectorTypeName, isDefault, sortOrder, createdBy);

            if(description != null) {
                selectorControl.createSelectorTypeDescription(selectorType, language, description, createdBy);
            }
        } else {
            handleExecutionError(DuplicateSelectorTypeNameException.class, eea, ExecutionErrors.DuplicateSelectorTypeName.name(),
                    selectorKind.getLastDetail().getSelectorKindName(), selectorTypeName);
        }

        return selectorType;
    }

    public SelectorType getSelectorTypeByName(final ExecutionErrorAccumulator eea, final SelectorKind selectorKind, final String selectorTypeName,
            final EntityPermission entityPermission) {
        var selectorControl = Session.getModelController(SelectorControl.class);
        var selectorType = selectorControl.getSelectorTypeByName(selectorKind, selectorTypeName, entityPermission);

        if(selectorType == null) {
            handleExecutionError(UnknownSelectorTypeNameException.class, eea, ExecutionErrors.UnknownSelectorTypeName.name(),
                    selectorKind.getLastDetail().getSelectorKindName(), selectorTypeName);
        }

        return selectorType;
    }

    public SelectorType getSelectorTypeByName(final ExecutionErrorAccumulator eea, final SelectorKind selectorKind, final String selectorTypeName) {
        return getSelectorTypeByName(eea, selectorKind, selectorTypeName, EntityPermission.READ_ONLY);
    }

    public SelectorType getSelectorTypeByNameForUpdate(final ExecutionErrorAccumulator eea, final SelectorKind selectorKind, final String selectorTypeName) {
        return getSelectorTypeByName(eea, selectorKind, selectorTypeName, EntityPermission.READ_WRITE);
    }

    public SelectorType getSelectorTypeByName(final ExecutionErrorAccumulator eea, final String selectorKindName, final String selectorTypeName,
            final EntityPermission entityPermission) {
        var selectorKind = SelectorKindLogic.getInstance().getSelectorKindByName(eea, selectorKindName);
        SelectorType selectorType = null;

        if(!eea.hasExecutionErrors()) {
            selectorType = getSelectorTypeByName(eea, selectorKind, selectorTypeName, entityPermission);
        }

        return selectorType;
    }

    public SelectorType getSelectorTypeByName(final ExecutionErrorAccumulator eea, final String selectorKindName, final String selectorTypeName) {
        return getSelectorTypeByName(eea, selectorKindName, selectorTypeName, EntityPermission.READ_ONLY);
    }

    public SelectorType getSelectorTypeByNameForUpdate(final ExecutionErrorAccumulator eea, final String selectorKindName, final String selectorTypeName) {
        return getSelectorTypeByName(eea, selectorKindName, selectorTypeName, EntityPermission.READ_WRITE);
    }

    public SelectorType getSelectorTypeByUniversalSpec(final ExecutionErrorAccumulator eea, final SelectorTypeUniversalSpec universalSpec,
            final boolean allowDefault, final EntityPermission entityPermission) {
        var selectorControl = Session.getModelController(SelectorControl.class);
        var selectorKindName = universalSpec.getSelectorKindName();
        var selectorTypeName = universalSpec.getSelectorTypeName();
        var nameParameterCount= ParameterUtils.getInstance().countNonNullParameters(selectorKindName, selectorTypeName);
        var possibleEntitySpecs= EntityInstanceLogic.getInstance().countPossibleEntitySpecs(universalSpec);
        SelectorType selectorType = null;

        if(nameParameterCount < 3 && possibleEntitySpecs == 0) {
            SelectorKind selectorKind = null;

            if(selectorKindName == null) {
                if(allowDefault) {
                    selectorKind = selectorControl.getDefaultSelectorKind();

                    if(selectorKind == null) {
                        handleExecutionError(UnknownDefaultSelectorKindException.class, eea, ExecutionErrors.UnknownDefaultSelectorKind.name());
                    }
                } else {
                    handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
                }
            } else {
                selectorKind = SelectorKindLogic.getInstance().getSelectorKindByName(eea, selectorKindName);
            }

            if(!eea.hasExecutionErrors()) {
                if(selectorTypeName == null) {
                    if(allowDefault) {
                        selectorType = selectorControl.getDefaultSelectorType(selectorKind, entityPermission);

                        if(selectorType == null) {
                            handleExecutionError(UnknownDefaultSelectorTypeException.class, eea, ExecutionErrors.UnknownDefaultSelectorType.name());
                        }
                    } else {
                        handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
                    }
                } else {
                    selectorType = getSelectorTypeByName(eea, selectorKind, selectorTypeName, entityPermission);
                }
            }
        } else if(nameParameterCount == 0 && possibleEntitySpecs == 1) {
            var entityInstance = EntityInstanceLogic.getInstance().getEntityInstance(eea, universalSpec,
                    ComponentVendors.ECHO_THREE.name(), EntityTypes.SelectorType.name());

            if(!eea.hasExecutionErrors()) {
                selectorType = selectorControl.getSelectorTypeByEntityInstance(entityInstance, entityPermission);
            }
        } else {
            handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
        }

        return selectorType;
    }

    public SelectorType getSelectorTypeByUniversalSpec(final ExecutionErrorAccumulator eea, final SelectorTypeUniversalSpec universalSpec,
            boolean allowDefault) {
        return getSelectorTypeByUniversalSpec(eea, universalSpec, allowDefault, EntityPermission.READ_ONLY);
    }

    public SelectorType getSelectorTypeByUniversalSpecForUpdate(final ExecutionErrorAccumulator eea, final SelectorTypeUniversalSpec universalSpec,
            boolean allowDefault) {
        return getSelectorTypeByUniversalSpec(eea, universalSpec, allowDefault, EntityPermission.READ_WRITE);
    }

    public void deleteSelectorType(final ExecutionErrorAccumulator eea, final SelectorType selectorType,
            final BasePK deletedBy) {
        var selectorControl = Session.getModelController(SelectorControl.class);

        selectorControl.deleteSelectorType(selectorType, deletedBy);
    }

}
