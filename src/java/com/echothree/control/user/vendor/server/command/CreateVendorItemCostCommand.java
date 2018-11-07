// --------------------------------------------------------------------------------
// Copyright 2002-2018 Echo Three, LLC
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
import com.echothree.model.control.inventory.server.InventoryControl;
import com.echothree.model.control.party.common.PartyConstants;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.control.uom.server.UomControl;
import com.echothree.model.control.vendor.server.VendorControl;
import com.echothree.model.data.inventory.server.entity.InventoryCondition;
import com.echothree.model.data.inventory.server.entity.InventoryConditionUse;
import com.echothree.model.data.inventory.server.entity.InventoryConditionUseType;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.model.data.uom.server.entity.UnitOfMeasureKind;
import com.echothree.model.data.uom.server.entity.UnitOfMeasureType;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.model.data.vendor.server.entity.Vendor;
import com.echothree.model.data.vendor.server.entity.VendorItem;
import com.echothree.model.data.vendor.server.entity.VendorItemCost;
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
                new PartyTypeDefinition(PartyConstants.PartyType_UTILITY, null),
                new PartyTypeDefinition(PartyConstants.PartyType_EMPLOYEE, Collections.unmodifiableList(Arrays.asList(
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
    public CreateVendorItemCostCommand(UserVisitPK userVisitPK, CreateVendorItemCostForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        VendorControl vendorControl = (VendorControl)Session.getModelController(VendorControl.class);
        String vendorName = form.getVendorName();
        Vendor vendor = vendorControl.getVendorByName(vendorName);
        
        if(vendor != null) {
            Party vendorParty = vendor.getParty();
            String vendorItemName = form.getVendorItemName();
            VendorItem vendorItem = vendorControl.getVendorItemByVendorPartyAndVendorItemName(vendorParty, vendorItemName);
            
            if(vendorItem != null) {
                InventoryControl inventoryControl = (InventoryControl)Session.getModelController(InventoryControl.class);
                String inventoryConditionName = form.getInventoryConditionName();
                InventoryCondition inventoryCondition = inventoryControl.getInventoryConditionByName(inventoryConditionName);
                
                if(inventoryCondition != null) {
                    InventoryConditionUseType inventoryConditionUseType = inventoryControl.getInventoryConditionUseTypeByName(InventoryConstants.InventoryConditionUseType_PURCHASE_ORDER);
                    InventoryConditionUse inventoryConditionUse = inventoryControl.getInventoryConditionUse(inventoryConditionUseType,
                            inventoryCondition);
                    
                    if(inventoryConditionUse != null) {
                        UomControl uomControl = (UomControl)Session.getModelController(UomControl.class);
                        UnitOfMeasureKind unitOfMeasureKind = vendorItem.getLastDetail().getItem().getLastDetail().getUnitOfMeasureKind();
                        String unitOfMeasureTypeName = form.getUnitOfMeasureTypeName();
                        UnitOfMeasureType unitOfMeasureType = uomControl.getUnitOfMeasureTypeByName(unitOfMeasureKind, unitOfMeasureTypeName);
                        
                        if(unitOfMeasureType != null) {
                            VendorItemCost vendorItemCost = vendorControl.getVendorItemCost(vendorItem, inventoryCondition,
                                    unitOfMeasureType);
                            
                            if(vendorItemCost == null) {
                                Long unitCost = Long.valueOf(form.getUnitCost());
                                
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
