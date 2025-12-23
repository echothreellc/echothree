// --------------------------------------------------------------------------------
// Copyright 2002-2026 Echo Three, LLC
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
import com.echothree.control.user.item.common.result.ItemResultFactory;
import com.echothree.model.control.item.server.control.ItemControl;
import com.echothree.model.control.item.server.logic.RelatedItemTypeLogic;
import com.echothree.model.data.item.server.entity.RelatedItem;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseSingleEntityCommand;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.enterprise.context.Dependent;

@Dependent
public class GetRelatedItemCommand
        extends BaseSingleEntityCommand<RelatedItem, GetRelatedItemForm> {

    // No COMMAND_SECURITY_DEFINITION, anyone may execute this command.
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
            new FieldDefinition("RelatedItemTypeName", FieldType.ENTITY_NAME, true, null, null),
            new FieldDefinition("FromItemName", FieldType.ENTITY_NAME, true, null, null),
            new FieldDefinition("ToItemName", FieldType.ENTITY_NAME, true, null, null)
        ));
    }
    
    /** Creates a new instance of GetRelatedItemCommand */
    public GetRelatedItemCommand() {
        super(null, FORM_FIELD_DEFINITIONS, false);
    }


    @Override
    protected RelatedItem getEntity() {
        var relatedItemType = RelatedItemTypeLogic.getInstance().getRelatedItemTypeByName(this, form.getRelatedItemTypeName());
        RelatedItem relatedItem = null;

        if(relatedItemType != null) {
            var itemControl = Session.getModelController(ItemControl.class);
            var fromItemName = form.getFromItemName();
            var fromItem = itemControl.getItemByName(fromItemName);

            if(fromItem != null) {
                var toItemName = form.getToItemName();
                var toItem = itemControl.getItemByName(toItemName);

                if(toItem != null) {
                    relatedItem = itemControl.getRelatedItem(relatedItemType, fromItem, toItem);

                    if(relatedItem == null) {
                        addExecutionError(ExecutionErrors.UnknownRelatedItem.name(),
                                relatedItemType.getLastDetail().getRelatedItemTypeName(),
                                fromItem.getLastDetail().getItemName(), toItem.getLastDetail().getItemName());
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownToItemName.name(), toItemName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownFromItemName.name(), fromItemName);
            }
        }

        return relatedItem;
    }

    @Override
    protected BaseResult getResult(RelatedItem relatedItem) {
        var result = ItemResultFactory.getGetRelatedItemResult();

        if(relatedItem != null) {
            var itemControl = Session.getModelController(ItemControl.class);

            result.setRelatedItem(itemControl.getRelatedItemTransfer(getUserVisit(), relatedItem));
        }

        return result;
    }

}
