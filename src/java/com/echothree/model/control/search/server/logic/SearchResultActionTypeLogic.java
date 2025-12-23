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

package com.echothree.model.control.search.server.logic;

import com.echothree.control.user.search.common.spec.SearchResultActionTypeUniversalSpec;
import com.echothree.model.control.core.common.ComponentVendors;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.model.control.core.common.exception.InvalidParameterCountException;
import com.echothree.model.control.core.server.logic.EntityInstanceLogic;
import com.echothree.model.control.search.common.exception.DuplicateSearchResultActionTypeNameException;
import com.echothree.model.control.search.common.exception.UnknownDefaultSearchResultActionTypeException;
import com.echothree.model.control.search.common.exception.UnknownSearchResultActionTypeNameException;
import com.echothree.model.control.search.server.control.SearchControl;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.search.server.entity.SearchResultActionType;
import com.echothree.model.data.search.server.value.SearchResultActionTypeDetailValue;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.spi.CDI;

@ApplicationScoped
public class SearchResultActionTypeLogic
    extends BaseLogic {

    protected SearchResultActionTypeLogic() {
        super();
    }

    public static SearchResultActionTypeLogic getInstance() {
        return CDI.current().select(SearchResultActionTypeLogic.class).get();
    }

    public SearchResultActionType createSearchResultActionType(final ExecutionErrorAccumulator eea, final String searchResultActionTypeName,
            final Boolean isDefault, final Integer sortOrder, final Language language, final String description,
            final BasePK createdBy) {
        var searchControl = Session.getModelController(SearchControl.class);
        var searchResultActionType = searchControl.getSearchResultActionTypeByName(searchResultActionTypeName);

        if(searchResultActionType == null) {
            searchResultActionType = searchControl.createSearchResultActionType(searchResultActionTypeName, isDefault,
                    sortOrder, createdBy);

            if(description != null) {
                searchControl.createSearchResultActionTypeDescription(searchResultActionType, language, description, createdBy);
            }
        } else {
            handleExecutionError(DuplicateSearchResultActionTypeNameException.class, eea, ExecutionErrors.DuplicateSearchResultActionTypeName.name(), searchResultActionTypeName);
        }

        return searchResultActionType;
    }

    public SearchResultActionType getSearchResultActionTypeByName(final ExecutionErrorAccumulator eea, final String searchResultActionTypeName,
            final EntityPermission entityPermission) {
        var searchControl = Session.getModelController(SearchControl.class);
        var searchResultActionType = searchControl.getSearchResultActionTypeByName(searchResultActionTypeName, entityPermission);

        if(searchResultActionType == null) {
            handleExecutionError(UnknownSearchResultActionTypeNameException.class, eea, ExecutionErrors.UnknownSearchResultActionTypeName.name(), searchResultActionTypeName);
        }

        return searchResultActionType;
    }

    public SearchResultActionType getSearchResultActionTypeByName(final ExecutionErrorAccumulator eea, final String searchResultActionTypeName) {
        return getSearchResultActionTypeByName(eea, searchResultActionTypeName, EntityPermission.READ_ONLY);
    }

    public SearchResultActionType getSearchResultActionTypeByNameForUpdate(final ExecutionErrorAccumulator eea, final String searchResultActionTypeName) {
        return getSearchResultActionTypeByName(eea, searchResultActionTypeName, EntityPermission.READ_WRITE);
    }

    public SearchResultActionType getSearchResultActionTypeByUniversalSpec(final ExecutionErrorAccumulator eea,
            final SearchResultActionTypeUniversalSpec universalSpec, boolean allowDefault, final EntityPermission entityPermission) {
        SearchResultActionType searchResultActionType = null;
        var searchControl = Session.getModelController(SearchControl.class);
        var searchResultActionTypeName = universalSpec.getSearchResultActionTypeName();
        var parameterCount = (searchResultActionTypeName == null ? 0 : 1) + EntityInstanceLogic.getInstance().countPossibleEntitySpecs(universalSpec);

        switch(parameterCount) {
            case 0 -> {
                if(allowDefault) {
                    searchResultActionType = searchControl.getDefaultSearchResultActionType(entityPermission);

                    if(searchResultActionType == null) {
                        handleExecutionError(UnknownDefaultSearchResultActionTypeException.class, eea, ExecutionErrors.UnknownDefaultSearchResultActionType.name());
                    }
                } else {
                    handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
                }
            }
            case 1 -> {
                if(searchResultActionTypeName == null) {
                    var entityInstance = EntityInstanceLogic.getInstance().getEntityInstance(eea, universalSpec,
                            ComponentVendors.ECHO_THREE.name(), EntityTypes.SearchResultActionType.name());

                    if(!eea.hasExecutionErrors()) {
                        searchResultActionType = searchControl.getSearchResultActionTypeByEntityInstance(entityInstance, entityPermission);
                    }
                } else {
                    searchResultActionType = getSearchResultActionTypeByName(eea, searchResultActionTypeName, entityPermission);
                }
            }
            default ->
                    handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
        }

        return searchResultActionType;
    }

    public SearchResultActionType getSearchResultActionTypeByUniversalSpec(final ExecutionErrorAccumulator eea,
            final SearchResultActionTypeUniversalSpec universalSpec, boolean allowDefault) {
        return getSearchResultActionTypeByUniversalSpec(eea, universalSpec, allowDefault, EntityPermission.READ_ONLY);
    }

    public SearchResultActionType getSearchResultActionTypeByUniversalSpecForUpdate(final ExecutionErrorAccumulator eea,
            final SearchResultActionTypeUniversalSpec universalSpec, boolean allowDefault) {
        return getSearchResultActionTypeByUniversalSpec(eea, universalSpec, allowDefault, EntityPermission.READ_WRITE);
    }

    public void updateSearchResultActionTypeFromValue(final SearchResultActionTypeDetailValue searchResultActionTypeDetailValue,
            final BasePK updatedBy) {
        final var searchControl = Session.getModelController(SearchControl.class);

        searchControl.updateSearchResultActionTypeFromValue(searchResultActionTypeDetailValue, updatedBy);
    }
    
    public void deleteSearchResultActionType(final ExecutionErrorAccumulator eea, final SearchResultActionType searchResultActionType,
            final BasePK deletedBy) {
        var searchControl = Session.getModelController(SearchControl.class);

        searchControl.deleteSearchResultActionType(searchResultActionType, deletedBy);
    }

}
