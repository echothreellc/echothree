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

package com.echothree.model.control.filter.server.logic;

import com.echothree.control.user.filter.common.spec.FilterStepElementUniversalSpec;
import com.echothree.model.control.core.common.ComponentVendors;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.model.control.core.common.exception.InvalidParameterCountException;
import com.echothree.model.control.core.server.logic.EntityInstanceLogic;
import com.echothree.model.control.filter.common.exception.UnknownDefaultFilterException;
import com.echothree.model.control.filter.common.exception.UnknownDefaultFilterKindException;
import com.echothree.model.control.filter.common.exception.UnknownDefaultFilterTypeException;
import com.echothree.model.control.filter.common.exception.UnknownFilterStepElementNameException;
import com.echothree.model.control.filter.server.control.FilterControl;
import com.echothree.model.data.filter.server.entity.Filter;
import com.echothree.model.data.filter.server.entity.FilterKind;
import com.echothree.model.data.filter.server.entity.FilterStep;
import com.echothree.model.data.filter.server.entity.FilterStepElement;
import com.echothree.model.data.filter.server.entity.FilterType;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import com.echothree.util.server.validation.ParameterUtils;

public class FilterStepElementLogic
        extends BaseLogic {

    private FilterStepElementLogic() {
        super();
    }

    private static class FilterStepElementLogicHolder {
        static FilterStepElementLogic instance = new FilterStepElementLogic();
    }

    public static FilterStepElementLogic getInstance() {
        return FilterStepElementLogicHolder.instance;
    }

    public FilterStepElement getFilterStepElementByName(final ExecutionErrorAccumulator eea, final FilterStep filterStep,
            final String filterStepElementName, final EntityPermission entityPermission) {
        var filterControl = Session.getModelController(FilterControl.class);
        var filterStepElement = filterControl.getFilterStepElementByName(filterStep, filterStepElementName, entityPermission);

        if(filterStepElement == null) {
            handleExecutionError(UnknownFilterStepElementNameException.class, eea, ExecutionErrors.UnknownFilterStepElementName.name(),
                    filterStep.getLastDetail().getFilter().getLastDetail().getFilterType().getLastDetail().getFilterKind().getLastDetail().getFilterKindName(),
                    filterStep.getLastDetail().getFilter().getLastDetail().getFilterType().getLastDetail().getFilterTypeName(),
                    filterStep.getLastDetail().getFilter().getLastDetail().getFilterName(),
                    filterStep.getLastDetail().getFilterStepName(),
                    filterStepElementName);
        }

        return filterStepElement;
    }

    public FilterStepElement getFilterStepElementByName(final ExecutionErrorAccumulator eea, final FilterStep filterStep,
            final String filterStepElementName) {
        return getFilterStepElementByName(eea, filterStep, filterStepElementName, EntityPermission.READ_ONLY);
    }

    public FilterStepElement getFilterStepElementByNameForUpdate(final ExecutionErrorAccumulator eea, final FilterStep filterStep,
            final String filterStepElementName) {
        return getFilterStepElementByName(eea, filterStep, filterStepElementName, EntityPermission.READ_WRITE);
    }

    public FilterStepElement getFilterStepElementByUniversalSpec(final ExecutionErrorAccumulator eea,
            final FilterStepElementUniversalSpec universalSpec, final boolean allowDefault, final EntityPermission entityPermission) {
        var filterControl = Session.getModelController(FilterControl.class);
        var filterKindName = universalSpec.getFilterKindName();
        var filterTypeName = universalSpec.getFilterTypeName();
        var filterName = universalSpec.getFilterName();
        var filterStepName = universalSpec.getFilterStepName();
        var filterStepElementName = universalSpec.getFilterStepElementName();
        var nameParameterCount = ParameterUtils.getInstance().countNonNullParameters(filterKindName, filterTypeName, filterName, filterStepName, filterStepElementName);
        var possibleEntitySpecs = EntityInstanceLogic.getInstance().countPossibleEntitySpecs(universalSpec);
        FilterStepElement filterStepElement = null;

        if(nameParameterCount < 6 && possibleEntitySpecs == 0) {
            FilterKind filterKind = null;
            FilterType filterType = null;
            Filter filter = null;
            FilterStep filterStep = null;

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
            }

            if(!eea.hasExecutionErrors()) {
                if(filterName == null) {
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
            }

            if(!eea.hasExecutionErrors()) {
                if(filterStepName == null) {
                    // FilterStep does not have a default.
                    handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
                } else {
                    filterStep = FilterStepLogic.getInstance().getFilterStepByName(eea, filter, filterStepName);
                }
            }
            if(!eea.hasExecutionErrors()) {
                if(filterStepElementName == null) {
                    // FilterStepElement does not have a default.
                    handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
                } else {
                    filterStepElement = getFilterStepElementByName(eea, filterStep, filterStepElementName, entityPermission);
                }
            }
        } else if(nameParameterCount == 0 && possibleEntitySpecs == 1) {
            var entityInstance = EntityInstanceLogic.getInstance().getEntityInstance(eea, universalSpec,
                    ComponentVendors.ECHO_THREE.name(), EntityTypes.FilterStepElement.name());

            if(!eea.hasExecutionErrors()) {
                filterStepElement = filterControl.getFilterStepElementByEntityInstance(entityInstance, entityPermission);
            }
        } else {
            handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
        }

        return filterStepElement;
    }

    public FilterStepElement getFilterStepElementByUniversalSpec(final ExecutionErrorAccumulator eea,
            final FilterStepElementUniversalSpec universalSpec, boolean allowDefault) {
        return getFilterStepElementByUniversalSpec(eea, universalSpec, allowDefault, EntityPermission.READ_ONLY);
    }

    public FilterStepElement getFilterStepElementByUniversalSpecForUpdate(final ExecutionErrorAccumulator eea,
            final FilterStepElementUniversalSpec universalSpec, boolean allowDefault) {
        return getFilterStepElementByUniversalSpec(eea, universalSpec, allowDefault, EntityPermission.READ_WRITE);
    }

}
