// --------------------------------------------------------------------------------
// Copyright 2002-2019 Echo Three, LLC
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

import com.echothree.control.user.inventory.common.edit.AllocationPriorityEdit;
import com.echothree.control.user.inventory.common.edit.InventoryEditFactory;
import com.echothree.control.user.inventory.common.form.EditAllocationPriorityForm;
import com.echothree.control.user.inventory.common.result.EditAllocationPriorityResult;
import com.echothree.control.user.inventory.common.result.InventoryResultFactory;
import com.echothree.control.user.inventory.common.spec.AllocationPrioritySpec;
import com.echothree.model.control.inventory.server.InventoryControl;
import com.echothree.model.control.party.common.PartyConstants;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.inventory.server.entity.AllocationPriority;
import com.echothree.model.data.inventory.server.entity.AllocationPriorityDescription;
import com.echothree.model.data.inventory.server.entity.AllocationPriorityDetail;
import com.echothree.model.data.inventory.server.value.AllocationPriorityDescriptionValue;
import com.echothree.model.data.inventory.server.value.AllocationPriorityDetailValue;
import com.echothree.model.data.party.common.pk.PartyPK;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.EditMode;
import com.echothree.util.server.control.BaseAbstractEditCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class EditAllocationPriorityCommand
        extends BaseAbstractEditCommand<AllocationPrioritySpec, AllocationPriorityEdit, EditAllocationPriorityResult, AllocationPriority, AllocationPriority> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyConstants.PartyType_UTILITY, null),
                new PartyTypeDefinition(PartyConstants.PartyType_EMPLOYEE, Collections.unmodifiableList(Arrays.asList(
                    new SecurityRoleDefinition(SecurityRoleGroups.AllocationPriority.name(), SecurityRoles.Edit.name())
                    )))
                )));
        
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("AllocationPriorityName", FieldType.ENTITY_NAME, true, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("AllocationPriorityName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("Priority", FieldType.UNSIGNED_INTEGER, true, null, null),
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 80L)
                ));
    }
    
    /** Creates a new instance of EditAllocationPriorityCommand */
    public EditAllocationPriorityCommand(UserVisitPK userVisitPK, EditAllocationPriorityForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }

    @Override
    public EditAllocationPriorityResult getResult() {
        return InventoryResultFactory.getEditAllocationPriorityResult();
    }

    @Override
    public AllocationPriorityEdit getEdit() {
        return InventoryEditFactory.getAllocationPriorityEdit();
    }

    @Override
    public AllocationPriority getEntity(EditAllocationPriorityResult result) {
        InventoryControl inventoryControl = (InventoryControl)Session.getModelController(InventoryControl.class);
        AllocationPriority allocationPriority;
        String allocationPriorityName = spec.getAllocationPriorityName();

        if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
            allocationPriority = inventoryControl.getAllocationPriorityByName(allocationPriorityName);
        } else { // EditMode.UPDATE
            allocationPriority = inventoryControl.getAllocationPriorityByNameForUpdate(allocationPriorityName);
        }

        if(allocationPriority != null) {
            result.setAllocationPriority(inventoryControl.getAllocationPriorityTransfer(getUserVisit(), allocationPriority));
        } else {
            addExecutionError(ExecutionErrors.UnknownAllocationPriorityName.name(), allocationPriorityName);
        }

        return allocationPriority;
    }

    @Override
    public AllocationPriority getLockEntity(AllocationPriority allocationPriority) {
        return allocationPriority;
    }

    @Override
    public void fillInResult(EditAllocationPriorityResult result, AllocationPriority allocationPriority) {
        InventoryControl inventoryControl = (InventoryControl)Session.getModelController(InventoryControl.class);

        result.setAllocationPriority(inventoryControl.getAllocationPriorityTransfer(getUserVisit(), allocationPriority));
    }

    @Override
    public void doLock(AllocationPriorityEdit edit, AllocationPriority allocationPriority) {
        InventoryControl inventoryControl = (InventoryControl)Session.getModelController(InventoryControl.class);
        AllocationPriorityDescription allocationPriorityDescription = inventoryControl.getAllocationPriorityDescription(allocationPriority, getPreferredLanguage());
        AllocationPriorityDetail allocationPriorityDetail = allocationPriority.getLastDetail();

        edit.setAllocationPriorityName(allocationPriorityDetail.getAllocationPriorityName());
        edit.setPriority(allocationPriorityDetail.getPriority().toString());
        edit.setIsDefault(allocationPriorityDetail.getIsDefault().toString());
        edit.setSortOrder(allocationPriorityDetail.getSortOrder().toString());

        if(allocationPriorityDescription != null) {
            edit.setDescription(allocationPriorityDescription.getDescription());
        }
    }

    @Override
    public void canUpdate(AllocationPriority allocationPriority) {
        InventoryControl inventoryControl = (InventoryControl)Session.getModelController(InventoryControl.class);
        String allocationPriorityName = edit.getAllocationPriorityName();
        AllocationPriority duplicateAllocationPriority = inventoryControl.getAllocationPriorityByName(allocationPriorityName);

        if(duplicateAllocationPriority != null && !allocationPriority.equals(duplicateAllocationPriority)) {
            addExecutionError(ExecutionErrors.DuplicateAllocationPriorityName.name(), allocationPriorityName);
        }
    }

    @Override
    public void doUpdate(AllocationPriority allocationPriority) {
        InventoryControl inventoryControl = (InventoryControl)Session.getModelController(InventoryControl.class);
        PartyPK partyPK = getPartyPK();
        AllocationPriorityDetailValue allocationPriorityDetailValue = inventoryControl.getAllocationPriorityDetailValueForUpdate(allocationPriority);
        AllocationPriorityDescription allocationPriorityDescription = inventoryControl.getAllocationPriorityDescriptionForUpdate(allocationPriority, getPreferredLanguage());
        String description = edit.getDescription();

        allocationPriorityDetailValue.setAllocationPriorityName(edit.getAllocationPriorityName());
        allocationPriorityDetailValue.setPriority(Integer.valueOf(edit.getPriority()));
        allocationPriorityDetailValue.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
        allocationPriorityDetailValue.setSortOrder(Integer.valueOf(edit.getSortOrder()));

        inventoryControl.updateAllocationPriorityFromValue(allocationPriorityDetailValue, partyPK);

        if(allocationPriorityDescription == null && description != null) {
            inventoryControl.createAllocationPriorityDescription(allocationPriority, getPreferredLanguage(), description, partyPK);
        } else {
            if(allocationPriorityDescription != null && description == null) {
                inventoryControl.deleteAllocationPriorityDescription(allocationPriorityDescription, partyPK);
            } else {
                if(allocationPriorityDescription != null && description != null) {
                    AllocationPriorityDescriptionValue allocationPriorityDescriptionValue = inventoryControl.getAllocationPriorityDescriptionValue(allocationPriorityDescription);

                    allocationPriorityDescriptionValue.setDescription(description);
                    inventoryControl.updateAllocationPriorityDescriptionFromValue(allocationPriorityDescriptionValue, partyPK);
                }
            }
        }
    }

}
