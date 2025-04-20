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

package com.echothree.util.server.persistence.translator;

import com.echothree.model.control.core.server.control.EntityInstanceControl;
import com.echothree.model.control.order.common.OrderTypes;
import com.echothree.model.control.order.server.control.OrderControl;
import com.echothree.model.control.order.server.logic.OrderLogic;
import com.echothree.model.control.sequence.common.SequenceTypes;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.order.common.pk.OrderPK;
import com.echothree.model.data.order.server.entity.OrderDetail;
import com.echothree.model.data.order.server.factory.OrderFactory;
import com.echothree.util.common.persistence.EntityNames;
import com.echothree.util.common.persistence.Names;
import com.echothree.util.common.persistence.Targets;
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
        var orderTypesToTargetsMap = new HashMap<String, String>();

        orderTypesToTargetsMap.put(OrderTypes.PURCHASE_ORDER.name(), Targets.PurchaseOrder.name());
        orderTypesToTargetsMap.put(OrderTypes.SALES_ORDER.name(), Targets.SalesOrder.name());
        orderTypesToTargetsMap.put(OrderTypes.WISHLIST.name(), Targets.Wishlist.name());

        orderTypesToTargets = Collections.unmodifiableMap(orderTypesToTargetsMap);

        var sequenceTypesToOrderTypesMap = new HashMap<String, String>();

        sequenceTypesToOrderTypesMap.put(SequenceTypes.PURCHASE_ORDER.name(), OrderTypes.PURCHASE_ORDER.name());
        sequenceTypesToOrderTypesMap.put(SequenceTypes.SALES_ORDER.name(), OrderTypes.SALES_ORDER.name());
        sequenceTypesToOrderTypesMap.put(SequenceTypes.WISHLIST.name(), OrderTypes.WISHLIST.name());
        
        sequenceTypesToOrderTypes = Collections.unmodifiableMap(sequenceTypesToOrderTypesMap);

        var sequenceTypesToTargetsMap = new HashMap<String, String>();

        sequenceTypesToTargetsMap.put(SequenceTypes.PURCHASE_ORDER.name(), Targets.PurchaseOrder.name());
        sequenceTypesToTargetsMap.put(SequenceTypes.SALES_ORDER.name(), Targets.SalesOrder.name());
        sequenceTypesToTargetsMap.put(SequenceTypes.WISHLIST.name(), Targets.Wishlist.name());
        
        sequenceTypesToTargets = Collections.unmodifiableMap(sequenceTypesToTargetsMap);
    }
    
    private EntityNames getNames(final Map<String, String> targetMap, final String key, final OrderDetail orderDetail) {
        var target = targetMap.get(key);
        EntityNames result = null;

        if(target != null) {
            var names = new MapWrapper<String>(1);

            names.put(Names.OrderName.name(), orderDetail.getOrderName());

            result = new EntityNames(target, names);
        }

        return result;
    }

    @Override
    public EntityNames getNames(final EntityInstance entityInstance) {
        var orderDetail = OrderFactory.getInstance().getEntityFromPK(EntityPermission.READ_ONLY,
                new OrderPK(entityInstance.getEntityUniqueId())).getLastDetail();
        var orderTypeName = orderDetail.getOrderType().getLastDetail().getOrderTypeName();
        
        return getNames(orderTypesToTargets, orderTypeName, orderDetail);
    }

    @Override
    public EntityInstanceAndNames getNames(final String sequenceTypeName, final String orderName,
            final boolean includeEntityInstance) {
        EntityInstanceAndNames result = null;
        var orderControl = Session.getModelController(OrderControl.class);
        var orderTypeName = sequenceTypesToOrderTypes.get(sequenceTypeName);

        if(orderTypeName != null) {
            var orderType = OrderLogic.getInstance().getOrderTypeByName(null, orderTypeName);
            var order = orderControl.getOrderByName(orderType, orderName);

            if(order != null) {
                var entityInstanceControl = Session.getModelController(EntityInstanceControl.class);
                var entityNames = getNames(sequenceTypesToTargets, sequenceTypeName, order.getLastDetail());
            
                result = entityNames == null ? null : new EntityInstanceAndNames(includeEntityInstance ? entityInstanceControl.getEntityInstanceByBasePK(order.getPrimaryKey()) : null, entityNames);
            }
        }
        
        return result;
    }
    
}
