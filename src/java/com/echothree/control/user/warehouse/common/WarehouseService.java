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
import com.echothree.control.user.warehouse.common.result.*;
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
    
    CommandResult<?> createLocationUseType(UserVisitPK userVisitPK, CreateLocationUseTypeForm form);

    CommandResult<GetLocationUseTypesResult> getLocationUseTypes(UserVisitPK userVisitPK, GetLocationUseTypesForm form);

    CommandResult<GetLocationUseTypeResult> getLocationUseType(UserVisitPK userVisitPK, GetLocationUseTypeForm form);

    CommandResult<GetLocationUseTypeChoicesResult> getLocationUseTypeChoices(UserVisitPK userVisitPK, GetLocationUseTypeChoicesForm form);
    
    // -------------------------------------------------------------------------
    //   Location Use Type Descriptions
    // -------------------------------------------------------------------------
    
    CommandResult<?> createLocationUseTypeDescription(UserVisitPK userVisitPK, CreateLocationUseTypeDescriptionForm form);

    // -------------------------------------------------------------------------
    //   WarehouseT ypes
    // -------------------------------------------------------------------------

    CommandResult<?> createWarehouseType(UserVisitPK userVisitPK, CreateWarehouseTypeForm form);

    CommandResult<GetWarehouseTypesResult> getWarehouseTypes(UserVisitPK userVisitPK, GetWarehouseTypesForm form);

    CommandResult<GetWarehouseTypeResult> getWarehouseType(UserVisitPK userVisitPK, GetWarehouseTypeForm form);

    CommandResult<GetWarehouseTypeChoicesResult> getWarehouseTypeChoices(UserVisitPK userVisitPK, GetWarehouseTypeChoicesForm form);

    CommandResult<?> setDefaultWarehouseType(UserVisitPK userVisitPK, SetDefaultWarehouseTypeForm form);

    CommandResult<EditWarehouseTypeResult> editWarehouseType(UserVisitPK userVisitPK, EditWarehouseTypeForm form);

    CommandResult<?> deleteWarehouseType(UserVisitPK userVisitPK, DeleteWarehouseTypeForm form);

    // -------------------------------------------------------------------------
    //   Warehouse Type Descriptions
    // -------------------------------------------------------------------------

    CommandResult<?> createWarehouseTypeDescription(UserVisitPK userVisitPK, CreateWarehouseTypeDescriptionForm form);

    CommandResult<GetWarehouseTypeDescriptionsResult> getWarehouseTypeDescriptions(UserVisitPK userVisitPK, GetWarehouseTypeDescriptionsForm form);

    CommandResult<GetWarehouseTypeDescriptionResult> getWarehouseTypeDescription(UserVisitPK userVisitPK, GetWarehouseTypeDescriptionForm form);

    CommandResult<EditWarehouseTypeDescriptionResult> editWarehouseTypeDescription(UserVisitPK userVisitPK, EditWarehouseTypeDescriptionForm form);

    CommandResult<?> deleteWarehouseTypeDescription(UserVisitPK userVisitPK, DeleteWarehouseTypeDescriptionForm form);

    // -------------------------------------------------------------------------
    //   Warehouses
    // -------------------------------------------------------------------------
    
    CommandResult<?> createWarehouse(UserVisitPK userVisitPK, CreateWarehouseForm form);
    
    CommandResult<GetWarehousesResult> getWarehouses(UserVisitPK userVisitPK, GetWarehousesForm form);
    
    CommandResult<GetWarehouseResult> getWarehouse(UserVisitPK userVisitPK, GetWarehouseForm form);
    
    CommandResult<GetWarehouseChoicesResult> getWarehouseChoices(UserVisitPK userVisitPK, GetWarehouseChoicesForm form);
    
    CommandResult<?> setDefaultWarehouse(UserVisitPK userVisitPK, SetDefaultWarehouseForm form);
    
    CommandResult<EditWarehouseResult> editWarehouse(UserVisitPK userVisitPK, EditWarehouseForm form);
    
    CommandResult<?> deleteWarehouse(UserVisitPK userVisitPK, DeleteWarehouseForm form);
    
    // -------------------------------------------------------------------------
    //   Location Types
    // -------------------------------------------------------------------------
    
    CommandResult<?> createLocationType(UserVisitPK userVisitPK, CreateLocationTypeForm form);
    
    CommandResult<GetLocationTypesResult> getLocationTypes(UserVisitPK userVisitPK, GetLocationTypesForm form);
    
    CommandResult<GetLocationTypeResult> getLocationType(UserVisitPK userVisitPK, GetLocationTypeForm form);
    
    CommandResult<GetLocationTypeChoicesResult> getLocationTypeChoices(UserVisitPK userVisitPK, GetLocationTypeChoicesForm form);
    
    CommandResult<?> setDefaultLocationType(UserVisitPK userVisitPK, SetDefaultLocationTypeForm form);
    
    CommandResult<EditLocationTypeResult> editLocationType(UserVisitPK userVisitPK, EditLocationTypeForm form);
    
    CommandResult<?> deleteLocationType(UserVisitPK userVisitPK, DeleteLocationTypeForm form);
    
    // -------------------------------------------------------------------------
    //   Location Type Descriptions
    // -------------------------------------------------------------------------
    
    CommandResult<?> createLocationTypeDescription(UserVisitPK userVisitPK, CreateLocationTypeDescriptionForm form);
    
    CommandResult<GetLocationTypeDescriptionsResult> getLocationTypeDescriptions(UserVisitPK userVisitPK, GetLocationTypeDescriptionsForm form);
    
    CommandResult<EditLocationTypeDescriptionResult> editLocationTypeDescription(UserVisitPK userVisitPK, EditLocationTypeDescriptionForm form);
    
    CommandResult<?> deleteLocationTypeDescription(UserVisitPK userVisitPK, DeleteLocationTypeDescriptionForm form);
    
    // -------------------------------------------------------------------------
    //   Location Name Elements
    // -------------------------------------------------------------------------
    
    CommandResult<?> createLocationNameElement(UserVisitPK userVisitPK, CreateLocationNameElementForm form);
    
    CommandResult<GetLocationNameElementsResult> getLocationNameElements(UserVisitPK userVisitPK, GetLocationNameElementsForm form);

    CommandResult<GetLocationNameElementResult> getLocationNameElement(UserVisitPK userVisitPK, GetLocationNameElementForm form);

    CommandResult<EditLocationNameElementResult> editLocationNameElement(UserVisitPK userVisitPK, EditLocationNameElementForm form);
    
    CommandResult<?> deleteLocationNameElement(UserVisitPK userVisitPK, DeleteLocationNameElementForm form);
    
    // -------------------------------------------------------------------------
    //   Location Name Element Descriptions
    // -------------------------------------------------------------------------
    
    CommandResult<?> createLocationNameElementDescription(UserVisitPK userVisitPK, CreateLocationNameElementDescriptionForm form);
    
    CommandResult<GetLocationNameElementDescriptionsResult> getLocationNameElementDescriptions(UserVisitPK userVisitPK, GetLocationNameElementDescriptionsForm form);
    
    CommandResult<EditLocationNameElementDescriptionResult> editLocationNameElementDescription(UserVisitPK userVisitPK, EditLocationNameElementDescriptionForm form);
    
    CommandResult<?> deleteLocationNameElementDescription(UserVisitPK userVisitPK, DeleteLocationNameElementDescriptionForm form);
    
    // -------------------------------------------------------------------------
    //   Locations
    // -------------------------------------------------------------------------
    
    CommandResult<?> createLocation(UserVisitPK userVisitPK, CreateLocationForm form);
    
    CommandResult<GetLocationsResult> getLocations(UserVisitPK userVisitPK, GetLocationsForm form);
    
    CommandResult<GetLocationResult> getLocation(UserVisitPK userVisitPK, GetLocationForm form);
    
    CommandResult<GetLocationChoicesResult> getLocationChoices(UserVisitPK userVisitPK, GetLocationChoicesForm form);
    
    CommandResult<GetLocationStatusChoicesResult> getLocationStatusChoices(UserVisitPK userVisitPK, GetLocationStatusChoicesForm form);
    
    CommandResult<?> setLocationStatus(UserVisitPK userVisitPK, SetLocationStatusForm form);
    
    CommandResult<EditLocationResult> editLocation(UserVisitPK userVisitPK, EditLocationForm form);
    
    CommandResult<?> deleteLocation(UserVisitPK userVisitPK, DeleteLocationForm form);
    
    // -------------------------------------------------------------------------
    //   Location Descriptions
    // -------------------------------------------------------------------------
    
    CommandResult<?> createLocationDescription(UserVisitPK userVisitPK, CreateLocationDescriptionForm form);
    
    CommandResult<GetLocationDescriptionsResult> getLocationDescriptions(UserVisitPK userVisitPK, GetLocationDescriptionsForm form);
    
    CommandResult<EditLocationDescriptionResult> editLocationDescription(UserVisitPK userVisitPK, EditLocationDescriptionForm form);
    
    CommandResult<?> deleteLocationDescription(UserVisitPK userVisitPK, DeleteLocationDescriptionForm form);
    
    // --------------------------------------------------------------------------------
    //   Location Capacities
    // --------------------------------------------------------------------------------
    
    CommandResult<?> createLocationCapacity(UserVisitPK userVisitPK, CreateLocationCapacityForm form);
    
    CommandResult<GetLocationCapacitiesResult> getLocationCapacities(UserVisitPK userVisitPK, GetLocationCapacitiesForm form);
    
    CommandResult<EditLocationCapacityResult> editLocationCapacity(UserVisitPK userVisitPK, EditLocationCapacityForm form);
    
    CommandResult<?> deleteLocationCapacity(UserVisitPK userVisitPK, DeleteLocationCapacityForm form);
    
    // --------------------------------------------------------------------------------
    //   Location Volumes
    // --------------------------------------------------------------------------------
    
    CommandResult<?> createLocationVolume(UserVisitPK userVisitPK, CreateLocationVolumeForm form);
    
    CommandResult<EditLocationVolumeResult> editLocationVolume(UserVisitPK userVisitPK, EditLocationVolumeForm form);
    
    CommandResult<?> deleteLocationVolume(UserVisitPK userVisitPK, DeleteLocationVolumeForm form);
    
}
