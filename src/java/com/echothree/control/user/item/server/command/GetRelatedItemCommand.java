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

import com.echothree.control.user.item.common.form.GetRelatedItemForm;
import com.echothree.control.user.item.common.result.GetRelatedItemResult;
import com.echothree.control.user.item.common.result.ItemResultFactory;
import com.echothree.model.control.item.server.control.ItemControl;
import com.echothree.model.data.item.server.entity.Item;
import com.echothree.model.data.item.server.entity.RelatedItem;
import com.echothree.model.data.item.server.entity.RelatedItemType;
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

public class GetRelatedItemCommand
        extends BaseSimpleCommand<GetRelatedItemForm> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
            new FieldDefinition("RelatedItemTypeName", FieldType.ENTITY_NAME, true, null, null),
            new FieldDefinition("FromItemName", FieldType.ENTITY_NAME, true, null, null),
            new FieldDefinition("ToItemName", FieldType.ENTITY_NAME, true, null, null)
        ));
    }
    
    /** Creates a new instance of GetRelatedItemCommand */
    public GetRelatedItemCommand(UserVisitPK userVisitPK, GetRelatedItemForm form) {
        super(userVisitPK, form, null, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        var itemControl = Session.getModelController(ItemControl.class);
        GetRelatedItemResult result = ItemResultFactory.getGetRelatedItemResult();
        String relatedItemTypeName = form.getRelatedItemTypeName();
        RelatedItemType relatedItemType = itemControl.getRelatedItemTypeByName(relatedItemTypeName);
        
        if(relatedItemType != null) {
            String fromItemName = form.getFromItemName();
            Item fromItem = itemControl.getItemByName(fromItemName);
            
            if(fromItem != null) {
                String toItemName = form.getToItemName();
                Item toItem = itemControl.getItemByName(toItemName);
                
                if(toItem != null) {
                    RelatedItem relatedItem = itemControl.getRelatedItem(relatedItemType, fromItem, toItem);
                    
                    if(relatedItem != null) {
                        result.setRelatedItem(itemControl.getRelatedItemTransfer(getUserVisit(), relatedItem));
                    } else {
                        addExecutionError(ExecutionErrors.UnknownRelatedItem.name(), relatedItemTypeName, fromItemName, toItemName);
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownToItemName.name(), toItemName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownFromItemName.name(), fromItemName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownRelatedItemTypeName.name(), relatedItemTypeName);
        }
        
        return result;
    }
    
}
