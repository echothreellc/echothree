// --------------------------------------------------------------------------------
// Copyright 2002-2018 Echo Three, LLC
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

import com.echothree.control.user.search.common.form.CreateSearchSortOrderForm;
import com.echothree.model.control.party.common.PartyConstants;
import com.echothree.model.control.search.server.SearchControl;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.party.common.pk.PartyPK;
import com.echothree.model.data.search.server.entity.SearchKind;
import com.echothree.model.data.search.server.entity.SearchSortOrder;
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

public class CreateSearchSortOrderCommand
        extends BaseSimpleCommand<CreateSearchSortOrderForm> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyConstants.PartyType_UTILITY, null),
                new PartyTypeDefinition(PartyConstants.PartyType_EMPLOYEE, Collections.unmodifiableList(Arrays.asList(
                    new SecurityRoleDefinition(SecurityRoleGroups.SearchSortOrder.name(), SecurityRoles.Create.name())
                    )))
                )));

        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("SearchKindName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("SearchSortOrderName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 80L)
                ));
    }
    
    /** Creates a new instance of CreateSearchSortOrderCommand */
    public CreateSearchSortOrderCommand(UserVisitPK userVisitPK, CreateSearchSortOrderForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        SearchControl searchControl = (SearchControl)Session.getModelController(SearchControl.class);
        String searchKindName = form.getSearchKindName();
        SearchKind searchKind = searchControl.getSearchKindByName(searchKindName);
        
        if(searchKind != null) {
            String searchSortOrderName = form.getSearchSortOrderName();
            SearchSortOrder searchSortOrder = searchControl.getSearchSortOrderByName(searchKind, searchSortOrderName);
            
            if(searchSortOrder == null) {
                PartyPK partyPK = getPartyPK();
                Boolean isDefault = Boolean.valueOf(form.getIsDefault());
                Integer sortOrder = Integer.valueOf(form.getSortOrder());
                String description = form.getDescription();

                searchSortOrder = searchControl.createSearchSortOrder(searchKind, searchSortOrderName, isDefault, sortOrder, partyPK);

                if(description != null) {
                    searchControl.createSearchSortOrderDescription(searchSortOrder, getPreferredLanguage(), description, partyPK);
                }
            } else {
                addExecutionError(ExecutionErrors.DuplicateSearchSortOrderName.name(), searchKindName, searchSortOrderName);
            }
        } else {
            addExecutionError(ExecutionErrors.DuplicateSearchKindName.name(), searchKindName);
        }
        
        return null;
    }
    
}
