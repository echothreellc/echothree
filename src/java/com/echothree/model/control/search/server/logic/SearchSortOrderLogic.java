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

package com.echothree.model.control.search.server.logic;

import com.echothree.control.user.search.common.spec.SearchSortOrderUniversalSpec;
import com.echothree.model.control.core.common.ComponentVendors;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.model.control.core.common.exception.InvalidParameterCountException;
import com.echothree.model.control.core.server.logic.EntityInstanceLogic;
import com.echothree.model.control.search.common.exception.DuplicateSearchSortOrderNameException;
import com.echothree.model.control.search.common.exception.UnknownDefaultSearchKindException;
import com.echothree.model.control.search.common.exception.UnknownDefaultSearchSortOrderException;
import com.echothree.model.control.search.common.exception.UnknownSearchSortOrderNameException;
import com.echothree.model.control.search.server.control.SearchControl;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.search.server.entity.SearchKind;
import com.echothree.model.data.search.server.entity.SearchSortOrder;
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
public class SearchSortOrderLogic
        extends BaseLogic {

    protected SearchSortOrderLogic() {
        super();
    }

    public static SearchSortOrderLogic getInstance() {
        return CDI.current().select(SearchSortOrderLogic.class).get();
    }

    public SearchSortOrder createSearchSortOrder(final ExecutionErrorAccumulator eea, final String searchKindName, final String searchSortOrderName,
            final Boolean isDefault, final Integer sortOrder, final Language language, final String description, final BasePK createdBy) {
        var searchKind = SearchKindLogic.getInstance().getSearchKindByName(eea, searchKindName);
        SearchSortOrder searchSortOrder = null;

        if(eea == null || !eea.hasExecutionErrors()) {
            searchSortOrder = createSearchSortOrder(eea, searchKind, searchSortOrderName, isDefault, sortOrder, language, description, createdBy);
        }

        return searchSortOrder;
    }

    public SearchSortOrder createSearchSortOrder(final ExecutionErrorAccumulator eea, final SearchKind searchKind, final String searchSortOrderName,
            final Boolean isDefault, final Integer sortOrder, final Language language, final String description, final BasePK createdBy) {
        var searchControl = Session.getModelController(SearchControl.class);
        var searchSortOrder = searchControl.getSearchSortOrderByName(searchKind, searchSortOrderName);

        if(searchSortOrder == null) {
            searchSortOrder = searchControl.createSearchSortOrder(searchKind, searchSortOrderName, isDefault, sortOrder, createdBy);

            if(description != null) {
                searchControl.createSearchSortOrderDescription(searchSortOrder, language, description, createdBy);
            }
        } else {
            handleExecutionError(DuplicateSearchSortOrderNameException.class, eea, ExecutionErrors.DuplicateSearchSortOrderName.name(), searchSortOrderName);
        }
        return searchSortOrder;
    }

    public SearchSortOrder getSearchSortOrderByName(final ExecutionErrorAccumulator eea, final SearchKind searchKind, final String searchSortOrderName,
            final EntityPermission entityPermission) {
        var searchControl = Session.getModelController(SearchControl.class);
        var searchSortOrder = searchControl.getSearchSortOrderByName(searchKind, searchSortOrderName, entityPermission);

        if(searchSortOrder == null) {
            handleExecutionError(UnknownSearchSortOrderNameException.class, eea, ExecutionErrors.UnknownSearchSortOrderName.name(),
                    searchKind.getLastDetail().getSearchKindName(), searchSortOrderName);
        }

        return searchSortOrder;
    }

    public SearchSortOrder getSearchSortOrderByName(final ExecutionErrorAccumulator eea, final SearchKind searchKind, final String searchSortOrderName) {
        return getSearchSortOrderByName(eea, searchKind, searchSortOrderName, EntityPermission.READ_ONLY);
    }

    public SearchSortOrder getSearchSortOrderByNameForUpdate(final ExecutionErrorAccumulator eea, final SearchKind searchKind, final String searchSortOrderName) {
        return getSearchSortOrderByName(eea, searchKind, searchSortOrderName, EntityPermission.READ_WRITE);
    }

    public SearchSortOrder getSearchSortOrderByName(final ExecutionErrorAccumulator eea, final String searchKindName, final String searchSortOrderName,
            final EntityPermission entityPermission) {
        var searchKind = SearchKindLogic.getInstance().getSearchKindByName(eea, searchKindName);
        SearchSortOrder searchSortOrder = null;

        if(!eea.hasExecutionErrors()) {
            searchSortOrder = getSearchSortOrderByName(eea, searchKind, searchSortOrderName, entityPermission);
        }

        return searchSortOrder;
    }

    public SearchSortOrder getSearchSortOrderByName(final ExecutionErrorAccumulator eea, final String searchKindName, final String searchSortOrderName) {
        return getSearchSortOrderByName(eea, searchKindName, searchSortOrderName, EntityPermission.READ_ONLY);
    }

    public SearchSortOrder getSearchSortOrderByNameForUpdate(final ExecutionErrorAccumulator eea, final String searchKindName, final String searchSortOrderName) {
        return getSearchSortOrderByName(eea, searchKindName, searchSortOrderName, EntityPermission.READ_WRITE);
    }

    public SearchSortOrder getSearchSortOrderByUniversalSpec(final ExecutionErrorAccumulator eea, final SearchSortOrderUniversalSpec universalSpec,
            final boolean allowDefault, final EntityPermission entityPermission) {
        var searchControl = Session.getModelController(SearchControl.class);
        var searchKindName = universalSpec.getSearchKindName();
        var searchSortOrderName = universalSpec.getSearchSortOrderName();
        var nameParameterCount= ParameterUtils.getInstance().countNonNullParameters(searchKindName, searchSortOrderName);
        var possibleEntitySpecs= EntityInstanceLogic.getInstance().countPossibleEntitySpecs(universalSpec);
        SearchSortOrder searchSortOrder = null;

        if(nameParameterCount < 3 && possibleEntitySpecs == 0) {
            SearchKind searchKind = null;

            if(searchKindName == null) {
                if(allowDefault) {
                    searchKind = searchControl.getDefaultSearchKind();

                    if(searchKind == null) {
                        handleExecutionError(UnknownDefaultSearchKindException.class, eea, ExecutionErrors.UnknownDefaultSearchKind.name());
                    }
                } else {
                    handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
                }
            } else {
                searchKind = SearchKindLogic.getInstance().getSearchKindByName(eea, searchKindName);
            }

            if(!eea.hasExecutionErrors()) {
                if(searchSortOrderName == null) {
                    if(allowDefault) {
                        searchSortOrder = searchControl.getDefaultSearchSortOrder(searchKind, entityPermission);

                        if(searchSortOrder == null) {
                            handleExecutionError(UnknownDefaultSearchSortOrderException.class, eea, ExecutionErrors.UnknownDefaultSearchSortOrder.name());
                        }
                    } else {
                        handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
                    }
                } else {
                    searchSortOrder = getSearchSortOrderByName(eea, searchKind, searchSortOrderName, entityPermission);
                }
            }
        } else if(nameParameterCount == 0 && possibleEntitySpecs == 1) {
            var entityInstance = EntityInstanceLogic.getInstance().getEntityInstance(eea, universalSpec,
                    ComponentVendors.ECHO_THREE.name(), EntityTypes.SearchSortOrder.name());

            if(!eea.hasExecutionErrors()) {
                searchSortOrder = searchControl.getSearchSortOrderByEntityInstance(entityInstance, entityPermission);
            }
        } else {
            handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
        }

        return searchSortOrder;
    }

    public SearchSortOrder getSearchSortOrderByUniversalSpec(final ExecutionErrorAccumulator eea, final SearchSortOrderUniversalSpec universalSpec,
            boolean allowDefault) {
        return getSearchSortOrderByUniversalSpec(eea, universalSpec, allowDefault, EntityPermission.READ_ONLY);
    }

    public SearchSortOrder getSearchSortOrderByUniversalSpecForUpdate(final ExecutionErrorAccumulator eea, final SearchSortOrderUniversalSpec universalSpec,
            boolean allowDefault) {
        return getSearchSortOrderByUniversalSpec(eea, universalSpec, allowDefault, EntityPermission.READ_WRITE);
    }

    public void deleteSearchSortOrder(final ExecutionErrorAccumulator eea, final SearchSortOrder searchSortOrder,
            final BasePK deletedBy) {
        var searchControl = Session.getModelController(SearchControl.class);

        searchControl.deleteSearchSortOrder(searchSortOrder, deletedBy);
    }
}
