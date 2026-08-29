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

import com.echothree.control.user.inventory.common.form.GetInventoryTransactionRoleTypeDescriptionForm;
import com.echothree.control.user.inventory.common.result.InventoryResultFactory;
import com.echothree.model.control.inventory.server.control.InventoryTransactionRoleControl;
import com.echothree.model.control.inventory.server.logic.InventoryTransactionRoleTypeLogic;
import com.echothree.model.control.inventory.server.logic.InventoryTransactionTypeLogic;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.logic.LanguageLogic;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.inventory.server.entity.InventoryTransactionRoleTypeDescription;
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
public class GetInventoryTransactionRoleTypeDescriptionCommand
        extends BaseSingleEntityCommand<InventoryTransactionRoleTypeDescription, GetInventoryTransactionRoleTypeDescriptionForm> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), List.of(
                        new SecurityRoleDefinition(SecurityRoleGroups.InventoryTransactionRoleType.name(), SecurityRoles.Description.name())
                ))
        ));
        
        FORM_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("InventoryTransactionTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("InventoryTransactionRoleTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, true, null, null)
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

    /** Creates a new instance of GetInventoryTransactionRoleTypeDescriptionCommand */
    public GetInventoryTransactionRoleTypeDescriptionCommand() {
        super(COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, true);
    }
    
    @Override
    protected InventoryTransactionRoleTypeDescription getEntity() {
        InventoryTransactionRoleTypeDescription inventoryTransactionRoleTypeDescription = null;
        var inventoryTransactionTypeName = form.getInventoryTransactionTypeName();
        var inventoryTransactionType = inventoryTransactionTypeLogic.getInventoryTransactionTypeByName(this, inventoryTransactionTypeName);

        if(!hasExecutionErrors()) {
            var inventoryTransactionRoleTypeName = form.getInventoryTransactionRoleTypeName();
            var inventoryTransactionRoleType = inventoryTransactionRoleTypeLogic.getInventoryTransactionRoleTypeByName(this,
                    inventoryTransactionType, inventoryTransactionRoleTypeName);

            if(!hasExecutionErrors()) {
                var languageIsoName = form.getLanguageIsoName();
                var language = languageLogic.getLanguageByName(this, languageIsoName);

                if(!hasExecutionErrors()) {
                    inventoryTransactionRoleTypeDescription = inventoryTransactionRoleControl.getInventoryTransactionRoleTypeDescription(
                            inventoryTransactionRoleType, language);

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
    protected BaseResult getResult(InventoryTransactionRoleTypeDescription inventoryTransactionRoleTypeDescription) {
        var result = InventoryResultFactory.getGetInventoryTransactionRoleTypeDescriptionResult();

        if(inventoryTransactionRoleTypeDescription != null) {
            result.setInventoryTransactionRoleTypeDescription(
                    inventoryTransactionRoleControl.getInventoryTransactionRoleTypeDescriptionTransfer(getUserVisit(),
                            inventoryTransactionRoleTypeDescription));
        }

        return result;
    }
    
}
