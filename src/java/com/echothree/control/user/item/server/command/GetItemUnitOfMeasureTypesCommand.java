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

package com.echothree.control.user.item.server.command;

import com.echothree.control.user.item.remote.form.GetItemUnitOfMeasureTypesForm;
import com.echothree.control.user.item.remote.result.GetItemUnitOfMeasureTypesResult;
import com.echothree.control.user.item.remote.result.ItemResultFactory;
import com.echothree.model.control.item.server.ItemControl;
import com.echothree.model.control.uom.server.UomControl;
import com.echothree.model.data.item.server.entity.Item;
import com.echothree.model.data.uom.server.entity.UnitOfMeasureType;
import com.echothree.model.data.user.remote.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.remote.command.BaseResult;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class GetItemUnitOfMeasureTypesCommand
        extends BaseSimpleCommand<GetItemUnitOfMeasureTypesForm> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ItemName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("UnitOfMeasureTypeName", FieldType.ENTITY_NAME, false, null, null)
                ));
    }
    
    /** Creates a new instance of GetItemUnitOfMeasureTypesCommand */
    public GetItemUnitOfMeasureTypesCommand(UserVisitPK userVisitPK, GetItemUnitOfMeasureTypesForm form) {
        super(userVisitPK, form, null, FORM_FIELD_DEFINITIONS, true);
    }
    
    @Override
    protected BaseResult execute() {
        ItemControl itemControl = (ItemControl)Session.getModelController(ItemControl.class);
        GetItemUnitOfMeasureTypesResult result = ItemResultFactory.getGetItemUnitOfMeasureTypesResult();
        String itemName = form.getItemName();
        Item item = itemControl.getItemByName(itemName);
        
        if(item != null) {
            String unitOfMeasureTypeName = form.getUnitOfMeasureTypeName();
            
            if(unitOfMeasureTypeName == null) {
                result.setItem(itemControl.getItemTransfer(getUserVisit(), item));
                result.setItemUnitOfMeasureTypes(itemControl.getItemUnitOfMeasureTypeTransfersByItem(getUserVisit(),
                        item));
            } else {
                UomControl uomControl = (UomControl)Session.getModelController(UomControl.class);
                UnitOfMeasureType unitOfMeasureType = uomControl.getUnitOfMeasureTypeByName(item.getLastDetail().getUnitOfMeasureKind(),
                        unitOfMeasureTypeName);
                
                if(unitOfMeasureType != null) {
                    result.setUnitOfMeasureType(uomControl.getUnitOfMeasureTypeTransfer(getUserVisit(), unitOfMeasureType));
                    result.setItemUnitOfMeasureTypes(itemControl.getItemUnitOfMeasureTypeTransfersByUnitOfMeasureType(getUserVisit(),
                            unitOfMeasureType));
                } else {
                    addExecutionError(ExecutionErrors.UnknownUnitOfMeasureTypeName.name(), unitOfMeasureTypeName);
                }
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownItemName.name(), itemName);
        }
        
        return result;
    }
    
}
