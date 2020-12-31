// --------------------------------------------------------------------------------
// Copyright 2002-2021 Echo Three, LLC
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

import com.echothree.control.user.search.common.form.GetSearchSortOrdersForm;
import com.echothree.control.user.search.common.result.GetSearchSortOrdersResult;
import com.echothree.control.user.search.common.result.SearchResultFactory;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.search.server.control.SearchControl;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.search.server.entity.SearchKind;
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

public class GetSearchSortOrdersCommand
        extends BaseSimpleCommand<GetSearchSortOrdersForm> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                    new SecurityRoleDefinition(SecurityRoleGroups.SearchSortOrder.name(), SecurityRoles.List.name())
                    )))
                )));

        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("SearchKindName", FieldType.ENTITY_NAME, true, null, null)
                ));
    }
    
    /** Creates a new instance of GetSearchSortOrdersCommand */
    public GetSearchSortOrdersCommand(UserVisitPK userVisitPK, GetSearchSortOrdersForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, true);
    }
    
    @Override
    protected BaseResult execute() {
        var searchControl = Session.getModelController(SearchControl.class);
        GetSearchSortOrdersResult result = SearchResultFactory.getGetSearchSortOrdersResult();
        String searchKindName = form.getSearchKindName();
        SearchKind searchKind = searchControl.getSearchKindByName(searchKindName);
        
        if(searchKind != null) {
            result.setSearchKind(searchControl.getSearchKindTransfer(getUserVisit(), searchKind));
            result.setSearchSortOrders(searchControl.getSearchSortOrderTransfersBySearchKind(getUserVisit(), searchKind));
        } else {
            addExecutionError(ExecutionErrors.UnknownSearchKindName.name(), searchKindName);
        }
        
        return result;
    }
    
}
