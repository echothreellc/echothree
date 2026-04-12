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

import com.echothree.control.user.filter.common.form.GetFilterAdjustmentsForm;
import com.echothree.control.user.filter.common.result.FilterResultFactory;
import com.echothree.model.control.filter.server.control.FilterControl;
import com.echothree.model.control.filter.server.logic.FilterKindLogic;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.filter.server.entity.FilterAdjustment;
import com.echothree.model.data.filter.server.entity.FilterKind;
import com.echothree.model.data.filter.server.factory.FilterAdjustmentFactory;
import com.echothree.model.data.user.common.pk.UserVisitPK;
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
public class GetFilterAdjustmentsCommand
        extends BasePaginatedMultipleEntitiesCommand<FilterAdjustment, GetFilterAdjustmentsForm> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;

    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), List.of(
                        new SecurityRoleDefinition(SecurityRoleGroups.FilterAdjustment.name(), SecurityRoles.List.name())
                ))
        ));

        FORM_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("FilterKindName", FieldType.ENTITY_NAME, true, null, null)
        );
    }
    
    /** Creates a new instance of GetFilterAdjustmentsCommand */
    public GetFilterAdjustmentsCommand() {
        super(COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, true);
    }

    @Inject
    FilterControl filterControl;

    @Inject
    FilterKindLogic filterKindLogic;

    FilterKind filterKind;

    @Override
    protected void handleForm() {
        filterKind = filterKindLogic.getFilterKindByName(this, form.getFilterKindName());
    }

    @Override
    protected Long getTotalEntities() {
        return hasExecutionErrors() ? null : filterControl.countFilterAdjustmentsByFilterKind(filterKind);
    }

    @Override
    protected Collection<FilterAdjustment> getEntities() {
        return hasExecutionErrors() ? null : filterControl.getFilterAdjustmentsByFilterKind(filterKind);
    }

    @Override
    protected BaseResult getResult(Collection<FilterAdjustment> entities) {
        var result = FilterResultFactory.getGetFilterAdjustmentsResult();

        if(entities != null) {
            result.setFilterKind(filterControl.getFilterKindTransfer(getUserVisit(), filterKind));

            if(session.hasLimit(FilterAdjustmentFactory.class)) {
                result.setFilterAdjustmentCount(getTotalEntities());
            }

            result.setFilterAdjustments(filterControl.getFilterAdjustmentTransfers(getUserVisit(), entities));
        }

        return result;
    }

}
