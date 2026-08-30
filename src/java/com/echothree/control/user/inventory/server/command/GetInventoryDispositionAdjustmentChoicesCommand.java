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

import com.echothree.control.user.inventory.common.form.GetInventoryDispositionAdjustmentChoicesForm;
import com.echothree.control.user.inventory.common.result.InventoryResultFactory;
import com.echothree.model.control.inventory.server.control.InventoryDispositionAdjustmentControl;
import com.echothree.model.control.inventory.server.logic.InventoryDispositionAdjustmentLogic;
import com.echothree.model.control.inventory.server.logic.InventoryTransactionTypeLogic;
import com.echothree.model.control.inventory.server.logic.InventoryDispositionLogic;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class GetInventoryDispositionAdjustmentChoicesCommand
        extends BaseSimpleCommand<GetInventoryDispositionAdjustmentChoicesForm> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), List.of(
                        new SecurityRoleDefinition(SecurityRoleGroups.InventoryDispositionAdjustment.name(), SecurityRoles.Choices.name())
                ))
        ));
        
        FORM_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("InventoryTransactionTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("InventoryDispositionName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("DefaultInventoryDispositionAdjustmentChoice", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("AllowNullChoice", FieldType.BOOLEAN, true, null, null)
        );
    }

    @Inject
    InventoryDispositionAdjustmentControl inventoryDispositionAdjustmentControl;

    @Inject
    InventoryTransactionTypeLogic inventoryTransactionTypeLogic;
    @Inject InventoryDispositionLogic inventoryDispositionLogic;

    /** Creates a new instance of GetInventoryDispositionAdjustmentChoicesCommand */
    public GetInventoryDispositionAdjustmentChoicesCommand() {
        super(COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        var result = InventoryResultFactory.getGetInventoryDispositionAdjustmentChoicesResult();
        var inventoryTransactionTypeName = form.getInventoryTransactionTypeName();
        var inventoryTransactionType = inventoryTransactionTypeLogic.getInventoryTransactionTypeByName(this, inventoryTransactionTypeName);

        if(!hasExecutionErrors()) {
            var inventoryDisposition = inventoryDispositionLogic.getInventoryDispositionByName(this, inventoryTransactionType,
                    form.getInventoryDispositionName());
            var defaultInventoryDispositionAdjustmentChoice = form.getDefaultInventoryDispositionAdjustmentChoice();
            var allowNullChoice = Boolean.parseBoolean(form.getAllowNullChoice());

            result.setInventoryDispositionAdjustmentChoices(
                    inventoryDispositionAdjustmentControl.getInventoryDispositionAdjustmentChoices(
                            defaultInventoryDispositionAdjustmentChoice, getPreferredLanguage(), allowNullChoice,
                    inventoryDisposition));
        }

        return result;
    }
    
}
