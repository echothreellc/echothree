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
import com.echothree.control.user.inventory.common.edit.InventoryDispositionAdjustmentDescriptionEdit;
import com.echothree.control.user.inventory.common.form.EditInventoryDispositionAdjustmentDescriptionForm;
import com.echothree.control.user.inventory.common.result.EditInventoryDispositionAdjustmentDescriptionResult;
import com.echothree.control.user.inventory.common.result.InventoryResultFactory;
import com.echothree.control.user.inventory.common.spec.InventoryDispositionAdjustmentDescriptionSpec;
import com.echothree.model.control.inventory.server.control.InventoryDispositionAdjustmentControl;
import com.echothree.model.control.inventory.server.logic.InventoryDispositionAdjustmentLogic;
import com.echothree.model.control.inventory.server.logic.InventoryTransactionTypeLogic;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.logic.LanguageLogic;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.inventory.server.entity.InventoryDispositionAdjustment;
import com.echothree.model.data.inventory.server.entity.InventoryDispositionAdjustmentDescription;
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
public class EditInventoryDispositionAdjustmentDescriptionCommand
        extends BaseAbstractEditCommand<InventoryDispositionAdjustmentDescriptionSpec, InventoryDispositionAdjustmentDescriptionEdit,
                EditInventoryDispositionAdjustmentDescriptionResult, InventoryDispositionAdjustmentDescription, InventoryDispositionAdjustment> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), List.of(
                        new SecurityRoleDefinition(SecurityRoleGroups.InventoryDispositionAdjustment.name(), SecurityRoles.Description.name())
                ))
        ));
        
        SPEC_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("InventoryTransactionTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("InventoryDispositionName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("InventoryDispositionAdjustmentName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, true, null, null)
        );
        
        EDIT_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("Description", FieldType.STRING, true, 1L, 132L)
        );
    }

    @Inject
    InventoryDispositionAdjustmentControl inventoryDispositionAdjustmentControl;

    @Inject
    InventoryDispositionAdjustmentLogic inventoryDispositionAdjustmentLogic;

    @Inject
    InventoryTransactionTypeLogic inventoryTransactionTypeLogic;

    @Inject
    LanguageLogic languageLogic;

    
    /** Creates a new instance of EditInventoryDispositionAdjustmentDescriptionCommand */
    public EditInventoryDispositionAdjustmentDescriptionCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    public EditInventoryDispositionAdjustmentDescriptionResult getResult() {
        return InventoryResultFactory.getEditInventoryDispositionAdjustmentDescriptionResult();
    }

    @Override
    public InventoryDispositionAdjustmentDescriptionEdit getEdit() {
        return InventoryEditFactory.getInventoryDispositionAdjustmentDescriptionEdit();
    }

    @Override
    public InventoryDispositionAdjustmentDescription getEntity(EditInventoryDispositionAdjustmentDescriptionResult result) {
        InventoryDispositionAdjustmentDescription inventoryDispositionAdjustmentDescription = null;
        var inventoryTransactionTypeName = spec.getInventoryTransactionTypeName();
        inventoryTransactionTypeLogic.getInventoryTransactionTypeByName(this, inventoryTransactionTypeName);

        if(!hasExecutionErrors()) {
            var inventoryDispositionAdjustmentName = spec.getInventoryDispositionAdjustmentName();
            var inventoryDispositionAdjustment = inventoryDispositionAdjustmentLogic.getInventoryDispositionAdjustmentByName(this,
                    spec.getInventoryTransactionTypeName(), spec.getInventoryDispositionName(), inventoryDispositionAdjustmentName);

            if(!hasExecutionErrors()) {
                var languageIsoName = spec.getLanguageIsoName();
                var language = languageLogic.getLanguageByName(this, languageIsoName);

                if(!hasExecutionErrors()) {
                    if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
                        inventoryDispositionAdjustmentDescription = inventoryDispositionAdjustmentControl
                                .getInventoryDispositionAdjustmentDescription(inventoryDispositionAdjustment, language);
                    } else { // EditMode.UPDATE
                        inventoryDispositionAdjustmentDescription = 
                                inventoryDispositionAdjustmentControl.getInventoryDispositionAdjustmentDescriptionForUpdate(
                                        inventoryDispositionAdjustment, language);
                    }

                    if(inventoryDispositionAdjustmentDescription == null) {
                        addExecutionError(ExecutionErrors.UnknownInventoryDispositionAdjustmentDescription.name(), inventoryTransactionTypeName,
                                inventoryDispositionAdjustmentName, languageIsoName);
                    }
                }
            }
        }

        return inventoryDispositionAdjustmentDescription;
    }

    @Override
    public InventoryDispositionAdjustment getLockEntity(InventoryDispositionAdjustmentDescription inventoryDispositionAdjustmentDescription) {
        return inventoryDispositionAdjustmentDescription.getInventoryDispositionAdjustment();
    }

    @Override
    public void fillInResult(EditInventoryDispositionAdjustmentDescriptionResult result,
            InventoryDispositionAdjustmentDescription inventoryDispositionAdjustmentDescription) {
        result.setInventoryDispositionAdjustmentDescription(
                inventoryDispositionAdjustmentControl.getInventoryDispositionAdjustmentDescriptionTransfer(getUserVisit(),
                        inventoryDispositionAdjustmentDescription));
    }

    @Override
    public void doLock(InventoryDispositionAdjustmentDescriptionEdit edit,
            InventoryDispositionAdjustmentDescription inventoryDispositionAdjustmentDescription) {
        edit.setDescription(inventoryDispositionAdjustmentDescription.getDescription());
    }

    @Override
    public void doUpdate(InventoryDispositionAdjustmentDescription inventoryDispositionAdjustmentDescription) {
        var inventoryDispositionAdjustmentDescriptionValue = 
                inventoryDispositionAdjustmentControl.getInventoryDispositionAdjustmentDescriptionValue(inventoryDispositionAdjustmentDescription);
        
        inventoryDispositionAdjustmentDescriptionValue.setDescription(edit.getDescription());

        inventoryDispositionAdjustmentControl.updateInventoryDispositionAdjustmentDescriptionFromValue(inventoryDispositionAdjustmentDescriptionValue,
                getPartyPK());
    }
    
}
