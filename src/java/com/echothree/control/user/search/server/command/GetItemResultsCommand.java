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

import com.echothree.control.user.search.common.form.GetItemResultsForm;
import com.echothree.control.user.search.common.result.SearchResultFactory;
import com.echothree.model.control.item.server.control.ItemControl;
import com.echothree.model.control.search.common.SearchKinds;
import com.echothree.model.control.search.server.logic.SearchLogic;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseGetResultsCommand;
import com.echothree.util.server.persistence.Session;
import java.util.List;

public class GetItemResultsCommand
        extends BaseGetResultsCommand<GetItemResultsForm> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;

    static {
        FORM_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("SearchTypeName", FieldType.ENTITY_NAME, true, null, null)
        );
    }

    /** Creates a new instance of GetItemResultsCommand */
    public GetItemResultsCommand() {
        super(null, FORM_FIELD_DEFINITIONS, true);
    }
    
    @Override
    protected BaseResult execute() {
        var result = SearchResultFactory.getGetItemResultsResult();
        var searchTypeName = form.getSearchTypeName();
        var userVisit = getUserVisit();
        var userVisitSearch = SearchLogic.getInstance().getUserVisitSearchByName(this, userVisit,
                SearchKinds.ITEM.name(), searchTypeName);

        if(!hasExecutionErrors()) {
            var itemControl = Session.getModelController(ItemControl.class);

            if(session.hasLimit(com.echothree.model.data.search.server.factory.SearchResultFactory.class)) {
                result.setItemResultCount(SearchLogic.getInstance().countSearchResults(userVisitSearch.getSearch()));
            }

            result.setItemResults(itemControl.getItemResultTransfers(userVisitSearch));
        }

        return result;
    }
}
