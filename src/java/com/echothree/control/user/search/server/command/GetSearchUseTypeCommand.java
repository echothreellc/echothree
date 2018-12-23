// --------------------------------------------------------------------------------
// Copyright 2002-2019 Echo Three, LLC
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

import com.echothree.control.user.search.common.form.GetSearchUseTypeForm;
import com.echothree.control.user.search.common.result.GetSearchUseTypeResult;
import com.echothree.control.user.search.common.result.SearchResultFactory;
import com.echothree.model.control.core.common.ComponentVendors;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.model.control.core.common.EventTypes;
import com.echothree.model.control.core.server.logic.EntityInstanceLogic;
import com.echothree.model.control.party.common.PartyConstants;
import com.echothree.model.control.search.server.SearchControl;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.search.server.entity.SearchUseType;
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

public class GetSearchUseTypeCommand
        extends BaseSimpleCommand<GetSearchUseTypeForm> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyConstants.PartyType_UTILITY, null),
                new PartyTypeDefinition(PartyConstants.PartyType_EMPLOYEE, Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.SearchUseType.name(), SecurityRoles.Review.name())
                        )))
                )));
        
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("SearchUseTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("EntityRef", FieldType.ENTITY_REF, false, null, null),
                new FieldDefinition("Key", FieldType.KEY, false, null, null),
                new FieldDefinition("Guid", FieldType.GUID, false, null, null),
                new FieldDefinition("Ulid", FieldType.ULID, false, null, null)
                ));
    }
    
    /** Creates a new instance of GetSearchUseTypeCommand */
    public GetSearchUseTypeCommand(UserVisitPK userVisitPK, GetSearchUseTypeForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, true);
    }
    
    @Override
    protected BaseResult execute() {
        GetSearchUseTypeResult result = SearchResultFactory.getGetSearchUseTypeResult();
        String searchUseTypeName = form.getSearchUseTypeName();
        int parameterCount = (searchUseTypeName == null ? 0 : 1) + EntityInstanceLogic.getInstance().countPossibleEntitySpecs(form);

        if(parameterCount == 1) {
            SearchControl searchControl = (SearchControl)Session.getModelController(SearchControl.class);
            SearchUseType searchUseType = null;

            if(searchUseTypeName == null) {
                EntityInstance entityInstance = EntityInstanceLogic.getInstance().getEntityInstance(this, form, ComponentVendors.ECHOTHREE.name(),
                        EntityTypes.SearchUseType.name());
                
                if(!hasExecutionErrors()) {
                    searchUseType = searchControl.getSearchUseTypeByEntityInstance(entityInstance);
                }
            } else {
                searchUseType = searchControl.getSearchUseTypeByName(searchUseTypeName);

                if(searchUseType == null) {
                    addExecutionError(ExecutionErrors.UnknownSearchUseTypeName.name(), searchUseTypeName);
                }
            }

            if(!hasExecutionErrors()) {
                result.setSearchUseType(searchControl.getSearchUseTypeTransfer(getUserVisit(), searchUseType));
                sendEventUsingNames(searchUseType.getPrimaryKey(), EventTypes.READ.name(), null, null, getPartyPK());
            }
        } else {
            addExecutionError(ExecutionErrors.InvalidParameterCount.name());
        }
        
        return result;
    }
    
}
