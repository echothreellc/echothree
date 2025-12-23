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

import com.echothree.control.user.search.common.spec.SearchKindUniversalSpec;
import com.echothree.model.control.core.common.ComponentVendors;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.model.control.core.common.exception.InvalidParameterCountException;
import com.echothree.model.control.core.server.logic.EntityInstanceLogic;
import com.echothree.model.control.search.common.exception.DuplicateSearchKindNameException;
import com.echothree.model.control.search.common.exception.UnknownDefaultSearchKindException;
import com.echothree.model.control.search.common.exception.UnknownSearchKindNameException;
import com.echothree.model.control.search.server.control.SearchControl;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.search.server.entity.SearchKind;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.spi.CDI;

@ApplicationScoped
public class SearchKindLogic
        extends BaseLogic {

    protected SearchKindLogic() {
        super();
    }

    public static SearchKindLogic getInstance() {
        return CDI.current().select(SearchKindLogic.class).get();
    }

    public SearchKind createSearchKind(final ExecutionErrorAccumulator eea, final String searchKindName,
            final Boolean isDefault, final Integer sortOrder, final Language language, final String description,
            final BasePK createdBy) {
        var searchControl = Session.getModelController(SearchControl.class);
        var searchKind = searchControl.getSearchKindByName(searchKindName);

        if(searchKind == null) {
            searchKind = searchControl.createSearchKind(searchKindName, isDefault, sortOrder, createdBy);

            if(description != null) {
                searchControl.createSearchKindDescription(searchKind, language, description, createdBy);
            }
        } else {
            handleExecutionError(DuplicateSearchKindNameException.class, eea, ExecutionErrors.DuplicateSearchKindName.name(), searchKindName);
        }

        return searchKind;
    }

    public SearchKind getSearchKindByName(final ExecutionErrorAccumulator eea, final String searchKindName,
            final EntityPermission entityPermission) {
        var searchControl = Session.getModelController(SearchControl.class);
        var searchKind = searchControl.getSearchKindByName(searchKindName, entityPermission);

        if(searchKind == null) {
            handleExecutionError(UnknownSearchKindNameException.class, eea, ExecutionErrors.UnknownSearchKindName.name(), searchKindName);
        }

        return searchKind;
    }

    public SearchKind getSearchKindByName(final ExecutionErrorAccumulator eea, final String searchKindName) {
        return getSearchKindByName(eea, searchKindName, EntityPermission.READ_ONLY);
    }

    public SearchKind getSearchKindByNameForUpdate(final ExecutionErrorAccumulator eea, final String searchKindName) {
        return getSearchKindByName(eea, searchKindName, EntityPermission.READ_WRITE);
    }

    public SearchKind getSearchKindByUniversalSpec(final ExecutionErrorAccumulator eea,
            final SearchKindUniversalSpec universalSpec, boolean allowDefault, final EntityPermission entityPermission) {
        SearchKind searchKind = null;
        var searchControl = Session.getModelController(SearchControl.class);
        var searchKindName = universalSpec.getSearchKindName();
        var parameterCount = (searchKindName == null ? 0 : 1) + EntityInstanceLogic.getInstance().countPossibleEntitySpecs(universalSpec);

        switch(parameterCount) {
            case 0 -> {
                if(allowDefault) {
                    searchKind = searchControl.getDefaultSearchKind(entityPermission);

                    if(searchKind == null) {
                        handleExecutionError(UnknownDefaultSearchKindException.class, eea, ExecutionErrors.UnknownDefaultSearchKind.name());
                    }
                } else {
                    handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
                }
            }
            case 1 -> {
                if(searchKindName == null) {
                    var entityInstance = EntityInstanceLogic.getInstance().getEntityInstance(eea, universalSpec,
                            ComponentVendors.ECHO_THREE.name(), EntityTypes.SearchKind.name());

                    if(!eea.hasExecutionErrors()) {
                        searchKind = searchControl.getSearchKindByEntityInstance(entityInstance, entityPermission);
                    }
                } else {
                    searchKind = getSearchKindByName(eea, searchKindName, entityPermission);
                }
            }
            default ->
                    handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
        }

        return searchKind;
    }

    public SearchKind getSearchKindByUniversalSpec(final ExecutionErrorAccumulator eea,
            final SearchKindUniversalSpec universalSpec, boolean allowDefault) {
        return getSearchKindByUniversalSpec(eea, universalSpec, allowDefault, EntityPermission.READ_ONLY);
    }

    public SearchKind getSearchKindByUniversalSpecForUpdate(final ExecutionErrorAccumulator eea,
            final SearchKindUniversalSpec universalSpec, boolean allowDefault) {
        return getSearchKindByUniversalSpec(eea, universalSpec, allowDefault, EntityPermission.READ_WRITE);
    }

    public void deleteSearchKind(final ExecutionErrorAccumulator eea, final SearchKind searchKind,
            final BasePK deletedBy) {
        var searchControl = Session.getModelController(SearchControl.class);

        searchControl.deleteSearchKind(searchKind, deletedBy);
    }

}
