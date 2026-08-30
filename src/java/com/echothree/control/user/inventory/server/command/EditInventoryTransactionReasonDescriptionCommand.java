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
import com.echothree.control.user.inventory.common.edit.InventoryTransactionReasonDescriptionEdit;
import com.echothree.control.user.inventory.common.form.EditInventoryTransactionReasonDescriptionForm;
import com.echothree.control.user.inventory.common.result.EditInventoryTransactionReasonDescriptionResult;
import com.echothree.control.user.inventory.common.result.InventoryResultFactory;
import com.echothree.control.user.inventory.common.spec.InventoryTransactionReasonDescriptionSpec;
import com.echothree.model.control.inventory.server.control.InventoryTransactionReasonControl;
import com.echothree.model.control.inventory.server.logic.InventoryTransactionReasonLogic;
import com.echothree.model.control.inventory.server.logic.InventoryTransactionTypeLogic;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.logic.LanguageLogic;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.inventory.server.entity.InventoryTransactionReason;
import com.echothree.model.data.inventory.server.entity.InventoryTransactionReasonDescription;
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
public class EditInventoryTransactionReasonDescriptionCommand
        extends BaseAbstractEditCommand<InventoryTransactionReasonDescriptionSpec, InventoryTransactionReasonDescriptionEdit,
                EditInventoryTransactionReasonDescriptionResult, InventoryTransactionReasonDescription, InventoryTransactionReason> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), List.of(
                        new SecurityRoleDefinition(SecurityRoleGroups.InventoryTransactionReason.name(), SecurityRoles.Description.name())
                ))
        ));
        
        SPEC_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("InventoryTransactionTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("InventoryTransactionReasonName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, true, null, null)
        );
        
        EDIT_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("Description", FieldType.STRING, true, 1L, 132L)
        );
    }

    @Inject
    InventoryTransactionReasonControl inventoryTransactionReasonControl;

    @Inject
    InventoryTransactionReasonLogic inventoryTransactionReasonLogic;

    @Inject
    InventoryTransactionTypeLogic inventoryTransactionTypeLogic;

    @Inject
    LanguageLogic languageLogic;

    
    /** Creates a new instance of EditInventoryTransactionReasonDescriptionCommand */
    public EditInventoryTransactionReasonDescriptionCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    public EditInventoryTransactionReasonDescriptionResult getResult() {
        return InventoryResultFactory.getEditInventoryTransactionReasonDescriptionResult();
    }

    @Override
    public InventoryTransactionReasonDescriptionEdit getEdit() {
        return InventoryEditFactory.getInventoryTransactionReasonDescriptionEdit();
    }

    @Override
    public InventoryTransactionReasonDescription getEntity(EditInventoryTransactionReasonDescriptionResult result) {
        InventoryTransactionReasonDescription inventoryTransactionReasonDescription = null;
        var inventoryTransactionTypeName = spec.getInventoryTransactionTypeName();
        var inventoryTransactionType = inventoryTransactionTypeLogic.getInventoryTransactionTypeByName(this, inventoryTransactionTypeName);

        if(!hasExecutionErrors()) {
            var inventoryTransactionReasonName = spec.getInventoryTransactionReasonName();
            var inventoryTransactionReason = inventoryTransactionReasonLogic.getInventoryTransactionReasonByName(this,
                    inventoryTransactionType, inventoryTransactionReasonName);

            if(!hasExecutionErrors()) {
                var languageIsoName = spec.getLanguageIsoName();
                var language = languageLogic.getLanguageByName(this, languageIsoName);

                if(!hasExecutionErrors()) {
                    if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
                        inventoryTransactionReasonDescription = 
                                inventoryTransactionReasonControl.getInventoryTransactionReasonDescription(inventoryTransactionReason, language);
                    } else { // EditMode.UPDATE
                        inventoryTransactionReasonDescription = 
                                inventoryTransactionReasonControl.getInventoryTransactionReasonDescriptionForUpdate(
                                        inventoryTransactionReason, language);
                    }

                    if(inventoryTransactionReasonDescription == null) {
                        addExecutionError(ExecutionErrors.UnknownInventoryTransactionReasonDescription.name(), inventoryTransactionTypeName,
                                inventoryTransactionReasonName, languageIsoName);
                    }
                }
            }
        }

        return inventoryTransactionReasonDescription;
    }

    @Override
    public InventoryTransactionReason getLockEntity(InventoryTransactionReasonDescription inventoryTransactionReasonDescription) {
        return inventoryTransactionReasonDescription.getInventoryTransactionReason();
    }

    @Override
    public void fillInResult(EditInventoryTransactionReasonDescriptionResult result,
            InventoryTransactionReasonDescription inventoryTransactionReasonDescription) {
        result.setInventoryTransactionReasonDescription(
                inventoryTransactionReasonControl.getInventoryTransactionReasonDescriptionTransfer(getUserVisit(),
                        inventoryTransactionReasonDescription));
    }

    @Override
    public void doLock(InventoryTransactionReasonDescriptionEdit edit,
            InventoryTransactionReasonDescription inventoryTransactionReasonDescription) {
        edit.setDescription(inventoryTransactionReasonDescription.getDescription());
    }

    @Override
    public void doUpdate(InventoryTransactionReasonDescription inventoryTransactionReasonDescription) {
        var inventoryTransactionReasonDescriptionValue = 
                inventoryTransactionReasonControl.getInventoryTransactionReasonDescriptionValue(inventoryTransactionReasonDescription);
        
        inventoryTransactionReasonDescriptionValue.setDescription(edit.getDescription());

        inventoryTransactionReasonControl.updateInventoryTransactionReasonDescriptionFromValue(inventoryTransactionReasonDescriptionValue,
                getPartyPK());
    }
    
}
