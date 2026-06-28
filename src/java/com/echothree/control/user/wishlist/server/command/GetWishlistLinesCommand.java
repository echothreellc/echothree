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

package com.echothree.control.user.wishlist.server.command;

import com.echothree.control.user.wishlist.common.form.GetWishlistLinesForm;
import com.echothree.control.user.wishlist.common.result.WishlistResultFactory;
import com.echothree.model.control.item.server.control.ItemControl;
import com.echothree.model.control.item.server.logic.ItemLogic;
import com.echothree.model.control.order.common.OrderTypes;
import com.echothree.model.control.order.server.control.OrderControl;
import com.echothree.model.control.order.server.control.OrderTypeControl;
import com.echothree.model.control.wishlist.server.control.WishlistControl;
import com.echothree.model.data.item.server.entity.Item;
import com.echothree.model.data.order.server.entity.Order;
import com.echothree.model.data.order.server.entity.OrderLine;
import com.echothree.model.data.order.server.factory.OrderLineFactory;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BasePaginatedMultipleEntitiesCommand;
import java.util.Collection;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class GetWishlistLinesCommand
        extends BasePaginatedMultipleEntitiesCommand<OrderLine, GetWishlistLinesForm> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("WishlistName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("ItemName", FieldType.ENTITY_NAME, false, null, null)
        );
    }

    @Inject
    ItemControl itemControl;

    @Inject
    OrderControl orderControl;

    @Inject
    OrderTypeControl orderTypeControl;

    @Inject
    WishlistControl wishlistControl;

    @Inject
    ItemLogic itemLogic;

    /** Creates a new instance of GetWishlistLinesCommand */
    public GetWishlistLinesCommand() {
        super(null, FORM_FIELD_DEFINITIONS, true);
    }
    
    private Order order;
    private Item item;

    @Override
    protected void handleForm() {
        var wishlistName = form.getWishlistName();
        var itemName = form.getItemName();
        var parameterCount = (wishlistName == null ? 0 : 1) + (itemName == null ? 0 : 1);

        if(parameterCount == 1) {
            if(wishlistName != null) {
                var orderType = orderTypeControl.getOrderTypeByName(OrderTypes.WISHLIST.name());
                
                if(orderType != null) {
                    order = orderControl.getOrderByName(orderType, wishlistName);

                    if(order == null || !order.getLastDetail().getOrderType().getLastDetail().getOrderTypeName().equals(OrderTypes.WISHLIST.name())) {
                        addExecutionError(ExecutionErrors.UnknownWishlistName.name(), wishlistName);
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownOrderTypeName.name(), OrderTypes.WISHLIST.name());
                }
            } else {
                item = itemLogic.getItemByName(this, itemName);
            }
        } else {
            addExecutionError(ExecutionErrors.InvalidParameterCount.name());
        }
    }

    @Override
    protected Long getTotalEntities() {
        Long total = null;

        if(!hasExecutionErrors()) {
            if(order != null) {
                total = wishlistControl.countWishlistLinesByOrder(order);
            } else {
                total = wishlistControl.countWishlistLinesByItem(item);
            }
        }

        return total;
    }

    @Override
    protected Collection<OrderLine> getEntities() {
        Collection<OrderLine> entities = null;

        if(!hasExecutionErrors()) {
            if(order != null) {
                entities = wishlistControl.getWishlistLinesByOrder(order);
            } else {
                entities = wishlistControl.getWishlistLinesByItem(item);
            }
        }

        return entities;
    }

    @Override
    protected BaseResult getResult(Collection<OrderLine> entities) {
        var result = WishlistResultFactory.getGetWishlistLinesResult();

        if(entities != null) {
            var userVisit = getUserVisit();

            if(order != null) {
                result.setWishlist(wishlistControl.getWishlistTransfer(userVisit, order));
            } else {
                result.setItem(itemControl.getItemTransfer(userVisit, item));
            }

            if(session.hasLimit(OrderLineFactory.class)) {
                result.setWishlistLineCount(getTotalEntities());
            }

            result.setWishlistLines(wishlistControl.getWishlistLineTransfers(userVisit, entities));
        }

        return result;
    }
    
}
