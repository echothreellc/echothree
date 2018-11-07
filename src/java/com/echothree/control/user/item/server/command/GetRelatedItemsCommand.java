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

import com.echothree.control.user.item.common.form.GetRelatedItemsForm;
import com.echothree.control.user.item.common.result.GetRelatedItemsResult;
import com.echothree.control.user.item.common.result.ItemResultFactory;
import com.echothree.model.control.item.server.ItemControl;
import com.echothree.model.data.item.server.entity.Item;
import com.echothree.model.data.item.server.entity.RelatedItemType;
import com.echothree.model.data.item.server.factory.RelatedItemFactory;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class GetRelatedItemsCommand
        extends BaseSimpleCommand<GetRelatedItemsForm> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
            new FieldDefinition("RelatedItemTypeName", FieldType.ENTITY_NAME, false, null, null),
            new FieldDefinition("FromItemName", FieldType.ENTITY_NAME, false, null, null),
            new FieldDefinition("ToItemName", FieldType.ENTITY_NAME, false, null, null)
        ));
    }
    
    /** Creates a new instance of GetRelatedItemsCommand */
    public GetRelatedItemsCommand(UserVisitPK userVisitPK, GetRelatedItemsForm form) {
        super(userVisitPK, form, null, FORM_FIELD_DEFINITIONS, true);
    }
    
    @Override
    protected BaseResult execute() {
        ItemControl itemControl = (ItemControl)Session.getModelController(ItemControl.class);
        GetRelatedItemsResult result = ItemResultFactory.getGetRelatedItemsResult();
        String fromItemName = form.getFromItemName();
        String toItemName = form.getToItemName();
        int parameterCount = (fromItemName == null ? 0 : 1) + (toItemName == null ? 0 : 1);

        if(parameterCount == 1) {
            String relatedItemTypeName = form.getRelatedItemTypeName();
            RelatedItemType relatedItemType = relatedItemTypeName == null ? null : itemControl.getRelatedItemTypeByName(relatedItemTypeName);

            if(relatedItemTypeName == null || relatedItemType != null) {
                Item fromItem = fromItemName == null ? null : itemControl.getItemByName(fromItemName);

                if(fromItemName == null || fromItem != null) {
                    Item toItem = toItemName == null ? null : itemControl.getItemByName(toItemName);

                    if(toItemName == null || toItem != null) {
                        UserVisit userVisit = getUserVisit();

                        if(relatedItemType == null) {
                            if(fromItem != null) {
                                if(session.hasLimit(RelatedItemFactory.class)) {
                                    result.setRelatedItemCount(itemControl.countRelatedItemsByFromItem(fromItem));
                                }

                                result.setRelatedItems(itemControl.getRelatedItemTransfersByFromItem(userVisit, fromItem));
                            } else if(toItem != null) {
                                if(session.hasLimit(RelatedItemFactory.class)) {
                                    result.setRelatedItemCount(itemControl.countRelatedItemsByToItem(toItem));
                                }

                                result.setRelatedItems(itemControl.getRelatedItemTransfersByToItem(userVisit, toItem));
                            }
                        } else {
                            result.setRelatedItemType(itemControl.getRelatedItemTypeTransfer(getUserVisit(), relatedItemType));

                            if(fromItem != null) {
                                if(session.hasLimit(RelatedItemFactory.class)) {
                                    result.setRelatedItemCount(itemControl.countRelatedItemsByRelatedItemTypeAndFromItem(relatedItemType, fromItem));
                                }

                                result.setRelatedItems(itemControl.getRelatedItemTransfersByRelatedItemTypeAndFromItem(userVisit, relatedItemType, fromItem));
                            } else if(toItem != null) {
                                if(session.hasLimit(RelatedItemFactory.class)) {
                                    result.setRelatedItemCount(itemControl.countRelatedItemsByRelatedItemTypeAndToItem(relatedItemType, toItem));
                                }

                                result.setRelatedItems(itemControl.getRelatedItemTransfersByRelatedItemTypeAndToItem(userVisit, relatedItemType, toItem));
                            }
                        }

                        if(fromItem != null) {
                            result.setFromItem(itemControl.getItemTransfer(userVisit, fromItem));
                        }

                        if(toItem != null) {
                            result.setToItem(itemControl.getItemTransfer(userVisit, toItem));
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
        
        return result;
    }
    
}
