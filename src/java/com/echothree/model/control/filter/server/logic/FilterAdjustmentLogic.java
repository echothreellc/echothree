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

import com.echothree.control.user.filter.common.spec.FilterAdjustmentUniversalSpec;
import com.echothree.model.control.core.common.ComponentVendors;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.model.control.core.common.exception.InvalidParameterCountException;
import com.echothree.model.control.core.server.logic.EntityInstanceLogic;
import com.echothree.model.control.filter.common.exception.CannotDeleteFilterAdjustmentInUseException;
import com.echothree.model.control.filter.common.exception.DuplicateFilterAdjustmentNameException;
import com.echothree.model.control.filter.common.exception.UnknownDefaultFilterAdjustmentException;
import com.echothree.model.control.filter.common.exception.UnknownDefaultFilterKindException;
import com.echothree.model.control.filter.common.exception.UnknownFilterAdjustmentNameException;
import com.echothree.model.control.filter.server.control.FilterControl;
import com.echothree.model.data.filter.server.entity.FilterAdjustment;
import com.echothree.model.data.filter.server.entity.FilterAdjustmentSource;
import com.echothree.model.data.filter.server.entity.FilterAdjustmentType;
import com.echothree.model.data.filter.server.entity.FilterKind;
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
public class FilterAdjustmentLogic
        extends BaseLogic {

    protected FilterAdjustmentLogic() {
        super();
    }

    public static FilterAdjustmentLogic getInstance() {
        return CDI.current().select(FilterAdjustmentLogic.class).get();
    }

    public FilterAdjustment createFilterAdjustment(final ExecutionErrorAccumulator eea, final String filterKindName,
            final String filterAdjustmentName, final String filterAdjustmentSourceName, final String filterAdjustmentTypeName,
            final Boolean isDefault, final Integer sortOrder, final Language language, final String description,
            final BasePK createdBy) {
        var filterKind = FilterKindLogic.getInstance().getFilterKindByName(eea, filterKindName);
        var filterAdjustmentSource = FilterAdjustmentSourceLogic.getInstance().getFilterAdjustmentSourceByName(eea, filterAdjustmentSourceName);
        var filterAdjustmentType = filterAdjustmentTypeName == null ? null : FilterAdjustmentTypeLogic.getInstance().getFilterAdjustmentTypeByName(eea, filterAdjustmentTypeName);
        FilterAdjustment filterAdjustment = null;

        if(eea == null || !eea.hasExecutionErrors()) {
            filterAdjustment = createFilterAdjustment(eea, filterKind, filterAdjustmentName, filterAdjustmentSource,
                    filterAdjustmentType, isDefault, sortOrder, language, description, createdBy);
        }

        return filterAdjustment;
    }

    public FilterAdjustment createFilterAdjustment(final ExecutionErrorAccumulator eea, final FilterKind filterKind, final String filterAdjustmentName,
            final FilterAdjustmentSource filterAdjustmentSource, final FilterAdjustmentType filterAdjustmentType, final Boolean isDefault,
            final Integer sortOrder, final Language language, final String description, final BasePK createdBy) {
        var filterControl = Session.getModelController(FilterControl.class);
        var filterAdjustment = filterControl.getFilterAdjustmentByName(filterKind, filterAdjustmentName);

        if(filterAdjustment == null) {
            filterAdjustment = filterControl.createFilterAdjustment(filterKind, filterAdjustmentName, filterAdjustmentSource,
                    filterAdjustmentType, isDefault, sortOrder, createdBy);

            if(description != null) {
                filterControl.createFilterAdjustmentDescription(filterAdjustment, language, description, createdBy);
            }
        } else {
            handleExecutionError(DuplicateFilterAdjustmentNameException.class, eea, ExecutionErrors.DuplicateFilterAdjustmentName.name(), filterAdjustmentName);
        }
        return filterAdjustment;
    }

    public FilterAdjustment getFilterAdjustmentByName(final ExecutionErrorAccumulator eea, final FilterKind filterKind, final String filterAdjustmentName,
            final EntityPermission entityPermission) {
        var filterControl = Session.getModelController(FilterControl.class);
        var filterAdjustment = filterControl.getFilterAdjustmentByName(filterKind, filterAdjustmentName, entityPermission);

        if(filterAdjustment == null) {
            handleExecutionError(UnknownFilterAdjustmentNameException.class, eea, ExecutionErrors.UnknownFilterAdjustmentName.name(),
                    filterKind.getLastDetail().getFilterKindName(), filterAdjustmentName);
        }

        return filterAdjustment;
    }

    public FilterAdjustment getFilterAdjustmentByName(final ExecutionErrorAccumulator eea, final FilterKind filterKind, final String filterAdjustmentName) {
        return getFilterAdjustmentByName(eea, filterKind, filterAdjustmentName, EntityPermission.READ_ONLY);
    }

    public FilterAdjustment getFilterAdjustmentByNameForUpdate(final ExecutionErrorAccumulator eea, final FilterKind filterKind, final String filterAdjustmentName) {
        return getFilterAdjustmentByName(eea, filterKind, filterAdjustmentName, EntityPermission.READ_WRITE);
    }

    public FilterAdjustment getFilterAdjustmentByName(final ExecutionErrorAccumulator eea, final String filterKindName, final String filterAdjustmentName,
            final EntityPermission entityPermission) {
        var filterKind = FilterKindLogic.getInstance().getFilterKindByName(eea, filterKindName);
        FilterAdjustment filterAdjustment = null;

        if(!eea.hasExecutionErrors()) {
            filterAdjustment = getFilterAdjustmentByName(eea, filterKind, filterAdjustmentName, entityPermission);
        }

        return filterAdjustment;
    }

    public FilterAdjustment getFilterAdjustmentByName(final ExecutionErrorAccumulator eea, final String filterKindName, final String filterAdjustmentName) {
        return getFilterAdjustmentByName(eea, filterKindName, filterAdjustmentName, EntityPermission.READ_ONLY);
    }

    public FilterAdjustment getFilterAdjustmentByNameForUpdate(final ExecutionErrorAccumulator eea, final String filterKindName, final String filterAdjustmentName) {
        return getFilterAdjustmentByName(eea, filterKindName, filterAdjustmentName, EntityPermission.READ_WRITE);
    }

    public FilterAdjustment getFilterAdjustmentByUniversalSpec(final ExecutionErrorAccumulator eea, final FilterAdjustmentUniversalSpec universalSpec,
            final boolean allowDefault, final EntityPermission entityPermission) {
        var filterControl = Session.getModelController(FilterControl.class);
        var filterKindName = universalSpec.getFilterKindName();
        var filterAdjustmentName = universalSpec.getFilterAdjustmentName();
        var nameParameterCount= ParameterUtils.getInstance().countNonNullParameters(filterKindName, filterAdjustmentName);
        var possibleEntitySpecs= EntityInstanceLogic.getInstance().countPossibleEntitySpecs(universalSpec);
        FilterAdjustment filterAdjustment = null;

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
                if(filterAdjustmentName == null) {
                    if(allowDefault) {
                        filterAdjustment = filterControl.getDefaultFilterAdjustment(filterKind, entityPermission);

                        if(filterAdjustment == null) {
                            handleExecutionError(UnknownDefaultFilterAdjustmentException.class, eea, ExecutionErrors.UnknownDefaultFilterAdjustment.name());
                        }
                    } else {
                        handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
                    }
                } else {
                    filterAdjustment = getFilterAdjustmentByName(eea, filterKind, filterAdjustmentName, entityPermission);
                }
            }
        } else if(nameParameterCount == 0 && possibleEntitySpecs == 1) {
            var entityInstance = EntityInstanceLogic.getInstance().getEntityInstance(eea, universalSpec,
                    ComponentVendors.ECHO_THREE.name(), EntityTypes.FilterAdjustment.name());

            if(!eea.hasExecutionErrors()) {
                filterAdjustment = filterControl.getFilterAdjustmentByEntityInstance(entityInstance, entityPermission);
            }
        } else {
            handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
        }

        return filterAdjustment;
    }

    public FilterAdjustment getFilterAdjustmentByUniversalSpec(final ExecutionErrorAccumulator eea, final FilterAdjustmentUniversalSpec universalSpec,
            boolean allowDefault) {
        return getFilterAdjustmentByUniversalSpec(eea, universalSpec, allowDefault, EntityPermission.READ_ONLY);
    }

    public FilterAdjustment getFilterAdjustmentByUniversalSpecForUpdate(final ExecutionErrorAccumulator eea, final FilterAdjustmentUniversalSpec universalSpec,
            boolean allowDefault) {
        return getFilterAdjustmentByUniversalSpec(eea, universalSpec, allowDefault, EntityPermission.READ_WRITE);
    }


    public void deleteFilterAdjustment(final ExecutionErrorAccumulator eea, final FilterAdjustment filterAdjustment,
            final BasePK deletedBy) {
        var filterControl = Session.getModelController(FilterControl.class);

        if(filterControl.countFiltersByFilterAdjustment(filterAdjustment) == 0
                && filterControl.countFilterStepElementsByFilterAdjustment(filterAdjustment) == 0) {
            filterControl.deleteFilterAdjustment(filterAdjustment, deletedBy);
        } else {
            var filterAdjustmentDetail = filterAdjustment.getLastDetail();

            handleExecutionError(CannotDeleteFilterAdjustmentInUseException.class, eea, ExecutionErrors.CannotDeleteFilterAdjustmentInUse.name(),
                    filterAdjustmentDetail.getFilterKind().getLastDetail().getFilterKindName(),
                    filterAdjustmentDetail.getFilterAdjustmentName());
        }

    }
}
