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
import com.echothree.control.user.inventory.common.edit.InventoryTransactionTypeDescriptionEdit;
import com.echothree.control.user.inventory.common.form.EditInventoryTransactionTypeDescriptionForm;
import com.echothree.control.user.inventory.common.result.EditInventoryTransactionTypeDescriptionResult;
import com.echothree.control.user.inventory.common.result.InventoryResultFactory;
import com.echothree.control.user.inventory.common.spec.InventoryTransactionTypeDescriptionSpec;
import com.echothree.model.control.inventory.server.control.InventoryTransactionTypeControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.inventory.server.entity.InventoryTransactionType;
import com.echothree.model.data.inventory.server.entity.InventoryTransactionTypeDescription;
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
import javax.enterprise.context.Dependent;

@Dependent
public class EditInventoryTransactionTypeDescriptionCommand
        extends BaseAbstractEditCommand<InventoryTransactionTypeDescriptionSpec, InventoryTransactionTypeDescriptionEdit, EditInventoryTransactionTypeDescriptionResult, InventoryTransactionTypeDescription, InventoryTransactionType> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.InventoryTransactionType.name(), SecurityRoles.Description.name())
                        )))
                )));
        
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("InventoryTransactionTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, true, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("Description", FieldType.STRING, true, 1L, 132L)
                ));
    }
    
    /** Creates a new instance of EditInventoryTransactionTypeDescriptionCommand */
    public EditInventoryTransactionTypeDescriptionCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    public EditInventoryTransactionTypeDescriptionResult getResult() {
        return InventoryResultFactory.getEditInventoryTransactionTypeDescriptionResult();
    }

    @Override
    public InventoryTransactionTypeDescriptionEdit getEdit() {
        return InventoryEditFactory.getInventoryTransactionTypeDescriptionEdit();
    }

    @Override
    public InventoryTransactionTypeDescription getEntity(EditInventoryTransactionTypeDescriptionResult result) {
        var inventoryTransactionTypeControl = Session.getModelController(InventoryTransactionTypeControl.class);
        InventoryTransactionTypeDescription inventoryTransactionTypeDescription = null;
        var inventoryTransactionTypeName = spec.getInventoryTransactionTypeName();
        var inventoryTransactionType = inventoryTransactionTypeControl.getInventoryTransactionTypeByName(inventoryTransactionTypeName);

        if(inventoryTransactionType != null) {
            var partyControl = Session.getModelController(PartyControl.class);
            var languageIsoName = spec.getLanguageIsoName();
            var language = partyControl.getLanguageByIsoName(languageIsoName);

            if(language != null) {
                if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
                    inventoryTransactionTypeDescription = inventoryTransactionTypeControl.getInventoryTransactionTypeDescription(inventoryTransactionType, language);
                } else { // EditMode.UPDATE
                    inventoryTransactionTypeDescription = inventoryTransactionTypeControl.getInventoryTransactionTypeDescriptionForUpdate(inventoryTransactionType, language);
                }

                if(inventoryTransactionTypeDescription == null) {
                    addExecutionError(ExecutionErrors.UnknownInventoryTransactionTypeDescription.name(), inventoryTransactionTypeName, languageIsoName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownLanguageIsoName.name(), languageIsoName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownInventoryTransactionTypeName.name(), inventoryTransactionTypeName);
        }

        return inventoryTransactionTypeDescription;
    }

    @Override
    public InventoryTransactionType getLockEntity(InventoryTransactionTypeDescription inventoryTransactionTypeDescription) {
        return inventoryTransactionTypeDescription.getInventoryTransactionType();
    }

    @Override
    public void fillInResult(EditInventoryTransactionTypeDescriptionResult result, InventoryTransactionTypeDescription inventoryTransactionTypeDescription) {
        var inventoryTransactionTypeControl = Session.getModelController(InventoryTransactionTypeControl.class);

        result.setInventoryTransactionTypeDescription(inventoryTransactionTypeControl.getInventoryTransactionTypeDescriptionTransfer(getUserVisit(), inventoryTransactionTypeDescription));
    }

    @Override
    public void doLock(InventoryTransactionTypeDescriptionEdit edit, InventoryTransactionTypeDescription inventoryTransactionTypeDescription) {
        edit.setDescription(inventoryTransactionTypeDescription.getDescription());
    }

    @Override
    public void doUpdate(InventoryTransactionTypeDescription inventoryTransactionTypeDescription) {
        var inventoryTransactionTypeControl = Session.getModelController(InventoryTransactionTypeControl.class);
        var inventoryTransactionTypeDescriptionValue = inventoryTransactionTypeControl.getInventoryTransactionTypeDescriptionValue(inventoryTransactionTypeDescription);
        inventoryTransactionTypeDescriptionValue.setDescription(edit.getDescription());

        inventoryTransactionTypeControl.updateInventoryTransactionTypeDescriptionFromValue(inventoryTransactionTypeDescriptionValue, getPartyPK());
    }
    
}
