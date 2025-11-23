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

package com.echothree.control.user.filter.server.command;

import com.echothree.control.user.filter.common.form.GetFilterAdjustmentTypesForm;
import com.echothree.control.user.filter.common.result.FilterResultFactory;
import com.echothree.model.control.filter.server.control.FilterControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.filter.server.entity.FilterAdjustmentType;
import com.echothree.model.data.filter.server.factory.FilterAdjustmentTypeFactory;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.server.control.BasePaginatedMultipleEntitiesCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import java.util.Collection;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

@RequestScoped
public class GetFilterAdjustmentTypesCommand
        extends BasePaginatedMultipleEntitiesCommand<FilterAdjustmentType, GetFilterAdjustmentTypesForm> {

    @Inject
    FilterControl filterControl;

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;

    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), List.of(
                        new SecurityRoleDefinition(SecurityRoleGroups.FilterAdjustmentType.name(), SecurityRoles.List.name())
                ))
        ));

        FORM_FIELD_DEFINITIONS = List.of();
    }

    /** Creates a new instance of GetFilterAdjustmentTypesCommand */
    public GetFilterAdjustmentTypesCommand() {
        super(COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, true);
    }

    @Override
    protected void handleForm() {
        // No fields to handle for this command.
    }

    @Override
    protected Long getTotalEntities() {
        return filterControl.countFilterAdjustmentTypes();
    }

    @Override
    protected Collection<FilterAdjustmentType> getEntities() {
        return filterControl.getFilterAdjustmentTypes();
    }

    @Override
    protected BaseResult getResult(Collection<FilterAdjustmentType> entities) {
        var result = FilterResultFactory.getGetFilterAdjustmentTypesResult();

        if(entities != null) {
            if(session.hasLimit(FilterAdjustmentTypeFactory.class)) {
                result.setFilterAdjustmentTypeCount(getTotalEntities());
            }

            result.setFilterAdjustmentTypes(filterControl.getFilterAdjustmentTypeTransfers(getUserVisit(), entities));
        }

        return result;
    }

}
