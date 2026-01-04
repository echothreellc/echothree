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

import com.echothree.control.user.search.common.spec.SearchUseTypeUniversalSpec;
import com.echothree.model.control.core.common.ComponentVendors;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.model.control.core.common.exception.InvalidParameterCountException;
import com.echothree.model.control.core.server.logic.EntityInstanceLogic;
import com.echothree.model.control.search.common.exception.DuplicateSearchUseTypeNameException;
import com.echothree.model.control.search.common.exception.UnknownDefaultSearchUseTypeException;
import com.echothree.model.control.search.common.exception.UnknownSearchUseTypeNameException;
import com.echothree.model.control.search.server.control.SearchControl;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.search.server.entity.SearchUseType;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.spi.CDI;

@ApplicationScoped
public class SearchUseTypeLogic
        extends BaseLogic {

    protected SearchUseTypeLogic() {
        super();
    }

    public static SearchUseTypeLogic getInstance() {
        return CDI.current().select(SearchUseTypeLogic.class).get();
    }

    public SearchUseType createSearchUseType(final ExecutionErrorAccumulator eea, final String searchUseTypeName,
            final Boolean isDefault, final Integer sortOrder, final Language language, final String description,
            final BasePK createdBy) {
        var searchControl = Session.getModelController(SearchControl.class);
        var searchUseType = searchControl.getSearchUseTypeByName(searchUseTypeName);

        if(searchUseType == null) {
            searchUseType = searchControl.createSearchUseType(searchUseTypeName, isDefault, sortOrder, createdBy);

            if(description != null) {
                searchControl.createSearchUseTypeDescription(searchUseType, language, description, createdBy);
            }
        } else {
            handleExecutionError(DuplicateSearchUseTypeNameException.class, eea, ExecutionErrors.DuplicateSearchUseTypeName.name(), searchUseTypeName);
        }

        return searchUseType;
    }

    public SearchUseType getSearchUseTypeByName(final ExecutionErrorAccumulator eea, final String searchUseTypeName,
            final EntityPermission entityPermission) {
        var searchControl = Session.getModelController(SearchControl.class);
        var searchUseType = searchControl.getSearchUseTypeByName(searchUseTypeName, entityPermission);

        if(searchUseType == null) {
            handleExecutionError(UnknownSearchUseTypeNameException.class, eea, ExecutionErrors.UnknownSearchUseTypeName.name(), searchUseTypeName);
        }

        return searchUseType;
    }

    public SearchUseType getSearchUseTypeByName(final ExecutionErrorAccumulator eea, final String searchUseTypeName) {
        return getSearchUseTypeByName(eea, searchUseTypeName, EntityPermission.READ_ONLY);
    }

    public SearchUseType getSearchUseTypeByNameForUpdate(final ExecutionErrorAccumulator eea, final String searchUseTypeName) {
        return getSearchUseTypeByName(eea, searchUseTypeName, EntityPermission.READ_WRITE);
    }

    public SearchUseType getSearchUseTypeByUniversalSpec(final ExecutionErrorAccumulator eea,
            final SearchUseTypeUniversalSpec universalSpec, boolean allowDefault, final EntityPermission entityPermission) {
        SearchUseType searchUseType = null;
        var searchControl = Session.getModelController(SearchControl.class);
        var searchUseTypeName = universalSpec.getSearchUseTypeName();
        var parameterCount = (searchUseTypeName == null ? 0 : 1) + EntityInstanceLogic.getInstance().countPossibleEntitySpecs(universalSpec);

        switch(parameterCount) {
            case 0 -> {
                if(allowDefault) {
                    searchUseType = searchControl.getDefaultSearchUseType(entityPermission);

                    if(searchUseType == null) {
                        handleExecutionError(UnknownDefaultSearchUseTypeException.class, eea, ExecutionErrors.UnknownDefaultSearchUseType.name());
                    }
                } else {
                    handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
                }
            }
            case 1 -> {
                if(searchUseTypeName == null) {
                    var entityInstance = EntityInstanceLogic.getInstance().getEntityInstance(eea, universalSpec,
                            ComponentVendors.ECHO_THREE.name(), EntityTypes.SearchUseType.name());

                    if(!eea.hasExecutionErrors()) {
                        searchUseType = searchControl.getSearchUseTypeByEntityInstance(entityInstance, entityPermission);
                    }
                } else {
                    searchUseType = getSearchUseTypeByName(eea, searchUseTypeName, entityPermission);
                }
            }
            default ->
                    handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
        }

        return searchUseType;
    }

    public SearchUseType getSearchUseTypeByUniversalSpec(final ExecutionErrorAccumulator eea,
            final SearchUseTypeUniversalSpec universalSpec, boolean allowDefault) {
        return getSearchUseTypeByUniversalSpec(eea, universalSpec, allowDefault, EntityPermission.READ_ONLY);
    }

    public SearchUseType getSearchUseTypeByUniversalSpecForUpdate(final ExecutionErrorAccumulator eea,
            final SearchUseTypeUniversalSpec universalSpec, boolean allowDefault) {
        return getSearchUseTypeByUniversalSpec(eea, universalSpec, allowDefault, EntityPermission.READ_WRITE);
    }

    public void deleteSearchUseType(final ExecutionErrorAccumulator eea, final SearchUseType searchUseType,
            final BasePK deletedBy) {
        var searchControl = Session.getModelController(SearchControl.class);

        searchControl.deleteSearchUseType(searchUseType, deletedBy);
    }

}
