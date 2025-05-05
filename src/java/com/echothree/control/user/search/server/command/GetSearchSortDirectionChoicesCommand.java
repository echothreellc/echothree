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

import com.echothree.control.user.search.common.form.GetSearchSortDirectionChoicesForm;
import com.echothree.control.user.search.common.result.SearchResultFactory;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.search.server.control.SearchControl;
import com.echothree.model.control.search.server.logic.SearchLogic;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class GetSearchSortDirectionChoicesCommand
        extends BaseSimpleCommand<GetSearchSortDirectionChoicesForm> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.SearchSortDirection.name(), SecurityRoles.Choices.name())
                        )))
                )));
        
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("SearchKindName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("SearchTypeName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("DefaultSearchSortDirectionChoice", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("AllowNullChoice", FieldType.BOOLEAN, true, null, null)
                ));
    }
    
    /** Creates a new instance of GetSearchSortDirectionChoicesCommand */
    public GetSearchSortDirectionChoicesCommand() {
        super(COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        var result = SearchResultFactory.getGetSearchSortDirectionChoicesResult();
        var searchKindName = form.getSearchKindName();
        var searchTypeName = form.getSearchTypeName();
        var parameterCount = (searchKindName == null ? 0 : 1) + (searchTypeName == null ? 0 : 1);

        if(parameterCount == 0 || parameterCount == 2) {
            var defaultSearchSortDirectionChoice = form.getDefaultSearchSortDirectionChoice();
            var party = getParty();
            var searchType = defaultSearchSortDirectionChoice == null && party != null ? SearchLogic.getInstance().getSearchTypeByName(this, searchKindName, searchTypeName) : null;

            if(!hasExecutionErrors()) {
                var searchControl = Session.getModelController(SearchControl.class);
                var allowNullChoice = Boolean.parseBoolean(form.getAllowNullChoice());

                if(searchType != null) {
                    var partySearchTypePreference = searchControl.getPartySearchTypePreference(party, searchType);

                    if(partySearchTypePreference != null) {
                        var searchSortDirection = partySearchTypePreference.getLastDetail().getSearchSortDirection();

                        if(searchSortDirection != null) {
                            defaultSearchSortDirectionChoice = searchSortDirection.getLastDetail().getSearchSortDirectionName();
                        }
                    }
                }

                result.setSearchSortDirectionChoices(searchControl.getSearchSortDirectionChoices(defaultSearchSortDirectionChoice, getPreferredLanguage(), allowNullChoice));
            }
        } else {
            addExecutionError(ExecutionErrors.InvalidParameterCount.name());
        }

        return result;
     }
    
}
