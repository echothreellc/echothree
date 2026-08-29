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
import com.echothree.control.user.inventory.common.edit.InventoryTransactionTimeTypeEdit;
import com.echothree.control.user.inventory.common.form.EditInventoryTransactionTimeTypeForm;
import com.echothree.control.user.inventory.common.result.EditInventoryTransactionTimeTypeResult;
import com.echothree.control.user.inventory.common.result.InventoryResultFactory;
import com.echothree.control.user.inventory.common.spec.InventoryTransactionTimeTypeUniversalSpec;
import com.echothree.model.control.inventory.server.control.InventoryTransactionTimeControl;
import com.echothree.model.control.inventory.server.control.InventoryTransactionTypeControl;
import com.echothree.model.control.inventory.server.logic.InventoryTransactionTimeTypeLogic;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.inventory.server.entity.InventoryTransactionTimeType;
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
public class EditInventoryTransactionTimeTypeCommand
        extends BaseAbstractEditCommand<InventoryTransactionTimeTypeUniversalSpec, InventoryTransactionTimeTypeEdit,
                EditInventoryTransactionTimeTypeResult, InventoryTransactionTimeType, InventoryTransactionTimeType> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), List.of(
                        new SecurityRoleDefinition(SecurityRoleGroups.InventoryTransactionTimeType.name(), SecurityRoles.Edit.name())
                ))
        ));
        
        SPEC_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("InventoryTransactionTypeName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("InventoryTransactionTimeTypeName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("EntityRef", FieldType.ENTITY_REF, false, null, null),
                new FieldDefinition("Uuid", FieldType.UUID, false, null, null)
        );
        
        EDIT_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("InventoryTransactionTimeTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 132L)
        );
    }

    @Inject
    InventoryTransactionTimeControl inventoryTransactionTimeControl;

    @Inject
    InventoryTransactionTypeControl inventoryTransactionTypeControl;

    @Inject
    InventoryTransactionTimeTypeLogic inventoryTransactionTimeTypeLogic;

    
    /** Creates a new instance of EditInventoryTransactionTimeTypeCommand */
    public EditInventoryTransactionTimeTypeCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }

    @Override
    public EditInventoryTransactionTimeTypeResult getResult() {
        return InventoryResultFactory.getEditInventoryTransactionTimeTypeResult();
    }

    @Override
    public InventoryTransactionTimeTypeEdit getEdit() {
        return InventoryEditFactory.getInventoryTransactionTimeTypeEdit();
    }

    @Override
    public InventoryTransactionTimeType getEntity(EditInventoryTransactionTimeTypeResult result) {
        return inventoryTransactionTimeTypeLogic.getInventoryTransactionTimeTypeByUniversalSpec(this, spec, false,
                editModeToEntityPermission(editMode));
    }

    @Override
    public InventoryTransactionTimeType getLockEntity(InventoryTransactionTimeType inventoryTransactionTimeType) {
        return inventoryTransactionTimeType;
    }

    @Override
    public void fillInResult(EditInventoryTransactionTimeTypeResult result, InventoryTransactionTimeType inventoryTransactionTimeType) {
        result.setInventoryTransactionTimeType(inventoryTransactionTimeControl.getInventoryTransactionTimeTypeTransfer(getUserVisit(),
                inventoryTransactionTimeType));
    }

    @Override
    public void doLock(InventoryTransactionTimeTypeEdit edit, InventoryTransactionTimeType inventoryTransactionTimeType) {
        var inventoryTransactionTimeTypeDescription = 
                inventoryTransactionTimeControl.getInventoryTransactionTimeTypeDescription(inventoryTransactionTimeType, getPreferredLanguage());
        var inventoryTransactionTimeTypeDetail = inventoryTransactionTimeType.getLastDetail();

        edit.setInventoryTransactionTimeTypeName(inventoryTransactionTimeTypeDetail.getInventoryTransactionTimeTypeName());
        edit.setIsDefault(inventoryTransactionTimeTypeDetail.getIsDefault().toString());
        edit.setSortOrder(inventoryTransactionTimeTypeDetail.getSortOrder().toString());

        if(inventoryTransactionTimeTypeDescription != null) {
            edit.setDescription(inventoryTransactionTimeTypeDescription.getDescription());
        }
    }

    @Override
    public void canUpdate(InventoryTransactionTimeType inventoryTransactionTimeType) {
        var inventoryTransactionTypeName = spec.getInventoryTransactionTypeName();
        var inventoryTransactionType = inventoryTransactionTypeControl.getInventoryTransactionTypeByName(inventoryTransactionTypeName);

        if(inventoryTransactionType != null) {
            var inventoryTransactionTimeTypeName = edit.getInventoryTransactionTimeTypeName();
            var duplicateInventoryTransactionTimeType = 
                    inventoryTransactionTimeControl.getInventoryTransactionTimeTypeByName(inventoryTransactionType, inventoryTransactionTimeTypeName);

            if(duplicateInventoryTransactionTimeType != null && !inventoryTransactionTimeType.equals(duplicateInventoryTransactionTimeType)) {
                addExecutionError(ExecutionErrors.DuplicateInventoryTransactionTimeTypeName.name(), inventoryTransactionTypeName,
                        inventoryTransactionTimeTypeName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownInventoryTransactionTypeName.name(), inventoryTransactionTypeName);
        }
    }

    @Override
    public void doUpdate(InventoryTransactionTimeType inventoryTransactionTimeType) {
        var partyPK = getPartyPK();
        var inventoryTransactionTimeTypeDetailValue = 
                inventoryTransactionTimeControl.getInventoryTransactionTimeTypeDetailValueForUpdate(inventoryTransactionTimeType);
        var inventoryTransactionTimeTypeDescription = 
                inventoryTransactionTimeControl.getInventoryTransactionTimeTypeDescriptionForUpdate(
                        inventoryTransactionTimeType, getPreferredLanguage());
        var description = edit.getDescription();

        inventoryTransactionTimeTypeDetailValue.setInventoryTransactionTimeTypeName(edit.getInventoryTransactionTimeTypeName());
        inventoryTransactionTimeTypeDetailValue.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
        inventoryTransactionTimeTypeDetailValue.setSortOrder(Integer.valueOf(edit.getSortOrder()));

        inventoryTransactionTimeControl.updateInventoryTransactionTimeTypeFromValue(inventoryTransactionTimeTypeDetailValue, partyPK);

        if(inventoryTransactionTimeTypeDescription == null && description != null) {
            inventoryTransactionTimeControl.createInventoryTransactionTimeTypeDescription(inventoryTransactionTimeType, getPreferredLanguage(),
                    description, partyPK);
        } else {
            if(inventoryTransactionTimeTypeDescription != null && description == null) {
                inventoryTransactionTimeControl.deleteInventoryTransactionTimeTypeDescription(inventoryTransactionTimeTypeDescription, partyPK);
            } else {
                if(inventoryTransactionTimeTypeDescription != null && description != null) {
                    var inventoryTransactionTimeTypeDescriptionValue = 
                            inventoryTransactionTimeControl.getInventoryTransactionTimeTypeDescriptionValue(inventoryTransactionTimeTypeDescription);

                    inventoryTransactionTimeTypeDescriptionValue.setDescription(description);
                    inventoryTransactionTimeControl.updateInventoryTransactionTimeTypeDescriptionFromValue(
                            inventoryTransactionTimeTypeDescriptionValue, partyPK);
                }
            }
        }
    }

}
