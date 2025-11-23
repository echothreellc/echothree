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

import com.echothree.control.user.selector.common.spec.SelectorUniversalSpec;
import com.echothree.model.control.core.common.ComponentVendors;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.model.control.core.common.exception.InvalidParameterCountException;
import com.echothree.model.control.core.server.logic.EntityInstanceLogic;
import com.echothree.model.control.selector.common.exception.DuplicateSelectorNameException;
import com.echothree.model.control.selector.common.exception.UnknownDefaultSelectorException;
import com.echothree.model.control.selector.common.exception.UnknownDefaultSelectorKindException;
import com.echothree.model.control.selector.common.exception.UnknownDefaultSelectorTypeException;
import com.echothree.model.control.selector.common.exception.UnknownSelectorNameException;
import com.echothree.model.control.selector.server.control.SelectorControl;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.selector.server.entity.Selector;
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
public class SelectorLogic
        extends BaseLogic {

    protected SelectorLogic() {
        super();
    }

    public static SelectorLogic getInstance() {
        return CDI.current().select(SelectorLogic.class).get();
    }

    public Selector createSelector(final ExecutionErrorAccumulator eea, final String selectorKindName, final String selectorTypeName,
            final String selectorName, final Boolean isDefault, final Integer sortOrder, final Language language, final String description,
            final BasePK createdBy) {
        var selectorType = SelectorTypeLogic.getInstance().getSelectorTypeByName(eea, selectorKindName, selectorTypeName);
        Selector selector = null;

        if(eea == null || !eea.hasExecutionErrors()) {
            selector = createSelector(eea, selectorType, selectorName, isDefault, sortOrder, language, description, createdBy);
        }

        return selector;
    }

    public Selector createSelector(final ExecutionErrorAccumulator eea, final SelectorType selectorType, final String selectorName,
            final Boolean isDefault, final Integer sortOrder, final Language language, final String description, final BasePK createdBy) {
        var selectorControl = Session.getModelController(SelectorControl.class);
        var selector = selectorControl.getSelectorByName(selectorType, selectorName);

        if(selector == null) {
            selector = selectorControl.createSelector(selectorType, selectorName, isDefault, sortOrder, createdBy);

            if(description != null) {
                selectorControl.createSelectorDescription(selector, language, description, createdBy);
            }
        } else {
            handleExecutionError(DuplicateSelectorNameException.class, eea, ExecutionErrors.DuplicateSelectorName.name(),
                    selectorType.getLastDetail().getSelectorKind().getLastDetail().getSelectorKindName(),
                    selectorType.getLastDetail().getSelectorTypeName(), selectorName);
        }
        return selector;
    }

    public Selector getSelectorByName(final ExecutionErrorAccumulator eea, final SelectorType selectorType, final String selectorName,
            final EntityPermission entityPermission) {
        var selectorControl = Session.getModelController(SelectorControl.class);
        var selector = selectorControl.getSelectorByName(selectorType, selectorName, entityPermission);

        if(selector == null) {
            var selectorTypeDetail = selectorType.getLastDetail();

            handleExecutionError(UnknownSelectorNameException.class, eea, ExecutionErrors.UnknownSelectorName.name(),
                    selectorTypeDetail.getSelectorKind().getLastDetail().getSelectorKindName(),
                    selectorTypeDetail.getSelectorTypeName(), selectorName);
        }

        return selector;
    }

    public Selector getSelectorByName(final ExecutionErrorAccumulator eea, final SelectorType selectorType, final String selectorName) {
        return getSelectorByName(eea, selectorType, selectorName, EntityPermission.READ_ONLY);
    }

    public Selector getSelectorByNameForUpdate(final ExecutionErrorAccumulator eea, final SelectorType selectorType, final String selectorName) {
        return getSelectorByName(eea, selectorType, selectorName, EntityPermission.READ_WRITE);
    }

    public Selector getSelectorByName(final ExecutionErrorAccumulator eea, final String selectorKindName,
            final String selectorTypeName, final String selectorName, final EntityPermission entityPermission) {
        var selectorType = SelectorTypeLogic.getInstance().getSelectorTypeByName(eea, selectorKindName, selectorTypeName);
        Selector selector = null;

        if(!eea.hasExecutionErrors()) {
            selector = getSelectorByName(eea, selectorType, selectorName, entityPermission);
        }

        return selector;
    }

    public Selector getSelectorByName(final ExecutionErrorAccumulator eea, final String selectorKindName,
            final String selectorType, final String selectorName) {
        return getSelectorByName(eea, selectorKindName, selectorType, selectorName, EntityPermission.READ_ONLY);
    }

    public Selector getSelectorByNameForUpdate(final ExecutionErrorAccumulator eea, final String selectorKindName,
            final String selectorType, final String selectorName) {
        return getSelectorByName(eea, selectorKindName, selectorType, selectorName, EntityPermission.READ_WRITE);
    }

    public Selector getSelectorByUniversalSpec(final ExecutionErrorAccumulator eea, final SelectorUniversalSpec universalSpec,
            final boolean allowDefault, final EntityPermission entityPermission) {
        var selectorControl = Session.getModelController(SelectorControl.class);
        var selectorKindName = universalSpec.getSelectorKindName();
        var selectorTypeName = universalSpec.getSelectorTypeName();
        var selectorName = universalSpec.getSelectorName();
        var nameParameterCount= ParameterUtils.getInstance().countNonNullParameters(selectorKindName, selectorTypeName, selectorName);
        var possibleEntitySpecs= EntityInstanceLogic.getInstance().countPossibleEntitySpecs(universalSpec);
        Selector selector = null;

        if(nameParameterCount < 4 && possibleEntitySpecs == 0) {
            SelectorKind selectorKind = null;
            SelectorType selectorType = null;

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

            if(selectorTypeName == null) {
                if(allowDefault) {
                    selectorType = selectorControl.getDefaultSelectorType(selectorKind);

                    if(selectorType == null) {
                        handleExecutionError(UnknownDefaultSelectorTypeException.class, eea, ExecutionErrors.UnknownDefaultSelectorType.name());
                    }
                } else {
                    handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
                }
            } else {
                selectorType = SelectorTypeLogic.getInstance().getSelectorTypeByName(eea, selectorKind, selectorTypeName);
            }

            if(!eea.hasExecutionErrors()) {
                if(selectorName == null) {
                    if(allowDefault) {
                        selector = selectorControl.getDefaultSelector(selectorType, entityPermission);

                        if(selector == null) {
                            handleExecutionError(UnknownDefaultSelectorException.class, eea, ExecutionErrors.UnknownDefaultSelector.name());
                        }
                    } else {
                        handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
                    }
                } else {
                    selector = getSelectorByName(eea, selectorType, selectorName, entityPermission);
                }
            }
        } else if(nameParameterCount == 0 && possibleEntitySpecs == 1) {
            var entityInstance = EntityInstanceLogic.getInstance().getEntityInstance(eea, universalSpec,
                    ComponentVendors.ECHO_THREE.name(), EntityTypes.Selector.name());

            if(!eea.hasExecutionErrors()) {
                selector = selectorControl.getSelectorByEntityInstance(entityInstance, entityPermission);
            }
        } else {
            handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
        }

        return selector;
    }

    public Selector getSelectorByUniversalSpec(final ExecutionErrorAccumulator eea, final SelectorUniversalSpec universalSpec,
            boolean allowDefault) {
        return getSelectorByUniversalSpec(eea, universalSpec, allowDefault, EntityPermission.READ_ONLY);
    }

    public Selector getSelectorByUniversalSpecForUpdate(final ExecutionErrorAccumulator eea, final SelectorUniversalSpec universalSpec,
            boolean allowDefault) {
        return getSelectorByUniversalSpec(eea, universalSpec, allowDefault, EntityPermission.READ_WRITE);
    }

}
