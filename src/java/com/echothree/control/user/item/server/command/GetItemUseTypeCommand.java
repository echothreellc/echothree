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

import com.echothree.control.user.item.remote.form.GetItemUseTypeForm;
import com.echothree.control.user.item.remote.result.GetItemUseTypeResult;
import com.echothree.control.user.item.remote.result.ItemResultFactory;
import com.echothree.model.control.item.server.ItemControl;
import com.echothree.model.data.item.server.entity.ItemUseType;
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

public class GetItemUseTypeCommand
        extends BaseSimpleCommand<GetItemUseTypeForm> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
            new FieldDefinition("ItemUseTypeName", FieldType.ENTITY_NAME, false, null, null)
        ));
    }
    
    /** Creates a new instance of GetItemUseTypeCommand */
    public GetItemUseTypeCommand(UserVisitPK userVisitPK, GetItemUseTypeForm form) {
        super(userVisitPK, form, null, FORM_FIELD_DEFINITIONS, true);
    }
    
    @Override
    protected BaseResult execute() {
        ItemControl itemControl = (ItemControl)Session.getModelController(ItemControl.class);
        GetItemUseTypeResult result = ItemResultFactory.getGetItemUseTypeResult();
        String itemUseTypeName = form.getItemUseTypeName();
        ItemUseType itemUseType = itemControl.getItemUseTypeByName(itemUseTypeName);
        
        if(itemUseType != null) {
            result.setItemUseType(itemControl.getItemUseTypeTransfer(getUserVisit(), itemUseType));
        } else {
            addExecutionError(ExecutionErrors.UnknownItemUseTypeName.name(), itemUseTypeName);
        }
        
        
        return result;
    }
    
}
