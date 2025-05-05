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

package com.echothree.control.user.vendor.server.command;

import com.echothree.control.user.vendor.common.form.CreateVendorItemCostForm;
import com.echothree.model.control.inventory.common.InventoryConstants;
import com.echothree.model.control.inventory.server.control.InventoryControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.control.uom.server.control.UomControl;
import com.echothree.model.control.vendor.server.control.VendorControl;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CreateVendorItemCostCommand
        extends BaseSimpleCommand<CreateVendorItemCostForm> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.VendorItemCost.name(), SecurityRoles.Create.name())
                        )))
                )));
        
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("VendorName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("VendorItemName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("InventoryConditionName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("UnitOfMeasureTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("UnitCost:VendorName,VendorName", FieldType.UNSIGNED_COST_UNIT, true, null, null)
                ));
    }
    
    /** Creates a new instance of CreateVendorItemCostCommand */
    public CreateVendorItemCostCommand() {
        super(COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        var vendorControl = Session.getModelController(VendorControl.class);
        var vendorName = form.getVendorName();
        var vendor = vendorControl.getVendorByName(vendorName);
        
        if(vendor != null) {
            var vendorParty = vendor.getParty();
            var vendorItemName = form.getVendorItemName();
            var vendorItem = vendorControl.getVendorItemByVendorPartyAndVendorItemName(vendorParty, vendorItemName);
            
            if(vendorItem != null) {
                var inventoryControl = Session.getModelController(InventoryControl.class);
                var inventoryConditionName = form.getInventoryConditionName();
                var inventoryCondition = inventoryControl.getInventoryConditionByName(inventoryConditionName);
                
                if(inventoryCondition != null) {
                    var inventoryConditionUseType = inventoryControl.getInventoryConditionUseTypeByName(InventoryConstants.InventoryConditionUseType_PURCHASE_ORDER);
                    var inventoryConditionUse = inventoryControl.getInventoryConditionUse(inventoryConditionUseType,
                            inventoryCondition);
                    
                    if(inventoryConditionUse != null) {
                        var uomControl = Session.getModelController(UomControl.class);
                        var unitOfMeasureKind = vendorItem.getLastDetail().getItem().getLastDetail().getUnitOfMeasureKind();
                        var unitOfMeasureTypeName = form.getUnitOfMeasureTypeName();
                        var unitOfMeasureType = uomControl.getUnitOfMeasureTypeByName(unitOfMeasureKind, unitOfMeasureTypeName);
                        
                        if(unitOfMeasureType != null) {
                            var vendorItemCost = vendorControl.getVendorItemCost(vendorItem, inventoryCondition,
                                    unitOfMeasureType);
                            
                            if(vendorItemCost == null) {
                                var unitCost = Long.valueOf(form.getUnitCost());
                                
                                vendorControl.createVendorItemCost(vendorItem, inventoryCondition, unitOfMeasureType, unitCost,
                                        getPartyPK());
                            } else {
                                addExecutionError(ExecutionErrors.DuplicateVendorItemCost.name());
                            }
                        } else {
                            addExecutionError(ExecutionErrors.UnknownUnitOfMeasureTypeName.name(), unitOfMeasureTypeName);
                        }
                    } else {
                        addExecutionError(ExecutionErrors.InvalidInventoryCondition.name(), inventoryConditionName);
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownInventoryConditionName.name(), inventoryConditionName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownVendorItemName.name(), vendorItemName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownVendorName.name(), vendorName);
        }
        
        return null;
    }
    
}
