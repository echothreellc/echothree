// --------------------------------------------------------------------------------
// Copyright 2002-2021 Echo Three, LLC
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

import com.echothree.control.user.filter.common.spec.FilterStepUniversalSpec;
import com.echothree.model.control.core.common.ComponentVendors;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.model.control.core.common.exception.InvalidParameterCountException;
import com.echothree.model.control.core.server.logic.EntityInstanceLogic;
import com.echothree.model.control.filter.common.exception.UnknownDefaultFilterException;
import com.echothree.model.control.filter.common.exception.UnknownDefaultFilterKindException;
import com.echothree.model.control.filter.common.exception.UnknownDefaultFilterTypeException;
import com.echothree.model.control.filter.common.exception.UnknownFilterStepNameException;
import com.echothree.model.control.filter.server.control.FilterControl;
import com.echothree.model.data.filter.server.entity.Filter;
import com.echothree.model.data.filter.server.entity.FilterKind;
import com.echothree.model.data.filter.server.entity.FilterStep;
import com.echothree.model.data.filter.server.entity.FilterType;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import com.echothree.util.server.validation.ParameterUtils;

public class FilterStepLogic
        extends BaseLogic {

    private FilterStepLogic() {
        super();
    }

    private static class FilterStepLogicHolder {
        static FilterStepLogic instance = new FilterStepLogic();
    }

    public static FilterStepLogic getInstance() {
        return FilterStepLogic.FilterStepLogicHolder.instance;
    }

    public FilterStep getFilterStepByName(final ExecutionErrorAccumulator eea, final Filter filter, final String filterStepName,
            final EntityPermission entityPermission) {
        var filterControl = Session.getModelController(FilterControl.class);
        var filterStep = filterControl.getFilterStepByName(filter, filterStepName, entityPermission);

        if(filterStep == null) {
            handleExecutionError(UnknownFilterStepNameException.class, eea, ExecutionErrors.UnknownFilterStepName.name(),
                    filter.getLastDetail().getFilterType().getLastDetail().getFilterKind().getLastDetail().getFilterKindName(),
                    filter.getLastDetail().getFilterType().getLastDetail().getFilterTypeName(),
                    filter.getLastDetail().getFilterName(), filterStepName);
        }

        return filterStep;
    }

    public FilterStep getFilterStepByName(final ExecutionErrorAccumulator eea, final Filter filter, final String filterStepName) {
        return getFilterStepByName(eea, filter, filterStepName, EntityPermission.READ_ONLY);
    }

    public FilterStep getFilterStepByNameForUpdate(final ExecutionErrorAccumulator eea, final Filter filter, final String filterStepName) {
        return getFilterStepByName(eea, filter, filterStepName, EntityPermission.READ_WRITE);
    }

    public FilterStep getFilterStepByName(final ExecutionErrorAccumulator eea, final String filterKindName, final String filterTypeName,
            final String filterName, final String filterStepName, final EntityPermission entityPermission) {
        var filterStepType = FilterLogic.getInstance().getFilterByName(eea, filterKindName, filterTypeName, filterName);
        FilterStep filterStep = null;

        if(!eea.hasExecutionErrors()) {
            filterStep = getFilterStepByName(eea, filterStepType, filterStepName, entityPermission);
        }

        return filterStep;
    }

    public FilterStep getFilterStepByName(final ExecutionErrorAccumulator eea, final String filterKindName,
            final String filterTypeName, final String filterName, final String filterStepName) {
        return getFilterStepByName(eea, filterKindName, filterTypeName, filterName, filterStepName, EntityPermission.READ_ONLY);
    }

    public FilterStep getFilterStepByNameForUpdate(final ExecutionErrorAccumulator eea, final String filterKindName,
            final String filterTypeName, final String filterName, final String filterStepName) {
        return getFilterStepByName(eea, filterKindName, filterTypeName, filterName, filterStepName, EntityPermission.READ_WRITE);
    }

    public FilterStep getFilterStepByUniversalSpec(final ExecutionErrorAccumulator eea, final FilterStepUniversalSpec universalSpec,
            final boolean allowDefault, final EntityPermission entityPermission) {
        var filterControl = Session.getModelController(FilterControl.class);
        var filterKindName = universalSpec.getFilterKindName();
        var filterTypeName = universalSpec.getFilterTypeName();
        var filterName = universalSpec.getFilterName();
        var filterStepName = universalSpec.getFilterStepName();
        var nameParameterCount= ParameterUtils.getInstance().countNonNullParameters(filterKindName, filterTypeName, filterName, filterStepName);
        var possibleEntitySpecs= EntityInstanceLogic.getInstance().countPossibleEntitySpecs(universalSpec);
        FilterStep filterStep = null;

        if(nameParameterCount < 5 && possibleEntitySpecs == 0) {
            FilterKind filterKind = null;
            FilterType filterType = null;
            Filter filter = null;

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

            if(filterTypeName == null && !eea.hasExecutionErrors()) {
                if(allowDefault) {
                    filterType = filterControl.getDefaultFilterType(filterKind);

                    if(filterType == null) {
                        handleExecutionError(UnknownDefaultFilterTypeException.class, eea, ExecutionErrors.UnknownDefaultFilterKind.name(),
                                filterKind.getLastDetail().getFilterKindName());
                    }
                } else {
                    handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
                }
            } else {
                filterType = FilterTypeLogic.getInstance().getFilterTypeByName(eea, filterKind, filterTypeName);
            }

            if(filterName == null && !eea.hasExecutionErrors()) {
                if(allowDefault) {
                    filter = filterControl.getDefaultFilter(filterType);

                    if(filter == null) {
                        handleExecutionError(UnknownDefaultFilterException.class, eea, ExecutionErrors.UnknownDefaultFilterKind.name(),
                                filterKind.getLastDetail().getFilterKindName());
                    }
                } else {
                    handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
                }
            } else {
                filter = FilterLogic.getInstance().getFilterByName(eea, filterType, filterName);
            }

            if(!eea.hasExecutionErrors()) {
                if(filterStepName == null) {
                    handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
                } else {
                    filterStep = getFilterStepByName(eea, filter, filterStepName, entityPermission);
                }
            }
        } else if(nameParameterCount == 0 && possibleEntitySpecs == 1) {
            var entityInstance = EntityInstanceLogic.getInstance().getEntityInstance(eea, universalSpec,
                    ComponentVendors.ECHOTHREE.name(), EntityTypes.FilterStep.name());

            if(!eea.hasExecutionErrors()) {
                filterStep = filterControl.getFilterStepByEntityInstance(entityInstance, entityPermission);
            }
        } else {
            handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
        }

        return filterStep;
    }

    public FilterStep getFilterStepByUniversalSpec(final ExecutionErrorAccumulator eea, final FilterStepUniversalSpec universalSpec,
            boolean allowDefault) {
        return getFilterStepByUniversalSpec(eea, universalSpec, allowDefault, EntityPermission.READ_ONLY);
    }

    public FilterStep getFilterStepByUniversalSpecForUpdate(final ExecutionErrorAccumulator eea, final FilterStepUniversalSpec universalSpec,
            boolean allowDefault) {
        return getFilterStepByUniversalSpec(eea, universalSpec, allowDefault, EntityPermission.READ_WRITE);
    }

}
