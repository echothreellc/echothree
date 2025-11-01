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

package com.echothree.model.control.filter.server.logic;

import com.echothree.control.user.filter.common.spec.FilterKindUniversalSpec;
import com.echothree.model.control.core.common.ComponentVendors;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.model.control.core.common.exception.InvalidParameterCountException;
import com.echothree.model.control.core.server.logic.EntityInstanceLogic;
import com.echothree.model.control.filter.common.exception.DuplicateFilterKindNameException;
import com.echothree.model.control.filter.common.exception.UnknownDefaultFilterKindException;
import com.echothree.model.control.filter.common.exception.UnknownFilterKindNameException;
import com.echothree.model.control.filter.server.control.FilterControl;
import com.echothree.model.data.filter.server.entity.FilterKind;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.spi.CDI;

@ApplicationScoped
public class FilterKindLogic
        extends BaseLogic {

    protected FilterKindLogic() {
        super();
    }

    public static FilterKindLogic getInstance() {
        return CDI.current().select(FilterKindLogic.class).get();
    }

    public FilterKind createFilterKind(final ExecutionErrorAccumulator eea, final String filterKindName,
            final Boolean isDefault, final Integer sortOrder, final Language language, final String description,
            final BasePK createdBy) {
        var filterControl = Session.getModelController(FilterControl.class);
        var filterKind = filterControl.getFilterKindByName(filterKindName);

        if(filterKind == null) {
            filterKind = filterControl.createFilterKind(filterKindName, isDefault, sortOrder, createdBy);

            if(description != null) {
                filterControl.createFilterKindDescription(filterKind, language, description, createdBy);
            }
        } else {
            handleExecutionError(DuplicateFilterKindNameException.class, eea, ExecutionErrors.DuplicateFilterKindName.name(), filterKindName);
        }

        return filterKind;
    }

    public FilterKind getFilterKindByName(final ExecutionErrorAccumulator eea, final String filterKindName,
            final EntityPermission entityPermission) {
        var filterControl = Session.getModelController(FilterControl.class);
        var filterKind = filterControl.getFilterKindByName(filterKindName, entityPermission);

        if(filterKind == null) {
            handleExecutionError(UnknownFilterKindNameException.class, eea, ExecutionErrors.UnknownFilterKindName.name(), filterKindName);
        }

        return filterKind;
    }

    public FilterKind getFilterKindByName(final ExecutionErrorAccumulator eea, final String filterKindName) {
        return getFilterKindByName(eea, filterKindName, EntityPermission.READ_ONLY);
    }

    public FilterKind getFilterKindByNameForUpdate(final ExecutionErrorAccumulator eea, final String filterKindName) {
        return getFilterKindByName(eea, filterKindName, EntityPermission.READ_WRITE);
    }

    public FilterKind getFilterKindByUniversalSpec(final ExecutionErrorAccumulator eea,
            final FilterKindUniversalSpec universalSpec, boolean allowDefault, final EntityPermission entityPermission) {
        FilterKind filterKind = null;
        var filterControl = Session.getModelController(FilterControl.class);
        var filterKindName = universalSpec.getFilterKindName();
        var parameterCount = (filterKindName == null ? 0 : 1) + EntityInstanceLogic.getInstance().countPossibleEntitySpecs(universalSpec);

        switch(parameterCount) {
            case 0:
                if(allowDefault) {
                    filterKind = filterControl.getDefaultFilterKind(entityPermission);

                    if(filterKind == null) {
                        handleExecutionError(UnknownDefaultFilterKindException.class, eea, ExecutionErrors.UnknownDefaultFilterKind.name());
                    }
                } else {
                    handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
                }
                break;
            case 1:
                if(filterKindName == null) {
                    var entityInstance = EntityInstanceLogic.getInstance().getEntityInstance(eea, universalSpec,
                            ComponentVendors.ECHO_THREE.name(), EntityTypes.FilterKind.name());

                    if(!eea.hasExecutionErrors()) {
                        filterKind = filterControl.getFilterKindByEntityInstance(entityInstance, entityPermission);
                    }
                } else {
                    filterKind = getFilterKindByName(eea, filterKindName, entityPermission);
                }
                break;
            default:
                handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
                break;
        }

        return filterKind;
    }

    public FilterKind getFilterKindByUniversalSpec(final ExecutionErrorAccumulator eea,
            final FilterKindUniversalSpec universalSpec, boolean allowDefault) {
        return getFilterKindByUniversalSpec(eea, universalSpec, allowDefault, EntityPermission.READ_ONLY);
    }

    public FilterKind getFilterKindByUniversalSpecForUpdate(final ExecutionErrorAccumulator eea,
            final FilterKindUniversalSpec universalSpec, boolean allowDefault) {
        return getFilterKindByUniversalSpec(eea, universalSpec, allowDefault, EntityPermission.READ_WRITE);
    }

    public void deleteFilterKind(final ExecutionErrorAccumulator eea, final FilterKind filterKind,
            final BasePK deletedBy) {
        var filterControl = Session.getModelController(FilterControl.class);

        filterControl.deleteFilterKind(filterKind, deletedBy);
    }

}
