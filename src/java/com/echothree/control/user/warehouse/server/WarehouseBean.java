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

package com.echothree.control.user.warehouse.server;

import com.echothree.control.user.warehouse.common.WarehouseRemote;
import com.echothree.control.user.warehouse.common.form.*;
import com.echothree.control.user.warehouse.common.result.*;
import com.echothree.control.user.warehouse.server.command.*;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.CommandResult;
import com.echothree.util.common.command.VoidResult;
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
    public CommandResult<VoidResult> createLocationUseType(UserVisitPK userVisitPK, CreateLocationUseTypeForm form) {
        return CDI.current().select(CreateLocationUseTypeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<GetLocationUseTypesResult> getLocationUseTypes(UserVisitPK userVisitPK, GetLocationUseTypesForm form) {
        return CDI.current().select(GetLocationUseTypesCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<GetLocationUseTypeResult> getLocationUseType(UserVisitPK userVisitPK, GetLocationUseTypeForm form) {
        return CDI.current().select(GetLocationUseTypeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<GetLocationUseTypeChoicesResult> getLocationUseTypeChoices(UserVisitPK userVisitPK, GetLocationUseTypeChoicesForm form) {
        return CDI.current().select(GetLocationUseTypeChoicesCommand.class).get().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Location Use Type Descriptions
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult<VoidResult> createLocationUseTypeDescription(UserVisitPK userVisitPK, CreateLocationUseTypeDescriptionForm form) {
        return CDI.current().select(CreateLocationUseTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }

    // -------------------------------------------------------------------------
    //   Warehouse Types
    // -------------------------------------------------------------------------

    @Override
    public CommandResult<CreateWarehouseTypeResult> createWarehouseType(UserVisitPK userVisitPK, CreateWarehouseTypeForm form) {
        return CDI.current().select(CreateWarehouseTypeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<GetWarehouseTypesResult> getWarehouseTypes(UserVisitPK userVisitPK, GetWarehouseTypesForm form) {
        return CDI.current().select(GetWarehouseTypesCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<GetWarehouseTypeResult> getWarehouseType(UserVisitPK userVisitPK, GetWarehouseTypeForm form) {
        return CDI.current().select(GetWarehouseTypeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<GetWarehouseTypeChoicesResult> getWarehouseTypeChoices(UserVisitPK userVisitPK, GetWarehouseTypeChoicesForm form) {
        return CDI.current().select(GetWarehouseTypeChoicesCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<VoidResult> setDefaultWarehouseType(UserVisitPK userVisitPK, SetDefaultWarehouseTypeForm form) {
        return CDI.current().select(SetDefaultWarehouseTypeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<EditWarehouseTypeResult> editWarehouseType(UserVisitPK userVisitPK, EditWarehouseTypeForm form) {
        return CDI.current().select(EditWarehouseTypeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<VoidResult> deleteWarehouseType(UserVisitPK userVisitPK, DeleteWarehouseTypeForm form) {
        return CDI.current().select(DeleteWarehouseTypeCommand.class).get().run(userVisitPK, form);
    }

    // -------------------------------------------------------------------------
    //   Warehouse Type Descriptions
    // -------------------------------------------------------------------------

    @Override
    public CommandResult<VoidResult> createWarehouseTypeDescription(UserVisitPK userVisitPK, CreateWarehouseTypeDescriptionForm form) {
        return CDI.current().select(CreateWarehouseTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<GetWarehouseTypeDescriptionsResult> getWarehouseTypeDescriptions(UserVisitPK userVisitPK, GetWarehouseTypeDescriptionsForm form) {
        return CDI.current().select(GetWarehouseTypeDescriptionsCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<GetWarehouseTypeDescriptionResult> getWarehouseTypeDescription(UserVisitPK userVisitPK, GetWarehouseTypeDescriptionForm form) {
        return CDI.current().select(GetWarehouseTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<EditWarehouseTypeDescriptionResult> editWarehouseTypeDescription(UserVisitPK userVisitPK, EditWarehouseTypeDescriptionForm form) {
        return CDI.current().select(EditWarehouseTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<VoidResult> deleteWarehouseTypeDescription(UserVisitPK userVisitPK, DeleteWarehouseTypeDescriptionForm form) {
        return CDI.current().select(DeleteWarehouseTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }

    // -------------------------------------------------------------------------
    //   Warehouses
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult<CreateWarehouseResult> createWarehouse(UserVisitPK userVisitPK, CreateWarehouseForm form) {
        return CDI.current().select(CreateWarehouseCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<GetWarehousesResult> getWarehouses(UserVisitPK userVisitPK, GetWarehousesForm form) {
        return CDI.current().select(GetWarehousesCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<GetWarehouseResult> getWarehouse(UserVisitPK userVisitPK, GetWarehouseForm form) {
        return CDI.current().select(GetWarehouseCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<GetWarehouseChoicesResult> getWarehouseChoices(UserVisitPK userVisitPK, GetWarehouseChoicesForm form) {
        return CDI.current().select(GetWarehouseChoicesCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<VoidResult> setDefaultWarehouse(UserVisitPK userVisitPK, SetDefaultWarehouseForm form) {
        return CDI.current().select(SetDefaultWarehouseCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<EditWarehouseResult> editWarehouse(UserVisitPK userVisitPK, EditWarehouseForm form) {
        return CDI.current().select(EditWarehouseCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<VoidResult> deleteWarehouse(UserVisitPK userVisitPK, DeleteWarehouseForm form) {
        return CDI.current().select(DeleteWarehouseCommand.class).get().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Location Types
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult<CreateLocationTypeResult> createLocationType(UserVisitPK userVisitPK, CreateLocationTypeForm form) {
        return CDI.current().select(CreateLocationTypeCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<GetLocationTypesResult> getLocationTypes(UserVisitPK userVisitPK, GetLocationTypesForm form) {
        return CDI.current().select(GetLocationTypesCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<GetLocationTypeResult> getLocationType(UserVisitPK userVisitPK, GetLocationTypeForm form) {
        return CDI.current().select(GetLocationTypeCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<GetLocationTypeChoicesResult> getLocationTypeChoices(UserVisitPK userVisitPK, GetLocationTypeChoicesForm form) {
        return CDI.current().select(GetLocationTypeChoicesCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<VoidResult> setDefaultLocationType(UserVisitPK userVisitPK, SetDefaultLocationTypeForm form) {
        return CDI.current().select(SetDefaultLocationTypeCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<EditLocationTypeResult> editLocationType(UserVisitPK userVisitPK, EditLocationTypeForm form) {
        return CDI.current().select(EditLocationTypeCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<VoidResult> deleteLocationType(UserVisitPK userVisitPK, DeleteLocationTypeForm form) {
        return CDI.current().select(DeleteLocationTypeCommand.class).get().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Location Type Descriptions
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult<VoidResult> createLocationTypeDescription(UserVisitPK userVisitPK, CreateLocationTypeDescriptionForm form) {
        return CDI.current().select(CreateLocationTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<GetLocationTypeDescriptionsResult> getLocationTypeDescriptions(UserVisitPK userVisitPK, GetLocationTypeDescriptionsForm form) {
        return CDI.current().select(GetLocationTypeDescriptionsCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<EditLocationTypeDescriptionResult> editLocationTypeDescription(UserVisitPK userVisitPK, EditLocationTypeDescriptionForm form) {
        return CDI.current().select(EditLocationTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<VoidResult> deleteLocationTypeDescription(UserVisitPK userVisitPK, DeleteLocationTypeDescriptionForm form) {
        return CDI.current().select(DeleteLocationTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Location Name Elements
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult<CreateLocationNameElementResult> createLocationNameElement(UserVisitPK userVisitPK, CreateLocationNameElementForm form) {
        return CDI.current().select(CreateLocationNameElementCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<GetLocationNameElementsResult> getLocationNameElements(UserVisitPK userVisitPK, GetLocationNameElementsForm form) {
        return CDI.current().select(GetLocationNameElementsCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<GetLocationNameElementResult> getLocationNameElement(UserVisitPK userVisitPK, GetLocationNameElementForm form) {
        return CDI.current().select(GetLocationNameElementCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<EditLocationNameElementResult> editLocationNameElement(UserVisitPK userVisitPK, EditLocationNameElementForm form) {
        return CDI.current().select(EditLocationNameElementCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<VoidResult> deleteLocationNameElement(UserVisitPK userVisitPK, DeleteLocationNameElementForm form) {
        return CDI.current().select(DeleteLocationNameElementCommand.class).get().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Location Name Element Descriptions
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult<VoidResult> createLocationNameElementDescription(UserVisitPK userVisitPK, CreateLocationNameElementDescriptionForm form) {
        return CDI.current().select(CreateLocationNameElementDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<GetLocationNameElementDescriptionsResult> getLocationNameElementDescriptions(UserVisitPK userVisitPK, GetLocationNameElementDescriptionsForm form) {
        return CDI.current().select(GetLocationNameElementDescriptionsCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<EditLocationNameElementDescriptionResult> editLocationNameElementDescription(UserVisitPK userVisitPK, EditLocationNameElementDescriptionForm form) {
        return CDI.current().select(EditLocationNameElementDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<VoidResult> deleteLocationNameElementDescription(UserVisitPK userVisitPK, DeleteLocationNameElementDescriptionForm form) {
        return CDI.current().select(DeleteLocationNameElementDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Locations
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult<CreateLocationResult> createLocation(UserVisitPK userVisitPK, CreateLocationForm form) {
        return CDI.current().select(CreateLocationCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<GetLocationsResult> getLocations(UserVisitPK userVisitPK, GetLocationsForm form) {
        return CDI.current().select(GetLocationsCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<GetLocationResult> getLocation(UserVisitPK userVisitPK, GetLocationForm form) {
        return CDI.current().select(GetLocationCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<GetLocationChoicesResult> getLocationChoices(UserVisitPK userVisitPK, GetLocationChoicesForm form) {
        return CDI.current().select(GetLocationChoicesCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<GetLocationStatusChoicesResult> getLocationStatusChoices(UserVisitPK userVisitPK, GetLocationStatusChoicesForm form) {
        return CDI.current().select(GetLocationStatusChoicesCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<VoidResult> setLocationStatus(UserVisitPK userVisitPK, SetLocationStatusForm form) {
        return CDI.current().select(SetLocationStatusCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<EditLocationResult> editLocation(UserVisitPK userVisitPK, EditLocationForm form) {
        return CDI.current().select(EditLocationCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<VoidResult> deleteLocation(UserVisitPK userVisitPK, DeleteLocationForm form) {
        return CDI.current().select(DeleteLocationCommand.class).get().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Location Descriptions
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult<VoidResult> createLocationDescription(UserVisitPK userVisitPK, CreateLocationDescriptionForm form) {
        return CDI.current().select(CreateLocationDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<GetLocationDescriptionsResult> getLocationDescriptions(UserVisitPK userVisitPK, GetLocationDescriptionsForm form) {
        return CDI.current().select(GetLocationDescriptionsCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<EditLocationDescriptionResult> editLocationDescription(UserVisitPK userVisitPK, EditLocationDescriptionForm form) {
        return CDI.current().select(EditLocationDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<VoidResult> deleteLocationDescription(UserVisitPK userVisitPK, DeleteLocationDescriptionForm form) {
        return CDI.current().select(DeleteLocationDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Location Capacities
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult<VoidResult> createLocationCapacity(UserVisitPK userVisitPK, CreateLocationCapacityForm form) {
        return CDI.current().select(CreateLocationCapacityCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<GetLocationCapacitiesResult> getLocationCapacities(UserVisitPK userVisitPK, GetLocationCapacitiesForm form) {
        return CDI.current().select(GetLocationCapacitiesCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<EditLocationCapacityResult> editLocationCapacity(UserVisitPK userVisitPK, EditLocationCapacityForm form) {
        return CDI.current().select(EditLocationCapacityCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<VoidResult> deleteLocationCapacity(UserVisitPK userVisitPK, DeleteLocationCapacityForm form) {
        return CDI.current().select(DeleteLocationCapacityCommand.class).get().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Location Volumes
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult<VoidResult> createLocationVolume(UserVisitPK userVisitPK, CreateLocationVolumeForm form) {
        return CDI.current().select(CreateLocationVolumeCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<EditLocationVolumeResult> editLocationVolume(UserVisitPK userVisitPK, EditLocationVolumeForm form) {
        return CDI.current().select(EditLocationVolumeCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<VoidResult> deleteLocationVolume(UserVisitPK userVisitPK, DeleteLocationVolumeForm form) {
        return CDI.current().select(DeleteLocationVolumeCommand.class).get().run(userVisitPK, form);
    }
    
}
