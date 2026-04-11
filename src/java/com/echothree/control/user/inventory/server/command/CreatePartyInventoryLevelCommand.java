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

import com.echothree.control.user.inventory.common.form.CreatePartyInventoryLevelForm;
import com.echothree.control.user.inventory.server.command.common.PartyInventoryLevelUtil;
import com.echothree.model.control.inventory.server.control.InventoryControl;
import com.echothree.model.control.item.server.control.ItemControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.control.uom.server.logic.UnitOfMeasureTypeLogic;
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
public class CreatePartyInventoryLevelCommand
        extends BaseSimpleCommand<CreatePartyInventoryLevelForm> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), List.of(
                        new SecurityRoleDefinition(SecurityRoleGroups.PartyInventoryLevel.name(), SecurityRoles.Create.name())
                ))
        ));

        FORM_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("PartyName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("CompanyName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("WarehouseName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("ItemName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("InventoryConditionName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("MinimumInventoryUnitOfMeasureTypeName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("MinimumInventory", FieldType.UNSIGNED_LONG, false, null, null),
                new FieldDefinition("MaximumInventoryUnitOfMeasureTypeName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("MaximumInventory", FieldType.UNSIGNED_LONG, false, null, null),
                new FieldDefinition("ReorderQuantityUnitOfMeasureTypeName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("ReorderQuantity", FieldType.UNSIGNED_LONG, false, null, null)
        );
    }

    @Inject
    InventoryControl inventoryControl;

    @Inject
    ItemControl itemControl;

    @Inject
    UnitOfMeasureTypeLogic unitOfMeasureTypeLogic;

    @Inject
    PartyInventoryLevelUtil partyInventoryLevelUtil;

    /** Creates a new instance of CreatePartyInventoryLevelCommand */
    public CreatePartyInventoryLevelCommand() {
        super(COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        var party = partyInventoryLevelUtil.getParty(this, form);
        
        if(party != null) {
            var itemName = form.getItemName();
            var item = itemControl.getItemByName(itemName);
            
            if(item != null) {
                var partyTypeName = partyInventoryLevelUtil.getPartyTypeName(party);
                
                if(partyTypeName.equals(PartyTypes.COMPANY.name())) {
                    if(!party.equals(item.getLastDetail().getCompanyParty())) {
                        addExecutionError(ExecutionErrors.InvalidCompany.name());
                    }
                }
                
                if(!hasExecutionErrors()) {
                    var inventoryConditionName = form.getInventoryConditionName();
                    var inventoryCondition = inventoryControl.getInventoryConditionByName(inventoryConditionName);
                    
                    if(inventoryCondition != null) {
                        var unitOfMeasureKind = item.getLastDetail().getUnitOfMeasureKind();
                        var minimumInventory = unitOfMeasureTypeLogic.checkUnitOfMeasure(this, unitOfMeasureKind,
                                form.getMinimumInventory(), form.getMinimumInventoryUnitOfMeasureTypeName(),
                                null, ExecutionErrors.MissingRequiredMinimumInventory.name(), null, ExecutionErrors.MissingRequiredMinimumInventoryUnitOfMeasureTypeName.name(),
                                null, ExecutionErrors.UnknownMinimumInventoryUnitOfMeasureTypeName.name());

                        if(!hasExecutionErrors()) {
                            var maximumInventory = unitOfMeasureTypeLogic.checkUnitOfMeasure(this, unitOfMeasureKind,
                                    form.getMaximumInventory(), form.getMaximumInventoryUnitOfMeasureTypeName(),
                                    null, ExecutionErrors.MissingRequiredMaximumInventory.name(), null, ExecutionErrors.MissingRequiredMaximumInventoryUnitOfMeasureTypeName.name(),
                                    null, ExecutionErrors.UnknownMaximumInventoryUnitOfMeasureTypeName.name());

                            if(!hasExecutionErrors()) {
                                var reorderQuantity = unitOfMeasureTypeLogic.checkUnitOfMeasure(this, unitOfMeasureKind,
                                        form.getReorderQuantity(), form.getReorderQuantityUnitOfMeasureTypeName(),
                                        null, ExecutionErrors.MissingRequiredReorderQuantity.name(), null, ExecutionErrors.MissingRequiredReorderQuantityUnitOfMeasureTypeName.name(),
                                        null, ExecutionErrors.UnknownReorderQuantityUnitOfMeasureTypeName.name());

                                if(!hasExecutionErrors()) {
                                    var partyInventoryLevel = inventoryControl.getPartyInventoryLevel(party, item, inventoryCondition);
                                    
                                    if(partyInventoryLevel == null) {
                                        inventoryControl.createPartyInventoryLevel(party, item, inventoryCondition, minimumInventory, maximumInventory,
                                                reorderQuantity, getPartyPK());
                                    } else {
                                        addExecutionError(ExecutionErrors.DuplicatePartyInventoryLevel.name(), party.getLastDetail().getPartyName(), itemName,
                                                inventoryConditionName);
                                    }
                                }
                            }
                        }
                    } else {
                        addExecutionError(ExecutionErrors.UnknownInventoryConditionName.name(), inventoryConditionName);
                    }
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownItemName.name(), itemName);
            }
        }
        
        return null;
    }
    
}
