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

package com.echothree.model.control.order.server.control;

import com.echothree.model.control.core.common.EventTypes;
import com.echothree.model.data.accounting.server.entity.Currency;
import com.echothree.model.data.batch.server.entity.Batch;
import com.echothree.model.data.order.server.entity.OrderBatch;
import com.echothree.model.data.order.server.factory.OrderBatchFactory;
import com.echothree.model.data.order.server.value.OrderBatchValue;
import static com.echothree.model.jooq.server.tables.order.OrderBatches.OrderBatches;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.cdi.CommandScope;
import com.echothree.util.server.control.BaseModelControl;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import javax.inject.Inject;

@CommandScope
public class OrderBatchControl
        extends BaseModelControl {

    /** Creates a new instance of OrderControl */
    protected OrderBatchControl() {
        super();
    }

    // --------------------------------------------------------------------------------
    //    Order Batches
    // --------------------------------------------------------------------------------

    @Inject
    private OrderBatchFactory orderBatchFactory;

    public OrderBatch createOrderBatch(Batch batch, Currency currency, Long count, Long amount, BasePK createdBy) {
        var orderBatch = orderBatchFactory.create(batch, currency, count, amount, session.getStartTime(), Session.MAX_TIME);

        sendEvent(batch.getPrimaryKey(), EventTypes.MODIFY, orderBatch.getPrimaryKey(), EventTypes.CREATE, createdBy);

        return orderBatch;
    }

    private OrderBatch getOrderBatch(Batch batch, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(OrderBatches.fields())
                .from(OrderBatches)
                .where(OrderBatches.BATCH.eq(batch.getPrimaryKey()),
                        OrderBatches.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return orderBatchFactory.getEntityFromQuery(entityPermission, query);
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
            var orderBatch = orderBatchFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                    orderBatchValue.getPrimaryKey());

            orderBatch.setThruTime(session.getStartTime());
            orderBatch.store();

            var batchPK = orderBatch.getBatchPK(); // Not updated
            var currencyPK = orderBatchValue.getCurrencyPK();
            var count = orderBatchValue.getCount();
            var amount = orderBatchValue.getAmount();

            orderBatch = orderBatchFactory.create(batchPK, currencyPK, count, amount, session.getStartTime(), Session.MAX_TIME);

            sendEvent(batchPK, EventTypes.MODIFY, orderBatch.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }

    public void deleteOrderBatch(OrderBatch orderBatch, BasePK deletedBy) {
        orderBatch.setThruTime(session.getStartTime());

        sendEvent(orderBatch.getBatchPK(), EventTypes.MODIFY, orderBatch.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }

    public void deleteOrderBatch(Batch batch, BasePK deletedBy) {
        deleteOrderBatch(getOrderBatchForUpdate(batch), deletedBy);
    }

}
