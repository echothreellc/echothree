// --------------------------------------------------------------------------------
// Copyright 2002-2024 Echo Three, LLC
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

import com.echothree.control.user.search.common.form.GetSearchSortOrderChoicesForm;
import com.echothree.control.user.search.common.result.GetSearchSortOrderChoicesResult;
import com.echothree.control.user.search.common.result.SearchResultFactory;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.search.server.control.SearchControl;
import com.echothree.model.control.search.server.logic.SearchLogic;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.model.data.search.server.entity.PartySearchTypePreference;
import com.echothree.model.data.search.server.entity.SearchKind;
import com.echothree.model.data.search.server.entity.SearchSortOrder;
import com.echothree.model.data.search.server.entity.SearchType;
import com.echothree.model.data.user.common.pk.UserVisitPK;
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

public class GetSearchSortOrderChoicesCommand
        extends BaseSimpleCommand<GetSearchSortOrderChoicesForm> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                    new SecurityRoleDefinition(SecurityRoleGroups.SearchSortOrder.name(), SecurityRoles.Choices.name())
                    )))
                )));

        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("SearchKindName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("SearchTypeName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("DefaultSearchSortOrderChoice", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("AllowNullChoice", FieldType.BOOLEAN, true, null, null)
                ));
    }
    
    /** Creates a new instance of GetSearchSortOrderChoicesCommand */
    public GetSearchSortOrderChoicesCommand(UserVisitPK userVisitPK, GetSearchSortOrderChoicesForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        SearchLogic searchLogic = SearchLogic.getInstance();
        GetSearchSortOrderChoicesResult result = SearchResultFactory.getGetSearchSortOrderChoicesResult();
        String searchKindName = form.getSearchKindName();
        SearchKind searchKind = searchLogic.getSearchKindByName(this, searchKindName);
        
        if(!hasExecutionErrors()) {
            String defaultSearchSortOrderChoice = form.getDefaultSearchSortOrderChoice();
            Party party = getParty();
            String searchTypeName = form.getSearchTypeName();
            SearchType searchType = searchTypeName != null && defaultSearchSortOrderChoice == null && party != null ? SearchLogic.getInstance().getSearchTypeByName(this, searchKind, searchTypeName) : null;
            
            if(!hasExecutionErrors()) {
                var searchControl = Session.getModelController(SearchControl.class);
                boolean allowNullChoice = Boolean.parseBoolean(form.getAllowNullChoice());

                if(searchType != null) {
                    PartySearchTypePreference partySearchTypePreference = searchControl.getPartySearchTypePreference(party, searchType);
                    
                    if(partySearchTypePreference != null) {
                        SearchSortOrder searchSortOrder = partySearchTypePreference.getLastDetail().getSearchSortOrder();
                        
                        if(searchSortOrder != null) {
                            defaultSearchSortOrderChoice = searchSortOrder.getLastDetail().getSearchSortOrderName();
                        }
                    }
                }
                
                result.setSearchSortOrderChoices(searchControl.getSearchSortOrderChoices(defaultSearchSortOrderChoice, getPreferredLanguage(), allowNullChoice,
                        searchKind));
            }
        }
        
        return result;
    }
    
}
