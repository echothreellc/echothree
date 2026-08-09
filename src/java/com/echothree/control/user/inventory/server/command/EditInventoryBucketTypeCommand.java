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

import com.echothree.control.user.inventory.common.edit.InventoryBucketTypeEdit;
import com.echothree.control.user.inventory.common.edit.InventoryEditFactory;
import com.echothree.control.user.inventory.common.result.EditInventoryBucketTypeResult;
import com.echothree.control.user.inventory.common.result.InventoryResultFactory;
import com.echothree.control.user.inventory.common.spec.InventoryBucketTypeUniversalSpec;
import com.echothree.model.control.inventory.server.control.InventoryBucketTypeControl;
import com.echothree.model.control.inventory.server.logic.InventoryBucketTypeLogic;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.inventory.server.entity.InventoryBucketType;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseAbstractEditCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import java.util.List;
import javax.inject.Inject;
import javax.enterprise.context.Dependent;

@Dependent
public class EditInventoryBucketTypeCommand
        extends BaseAbstractEditCommand<InventoryBucketTypeUniversalSpec, InventoryBucketTypeEdit, EditInventoryBucketTypeResult, InventoryBucketType, InventoryBucketType> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;

    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), List.of(
                        new SecurityRoleDefinition(SecurityRoleGroups.InventoryBucketType.name(), SecurityRoles.Edit.name())
                ))
        ));

        SPEC_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("InventoryBucketTypeName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("EntityRef", FieldType.ENTITY_REF, false, null, null),
                new FieldDefinition("Uuid", FieldType.UUID, false, null, null)
        );

        EDIT_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("InventoryBucketTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 132L)
        );
    }

    @Inject
    InventoryBucketTypeControl inventoryBucketTypeControl;

    @Inject
    InventoryBucketTypeLogic inventoryBucketTypeLogic;

    /** Creates a new instance of EditInventoryBucketTypeCommand */
    public EditInventoryBucketTypeCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }

    @Override
    public EditInventoryBucketTypeResult getResult() {
        return InventoryResultFactory.getEditInventoryBucketTypeResult();
    }

    @Override
    public InventoryBucketTypeEdit getEdit() {
        return InventoryEditFactory.getInventoryBucketTypeEdit();
    }

    @Override
    public InventoryBucketType getEntity(EditInventoryBucketTypeResult result) {
        var inventoryBucketTypeName = spec.getInventoryBucketTypeName();
        var inventoryBucketType = inventoryBucketTypeLogic.getInventoryBucketTypeByUniversalSpec(this, spec, false,
                editModeToEntityPermission(editMode));

        if(inventoryBucketType != null) {
            result.setInventoryBucketType(inventoryBucketTypeControl.getInventoryBucketTypeTransfer(getUserVisit(), inventoryBucketType));
        } else {
            addExecutionError(ExecutionErrors.UnknownInventoryBucketTypeName.name(), inventoryBucketTypeName);
        }

        return inventoryBucketType;
    }

    @Override
    public InventoryBucketType getLockEntity(InventoryBucketType inventoryBucketType) {
        return inventoryBucketType;
    }

    @Override
    public void fillInResult(EditInventoryBucketTypeResult result, InventoryBucketType inventoryBucketType) {
        result.setInventoryBucketType(inventoryBucketTypeControl.getInventoryBucketTypeTransfer(getUserVisit(), inventoryBucketType));
    }

    @Override
    public void doLock(InventoryBucketTypeEdit edit, InventoryBucketType inventoryBucketType) {
        var inventoryBucketTypeDescription = inventoryBucketTypeControl.getInventoryBucketTypeDescription(inventoryBucketType, getPreferredLanguage());
        var inventoryBucketTypeDetail = inventoryBucketType.getLastDetail();

        edit.setInventoryBucketTypeName(inventoryBucketTypeDetail.getInventoryBucketTypeName());
        edit.setIsDefault(inventoryBucketTypeDetail.getIsDefault().toString());
        edit.setSortOrder(inventoryBucketTypeDetail.getSortOrder().toString());

        if(inventoryBucketTypeDescription != null) {
            edit.setDescription(inventoryBucketTypeDescription.getDescription());
        }
    }

    @Override
    public void canUpdate(InventoryBucketType inventoryBucketType) {
        var inventoryBucketTypeName = edit.getInventoryBucketTypeName();
        var duplicateInventoryBucketType = inventoryBucketTypeControl.getInventoryBucketTypeByName(inventoryBucketTypeName);

        if(duplicateInventoryBucketType != null && !inventoryBucketType.equals(duplicateInventoryBucketType)) {
            addExecutionError(ExecutionErrors.DuplicateInventoryBucketTypeName.name(), inventoryBucketTypeName);
        }
    }

    @Override
    public void doUpdate(InventoryBucketType inventoryBucketType) {
        var partyPK = getPartyPK();
        var inventoryBucketTypeDetailValue = inventoryBucketTypeControl.getInventoryBucketTypeDetailValueForUpdate(inventoryBucketType);
        var inventoryBucketTypeDescription = inventoryBucketTypeControl.getInventoryBucketTypeDescriptionForUpdate(inventoryBucketType, getPreferredLanguage());
        var description = edit.getDescription();

        inventoryBucketTypeDetailValue.setInventoryBucketTypeName(edit.getInventoryBucketTypeName());
        inventoryBucketTypeDetailValue.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
        inventoryBucketTypeDetailValue.setSortOrder(Integer.valueOf(edit.getSortOrder()));

        inventoryBucketTypeLogic.updateInventoryBucketTypeFromValue(inventoryBucketTypeDetailValue, partyPK);

        if(inventoryBucketTypeDescription == null && description != null) {
            inventoryBucketTypeControl.createInventoryBucketTypeDescription(inventoryBucketType, getPreferredLanguage(), description, partyPK);
        } else {
            if(inventoryBucketTypeDescription != null && description == null) {
                inventoryBucketTypeControl.deleteInventoryBucketTypeDescription(inventoryBucketTypeDescription, partyPK);
            } else {
                if(inventoryBucketTypeDescription != null && description != null) {
                    var inventoryBucketTypeDescriptionValue = inventoryBucketTypeControl.getInventoryBucketTypeDescriptionValue(inventoryBucketTypeDescription);

                    inventoryBucketTypeDescriptionValue.setDescription(description);
                    inventoryBucketTypeControl.updateInventoryBucketTypeDescriptionFromValue(inventoryBucketTypeDescriptionValue, partyPK);
                }
            }
        }
    }

}
