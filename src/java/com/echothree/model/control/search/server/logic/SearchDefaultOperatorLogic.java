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

import com.echothree.control.user.search.common.spec.SearchDefaultOperatorUniversalSpec;
import com.echothree.model.control.core.common.ComponentVendors;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.model.control.core.common.exception.InvalidParameterCountException;
import com.echothree.model.control.core.server.logic.EntityInstanceLogic;
import com.echothree.model.control.search.common.exception.DuplicateSearchDefaultOperatorNameException;
import com.echothree.model.control.search.common.exception.UnknownDefaultSearchDefaultOperatorException;
import com.echothree.model.control.search.common.exception.UnknownSearchDefaultOperatorNameException;
import com.echothree.model.control.search.server.control.SearchControl;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.search.server.entity.SearchDefaultOperator;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.spi.CDI;

@ApplicationScoped
public class SearchDefaultOperatorLogic
        extends BaseLogic {

    protected SearchDefaultOperatorLogic() {
        super();
    }

    public static SearchDefaultOperatorLogic getInstance() {
        return CDI.current().select(SearchDefaultOperatorLogic.class).get();
    }

    public SearchDefaultOperator createSearchDefaultOperator(final ExecutionErrorAccumulator eea, final String searchDefaultOperatorName,
            final Boolean isDefault, final Integer sortOrder, final Language language, final String description,
            final BasePK createdBy) {
        var searchControl = Session.getModelController(SearchControl.class);
        var searchDefaultOperator = searchControl.getSearchDefaultOperatorByName(searchDefaultOperatorName);

        if(searchDefaultOperator == null) {
            searchDefaultOperator = searchControl.createSearchDefaultOperator(searchDefaultOperatorName, isDefault, sortOrder, createdBy);

            if(description != null) {
                searchControl.createSearchDefaultOperatorDescription(searchDefaultOperator, language, description, createdBy);
            }
        } else {
            handleExecutionError(DuplicateSearchDefaultOperatorNameException.class, eea, ExecutionErrors.DuplicateSearchDefaultOperatorName.name(), searchDefaultOperatorName);
        }

        return searchDefaultOperator;
    }

    public SearchDefaultOperator getSearchDefaultOperatorByName(final ExecutionErrorAccumulator eea, final String searchDefaultOperatorName,
            final EntityPermission entityPermission) {
        var searchControl = Session.getModelController(SearchControl.class);
        var searchDefaultOperator = searchControl.getSearchDefaultOperatorByName(searchDefaultOperatorName, entityPermission);

        if(searchDefaultOperator == null) {
            handleExecutionError(UnknownSearchDefaultOperatorNameException.class, eea, ExecutionErrors.UnknownSearchDefaultOperatorName.name(), searchDefaultOperatorName);
        }

        return searchDefaultOperator;
    }

    public SearchDefaultOperator getSearchDefaultOperatorByName(final ExecutionErrorAccumulator eea, final String searchDefaultOperatorName) {
        return getSearchDefaultOperatorByName(eea, searchDefaultOperatorName, EntityPermission.READ_ONLY);
    }

    public SearchDefaultOperator getSearchDefaultOperatorByNameForUpdate(final ExecutionErrorAccumulator eea, final String searchDefaultOperatorName) {
        return getSearchDefaultOperatorByName(eea, searchDefaultOperatorName, EntityPermission.READ_WRITE);
    }

    public SearchDefaultOperator getSearchDefaultOperatorByUniversalSpec(final ExecutionErrorAccumulator eea,
            final SearchDefaultOperatorUniversalSpec universalSpec, boolean allowDefault, final EntityPermission entityPermission) {
        SearchDefaultOperator searchDefaultOperator = null;
        var searchControl = Session.getModelController(SearchControl.class);
        var searchDefaultOperatorName = universalSpec.getSearchDefaultOperatorName();
        var parameterCount = (searchDefaultOperatorName == null ? 0 : 1) + EntityInstanceLogic.getInstance().countPossibleEntitySpecs(universalSpec);

        switch(parameterCount) {
            case 0:
                if(allowDefault) {
                    searchDefaultOperator = searchControl.getDefaultSearchDefaultOperator(entityPermission);

                    if(searchDefaultOperator == null) {
                        handleExecutionError(UnknownDefaultSearchDefaultOperatorException.class, eea, ExecutionErrors.UnknownDefaultSearchDefaultOperator.name());
                    }
                } else {
                    handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
                }
                break;
            case 1:
                if(searchDefaultOperatorName == null) {
                    var entityInstance = EntityInstanceLogic.getInstance().getEntityInstance(eea, universalSpec,
                            ComponentVendors.ECHO_THREE.name(), EntityTypes.SearchDefaultOperator.name());

                    if(!eea.hasExecutionErrors()) {
                        searchDefaultOperator = searchControl.getSearchDefaultOperatorByEntityInstance(entityInstance, entityPermission);
                    }
                } else {
                    searchDefaultOperator = getSearchDefaultOperatorByName(eea, searchDefaultOperatorName, entityPermission);
                }
                break;
            default:
                handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
                break;
        }

        return searchDefaultOperator;
    }

    public SearchDefaultOperator getSearchDefaultOperatorByUniversalSpec(final ExecutionErrorAccumulator eea,
            final SearchDefaultOperatorUniversalSpec universalSpec, boolean allowDefault) {
        return getSearchDefaultOperatorByUniversalSpec(eea, universalSpec, allowDefault, EntityPermission.READ_ONLY);
    }

    public SearchDefaultOperator getSearchDefaultOperatorByUniversalSpecForUpdate(final ExecutionErrorAccumulator eea,
            final SearchDefaultOperatorUniversalSpec universalSpec, boolean allowDefault) {
        return getSearchDefaultOperatorByUniversalSpec(eea, universalSpec, allowDefault, EntityPermission.READ_WRITE);
    }

    public void deleteSearchDefaultOperator(final ExecutionErrorAccumulator eea, final SearchDefaultOperator searchDefaultOperator,
            final BasePK deletedBy) {
        var searchControl = Session.getModelController(SearchControl.class);

        searchControl.deleteSearchDefaultOperator(searchDefaultOperator, deletedBy);
    }

}
