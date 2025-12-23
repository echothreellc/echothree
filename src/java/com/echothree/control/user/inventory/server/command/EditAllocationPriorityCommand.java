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

import com.echothree.control.user.inventory.common.edit.AllocationPriorityEdit;
import com.echothree.control.user.inventory.common.edit.InventoryEditFactory;
import com.echothree.control.user.inventory.common.form.EditAllocationPriorityForm;
import com.echothree.control.user.inventory.common.result.EditAllocationPriorityResult;
import com.echothree.control.user.inventory.common.result.InventoryResultFactory;
import com.echothree.control.user.inventory.common.spec.AllocationPriorityUniversalSpec;
import com.echothree.model.control.inventory.server.control.InventoryControl;
import com.echothree.model.control.inventory.server.logic.AllocationPriorityLogic;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.inventory.server.entity.AllocationPriority;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.EditMode;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseAbstractEditCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.enterprise.context.Dependent;

@Dependent
public class EditAllocationPriorityCommand
        extends BaseAbstractEditCommand<AllocationPriorityUniversalSpec, AllocationPriorityEdit, EditAllocationPriorityResult, AllocationPriority, AllocationPriority> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
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
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 132L)
                ));
    }
    
    /** Creates a new instance of EditAllocationPriorityCommand */
    public EditAllocationPriorityCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
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
        AllocationPriority allocationPriority;

        if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
            allocationPriority = AllocationPriorityLogic.getInstance().getAllocationPriorityByUniversalSpec(this, spec, false);
        } else { // EditMode.UPDATE
            allocationPriority = AllocationPriorityLogic.getInstance().getAllocationPriorityByUniversalSpecForUpdate(this, spec, false);
        }

        if(!hasExecutionErrors()) {
            var inventoryControl = Session.getModelController(InventoryControl.class);

            result.setAllocationPriority(inventoryControl.getAllocationPriorityTransfer(getUserVisit(), allocationPriority));
        }

        return allocationPriority;
    }

    @Override
    public AllocationPriority getLockEntity(AllocationPriority allocationPriority) {
        return allocationPriority;
    }

    @Override
    public void fillInResult(EditAllocationPriorityResult result, AllocationPriority allocationPriority) {
        var inventoryControl = Session.getModelController(InventoryControl.class);

        result.setAllocationPriority(inventoryControl.getAllocationPriorityTransfer(getUserVisit(), allocationPriority));
    }

    @Override
    public void doLock(AllocationPriorityEdit edit, AllocationPriority allocationPriority) {
        var inventoryControl = Session.getModelController(InventoryControl.class);
        var allocationPriorityDescription = inventoryControl.getAllocationPriorityDescription(allocationPriority, getPreferredLanguage());
        var allocationPriorityDetail = allocationPriority.getLastDetail();

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
        var inventoryControl = Session.getModelController(InventoryControl.class);
        var allocationPriorityName = edit.getAllocationPriorityName();
        var duplicateAllocationPriority = inventoryControl.getAllocationPriorityByName(allocationPriorityName);

        if(duplicateAllocationPriority != null && !allocationPriority.equals(duplicateAllocationPriority)) {
            addExecutionError(ExecutionErrors.DuplicateAllocationPriorityName.name(), allocationPriorityName);
        }
    }

    @Override
    public void doUpdate(AllocationPriority allocationPriority) {
        var inventoryControl = Session.getModelController(InventoryControl.class);
        var partyPK = getPartyPK();
        var allocationPriorityDetailValue = inventoryControl.getAllocationPriorityDetailValueForUpdate(allocationPriority);
        var allocationPriorityDescription = inventoryControl.getAllocationPriorityDescriptionForUpdate(allocationPriority, getPreferredLanguage());
        var description = edit.getDescription();

        allocationPriorityDetailValue.setAllocationPriorityName(edit.getAllocationPriorityName());
        allocationPriorityDetailValue.setPriority(Integer.valueOf(edit.getPriority()));
        allocationPriorityDetailValue.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
        allocationPriorityDetailValue.setSortOrder(Integer.valueOf(edit.getSortOrder()));

        AllocationPriorityLogic.getInstance().updateAllocationPriorityFromValue(allocationPriorityDetailValue, partyPK);

        if(allocationPriorityDescription == null && description != null) {
            inventoryControl.createAllocationPriorityDescription(allocationPriority, getPreferredLanguage(), description, partyPK);
        } else {
            if(allocationPriorityDescription != null && description == null) {
                inventoryControl.deleteAllocationPriorityDescription(allocationPriorityDescription, partyPK);
            } else {
                if(allocationPriorityDescription != null && description != null) {
                    var allocationPriorityDescriptionValue = inventoryControl.getAllocationPriorityDescriptionValue(allocationPriorityDescription);

                    allocationPriorityDescriptionValue.setDescription(description);
                    inventoryControl.updateAllocationPriorityDescriptionFromValue(allocationPriorityDescriptionValue, partyPK);
                }
            }
        }
    }

}
