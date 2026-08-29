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
import com.echothree.control.user.inventory.common.edit.InventoryDispositionDescriptionEdit;
import com.echothree.control.user.inventory.common.form.EditInventoryDispositionDescriptionForm;
import com.echothree.control.user.inventory.common.result.EditInventoryDispositionDescriptionResult;
import com.echothree.control.user.inventory.common.result.InventoryResultFactory;
import com.echothree.control.user.inventory.common.spec.InventoryDispositionDescriptionSpec;
import com.echothree.model.control.inventory.server.control.InventoryDispositionControl;
import com.echothree.model.control.inventory.server.logic.InventoryDispositionLogic;
import com.echothree.model.control.inventory.server.logic.InventoryTransactionTypeLogic;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.logic.LanguageLogic;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.inventory.server.entity.InventoryDisposition;
import com.echothree.model.data.inventory.server.entity.InventoryDispositionDescription;
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
public class EditInventoryDispositionDescriptionCommand
        extends BaseAbstractEditCommand<InventoryDispositionDescriptionSpec, InventoryDispositionDescriptionEdit,
                EditInventoryDispositionDescriptionResult, InventoryDispositionDescription, InventoryDisposition> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), List.of(
                        new SecurityRoleDefinition(SecurityRoleGroups.InventoryDisposition.name(), SecurityRoles.Description.name())
                ))
        ));
        
        SPEC_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("InventoryTransactionTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("InventoryDispositionName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, true, null, null)
        );
        
        EDIT_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("Description", FieldType.STRING, true, 1L, 132L)
        );
    }

    @Inject
    InventoryDispositionControl inventoryDispositionControl;

    @Inject
    InventoryDispositionLogic inventoryDispositionLogic;

    @Inject
    InventoryTransactionTypeLogic inventoryTransactionTypeLogic;

    @Inject
    LanguageLogic languageLogic;

    
    /** Creates a new instance of EditInventoryDispositionDescriptionCommand */
    public EditInventoryDispositionDescriptionCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    public EditInventoryDispositionDescriptionResult getResult() {
        return InventoryResultFactory.getEditInventoryDispositionDescriptionResult();
    }

    @Override
    public InventoryDispositionDescriptionEdit getEdit() {
        return InventoryEditFactory.getInventoryDispositionDescriptionEdit();
    }

    @Override
    public InventoryDispositionDescription getEntity(EditInventoryDispositionDescriptionResult result) {
        InventoryDispositionDescription inventoryDispositionDescription = null;
        var inventoryTransactionTypeName = spec.getInventoryTransactionTypeName();
        var inventoryTransactionType = inventoryTransactionTypeLogic.getInventoryTransactionTypeByName(this, inventoryTransactionTypeName);

        if(!hasExecutionErrors()) {
            var inventoryDispositionName = spec.getInventoryDispositionName();
            var inventoryDisposition = inventoryDispositionLogic.getInventoryDispositionByName(this,
                    inventoryTransactionType, inventoryDispositionName);

            if(!hasExecutionErrors()) {
                var languageIsoName = spec.getLanguageIsoName();
                var language = languageLogic.getLanguageByName(this, languageIsoName);

                if(!hasExecutionErrors()) {
                    if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
                        inventoryDispositionDescription = 
                                inventoryDispositionControl.getInventoryDispositionDescription(inventoryDisposition, language);
                    } else { // EditMode.UPDATE
                        inventoryDispositionDescription = 
                                inventoryDispositionControl.getInventoryDispositionDescriptionForUpdate(
                                        inventoryDisposition, language);
                    }

                    if(inventoryDispositionDescription == null) {
                        addExecutionError(ExecutionErrors.UnknownInventoryDispositionDescription.name(), inventoryTransactionTypeName,
                                inventoryDispositionName, languageIsoName);
                    }
                }
            }
        }

        return inventoryDispositionDescription;
    }

    @Override
    public InventoryDisposition getLockEntity(InventoryDispositionDescription inventoryDispositionDescription) {
        return inventoryDispositionDescription.getInventoryDisposition();
    }

    @Override
    public void fillInResult(EditInventoryDispositionDescriptionResult result,
            InventoryDispositionDescription inventoryDispositionDescription) {
        result.setInventoryDispositionDescription(
                inventoryDispositionControl.getInventoryDispositionDescriptionTransfer(getUserVisit(),
                        inventoryDispositionDescription));
    }

    @Override
    public void doLock(InventoryDispositionDescriptionEdit edit,
            InventoryDispositionDescription inventoryDispositionDescription) {
        edit.setDescription(inventoryDispositionDescription.getDescription());
    }

    @Override
    public void doUpdate(InventoryDispositionDescription inventoryDispositionDescription) {
        var inventoryDispositionDescriptionValue = 
                inventoryDispositionControl.getInventoryDispositionDescriptionValue(inventoryDispositionDescription);
        
        inventoryDispositionDescriptionValue.setDescription(edit.getDescription());

        inventoryDispositionControl.updateInventoryDispositionDescriptionFromValue(inventoryDispositionDescriptionValue,
                getPartyPK());
    }
    
}
