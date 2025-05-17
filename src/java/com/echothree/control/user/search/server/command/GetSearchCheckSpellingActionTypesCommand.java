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

package com.echothree.control.user.search.server.command;

import com.echothree.control.user.search.common.form.GetSearchCheckSpellingActionTypesForm;
import com.echothree.control.user.search.common.result.SearchResultFactory;
import com.echothree.model.control.search.server.control.SearchControl;
import com.echothree.model.data.search.server.entity.SearchCheckSpellingActionType;
import com.echothree.model.data.search.server.factory.SearchCheckSpellingActionTypeFactory;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.server.control.BaseMultipleEntitiesCommand;
import com.echothree.util.server.persistence.Session;
import java.util.Collection;
import java.util.List;

public class GetSearchCheckSpellingActionTypesCommand
        extends BaseMultipleEntitiesCommand<SearchCheckSpellingActionType, GetSearchCheckSpellingActionTypesForm> {

    // No COMMAND_SECURITY_DEFINITION, anyone may execute this command.
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;

    static {
        FORM_FIELD_DEFINITIONS = List.of();
    }

    /** Creates a new instance of GetSearchCheckSpellingActionTypesCommand */
    public GetSearchCheckSpellingActionTypesCommand() {
        super(null, FORM_FIELD_DEFINITIONS, true);
    }

    @Override
    protected Collection<SearchCheckSpellingActionType> getEntities() {
        var searchControl = Session.getModelController(SearchControl.class);

        return searchControl.getSearchCheckSpellingActionTypes();
    }

    @Override
    protected BaseResult getResult(Collection<SearchCheckSpellingActionType> entities) {
        var result = SearchResultFactory.getGetSearchCheckSpellingActionTypesResult();

        if(entities != null) {
            var searchControl = Session.getModelController(SearchControl.class);
            var userVisit = getUserVisit();

            if(session.hasLimit(SearchCheckSpellingActionTypeFactory.class)) {
                result.setSearchCheckSpellingActionTypeCount(searchControl.countSearchCheckSpellingActionTypes());
            }

            result.setSearchCheckSpellingActionTypes(searchControl.getSearchCheckSpellingActionTypeTransfers(userVisit, entities));
        }

        return result;
    }

}
