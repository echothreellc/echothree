// --------------------------------------------------------------------------------
// Copyright 2002-2025 Echo Three, LLC
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
import com.echothree.control.user.inventory.common.edit.InventoryAdjustmentTypeDescriptionEdit;
import com.echothree.control.user.inventory.common.form.EditInventoryAdjustmentTypeDescriptionForm;
import com.echothree.control.user.inventory.common.result.EditInventoryAdjustmentTypeDescriptionResult;
import com.echothree.control.user.inventory.common.result.InventoryResultFactory;
import com.echothree.control.user.inventory.common.spec.InventoryAdjustmentTypeDescriptionSpec;
import com.echothree.model.control.inventory.server.control.InventoryAdjustmentTypeControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.inventory.server.entity.InventoryAdjustmentType;
import com.echothree.model.data.inventory.server.entity.InventoryAdjustmentTypeDescription;
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

public class EditInventoryAdjustmentTypeDescriptionCommand
        extends BaseAbstractEditCommand<InventoryAdjustmentTypeDescriptionSpec, InventoryAdjustmentTypeDescriptionEdit, EditInventoryAdjustmentTypeDescriptionResult, InventoryAdjustmentTypeDescription, InventoryAdjustmentType> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.InventoryAdjustmentType.name(), SecurityRoles.Description.name())
                        )))
                )));
        
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("InventoryAdjustmentTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, true, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("Description", FieldType.STRING, true, 1L, 132L)
                ));
    }
    
    /** Creates a new instance of EditInventoryAdjustmentTypeDescriptionCommand */
    public EditInventoryAdjustmentTypeDescriptionCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    public EditInventoryAdjustmentTypeDescriptionResult getResult() {
        return InventoryResultFactory.getEditInventoryAdjustmentTypeDescriptionResult();
    }

    @Override
    public InventoryAdjustmentTypeDescriptionEdit getEdit() {
        return InventoryEditFactory.getInventoryAdjustmentTypeDescriptionEdit();
    }

    @Override
    public InventoryAdjustmentTypeDescription getEntity(EditInventoryAdjustmentTypeDescriptionResult result) {
        var inventoryAdjustmentTypeControl = Session.getModelController(InventoryAdjustmentTypeControl.class);
        InventoryAdjustmentTypeDescription inventoryAdjustmentTypeDescription = null;
        var inventoryAdjustmentTypeName = spec.getInventoryAdjustmentTypeName();
        var inventoryAdjustmentType = inventoryAdjustmentTypeControl.getInventoryAdjustmentTypeByName(inventoryAdjustmentTypeName);

        if(inventoryAdjustmentType != null) {
            var partyControl = Session.getModelController(PartyControl.class);
            var languageIsoName = spec.getLanguageIsoName();
            var language = partyControl.getLanguageByIsoName(languageIsoName);

            if(language != null) {
                if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
                    inventoryAdjustmentTypeDescription = inventoryAdjustmentTypeControl.getInventoryAdjustmentTypeDescription(inventoryAdjustmentType, language);
                } else { // EditMode.UPDATE
                    inventoryAdjustmentTypeDescription = inventoryAdjustmentTypeControl.getInventoryAdjustmentTypeDescriptionForUpdate(inventoryAdjustmentType, language);
                }

                if(inventoryAdjustmentTypeDescription == null) {
                    addExecutionError(ExecutionErrors.UnknownInventoryAdjustmentTypeDescription.name(), inventoryAdjustmentTypeName, languageIsoName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownLanguageIsoName.name(), languageIsoName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownInventoryAdjustmentTypeName.name(), inventoryAdjustmentTypeName);
        }

        return inventoryAdjustmentTypeDescription;
    }

    @Override
    public InventoryAdjustmentType getLockEntity(InventoryAdjustmentTypeDescription inventoryAdjustmentTypeDescription) {
        return inventoryAdjustmentTypeDescription.getInventoryAdjustmentType();
    }

    @Override
    public void fillInResult(EditInventoryAdjustmentTypeDescriptionResult result, InventoryAdjustmentTypeDescription inventoryAdjustmentTypeDescription) {
        var inventoryAdjustmentTypeControl = Session.getModelController(InventoryAdjustmentTypeControl.class);

        result.setInventoryAdjustmentTypeDescription(inventoryAdjustmentTypeControl.getInventoryAdjustmentTypeDescriptionTransfer(getUserVisit(), inventoryAdjustmentTypeDescription));
    }

    @Override
    public void doLock(InventoryAdjustmentTypeDescriptionEdit edit, InventoryAdjustmentTypeDescription inventoryAdjustmentTypeDescription) {
        edit.setDescription(inventoryAdjustmentTypeDescription.getDescription());
    }

    @Override
    public void doUpdate(InventoryAdjustmentTypeDescription inventoryAdjustmentTypeDescription) {
        var inventoryAdjustmentTypeControl = Session.getModelController(InventoryAdjustmentTypeControl.class);
        var inventoryAdjustmentTypeDescriptionValue = inventoryAdjustmentTypeControl.getInventoryAdjustmentTypeDescriptionValue(inventoryAdjustmentTypeDescription);
        inventoryAdjustmentTypeDescriptionValue.setDescription(edit.getDescription());

        inventoryAdjustmentTypeControl.updateInventoryAdjustmentTypeDescriptionFromValue(inventoryAdjustmentTypeDescriptionValue, getPartyPK());
    }
    
}
