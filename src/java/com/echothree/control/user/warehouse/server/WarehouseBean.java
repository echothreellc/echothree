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

package com.echothree.control.user.warehouse.server;

import com.echothree.control.user.warehouse.common.WarehouseRemote;
import com.echothree.control.user.warehouse.common.form.*;
import com.echothree.control.user.warehouse.server.command.*;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.CommandResult;
import javax.ejb.Stateless;
import javax.enterprise.inject.spi.CDI;

@Stateless
public class WarehouseBean
        extends WarehouseFormsImpl
        implements WarehouseRemote, WarehouseLocal {
    
    // -------------------------------------------------------------------------
    //   Testing
    // -------------------------------------------------------------------------
    
    @Override
    public String ping() {
        return "WarehouseBean is alive!";
    }
    
    // -------------------------------------------------------------------------
    //   Location Use Types
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createLocationUseType(UserVisitPK userVisitPK, CreateLocationUseTypeForm form) {
        return CDI.current().select(CreateLocationUseTypeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getLocationUseTypes(UserVisitPK userVisitPK, GetLocationUseTypesForm form) {
        return CDI.current().select(GetLocationUseTypesCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getLocationUseType(UserVisitPK userVisitPK, GetLocationUseTypeForm form) {
        return CDI.current().select(GetLocationUseTypeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getLocationUseTypeChoices(UserVisitPK userVisitPK, GetLocationUseTypeChoicesForm form) {
        return CDI.current().select(GetLocationUseTypeChoicesCommand.class).get().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Location Use Type Descriptions
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createLocationUseTypeDescription(UserVisitPK userVisitPK, CreateLocationUseTypeDescriptionForm form) {
        return CDI.current().select(CreateLocationUseTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }

    // -------------------------------------------------------------------------
    //   Warehouse Types
    // -------------------------------------------------------------------------

    @Override
    public CommandResult createWarehouseType(UserVisitPK userVisitPK, CreateWarehouseTypeForm form) {
        return CDI.current().select(CreateWarehouseTypeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getWarehouseTypes(UserVisitPK userVisitPK, GetWarehouseTypesForm form) {
        return CDI.current().select(GetWarehouseTypesCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getWarehouseType(UserVisitPK userVisitPK, GetWarehouseTypeForm form) {
        return CDI.current().select(GetWarehouseTypeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getWarehouseTypeChoices(UserVisitPK userVisitPK, GetWarehouseTypeChoicesForm form) {
        return CDI.current().select(GetWarehouseTypeChoicesCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult setDefaultWarehouseType(UserVisitPK userVisitPK, SetDefaultWarehouseTypeForm form) {
        return CDI.current().select(SetDefaultWarehouseTypeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult editWarehouseType(UserVisitPK userVisitPK, EditWarehouseTypeForm form) {
        return CDI.current().select(EditWarehouseTypeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteWarehouseType(UserVisitPK userVisitPK, DeleteWarehouseTypeForm form) {
        return CDI.current().select(DeleteWarehouseTypeCommand.class).get().run(userVisitPK, form);
    }

    // -------------------------------------------------------------------------
    //   Warehouse Type Descriptions
    // -------------------------------------------------------------------------

    @Override
    public CommandResult createWarehouseTypeDescription(UserVisitPK userVisitPK, CreateWarehouseTypeDescriptionForm form) {
        return CDI.current().select(CreateWarehouseTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getWarehouseTypeDescriptions(UserVisitPK userVisitPK, GetWarehouseTypeDescriptionsForm form) {
        return CDI.current().select(GetWarehouseTypeDescriptionsCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getWarehouseTypeDescription(UserVisitPK userVisitPK, GetWarehouseTypeDescriptionForm form) {
        return CDI.current().select(GetWarehouseTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult editWarehouseTypeDescription(UserVisitPK userVisitPK, EditWarehouseTypeDescriptionForm form) {
        return CDI.current().select(EditWarehouseTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteWarehouseTypeDescription(UserVisitPK userVisitPK, DeleteWarehouseTypeDescriptionForm form) {
        return CDI.current().select(DeleteWarehouseTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }

    // -------------------------------------------------------------------------
    //   Warehouses
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createWarehouse(UserVisitPK userVisitPK, CreateWarehouseForm form) {
        return CDI.current().select(CreateWarehouseCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getWarehouses(UserVisitPK userVisitPK, GetWarehousesForm form) {
        return CDI.current().select(GetWarehousesCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getWarehouse(UserVisitPK userVisitPK, GetWarehouseForm form) {
        return CDI.current().select(GetWarehouseCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getWarehouseChoices(UserVisitPK userVisitPK, GetWarehouseChoicesForm form) {
        return CDI.current().select(GetWarehouseChoicesCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult setDefaultWarehouse(UserVisitPK userVisitPK, SetDefaultWarehouseForm form) {
        return CDI.current().select(SetDefaultWarehouseCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editWarehouse(UserVisitPK userVisitPK, EditWarehouseForm form) {
        return CDI.current().select(EditWarehouseCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteWarehouse(UserVisitPK userVisitPK, DeleteWarehouseForm form) {
        return CDI.current().select(DeleteWarehouseCommand.class).get().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Location Types
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createLocationType(UserVisitPK userVisitPK, CreateLocationTypeForm form) {
        return CDI.current().select(CreateLocationTypeCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getLocationTypes(UserVisitPK userVisitPK, GetLocationTypesForm form) {
        return CDI.current().select(GetLocationTypesCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getLocationType(UserVisitPK userVisitPK, GetLocationTypeForm form) {
        return CDI.current().select(GetLocationTypeCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getLocationTypeChoices(UserVisitPK userVisitPK, GetLocationTypeChoicesForm form) {
        return CDI.current().select(GetLocationTypeChoicesCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult setDefaultLocationType(UserVisitPK userVisitPK, SetDefaultLocationTypeForm form) {
        return CDI.current().select(SetDefaultLocationTypeCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editLocationType(UserVisitPK userVisitPK, EditLocationTypeForm form) {
        return CDI.current().select(EditLocationTypeCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteLocationType(UserVisitPK userVisitPK, DeleteLocationTypeForm form) {
        return CDI.current().select(DeleteLocationTypeCommand.class).get().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Location Type Descriptions
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createLocationTypeDescription(UserVisitPK userVisitPK, CreateLocationTypeDescriptionForm form) {
        return CDI.current().select(CreateLocationTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getLocationTypeDescriptions(UserVisitPK userVisitPK, GetLocationTypeDescriptionsForm form) {
        return CDI.current().select(GetLocationTypeDescriptionsCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editLocationTypeDescription(UserVisitPK userVisitPK, EditLocationTypeDescriptionForm form) {
        return CDI.current().select(EditLocationTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteLocationTypeDescription(UserVisitPK userVisitPK, DeleteLocationTypeDescriptionForm form) {
        return CDI.current().select(DeleteLocationTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Location Name Elements
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createLocationNameElement(UserVisitPK userVisitPK, CreateLocationNameElementForm form) {
        return CDI.current().select(CreateLocationNameElementCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getLocationNameElements(UserVisitPK userVisitPK, GetLocationNameElementsForm form) {
        return CDI.current().select(GetLocationNameElementsCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getLocationNameElement(UserVisitPK userVisitPK, GetLocationNameElementForm form) {
        return CDI.current().select(GetLocationNameElementCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult editLocationNameElement(UserVisitPK userVisitPK, EditLocationNameElementForm form) {
        return CDI.current().select(EditLocationNameElementCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteLocationNameElement(UserVisitPK userVisitPK, DeleteLocationNameElementForm form) {
        return CDI.current().select(DeleteLocationNameElementCommand.class).get().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Location Name Element Descriptions
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createLocationNameElementDescription(UserVisitPK userVisitPK, CreateLocationNameElementDescriptionForm form) {
        return CDI.current().select(CreateLocationNameElementDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getLocationNameElementDescriptions(UserVisitPK userVisitPK, GetLocationNameElementDescriptionsForm form) {
        return CDI.current().select(GetLocationNameElementDescriptionsCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editLocationNameElementDescription(UserVisitPK userVisitPK, EditLocationNameElementDescriptionForm form) {
        return CDI.current().select(EditLocationNameElementDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteLocationNameElementDescription(UserVisitPK userVisitPK, DeleteLocationNameElementDescriptionForm form) {
        return CDI.current().select(DeleteLocationNameElementDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Locations
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createLocation(UserVisitPK userVisitPK, CreateLocationForm form) {
        return CDI.current().select(CreateLocationCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getLocations(UserVisitPK userVisitPK, GetLocationsForm form) {
        return CDI.current().select(GetLocationsCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getLocation(UserVisitPK userVisitPK, GetLocationForm form) {
        return CDI.current().select(GetLocationCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getLocationChoices(UserVisitPK userVisitPK, GetLocationChoicesForm form) {
        return CDI.current().select(GetLocationChoicesCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getLocationStatusChoices(UserVisitPK userVisitPK, GetLocationStatusChoicesForm form) {
        return CDI.current().select(GetLocationStatusChoicesCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult setLocationStatus(UserVisitPK userVisitPK, SetLocationStatusForm form) {
        return CDI.current().select(SetLocationStatusCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editLocation(UserVisitPK userVisitPK, EditLocationForm form) {
        return CDI.current().select(EditLocationCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteLocation(UserVisitPK userVisitPK, DeleteLocationForm form) {
        return CDI.current().select(DeleteLocationCommand.class).get().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Location Descriptions
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createLocationDescription(UserVisitPK userVisitPK, CreateLocationDescriptionForm form) {
        return CDI.current().select(CreateLocationDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getLocationDescriptions(UserVisitPK userVisitPK, GetLocationDescriptionsForm form) {
        return CDI.current().select(GetLocationDescriptionsCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editLocationDescription(UserVisitPK userVisitPK, EditLocationDescriptionForm form) {
        return CDI.current().select(EditLocationDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteLocationDescription(UserVisitPK userVisitPK, DeleteLocationDescriptionForm form) {
        return CDI.current().select(DeleteLocationDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Location Capacities
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createLocationCapacity(UserVisitPK userVisitPK, CreateLocationCapacityForm form) {
        return CDI.current().select(CreateLocationCapacityCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getLocationCapacities(UserVisitPK userVisitPK, GetLocationCapacitiesForm form) {
        return CDI.current().select(GetLocationCapacitiesCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editLocationCapacity(UserVisitPK userVisitPK, EditLocationCapacityForm form) {
        return CDI.current().select(EditLocationCapacityCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteLocationCapacity(UserVisitPK userVisitPK, DeleteLocationCapacityForm form) {
        return CDI.current().select(DeleteLocationCapacityCommand.class).get().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Location Volumes
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createLocationVolume(UserVisitPK userVisitPK, CreateLocationVolumeForm form) {
        return CDI.current().select(CreateLocationVolumeCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editLocationVolume(UserVisitPK userVisitPK, EditLocationVolumeForm form) {
        return CDI.current().select(EditLocationVolumeCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteLocationVolume(UserVisitPK userVisitPK, DeleteLocationVolumeForm form) {
        return CDI.current().select(DeleteLocationVolumeCommand.class).get().run(userVisitPK, form);
    }
    
}
