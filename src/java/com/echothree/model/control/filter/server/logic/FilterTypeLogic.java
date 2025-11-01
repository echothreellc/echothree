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

import com.echothree.control.user.filter.common.spec.FilterTypeUniversalSpec;
import com.echothree.model.control.core.common.ComponentVendors;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.model.control.core.common.exception.InvalidParameterCountException;
import com.echothree.model.control.core.server.logic.EntityInstanceLogic;
import com.echothree.model.control.filter.common.exception.DuplicateFilterTypeNameException;
import com.echothree.model.control.filter.common.exception.UnknownDefaultFilterKindException;
import com.echothree.model.control.filter.common.exception.UnknownDefaultFilterTypeException;
import com.echothree.model.control.filter.common.exception.UnknownFilterTypeNameException;
import com.echothree.model.control.filter.server.control.FilterControl;
import com.echothree.model.data.filter.server.entity.FilterKind;
import com.echothree.model.data.filter.server.entity.FilterType;
import com.echothree.model.data.party.server.entity.Language;
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
public class FilterTypeLogic
        extends BaseLogic {

    protected FilterTypeLogic() {
        super();
    }

    public static FilterTypeLogic getInstance() {
        return CDI.current().select(FilterTypeLogic.class).get();
    }

    public FilterType createFilterType(final ExecutionErrorAccumulator eea, final String filterKindName, final String filterTypeName,
            final Boolean isDefault, final Integer sortOrder, final Language language, final String description, final BasePK createdBy) {
        var filterKind = FilterKindLogic.getInstance().getFilterKindByName(eea, filterKindName);
        FilterType filterType = null;

        if(eea == null || !eea.hasExecutionErrors()) {
            filterType = createFilterType(eea, filterKind, filterTypeName, isDefault, sortOrder, language, description, createdBy);
        }

        return filterType;
    }

    public FilterType createFilterType(final ExecutionErrorAccumulator eea, final FilterKind filterKind, final String filterTypeName,
            final Boolean isDefault, final Integer sortOrder, final Language language, final String description, final BasePK createdBy) {
        var filterControl = Session.getModelController(FilterControl.class);
        var filterType = filterControl.getFilterTypeByName(filterKind, filterTypeName);

        if(filterType == null) {
            filterType = filterControl.createFilterType(filterKind, filterTypeName, isDefault, sortOrder, createdBy);

            if(description != null) {
                filterControl.createFilterTypeDescription(filterType, language, description, createdBy);
            }
        } else {
            handleExecutionError(DuplicateFilterTypeNameException.class, eea, ExecutionErrors.DuplicateFilterTypeName.name(), filterTypeName);
        }
        return filterType;
    }

    public FilterType getFilterTypeByName(final ExecutionErrorAccumulator eea, final FilterKind filterKind, final String filterTypeName,
            final EntityPermission entityPermission) {
        var filterControl = Session.getModelController(FilterControl.class);
        var filterType = filterControl.getFilterTypeByName(filterKind, filterTypeName, entityPermission);

        if(filterType == null) {
            handleExecutionError(UnknownFilterTypeNameException.class, eea, ExecutionErrors.UnknownFilterTypeName.name(),
                    filterKind.getLastDetail().getFilterKindName(), filterTypeName);
        }

        return filterType;
    }

    public FilterType getFilterTypeByName(final ExecutionErrorAccumulator eea, final FilterKind filterKind, final String filterTypeName) {
        return getFilterTypeByName(eea, filterKind, filterTypeName, EntityPermission.READ_ONLY);
    }

    public FilterType getFilterTypeByNameForUpdate(final ExecutionErrorAccumulator eea, final FilterKind filterKind, final String filterTypeName) {
        return getFilterTypeByName(eea, filterKind, filterTypeName, EntityPermission.READ_WRITE);
    }

    public FilterType getFilterTypeByName(final ExecutionErrorAccumulator eea, final String filterKindName, final String filterTypeName,
            final EntityPermission entityPermission) {
        var filterKind = FilterKindLogic.getInstance().getFilterKindByName(eea, filterKindName);
        FilterType filterType = null;

        if(!eea.hasExecutionErrors()) {
            filterType = getFilterTypeByName(eea, filterKind, filterTypeName, entityPermission);
        }

        return filterType;
    }

    public FilterType getFilterTypeByName(final ExecutionErrorAccumulator eea, final String filterKindName, final String filterTypeName) {
        return getFilterTypeByName(eea, filterKindName, filterTypeName, EntityPermission.READ_ONLY);
    }

    public FilterType getFilterTypeByNameForUpdate(final ExecutionErrorAccumulator eea, final String filterKindName, final String filterTypeName) {
        return getFilterTypeByName(eea, filterKindName, filterTypeName, EntityPermission.READ_WRITE);
    }

    public FilterType getFilterTypeByUniversalSpec(final ExecutionErrorAccumulator eea, final FilterTypeUniversalSpec universalSpec,
            final boolean allowDefault, final EntityPermission entityPermission) {
        var filterControl = Session.getModelController(FilterControl.class);
        var filterKindName = universalSpec.getFilterKindName();
        var filterTypeName = universalSpec.getFilterTypeName();
        var nameParameterCount= ParameterUtils.getInstance().countNonNullParameters(filterKindName, filterTypeName);
        var possibleEntitySpecs= EntityInstanceLogic.getInstance().countPossibleEntitySpecs(universalSpec);
        FilterType filterType = null;

        if(nameParameterCount < 3 && possibleEntitySpecs == 0) {
            FilterKind filterKind = null;

            if(filterKindName == null) {
                if(allowDefault) {
                    filterKind = filterControl.getDefaultFilterKind();

                    if(filterKind == null) {
                        handleExecutionError(UnknownDefaultFilterKindException.class, eea, ExecutionErrors.UnknownDefaultFilterKind.name());
                    }
                } else {
                    handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
                }
            } else {
                filterKind = FilterKindLogic.getInstance().getFilterKindByName(eea, filterKindName);
            }

            if(!eea.hasExecutionErrors()) {
                if(filterTypeName == null) {
                    if(allowDefault) {
                        filterType = filterControl.getDefaultFilterType(filterKind, entityPermission);

                        if(filterType == null) {
                            handleExecutionError(UnknownDefaultFilterTypeException.class, eea, ExecutionErrors.UnknownDefaultFilterType.name());
                        }
                    } else {
                        handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
                    }
                } else {
                    filterType = getFilterTypeByName(eea, filterKind, filterTypeName, entityPermission);
                }
            }
        } else if(nameParameterCount == 0 && possibleEntitySpecs == 1) {
            var entityInstance = EntityInstanceLogic.getInstance().getEntityInstance(eea, universalSpec,
                    ComponentVendors.ECHO_THREE.name(), EntityTypes.FilterType.name());

            if(!eea.hasExecutionErrors()) {
                filterType = filterControl.getFilterTypeByEntityInstance(entityInstance, entityPermission);
            }
        } else {
            handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
        }

        return filterType;
    }

    public FilterType getFilterTypeByUniversalSpec(final ExecutionErrorAccumulator eea, final FilterTypeUniversalSpec universalSpec,
            boolean allowDefault) {
        return getFilterTypeByUniversalSpec(eea, universalSpec, allowDefault, EntityPermission.READ_ONLY);
    }

    public FilterType getFilterTypeByUniversalSpecForUpdate(final ExecutionErrorAccumulator eea, final FilterTypeUniversalSpec universalSpec,
            boolean allowDefault) {
        return getFilterTypeByUniversalSpec(eea, universalSpec, allowDefault, EntityPermission.READ_WRITE);
    }

    public void deleteFilterType(final ExecutionErrorAccumulator eea, final FilterType filterType,
            final BasePK deletedBy) {
        var filterControl = Session.getModelController(FilterControl.class);

        filterControl.deleteFilterType(filterType, deletedBy);
    }
}
