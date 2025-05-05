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

import com.echothree.control.user.search.common.form.GetSearchSortDirectionForm;
import com.echothree.control.user.search.common.result.SearchResultFactory;
import com.echothree.model.control.core.common.EventTypes;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.search.server.control.SearchControl;
import com.echothree.model.control.search.server.logic.SearchSortDirectionLogic;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.search.server.entity.SearchSortDirection;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseSingleEntityCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
import java.util.List;

public class GetSearchSortDirectionCommand
        extends BaseSingleEntityCommand<SearchSortDirection, GetSearchSortDirectionForm> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;

    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), List.of(
                        new SecurityRoleDefinition(SecurityRoleGroups.SearchSortDirection.name(), SecurityRoles.Review.name())
                ))
        ));

        FORM_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("SearchSortDirectionName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("EntityRef", FieldType.ENTITY_REF, false, null, null),
                new FieldDefinition("Uuid", FieldType.UUID, false, null, null)
        );
    }

    /** Creates a new instance of GetSearchSortDirectionCommand */
    public GetSearchSortDirectionCommand() {
        super(COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, true);
    }

    @Override
    protected SearchSortDirection getEntity() {
        var searchSortDirection = SearchSortDirectionLogic.getInstance().getSearchSortDirectionByUniversalSpec(this, form, true);

        if(searchSortDirection != null) {
            sendEvent(searchSortDirection.getPrimaryKey(), EventTypes.READ, null, null, getPartyPK());
        }

        return searchSortDirection;
    }

    @Override
    protected BaseResult getResult(SearchSortDirection searchSortDirection) {
        var searchControl = Session.getModelController(SearchControl.class);
        var result = SearchResultFactory.getGetSearchSortDirectionResult();

        if(searchSortDirection != null) {
            result.setSearchSortDirection(searchControl.getSearchSortDirectionTransfer(getUserVisit(), searchSortDirection));
        }

        return result;
    }

}
