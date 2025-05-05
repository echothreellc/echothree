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

import com.echothree.control.user.search.common.form.GetEntityAttributeGroupResultsForm;
import com.echothree.control.user.search.common.result.SearchResultFactory;
import com.echothree.model.control.core.server.control.EntityAttributeGroupControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.search.common.SearchKinds;
import com.echothree.model.control.search.server.control.SearchControl;
import com.echothree.model.control.search.server.logic.SearchLogic;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
import java.util.List;

public class GetEntityAttributeGroupResultsCommand
        extends BaseSimpleCommand<GetEntityAttributeGroupResultsForm> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;

    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), List.of(
                        new SecurityRoleDefinition(SecurityRoleGroups.EntityAttributeGroup.name(), SecurityRoles.Search.name())
                ))
        ));

        FORM_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("SearchTypeName", FieldType.ENTITY_NAME, true, null, null)
        );
    }

    /** Creates a new instance of GetEntityAttributeGroupResultsCommand */
    public GetEntityAttributeGroupResultsCommand() {
        super(COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, true);
    }
    
    @Override
    protected BaseResult execute() {
        var result = SearchResultFactory.getGetEntityAttributeGroupResultsResult();
        var searchControl = Session.getModelController(SearchControl.class);
        var searchKind = searchControl.getSearchKindByName(SearchKinds.ENTITY_ATTRIBUTE_GROUP.name());
        
        if(searchKind != null) {
            var searchTypeName = form.getSearchTypeName();
            var searchType = searchControl.getSearchTypeByName(searchKind, searchTypeName);
            
            if(searchType != null) {
                var userVisit = getUserVisit();
                var userVisitSearch = searchControl.getUserVisitSearch(userVisit, searchType);
                
                if(userVisitSearch != null) {
                    var entityListItemControl = Session.getModelController(EntityAttributeGroupControl.class);

                    if(session.hasLimit(com.echothree.model.data.search.server.factory.SearchResultFactory.class)) {
                        result.setEntityAttributeGroupResultCount(SearchLogic.getInstance().countSearchResults(userVisitSearch.getSearch()));
                    }

                    result.setEntityAttributeGroupResults(entityListItemControl.getEntityAttributeGroupResultTransfers(userVisit, userVisitSearch));
                } else {
                    addExecutionError(ExecutionErrors.UnknownUserVisitSearch.name());
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownSearchTypeName.name(), SearchKinds.ENTITY_ATTRIBUTE_GROUP.name(), searchTypeName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownSearchKindName.name(), SearchKinds.ENTITY_ATTRIBUTE_GROUP.name());
        }
        
        return result;
    }
}
