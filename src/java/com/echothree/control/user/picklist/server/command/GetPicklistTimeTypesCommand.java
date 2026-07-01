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

package com.echothree.control.user.picklist.server.command;

import com.echothree.control.user.picklist.common.form.GetPicklistTimeTypesForm;
import com.echothree.control.user.picklist.common.result.PicklistResultFactory;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.picklist.server.control.PicklistControl;
import com.echothree.model.control.picklist.server.logic.PicklistTypeLogic;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.picklist.server.entity.PicklistTimeType;
import com.echothree.model.data.picklist.server.entity.PicklistType;
import com.echothree.model.data.picklist.server.factory.PicklistTimeTypeFactory;
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
public class GetPicklistTimeTypesCommand
        extends BasePaginatedMultipleEntitiesCommand<PicklistTimeType, GetPicklistTimeTypesForm> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;

    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), List.of(
                        new SecurityRoleDefinition(SecurityRoleGroups.PicklistTimeType.name(), SecurityRoles.List.name())
                ))
        ));

        FORM_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("PicklistTypeName", FieldType.ENTITY_NAME, true, null, null)
        );
    }

    @Inject
    PicklistControl picklistControl;

    @Inject
    PicklistTypeLogic picklistTypeLogic;

    /** Creates a new instance of GetPicklistTimeTypesCommand */
    public GetPicklistTimeTypesCommand() {
        super(COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, true);
    }

    PicklistType picklistType;

    @Override
    protected void handleForm() {
        picklistType = picklistTypeLogic.getPicklistTypeByName(this, form.getPicklistTypeName());
    }

    @Override
    protected Long getTotalEntities() {
        return hasExecutionErrors() ? null : picklistControl.countPicklistTimeTypesByPicklistType(picklistType);
    }

    @Override
    protected Collection<PicklistTimeType> getEntities() {
        return hasExecutionErrors() ? null : picklistControl.getPicklistTimeTypes(picklistType);
    }

    @Override
    protected BaseResult getResult(Collection<PicklistTimeType> entities) {
        var result = PicklistResultFactory.getGetPicklistTimeTypesResult();

        if(entities != null) {
            var userVisit = getUserVisit();

            result.setPicklistType(picklistControl.getPicklistTypeTransfer(userVisit, picklistType));

            if(session.hasLimit(PicklistTimeTypeFactory.class)) {
                result.setPicklistTimeTypeCount(getTotalEntities());
            }

            result.setPicklistTimeTypes(picklistControl.getPicklistTimeTypeTransfers(userVisit, entities));
        }

        return result;
    }

}
