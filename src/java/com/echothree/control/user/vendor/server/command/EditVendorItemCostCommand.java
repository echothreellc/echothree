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

import com.echothree.control.user.vendor.common.edit.VendorEditFactory;
import com.echothree.control.user.vendor.common.edit.VendorItemCostEdit;
import com.echothree.control.user.vendor.common.form.EditVendorItemCostForm;
import com.echothree.control.user.vendor.common.result.VendorResultFactory;
import com.echothree.control.user.vendor.common.spec.VendorItemCostSpec;
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
import com.echothree.util.common.command.EditMode;
import com.echothree.util.common.form.BaseForm;
import com.echothree.util.server.control.BaseEditCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
import com.echothree.util.server.string.AmountUtils;
import com.echothree.util.server.validation.Validator;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.enterprise.context.Dependent;

@Dependent
public class EditVendorItemCostCommand
        extends BaseEditCommand<VendorItemCostSpec, VendorItemCostEdit> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                    new SecurityRoleDefinition(SecurityRoleGroups.VendorItemCost.name(), SecurityRoles.Edit.name())
                    )))
                )));
        
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("VendorName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("VendorItemName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("InventoryConditionName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("UnitOfMeasureTypeName", FieldType.ENTITY_NAME, true, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("UnitCost", FieldType.UNSIGNED_COST_UNIT, true, null, null)
                ));
    }
    
    /** Creates a new instance of EditVendorItemCostCommand */
    public EditVendorItemCostCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    protected void setupValidatorForEdit(Validator validator, BaseForm specForm) {
        var vendorControl = Session.getModelController(VendorControl.class);
        var vendorName = spec.getVendorName();
        var vendor = vendorControl.getVendorByName(vendorName);
        
        if(vendor != null) {
            var vendorParty = vendor.getParty();
            
            validator.setCurrency(getPreferredCurrency(vendorParty));
        }
    }
    
    @Override
    protected BaseResult execute() {
        var vendorControl = Session.getModelController(VendorControl.class);
        var result = VendorResultFactory.getEditVendorItemCostResult();
        var vendorName = spec.getVendorName();
        var vendor = vendorControl.getVendorByName(vendorName);
        
        if(vendor != null) {
            var vendorParty = vendor.getParty();
            var vendorItemName = spec.getVendorItemName();
            var vendorItem = vendorControl.getVendorItemByVendorPartyAndVendorItemName(vendorParty, vendorItemName);
            
            if(vendorItem != null) {
                var inventoryControl = Session.getModelController(InventoryControl.class);
                var inventoryConditionName = spec.getInventoryConditionName();
                var inventoryCondition = inventoryControl.getInventoryConditionByName(inventoryConditionName);
                
                if(inventoryCondition != null) {
                    var uomControl = Session.getModelController(UomControl.class);
                    var unitOfMeasureKind = vendorItem.getLastDetail().getItem().getLastDetail().getUnitOfMeasureKind();
                    var unitOfMeasureTypeName = spec.getUnitOfMeasureTypeName();
                    var unitOfMeasureType = uomControl.getUnitOfMeasureTypeByName(unitOfMeasureKind, unitOfMeasureTypeName);
                    
                    if(unitOfMeasureType != null) {
                        if(editMode.equals(EditMode.LOCK)) {
                            var vendorItemCost = vendorControl.getVendorItemCost(vendorItem, inventoryCondition,
                                    unitOfMeasureType);
                            
                            if(vendorItemCost != null) {
                                result.setVendorItemCost(vendorControl.getVendorItemCostTransfer(getUserVisit(), vendorItemCost));
                                
                                if(lockEntity(vendorItem)) {
                                    var edit = VendorEditFactory.getVendorItemCostEdit();
                                    
                                    result.setEdit(edit);
                                    edit.setUnitCost(AmountUtils.getInstance().formatCostUnit(getPreferredCurrency(vendorParty),
                                            vendorItemCost.getUnitCost()));
                                } else {
                                    addExecutionError(ExecutionErrors.EntityLockFailed.name());
                                }
                                
                                result.setEntityLock(getEntityLockTransfer(vendorItem));
                            } else {
                                addExecutionError(ExecutionErrors.UnknownVendorItemCost.name());
                            }
                        } else if(editMode.equals(EditMode.UPDATE)) {
                            var vendorItemCostValue = vendorControl.getVendorItemCostValueForUpdate(vendorItem,
                                    inventoryCondition, unitOfMeasureType);
                            
                            if(vendorItemCostValue != null) {
                                if(lockEntityForUpdate(vendorItem)) {
                                    try {
                                        vendorItemCostValue.setUnitCost(Long.valueOf(edit.getUnitCost()));
                                        
                                        vendorControl.updateVendorItemCostFromValue(vendorItemCostValue, getPartyPK());
                                    } finally {
                                        unlockEntity(vendorItem);
                                    }
                                } else {
                                    addExecutionError(ExecutionErrors.EntityLockStale.name());
                                }
                            } else {
                                addExecutionError(ExecutionErrors.UnknownVendorItemCost.name());
                            }
                        }
                    } else {
                        addExecutionError(ExecutionErrors.UnknownUnitOfMeasureTypeName.name(), unitOfMeasureTypeName);
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
        
        return result;
    }
    
}
