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

import com.echothree.control.user.inventory.common.edit.InventoryDispositionAdjustmentEdit;
import com.echothree.control.user.inventory.common.edit.InventoryEditFactory;
import com.echothree.control.user.inventory.common.result.EditInventoryDispositionAdjustmentResult;
import com.echothree.control.user.inventory.common.result.InventoryResultFactory;
import com.echothree.control.user.inventory.common.spec.InventoryDispositionAdjustmentUniversalSpec;
import com.echothree.model.control.inventory.server.control.InventoryDispositionAdjustmentControl;
import com.echothree.model.control.inventory.server.control.InventoryTransactionTypeControl;
import com.echothree.model.control.inventory.server.logic.InventoryDispositionAdjustmentLogic;
import com.echothree.model.control.inventory.server.logic.InventoryDispositionLogic;
import com.echothree.model.control.inventory.server.logic.InventoryAdjustmentTypeLogic;
import com.echothree.model.control.inventory.server.logic.InventoryBucketTypeLogic;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.inventory.server.entity.InventoryDispositionAdjustment;
import com.echothree.model.data.inventory.server.entity.InventoryDisposition;
import com.echothree.model.data.inventory.server.entity.InventoryAdjustmentType;
import com.echothree.model.data.inventory.server.entity.InventoryBucketType;
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
public class EditInventoryDispositionAdjustmentCommand
        extends BaseAbstractEditCommand<InventoryDispositionAdjustmentUniversalSpec, InventoryDispositionAdjustmentEdit,
                EditInventoryDispositionAdjustmentResult, InventoryDispositionAdjustment, InventoryDispositionAdjustment> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), List.of(
                        new SecurityRoleDefinition(SecurityRoleGroups.InventoryDispositionAdjustment.name(), SecurityRoles.Edit.name())
                ))
        ));
        
        SPEC_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("InventoryTransactionTypeName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("InventoryDispositionName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("InventoryDispositionAdjustmentName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("EntityRef", FieldType.ENTITY_REF, false, null, null),
                new FieldDefinition("Uuid", FieldType.UUID, false, null, null)
        );
        
        EDIT_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("InventoryDispositionAdjustmentName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("InventoryAdjustmentTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("InventoryBucketTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 132L)
        );
    }

    @Inject
    InventoryDispositionAdjustmentControl inventoryDispositionAdjustmentControl;

    @Inject
    InventoryTransactionTypeControl inventoryTransactionTypeControl;

    @Inject
    InventoryDispositionAdjustmentLogic inventoryDispositionAdjustmentLogic;

    @Inject
    InventoryDispositionLogic inventoryDispositionLogic;

    @Inject InventoryAdjustmentTypeLogic inventoryAdjustmentTypeLogic;
    @Inject InventoryBucketTypeLogic inventoryBucketTypeLogic;

    
    /** Creates a new instance of EditInventoryDispositionAdjustmentCommand */
    public EditInventoryDispositionAdjustmentCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }

    @Override
    public EditInventoryDispositionAdjustmentResult getResult() {
        return InventoryResultFactory.getEditInventoryDispositionAdjustmentResult();
    }

    @Override
    public InventoryDispositionAdjustmentEdit getEdit() {
        return InventoryEditFactory.getInventoryDispositionAdjustmentEdit();
    }

    @Override
    public InventoryDispositionAdjustment getEntity(EditInventoryDispositionAdjustmentResult result) {
        return inventoryDispositionAdjustmentLogic.getInventoryDispositionAdjustmentByUniversalSpec(this, spec, false,
                editModeToEntityPermission(editMode));
    }

    @Override
    public InventoryDispositionAdjustment getLockEntity(InventoryDispositionAdjustment inventoryDispositionAdjustment) {
        return inventoryDispositionAdjustment;
    }

    @Override
    public void fillInResult(EditInventoryDispositionAdjustmentResult result, InventoryDispositionAdjustment inventoryDispositionAdjustment) {
        result.setInventoryDispositionAdjustment(inventoryDispositionAdjustmentControl.getInventoryDispositionAdjustmentTransfer(getUserVisit(),
                inventoryDispositionAdjustment));
    }

    @Override
    public void doLock(InventoryDispositionAdjustmentEdit edit, InventoryDispositionAdjustment inventoryDispositionAdjustment) {
        var inventoryDispositionAdjustmentDescription = 
                inventoryDispositionAdjustmentControl.getInventoryDispositionAdjustmentDescription(inventoryDispositionAdjustment, getPreferredLanguage());
        var inventoryDispositionAdjustmentDetail = inventoryDispositionAdjustment.getLastDetail();

        edit.setInventoryDispositionAdjustmentName(inventoryDispositionAdjustmentDetail.getInventoryDispositionAdjustmentName());
        edit.setInventoryAdjustmentTypeName(
                inventoryDispositionAdjustmentDetail.getInventoryAdjustmentType().getLastDetail().getInventoryAdjustmentTypeName());
        edit.setInventoryBucketTypeName(
                inventoryDispositionAdjustmentDetail.getInventoryBucketType().getLastDetail().getInventoryBucketTypeName());
        edit.setIsDefault(inventoryDispositionAdjustmentDetail.getIsDefault().toString());
        edit.setSortOrder(inventoryDispositionAdjustmentDetail.getSortOrder().toString());

        if(inventoryDispositionAdjustmentDescription != null) {
            edit.setDescription(inventoryDispositionAdjustmentDescription.getDescription());
        }
    }

    InventoryAdjustmentType inventoryAdjustmentType;
    InventoryBucketType inventoryBucketType;

    @Override
    public void canUpdate(InventoryDispositionAdjustment inventoryDispositionAdjustment) {
        var inventoryDisposition = inventoryDispositionAdjustment.getLastDetail().getInventoryDisposition();
        var inventoryDispositionAdjustmentName = edit.getInventoryDispositionAdjustmentName();
        var duplicateInventoryDispositionAdjustment =
                inventoryDispositionAdjustmentControl.getInventoryDispositionAdjustmentByName(inventoryDisposition,
                        inventoryDispositionAdjustmentName);

        if(duplicateInventoryDispositionAdjustment != null && !inventoryDispositionAdjustment.equals(duplicateInventoryDispositionAdjustment)) {
            addExecutionError(ExecutionErrors.DuplicateInventoryDispositionAdjustmentName.name(),
                    inventoryDisposition.getLastDetail().getInventoryDispositionName(),
                    inventoryDispositionAdjustmentName);
        }

        inventoryAdjustmentType = inventoryAdjustmentTypeLogic.getInventoryAdjustmentTypeByName(this,
                edit.getInventoryAdjustmentTypeName());
        inventoryBucketType = inventoryBucketTypeLogic.getInventoryBucketTypeByName(this, edit.getInventoryBucketTypeName());
    }

    @Override
    public void doUpdate(InventoryDispositionAdjustment inventoryDispositionAdjustment) {
        var partyPK = getPartyPK();
        var inventoryDispositionAdjustmentDetailValue = 
                inventoryDispositionAdjustmentControl.getInventoryDispositionAdjustmentDetailValueForUpdate(inventoryDispositionAdjustment);
        var inventoryDispositionAdjustmentDescription = 
                inventoryDispositionAdjustmentControl.getInventoryDispositionAdjustmentDescriptionForUpdate(
                        inventoryDispositionAdjustment, getPreferredLanguage());
        var description = edit.getDescription();

        inventoryDispositionAdjustmentDetailValue.setInventoryDispositionAdjustmentName(edit.getInventoryDispositionAdjustmentName());
        inventoryDispositionAdjustmentDetailValue.setInventoryAdjustmentTypePK(inventoryAdjustmentType.getPrimaryKey());
        inventoryDispositionAdjustmentDetailValue.setInventoryBucketTypePK(inventoryBucketType.getPrimaryKey());
        inventoryDispositionAdjustmentDetailValue.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
        inventoryDispositionAdjustmentDetailValue.setSortOrder(Integer.valueOf(edit.getSortOrder()));

        inventoryDispositionAdjustmentControl.updateInventoryDispositionAdjustmentFromValue(inventoryDispositionAdjustmentDetailValue, partyPK);

        if(inventoryDispositionAdjustmentDescription == null && description != null) {
            inventoryDispositionAdjustmentControl.createInventoryDispositionAdjustmentDescription(inventoryDispositionAdjustment, getPreferredLanguage(),
                    description, partyPK);
        } else {
            if(inventoryDispositionAdjustmentDescription != null && description == null) {
                inventoryDispositionAdjustmentControl.deleteInventoryDispositionAdjustmentDescription(inventoryDispositionAdjustmentDescription, partyPK);
            } else {
                if(inventoryDispositionAdjustmentDescription != null && description != null) {
                    var inventoryDispositionAdjustmentDescriptionValue = inventoryDispositionAdjustmentControl
                            .getInventoryDispositionAdjustmentDescriptionValue(inventoryDispositionAdjustmentDescription);

                    inventoryDispositionAdjustmentDescriptionValue.setDescription(description);
                    inventoryDispositionAdjustmentControl.updateInventoryDispositionAdjustmentDescriptionFromValue(
                            inventoryDispositionAdjustmentDescriptionValue, partyPK);
                }
            }
        }
    }

}
