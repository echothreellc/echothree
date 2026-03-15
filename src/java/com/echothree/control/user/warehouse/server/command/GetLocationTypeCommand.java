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

package com.echothree.control.user.warehouse.server.command;

import com.echothree.control.user.warehouse.common.form.GetLocationTypeForm;
import com.echothree.control.user.warehouse.common.result.WarehouseResultFactory;
import com.echothree.model.control.core.common.EventTypes;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.control.warehouse.server.control.WarehouseControl;
import com.echothree.model.control.warehouse.server.logic.WarehouseLogic;
import com.echothree.model.data.warehouse.server.entity.LocationType;
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
public class GetLocationTypeCommand
        extends BaseSingleEntityCommand<LocationType, GetLocationTypeForm> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), List.of(
                        new SecurityRoleDefinition(SecurityRoleGroups.LocationType.name(), SecurityRoles.Review.name())
                ))
        ));

        FORM_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("WarehouseName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("LocationTypeName", FieldType.ENTITY_NAME, true, null, null)
        );
    }
    
    /** Creates a new instance of GetLocationTypeCommand */
    public GetLocationTypeCommand() {
        super(COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, true);
    }

    @Inject
    WarehouseControl warehouseControl;

    @Inject
    WarehouseLogic warehouseLogic;

    @Override
    protected LocationType getEntity() {
        var warehouse = warehouseLogic.getWarehouseByName(this, form.getWarehouseName(), null, null, false);
        LocationType entity = null;

        if(!hasExecutionErrors()) {
            var warehouseParty = warehouse.getParty();
            var locationTypeName = form.getLocationTypeName();

            entity = warehouseControl.getLocationTypeByName(warehouseParty, locationTypeName);

            if(entity != null) {
                sendEvent(entity.getPrimaryKey(), EventTypes.READ, null, null, getPartyPK());
            } else {
                addExecutionError(ExecutionErrors.UnknownLocationTypeName.name(), warehouse.getWarehouseName(), locationTypeName);
            }
        }

        return entity;
    }

    @Override
    protected BaseResult getResult(LocationType entity) {
        var result = WarehouseResultFactory.getGetLocationTypeResult();

        if(entity != null) {
            result.setLocationType(warehouseControl.getLocationTypeTransfer(getUserVisit(), entity));
        }

        return result;
    }
    
}
