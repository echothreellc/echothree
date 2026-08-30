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

import com.echothree.control.user.inventory.common.form.GetInventoryDispositionAdjustmentDescriptionForm;
import com.echothree.control.user.inventory.common.result.InventoryResultFactory;
import com.echothree.model.control.inventory.server.control.InventoryDispositionAdjustmentControl;
import com.echothree.model.control.inventory.server.logic.InventoryDispositionAdjustmentLogic;
import com.echothree.model.control.inventory.server.logic.InventoryTransactionTypeLogic;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.logic.LanguageLogic;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.inventory.server.entity.InventoryDispositionAdjustmentDescription;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseSingleEntityCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class GetInventoryDispositionAdjustmentDescriptionCommand
        extends BaseSingleEntityCommand<InventoryDispositionAdjustmentDescription, GetInventoryDispositionAdjustmentDescriptionForm> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), List.of(
                        new SecurityRoleDefinition(SecurityRoleGroups.InventoryDispositionAdjustment.name(), SecurityRoles.Description.name())
                ))
        ));
        
        FORM_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("InventoryTransactionTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("InventoryDispositionName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("InventoryDispositionAdjustmentName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, true, null, null)
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

    /** Creates a new instance of GetInventoryDispositionAdjustmentDescriptionCommand */
    public GetInventoryDispositionAdjustmentDescriptionCommand() {
        super(COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, true);
    }
    
    @Override
    protected InventoryDispositionAdjustmentDescription getEntity() {
        InventoryDispositionAdjustmentDescription inventoryDispositionAdjustmentDescription = null;
        var inventoryTransactionTypeName = form.getInventoryTransactionTypeName();
        inventoryTransactionTypeLogic.getInventoryTransactionTypeByName(this, inventoryTransactionTypeName);

        if(!hasExecutionErrors()) {
            var inventoryDispositionAdjustmentName = form.getInventoryDispositionAdjustmentName();
            var inventoryDispositionAdjustment = inventoryDispositionAdjustmentLogic.getInventoryDispositionAdjustmentByName(this,
                    form.getInventoryTransactionTypeName(), form.getInventoryDispositionName(), inventoryDispositionAdjustmentName);

            if(!hasExecutionErrors()) {
                var languageIsoName = form.getLanguageIsoName();
                var language = languageLogic.getLanguageByName(this, languageIsoName);

                if(!hasExecutionErrors()) {
                    inventoryDispositionAdjustmentDescription = inventoryDispositionAdjustmentControl.getInventoryDispositionAdjustmentDescription(
                            inventoryDispositionAdjustment, language);

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
    protected BaseResult getResult(InventoryDispositionAdjustmentDescription inventoryDispositionAdjustmentDescription) {
        var result = InventoryResultFactory.getGetInventoryDispositionAdjustmentDescriptionResult();

        if(inventoryDispositionAdjustmentDescription != null) {
            result.setInventoryDispositionAdjustmentDescription(
                    inventoryDispositionAdjustmentControl.getInventoryDispositionAdjustmentDescriptionTransfer(getUserVisit(),
                            inventoryDispositionAdjustmentDescription));
        }

        return result;
    }
    
}
