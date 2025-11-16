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

import com.echothree.util.server.transfer.BaseTransferCaches;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

@RequestScoped
public class OrderTransferCaches
        extends BaseTransferCaches {
    
    @Inject
    OrderTypeTransferCache orderTypeTransferCache;
    
    @Inject
    OrderTypeDescriptionTransferCache orderTypeDescriptionTransferCache;
    
    @Inject
    OrderTimeTypeTransferCache orderTimeTypeTransferCache;
    
    @Inject
    OrderTimeTypeDescriptionTransferCache orderTimeTypeDescriptionTransferCache;
    
    @Inject
    OrderPaymentPreferenceTransferCache orderPaymentPreferenceTransferCache;
    
    @Inject
    OrderShipmentGroupTransferCache orderShipmentGroupTransferCache;
    
    @Inject
    OrderTimeTransferCache orderTimeTransferCache;
    
    @Inject
    OrderLineTimeTransferCache orderLineTimeTransferCache;
    
    @Inject
    OrderAdjustmentTypeTransferCache orderAdjustmentTypeTransferCache;
    
    @Inject
    OrderAdjustmentTypeDescriptionTransferCache orderAdjustmentTypeDescriptionTransferCache;
    
    @Inject
    OrderLineAdjustmentTypeTransferCache orderLineAdjustmentTypeTransferCache;
    
    @Inject
    OrderLineAdjustmentTypeDescriptionTransferCache orderLineAdjustmentTypeDescriptionTransferCache;
    
    @Inject
    OrderRoleTypeTransferCache orderRoleTypeTransferCache;
    
    @Inject
    OrderRoleTransferCache orderRoleTransferCache;
    
    @Inject
    OrderAliasTypeTransferCache orderAliasTypeTransferCache;
    
    @Inject
    OrderAliasTypeDescriptionTransferCache orderAliasTypeDescriptionTransferCache;
    
    @Inject
    OrderAliasTransferCache orderAliasTransferCache;
    
    @Inject
    OrderPriorityTransferCache orderPriorityTransferCache;
    
    @Inject
    OrderPriorityDescriptionTransferCache orderPriorityDescriptionTransferCache;

    /** Creates a new instance of OrderTransferCaches */
    protected OrderTransferCaches() {
        super();
    }
    
    public OrderTypeTransferCache getOrderTypeTransferCache() {
        return orderTypeTransferCache;
    }
    
    public OrderTypeDescriptionTransferCache getOrderTypeDescriptionTransferCache() {
        return orderTypeDescriptionTransferCache;
    }

    public OrderTimeTypeTransferCache getOrderTimeTypeTransferCache() {
        return orderTimeTypeTransferCache;
    }

    public OrderTimeTypeDescriptionTransferCache getOrderTimeTypeDescriptionTransferCache() {
        return orderTimeTypeDescriptionTransferCache;
    }

    public OrderPaymentPreferenceTransferCache getOrderPaymentPreferenceTransferCache() {
        return orderPaymentPreferenceTransferCache;
    }

    public OrderShipmentGroupTransferCache getOrderShipmentGroupTransferCache() {
        return orderShipmentGroupTransferCache;
    }

    public OrderTimeTransferCache getOrderTimeTransferCache() {
        return orderTimeTransferCache;
    }

    public OrderLineTimeTransferCache getOrderLineTimeTransferCache() {
        return orderLineTimeTransferCache;
    }

    public OrderAdjustmentTypeTransferCache getOrderAdjustmentTypeTransferCache() {
        return orderAdjustmentTypeTransferCache;
    }

    public OrderAdjustmentTypeDescriptionTransferCache getOrderAdjustmentTypeDescriptionTransferCache() {
        return orderAdjustmentTypeDescriptionTransferCache;
    }

    public OrderLineAdjustmentTypeTransferCache getOrderLineAdjustmentTypeTransferCache() {
        return orderLineAdjustmentTypeTransferCache;
    }

    public OrderLineAdjustmentTypeDescriptionTransferCache getOrderLineAdjustmentTypeDescriptionTransferCache() {
        return orderLineAdjustmentTypeDescriptionTransferCache;
    }

    public OrderRoleTypeTransferCache getOrderRoleTypeTransferCache() {
        return orderRoleTypeTransferCache;
    }

    public OrderRoleTransferCache getOrderRoleTransferCache() {
        return orderRoleTransferCache;
    }

    public OrderAliasTypeTransferCache getOrderAliasTypeTransferCache() {
        return orderAliasTypeTransferCache;
    }

    public OrderAliasTypeDescriptionTransferCache getOrderAliasTypeDescriptionTransferCache() {
        return orderAliasTypeDescriptionTransferCache;
    }

    public OrderAliasTransferCache getOrderAliasTransferCache() {
        return orderAliasTransferCache;
    }

    public OrderPriorityTransferCache getOrderPriorityTransferCache() {
        return orderPriorityTransferCache;
    }

    public OrderPriorityDescriptionTransferCache getOrderPriorityDescriptionTransferCache() {
        return orderPriorityDescriptionTransferCache;
    }

}
