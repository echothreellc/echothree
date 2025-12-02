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

import com.echothree.control.user.search.common.form.GetSearchCheckSpellingActionTypeForm;
import com.echothree.control.user.search.common.result.SearchResultFactory;
import com.echothree.model.control.core.common.EventTypes;
import com.echothree.model.control.search.server.control.SearchControl;
import com.echothree.model.control.search.server.logic.SearchCheckSpellingActionTypeLogic;
import com.echothree.model.data.search.server.entity.SearchCheckSpellingActionType;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseSingleEntityCommand;
import com.echothree.util.server.persistence.Session;
import java.util.List;
import javax.enterprise.context.Dependent;

@Dependent
public class GetSearchCheckSpellingActionTypeCommand
        extends BaseSingleEntityCommand<SearchCheckSpellingActionType, GetSearchCheckSpellingActionTypeForm> {

    // No COMMAND_SECURITY_DEFINITION, anyone may execute this command.
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;

    static {
        FORM_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("SearchCheckSpellingActionTypeName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("EntityRef", FieldType.ENTITY_REF, false, null, null),
                new FieldDefinition("Uuid", FieldType.UUID, false, null, null)
        );
    }

    /** Creates a new instance of GetSearchCheckSpellingActionTypeCommand */
    public GetSearchCheckSpellingActionTypeCommand() {
        super(null, FORM_FIELD_DEFINITIONS, true);
    }

    @Override
    protected SearchCheckSpellingActionType getEntity() {
        var searchCheckSpellingActionType = SearchCheckSpellingActionTypeLogic.getInstance().getSearchCheckSpellingActionTypeByUniversalSpec(this, form);

        if(searchCheckSpellingActionType != null) {
            sendEvent(searchCheckSpellingActionType.getPrimaryKey(), EventTypes.READ, null, null, getPartyPK());
        }

        return searchCheckSpellingActionType;
    }

    @Override
    protected BaseResult getResult(SearchCheckSpellingActionType searchCheckSpellingActionType) {
        var result = SearchResultFactory.getGetSearchCheckSpellingActionTypeResult();

        if(searchCheckSpellingActionType != null) {
            var searchControl = Session.getModelController(SearchControl.class);

            result.setSearchCheckSpellingActionType(searchControl.getSearchCheckSpellingActionTypeTransfer(getUserVisit(), searchCheckSpellingActionType));
        }

        return result;
    }

}
