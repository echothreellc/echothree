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

import com.echothree.control.user.warehouse.common.edit.LocationDescriptionEdit;
import com.echothree.control.user.warehouse.common.edit.WarehouseEditFactory;
import com.echothree.control.user.warehouse.common.result.EditLocationDescriptionResult;
import com.echothree.control.user.warehouse.common.result.WarehouseResultFactory;
import com.echothree.control.user.warehouse.common.spec.LocationDescriptionSpec;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.control.warehouse.server.control.WarehouseControl;
import com.echothree.model.data.warehouse.server.entity.Location;
import com.echothree.model.data.warehouse.server.entity.LocationDescription;
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
public class EditLocationDescriptionCommand
        extends BaseAbstractEditCommand<LocationDescriptionSpec, LocationDescriptionEdit, EditLocationDescriptionResult, LocationDescription, Location> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), List.of(
                        new SecurityRoleDefinition(SecurityRoleGroups.Location.name(), SecurityRoles.Description.name())
                ))
        ));

        SPEC_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("WarehouseName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("LocationName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, true, null, null)
        );
        
        EDIT_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("Description", FieldType.STRING, true, 1L, 132L)
        );
    }

    @Inject
    PartyControl partyControl;

    @Inject
    WarehouseControl warehouseControl;

    /** Creates a new instance of EditLocationDescriptionCommand */
    public EditLocationDescriptionCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }

    @Override
    public EditLocationDescriptionResult getResult() {
        return WarehouseResultFactory.getEditLocationDescriptionResult();
    }

    @Override
    public LocationDescriptionEdit getEdit() {
        return WarehouseEditFactory.getLocationDescriptionEdit();
    }

    @Override
    public LocationDescription getEntity(EditLocationDescriptionResult result) {
        LocationDescription locationDescription = null;
        var warehouseName = spec.getWarehouseName();
        var warehouse = warehouseControl.getWarehouseByName(warehouseName);

        if(warehouse != null) {
            var warehouseParty = warehouse.getParty();
            var locationName = spec.getLocationName();
            var location = warehouseControl.getLocationByName(warehouseParty, locationName);

            if(location != null) {
                var languageIsoName = spec.getLanguageIsoName();
                var language = partyControl.getLanguageByIsoName(languageIsoName);

                if(language != null) {
                    locationDescription = warehouseControl.getLocationDescription(location, language, editModeToEntityPermission(editMode));

                    if(locationDescription == null) {
                        addExecutionError(ExecutionErrors.UnknownLocationDescription.name(),
                                warehouse.getWarehouseName(), location.getLastDetail().getLocationName(),
                                language.getLanguageIsoName());
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownLanguageIsoName.name(), languageIsoName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownLocationName.name(), warehouse.getWarehouseName(), locationName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownWarehouseName.name(), warehouseName);
        }

        return locationDescription;
    }

    @Override
    public Location getLockEntity(LocationDescription locationDescription) {
        return locationDescription.getLocation();
    }

    @Override
    public void fillInResult(EditLocationDescriptionResult result, LocationDescription locationDescription) {
        result.setLocationDescription(warehouseControl.getLocationDescriptionTransfer(getUserVisit(), locationDescription));
    }

    @Override
    public void doLock(LocationDescriptionEdit edit, LocationDescription locationDescription) {
        edit.setDescription(locationDescription.getDescription());
    }

    @Override
    public void doUpdate(LocationDescription locationDescription) {
        var locationDescriptionValue = warehouseControl.getLocationDescriptionValue(locationDescription);

        locationDescriptionValue.setDescription(edit.getDescription());

        warehouseControl.updateLocationDescriptionFromValue(locationDescriptionValue, getPartyPK());
    }
    
}
