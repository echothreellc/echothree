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

import com.echothree.control.user.filter.common.spec.FilterUniversalSpec;
import com.echothree.model.control.club.server.control.ClubControl;
import com.echothree.model.control.core.common.ComponentVendors;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.model.control.core.common.exception.InvalidParameterCountException;
import com.echothree.model.control.core.server.logic.EntityInstanceLogic;
import com.echothree.model.control.filter.common.exception.CannotDeleteFilterInUseException;
import com.echothree.model.control.filter.common.exception.DuplicateFilterNameException;
import com.echothree.model.control.filter.common.exception.UnknownDefaultFilterException;
import com.echothree.model.control.filter.common.exception.UnknownDefaultFilterKindException;
import com.echothree.model.control.filter.common.exception.UnknownDefaultFilterTypeException;
import com.echothree.model.control.filter.common.exception.UnknownFilterNameException;
import com.echothree.model.control.filter.server.control.FilterControl;
import com.echothree.model.control.offer.server.control.OfferControl;
import com.echothree.model.control.selector.common.SelectorKinds;
import com.echothree.model.control.selector.common.SelectorTypes;
import com.echothree.model.control.selector.server.logic.SelectorLogic;
import com.echothree.model.control.vendor.server.control.VendorControl;
import com.echothree.model.data.filter.server.entity.Filter;
import com.echothree.model.data.filter.server.entity.FilterAdjustment;
import com.echothree.model.data.filter.server.entity.FilterKind;
import com.echothree.model.data.filter.server.entity.FilterType;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.selector.server.entity.Selector;
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
public class FilterLogic
        extends BaseLogic {

    protected FilterLogic() {
        super();
    }

    public static FilterLogic getInstance() {
        return CDI.current().select(FilterLogic.class).get();
    }

    public Filter createFilter(final ExecutionErrorAccumulator eea, final String filterKindName, final String filterTypeName,
            final String filterName, final String initialFilterAdjustmentName, final String filterItemSelectorName,
            final Boolean isDefault, final Integer sortOrder, final Language language, final String description,
            final BasePK createdBy) {
        var filterType = FilterTypeLogic.getInstance().getFilterTypeByName(eea, filterKindName, filterTypeName);
        Filter filter = null;

        if(eea == null || !eea.hasExecutionErrors()) {
            var initialFilterAdjustment = FilterAdjustmentLogic.getInstance().getFilterAdjustmentByName(eea,
                    filterType.getLastDetail().getFilterKind(), initialFilterAdjustmentName);

            if(eea == null || !eea.hasExecutionErrors()) {
                var filterItemSelector = filterItemSelectorName == null ? null :
                        SelectorLogic.getInstance().getSelectorByName(eea, SelectorKinds.ITEM.name(),
                                SelectorTypes.FILTER.name(), filterItemSelectorName);

                if(eea == null || !eea.hasExecutionErrors()) {
                    filter = createFilter(eea, filterType, filterName, initialFilterAdjustment, filterItemSelector,
                            isDefault, sortOrder, language, description, createdBy);
                }
            }
        }

        return filter;
    }

    public Filter createFilter(final ExecutionErrorAccumulator eea, final FilterType filterType, final String filterName,
            final FilterAdjustment initialFilterAdjustment, final Selector filterItemSelector, final Boolean isDefault,
            final Integer sortOrder, final Language language, final String description, final BasePK createdBy) {
        var filterControl = Session.getModelController(FilterControl.class);
        var filter = filterControl.getFilterByName(filterType, filterName);

        if(filter == null) {
            filter = filterControl.createFilter(filterType, filterName, initialFilterAdjustment, filterItemSelector,
                    isDefault, sortOrder, createdBy);

            if(description != null) {
                filterControl.createFilterDescription(filter, language, description, createdBy);
            }
        } else {
            handleExecutionError(DuplicateFilterNameException.class, eea, ExecutionErrors.DuplicateFilterName.name(), filterName);
        }
        return filter;
    }

    public Filter getFilterByName(final ExecutionErrorAccumulator eea, final FilterType filterType, final String filterName,
            final EntityPermission entityPermission) {
        var filterControl = Session.getModelController(FilterControl.class);
        var filter = filterControl.getFilterByName(filterType, filterName, entityPermission);

        if(filter == null) {
            handleExecutionError(UnknownFilterNameException.class, eea, ExecutionErrors.UnknownFilterName.name(),
                    filterType.getLastDetail().getFilterKind().getLastDetail().getFilterKindName(),
                    filterType.getLastDetail().getFilterTypeName(), filterName);
        }

        return filter;
    }

    public Filter getFilterByName(final ExecutionErrorAccumulator eea, final FilterType filterType, final String filterName) {
        return getFilterByName(eea, filterType, filterName, EntityPermission.READ_ONLY);
    }

    public Filter getFilterByNameForUpdate(final ExecutionErrorAccumulator eea, final FilterType filterType, final String filterName) {
        return getFilterByName(eea, filterType, filterName, EntityPermission.READ_WRITE);
    }

    public Filter getFilterByName(final ExecutionErrorAccumulator eea, final String filterKindName, final String filterTypeName,
            final String filterName, final EntityPermission entityPermission) {
        var filterType = FilterTypeLogic.getInstance().getFilterTypeByName(eea, filterKindName, filterTypeName);
        Filter filter = null;

        if(!eea.hasExecutionErrors()) {
            filter = getFilterByName(eea, filterType, filterName, entityPermission);
        }

        return filter;
    }

    public Filter getFilterByName(final ExecutionErrorAccumulator eea, final String filterKindName, final String filterTypeName, final String filterName) {
        return getFilterByName(eea, filterKindName, filterTypeName, filterName, EntityPermission.READ_ONLY);
    }

    public Filter getFilterByNameForUpdate(final ExecutionErrorAccumulator eea, final String filterKindName, final String filterTypeName, final String filterName) {
        return getFilterByName(eea, filterKindName, filterTypeName, filterName, EntityPermission.READ_WRITE);
    }

    public Filter getFilterByUniversalSpec(final ExecutionErrorAccumulator eea, final FilterUniversalSpec universalSpec,
            final boolean allowDefault, final EntityPermission entityPermission) {
        var filterControl = Session.getModelController(FilterControl.class);
        var filterKindName = universalSpec.getFilterKindName();
        var filterTypeName = universalSpec.getFilterTypeName();
        var filterName = universalSpec.getFilterName();
        var nameParameterCount= ParameterUtils.getInstance().countNonNullParameters(filterKindName, filterTypeName, filterName);
        var possibleEntitySpecs= EntityInstanceLogic.getInstance().countPossibleEntitySpecs(universalSpec);
        Filter filter = null;

        if(nameParameterCount < 4 && possibleEntitySpecs == 0) {
            FilterKind filterKind = null;
            FilterType filterType = null;

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

            if(!eea.hasExecutionErrors()) {
                if(filterName == null) {
                    if(allowDefault) {
                        filter = filterControl.getDefaultFilter(filterType, entityPermission);

                        if(filter == null) {
                            handleExecutionError(UnknownDefaultFilterException.class, eea, ExecutionErrors.UnknownDefaultFilter.name(),
                                    filterKind.getLastDetail().getFilterKindName(),
                                    filterType.getLastDetail().getFilterTypeName());
                        }
                    } else {
                        handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
                    }
                } else {
                    filter = getFilterByName(eea, filterType, filterName, entityPermission);
                }
            }
        } else if(nameParameterCount == 0 && possibleEntitySpecs == 1) {
            var entityInstance = EntityInstanceLogic.getInstance().getEntityInstance(eea, universalSpec,
                    ComponentVendors.ECHO_THREE.name(), EntityTypes.Filter.name());

            if(!eea.hasExecutionErrors()) {
                filter = filterControl.getFilterByEntityInstance(entityInstance, entityPermission);
            }
        } else {
            handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
        }

        return filter;
    }

    public Filter getFilterByUniversalSpec(final ExecutionErrorAccumulator eea, final FilterUniversalSpec universalSpec,
            boolean allowDefault) {
        return getFilterByUniversalSpec(eea, universalSpec, allowDefault, EntityPermission.READ_ONLY);
    }

    public Filter getFilterByUniversalSpecForUpdate(final ExecutionErrorAccumulator eea, final FilterUniversalSpec universalSpec,
            boolean allowDefault) {
        return getFilterByUniversalSpec(eea, universalSpec, allowDefault, EntityPermission.READ_WRITE);
    }

    public void deleteFilter(final ExecutionErrorAccumulator eea, final Filter filter, final BasePK deletedBy) {
        var clubControl = Session.getModelController(ClubControl.class);
        var offerControl = Session.getModelController(OfferControl.class);
        var vendorControl = Session.getModelController(VendorControl.class);

        if(clubControl.countClubsByFilter(filter) == 0
                && offerControl.countOffersByFilter(filter) == 0
                && vendorControl.countVendorByFilter(filter) == 0) {
            var filterControl = Session.getModelController(FilterControl.class);

            filterControl.deleteFilter(filter, deletedBy);
        } else {
            var filterDetail = filter.getLastDetail();

            handleExecutionError(CannotDeleteFilterInUseException.class, eea, ExecutionErrors.CannotDeleteFilterInUse.name(),
                    filterDetail.getFilterType().getLastDetail().getFilterKind().getLastDetail().getFilterKindName(),
                    filterDetail.getFilterType().getLastDetail().getFilterTypeName(),
                    filterDetail.getFilterName());
        }
    }

}
