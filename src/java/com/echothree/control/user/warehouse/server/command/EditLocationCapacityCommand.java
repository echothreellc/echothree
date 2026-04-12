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

import com.echothree.control.user.warehouse.common.edit.LocationCapacityEdit;
import com.echothree.control.user.warehouse.common.edit.WarehouseEditFactory;
import com.echothree.control.user.warehouse.common.result.EditLocationCapacityResult;
import com.echothree.control.user.warehouse.common.result.WarehouseResultFactory;
import com.echothree.control.user.warehouse.common.spec.LocationCapacitySpec;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.control.uom.server.control.UomControl;
import com.echothree.model.control.warehouse.server.control.WarehouseControl;
import com.echothree.model.data.warehouse.server.entity.Location;
import com.echothree.model.data.warehouse.server.entity.LocationCapacity;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseAbstractEditCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class EditLocationCapacityCommand
        extends BaseAbstractEditCommand<LocationCapacitySpec, LocationCapacityEdit, EditLocationCapacityResult, LocationCapacity, Location> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), List.of(
                        new SecurityRoleDefinition(SecurityRoleGroups.LocationCapacity.name(), SecurityRoles.Edit.name())
                ))
        ));

        SPEC_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("WarehouseName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("LocationName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("UnitOfMeasureKindName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("UnitOfMeasureTypeName", FieldType.ENTITY_NAME, true, null, null)
                );
        
        EDIT_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("Capacity", FieldType.UNSIGNED_LONG, true, null, null)
                );
    }
    
    /** Creates a new instance of EditLocationCapacityCommand */
    public EditLocationCapacityCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }

    @Inject
    UomControl uomControl;

    @Inject
    WarehouseControl warehouseControl;

    @Override
    public EditLocationCapacityResult getResult() {
        return WarehouseResultFactory.getEditLocationCapacityResult();
    }

    @Override
    public LocationCapacityEdit getEdit() {
        return WarehouseEditFactory.getLocationCapacityEdit();
    }

    @Override
    public LocationCapacity getEntity(EditLocationCapacityResult result) {
        LocationCapacity locationCapacity = null;
        var warehouseName = spec.getWarehouseName();
        var warehouse = warehouseControl.getWarehouseByName(warehouseName);

        if(warehouse != null) {
            var locationName = spec.getLocationName();
            var location = warehouseControl.getLocationByName(warehouse.getParty(), locationName);

            if(location != null) {
                var unitOfMeasureKindName = spec.getUnitOfMeasureKindName();
                var unitOfMeasureKind = uomControl.getUnitOfMeasureKindByName(unitOfMeasureKindName);

                if(unitOfMeasureKind != null) {
                    var unitOfMeasureTypeName = spec.getUnitOfMeasureTypeName();
                    var unitOfMeasureType = uomControl.getUnitOfMeasureTypeByName(unitOfMeasureKind, unitOfMeasureTypeName);

                    if(unitOfMeasureType != null) {
                        locationCapacity = warehouseControl.getLocationCapacity(location, unitOfMeasureType, editModeToEntityPermission(editMode));

                        if(locationCapacity == null) {
                            addExecutionError(ExecutionErrors.UnknownLocationCapacity.name(),
                                    warehouse.getWarehouseName(), location.getLastDetail().getLocationName(),
                                    unitOfMeasureKind.getLastDetail().getUnitOfMeasureKindName(),
                                    unitOfMeasureType.getLastDetail().getUnitOfMeasureTypeName());
                        }
                    } else {
                        addExecutionError(ExecutionErrors.UnknownUnitOfMeasureTypeName.name(),
                                unitOfMeasureKind.getLastDetail().getUnitOfMeasureKindName(), unitOfMeasureTypeName);
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownUnitOfMeasureKindName.name(), unitOfMeasureKindName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownLocationName.name(), warehouse.getWarehouseName(), locationName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownWarehouseName.name(), warehouseName);
        }

        return locationCapacity;
    }

    @Override
    public Location getLockEntity(LocationCapacity locationCapacity) {
        return locationCapacity.getLocation();
    }

    @Override
    public void fillInResult(EditLocationCapacityResult result, LocationCapacity locationCapacity) {
        result.setLocationCapacity(warehouseControl.getLocationCapacityTransfer(getUserVisit(), locationCapacity));
    }

    @Override
    public void doLock(LocationCapacityEdit edit, LocationCapacity locationCapacity) {
        edit.setCapacity(locationCapacity.getCapacity().toString());
    }

    @Override
    public void doUpdate(LocationCapacity locationCapacity) {
        var locationCapacityValue = locationCapacity.getLocationCapacityValue().clone();

        locationCapacityValue.setCapacity(Long.valueOf(edit.getCapacity()));

        warehouseControl.updateLocationCapacityFromValue(locationCapacityValue, getPartyPK());
    }
    
}
