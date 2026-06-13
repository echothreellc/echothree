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

import com.echothree.control.user.warehouse.common.form.GetLocationCapacitiesForm;
import com.echothree.control.user.warehouse.common.result.WarehouseResultFactory;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.control.warehouse.server.control.WarehouseControl;
import com.echothree.model.control.warehouse.server.logic.WarehouseLogic;
import com.echothree.model.data.warehouse.server.entity.Location;
import com.echothree.model.data.warehouse.server.entity.LocationCapacity;
import com.echothree.model.data.warehouse.server.factory.LocationCapacityFactory;
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
public class GetLocationCapacitiesCommand
        extends BasePaginatedMultipleEntitiesCommand<LocationCapacity, GetLocationCapacitiesForm> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), List.of(
                        new SecurityRoleDefinition(SecurityRoleGroups.LocationCapacity.name(), SecurityRoles.List.name())
                ))
        ));

        FORM_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("WarehouseName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("LocationName", FieldType.ENTITY_NAME, true, null, null)
        );
    }
    
    @Inject
    WarehouseControl warehouseControl;

    @Inject
    WarehouseLogic warehouseLogic;

    /** Creates a new instance of GetLocationCapacitiesCommand */
    public GetLocationCapacitiesCommand() {
        super(COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, true);
    }

    Location location;

    @Override
    protected void handleForm() {
        var warehouseName = form.getWarehouseName();
        var warehouse = warehouseLogic.getWarehouseByName(this, warehouseName, null, null, false);

        if(!hasExecutionErrors()) {
            var locationName = form.getLocationName();

            location = warehouseControl.getLocationByName(warehouse.getParty(), locationName);

            if(location == null) {
                addExecutionError(ExecutionErrors.UnknownLocationName.name(), locationName);
            }
        }
    }

    @Override
    protected Long getTotalEntities() {
        return hasExecutionErrors() ? null : warehouseControl.countLocationCapacitiesByLocation(location);
    }

    @Override
    protected Collection<LocationCapacity> getEntities() {
        return hasExecutionErrors() ? null : warehouseControl.getLocationCapacitiesByLocation(location);
    }

    @Override
    protected BaseResult getResult(Collection<LocationCapacity> entities) {
        var result = WarehouseResultFactory.getGetLocationCapacitiesResult();

        if(entities != null) {
            var userVisit = getUserVisit();

            result.setLocation(warehouseControl.getLocationTransfer(userVisit, location));

            if(session.hasLimit(LocationCapacityFactory.class)) {
                result.setLocationCapacityCount(getTotalEntities());
            }

            result.setLocationCapacities(warehouseControl.getLocationCapacityTransfers(userVisit, entities));
        }

        return result;
    }

}
