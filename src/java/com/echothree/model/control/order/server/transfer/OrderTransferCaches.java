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

package com.echothree.model.control.order.server.transfer;

import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.transfer.BaseTransferCaches;
import javax.enterprise.inject.spi.CDI;

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
    public OrderTransferCaches() {
        super();
    }
    
    public OrderTypeTransferCache getOrderTypeTransferCache() {
        if(orderTypeTransferCache == null)
            orderTypeTransferCache = CDI.current().select(OrderTypeTransferCache.class).get();
        
        return orderTypeTransferCache;
    }
    
    public OrderTypeDescriptionTransferCache getOrderTypeDescriptionTransferCache() {
        if(orderTypeDescriptionTransferCache == null)
            orderTypeDescriptionTransferCache = CDI.current().select(OrderTypeDescriptionTransferCache.class).get();

        return orderTypeDescriptionTransferCache;
    }

    public OrderTimeTypeTransferCache getOrderTimeTypeTransferCache() {
        if(orderTimeTypeTransferCache == null)
            orderTimeTypeTransferCache = CDI.current().select(OrderTimeTypeTransferCache.class).get();

        return orderTimeTypeTransferCache;
    }

    public OrderTimeTypeDescriptionTransferCache getOrderTimeTypeDescriptionTransferCache() {
        if(orderTimeTypeDescriptionTransferCache == null)
            orderTimeTypeDescriptionTransferCache = CDI.current().select(OrderTimeTypeDescriptionTransferCache.class).get();

        return orderTimeTypeDescriptionTransferCache;
    }

    public OrderPaymentPreferenceTransferCache getOrderPaymentPreferenceTransferCache() {
        if(orderPaymentPreferenceTransferCache == null)
            orderPaymentPreferenceTransferCache = CDI.current().select(OrderPaymentPreferenceTransferCache.class).get();

        return orderPaymentPreferenceTransferCache;
    }

    public OrderShipmentGroupTransferCache getOrderShipmentGroupTransferCache() {
        if(orderShipmentGroupTransferCache == null)
            orderShipmentGroupTransferCache = CDI.current().select(OrderShipmentGroupTransferCache.class).get();

        return orderShipmentGroupTransferCache;
    }

    public OrderTimeTransferCache getOrderTimeTransferCache() {
        if(orderTimeTransferCache == null)
            orderTimeTransferCache = CDI.current().select(OrderTimeTransferCache.class).get();

        return orderTimeTransferCache;
    }

    public OrderLineTimeTransferCache getOrderLineTimeTransferCache() {
        if(orderLineTimeTransferCache == null)
            orderLineTimeTransferCache = CDI.current().select(OrderLineTimeTransferCache.class).get();

        return orderLineTimeTransferCache;
    }

    public OrderAdjustmentTypeTransferCache getOrderAdjustmentTypeTransferCache() {
        if(orderAdjustmentTypeTransferCache == null)
            orderAdjustmentTypeTransferCache = CDI.current().select(OrderAdjustmentTypeTransferCache.class).get();

        return orderAdjustmentTypeTransferCache;
    }

    public OrderAdjustmentTypeDescriptionTransferCache getOrderAdjustmentTypeDescriptionTransferCache() {
        if(orderAdjustmentTypeDescriptionTransferCache == null)
            orderAdjustmentTypeDescriptionTransferCache = CDI.current().select(OrderAdjustmentTypeDescriptionTransferCache.class).get();

        return orderAdjustmentTypeDescriptionTransferCache;
    }

    public OrderLineAdjustmentTypeTransferCache getOrderLineAdjustmentTypeTransferCache() {
        if(orderLineAdjustmentTypeTransferCache == null)
            orderLineAdjustmentTypeTransferCache = CDI.current().select(OrderLineAdjustmentTypeTransferCache.class).get();

        return orderLineAdjustmentTypeTransferCache;
    }

    public OrderLineAdjustmentTypeDescriptionTransferCache getOrderLineAdjustmentTypeDescriptionTransferCache() {
        if(orderLineAdjustmentTypeDescriptionTransferCache == null)
            orderLineAdjustmentTypeDescriptionTransferCache = CDI.current().select(OrderLineAdjustmentTypeDescriptionTransferCache.class).get();

        return orderLineAdjustmentTypeDescriptionTransferCache;
    }

    public OrderRoleTypeTransferCache getOrderRoleTypeTransferCache() {
        if(orderRoleTypeTransferCache == null)
            orderRoleTypeTransferCache = CDI.current().select(OrderRoleTypeTransferCache.class).get();

        return orderRoleTypeTransferCache;
    }

    public OrderRoleTransferCache getOrderRoleTransferCache() {
        if(orderRoleTransferCache == null)
            orderRoleTransferCache = CDI.current().select(OrderRoleTransferCache.class).get();

        return orderRoleTransferCache;
    }

    public OrderAliasTypeTransferCache getOrderAliasTypeTransferCache() {
        if(orderAliasTypeTransferCache == null)
            orderAliasTypeTransferCache = CDI.current().select(OrderAliasTypeTransferCache.class).get();

        return orderAliasTypeTransferCache;
    }

    public OrderAliasTypeDescriptionTransferCache getOrderAliasTypeDescriptionTransferCache() {
        if(orderAliasTypeDescriptionTransferCache == null)
            orderAliasTypeDescriptionTransferCache = CDI.current().select(OrderAliasTypeDescriptionTransferCache.class).get();

        return orderAliasTypeDescriptionTransferCache;
    }

    public OrderAliasTransferCache getOrderAliasTransferCache() {
        if(orderAliasTransferCache == null)
            orderAliasTransferCache = CDI.current().select(OrderAliasTransferCache.class).get();

        return orderAliasTransferCache;
    }

    public OrderPriorityTransferCache getOrderPriorityTransferCache() {
        if(orderPriorityTransferCache == null)
            orderPriorityTransferCache = CDI.current().select(OrderPriorityTransferCache.class).get();

        return orderPriorityTransferCache;
    }

    public OrderPriorityDescriptionTransferCache getOrderPriorityDescriptionTransferCache() {
        if(orderPriorityDescriptionTransferCache == null)
            orderPriorityDescriptionTransferCache = CDI.current().select(OrderPriorityDescriptionTransferCache.class).get();

        return orderPriorityDescriptionTransferCache;
    }

}
