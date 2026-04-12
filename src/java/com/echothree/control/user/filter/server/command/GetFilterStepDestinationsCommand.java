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

import com.echothree.control.user.filter.common.form.GetFilterStepDestinationsForm;
import com.echothree.control.user.filter.common.result.FilterResultFactory;
import com.echothree.model.control.filter.server.control.FilterControl;
import com.echothree.model.control.filter.server.logic.FilterStepLogic;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.filter.server.entity.FilterStep;
import com.echothree.model.data.filter.server.entity.FilterStepDestination;
import com.echothree.model.data.filter.server.factory.FilterStepDestinationFactory;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.message.ExecutionErrors;
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
public class GetFilterStepDestinationsCommand
        extends BasePaginatedMultipleEntitiesCommand<FilterStepDestination, GetFilterStepDestinationsForm> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;

    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), List.of(
                        new SecurityRoleDefinition(SecurityRoleGroups.Filter.name(), SecurityRoles.FilterStepDestination.name())
                ))
        ));

        FORM_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("FilterKindName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("FilterTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("FilterName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("FromFilterStepName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("ToFilterStepName", FieldType.ENTITY_NAME, false, null, null)
        );
    }
    
    /** Creates a new instance of GetFilterStepDestinationsCommand */
    public GetFilterStepDestinationsCommand() {
        super(COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, true);
    }

    @Inject
    FilterControl filterControl;

    @Inject
    FilterStepLogic filterStepLogic;

    private FilterStep fromFilterStep;
    private FilterStep toFilterStep;

    @Override
    protected void handleForm() {
        var fromFilterStepName = form.getFromFilterStepName();
        var toFilterStepName = form.getToFilterStepName();

        if(fromFilterStepName != null && toFilterStepName == null) {
            fromFilterStep = filterStepLogic.getFilterStepByName(this, form.getFilterKindName(), form.getFilterTypeName(),
                    form.getFilterName(), fromFilterStepName);
        } else if(fromFilterStepName == null && toFilterStepName != null) {
            toFilterStep = filterStepLogic.getFilterStepByName(this, form.getFilterKindName(), form.getFilterTypeName(),
                    form.getFilterName(), toFilterStepName);
        } else {
            addExecutionError(ExecutionErrors.InvalidFilterStepSpecification.name());
        }
    }

    @Override
    protected Long getTotalEntities() {
        Long total = null;

        if(!hasExecutionErrors()) {
            if(fromFilterStep != null) {
                total = filterControl.countFilterStepDestinationsByFromFilterStep(fromFilterStep);
            } else if(toFilterStep != null) {
                total = filterControl.countFilterStepDestinationsByToFilterStep(toFilterStep);
            }
        }

        return total;
    }

    @Override
    protected Collection<FilterStepDestination> getEntities() {
        Collection<FilterStepDestination> entities = null;

        if(!hasExecutionErrors()) {
            if(fromFilterStep != null) {
                entities = filterControl.getFilterStepDestinationsByFromFilterStep(fromFilterStep);
            } else if(toFilterStep != null) {
                entities = filterControl.getFilterStepDestinationsByToFilterStep(toFilterStep);
            }
        }

        return entities;
    }

    @Override
    protected BaseResult getResult(Collection<FilterStepDestination> entities) {
        var result = FilterResultFactory.getGetFilterStepDestinationsResult();

        if(entities != null) {
            var userVisit = getUserVisit();

            if(fromFilterStep != null) {
                result.setFromFilterStep(filterControl.getFilterStepTransfer(userVisit, fromFilterStep));
            } else if(toFilterStep != null) {
                result.setToFilterStep(filterControl.getFilterStepTransfer(userVisit, toFilterStep));
            }

            if(session.hasLimit(FilterStepDestinationFactory.class)) {
                result.setFilterStepDestinationCount(getTotalEntities());
            }

            result.setFilterStepDestinations(filterControl.getFilterStepDestinationTransfers(userVisit, entities));
        }

        return result;
    }

}
