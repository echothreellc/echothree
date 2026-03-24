// --------------------------------------------------------------------------------
// Copyright 2002-2026 Echo Three, LLC
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

package com.echothree.control.user.filter.server.command;

import com.echothree.control.user.filter.common.form.GetFilterEntranceStepsForm;
import com.echothree.control.user.filter.common.result.FilterResultFactory;
import com.echothree.model.control.filter.server.control.FilterControl;
import com.echothree.model.control.filter.server.logic.FilterLogic;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.filter.server.entity.Filter;
import com.echothree.model.data.filter.server.entity.FilterEntranceStep;
import com.echothree.model.data.filter.server.factory.FilterEntranceStepFactory;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BasePaginatedMultipleEntitiesCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import java.util.Collection;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class GetFilterEntranceStepsCommand
        extends BasePaginatedMultipleEntitiesCommand<FilterEntranceStep, GetFilterEntranceStepsForm> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;

    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), List.of(
                        new SecurityRoleDefinition(SecurityRoleGroups.Filter.name(), SecurityRoles.FilterEntranceStep.name())
                ))
        ));

        FORM_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("FilterKindName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("FilterTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("FilterName", FieldType.ENTITY_NAME, true, null, null)
        );
    }
    
    /** Creates a new instance of GetFilterEntranceStepsCommand */
    public GetFilterEntranceStepsCommand() {
        super(COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, true);
    }
    
    @Inject
    FilterControl filterControl;

    @Inject
    FilterLogic filterLogic;

    private Filter filter;

    @Override
    protected void handleForm() {
        filter = filterLogic.getFilterByName(this, form.getFilterKindName(), form.getFilterTypeName(), form.getFilterName());
    }

    @Override
    protected Long getTotalEntities() {
        return hasExecutionErrors() ? null : filterControl.countFilterEntranceStepsByFilter(filter);
    }

    @Override
    protected Collection<FilterEntranceStep> getEntities() {
        return hasExecutionErrors() ? null : filterControl.getFilterEntranceStepsByFilter(filter);
    }

    @Override
    protected BaseResult getResult(Collection<FilterEntranceStep> entities) {
        var result = FilterResultFactory.getGetFilterEntranceStepsResult();

        if(entities != null) {
            var userVisit = getUserVisit();

            result.setFilter(filterControl.getFilterTransfer(userVisit, filter));

            if(session.hasLimit(FilterEntranceStepFactory.class)) {
                result.setFilterEntranceStepCount(getTotalEntities());
            }

            result.setFilterEntranceSteps(filterControl.getFilterEntranceStepTransfers(userVisit, entities));
        }

        return result;
    }
    
}
