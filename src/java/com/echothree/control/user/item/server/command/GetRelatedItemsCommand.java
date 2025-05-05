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

package com.echothree.control.user.item.server.command;

import com.echothree.control.user.item.common.form.GetRelatedItemsForm;
import com.echothree.control.user.item.common.result.ItemResultFactory;
import com.echothree.model.control.item.server.control.ItemControl;
import com.echothree.model.data.item.server.entity.Item;
import com.echothree.model.data.item.server.entity.RelatedItem;
import com.echothree.model.data.item.server.entity.RelatedItemType;
import com.echothree.model.data.item.server.factory.RelatedItemFactory;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseMultipleEntitiesCommand;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class GetRelatedItemsCommand
        extends BaseMultipleEntitiesCommand<RelatedItem, GetRelatedItemsForm> {

    // No COMMAND_SECURITY_DEFINITION, anyone may execute this command.
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
            new FieldDefinition("RelatedItemTypeName", FieldType.ENTITY_NAME, false, null, null),
            new FieldDefinition("FromItemName", FieldType.ENTITY_NAME, false, null, null),
            new FieldDefinition("ToItemName", FieldType.ENTITY_NAME, false, null, null)
        ));
    }
    
    /** Creates a new instance of GetRelatedItemsCommand */
    public GetRelatedItemsCommand() {
        super(null, FORM_FIELD_DEFINITIONS, true);
    }

    RelatedItemType relatedItemType;
    Item fromItem;
    Item toItem;
    Long relatedItemCount;

    @Override
    protected Collection<RelatedItem> getEntities() {
        var fromItemName = form.getFromItemName();
        var toItemName = form.getToItemName();
        var parameterCount = (fromItemName == null ? 0 : 1) + (toItemName == null ? 0 : 1);
        Collection<RelatedItem> relatedItems = null;

        if(parameterCount == 1) {
            var itemControl = Session.getModelController(ItemControl.class);
            var relatedItemTypeName = form.getRelatedItemTypeName();

            relatedItemType = relatedItemTypeName == null ? null : itemControl.getRelatedItemTypeByName(relatedItemTypeName);

            if(relatedItemTypeName == null || relatedItemType != null) {
                fromItem = fromItemName == null ? null : itemControl.getItemByName(fromItemName);

                if(fromItemName == null || fromItem != null) {
                    toItem = toItemName == null ? null : itemControl.getItemByName(toItemName);

                    if(toItemName == null || toItem != null) {
                        if(relatedItemType == null) {
                            if(fromItem != null) {
                                if(session.hasLimit(RelatedItemFactory.class)) {
                                    relatedItemCount = itemControl.countRelatedItemsByFromItem(fromItem);
                                }

                                relatedItems = itemControl.getRelatedItemsByFromItem(fromItem);
                            } else if(toItem != null) {
                                if(session.hasLimit(RelatedItemFactory.class)) {
                                    relatedItemCount = itemControl.countRelatedItemsByToItem(toItem);
                                }

                                relatedItems = itemControl.getRelatedItemsByToItem(toItem);
                            }
                        } else {
                            if(fromItem != null) {
                                if(session.hasLimit(RelatedItemFactory.class)) {
                                    relatedItemCount = itemControl.countRelatedItemsByRelatedItemTypeAndFromItem(relatedItemType, fromItem);
                                }

                                relatedItems = itemControl.getRelatedItemsByRelatedItemTypeAndFromItem(relatedItemType, fromItem);
                            } else if(toItem != null) {
                                if(session.hasLimit(RelatedItemFactory.class)) {
                                    relatedItemCount = itemControl.countRelatedItemsByRelatedItemTypeAndToItem(relatedItemType, toItem);
                                }

                                relatedItems = itemControl.getRelatedItemsByRelatedItemTypeAndToItem(relatedItemType, toItem);
                            }
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
        } else {
            addExecutionError(ExecutionErrors.InvalidParameterCount.name());
        }

        return relatedItems;
    }

    @Override
    protected BaseResult getResult(Collection<RelatedItem> entities) {
        var result = ItemResultFactory.getGetRelatedItemsResult();

        if(entities != null) {
            var itemControl = Session.getModelController(ItemControl.class);
            var userVisit = getUserVisit();

            if(relatedItemType != null) {
                result.setRelatedItemType(itemControl.getRelatedItemTypeTransfer(userVisit, relatedItemType));
            }

            if(fromItem != null) {
                result.setFromItem(itemControl.getItemTransfer(userVisit, fromItem));
            }

            if(toItem != null) {
                result.setToItem(itemControl.getItemTransfer(userVisit, toItem));
            }

            if(session.hasLimit(RelatedItemFactory.class)) {
                result.setRelatedItemCount(relatedItemCount);
            }

            result.setRelatedItems(itemControl.getRelatedItemTransfers(userVisit, entities));
        }

        return result;
    }

}
