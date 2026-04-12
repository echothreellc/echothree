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

import com.echothree.control.user.warehouse.common.edit.LocationTypeEdit;
import com.echothree.control.user.warehouse.common.edit.WarehouseEditFactory;
import com.echothree.control.user.warehouse.common.result.EditLocationTypeResult;
import com.echothree.control.user.warehouse.common.result.WarehouseResultFactory;
import com.echothree.control.user.warehouse.common.spec.LocationTypeSpec;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.control.warehouse.server.control.WarehouseControl;
import com.echothree.model.data.warehouse.server.entity.LocationType;
import com.echothree.model.data.warehouse.server.entity.Warehouse;
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
public class EditLocationTypeCommand
        extends BaseAbstractEditCommand<LocationTypeSpec, LocationTypeEdit, EditLocationTypeResult, LocationType, LocationType> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), List.of(
                        new SecurityRoleDefinition(SecurityRoleGroups.LocationType.name(), SecurityRoles.Edit.name())
                ))
        ));

        SPEC_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("WarehouseName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("LocationTypeName", FieldType.ENTITY_NAME, true, null, null)
        );
        
        EDIT_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("LocationTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 132L)
        );
    }
    
    @Inject
    WarehouseControl warehouseControl;

    /** Creates a new instance of EditLocationTypeCommand */
    public EditLocationTypeCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    public EditLocationTypeResult getResult() {
        return WarehouseResultFactory.getEditLocationTypeResult();
    }

    @Override
    public LocationTypeEdit getEdit() {
        return WarehouseEditFactory.getLocationTypeEdit();
    }

    Warehouse warehouse;

    @Override
    public LocationType getEntity(EditLocationTypeResult result) {
        LocationType locationType = null;
        var warehouseName = spec.getWarehouseName();

        warehouse = warehouseControl.getWarehouseByName(warehouseName);

        if(warehouse != null) {
            var warehouseParty = warehouse.getParty();
            var locationTypeName = spec.getLocationTypeName();

            locationType = warehouseControl.getLocationTypeByName(warehouseParty, locationTypeName,
                    editModeToEntityPermission(editMode));

            if(locationType == null) {
                addExecutionError(ExecutionErrors.UnknownLocationTypeName.name(), warehouse.getWarehouseName(), locationTypeName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownWarehouseName.name(), warehouseName);
        }

        return locationType;
    }

    @Override
    public LocationType getLockEntity(LocationType locationType) {
        return locationType;
    }

    @Override
    public void fillInResult(EditLocationTypeResult result, LocationType locationType) {
        result.setLocationType(warehouseControl.getLocationTypeTransfer(getUserVisit(), locationType));
    }

    @Override
    public void doLock(LocationTypeEdit edit, LocationType locationType) {
        var locationTypeDescription = warehouseControl.getLocationTypeDescription(locationType, getPreferredLanguage());
        var locationTypeDetail = locationType.getLastDetail();

        edit.setLocationTypeName(locationTypeDetail.getLocationTypeName());
        edit.setIsDefault(locationTypeDetail.getIsDefault().toString());
        edit.setSortOrder(locationTypeDetail.getSortOrder().toString());

        if(locationTypeDescription != null) {
            edit.setDescription(locationTypeDescription.getDescription());
        }
    }

    @Override
    public void canUpdate(LocationType locationType) {
        var warehouseParty = warehouse.getParty();
        var locationTypeName = edit.getLocationTypeName();
        var duplicateLocationType = warehouseControl.getLocationTypeByName(warehouseParty, locationTypeName);

        if(duplicateLocationType != null && !locationType.equals(duplicateLocationType)) {
            addExecutionError(ExecutionErrors.DuplicateLocationTypeName.name(), warehouse.getWarehouseName(), locationTypeName);
        }
    }

    @Override
    public void doUpdate(LocationType locationType) {
        var partyPK = getPartyPK();
        var locationTypeDetailValue = warehouseControl.getLocationTypeDetailValueForUpdate(locationType);
        var locationTypeDescription = warehouseControl.getLocationTypeDescriptionForUpdate(locationType, getPreferredLanguage());
        var description = edit.getDescription();

        locationTypeDetailValue.setLocationTypeName(edit.getLocationTypeName());
        locationTypeDetailValue.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
        locationTypeDetailValue.setSortOrder(Integer.valueOf(edit.getSortOrder()));

        warehouseControl.updateLocationTypeFromValue(locationTypeDetailValue, partyPK);

        if(locationTypeDescription == null && description != null) {
            warehouseControl.createLocationTypeDescription(locationType, getPreferredLanguage(), description, partyPK);
        } else if(locationTypeDescription != null && description == null) {
            warehouseControl.deleteLocationTypeDescription(locationTypeDescription, partyPK);
        } else if(locationTypeDescription != null && description != null) {
            var locationTypeDescriptionValue = warehouseControl.getLocationTypeDescriptionValue(locationTypeDescription);

            locationTypeDescriptionValue.setDescription(description);
            warehouseControl.updateLocationTypeDescriptionFromValue(locationTypeDescriptionValue, partyPK);
        }
    }

}
