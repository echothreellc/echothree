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

package com.echothree.model.control.item.server.transfer;

import com.echothree.model.control.customer.server.control.CustomerControl;
import com.echothree.model.control.item.common.ItemProperties;
import com.echothree.model.control.item.common.transfer.ItemShippingTimeTransfer;
import com.echothree.model.control.item.server.control.ItemControl;
import com.echothree.model.data.item.server.entity.ItemShippingTime;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.common.form.TransferProperties;
import com.echothree.util.server.persistence.Session;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class ItemShippingTimeTransferCache
        extends BaseItemTransferCache<ItemShippingTime, ItemShippingTimeTransfer> {
    
    CustomerControl customerControl = Session.getModelController(CustomerControl.class);
    ItemControl itemControl = Session.getModelController(ItemControl.class);

    TransferProperties transferProperties;
    boolean filterItem;
    boolean filterCustomerType;
    boolean filterUnformattedShippingStartTime;
    boolean filterShippingStartTime;
    boolean filterUnformattedShippingEndTime;
    boolean filterShippingEndTime;

    /** Creates a new instance of ItemShippingTimeTransferCache */
    protected ItemShippingTimeTransferCache() {
        super();

        transferProperties = session.getTransferProperties();
        if(transferProperties != null) {
            var properties = transferProperties.getProperties(ItemShippingTimeTransfer.class);
            
            if(properties != null) {
                filterItem = !properties.contains(ItemProperties.ITEM);
                filterCustomerType = !properties.contains(ItemProperties.CUSTOMER_TYPE);
                filterUnformattedShippingStartTime = !properties.contains(ItemProperties.UNFORMATTED_SHIPPING_START_TIME);
                filterShippingStartTime = !properties.contains(ItemProperties.SHIPPING_START_TIME);
                filterUnformattedShippingEndTime = !properties.contains(ItemProperties.UNFORMATTED_SHIPPING_END_TIME);
                filterShippingEndTime = !properties.contains(ItemProperties.SHIPPING_END_TIME);
            }
        }
    }
    
    @Override
    public ItemShippingTimeTransfer getTransfer(UserVisit userVisit, ItemShippingTime itemShippingTime) {
        var itemShippingTimeTransfer = get(itemShippingTime);
        
        if(itemShippingTimeTransfer == null) {
            var itemTransfer = filterItem ? null : itemControl.getItemTransfer(userVisit, itemShippingTime.getItem());
            var customerTypeTransfer = filterCustomerType ? null : customerControl.getCustomerTypeTransfer(userVisit, itemShippingTime.getCustomerType());
            var unformattedShippingStartTime = itemShippingTime.getShippingStartTime();
            var shippingStartTime = filterShippingStartTime ? null : formatTypicalDateTime(userVisit, unformattedShippingStartTime);
            var unformattedShippingEndTime = itemShippingTime.getShippingEndTime();
            var shippingEndTime = filterShippingEndTime ? null : formatTypicalDateTime(userVisit, unformattedShippingEndTime);
            
            itemShippingTimeTransfer = new ItemShippingTimeTransfer(itemTransfer, customerTypeTransfer,
                    filterUnformattedShippingStartTime ? null : unformattedShippingStartTime, shippingStartTime,
                    filterUnformattedShippingEndTime ? null : unformattedShippingStartTime, shippingEndTime);
            put(userVisit, itemShippingTime, itemShippingTimeTransfer);
        }
        
        return itemShippingTimeTransfer;
    }
    
}
