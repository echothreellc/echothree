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

import com.echothree.control.user.search.common.spec.SearchTypeUniversalSpec;
import com.echothree.model.control.core.common.ComponentVendors;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.model.control.core.common.exception.InvalidParameterCountException;
import com.echothree.model.control.core.server.logic.EntityInstanceLogic;
import com.echothree.model.control.search.common.exception.DuplicateSearchTypeNameException;
import com.echothree.model.control.search.common.exception.UnknownDefaultSearchKindException;
import com.echothree.model.control.search.common.exception.UnknownDefaultSearchTypeException;
import com.echothree.model.control.search.common.exception.UnknownSearchTypeNameException;
import com.echothree.model.control.search.server.control.SearchControl;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.search.server.entity.SearchKind;
import com.echothree.model.data.search.server.entity.SearchType;
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
public class SearchTypeLogic
        extends BaseLogic {

    protected SearchTypeLogic() {
        super();
    }

    public static SearchTypeLogic getInstance() {
        return CDI.current().select(SearchTypeLogic.class).get();
    }

    public SearchType createSearchType(final ExecutionErrorAccumulator eea, final String searchKindName, final String searchTypeName,
            final Boolean isDefault, final Integer sortOrder, final Language language, final String description, final BasePK createdBy) {
        var searchKind = SearchKindLogic.getInstance().getSearchKindByName(eea, searchKindName);
        SearchType searchType = null;

        if(eea == null || !eea.hasExecutionErrors()) {
            searchType = createSearchType(eea, searchKind, searchTypeName, isDefault, sortOrder, language, description, createdBy);
        }

        return searchType;
    }

    public SearchType createSearchType(final ExecutionErrorAccumulator eea, final SearchKind searchKind, final String searchTypeName,
            final Boolean isDefault, final Integer sortOrder, final Language language, final String description, final BasePK createdBy) {
        var searchControl = Session.getModelController(SearchControl.class);
        var searchType = searchControl.getSearchTypeByName(searchKind, searchTypeName);

        if(searchType == null) {
            searchType = searchControl.createSearchType(searchKind, searchTypeName, isDefault, sortOrder, createdBy);

            if(description != null) {
                searchControl.createSearchTypeDescription(searchType, language, description, createdBy);
            }
        } else {
            handleExecutionError(DuplicateSearchTypeNameException.class, eea, ExecutionErrors.DuplicateSearchTypeName.name(), searchTypeName);
        }
        return searchType;
    }

    public SearchType getSearchTypeByName(final ExecutionErrorAccumulator eea, final SearchKind searchKind, final String searchTypeName,
            final EntityPermission entityPermission) {
        var searchControl = Session.getModelController(SearchControl.class);
        var searchType = searchControl.getSearchTypeByName(searchKind, searchTypeName, entityPermission);

        if(searchType == null) {
            handleExecutionError(UnknownSearchTypeNameException.class, eea, ExecutionErrors.UnknownSearchTypeName.name(),
                    searchKind.getLastDetail().getSearchKindName(), searchTypeName);
        }

        return searchType;
    }

    public SearchType getSearchTypeByName(final ExecutionErrorAccumulator eea, final SearchKind searchKind, final String searchTypeName) {
        return getSearchTypeByName(eea, searchKind, searchTypeName, EntityPermission.READ_ONLY);
    }

    public SearchType getSearchTypeByNameForUpdate(final ExecutionErrorAccumulator eea, final SearchKind searchKind, final String searchTypeName) {
        return getSearchTypeByName(eea, searchKind, searchTypeName, EntityPermission.READ_WRITE);
    }

    public SearchType getSearchTypeByName(final ExecutionErrorAccumulator eea, final String searchKindName, final String searchTypeName,
            final EntityPermission entityPermission) {
        var searchKind = SearchKindLogic.getInstance().getSearchKindByName(eea, searchKindName);
        SearchType searchType = null;

        if(!eea.hasExecutionErrors()) {
            searchType = getSearchTypeByName(eea, searchKind, searchTypeName, entityPermission);
        }

        return searchType;
    }

    public SearchType getSearchTypeByName(final ExecutionErrorAccumulator eea, final String searchKindName, final String searchTypeName) {
        return getSearchTypeByName(eea, searchKindName, searchTypeName, EntityPermission.READ_ONLY);
    }

    public SearchType getSearchTypeByNameForUpdate(final ExecutionErrorAccumulator eea, final String searchKindName, final String searchTypeName) {
        return getSearchTypeByName(eea, searchKindName, searchTypeName, EntityPermission.READ_WRITE);
    }

    public SearchType getSearchTypeByUniversalSpec(final ExecutionErrorAccumulator eea, final SearchTypeUniversalSpec universalSpec,
            final boolean allowDefault, final EntityPermission entityPermission) {
        var searchControl = Session.getModelController(SearchControl.class);
        var searchKindName = universalSpec.getSearchKindName();
        var searchTypeName = universalSpec.getSearchTypeName();
        var nameParameterCount= ParameterUtils.getInstance().countNonNullParameters(searchKindName, searchTypeName);
        var possibleEntitySpecs= EntityInstanceLogic.getInstance().countPossibleEntitySpecs(universalSpec);
        SearchType searchType = null;

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
                if(searchTypeName == null) {
                    if(allowDefault) {
                        searchType = searchControl.getDefaultSearchType(searchKind, entityPermission);

                        if(searchType == null) {
                            handleExecutionError(UnknownDefaultSearchTypeException.class, eea, ExecutionErrors.UnknownDefaultSearchType.name());
                        }
                    } else {
                        handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
                    }
                } else {
                    searchType = getSearchTypeByName(eea, searchKind, searchTypeName, entityPermission);
                }
            }
        } else if(nameParameterCount == 0 && possibleEntitySpecs == 1) {
            var entityInstance = EntityInstanceLogic.getInstance().getEntityInstance(eea, universalSpec,
                    ComponentVendors.ECHO_THREE.name(), EntityTypes.SearchType.name());

            if(!eea.hasExecutionErrors()) {
                searchType = searchControl.getSearchTypeByEntityInstance(entityInstance, entityPermission);
            }
        } else {
            handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
        }

        return searchType;
    }

    public SearchType getSearchTypeByUniversalSpec(final ExecutionErrorAccumulator eea, final SearchTypeUniversalSpec universalSpec,
            boolean allowDefault) {
        return getSearchTypeByUniversalSpec(eea, universalSpec, allowDefault, EntityPermission.READ_ONLY);
    }

    public SearchType getSearchTypeByUniversalSpecForUpdate(final ExecutionErrorAccumulator eea, final SearchTypeUniversalSpec universalSpec,
            boolean allowDefault) {
        return getSearchTypeByUniversalSpec(eea, universalSpec, allowDefault, EntityPermission.READ_WRITE);
    }

    public void deleteSearchType(final ExecutionErrorAccumulator eea, final SearchType searchType,
            final BasePK deletedBy) {
        var searchControl = Session.getModelController(SearchControl.class);

        searchControl.deleteSearchType(searchType, deletedBy);
    }
}
