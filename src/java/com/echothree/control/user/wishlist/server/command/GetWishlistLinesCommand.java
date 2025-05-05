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

package com.echothree.control.user.wishlist.server.command;

import com.echothree.control.user.wishlist.common.form.GetWishlistLinesForm;
import com.echothree.control.user.wishlist.common.result.WishlistResultFactory;
import com.echothree.model.control.item.server.control.ItemControl;
import com.echothree.model.control.order.common.OrderTypes;
import com.echothree.model.control.order.server.control.OrderControl;
import com.echothree.model.control.order.server.control.OrderTypeControl;
import com.echothree.model.control.wishlist.server.control.WishlistControl;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class GetWishlistLinesCommand
        extends BaseSimpleCommand<GetWishlistLinesForm> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
            new FieldDefinition("WishlistName", FieldType.ENTITY_NAME, false, null, null),
            new FieldDefinition("ItemName", FieldType.ENTITY_NAME, false, null, null)
        ));
    }
    
    /** Creates a new instance of GetWishlistLinesCommand */
    public GetWishlistLinesCommand() {
        super(null, FORM_FIELD_DEFINITIONS, true);
    }
    
    @Override
    protected BaseResult execute() {
        var result = WishlistResultFactory.getGetWishlistLinesResult();
        var wishlistName = form.getWishlistName();
        var itemName = form.getItemName();
        var parameterCount = (wishlistName == null ? 0 : 1) + (itemName == null ? 0 : 1);
        
        if(parameterCount == 1) {
            var wishlistControl = Session.getModelController(WishlistControl.class);
            
            if(wishlistName != null) {
                var orderControl = Session.getModelController(OrderControl.class);
                var orderTypeControl = Session.getModelController(OrderTypeControl.class);
                var order = orderControl.getOrderByName(orderTypeControl.getOrderTypeByName(OrderTypes.WISHLIST.name()), wishlistName);
                
                if(order != null && order.getLastDetail().getOrderType().getLastDetail().getOrderTypeName().equals(OrderTypes.WISHLIST.name())) {
                    result.setWishlistLines(wishlistControl.getWishlistLineTransfersByOrder(getUserVisit(), order));
                } else {
                    addExecutionError(ExecutionErrors.UnknownWishlistName.name(), wishlistName);
                }
            }
            
            if(itemName != null) {
                var itemControl = Session.getModelController(ItemControl.class);
                var item = itemControl.getItemByName(itemName);
                
                if(item != null) {
                    result.setWishlistLines(wishlistControl.getWishlistLineTransfersByItem(getUserVisit(), item));
                } else {
                    addExecutionError(ExecutionErrors.UnknownItemName.name(), itemName);
                }
            }
        } else {
            addExecutionError(ExecutionErrors.InvalidParameterCount.name());
        }
        
        return result;
    }
    
}
