// --------------------------------------------------------------------------------
// Copyright 2002-2020 Echo Three, LLC
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

package com.echothree.model.control.order.server.transfer;

import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.transfer.BaseTransferCaches;

public class OrderTransferCaches
        extends BaseTransferCaches {
    
    protected OrderTypeTransferCache orderTypeTransferCache;
    protected OrderTypeDescriptionTransferCache orderTypeDescriptionTransferCache;
    protected OrderTimeTypeTransferCache orderTimeTypeTransferCache;
    protected OrderTimeTypeDescriptionTransferCache orderTimeTypeDescriptionTransferCache;
    protected OrderPaymentPreferenceTransferCache orderPaymentPreferenceTransferCache;
    protected OrderShipmentGroupTransferCache orderShipmentGroupTransferCache;
    protected OrderTimeTransferCache orderTimeTransferCache;
    protected OrderLineTimeTransferCache orderLineTimeTransferCache;
    protected OrderAdjustmentTypeTransferCache orderAdjustmentTypeTransferCache;
    protected OrderAdjustmentTypeDescriptionTransferCache orderAdjustmentTypeDescriptionTransferCache;
    protected OrderLineAdjustmentTypeTransferCache orderLineAdjustmentTypeTransferCache;
    protected OrderLineAdjustmentTypeDescriptionTransferCache orderLineAdjustmentTypeDescriptionTransferCache;
    protected OrderRoleTypeTransferCache orderRoleTypeTransferCache;
    protected OrderRoleTransferCache orderRoleTransferCache;
    protected OrderAliasTypeTransferCache orderAliasTypeTransferCache;
    protected OrderAliasTypeDescriptionTransferCache orderAliasTypeDescriptionTransferCache;
    protected OrderAliasTransferCache orderAliasTransferCache;
    protected OrderPriorityTransferCache orderPriorityTransferCache;
    protected OrderPriorityDescriptionTransferCache orderPriorityDescriptionTransferCache;
    
    /** Creates a new instance of OrderTransferCaches */
    public OrderTransferCaches(UserVisit userVisit) {
        super(userVisit);
    }
    
    public OrderTypeTransferCache getOrderTypeTransferCache() {
        if(orderTypeTransferCache == null)
            orderTypeTransferCache = new OrderTypeTransferCache(userVisit);
        
        return orderTypeTransferCache;
    }
    
    public OrderTypeDescriptionTransferCache getOrderTypeDescriptionTransferCache() {
        if(orderTypeDescriptionTransferCache == null)
            orderTypeDescriptionTransferCache = new OrderTypeDescriptionTransferCache(userVisit);

        return orderTypeDescriptionTransferCache;
    }

    public OrderTimeTypeTransferCache getOrderTimeTypeTransferCache() {
        if(orderTimeTypeTransferCache == null)
            orderTimeTypeTransferCache = new OrderTimeTypeTransferCache(userVisit);

        return orderTimeTypeTransferCache;
    }

    public OrderTimeTypeDescriptionTransferCache getOrderTimeTypeDescriptionTransferCache() {
        if(orderTimeTypeDescriptionTransferCache == null)
            orderTimeTypeDescriptionTransferCache = new OrderTimeTypeDescriptionTransferCache(userVisit);

        return orderTimeTypeDescriptionTransferCache;
    }

    public OrderPaymentPreferenceTransferCache getOrderPaymentPreferenceTransferCache() {
        if(orderPaymentPreferenceTransferCache == null)
            orderPaymentPreferenceTransferCache = new OrderPaymentPreferenceTransferCache(userVisit);

        return orderPaymentPreferenceTransferCache;
    }

    public OrderShipmentGroupTransferCache getOrderShipmentGroupTransferCache() {
        if(orderShipmentGroupTransferCache == null)
            orderShipmentGroupTransferCache = new OrderShipmentGroupTransferCache(userVisit);

        return orderShipmentGroupTransferCache;
    }

    public OrderTimeTransferCache getOrderTimeTransferCache() {
        if(orderTimeTransferCache == null)
            orderTimeTransferCache = new OrderTimeTransferCache(userVisit);

        return orderTimeTransferCache;
    }

    public OrderLineTimeTransferCache getOrderLineTimeTransferCache() {
        if(orderLineTimeTransferCache == null)
            orderLineTimeTransferCache = new OrderLineTimeTransferCache(userVisit);

        return orderLineTimeTransferCache;
    }

    public OrderAdjustmentTypeTransferCache getOrderAdjustmentTypeTransferCache() {
        if(orderAdjustmentTypeTransferCache == null)
            orderAdjustmentTypeTransferCache = new OrderAdjustmentTypeTransferCache(userVisit);

        return orderAdjustmentTypeTransferCache;
    }

    public OrderAdjustmentTypeDescriptionTransferCache getOrderAdjustmentTypeDescriptionTransferCache() {
        if(orderAdjustmentTypeDescriptionTransferCache == null)
            orderAdjustmentTypeDescriptionTransferCache = new OrderAdjustmentTypeDescriptionTransferCache(userVisit);

        return orderAdjustmentTypeDescriptionTransferCache;
    }

    public OrderLineAdjustmentTypeTransferCache getOrderLineAdjustmentTypeTransferCache() {
        if(orderLineAdjustmentTypeTransferCache == null)
            orderLineAdjustmentTypeTransferCache = new OrderLineAdjustmentTypeTransferCache(userVisit);

        return orderLineAdjustmentTypeTransferCache;
    }

    public OrderLineAdjustmentTypeDescriptionTransferCache getOrderLineAdjustmentTypeDescriptionTransferCache() {
        if(orderLineAdjustmentTypeDescriptionTransferCache == null)
            orderLineAdjustmentTypeDescriptionTransferCache = new OrderLineAdjustmentTypeDescriptionTransferCache(userVisit);

        return orderLineAdjustmentTypeDescriptionTransferCache;
    }

    public OrderRoleTypeTransferCache getOrderRoleTypeTransferCache() {
        if(orderRoleTypeTransferCache == null)
            orderRoleTypeTransferCache = new OrderRoleTypeTransferCache(userVisit);

        return orderRoleTypeTransferCache;
    }

    public OrderRoleTransferCache getOrderRoleTransferCache() {
        if(orderRoleTransferCache == null)
            orderRoleTransferCache = new OrderRoleTransferCache(userVisit);

        return orderRoleTransferCache;
    }

    public OrderAliasTypeTransferCache getOrderAliasTypeTransferCache() {
        if(orderAliasTypeTransferCache == null)
            orderAliasTypeTransferCache = new OrderAliasTypeTransferCache(userVisit);

        return orderAliasTypeTransferCache;
    }

    public OrderAliasTypeDescriptionTransferCache getOrderAliasTypeDescriptionTransferCache() {
        if(orderAliasTypeDescriptionTransferCache == null)
            orderAliasTypeDescriptionTransferCache = new OrderAliasTypeDescriptionTransferCache(userVisit);

        return orderAliasTypeDescriptionTransferCache;
    }

    public OrderAliasTransferCache getOrderAliasTransferCache() {
        if(orderAliasTransferCache == null)
            orderAliasTransferCache = new OrderAliasTransferCache(userVisit);

        return orderAliasTransferCache;
    }

    public OrderPriorityTransferCache getOrderPriorityTransferCache() {
        if(orderPriorityTransferCache == null)
            orderPriorityTransferCache = new OrderPriorityTransferCache(userVisit);

        return orderPriorityTransferCache;
    }

    public OrderPriorityDescriptionTransferCache getOrderPriorityDescriptionTransferCache() {
        if(orderPriorityDescriptionTransferCache == null)
            orderPriorityDescriptionTransferCache = new OrderPriorityDescriptionTransferCache(userVisit);

        return orderPriorityDescriptionTransferCache;
    }

}
