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

import com.echothree.control.user.warehouse.common.edit.LocationTypeDescriptionEdit;
import com.echothree.control.user.warehouse.common.edit.WarehouseEditFactory;
import com.echothree.control.user.warehouse.common.result.EditLocationTypeDescriptionResult;
import com.echothree.control.user.warehouse.common.result.WarehouseResultFactory;
import com.echothree.control.user.warehouse.common.spec.LocationTypeDescriptionSpec;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.control.warehouse.server.control.WarehouseControl;
import com.echothree.model.data.warehouse.server.entity.LocationType;
import com.echothree.model.data.warehouse.server.entity.LocationTypeDescription;
import com.echothree.util.common.command.EditMode;
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
public class EditLocationTypeDescriptionCommand
        extends BaseAbstractEditCommand<LocationTypeDescriptionSpec, LocationTypeDescriptionEdit, EditLocationTypeDescriptionResult, LocationTypeDescription, LocationType> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), List.of(
                        new SecurityRoleDefinition(SecurityRoleGroups.LocationType.name(), SecurityRoles.Description.name())
                ))
        ));

        SPEC_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("WarehouseName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("LocationTypeName", FieldType.ENTITY_NAME, true, null, null),
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

    /** Creates a new instance of EditLocationTypeDescriptionCommand */
    public EditLocationTypeDescriptionCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    protected EditLocationTypeDescriptionResult getResult() {
        return WarehouseResultFactory.getEditLocationTypeDescriptionResult();
    }

    @Override
    protected LocationTypeDescriptionEdit getEdit() {
        return WarehouseEditFactory.getLocationTypeDescriptionEdit();
    }

    @Override
    protected LocationTypeDescription getEntity(EditLocationTypeDescriptionResult result) {
        LocationTypeDescription locationTypeDescription = null;
        var warehouseName = spec.getWarehouseName();
        var warehouse = warehouseControl.getWarehouseByName(warehouseName);

        if(warehouse != null) {
            var warehouseParty = warehouse.getParty();
            var locationTypeName = spec.getLocationTypeName();
            var locationType = warehouseControl.getLocationTypeByName(warehouseParty, locationTypeName);

            if(locationType != null) {
                var languageIsoName = spec.getLanguageIsoName();
                var language = partyControl.getLanguageByIsoName(languageIsoName);

                if(language != null) {
                    locationTypeDescription = warehouseControl.getLocationTypeDescription(locationType, language, editModeToEntityPermission(editMode));

                    if(locationTypeDescription == null) {
                        addExecutionError(ExecutionErrors.UnknownLocationTypeDescription.name(),
                                warehouse.getWarehouseName(), locationType.getLastDetail().getLocationTypeName(),
                                language.getLanguageIsoName());
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownLanguageIsoName.name(), languageIsoName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownLocationTypeName.name(), warehouse.getWarehouseName(), locationTypeName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownWarehouseName.name(), warehouseName);
        }

        return locationTypeDescription;
    }

    @Override
    protected LocationType getLockEntity(LocationTypeDescription locationTypeDescription) {
        return locationTypeDescription.getLocationType();
    }

    @Override
    protected void fillInResult(EditLocationTypeDescriptionResult result, LocationTypeDescription locationTypeDescription) {
        result.setLocationTypeDescription(warehouseControl.getLocationTypeDescriptionTransfer(getUserVisit(), locationTypeDescription));
    }

    @Override
    protected void doLock(LocationTypeDescriptionEdit edit, LocationTypeDescription locationTypeDescription) {
        edit.setDescription(locationTypeDescription.getDescription());
    }

    @Override
    protected void doUpdate(LocationTypeDescription locationTypeDescription) {
        var locationTypeDescriptionValue = warehouseControl.getLocationTypeDescriptionValue(locationTypeDescription);

        locationTypeDescriptionValue.setDescription(edit.getDescription());

        warehouseControl.updateLocationTypeDescriptionFromValue(locationTypeDescriptionValue, getPartyPK());
    }
    
}
