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

import com.echothree.control.user.item.common.form.GetRelatedItemsForm;
import com.echothree.control.user.item.common.result.ItemResultFactory;
import com.echothree.model.control.item.server.control.ItemControl;
import com.echothree.model.data.item.server.entity.Item;
import com.echothree.model.data.item.server.entity.RelatedItem;
import com.echothree.model.data.item.server.entity.RelatedItemType;
import com.echothree.model.data.item.server.factory.RelatedItemFactory;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BasePaginatedMultipleEntitiesCommand;
import com.echothree.util.server.persistence.Session;
import java.util.Collection;
import java.util.List;
import javax.enterprise.context.Dependent;

@Dependent
public class GetRelatedItemsCommand
        extends BasePaginatedMultipleEntitiesCommand<RelatedItem, GetRelatedItemsForm> {

    // No COMMAND_SECURITY_DEFINITION, anyone may execute this command.
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("RelatedItemTypeName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("FromItemName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("ToItemName", FieldType.ENTITY_NAME, false, null, null)
        );
    }
    
    /** Creates a new instance of GetRelatedItemsCommand */
    public GetRelatedItemsCommand() {
        super(null, FORM_FIELD_DEFINITIONS, true);
    }

    RelatedItemType relatedItemType;
    Item fromItem;
    Item toItem;

    @Override
    protected void handleForm() {
        var fromItemName = form.getFromItemName();
        var toItemName = form.getToItemName();
        var parameterCount = (fromItemName == null ? 0 : 1) + (toItemName == null ? 0 : 1);

        if(parameterCount == 1) {
            var itemControl = Session.getModelController(ItemControl.class);
            var relatedItemTypeName = form.getRelatedItemTypeName();

            relatedItemType = relatedItemTypeName == null ? null : itemControl.getRelatedItemTypeByName(relatedItemTypeName);

            if(relatedItemTypeName == null || relatedItemType != null) {
                fromItem = fromItemName == null ? null : itemControl.getItemByName(fromItemName);

                if(fromItemName == null || fromItem != null) {
                    toItem = toItemName == null ? null : itemControl.getItemByName(toItemName);

                    if(toItemName != null && toItem == null) {
                        addExecutionError(ExecutionErrors.UnknownToItemName.name(), toItemName);
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownFromItemName.name(), fromItemName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownRelatedItemTypeName.name(), relatedItemTypeName);
            }
        } else {
            addExecutionError(ExecutionErrors.InvalidParameterCount.name());
        }
    }

    @Override
    protected Long getTotalEntities() {
        var itemControl = Session.getModelController(ItemControl.class);
        Long totalEntities = null;

        if(!hasExecutionErrors()) {
            if(relatedItemType == null) {
                if(fromItem != null) {
                    totalEntities = itemControl.countRelatedItemsByFromItem(fromItem);
                } else {
                    totalEntities = itemControl.countRelatedItemsByToItem(toItem);
                }
            } else {
                if(fromItem != null) {
                    totalEntities = itemControl.countRelatedItemsByRelatedItemTypeAndFromItem(relatedItemType, fromItem);
                } else {
                    totalEntities = itemControl.countRelatedItemsByRelatedItemTypeAndToItem(relatedItemType, toItem);
                }
            }
        }

        return totalEntities;
    }

    @Override
    protected Collection<RelatedItem> getEntities() {
        Collection<RelatedItem> entities = null;

        if(!hasExecutionErrors()) {
            var itemControl = Session.getModelController(ItemControl.class);

            if(relatedItemType == null) {
                if(fromItem != null) {
                    entities = itemControl.getRelatedItemsByFromItem(fromItem);
                } else if(toItem != null) {
                    entities = itemControl.getRelatedItemsByToItem(toItem);
                }
            } else {
                if(fromItem != null) {
                    entities = itemControl.getRelatedItemsByRelatedItemTypeAndFromItem(relatedItemType, fromItem);
                } else if(toItem != null) {
                    entities = itemControl.getRelatedItemsByRelatedItemTypeAndToItem(relatedItemType, toItem);
                }
            }
        }

        return entities;
    }

    @Override
    protected BaseResult getResult(Collection<RelatedItem> entities) {
        var result = ItemResultFactory.getGetRelatedItemsResult();

        if(entities != null) {
            var itemControl = Session.getModelController(ItemControl.class);
            var userVisit = getUserVisit();

            if(session.hasLimit(RelatedItemFactory.class)) {
                result.setRelatedItemCount(getTotalEntities());
            }

            if(relatedItemType != null) {
                result.setRelatedItemType(itemControl.getRelatedItemTypeTransfer(userVisit, relatedItemType));
            }

            if(fromItem != null) {
                result.setFromItem(itemControl.getItemTransfer(userVisit, fromItem));
            }

            if(toItem != null) {
                result.setToItem(itemControl.getItemTransfer(userVisit, toItem));
            }

            result.setRelatedItems(itemControl.getRelatedItemTransfers(userVisit, entities));
        }

        return result;
    }

}
