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
import com.echothree.control.user.inventory.common.edit.InventoryDispositionEdit;
import com.echothree.control.user.inventory.common.form.EditInventoryDispositionForm;
import com.echothree.control.user.inventory.common.result.EditInventoryDispositionResult;
import com.echothree.control.user.inventory.common.result.InventoryResultFactory;
import com.echothree.control.user.inventory.common.spec.InventoryDispositionUniversalSpec;
import com.echothree.model.control.inventory.server.control.InventoryDispositionControl;
import com.echothree.model.control.inventory.server.control.InventoryTransactionTypeControl;
import com.echothree.model.control.inventory.server.logic.InventoryDispositionLogic;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.inventory.server.entity.InventoryDisposition;
import com.echothree.model.data.user.common.pk.UserVisitPK;
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
public class EditInventoryDispositionCommand
        extends BaseAbstractEditCommand<InventoryDispositionUniversalSpec, InventoryDispositionEdit,
                EditInventoryDispositionResult, InventoryDisposition, InventoryDisposition> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), List.of(
                        new SecurityRoleDefinition(SecurityRoleGroups.InventoryDisposition.name(), SecurityRoles.Edit.name())
                ))
        ));
        
        SPEC_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("InventoryTransactionTypeName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("InventoryDispositionName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("EntityRef", FieldType.ENTITY_REF, false, null, null),
                new FieldDefinition("Uuid", FieldType.UUID, false, null, null)
        );
        
        EDIT_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("InventoryDispositionName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 132L)
        );
    }

    @Inject
    InventoryDispositionControl inventoryDispositionControl;

    @Inject
    InventoryTransactionTypeControl inventoryTransactionTypeControl;

    @Inject
    InventoryDispositionLogic inventoryDispositionLogic;

    
    /** Creates a new instance of EditInventoryDispositionCommand */
    public EditInventoryDispositionCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }

    @Override
    public EditInventoryDispositionResult getResult() {
        return InventoryResultFactory.getEditInventoryDispositionResult();
    }

    @Override
    public InventoryDispositionEdit getEdit() {
        return InventoryEditFactory.getInventoryDispositionEdit();
    }

    @Override
    public InventoryDisposition getEntity(EditInventoryDispositionResult result) {
        return inventoryDispositionLogic.getInventoryDispositionByUniversalSpec(this, spec, false,
                editModeToEntityPermission(editMode));
    }

    @Override
    public InventoryDisposition getLockEntity(InventoryDisposition inventoryDisposition) {
        return inventoryDisposition;
    }

    @Override
    public void fillInResult(EditInventoryDispositionResult result, InventoryDisposition inventoryDisposition) {
        result.setInventoryDisposition(inventoryDispositionControl.getInventoryDispositionTransfer(getUserVisit(),
                inventoryDisposition));
    }

    @Override
    public void doLock(InventoryDispositionEdit edit, InventoryDisposition inventoryDisposition) {
        var inventoryDispositionDescription = 
                inventoryDispositionControl.getInventoryDispositionDescription(inventoryDisposition, getPreferredLanguage());
        var inventoryDispositionDetail = inventoryDisposition.getLastDetail();

        edit.setInventoryDispositionName(inventoryDispositionDetail.getInventoryDispositionName());
        edit.setIsDefault(inventoryDispositionDetail.getIsDefault().toString());
        edit.setSortOrder(inventoryDispositionDetail.getSortOrder().toString());

        if(inventoryDispositionDescription != null) {
            edit.setDescription(inventoryDispositionDescription.getDescription());
        }
    }

    @Override
    public void canUpdate(InventoryDisposition inventoryDisposition) {
        var inventoryTransactionTypeName = spec.getInventoryTransactionTypeName();
        var inventoryTransactionType = inventoryTransactionTypeControl.getInventoryTransactionTypeByName(inventoryTransactionTypeName);

        if(inventoryTransactionType != null) {
            var inventoryDispositionName = edit.getInventoryDispositionName();
            var duplicateInventoryDisposition = 
                    inventoryDispositionControl.getInventoryDispositionByName(inventoryTransactionType, inventoryDispositionName);

            if(duplicateInventoryDisposition != null && !inventoryDisposition.equals(duplicateInventoryDisposition)) {
                addExecutionError(ExecutionErrors.DuplicateInventoryDispositionName.name(), inventoryTransactionTypeName,
                        inventoryDispositionName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownInventoryTransactionTypeName.name(), inventoryTransactionTypeName);
        }
    }

    @Override
    public void doUpdate(InventoryDisposition inventoryDisposition) {
        var partyPK = getPartyPK();
        var inventoryDispositionDetailValue = 
                inventoryDispositionControl.getInventoryDispositionDetailValueForUpdate(inventoryDisposition);
        var inventoryDispositionDescription = 
                inventoryDispositionControl.getInventoryDispositionDescriptionForUpdate(
                        inventoryDisposition, getPreferredLanguage());
        var description = edit.getDescription();

        inventoryDispositionDetailValue.setInventoryDispositionName(edit.getInventoryDispositionName());
        inventoryDispositionDetailValue.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
        inventoryDispositionDetailValue.setSortOrder(Integer.valueOf(edit.getSortOrder()));

        inventoryDispositionControl.updateInventoryDispositionFromValue(inventoryDispositionDetailValue, partyPK);

        if(inventoryDispositionDescription == null && description != null) {
            inventoryDispositionControl.createInventoryDispositionDescription(inventoryDisposition, getPreferredLanguage(),
                    description, partyPK);
        } else {
            if(inventoryDispositionDescription != null && description == null) {
                inventoryDispositionControl.deleteInventoryDispositionDescription(inventoryDispositionDescription, partyPK);
            } else {
                if(inventoryDispositionDescription != null && description != null) {
                    var inventoryDispositionDescriptionValue = 
                            inventoryDispositionControl.getInventoryDispositionDescriptionValue(inventoryDispositionDescription);

                    inventoryDispositionDescriptionValue.setDescription(description);
                    inventoryDispositionControl.updateInventoryDispositionDescriptionFromValue(
                            inventoryDispositionDescriptionValue, partyPK);
                }
            }
        }
    }

}
