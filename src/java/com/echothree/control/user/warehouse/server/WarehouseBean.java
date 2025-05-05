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
        return new CreateLocationUseTypeCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getLocationUseTypes(UserVisitPK userVisitPK, GetLocationUseTypesForm form) {
        return new GetLocationUseTypesCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getLocationUseType(UserVisitPK userVisitPK, GetLocationUseTypeForm form) {
        return new GetLocationUseTypeCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getLocationUseTypeChoices(UserVisitPK userVisitPK, GetLocationUseTypeChoicesForm form) {
        return new GetLocationUseTypeChoicesCommand().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Location Use Type Descriptions
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createLocationUseTypeDescription(UserVisitPK userVisitPK, CreateLocationUseTypeDescriptionForm form) {
        return new CreateLocationUseTypeDescriptionCommand().run(userVisitPK, form);
    }

    // -------------------------------------------------------------------------
    //   Warehouse Types
    // -------------------------------------------------------------------------

    @Override
    public CommandResult createWarehouseType(UserVisitPK userVisitPK, CreateWarehouseTypeForm form) {
        return new CreateWarehouseTypeCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getWarehouseTypes(UserVisitPK userVisitPK, GetWarehouseTypesForm form) {
        return new GetWarehouseTypesCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getWarehouseType(UserVisitPK userVisitPK, GetWarehouseTypeForm form) {
        return new GetWarehouseTypeCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getWarehouseTypeChoices(UserVisitPK userVisitPK, GetWarehouseTypeChoicesForm form) {
        return new GetWarehouseTypeChoicesCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult setDefaultWarehouseType(UserVisitPK userVisitPK, SetDefaultWarehouseTypeForm form) {
        return new SetDefaultWarehouseTypeCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult editWarehouseType(UserVisitPK userVisitPK, EditWarehouseTypeForm form) {
        return new EditWarehouseTypeCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteWarehouseType(UserVisitPK userVisitPK, DeleteWarehouseTypeForm form) {
        return new DeleteWarehouseTypeCommand().run(userVisitPK, form);
    }

    // -------------------------------------------------------------------------
    //   Warehouse Type Descriptions
    // -------------------------------------------------------------------------

    @Override
    public CommandResult createWarehouseTypeDescription(UserVisitPK userVisitPK, CreateWarehouseTypeDescriptionForm form) {
        return new CreateWarehouseTypeDescriptionCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getWarehouseTypeDescriptions(UserVisitPK userVisitPK, GetWarehouseTypeDescriptionsForm form) {
        return new GetWarehouseTypeDescriptionsCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getWarehouseTypeDescription(UserVisitPK userVisitPK, GetWarehouseTypeDescriptionForm form) {
        return new GetWarehouseTypeDescriptionCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult editWarehouseTypeDescription(UserVisitPK userVisitPK, EditWarehouseTypeDescriptionForm form) {
        return new EditWarehouseTypeDescriptionCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteWarehouseTypeDescription(UserVisitPK userVisitPK, DeleteWarehouseTypeDescriptionForm form) {
        return new DeleteWarehouseTypeDescriptionCommand().run(userVisitPK, form);
    }

    // -------------------------------------------------------------------------
    //   Warehouses
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createWarehouse(UserVisitPK userVisitPK, CreateWarehouseForm form) {
        return new CreateWarehouseCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getWarehouses(UserVisitPK userVisitPK, GetWarehousesForm form) {
        return new GetWarehousesCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getWarehouse(UserVisitPK userVisitPK, GetWarehouseForm form) {
        return new GetWarehouseCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getWarehouseChoices(UserVisitPK userVisitPK, GetWarehouseChoicesForm form) {
        return new GetWarehouseChoicesCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult setDefaultWarehouse(UserVisitPK userVisitPK, SetDefaultWarehouseForm form) {
        return new SetDefaultWarehouseCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editWarehouse(UserVisitPK userVisitPK, EditWarehouseForm form) {
        return new EditWarehouseCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteWarehouse(UserVisitPK userVisitPK, DeleteWarehouseForm form) {
        return new DeleteWarehouseCommand().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Location Types
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createLocationType(UserVisitPK userVisitPK, CreateLocationTypeForm form) {
        return new CreateLocationTypeCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getLocationTypes(UserVisitPK userVisitPK, GetLocationTypesForm form) {
        return new GetLocationTypesCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getLocationType(UserVisitPK userVisitPK, GetLocationTypeForm form) {
        return new GetLocationTypeCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getLocationTypeChoices(UserVisitPK userVisitPK, GetLocationTypeChoicesForm form) {
        return new GetLocationTypeChoicesCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult setDefaultLocationType(UserVisitPK userVisitPK, SetDefaultLocationTypeForm form) {
        return new SetDefaultLocationTypeCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editLocationType(UserVisitPK userVisitPK, EditLocationTypeForm form) {
        return new EditLocationTypeCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteLocationType(UserVisitPK userVisitPK, DeleteLocationTypeForm form) {
        return new DeleteLocationTypeCommand().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Location Type Descriptions
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createLocationTypeDescription(UserVisitPK userVisitPK, CreateLocationTypeDescriptionForm form) {
        return new CreateLocationTypeDescriptionCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getLocationTypeDescriptions(UserVisitPK userVisitPK, GetLocationTypeDescriptionsForm form) {
        return new GetLocationTypeDescriptionsCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editLocationTypeDescription(UserVisitPK userVisitPK, EditLocationTypeDescriptionForm form) {
        return new EditLocationTypeDescriptionCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteLocationTypeDescription(UserVisitPK userVisitPK, DeleteLocationTypeDescriptionForm form) {
        return new DeleteLocationTypeDescriptionCommand().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Location Name Elements
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createLocationNameElement(UserVisitPK userVisitPK, CreateLocationNameElementForm form) {
        return new CreateLocationNameElementCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getLocationNameElements(UserVisitPK userVisitPK, GetLocationNameElementsForm form) {
        return new GetLocationNameElementsCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getLocationNameElement(UserVisitPK userVisitPK, GetLocationNameElementForm form) {
        return new GetLocationNameElementCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult editLocationNameElement(UserVisitPK userVisitPK, EditLocationNameElementForm form) {
        return new EditLocationNameElementCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteLocationNameElement(UserVisitPK userVisitPK, DeleteLocationNameElementForm form) {
        return new DeleteLocationNameElementCommand().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Location Name Element Descriptions
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createLocationNameElementDescription(UserVisitPK userVisitPK, CreateLocationNameElementDescriptionForm form) {
        return new CreateLocationNameElementDescriptionCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getLocationNameElementDescriptions(UserVisitPK userVisitPK, GetLocationNameElementDescriptionsForm form) {
        return new GetLocationNameElementDescriptionsCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editLocationNameElementDescription(UserVisitPK userVisitPK, EditLocationNameElementDescriptionForm form) {
        return new EditLocationNameElementDescriptionCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteLocationNameElementDescription(UserVisitPK userVisitPK, DeleteLocationNameElementDescriptionForm form) {
        return new DeleteLocationNameElementDescriptionCommand().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Locations
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createLocation(UserVisitPK userVisitPK, CreateLocationForm form) {
        return new CreateLocationCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getLocations(UserVisitPK userVisitPK, GetLocationsForm form) {
        return new GetLocationsCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getLocation(UserVisitPK userVisitPK, GetLocationForm form) {
        return new GetLocationCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getLocationChoices(UserVisitPK userVisitPK, GetLocationChoicesForm form) {
        return new GetLocationChoicesCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getLocationStatusChoices(UserVisitPK userVisitPK, GetLocationStatusChoicesForm form) {
        return new GetLocationStatusChoicesCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult setLocationStatus(UserVisitPK userVisitPK, SetLocationStatusForm form) {
        return new SetLocationStatusCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editLocation(UserVisitPK userVisitPK, EditLocationForm form) {
        return new EditLocationCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteLocation(UserVisitPK userVisitPK, DeleteLocationForm form) {
        return new DeleteLocationCommand().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Location Descriptions
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createLocationDescription(UserVisitPK userVisitPK, CreateLocationDescriptionForm form) {
        return new CreateLocationDescriptionCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getLocationDescriptions(UserVisitPK userVisitPK, GetLocationDescriptionsForm form) {
        return new GetLocationDescriptionsCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editLocationDescription(UserVisitPK userVisitPK, EditLocationDescriptionForm form) {
        return new EditLocationDescriptionCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteLocationDescription(UserVisitPK userVisitPK, DeleteLocationDescriptionForm form) {
        return new DeleteLocationDescriptionCommand().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Location Capacities
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createLocationCapacity(UserVisitPK userVisitPK, CreateLocationCapacityForm form) {
        return new CreateLocationCapacityCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getLocationCapacities(UserVisitPK userVisitPK, GetLocationCapacitiesForm form) {
        return new GetLocationCapacitiesCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editLocationCapacity(UserVisitPK userVisitPK, EditLocationCapacityForm form) {
        return new EditLocationCapacityCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteLocationCapacity(UserVisitPK userVisitPK, DeleteLocationCapacityForm form) {
        return new DeleteLocationCapacityCommand().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Location Volumes
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createLocationVolume(UserVisitPK userVisitPK, CreateLocationVolumeForm form) {
        return new CreateLocationVolumeCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editLocationVolume(UserVisitPK userVisitPK, EditLocationVolumeForm form) {
        return new EditLocationVolumeCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteLocationVolume(UserVisitPK userVisitPK, DeleteLocationVolumeForm form) {
        return new DeleteLocationVolumeCommand().run(userVisitPK, form);
    }
    
}
