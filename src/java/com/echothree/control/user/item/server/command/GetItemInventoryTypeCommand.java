// --------------------------------------------------------------------------------
// Copyright 2002-2021 Echo Three, LLC
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

import com.echothree.control.user.item.common.form.GetItemInventoryTypeForm;
import com.echothree.control.user.item.common.result.GetItemInventoryTypeResult;
import com.echothree.control.user.item.common.result.ItemResultFactory;
import com.echothree.model.control.item.server.control.ItemControl;
import com.echothree.model.data.item.server.entity.ItemInventoryType;
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

public class GetItemInventoryTypeCommand
        extends BaseSimpleCommand<GetItemInventoryTypeForm> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
            new FieldDefinition("ItemInventoryTypeName", FieldType.ENTITY_NAME, false, null, null)
        ));
    }
    
    /** Creates a new instance of GetItemInventoryTypeCommand */
    public GetItemInventoryTypeCommand(UserVisitPK userVisitPK, GetItemInventoryTypeForm form) {
        super(userVisitPK, form, null, FORM_FIELD_DEFINITIONS, true);
    }
    
    @Override
    protected BaseResult execute() {
        var itemControl = Session.getModelController(ItemControl.class);
        GetItemInventoryTypeResult result = ItemResultFactory.getGetItemInventoryTypeResult();
        String itemInventoryTypeName = form.getItemInventoryTypeName();
        ItemInventoryType itemInventoryType = itemControl.getItemInventoryTypeByName(itemInventoryTypeName);
        
        if(itemInventoryType != null) {
            result.setItemInventoryType(itemControl.getItemInventoryTypeTransfer(getUserVisit(), itemInventoryType));
        } else {
            addExecutionError(ExecutionErrors.UnknownItemInventoryTypeName.name(), itemInventoryTypeName);
        }
        
        
        return result;
    }
    
}
