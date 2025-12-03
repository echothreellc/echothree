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

package com.echothree.model.control.order.server.control;

import com.echothree.model.control.core.common.EventTypes;
import com.echothree.model.data.accounting.server.entity.Currency;
import com.echothree.model.data.batch.server.entity.Batch;
import com.echothree.model.data.order.server.entity.OrderBatch;
import com.echothree.model.data.order.server.factory.OrderBatchFactory;
import com.echothree.model.data.order.server.value.OrderBatchValue;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import com.echothree.util.server.cdi.CommandScope;

@CommandScope
public class OrderBatchControl
        extends BaseOrderControl {

    /** Creates a new instance of OrderControl */
    protected OrderBatchControl() {
        super();
    }

    // --------------------------------------------------------------------------------
    //    Order Batches
    // --------------------------------------------------------------------------------

    public OrderBatch createOrderBatch(Batch batch, Currency currency, Long count, Long amount, BasePK createdBy) {
        var orderBatch = OrderBatchFactory.getInstance().create(batch, currency, count, amount, session.START_TIME_LONG, Session.MAX_TIME_LONG);

        sendEvent(batch.getPrimaryKey(), EventTypes.MODIFY, orderBatch.getPrimaryKey(), EventTypes.CREATE, createdBy);

        return orderBatch;
    }

    private static final Map<EntityPermission, String> getOrderBatchQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM orderbatches " +
                "WHERE ordbtch_btch_batchid = ? AND ordbtch_thrutime = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM orderbatches " +
                "WHERE ordbtch_btch_batchid = ? AND ordbtch_thrutime = ? " +
                "FOR UPDATE");
        getOrderBatchQueries = Collections.unmodifiableMap(queryMap);
    }

    private OrderBatch getOrderBatch(Batch batch, EntityPermission entityPermission) {
        return OrderBatchFactory.getInstance().getEntityFromQuery(entityPermission, getOrderBatchQueries,
                batch, Session.MAX_TIME_LONG);
    }

    public OrderBatch getOrderBatch(Batch batch) {
        return getOrderBatch(batch, EntityPermission.READ_ONLY);
    }

    public OrderBatch getOrderBatchForUpdate(Batch batch) {
        return getOrderBatch(batch, EntityPermission.READ_WRITE);
    }

    public OrderBatchValue getOrderBatchValue(OrderBatch orderBatch) {
        return orderBatch == null? null: orderBatch.getOrderBatchValue().clone();
    }

    public OrderBatchValue getOrderBatchValueForUpdate(Batch batch) {
        return getOrderBatchValue(getOrderBatchForUpdate(batch));
    }

    public void updateOrderBatchFromValue(OrderBatchValue orderBatchValue, BasePK updatedBy) {
        if(orderBatchValue.hasBeenModified()) {
            var orderBatch = OrderBatchFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                    orderBatchValue.getPrimaryKey());

            orderBatch.setThruTime(session.START_TIME_LONG);
            orderBatch.store();

            var batchPK = orderBatch.getBatchPK(); // Not updated
            var currencyPK = orderBatchValue.getCurrencyPK();
            var count = orderBatchValue.getCount();
            var amount = orderBatchValue.getAmount();

            orderBatch = OrderBatchFactory.getInstance().create(batchPK, currencyPK, count, amount, session.START_TIME_LONG, Session.MAX_TIME_LONG);

            sendEvent(batchPK, EventTypes.MODIFY, orderBatch.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }

    public void deleteOrderBatch(OrderBatch orderBatch, BasePK deletedBy) {
        orderBatch.setThruTime(session.START_TIME_LONG);

        sendEvent(orderBatch.getBatchPK(), EventTypes.MODIFY, orderBatch.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }

    public void deleteOrderBatch(Batch batch, BasePK deletedBy) {
        deleteOrderBatch(getOrderBatchForUpdate(batch), deletedBy);
    }

}
