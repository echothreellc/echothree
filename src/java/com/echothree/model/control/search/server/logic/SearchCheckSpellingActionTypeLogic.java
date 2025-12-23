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

import com.echothree.control.user.search.common.spec.SearchCheckSpellingActionTypeUniversalSpec;
import com.echothree.model.control.core.common.ComponentVendors;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.model.control.core.common.exception.InvalidParameterCountException;
import com.echothree.model.control.core.server.logic.EntityInstanceLogic;
import com.echothree.model.control.search.common.exception.UnknownSearchCheckSpellingActionTypeNameException;
import com.echothree.model.control.search.common.transfer.SearchCheckSpellingActionTypeTransfer;
import com.echothree.model.control.search.server.control.SearchControl;
import com.echothree.model.data.search.server.entity.SearchCheckSpellingActionType;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.common.exception.BaseException;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.spi.CDI;

@ApplicationScoped
public class SearchCheckSpellingActionTypeLogic
        extends BaseLogic {

    protected SearchCheckSpellingActionTypeLogic() {
        super();
    }

    public static SearchCheckSpellingActionTypeLogic getInstance() {
        return CDI.current().select(SearchCheckSpellingActionTypeLogic.class).get();
    }

    public SearchCheckSpellingActionType getSearchCheckSpellingActionTypeByName(final Class<? extends BaseException> unknownException, final ExecutionErrors unknownExecutionError,
            final ExecutionErrorAccumulator eea, final String searchCheckSpellingActionTypeName, final EntityPermission entityPermission) {
        var searchControl = Session.getModelController(SearchControl.class);
        var searchCheckSpellingActionType = searchControl.getSearchCheckSpellingActionTypeByName(searchCheckSpellingActionTypeName, entityPermission);

        if(searchCheckSpellingActionType == null) {
            handleExecutionError(unknownException, eea, unknownExecutionError.name(), searchCheckSpellingActionTypeName);
        }

        return searchCheckSpellingActionType;
    }

    public SearchCheckSpellingActionType getSearchCheckSpellingActionTypeByName(final ExecutionErrorAccumulator eea, final String searchCheckSpellingActionTypeName,
            final EntityPermission entityPermission) {
        return getSearchCheckSpellingActionTypeByName(UnknownSearchCheckSpellingActionTypeNameException.class, ExecutionErrors.UnknownSearchCheckSpellingActionTypeName, eea,
                searchCheckSpellingActionTypeName, entityPermission);
    }

    public SearchCheckSpellingActionType getSearchCheckSpellingActionTypeByName(final ExecutionErrorAccumulator eea, final String searchCheckSpellingActionTypeName) {
        return getSearchCheckSpellingActionTypeByName(eea, searchCheckSpellingActionTypeName, EntityPermission.READ_ONLY);
    }

    public SearchCheckSpellingActionType getSearchCheckSpellingActionTypeByNameForUpdate(final ExecutionErrorAccumulator eea, final String searchCheckSpellingActionTypeName) {
        return getSearchCheckSpellingActionTypeByName(eea, searchCheckSpellingActionTypeName, EntityPermission.READ_WRITE);
    }

    public SearchCheckSpellingActionType getSearchCheckSpellingActionTypeByUniversalSpec(final ExecutionErrorAccumulator eea,
            final SearchCheckSpellingActionTypeUniversalSpec universalSpec, final EntityPermission entityPermission) {
        SearchCheckSpellingActionType searchCheckSpellingActionType = null;
        var searchControl = Session.getModelController(SearchControl.class);
        var searchCheckSpellingActionTypeName = universalSpec.getSearchCheckSpellingActionTypeName();
        var parameterCount = (searchCheckSpellingActionTypeName == null ? 0 : 1) + EntityInstanceLogic.getInstance().countPossibleEntitySpecs(universalSpec);

        switch(parameterCount) {
            case 1 -> {
                if(searchCheckSpellingActionTypeName == null) {
                    var entityInstance = EntityInstanceLogic.getInstance().getEntityInstance(eea, universalSpec,
                            ComponentVendors.ECHO_THREE.name(), EntityTypes.SearchCheckSpellingActionType.name());

                    if(!eea.hasExecutionErrors()) {
                        searchCheckSpellingActionType = searchControl.getSearchCheckSpellingActionTypeByEntityInstance(entityInstance, entityPermission);
                    }
                } else {
                    searchCheckSpellingActionType = getSearchCheckSpellingActionTypeByName(eea, searchCheckSpellingActionTypeName, entityPermission);
                }
            }
            default ->
                    handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
        }

        return searchCheckSpellingActionType;
    }

    public SearchCheckSpellingActionType getSearchCheckSpellingActionTypeByUniversalSpec(final ExecutionErrorAccumulator eea,
            final SearchCheckSpellingActionTypeUniversalSpec universalSpec) {
        return getSearchCheckSpellingActionTypeByUniversalSpec(eea, universalSpec, EntityPermission.READ_ONLY);
    }

    public SearchCheckSpellingActionType getSearchCheckSpellingActionTypeByUniversalSpecForUpdate(final ExecutionErrorAccumulator eea,
            final SearchCheckSpellingActionTypeUniversalSpec universalSpec) {
        return getSearchCheckSpellingActionTypeByUniversalSpec(eea, universalSpec, EntityPermission.READ_WRITE);
    }

    public SearchCheckSpellingActionTypeTransfer getSearchCheckSpellingActionTypeTransferByName(final ExecutionErrorAccumulator eea, final UserVisit userVisit, final String searchCheckSpellingActionTypeName) {
        var searchControl = Session.getModelController(SearchControl.class);

        return searchControl.getSearchCheckSpellingActionTypeTransfer(userVisit, getSearchCheckSpellingActionTypeByName(eea, searchCheckSpellingActionTypeName));
    }

}
