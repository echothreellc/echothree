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
import com.echothree.control.user.inventory.common.edit.InventoryTransactionRoleTypeEdit;
import com.echothree.control.user.inventory.common.result.EditInventoryTransactionRoleTypeResult;
import com.echothree.control.user.inventory.common.result.InventoryResultFactory;
import com.echothree.control.user.inventory.common.spec.InventoryTransactionRoleTypeUniversalSpec;
import com.echothree.model.control.inventory.server.control.InventoryTransactionRoleControl;
import com.echothree.model.control.inventory.server.control.InventoryTransactionTypeControl;
import com.echothree.model.control.inventory.server.logic.InventoryTransactionRoleTypeLogic;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.inventory.server.entity.InventoryTransactionRoleType;
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
public class EditInventoryTransactionRoleTypeCommand
        extends BaseAbstractEditCommand<InventoryTransactionRoleTypeUniversalSpec, InventoryTransactionRoleTypeEdit,
                EditInventoryTransactionRoleTypeResult, InventoryTransactionRoleType, InventoryTransactionRoleType> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), List.of(
                        new SecurityRoleDefinition(SecurityRoleGroups.InventoryTransactionRoleType.name(), SecurityRoles.Edit.name())
                ))
        ));
        
        SPEC_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("InventoryTransactionTypeName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("InventoryTransactionRoleTypeName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("EntityRef", FieldType.ENTITY_REF, false, null, null),
                new FieldDefinition("Uuid", FieldType.UUID, false, null, null)
        );
        
        EDIT_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("InventoryTransactionRoleTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 132L)
        );
    }

    @Inject
    InventoryTransactionRoleControl inventoryTransactionRoleControl;

    @Inject
    InventoryTransactionTypeControl inventoryTransactionTypeControl;

    @Inject
    InventoryTransactionRoleTypeLogic inventoryTransactionRoleTypeLogic;

    
    /** Creates a new instance of EditInventoryTransactionRoleTypeCommand */
    public EditInventoryTransactionRoleTypeCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }

    @Override
    public EditInventoryTransactionRoleTypeResult getResult() {
        return InventoryResultFactory.getEditInventoryTransactionRoleTypeResult();
    }

    @Override
    public InventoryTransactionRoleTypeEdit getEdit() {
        return InventoryEditFactory.getInventoryTransactionRoleTypeEdit();
    }

    @Override
    public InventoryTransactionRoleType getEntity(EditInventoryTransactionRoleTypeResult result) {
        return inventoryTransactionRoleTypeLogic.getInventoryTransactionRoleTypeByUniversalSpec(this, spec, false,
                editModeToEntityPermission(editMode));
    }

    @Override
    public InventoryTransactionRoleType getLockEntity(InventoryTransactionRoleType inventoryTransactionRoleType) {
        return inventoryTransactionRoleType;
    }

    @Override
    public void fillInResult(EditInventoryTransactionRoleTypeResult result, InventoryTransactionRoleType inventoryTransactionRoleType) {
        result.setInventoryTransactionRoleType(inventoryTransactionRoleControl.getInventoryTransactionRoleTypeTransfer(getUserVisit(),
                inventoryTransactionRoleType));
    }

    @Override
    public void doLock(InventoryTransactionRoleTypeEdit edit, InventoryTransactionRoleType inventoryTransactionRoleType) {
        var inventoryTransactionRoleTypeDescription = 
                inventoryTransactionRoleControl.getInventoryTransactionRoleTypeDescription(inventoryTransactionRoleType, getPreferredLanguage());
        var inventoryTransactionRoleTypeDetail = inventoryTransactionRoleType.getLastDetail();

        edit.setInventoryTransactionRoleTypeName(inventoryTransactionRoleTypeDetail.getInventoryTransactionRoleTypeName());
        edit.setIsDefault(inventoryTransactionRoleTypeDetail.getIsDefault().toString());
        edit.setSortOrder(inventoryTransactionRoleTypeDetail.getSortOrder().toString());

        if(inventoryTransactionRoleTypeDescription != null) {
            edit.setDescription(inventoryTransactionRoleTypeDescription.getDescription());
        }
    }

    @Override
    public void canUpdate(InventoryTransactionRoleType inventoryTransactionRoleType) {
        var inventoryTransactionType = inventoryTransactionRoleType.getLastDetail().getInventoryTransactionType();
        var inventoryTransactionRoleTypeName = edit.getInventoryTransactionRoleTypeName();
        var duplicateInventoryTransactionRoleType =
                inventoryTransactionRoleControl.getInventoryTransactionRoleTypeByName(inventoryTransactionType, inventoryTransactionRoleTypeName);

        if(duplicateInventoryTransactionRoleType != null && !inventoryTransactionRoleType.equals(duplicateInventoryTransactionRoleType)) {
            addExecutionError(ExecutionErrors.DuplicateInventoryTransactionRoleTypeName.name(),
                    inventoryTransactionType.getLastDetail().getInventoryTransactionTypeName(),
                    inventoryTransactionRoleTypeName);
        }
    }

    @Override
    public void doUpdate(InventoryTransactionRoleType inventoryTransactionRoleType) {
        var partyPK = getPartyPK();
        var inventoryTransactionRoleTypeDetailValue = 
                inventoryTransactionRoleControl.getInventoryTransactionRoleTypeDetailValueForUpdate(inventoryTransactionRoleType);
        var inventoryTransactionRoleTypeDescription = 
                inventoryTransactionRoleControl.getInventoryTransactionRoleTypeDescriptionForUpdate(
                        inventoryTransactionRoleType, getPreferredLanguage());
        var description = edit.getDescription();

        inventoryTransactionRoleTypeDetailValue.setInventoryTransactionRoleTypeName(edit.getInventoryTransactionRoleTypeName());
        inventoryTransactionRoleTypeDetailValue.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
        inventoryTransactionRoleTypeDetailValue.setSortOrder(Integer.valueOf(edit.getSortOrder()));

        inventoryTransactionRoleControl.updateInventoryTransactionRoleTypeFromValue(inventoryTransactionRoleTypeDetailValue, partyPK);

        if(inventoryTransactionRoleTypeDescription == null && description != null) {
            inventoryTransactionRoleControl.createInventoryTransactionRoleTypeDescription(inventoryTransactionRoleType, getPreferredLanguage(),
                    description, partyPK);
        } else {
            if(inventoryTransactionRoleTypeDescription != null && description == null) {
                inventoryTransactionRoleControl.deleteInventoryTransactionRoleTypeDescription(inventoryTransactionRoleTypeDescription, partyPK);
            } else {
                if(inventoryTransactionRoleTypeDescription != null && description != null) {
                    var inventoryTransactionRoleTypeDescriptionValue = 
                            inventoryTransactionRoleControl.getInventoryTransactionRoleTypeDescriptionValue(inventoryTransactionRoleTypeDescription);

                    inventoryTransactionRoleTypeDescriptionValue.setDescription(description);
                    inventoryTransactionRoleControl.updateInventoryTransactionRoleTypeDescriptionFromValue(
                            inventoryTransactionRoleTypeDescriptionValue, partyPK);
                }
            }
        }
    }

}
