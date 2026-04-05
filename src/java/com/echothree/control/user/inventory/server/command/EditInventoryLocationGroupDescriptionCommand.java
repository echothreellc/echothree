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

package com.echothree.control.user.inventory.server.command;

import com.echothree.control.user.inventory.common.edit.InventoryEditFactory;
import com.echothree.control.user.inventory.common.edit.InventoryLocationGroupDescriptionEdit;
import com.echothree.control.user.inventory.common.result.EditInventoryLocationGroupDescriptionResult;
import com.echothree.control.user.inventory.common.result.InventoryResultFactory;
import com.echothree.control.user.inventory.common.spec.InventoryLocationGroupDescriptionSpec;
import com.echothree.model.control.inventory.server.control.InventoryControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.control.warehouse.server.control.WarehouseControl;
import com.echothree.model.data.inventory.server.entity.InventoryLocationGroup;
import com.echothree.model.data.inventory.server.entity.InventoryLocationGroupDescription;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.EditMode;
import com.echothree.util.server.control.BaseAbstractEditCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
import java.util.List;
import javax.inject.Inject;
import javax.enterprise.context.Dependent;

@Dependent
public class EditInventoryLocationGroupDescriptionCommand
        extends BaseAbstractEditCommand<InventoryLocationGroupDescriptionSpec, InventoryLocationGroupDescriptionEdit, EditInventoryLocationGroupDescriptionResult, InventoryLocationGroupDescription, InventoryLocationGroup> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), List.of(
                        new SecurityRoleDefinition(SecurityRoleGroups.InventoryLocationGroup.name(), SecurityRoles.Description.name())
                ))
        ));

        SPEC_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("WarehouseName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("InventoryLocationGroupName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, true, null, null)
        );
        
        EDIT_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("Description", FieldType.STRING, true, 1L, 132L)
        );
    }
    
    /** Creates a new instance of EditInventoryLocationGroupDescriptionCommand */
    public EditInventoryLocationGroupDescriptionCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }

    @Inject
    InventoryControl inventoryControl;

    @Inject
    PartyControl partyControl;

    @Inject
    WarehouseControl warehouseControl;
    
    @Override
    public EditInventoryLocationGroupDescriptionResult getResult() {
        return InventoryResultFactory.getEditInventoryLocationGroupDescriptionResult();
    }

    @Override
    public InventoryLocationGroupDescriptionEdit getEdit() {
        return InventoryEditFactory.getInventoryLocationGroupDescriptionEdit();
    }

    @Override
    public InventoryLocationGroupDescription getEntity(EditInventoryLocationGroupDescriptionResult result) {
        InventoryLocationGroupDescription inventoryLocationGroupDescription = null;
        var warehouseName = spec.getWarehouseName();
        var warehouse = warehouseControl.getWarehouseByName(warehouseName);

        if(warehouse != null) {
            var warehouseParty = warehouse.getParty();
            var inventoryLocationGroupName = spec.getInventoryLocationGroupName();
            var inventoryLocationGroup = inventoryControl.getInventoryLocationGroupByName(warehouseParty, inventoryLocationGroupName);

            if(inventoryLocationGroup != null) {
                var languageIsoName = spec.getLanguageIsoName();
                var language = partyControl.getLanguageByIsoName(languageIsoName);

                if(language != null) {
                    if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
                        inventoryLocationGroupDescription = inventoryControl.getInventoryLocationGroupDescription(inventoryLocationGroup, language);
                    } else { // EditMode.UPDATE
                        inventoryLocationGroupDescription = inventoryControl.getInventoryLocationGroupDescriptionForUpdate(inventoryLocationGroup, language);
                    }

                    if(inventoryLocationGroupDescription == null) {
                        addExecutionError(ExecutionErrors.UnknownInventoryLocationGroupDescription.name(), warehouseName, inventoryLocationGroupName, languageIsoName);
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownLanguageIsoName.name(), languageIsoName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownInventoryLocationGroupName.name(), warehouseName, inventoryLocationGroupName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownWarehouseName.name(), warehouseName);
        }

        return inventoryLocationGroupDescription;
    }

    @Override
    public InventoryLocationGroup getLockEntity(InventoryLocationGroupDescription inventoryLocationGroupDescription) {
        return inventoryLocationGroupDescription.getInventoryLocationGroup();
    }

    @Override
    public void fillInResult(EditInventoryLocationGroupDescriptionResult result, InventoryLocationGroupDescription inventoryLocationGroupDescription) {
        result.setInventoryLocationGroupDescription(inventoryControl.getInventoryLocationGroupDescriptionTransfer(getUserVisit(), inventoryLocationGroupDescription));
    }

    @Override
    public void doLock(InventoryLocationGroupDescriptionEdit edit, InventoryLocationGroupDescription inventoryLocationGroupDescription) {
        edit.setDescription(inventoryLocationGroupDescription.getDescription());
    }

    @Override
    public void doUpdate(InventoryLocationGroupDescription inventoryLocationGroupDescription) {
        var inventoryLocationGroupDescriptionValue = inventoryControl.getInventoryLocationGroupDescriptionValue(inventoryLocationGroupDescription);

        inventoryLocationGroupDescriptionValue.setDescription(edit.getDescription());

        inventoryControl.updateInventoryLocationGroupDescriptionFromValue(inventoryLocationGroupDescriptionValue, getPartyPK());
    }
    
}
