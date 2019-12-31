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

package com.echothree.util.server.persistence.translator;

import com.echothree.model.control.core.server.CoreControl;
import com.echothree.model.control.order.common.OrderConstants;
import com.echothree.model.control.order.server.OrderControl;
import com.echothree.model.control.order.server.logic.OrderLogic;
import com.echothree.model.control.sequence.common.SequenceConstants;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.order.common.pk.OrderPK;
import com.echothree.model.data.order.server.entity.Order;
import com.echothree.model.data.order.server.entity.OrderDetail;
import com.echothree.model.data.order.server.entity.OrderType;
import com.echothree.model.data.order.server.factory.OrderFactory;
import com.echothree.util.common.persistence.EntityNames;
import com.echothree.util.common.persistence.EntityNamesConstants;
import com.echothree.util.common.transfer.MapWrapper;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class OrderNameTranslator
        implements EntityInstanceTranslator, SequenceTypeTranslator {

    private final static Map<String, String> orderTypesToTargets;
    private final static Map<String, String> sequenceTypesToOrderTypes;
    private final static Map<String, String> sequenceTypesToTargets;

    static {
        Map<String, String> targetMap = new HashMap<>();
        
        targetMap.put(OrderConstants.OrderType_PURCHASE_ORDER, EntityNamesConstants.Target_PurchaseOrder);
        targetMap.put(OrderConstants.OrderType_SALES_ORDER, EntityNamesConstants.Target_SalesOrder);
        targetMap.put(OrderConstants.OrderType_WISHLIST, EntityNamesConstants.Target_Wishlist);

        orderTypesToTargets = Collections.unmodifiableMap(targetMap);
        
        targetMap = new HashMap<>();
        
        targetMap.put(SequenceConstants.SequenceType_PURCHASE_ORDER, OrderConstants.OrderType_PURCHASE_ORDER);
        targetMap.put(SequenceConstants.SequenceType_SALES_ORDER, OrderConstants.OrderType_SALES_ORDER);
        targetMap.put(SequenceConstants.SequenceType_WISHLIST, OrderConstants.OrderType_WISHLIST);
        
        sequenceTypesToOrderTypes = Collections.unmodifiableMap(targetMap);
        
        targetMap = new HashMap<>();
        
        targetMap.put(SequenceConstants.SequenceType_PURCHASE_ORDER, EntityNamesConstants.Target_PurchaseOrder);
        targetMap.put(SequenceConstants.SequenceType_SALES_ORDER, EntityNamesConstants.Target_SalesOrder);
        targetMap.put(SequenceConstants.SequenceType_WISHLIST, EntityNamesConstants.Target_Wishlist);
        
        sequenceTypesToTargets = Collections.unmodifiableMap(targetMap);
    }
    
    public EntityNames getNames(final Map<String, String> targetMap, final String key, final OrderDetail orderDetail) {
        String target = targetMap.get(key);
        EntityNames result = null;

        if(target != null) {
            MapWrapper<String> names = new MapWrapper<>(1);

            names.put(EntityNamesConstants.Name_OrderName, orderDetail.getOrderName());

            result = new EntityNames(target, names);
        }

        return result;
    }

    @Override
    public EntityNames getNames(final EntityInstance entityInstance) {
        OrderDetail orderDetail = OrderFactory.getInstance().getEntityFromPK(EntityPermission.READ_ONLY,
                new OrderPK(entityInstance.getEntityUniqueId())).getLastDetail();
        String orderTypeName = orderDetail.getOrderType().getLastDetail().getOrderTypeName();
        
        return getNames(orderTypesToTargets, orderTypeName, orderDetail);
    }

    @Override
    public EntityInstanceAndNames getNames(final String sequenceTypeName, final String orderName, final boolean includeEntityInstance) {
        var orderControl = (OrderControl)Session.getModelController(OrderControl.class);
        String orderTypeName = sequenceTypesToOrderTypes.get(sequenceTypeName);
        EntityInstanceAndNames result = null;
        
        if(orderTypeName != null) {
            OrderType orderType = OrderLogic.getInstance().getOrderTypeByName(null, orderTypeName);
            Order order = orderControl.getOrderByName(orderType, orderName);

            if(order != null) {
                var coreControl = (CoreControl)Session.getModelController(CoreControl.class);
                EntityNames entityNames = getNames(sequenceTypesToTargets, sequenceTypeName, order.getLastDetail());
            
                result = entityNames == null ? null : new EntityInstanceAndNames(includeEntityInstance ? coreControl.getEntityInstanceByBasePK(order.getPrimaryKey()) : null, entityNames);
            }
        }
        
        return result;
    }
    
}
