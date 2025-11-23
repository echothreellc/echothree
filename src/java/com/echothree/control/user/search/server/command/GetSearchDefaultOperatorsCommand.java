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

import com.echothree.control.user.search.common.form.GetSearchDefaultOperatorsForm;
import com.echothree.control.user.search.common.result.SearchResultFactory;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.search.server.control.SearchControl;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.search.server.entity.SearchDefaultOperator;
import com.echothree.model.data.search.server.factory.SearchDefaultOperatorFactory;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.server.control.BasePaginatedMultipleEntitiesCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
import java.util.Collection;
import java.util.List;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class GetSearchDefaultOperatorsCommand
        extends BasePaginatedMultipleEntitiesCommand<SearchDefaultOperator, GetSearchDefaultOperatorsForm> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;

    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), List.of(
                        new SecurityRoleDefinition(SecurityRoleGroups.SearchDefaultOperator.name(), SecurityRoles.List.name())
                ))
        ));

        FORM_FIELD_DEFINITIONS = List.of();
    }

    /** Creates a new instance of GetSearchDefaultOperatorsCommand */
    public GetSearchDefaultOperatorsCommand() {
        super(COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, true);
    }

    @Override
    protected void handleForm() {
        // No form fields.
    }

    @Override
    protected Long getTotalEntities() {
        var searchControl = Session.getModelController(SearchControl.class);

        return searchControl.countSearchDefaultOperators();
    }

    @Override
    protected Collection<SearchDefaultOperator> getEntities() {
        var searchControl = Session.getModelController(SearchControl.class);

        return searchControl.getSearchDefaultOperators();
    }

    @Override
    protected BaseResult getResult(Collection<SearchDefaultOperator> entities) {
        var result = SearchResultFactory.getGetSearchDefaultOperatorsResult();

        if(entities != null) {
            var searchControl = Session.getModelController(SearchControl.class);

            if(session.hasLimit(SearchDefaultOperatorFactory.class)) {
                result.setSearchDefaultOperatorCount(getTotalEntities());
            }

            result.setSearchDefaultOperators(searchControl.getSearchDefaultOperatorTransfers(getUserVisit(), entities));
        }

        return result;
    }

}
