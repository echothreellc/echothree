// --------------------------------------------------------------------------------
// Copyright 2002-2019 Echo Three, LLC
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
import com.echothree.control.user.uom.common.result.GetUnitOfMeasureTypeChoicesResult;
import com.echothree.control.user.uom.common.result.UomResultFactory;
import com.echothree.model.control.item.server.ItemControl;
import com.echothree.model.control.uom.server.UomControl;
import com.echothree.model.control.vendor.server.VendorControl;
import com.echothree.model.data.item.server.entity.Item;
import com.echothree.model.data.uom.server.entity.UnitOfMeasureKind;
import com.echothree.model.data.uom.server.entity.UnitOfMeasureKindUse;
import com.echothree.model.data.uom.server.entity.UnitOfMeasureKindUseType;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.model.data.vendor.server.entity.Vendor;
import com.echothree.model.data.vendor.server.entity.VendorItem;
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
    public GetUnitOfMeasureTypeChoicesCommand(UserVisitPK userVisitPK, GetUnitOfMeasureTypeChoicesForm form) {
        super(userVisitPK, form, null, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        var uomControl = (UomControl)Session.getModelController(UomControl.class);
        GetUnitOfMeasureTypeChoicesResult result = UomResultFactory.getGetUnitOfMeasureTypeChoicesResult();
        String unitOfMeasureKindName = form.getUnitOfMeasureKindName();
        String unitOfMeasureKindUseTypeName = form.getUnitOfMeasureKindUseTypeName();
        String vendorName = form.getVendorName();
        String vendorItemName = form.getVendorItemName();
        String itemName = form.getItemName();
        int parameterCount = (unitOfMeasureKindName != null? 1: 0) + (unitOfMeasureKindUseTypeName != null? 1: 0)
                + (vendorName != null && vendorItemName != null? 1: 0) + (itemName != null? 1: 0);
        
        if(parameterCount == 1) {
            UnitOfMeasureKind unitOfMeasureKind = null;
            
            if(unitOfMeasureKindName != null) {
                unitOfMeasureKind = uomControl.getUnitOfMeasureKindByName(unitOfMeasureKindName);
                
                if(unitOfMeasureKind == null) {
                    addExecutionError(ExecutionErrors.UnknownUnitOfMeasureKindName.name(), unitOfMeasureKindName);
                }
            } else if(unitOfMeasureKindUseTypeName != null) {
                UnitOfMeasureKindUseType unitOfMeasureKindUseType = uomControl.getUnitOfMeasureKindUseTypeByName(unitOfMeasureKindUseTypeName);
                
                if(unitOfMeasureKindUseType != null) {
                    if(!unitOfMeasureKindUseType.getAllowMultiple()) {
                        UnitOfMeasureKindUse unitOfMeasureKindUse = uomControl.getUnitOfMeasureKindUseByUnitOfMeasureKindUseType(unitOfMeasureKindUseType);
                        
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
                var vendorControl = (VendorControl)Session.getModelController(VendorControl.class);
                Vendor vendor = vendorControl.getVendorByName(vendorName);
                
                if(vendor != null) {
                    VendorItem vendorItem = vendorControl.getVendorItemByVendorPartyAndVendorItemName(vendor.getParty(), vendorItemName);
                    
                    if(vendorItem != null) {
                        unitOfMeasureKind = vendorItem.getLastDetail().getItem().getLastDetail().getUnitOfMeasureKind();
                    } else {
                        addExecutionError(ExecutionErrors.UnknownVendorItemName.name(), vendorItemName);
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownVendorName.name(), vendorName);
                }
            } else {
                var itemControl = (ItemControl)Session.getModelController(ItemControl.class);
                Item item = itemControl.getItemByName(itemName);
                
                if(item != null) {
                    unitOfMeasureKind = item.getLastDetail().getUnitOfMeasureKind();
                } else {
                    addExecutionError(ExecutionErrors.UnknownItemName.name(), itemName);
                }
            }
            
            if(!hasExecutionErrors()) {
                String defaultUnitOfMeasureTypeChoice = form.getDefaultUnitOfMeasureTypeChoice();
                boolean allowNullChoice = Boolean.parseBoolean(form.getAllowNullChoice());
                
                result.setUnitOfMeasureTypeChoices(uomControl.getUnitOfMeasureTypeChoices(defaultUnitOfMeasureTypeChoice,
                        getPreferredLanguage(), allowNullChoice, unitOfMeasureKind));
            }
        } else {
            addExecutionError(ExecutionErrors.InvalidParameterCount.name());
        }
        
        return result;
    }
    
}
