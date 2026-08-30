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

import com.echothree.control.user.inventory.common.edit.InventoryTransactionReasonEdit;
import com.echothree.control.user.inventory.common.edit.InventoryEditFactory;
import com.echothree.control.user.inventory.common.result.EditInventoryTransactionReasonResult;
import com.echothree.control.user.inventory.common.result.InventoryResultFactory;
import com.echothree.control.user.inventory.common.spec.InventoryTransactionReasonUniversalSpec;
import com.echothree.model.control.inventory.server.control.InventoryTransactionReasonControl;
import com.echothree.model.control.inventory.server.control.InventoryTransactionTypeControl;
import com.echothree.model.control.inventory.server.logic.InventoryTransactionReasonLogic;
import com.echothree.model.control.inventory.server.logic.InventoryDispositionLogic;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.inventory.server.entity.InventoryTransactionReason;
import com.echothree.model.data.inventory.server.entity.InventoryDisposition;
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
public class EditInventoryTransactionReasonCommand
        extends BaseAbstractEditCommand<InventoryTransactionReasonUniversalSpec, InventoryTransactionReasonEdit,
                EditInventoryTransactionReasonResult, InventoryTransactionReason, InventoryTransactionReason> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), List.of(
                        new SecurityRoleDefinition(SecurityRoleGroups.InventoryTransactionReason.name(), SecurityRoles.Edit.name())
                ))
        ));
        
        SPEC_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("InventoryTransactionTypeName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("InventoryTransactionReasonName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("EntityRef", FieldType.ENTITY_REF, false, null, null),
                new FieldDefinition("Uuid", FieldType.UUID, false, null, null)
        );
        
        EDIT_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("InventoryTransactionReasonName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("InventoryDispositionName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 132L)
        );
    }

    @Inject
    InventoryTransactionReasonControl inventoryTransactionReasonControl;

    @Inject
    InventoryTransactionTypeControl inventoryTransactionTypeControl;

    @Inject
    InventoryTransactionReasonLogic inventoryTransactionReasonLogic;

    @Inject
    InventoryDispositionLogic inventoryDispositionLogic;

    
    /** Creates a new instance of EditInventoryTransactionReasonCommand */
    public EditInventoryTransactionReasonCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }

    @Override
    public EditInventoryTransactionReasonResult getResult() {
        return InventoryResultFactory.getEditInventoryTransactionReasonResult();
    }

    @Override
    public InventoryTransactionReasonEdit getEdit() {
        return InventoryEditFactory.getInventoryTransactionReasonEdit();
    }

    @Override
    public InventoryTransactionReason getEntity(EditInventoryTransactionReasonResult result) {
        return inventoryTransactionReasonLogic.getInventoryTransactionReasonByUniversalSpec(this, spec, false,
                editModeToEntityPermission(editMode));
    }

    @Override
    public InventoryTransactionReason getLockEntity(InventoryTransactionReason inventoryTransactionReason) {
        return inventoryTransactionReason;
    }

    @Override
    public void fillInResult(EditInventoryTransactionReasonResult result, InventoryTransactionReason inventoryTransactionReason) {
        result.setInventoryTransactionReason(inventoryTransactionReasonControl.getInventoryTransactionReasonTransfer(getUserVisit(),
                inventoryTransactionReason));
    }

    @Override
    public void doLock(InventoryTransactionReasonEdit edit, InventoryTransactionReason inventoryTransactionReason) {
        var inventoryTransactionReasonDescription = 
                inventoryTransactionReasonControl.getInventoryTransactionReasonDescription(inventoryTransactionReason, getPreferredLanguage());
        var inventoryTransactionReasonDetail = inventoryTransactionReason.getLastDetail();

        edit.setInventoryTransactionReasonName(inventoryTransactionReasonDetail.getInventoryTransactionReasonName());
        edit.setInventoryDispositionName(inventoryTransactionReasonDetail.getInventoryDisposition().getLastDetail().getInventoryDispositionName());
        edit.setIsDefault(inventoryTransactionReasonDetail.getIsDefault().toString());
        edit.setSortOrder(inventoryTransactionReasonDetail.getSortOrder().toString());

        if(inventoryTransactionReasonDescription != null) {
            edit.setDescription(inventoryTransactionReasonDescription.getDescription());
        }
    }

    InventoryDisposition inventoryDisposition;

    @Override
    public void canUpdate(InventoryTransactionReason inventoryTransactionReason) {
        var inventoryTransactionType = inventoryTransactionReason.getLastDetail().getInventoryTransactionType();
        var inventoryTransactionReasonName = edit.getInventoryTransactionReasonName();
        var duplicateInventoryTransactionReason =
                inventoryTransactionReasonControl.getInventoryTransactionReasonByName(inventoryTransactionType, inventoryTransactionReasonName);

        if(duplicateInventoryTransactionReason != null && !inventoryTransactionReason.equals(duplicateInventoryTransactionReason)) {
            addExecutionError(ExecutionErrors.DuplicateInventoryTransactionReasonName.name(),
                    inventoryTransactionType.getLastDetail().getInventoryTransactionTypeName(),
                    inventoryTransactionReasonName);
        }

        inventoryDisposition = inventoryDispositionLogic.getInventoryDispositionByName(this, inventoryTransactionType,
                edit.getInventoryDispositionName());
    }

    @Override
    public void doUpdate(InventoryTransactionReason inventoryTransactionReason) {
        var partyPK = getPartyPK();
        var inventoryTransactionReasonDetailValue = 
                inventoryTransactionReasonControl.getInventoryTransactionReasonDetailValueForUpdate(inventoryTransactionReason);
        var inventoryTransactionReasonDescription = 
                inventoryTransactionReasonControl.getInventoryTransactionReasonDescriptionForUpdate(
                        inventoryTransactionReason, getPreferredLanguage());
        var description = edit.getDescription();

        inventoryTransactionReasonDetailValue.setInventoryTransactionReasonName(edit.getInventoryTransactionReasonName());
        inventoryTransactionReasonDetailValue.setInventoryDispositionPK(inventoryDisposition.getPrimaryKey());
        inventoryTransactionReasonDetailValue.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
        inventoryTransactionReasonDetailValue.setSortOrder(Integer.valueOf(edit.getSortOrder()));

        inventoryTransactionReasonControl.updateInventoryTransactionReasonFromValue(inventoryTransactionReasonDetailValue, partyPK);

        if(inventoryTransactionReasonDescription == null && description != null) {
            inventoryTransactionReasonControl.createInventoryTransactionReasonDescription(inventoryTransactionReason, getPreferredLanguage(),
                    description, partyPK);
        } else {
            if(inventoryTransactionReasonDescription != null && description == null) {
                inventoryTransactionReasonControl.deleteInventoryTransactionReasonDescription(inventoryTransactionReasonDescription, partyPK);
            } else {
                if(inventoryTransactionReasonDescription != null && description != null) {
                    var inventoryTransactionReasonDescriptionValue = 
                            inventoryTransactionReasonControl.getInventoryTransactionReasonDescriptionValue(inventoryTransactionReasonDescription);

                    inventoryTransactionReasonDescriptionValue.setDescription(description);
                    inventoryTransactionReasonControl.updateInventoryTransactionReasonDescriptionFromValue(
                            inventoryTransactionReasonDescriptionValue, partyPK);
                }
            }
        }
    }

}
