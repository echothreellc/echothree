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
import com.echothree.control.user.inventory.common.edit.InventoryTransactionRoleTypeDescriptionEdit;
import com.echothree.control.user.inventory.common.form.EditInventoryTransactionRoleTypeDescriptionForm;
import com.echothree.control.user.inventory.common.result.EditInventoryTransactionRoleTypeDescriptionResult;
import com.echothree.control.user.inventory.common.result.InventoryResultFactory;
import com.echothree.control.user.inventory.common.spec.InventoryTransactionRoleTypeDescriptionSpec;
import com.echothree.model.control.inventory.server.control.InventoryTransactionRoleControl;
import com.echothree.model.control.inventory.server.logic.InventoryTransactionRoleTypeLogic;
import com.echothree.model.control.inventory.server.logic.InventoryTransactionTypeLogic;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.logic.LanguageLogic;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.inventory.server.entity.InventoryTransactionRoleType;
import com.echothree.model.data.inventory.server.entity.InventoryTransactionRoleTypeDescription;
import com.echothree.model.data.user.common.pk.UserVisitPK;
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
public class EditInventoryTransactionRoleTypeDescriptionCommand
        extends BaseAbstractEditCommand<InventoryTransactionRoleTypeDescriptionSpec, InventoryTransactionRoleTypeDescriptionEdit,
                EditInventoryTransactionRoleTypeDescriptionResult, InventoryTransactionRoleTypeDescription, InventoryTransactionRoleType> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), List.of(
                        new SecurityRoleDefinition(SecurityRoleGroups.InventoryTransactionRoleType.name(), SecurityRoles.Description.name())
                ))
        ));
        
        SPEC_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("InventoryTransactionTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("InventoryTransactionRoleTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, true, null, null)
        );
        
        EDIT_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("Description", FieldType.STRING, true, 1L, 132L)
        );
    }

    @Inject
    InventoryTransactionRoleControl inventoryTransactionRoleControl;

    @Inject
    InventoryTransactionRoleTypeLogic inventoryTransactionRoleTypeLogic;

    @Inject
    InventoryTransactionTypeLogic inventoryTransactionTypeLogic;

    @Inject
    LanguageLogic languageLogic;

    
    /** Creates a new instance of EditInventoryTransactionRoleTypeDescriptionCommand */
    public EditInventoryTransactionRoleTypeDescriptionCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    public EditInventoryTransactionRoleTypeDescriptionResult getResult() {
        return InventoryResultFactory.getEditInventoryTransactionRoleTypeDescriptionResult();
    }

    @Override
    public InventoryTransactionRoleTypeDescriptionEdit getEdit() {
        return InventoryEditFactory.getInventoryTransactionRoleTypeDescriptionEdit();
    }

    @Override
    public InventoryTransactionRoleTypeDescription getEntity(EditInventoryTransactionRoleTypeDescriptionResult result) {
        InventoryTransactionRoleTypeDescription inventoryTransactionRoleTypeDescription = null;
        var inventoryTransactionTypeName = spec.getInventoryTransactionTypeName();
        var inventoryTransactionType = inventoryTransactionTypeLogic.getInventoryTransactionTypeByName(this, inventoryTransactionTypeName);

        if(!hasExecutionErrors()) {
            var inventoryTransactionRoleTypeName = spec.getInventoryTransactionRoleTypeName();
            var inventoryTransactionRoleType = inventoryTransactionRoleTypeLogic.getInventoryTransactionRoleTypeByName(this,
                    inventoryTransactionType, inventoryTransactionRoleTypeName);

            if(!hasExecutionErrors()) {
                var languageIsoName = spec.getLanguageIsoName();
                var language = languageLogic.getLanguageByName(this, languageIsoName);

                if(!hasExecutionErrors()) {
                    if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
                        inventoryTransactionRoleTypeDescription = 
                                inventoryTransactionRoleControl.getInventoryTransactionRoleTypeDescription(inventoryTransactionRoleType, language);
                    } else { // EditMode.UPDATE
                        inventoryTransactionRoleTypeDescription = 
                                inventoryTransactionRoleControl.getInventoryTransactionRoleTypeDescriptionForUpdate(
                                        inventoryTransactionRoleType, language);
                    }

                    if(inventoryTransactionRoleTypeDescription == null) {
                        addExecutionError(ExecutionErrors.UnknownInventoryTransactionRoleTypeDescription.name(), inventoryTransactionTypeName,
                                inventoryTransactionRoleTypeName, languageIsoName);
                    }
                }
            }
        }

        return inventoryTransactionRoleTypeDescription;
    }

    @Override
    public InventoryTransactionRoleType getLockEntity(InventoryTransactionRoleTypeDescription inventoryTransactionRoleTypeDescription) {
        return inventoryTransactionRoleTypeDescription.getInventoryTransactionRoleType();
    }

    @Override
    public void fillInResult(EditInventoryTransactionRoleTypeDescriptionResult result,
            InventoryTransactionRoleTypeDescription inventoryTransactionRoleTypeDescription) {
        result.setInventoryTransactionRoleTypeDescription(
                inventoryTransactionRoleControl.getInventoryTransactionRoleTypeDescriptionTransfer(getUserVisit(),
                        inventoryTransactionRoleTypeDescription));
    }

    @Override
    public void doLock(InventoryTransactionRoleTypeDescriptionEdit edit,
            InventoryTransactionRoleTypeDescription inventoryTransactionRoleTypeDescription) {
        edit.setDescription(inventoryTransactionRoleTypeDescription.getDescription());
    }

    @Override
    public void doUpdate(InventoryTransactionRoleTypeDescription inventoryTransactionRoleTypeDescription) {
        var inventoryTransactionRoleTypeDescriptionValue = 
                inventoryTransactionRoleControl.getInventoryTransactionRoleTypeDescriptionValue(inventoryTransactionRoleTypeDescription);
        
        inventoryTransactionRoleTypeDescriptionValue.setDescription(edit.getDescription());

        inventoryTransactionRoleControl.updateInventoryTransactionRoleTypeDescriptionFromValue(inventoryTransactionRoleTypeDescriptionValue,
                getPartyPK());
    }
    
}
