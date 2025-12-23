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

package com.echothree.control.user.warehouse.common;

import com.echothree.control.user.warehouse.common.form.*;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.CommandResult;

public interface WarehouseService
        extends WarehouseForms {
    
    // -------------------------------------------------------------------------
    //   Testing
    // -------------------------------------------------------------------------
    
    String ping();
    
    // -------------------------------------------------------------------------
    //   Location Use Types
    // -------------------------------------------------------------------------
    
    CommandResult createLocationUseType(UserVisitPK userVisitPK, CreateLocationUseTypeForm form);

    CommandResult getLocationUseTypes(UserVisitPK userVisitPK, GetLocationUseTypesForm form);

    CommandResult getLocationUseType(UserVisitPK userVisitPK, GetLocationUseTypeForm form);

    CommandResult getLocationUseTypeChoices(UserVisitPK userVisitPK, GetLocationUseTypeChoicesForm form);
    
    // -------------------------------------------------------------------------
    //   Location Use Type Descriptions
    // -------------------------------------------------------------------------
    
    CommandResult createLocationUseTypeDescription(UserVisitPK userVisitPK, CreateLocationUseTypeDescriptionForm form);

    // -------------------------------------------------------------------------
    //   WarehouseT ypes
    // -------------------------------------------------------------------------

    CommandResult createWarehouseType(UserVisitPK userVisitPK, CreateWarehouseTypeForm form);

    CommandResult getWarehouseTypes(UserVisitPK userVisitPK, GetWarehouseTypesForm form);

    CommandResult getWarehouseType(UserVisitPK userVisitPK, GetWarehouseTypeForm form);

    CommandResult getWarehouseTypeChoices(UserVisitPK userVisitPK, GetWarehouseTypeChoicesForm form);

    CommandResult setDefaultWarehouseType(UserVisitPK userVisitPK, SetDefaultWarehouseTypeForm form);

    CommandResult editWarehouseType(UserVisitPK userVisitPK, EditWarehouseTypeForm form);

    CommandResult deleteWarehouseType(UserVisitPK userVisitPK, DeleteWarehouseTypeForm form);

    // -------------------------------------------------------------------------
    //   Warehouse Type Descriptions
    // -------------------------------------------------------------------------

    CommandResult createWarehouseTypeDescription(UserVisitPK userVisitPK, CreateWarehouseTypeDescriptionForm form);

    CommandResult getWarehouseTypeDescriptions(UserVisitPK userVisitPK, GetWarehouseTypeDescriptionsForm form);

    CommandResult getWarehouseTypeDescription(UserVisitPK userVisitPK, GetWarehouseTypeDescriptionForm form);

    CommandResult editWarehouseTypeDescription(UserVisitPK userVisitPK, EditWarehouseTypeDescriptionForm form);

    CommandResult deleteWarehouseTypeDescription(UserVisitPK userVisitPK, DeleteWarehouseTypeDescriptionForm form);

    // -------------------------------------------------------------------------
    //   Warehouses
    // -------------------------------------------------------------------------
    
    CommandResult createWarehouse(UserVisitPK userVisitPK, CreateWarehouseForm form);
    
    CommandResult getWarehouses(UserVisitPK userVisitPK, GetWarehousesForm form);
    
    CommandResult getWarehouse(UserVisitPK userVisitPK, GetWarehouseForm form);
    
    CommandResult getWarehouseChoices(UserVisitPK userVisitPK, GetWarehouseChoicesForm form);
    
    CommandResult setDefaultWarehouse(UserVisitPK userVisitPK, SetDefaultWarehouseForm form);
    
    CommandResult editWarehouse(UserVisitPK userVisitPK, EditWarehouseForm form);
    
    CommandResult deleteWarehouse(UserVisitPK userVisitPK, DeleteWarehouseForm form);
    
    // -------------------------------------------------------------------------
    //   Location Types
    // -------------------------------------------------------------------------
    
    CommandResult createLocationType(UserVisitPK userVisitPK, CreateLocationTypeForm form);
    
    CommandResult getLocationTypes(UserVisitPK userVisitPK, GetLocationTypesForm form);
    
    CommandResult getLocationType(UserVisitPK userVisitPK, GetLocationTypeForm form);
    
    CommandResult getLocationTypeChoices(UserVisitPK userVisitPK, GetLocationTypeChoicesForm form);
    
    CommandResult setDefaultLocationType(UserVisitPK userVisitPK, SetDefaultLocationTypeForm form);
    
    CommandResult editLocationType(UserVisitPK userVisitPK, EditLocationTypeForm form);
    
    CommandResult deleteLocationType(UserVisitPK userVisitPK, DeleteLocationTypeForm form);
    
    // -------------------------------------------------------------------------
    //   Location Type Descriptions
    // -------------------------------------------------------------------------
    
    CommandResult createLocationTypeDescription(UserVisitPK userVisitPK, CreateLocationTypeDescriptionForm form);
    
    CommandResult getLocationTypeDescriptions(UserVisitPK userVisitPK, GetLocationTypeDescriptionsForm form);
    
    CommandResult editLocationTypeDescription(UserVisitPK userVisitPK, EditLocationTypeDescriptionForm form);
    
    CommandResult deleteLocationTypeDescription(UserVisitPK userVisitPK, DeleteLocationTypeDescriptionForm form);
    
    // -------------------------------------------------------------------------
    //   Location Name Elements
    // -------------------------------------------------------------------------
    
    CommandResult createLocationNameElement(UserVisitPK userVisitPK, CreateLocationNameElementForm form);
    
    CommandResult getLocationNameElements(UserVisitPK userVisitPK, GetLocationNameElementsForm form);

    CommandResult getLocationNameElement(UserVisitPK userVisitPK, GetLocationNameElementForm form);

    CommandResult editLocationNameElement(UserVisitPK userVisitPK, EditLocationNameElementForm form);
    
    CommandResult deleteLocationNameElement(UserVisitPK userVisitPK, DeleteLocationNameElementForm form);
    
    // -------------------------------------------------------------------------
    //   Location Name Element Descriptions
    // -------------------------------------------------------------------------
    
    CommandResult createLocationNameElementDescription(UserVisitPK userVisitPK, CreateLocationNameElementDescriptionForm form);
    
    CommandResult getLocationNameElementDescriptions(UserVisitPK userVisitPK, GetLocationNameElementDescriptionsForm form);
    
    CommandResult editLocationNameElementDescription(UserVisitPK userVisitPK, EditLocationNameElementDescriptionForm form);
    
    CommandResult deleteLocationNameElementDescription(UserVisitPK userVisitPK, DeleteLocationNameElementDescriptionForm form);
    
    // -------------------------------------------------------------------------
    //   Locations
    // -------------------------------------------------------------------------
    
    CommandResult createLocation(UserVisitPK userVisitPK, CreateLocationForm form);
    
    CommandResult getLocations(UserVisitPK userVisitPK, GetLocationsForm form);
    
    CommandResult getLocation(UserVisitPK userVisitPK, GetLocationForm form);
    
    CommandResult getLocationChoices(UserVisitPK userVisitPK, GetLocationChoicesForm form);
    
    CommandResult getLocationStatusChoices(UserVisitPK userVisitPK, GetLocationStatusChoicesForm form);
    
    CommandResult setLocationStatus(UserVisitPK userVisitPK, SetLocationStatusForm form);
    
    CommandResult editLocation(UserVisitPK userVisitPK, EditLocationForm form);
    
    CommandResult deleteLocation(UserVisitPK userVisitPK, DeleteLocationForm form);
    
    // -------------------------------------------------------------------------
    //   Location Descriptions
    // -------------------------------------------------------------------------
    
    CommandResult createLocationDescription(UserVisitPK userVisitPK, CreateLocationDescriptionForm form);
    
    CommandResult getLocationDescriptions(UserVisitPK userVisitPK, GetLocationDescriptionsForm form);
    
    CommandResult editLocationDescription(UserVisitPK userVisitPK, EditLocationDescriptionForm form);
    
    CommandResult deleteLocationDescription(UserVisitPK userVisitPK, DeleteLocationDescriptionForm form);
    
    // --------------------------------------------------------------------------------
    //   Location Capacities
    // --------------------------------------------------------------------------------
    
    CommandResult createLocationCapacity(UserVisitPK userVisitPK, CreateLocationCapacityForm form);
    
    CommandResult getLocationCapacities(UserVisitPK userVisitPK, GetLocationCapacitiesForm form);
    
    CommandResult editLocationCapacity(UserVisitPK userVisitPK, EditLocationCapacityForm form);
    
    CommandResult deleteLocationCapacity(UserVisitPK userVisitPK, DeleteLocationCapacityForm form);
    
    // --------------------------------------------------------------------------------
    //   Location Volumes
    // --------------------------------------------------------------------------------
    
    CommandResult createLocationVolume(UserVisitPK userVisitPK, CreateLocationVolumeForm form);
    
    CommandResult editLocationVolume(UserVisitPK userVisitPK, EditLocationVolumeForm form);
    
    CommandResult deleteLocationVolume(UserVisitPK userVisitPK, DeleteLocationVolumeForm form);
    
}
