// --------------------------------------------------------------------------------
// Copyright 2002-2024 Echo Three, LLC
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
        return new CreateLocationUseTypeCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getLocationUseTypes(UserVisitPK userVisitPK, GetLocationUseTypesForm form) {
        return new GetLocationUseTypesCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getLocationUseType(UserVisitPK userVisitPK, GetLocationUseTypeForm form) {
        return new GetLocationUseTypeCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getLocationUseTypeChoices(UserVisitPK userVisitPK, GetLocationUseTypeChoicesForm form) {
        return new GetLocationUseTypeChoicesCommand(userVisitPK, form).run();
    }
    
    // -------------------------------------------------------------------------
    //   Location Use Type Descriptions
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createLocationUseTypeDescription(UserVisitPK userVisitPK, CreateLocationUseTypeDescriptionForm form) {
        return new CreateLocationUseTypeDescriptionCommand(userVisitPK, form).run();
    }
    
    // -------------------------------------------------------------------------
    //   Warehouses
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createWarehouse(UserVisitPK userVisitPK, CreateWarehouseForm form) {
        return new CreateWarehouseCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getWarehouses(UserVisitPK userVisitPK, GetWarehousesForm form) {
        return new GetWarehousesCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getWarehouse(UserVisitPK userVisitPK, GetWarehouseForm form) {
        return new GetWarehouseCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getWarehouseChoices(UserVisitPK userVisitPK, GetWarehouseChoicesForm form) {
        return new GetWarehouseChoicesCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult setDefaultWarehouse(UserVisitPK userVisitPK, SetDefaultWarehouseForm form) {
        return new SetDefaultWarehouseCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editWarehouse(UserVisitPK userVisitPK, EditWarehouseForm form) {
        return new EditWarehouseCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteWarehouse(UserVisitPK userVisitPK, DeleteWarehouseForm form) {
        return new DeleteWarehouseCommand(userVisitPK, form).run();
    }
    
    // -------------------------------------------------------------------------
    //   Location Types
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createLocationType(UserVisitPK userVisitPK, CreateLocationTypeForm form) {
        return new CreateLocationTypeCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getLocationTypes(UserVisitPK userVisitPK, GetLocationTypesForm form) {
        return new GetLocationTypesCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getLocationType(UserVisitPK userVisitPK, GetLocationTypeForm form) {
        return new GetLocationTypeCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getLocationTypeChoices(UserVisitPK userVisitPK, GetLocationTypeChoicesForm form) {
        return new GetLocationTypeChoicesCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult setDefaultLocationType(UserVisitPK userVisitPK, SetDefaultLocationTypeForm form) {
        return new SetDefaultLocationTypeCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editLocationType(UserVisitPK userVisitPK, EditLocationTypeForm form) {
        return new EditLocationTypeCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteLocationType(UserVisitPK userVisitPK, DeleteLocationTypeForm form) {
        return new DeleteLocationTypeCommand(userVisitPK, form).run();
    }
    
    // -------------------------------------------------------------------------
    //   Location Type Descriptions
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createLocationTypeDescription(UserVisitPK userVisitPK, CreateLocationTypeDescriptionForm form) {
        return new CreateLocationTypeDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getLocationTypeDescriptions(UserVisitPK userVisitPK, GetLocationTypeDescriptionsForm form) {
        return new GetLocationTypeDescriptionsCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editLocationTypeDescription(UserVisitPK userVisitPK, EditLocationTypeDescriptionForm form) {
        return new EditLocationTypeDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteLocationTypeDescription(UserVisitPK userVisitPK, DeleteLocationTypeDescriptionForm form) {
        return new DeleteLocationTypeDescriptionCommand(userVisitPK, form).run();
    }
    
    // -------------------------------------------------------------------------
    //   Location Name Elements
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createLocationNameElement(UserVisitPK userVisitPK, CreateLocationNameElementForm form) {
        return new CreateLocationNameElementCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getLocationNameElements(UserVisitPK userVisitPK, GetLocationNameElementsForm form) {
        return new GetLocationNameElementsCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getLocationNameElement(UserVisitPK userVisitPK, GetLocationNameElementForm form) {
        return new GetLocationNameElementCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult editLocationNameElement(UserVisitPK userVisitPK, EditLocationNameElementForm form) {
        return new EditLocationNameElementCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteLocationNameElement(UserVisitPK userVisitPK, DeleteLocationNameElementForm form) {
        return new DeleteLocationNameElementCommand(userVisitPK, form).run();
    }
    
    // -------------------------------------------------------------------------
    //   Location Name Element Descriptions
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createLocationNameElementDescription(UserVisitPK userVisitPK, CreateLocationNameElementDescriptionForm form) {
        return new CreateLocationNameElementDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getLocationNameElementDescriptions(UserVisitPK userVisitPK, GetLocationNameElementDescriptionsForm form) {
        return new GetLocationNameElementDescriptionsCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editLocationNameElementDescription(UserVisitPK userVisitPK, EditLocationNameElementDescriptionForm form) {
        return new EditLocationNameElementDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteLocationNameElementDescription(UserVisitPK userVisitPK, DeleteLocationNameElementDescriptionForm form) {
        return new DeleteLocationNameElementDescriptionCommand(userVisitPK, form).run();
    }
    
    // -------------------------------------------------------------------------
    //   Locations
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createLocation(UserVisitPK userVisitPK, CreateLocationForm form) {
        return new CreateLocationCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getLocations(UserVisitPK userVisitPK, GetLocationsForm form) {
        return new GetLocationsCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getLocation(UserVisitPK userVisitPK, GetLocationForm form) {
        return new GetLocationCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getLocationChoices(UserVisitPK userVisitPK, GetLocationChoicesForm form) {
        return new GetLocationChoicesCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getLocationStatusChoices(UserVisitPK userVisitPK, GetLocationStatusChoicesForm form) {
        return new GetLocationStatusChoicesCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult setLocationStatus(UserVisitPK userVisitPK, SetLocationStatusForm form) {
        return new SetLocationStatusCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editLocation(UserVisitPK userVisitPK, EditLocationForm form) {
        return new EditLocationCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteLocation(UserVisitPK userVisitPK, DeleteLocationForm form) {
        return new DeleteLocationCommand(userVisitPK, form).run();
    }
    
    // -------------------------------------------------------------------------
    //   Location Descriptions
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createLocationDescription(UserVisitPK userVisitPK, CreateLocationDescriptionForm form) {
        return new CreateLocationDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getLocationDescriptions(UserVisitPK userVisitPK, GetLocationDescriptionsForm form) {
        return new GetLocationDescriptionsCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editLocationDescription(UserVisitPK userVisitPK, EditLocationDescriptionForm form) {
        return new EditLocationDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteLocationDescription(UserVisitPK userVisitPK, DeleteLocationDescriptionForm form) {
        return new DeleteLocationDescriptionCommand(userVisitPK, form).run();
    }
    
    // --------------------------------------------------------------------------------
    //   Location Capacities
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createLocationCapacity(UserVisitPK userVisitPK, CreateLocationCapacityForm form) {
        return new CreateLocationCapacityCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getLocationCapacities(UserVisitPK userVisitPK, GetLocationCapacitiesForm form) {
        return new GetLocationCapacitiesCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editLocationCapacity(UserVisitPK userVisitPK, EditLocationCapacityForm form) {
        return new EditLocationCapacityCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteLocationCapacity(UserVisitPK userVisitPK, DeleteLocationCapacityForm form) {
        return new DeleteLocationCapacityCommand(userVisitPK, form).run();
    }
    
    // --------------------------------------------------------------------------------
    //   Location Volumes
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createLocationVolume(UserVisitPK userVisitPK, CreateLocationVolumeForm form) {
        return new CreateLocationVolumeCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editLocationVolume(UserVisitPK userVisitPK, EditLocationVolumeForm form) {
        return new EditLocationVolumeCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteLocationVolume(UserVisitPK userVisitPK, DeleteLocationVolumeForm form) {
        return new DeleteLocationVolumeCommand(userVisitPK, form).run();
    }
    
}
