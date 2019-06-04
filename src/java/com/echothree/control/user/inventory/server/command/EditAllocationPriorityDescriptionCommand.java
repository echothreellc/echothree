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

import com.echothree.control.user.inventory.common.edit.AllocationPriorityDescriptionEdit;
import com.echothree.control.user.inventory.common.edit.InventoryEditFactory;
import com.echothree.control.user.inventory.common.form.EditAllocationPriorityDescriptionForm;
import com.echothree.control.user.inventory.common.result.EditAllocationPriorityDescriptionResult;
import com.echothree.control.user.inventory.common.result.InventoryResultFactory;
import com.echothree.control.user.inventory.common.spec.AllocationPriorityDescriptionSpec;
import com.echothree.model.control.inventory.server.InventoryControl;
import com.echothree.model.control.party.common.PartyConstants;
import com.echothree.model.control.party.server.PartyControl;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.inventory.server.entity.AllocationPriority;
import com.echothree.model.data.inventory.server.entity.AllocationPriorityDescription;
import com.echothree.model.data.inventory.server.value.AllocationPriorityDescriptionValue;
import com.echothree.model.data.party.server.entity.Language;
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

public class EditAllocationPriorityDescriptionCommand
        extends BaseAbstractEditCommand<AllocationPriorityDescriptionSpec, AllocationPriorityDescriptionEdit, EditAllocationPriorityDescriptionResult, AllocationPriorityDescription, AllocationPriority> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyConstants.PartyType_UTILITY, null),
                new PartyTypeDefinition(PartyConstants.PartyType_EMPLOYEE, Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.AllocationPriority.name(), SecurityRoles.Description.name())
                        )))
                )));
        
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("AllocationPriorityName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, true, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("Description", FieldType.STRING, true, 1L, 80L)
                ));
    }
    
    /** Creates a new instance of EditAllocationPriorityDescriptionCommand */
    public EditAllocationPriorityDescriptionCommand(UserVisitPK userVisitPK, EditAllocationPriorityDescriptionForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    public EditAllocationPriorityDescriptionResult getResult() {
        return InventoryResultFactory.getEditAllocationPriorityDescriptionResult();
    }

    @Override
    public AllocationPriorityDescriptionEdit getEdit() {
        return InventoryEditFactory.getAllocationPriorityDescriptionEdit();
    }

    @Override
    public AllocationPriorityDescription getEntity(EditAllocationPriorityDescriptionResult result) {
        var inventoryControl = (InventoryControl)Session.getModelController(InventoryControl.class);
        AllocationPriorityDescription allocationPriorityDescription = null;
        String allocationPriorityName = spec.getAllocationPriorityName();
        AllocationPriority allocationPriority = inventoryControl.getAllocationPriorityByName(allocationPriorityName);

        if(allocationPriority != null) {
            var partyControl = (PartyControl)Session.getModelController(PartyControl.class);
            String languageIsoName = spec.getLanguageIsoName();
            Language language = partyControl.getLanguageByIsoName(languageIsoName);

            if(language != null) {
                if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
                    allocationPriorityDescription = inventoryControl.getAllocationPriorityDescription(allocationPriority, language);
                } else { // EditMode.UPDATE
                    allocationPriorityDescription = inventoryControl.getAllocationPriorityDescriptionForUpdate(allocationPriority, language);
                }

                if(allocationPriorityDescription == null) {
                    addExecutionError(ExecutionErrors.UnknownAllocationPriorityDescription.name(), allocationPriorityName, languageIsoName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownLanguageIsoName.name(), languageIsoName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownAllocationPriorityName.name(), allocationPriorityName);
        }

        return allocationPriorityDescription;
    }

    @Override
    public AllocationPriority getLockEntity(AllocationPriorityDescription allocationPriorityDescription) {
        return allocationPriorityDescription.getAllocationPriority();
    }

    @Override
    public void fillInResult(EditAllocationPriorityDescriptionResult result, AllocationPriorityDescription allocationPriorityDescription) {
        var inventoryControl = (InventoryControl)Session.getModelController(InventoryControl.class);

        result.setAllocationPriorityDescription(inventoryControl.getAllocationPriorityDescriptionTransfer(getUserVisit(), allocationPriorityDescription));
    }

    @Override
    public void doLock(AllocationPriorityDescriptionEdit edit, AllocationPriorityDescription allocationPriorityDescription) {
        edit.setDescription(allocationPriorityDescription.getDescription());
    }

    @Override
    public void doUpdate(AllocationPriorityDescription allocationPriorityDescription) {
        var inventoryControl = (InventoryControl)Session.getModelController(InventoryControl.class);
        AllocationPriorityDescriptionValue allocationPriorityDescriptionValue = inventoryControl.getAllocationPriorityDescriptionValue(allocationPriorityDescription);
        allocationPriorityDescriptionValue.setDescription(edit.getDescription());

        inventoryControl.updateAllocationPriorityDescriptionFromValue(allocationPriorityDescriptionValue, getPartyPK());
    }
    
}
