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

import com.echothree.control.user.filter.common.form.GetFilterStepDestinationForm;
import com.echothree.control.user.filter.common.result.FilterResultFactory;
import com.echothree.model.control.filter.server.control.FilterControl;
import com.echothree.model.control.filter.server.logic.FilterLogic;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.filter.server.entity.FilterStepDestination;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseSingleEntityCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class GetFilterStepDestinationCommand
        extends BaseSingleEntityCommand<FilterStepDestination, GetFilterStepDestinationForm> {

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
                new FieldDefinition("FromFilterStepName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("ToFilterStepName", FieldType.ENTITY_NAME, true, null, null)
        );
    }

    /** Creates a new instance of GetFilterStepDestinationCommand */
    public GetFilterStepDestinationCommand() {
        super(COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, false);
    }

    @Inject
    FilterControl filterControl;

    @Inject
    FilterLogic filterLogic;

    @Override
    protected FilterStepDestination getEntity() {
        var filterKindName = form.getFilterKindName();
        var filterTypeName = form.getFilterTypeName();
        var filterName = form.getFilterName();
        var filter = filterLogic.getFilterByName(this, filterKindName, filterTypeName, filterName);
        FilterStepDestination filterStepDestination = null;

        if(!hasExecutionErrors()) {
            var fromFilterStepName = form.getFromFilterStepName();
            var fromFilterStep = filterControl.getFilterStepByName(filter, fromFilterStepName);

            if(fromFilterStep != null) {
                var toFilterStepName = form.getToFilterStepName();
                var toFilterStep = filterControl.getFilterStepByName(filter, toFilterStepName);

                if(toFilterStep != null) {
                    filterStepDestination = filterControl.getFilterStepDestination(fromFilterStep, toFilterStep);

                    if(filterStepDestination == null) {
                        addExecutionError(ExecutionErrors.UnknownFilterStepDestination.name(),
                                filter.getLastDetail().getFilterType().getLastDetail().getFilterKind().getLastDetail().getFilterKindName(),
                                filter.getLastDetail().getFilterType().getLastDetail().getFilterTypeName(),
                                filter.getLastDetail().getFilterName(), fromFilterStep.getLastDetail().getFilterStepName(),
                                toFilterStep.getLastDetail().getFilterStepName());
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownToFilterStepName.name(), toFilterStepName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownFromFilterStepName.name(), fromFilterStepName);
            }
        }

        return filterStepDestination;
    }

    @Override
    protected BaseResult getResult(FilterStepDestination filterStepDestination) {
        var result = FilterResultFactory.getGetFilterStepDestinationResult();

        if(filterStepDestination != null) {
            result.setFilterStepDestination(filterControl.getFilterStepDestinationTransfer(getUserVisit(), filterStepDestination));
        }

        return result;
    }

}
