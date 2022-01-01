// --------------------------------------------------------------------------------
// Copyright 2002-2022 Echo Three, LLC
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

import com.echothree.control.user.item.common.form.GetItemPriceTypeForm;
import com.echothree.control.user.item.common.result.GetItemPriceTypeResult;
import com.echothree.control.user.item.common.result.ItemResultFactory;
import com.echothree.model.control.item.server.control.ItemControl;
import com.echothree.model.data.item.server.entity.ItemPriceType;
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

public class GetItemPriceTypeCommand
        extends BaseSimpleCommand<GetItemPriceTypeForm> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
            new FieldDefinition("ItemPriceTypeName", FieldType.ENTITY_NAME, false, null, null)
        ));
    }
    
    /** Creates a new instance of GetItemPriceTypeCommand */
    public GetItemPriceTypeCommand(UserVisitPK userVisitPK, GetItemPriceTypeForm form) {
        super(userVisitPK, form, null, FORM_FIELD_DEFINITIONS, true);
    }
    
    @Override
    protected BaseResult execute() {
        var itemControl = Session.getModelController(ItemControl.class);
        GetItemPriceTypeResult result = ItemResultFactory.getGetItemPriceTypeResult();
        String itemPriceTypeName = form.getItemPriceTypeName();
        ItemPriceType itemPriceType = itemControl.getItemPriceTypeByName(itemPriceTypeName);
        
        if(itemPriceType != null) {
            result.setItemPriceType(itemControl.getItemPriceTypeTransfer(getUserVisit(), itemPriceType));
        } else {
            addExecutionError(ExecutionErrors.UnknownItemPriceTypeName.name(), itemPriceTypeName);
        }
        
        
        return result;
    }
    
}
