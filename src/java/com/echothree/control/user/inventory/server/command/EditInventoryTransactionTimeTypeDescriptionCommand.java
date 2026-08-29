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
import com.echothree.control.user.inventory.common.edit.InventoryTransactionTimeTypeDescriptionEdit;
import com.echothree.control.user.inventory.common.form.EditInventoryTransactionTimeTypeDescriptionForm;
import com.echothree.control.user.inventory.common.result.EditInventoryTransactionTimeTypeDescriptionResult;
import com.echothree.control.user.inventory.common.result.InventoryResultFactory;
import com.echothree.control.user.inventory.common.spec.InventoryTransactionTimeTypeDescriptionSpec;
import com.echothree.model.control.inventory.server.control.InventoryTransactionTimeControl;
import com.echothree.model.control.inventory.server.logic.InventoryTransactionTimeTypeLogic;
import com.echothree.model.control.inventory.server.logic.InventoryTransactionTypeLogic;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.logic.LanguageLogic;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.inventory.server.entity.InventoryTransactionTimeType;
import com.echothree.model.data.inventory.server.entity.InventoryTransactionTimeTypeDescription;
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
public class EditInventoryTransactionTimeTypeDescriptionCommand
        extends BaseAbstractEditCommand<InventoryTransactionTimeTypeDescriptionSpec, InventoryTransactionTimeTypeDescriptionEdit,
                EditInventoryTransactionTimeTypeDescriptionResult, InventoryTransactionTimeTypeDescription, InventoryTransactionTimeType> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), List.of(
                        new SecurityRoleDefinition(SecurityRoleGroups.InventoryTransactionTimeType.name(), SecurityRoles.Description.name())
                ))
        ));
        
        SPEC_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("InventoryTransactionTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("InventoryTransactionTimeTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, true, null, null)
        );
        
        EDIT_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("Description", FieldType.STRING, true, 1L, 132L)
        );
    }

    @Inject
    InventoryTransactionTimeControl inventoryTransactionTimeControl;

    @Inject
    InventoryTransactionTimeTypeLogic inventoryTransactionTimeTypeLogic;

    @Inject
    InventoryTransactionTypeLogic inventoryTransactionTypeLogic;

    @Inject
    LanguageLogic languageLogic;

    
    /** Creates a new instance of EditInventoryTransactionTimeTypeDescriptionCommand */
    public EditInventoryTransactionTimeTypeDescriptionCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    public EditInventoryTransactionTimeTypeDescriptionResult getResult() {
        return InventoryResultFactory.getEditInventoryTransactionTimeTypeDescriptionResult();
    }

    @Override
    public InventoryTransactionTimeTypeDescriptionEdit getEdit() {
        return InventoryEditFactory.getInventoryTransactionTimeTypeDescriptionEdit();
    }

    @Override
    public InventoryTransactionTimeTypeDescription getEntity(EditInventoryTransactionTimeTypeDescriptionResult result) {
        InventoryTransactionTimeTypeDescription inventoryTransactionTimeTypeDescription = null;
        var inventoryTransactionTypeName = spec.getInventoryTransactionTypeName();
        var inventoryTransactionType = inventoryTransactionTypeLogic.getInventoryTransactionTypeByName(this, inventoryTransactionTypeName);

        if(!hasExecutionErrors()) {
            var inventoryTransactionTimeTypeName = spec.getInventoryTransactionTimeTypeName();
            var inventoryTransactionTimeType = inventoryTransactionTimeTypeLogic.getInventoryTransactionTimeTypeByName(this,
                    inventoryTransactionType, inventoryTransactionTimeTypeName);

            if(!hasExecutionErrors()) {
                var languageIsoName = spec.getLanguageIsoName();
                var language = languageLogic.getLanguageByName(this, languageIsoName);

                if(!hasExecutionErrors()) {
                    if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
                        inventoryTransactionTimeTypeDescription = 
                                inventoryTransactionTimeControl.getInventoryTransactionTimeTypeDescription(inventoryTransactionTimeType, language);
                    } else { // EditMode.UPDATE
                        inventoryTransactionTimeTypeDescription = 
                                inventoryTransactionTimeControl.getInventoryTransactionTimeTypeDescriptionForUpdate(
                                        inventoryTransactionTimeType, language);
                    }

                    if(inventoryTransactionTimeTypeDescription == null) {
                        addExecutionError(ExecutionErrors.UnknownInventoryTransactionTimeTypeDescription.name(), inventoryTransactionTypeName,
                                inventoryTransactionTimeTypeName, languageIsoName);
                    }
                }
            }
        }

        return inventoryTransactionTimeTypeDescription;
    }

    @Override
    public InventoryTransactionTimeType getLockEntity(InventoryTransactionTimeTypeDescription inventoryTransactionTimeTypeDescription) {
        return inventoryTransactionTimeTypeDescription.getInventoryTransactionTimeType();
    }

    @Override
    public void fillInResult(EditInventoryTransactionTimeTypeDescriptionResult result,
            InventoryTransactionTimeTypeDescription inventoryTransactionTimeTypeDescription) {
        result.setInventoryTransactionTimeTypeDescription(
                inventoryTransactionTimeControl.getInventoryTransactionTimeTypeDescriptionTransfer(getUserVisit(),
                        inventoryTransactionTimeTypeDescription));
    }

    @Override
    public void doLock(InventoryTransactionTimeTypeDescriptionEdit edit,
            InventoryTransactionTimeTypeDescription inventoryTransactionTimeTypeDescription) {
        edit.setDescription(inventoryTransactionTimeTypeDescription.getDescription());
    }

    @Override
    public void doUpdate(InventoryTransactionTimeTypeDescription inventoryTransactionTimeTypeDescription) {
        var inventoryTransactionTimeTypeDescriptionValue = 
                inventoryTransactionTimeControl.getInventoryTransactionTimeTypeDescriptionValue(inventoryTransactionTimeTypeDescription);
        
        inventoryTransactionTimeTypeDescriptionValue.setDescription(edit.getDescription());

        inventoryTransactionTimeControl.updateInventoryTransactionTimeTypeDescriptionFromValue(inventoryTransactionTimeTypeDescriptionValue,
                getPartyPK());
    }
    
}
