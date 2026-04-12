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

import com.echothree.control.user.inventory.common.edit.InventoryConditionDescriptionEdit;
import com.echothree.control.user.inventory.common.edit.InventoryEditFactory;
import com.echothree.control.user.inventory.common.result.EditInventoryConditionDescriptionResult;
import com.echothree.control.user.inventory.common.result.InventoryResultFactory;
import com.echothree.control.user.inventory.common.spec.InventoryConditionDescriptionSpec;
import com.echothree.model.control.inventory.server.control.InventoryControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.inventory.server.entity.InventoryCondition;
import com.echothree.model.data.inventory.server.entity.InventoryConditionDescription;
import com.echothree.util.common.command.EditMode;
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
public class EditInventoryConditionDescriptionCommand
        extends BaseAbstractEditCommand<InventoryConditionDescriptionSpec, InventoryConditionDescriptionEdit, EditInventoryConditionDescriptionResult, InventoryConditionDescription, InventoryCondition> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), List.of(
                        new SecurityRoleDefinition(SecurityRoleGroups.InventoryCondition.name(), SecurityRoles.Description.name())
                ))
        ));
        
        SPEC_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("InventoryConditionName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, true, null, null)
        );
        
        EDIT_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("Description", FieldType.STRING, true, 1L, 132L)
        );
    }
    
    /** Creates a new instance of EditInventoryConditionDescriptionCommand */
    public EditInventoryConditionDescriptionCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Inject
    InventoryControl inventoryControl;

    @Inject
    PartyControl partyControl;

    @Override
    public EditInventoryConditionDescriptionResult getResult() {
        return InventoryResultFactory.getEditInventoryConditionDescriptionResult();
    }

    @Override
    public InventoryConditionDescriptionEdit getEdit() {
        return InventoryEditFactory.getInventoryConditionDescriptionEdit();
    }

    @Override
    public InventoryConditionDescription getEntity(EditInventoryConditionDescriptionResult result) {
        InventoryConditionDescription inventoryConditionDescription = null;
        var inventoryConditionName = spec.getInventoryConditionName();
        var inventoryCondition = inventoryControl.getInventoryConditionByName(inventoryConditionName);

        if(inventoryCondition != null) {
            var languageIsoName = spec.getLanguageIsoName();
            var language = partyControl.getLanguageByIsoName(languageIsoName);

            if(language != null) {
                if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
                    inventoryConditionDescription = inventoryControl.getInventoryConditionDescription(inventoryCondition, language);
                } else { // EditMode.UPDATE
                    inventoryConditionDescription = inventoryControl.getInventoryConditionDescriptionForUpdate(inventoryCondition, language);
                }

                if(inventoryConditionDescription == null) {
                    addExecutionError(ExecutionErrors.UnknownInventoryConditionDescription.name(), inventoryConditionName, languageIsoName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownLanguageIsoName.name(), languageIsoName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownInventoryConditionName.name(), inventoryConditionName);
        }

        return inventoryConditionDescription;
    }

    @Override
    public InventoryCondition getLockEntity(InventoryConditionDescription inventoryConditionDescription) {
        return inventoryConditionDescription.getInventoryCondition();
    }

    @Override
    public void fillInResult(EditInventoryConditionDescriptionResult result, InventoryConditionDescription inventoryConditionDescription) {
        result.setInventoryConditionDescription(inventoryControl.getInventoryConditionDescriptionTransfer(getUserVisit(), inventoryConditionDescription));
    }

    @Override
    public void doLock(InventoryConditionDescriptionEdit edit, InventoryConditionDescription inventoryConditionDescription) {
        edit.setDescription(inventoryConditionDescription.getDescription());
    }

    @Override
    public void doUpdate(InventoryConditionDescription inventoryConditionDescription) {
        var inventoryConditionDescriptionValue = inventoryControl.getInventoryConditionDescriptionValue(inventoryConditionDescription);

        inventoryConditionDescriptionValue.setDescription(edit.getDescription());

        inventoryControl.updateInventoryConditionDescriptionFromValue(inventoryConditionDescriptionValue, getPartyPK());
    }
    
}
