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

import com.echothree.control.user.search.common.spec.SearchSortDirectionUniversalSpec;
import com.echothree.model.control.core.common.ComponentVendors;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.model.control.core.common.exception.InvalidParameterCountException;
import com.echothree.model.control.core.server.logic.EntityInstanceLogic;
import com.echothree.model.control.search.common.exception.DuplicateSearchSortDirectionNameException;
import com.echothree.model.control.search.common.exception.UnknownDefaultSearchSortDirectionException;
import com.echothree.model.control.search.common.exception.UnknownSearchSortDirectionNameException;
import com.echothree.model.control.search.server.control.SearchControl;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.search.server.entity.SearchSortDirection;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.spi.CDI;

@ApplicationScoped
public class SearchSortDirectionLogic
        extends BaseLogic {

    protected SearchSortDirectionLogic() {
        super();
    }

    public static SearchSortDirectionLogic getInstance() {
        return CDI.current().select(SearchSortDirectionLogic.class).get();
    }

    public SearchSortDirection createSearchSortDirection(final ExecutionErrorAccumulator eea, final String searchSortDirectionName,
            final Boolean isDefault, final Integer sortOrder, final Language language, final String description,
            final BasePK createdBy) {
        var searchControl = Session.getModelController(SearchControl.class);
        var searchSortDirection = searchControl.getSearchSortDirectionByName(searchSortDirectionName);

        if(searchSortDirection == null) {
            searchSortDirection = searchControl.createSearchSortDirection(searchSortDirectionName, isDefault, sortOrder, createdBy);

            if(description != null) {
                searchControl.createSearchSortDirectionDescription(searchSortDirection, language, description, createdBy);
            }
        } else {
            handleExecutionError(DuplicateSearchSortDirectionNameException.class, eea, ExecutionErrors.DuplicateSearchSortDirectionName.name(), searchSortDirectionName);
        }

        return searchSortDirection;
    }

    public SearchSortDirection getSearchSortDirectionByName(final ExecutionErrorAccumulator eea, final String searchSortDirectionName,
            final EntityPermission entityPermission) {
        var searchControl = Session.getModelController(SearchControl.class);
        var searchSortDirection = searchControl.getSearchSortDirectionByName(searchSortDirectionName, entityPermission);

        if(searchSortDirection == null) {
            handleExecutionError(UnknownSearchSortDirectionNameException.class, eea, ExecutionErrors.UnknownSearchSortDirectionName.name(), searchSortDirectionName);
        }

        return searchSortDirection;
    }

    public SearchSortDirection getSearchSortDirectionByName(final ExecutionErrorAccumulator eea, final String searchSortDirectionName) {
        return getSearchSortDirectionByName(eea, searchSortDirectionName, EntityPermission.READ_ONLY);
    }

    public SearchSortDirection getSearchSortDirectionByNameForUpdate(final ExecutionErrorAccumulator eea, final String searchSortDirectionName) {
        return getSearchSortDirectionByName(eea, searchSortDirectionName, EntityPermission.READ_WRITE);
    }

    public SearchSortDirection getSearchSortDirectionByUniversalSpec(final ExecutionErrorAccumulator eea,
            final SearchSortDirectionUniversalSpec universalSpec, boolean allowDefault, final EntityPermission entityPermission) {
        SearchSortDirection searchSortDirection = null;
        var searchControl = Session.getModelController(SearchControl.class);
        var searchSortDirectionName = universalSpec.getSearchSortDirectionName();
        var parameterCount = (searchSortDirectionName == null ? 0 : 1) + EntityInstanceLogic.getInstance().countPossibleEntitySpecs(universalSpec);

        switch(parameterCount) {
            case 0 -> {
                if(allowDefault) {
                    searchSortDirection = searchControl.getDefaultSearchSortDirection(entityPermission);

                    if(searchSortDirection == null) {
                        handleExecutionError(UnknownDefaultSearchSortDirectionException.class, eea, ExecutionErrors.UnknownDefaultSearchSortDirection.name());
                    }
                } else {
                    handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
                }
            }
            case 1 -> {
                if(searchSortDirectionName == null) {
                    var entityInstance = EntityInstanceLogic.getInstance().getEntityInstance(eea, universalSpec,
                            ComponentVendors.ECHO_THREE.name(), EntityTypes.SearchSortDirection.name());

                    if(!eea.hasExecutionErrors()) {
                        searchSortDirection = searchControl.getSearchSortDirectionByEntityInstance(entityInstance, entityPermission);
                    }
                } else {
                    searchSortDirection = getSearchSortDirectionByName(eea, searchSortDirectionName, entityPermission);
                }
            }
            default ->
                    handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
        }

        return searchSortDirection;
    }

    public SearchSortDirection getSearchSortDirectionByUniversalSpec(final ExecutionErrorAccumulator eea,
            final SearchSortDirectionUniversalSpec universalSpec, boolean allowDefault) {
        return getSearchSortDirectionByUniversalSpec(eea, universalSpec, allowDefault, EntityPermission.READ_ONLY);
    }

    public SearchSortDirection getSearchSortDirectionByUniversalSpecForUpdate(final ExecutionErrorAccumulator eea,
            final SearchSortDirectionUniversalSpec universalSpec, boolean allowDefault) {
        return getSearchSortDirectionByUniversalSpec(eea, universalSpec, allowDefault, EntityPermission.READ_WRITE);
    }

    public void deleteSearchSortDirection(final ExecutionErrorAccumulator eea, final SearchSortDirection searchSortDirection,
            final BasePK deletedBy) {
        var searchControl = Session.getModelController(SearchControl.class);

        searchControl.deleteSearchSortDirection(searchSortDirection, deletedBy);
    }

}
