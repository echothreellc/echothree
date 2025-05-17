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

package com.echothree.control.user.uom.server.command;

import com.echothree.control.user.uom.common.form.GetUnitOfMeasureTypeChoicesForm;
import com.echothree.control.user.uom.common.result.UomResultFactory;
import com.echothree.model.control.item.server.control.ItemControl;
import com.echothree.model.control.uom.server.control.UomControl;
import com.echothree.model.control.vendor.server.control.VendorControl;
import com.echothree.model.data.uom.server.entity.UnitOfMeasureKind;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class GetUnitOfMeasureTypeChoicesCommand
        extends BaseSimpleCommand<GetUnitOfMeasureTypeChoicesForm> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("DefaultUnitOfMeasureTypeChoice", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("AllowNullChoice", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("UnitOfMeasureKindName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("UnitOfMeasureKindUseTypeName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("VendorName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("VendorItemName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("ItemName", FieldType.ENTITY_NAME, false, null, null)
                ));
    }
    
    /** Creates a new instance of GetUnitOfMeasureTypeChoicesCommand */
    public GetUnitOfMeasureTypeChoicesCommand() {
        super(null, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        var uomControl = Session.getModelController(UomControl.class);
        var result = UomResultFactory.getGetUnitOfMeasureTypeChoicesResult();
        var unitOfMeasureKindName = form.getUnitOfMeasureKindName();
        var unitOfMeasureKindUseTypeName = form.getUnitOfMeasureKindUseTypeName();
        var vendorName = form.getVendorName();
        var vendorItemName = form.getVendorItemName();
        var itemName = form.getItemName();
        var parameterCount = (unitOfMeasureKindName != null? 1: 0) + (unitOfMeasureKindUseTypeName != null? 1: 0)
                + (vendorName != null && vendorItemName != null? 1: 0) + (itemName != null? 1: 0);
        
        if(parameterCount == 1) {
            UnitOfMeasureKind unitOfMeasureKind = null;
            
            if(unitOfMeasureKindName != null) {
                unitOfMeasureKind = uomControl.getUnitOfMeasureKindByName(unitOfMeasureKindName);
                
                if(unitOfMeasureKind == null) {
                    addExecutionError(ExecutionErrors.UnknownUnitOfMeasureKindName.name(), unitOfMeasureKindName);
                }
            } else if(unitOfMeasureKindUseTypeName != null) {
                var unitOfMeasureKindUseType = uomControl.getUnitOfMeasureKindUseTypeByName(unitOfMeasureKindUseTypeName);
                
                if(unitOfMeasureKindUseType != null) {
                    if(!unitOfMeasureKindUseType.getAllowMultiple()) {
                        var unitOfMeasureKindUse = uomControl.getUnitOfMeasureKindUseByUnitOfMeasureKindUseType(unitOfMeasureKindUseType);
                        
                        if(unitOfMeasureKindUse != null) {
                            unitOfMeasureKind = unitOfMeasureKindUse.getUnitOfMeasureKind();
                        } else {
                            addExecutionError(ExecutionErrors.UnknownUnitOfMeasureKindUse.name());
                        }
                    } else {
                        addExecutionError(ExecutionErrors.InvalidUnitOfMeasureKindUseType.name());
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownUnitOfMeasureKindUseTypeName.name(), unitOfMeasureKindUseTypeName);
                }
            } else if(vendorName != null) {
                var vendorControl = Session.getModelController(VendorControl.class);
                var vendor = vendorControl.getVendorByName(vendorName);
                
                if(vendor != null) {
                    var vendorItem = vendorControl.getVendorItemByVendorPartyAndVendorItemName(vendor.getParty(), vendorItemName);
                    
                    if(vendorItem != null) {
                        unitOfMeasureKind = vendorItem.getLastDetail().getItem().getLastDetail().getUnitOfMeasureKind();
                    } else {
                        addExecutionError(ExecutionErrors.UnknownVendorItemName.name(), vendorItemName);
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownVendorName.name(), vendorName);
                }
            } else {
                var itemControl = Session.getModelController(ItemControl.class);
                var item = itemControl.getItemByName(itemName);
                
                if(item != null) {
                    unitOfMeasureKind = item.getLastDetail().getUnitOfMeasureKind();
                } else {
                    addExecutionError(ExecutionErrors.UnknownItemName.name(), itemName);
                }
            }
            
            if(!hasExecutionErrors()) {
                var defaultUnitOfMeasureTypeChoice = form.getDefaultUnitOfMeasureTypeChoice();
                var allowNullChoice = Boolean.parseBoolean(form.getAllowNullChoice());
                
                result.setUnitOfMeasureTypeChoices(uomControl.getUnitOfMeasureTypeChoices(defaultUnitOfMeasureTypeChoice,
                        getPreferredLanguage(), allowNullChoice, unitOfMeasureKind));
            }
        } else {
            addExecutionError(ExecutionErrors.InvalidParameterCount.name());
        }
        
        return result;
    }
    
}
